package com.jarifjak.prescribeit.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.jarifjak.prescribeit.R;
import com.jarifjak.prescribeit.model.Doctor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewDoctorProfileActivity extends AppCompatActivity {

    @BindView(R.id.nameFieldIndividual)
    AppCompatTextView nameTV;
    @BindView(R.id.detailsFieldIndividual)
    AppCompatTextView detailsTV;
    @BindView(R.id.appointmentFieldIndividual)
    AppCompatTextView appointmentTV;
    @BindView(R.id.phoneFieldIndividual)
    AppCompatTextView numberTV;
    @BindView(R.id.emailFieldIndividual)
    AppCompatTextView emailTV;
    @BindView(R.id.updateButton)
    AppCompatButton updateButton;
    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.doctorLogo)
    AppCompatImageView doctorLogo;


    private Doctor doctor;
    private String jsonObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doctor_profile);
        ButterKnife.bind(this);

        jsonObject = getIntent().getStringExtra("doctor");

        doctor = new Gson().fromJson(jsonObject, Doctor.class);

        setFields();
    }

    @SuppressLint("SetTextI18n")
    void setFields() {

        nameTV.setText(doctor.getFirstName() + doctor.getLastName());
        detailsTV.setText(doctor.getDetails());
        appointmentTV.setText(doctor.getAppointmentDate());
        numberTV.setText(doctor.getNumber());
        emailTV.setText(doctor.getEmail());
    }

    @OnClick({R.id.updateButton, R.id.fab})
    public void onViewClicked(View view) {

        switch (view.getId()) {

            case R.id.updateButton:

                startActivity(new Intent(this, SearchActivity.class).putExtra("doctor",jsonObject));
                finish();

                break;

            case R.id.fab:

                call(doctor.getNumber());
                break;
        }
    }

    public void call(String number) {

        Intent intent = new Intent(Intent.ACTION_CALL);

        intent.setData(Uri.parse("tel:" + number));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},400);

        }

        startActivity(intent);
    }

}
