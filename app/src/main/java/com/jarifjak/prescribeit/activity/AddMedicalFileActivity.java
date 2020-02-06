package com.jarifjak.prescribeit.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.jarifjak.prescribeit.R;
import com.jarifjak.prescribeit.database.DatabaseManager;
import com.jarifjak.prescribeit.model.MedicalHistory;
import com.jarifjak.prescribeit.utils.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.SimpleTimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class AddMedicalFileActivity extends AppCompatActivity {

    @BindView(R.id.medicalHistoryIV)
    AppCompatImageView medicalHistoryIV;
    @BindView(R.id.takePhotoTV)
    AppCompatTextView takePhotoTV;
    @BindView(R.id.doctorNameET)
    AppCompatEditText doctorNameET;
    @BindView(R.id.detailsET)
    AppCompatEditText detailsET;
    @BindView(R.id.dateTV)
    AppCompatTextView dateTV;

    private DatabaseManager databaseManager;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private String creationTime;
    private String filePath;
    private boolean imageCreationFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medical_file);
        ButterKnife.bind(this);

        databaseManager = new DatabaseManager(this);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @OnClick({R.id.takePhotoTV, R.id.dateTV})
    public void onViewClicked(View view) {

        switch (view.getId()) {

            case R.id.takePhotoTV:
                takePhoto();
                break;

            case R.id.dateTV:
                pickDate();
                break;
        }
    }


    private void pickDate() {

        calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(AddMedicalFileActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                creationTime = day + "-" + (month + 1) + "-" + year;

                dateTV.setText(creationTime);
            }

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }


    private void takePhoto() {

        imageCreationFlag = false;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, Constants.PERMISSION_REQUEST_CODE);

        } else {

            dispatchTakePictureIntent();
        }
    }


    void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            startActivityForResult(takePictureIntent, Constants.REQUEST_IMAGE_CAPTURE);

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == Constants.PERMISSION_REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                dispatchTakePictureIntent();

            } else {

                Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                        showDialogAndMessage("You need to allow access permissions",

                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                            ActivityCompat.requestPermissions(AddMedicalFileActivity.this,
                                                    new String[]{Manifest.permission.CAMERA},
                                                    Constants.PERMISSION_REQUEST_CODE);

                                        }
                                    }
                                });
                    }
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            medicalHistoryIV.setImageBitmap(imageBitmap);

            File imageFile = createImageFile();
            FileOutputStream fileOutputStream;

            try {

                fileOutputStream = new FileOutputStream(imageFile);
                Objects.requireNonNull(imageBitmap).compress(Bitmap.CompressFormat.JPEG,100, fileOutputStream);

                fileOutputStream.flush();
                fileOutputStream.close();

                imageCreationFlag = true;


            } catch (IOException e) {

                Log.d("mylog", e.toString());

            }

        }
    }

    private void showDialogAndMessage(String message, DialogInterface.OnClickListener okListener) {

        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private File createImageFile() {

        String fileName = "PI_" + System.currentTimeMillis() + ".jpeg";
        String dirPath = "PrescribeIT";
        filePath = "PrescribeIT" + File.separatorChar + fileName;

        File file = new File(Objects.requireNonNull(getApplicationContext().getExternalFilesDir(null)).getAbsolutePath(), dirPath);

        if (!file.exists()) {

            file.mkdirs();
        }

        File imageFile = new File(Objects.requireNonNull(getApplicationContext().getExternalFilesDir(null)).getAbsolutePath(), filePath);

        return imageFile;
    }

    private void saveMedicalHistory() {

        String doctorName = doctorNameET.getText().toString().trim();
        String details = detailsET.getText().toString().trim();

        if (doctorName.length() == 0 || details.length() == 0 || creationTime.length() ==0 || !imageCreationFlag) {

            if (doctorName.length() == 0)

                doctorNameET.setError("Please Enter Doctor's Name!!");

            else if (details.length() == 0)

                detailsET.setError("Please Enter Doctor's Details!!");

            else if (creationTime.length() == 0)

                dateTV.setError("Please Select Date!!");

            else if (imageCreationFlag)

                Toast.makeText(this, "Please Select Image", Toast.LENGTH_SHORT).show();

            return;
        }

        MedicalHistory medicalHistory = new MedicalHistory(filePath, doctorName, details, creationTime);
        boolean isInserted = databaseManager.insertMedicalHistory(medicalHistory);

        if (isInserted)

            Toast.makeText(this, "Inserted!!", Toast.LENGTH_SHORT).show();

        else

            Toast.makeText(this, "Failed!!", Toast.LENGTH_SHORT).show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.save_action, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            finish();

        } else if (item.getItemId() == R.id.action_save) {

            saveMedicalHistory();

        }

        return super.onOptionsItemSelected(item);
    }



}
