package com.jarifjak.prescribeit.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.jarifjak.prescribeit.R;
import com.jarifjak.prescribeit.database.DatabaseManager;
import com.jarifjak.prescribeit.model.Doctor;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;

public class AddDoctorActivity extends AppCompatActivity {

    @BindView(R.id.firstNameET)
    AppCompatEditText firstNameET;
    @BindView(R.id.lastNameET)
    AppCompatEditText lastNameET;
    @BindView(R.id.detailsET)
    AppCompatEditText detailsET;
    @BindView(R.id.appointmentET)
    AppCompatEditText appointmentET;
    @BindView(R.id.phoneNumberET)
    AppCompatEditText phoneNumberET;
    @BindView(R.id.emailET)
    AppCompatEditText emailET;
    @BindView(R.id.saveDoctorBTN)
    Button saveDoctor;

    private DatabaseManager databaseManager;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private String creationTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);
        ButterKnife.bind(this);

        databaseManager = new DatabaseManager(this);
    }


    @OnFocusChange({R.id.firstNameET, R.id.lastNameET, R.id.detailsET, R.id.phoneNumberET, R.id.emailET})
    public void onFocusChanged(View view, boolean focused) {

        switch (view.getId()) {

            case R.id.firstNameET:
                firstNameET.setHint("");
                break;

            case R.id.lastNameET:
                lastNameET.setHint("");
                break;

            case R.id.detailsET:
                detailsET.setHint("");
                break;

            case R.id.phoneNumberET:
                phoneNumberET.setHint("");
                break;

            case R.id.emailET:
                emailET.setHint("");
                break;
        }

    }


    @OnClick({R.id.saveDoctorBTN, R.id.appointmentET})
    public void onViewClicked(View view) {

        if (view.getId() == R.id.appointmentET) {

            appointmentET.setHint("");

            calendar = Calendar.getInstance();

            datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                    creationTime = day + "-" + (month + 1) + "-" + year;

                    appointmentET.setText(creationTime);
                }

            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.show();

        } else if (view.getId() == R.id.saveDoctorBTN) {

            String firstName = firstNameET.getText().toString().trim();
            String lastName = lastNameET.getText().toString().trim();
            String number = phoneNumberET.getText().toString().trim();
            String appointment = creationTime;
            String email = emailET.getText().toString().trim();
            String details = detailsET.getText().toString().trim();


            if (firstName.length() == 0 || lastName.length() == 0 || number.length() == 0 || email.length() == 0 || details.length() == 0 || appointment.length() == 0) {

                if (firstName.length() == 0)

                    firstNameET.setError("Please Enter First Name!!");

                else if (lastName.length() == 0)

                    lastNameET.setError("Please Enter Last Name!!");

                else if (details.length() == 0)

                    detailsET.setError("Please Enter Phone Number!!");

                else if (appointment.length() == 0)

                    appointmentET.setError("Please Enter Email Address!!");

                else if (number.length() == 0)

                    phoneNumberET.setError("Please Enter Details!!");

                else if (email.length() == 0)

                    emailET.setError("Please Select Date!!");


                return;
            }

            Doctor doctor = new Doctor(0, firstName, lastName, details, appointment, number, email);
            boolean isInserted = databaseManager.insertDoctor(doctor);

            String message = (isInserted) ? "Inserted!!" : "Failed!!";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }
}
