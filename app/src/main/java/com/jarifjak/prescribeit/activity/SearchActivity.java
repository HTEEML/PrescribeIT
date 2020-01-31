package com.jarifjak.prescribeit.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.gson.Gson;
import com.jarifjak.prescribeit.R;
import com.jarifjak.prescribeit.database.DatabaseManager;
import com.jarifjak.prescribeit.model.Doctor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.searchDoctorName)
    AppCompatEditText searchDoctorET;
    @BindView(R.id.searchButton)
    AppCompatButton searchButton;
    @BindView(R.id.firstNameET)
    AppCompatEditText firstNameET;
    @BindView(R.id.lastNameET)
    AppCompatEditText lastNameET;
    @BindView(R.id.detailsET)
    AppCompatEditText detailsET;
    @BindView(R.id.appointmentET)
    AppCompatEditText appointmentET;
    @BindView(R.id.phoneNumberET)
    AppCompatEditText numberET;
    @BindView(R.id.emailET)
    AppCompatEditText emailET;
    @BindView(R.id.updateButton)
    AppCompatButton updateButton;
    @BindView(R.id.demoFields)
    LinearLayout demoFields;
    @BindView(R.id.searchLayout)
    LinearLayout searchLayout;
    @BindView(R.id.noResultFoundTV)
    AppCompatTextView noResultFoundTV;

    private DatabaseManager databaseManager;
    DatePickerDialog datePickerDialog;
    private String creationTime;
    private Doctor doctor;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        databaseManager = new DatabaseManager(this);

        String jsonObject = getIntent().getStringExtra("doctor");

        if (jsonObject != null) {

            doctor = new Gson().fromJson(jsonObject, Doctor.class);
            initializeFields(doctor);
        }

    }


    private void initializeFields(Doctor doctor) {

        id = doctor.getId();
        firstNameET.setText(doctor.getFirstName());
        lastNameET.setText(doctor.getLastName());
        detailsET.setText(doctor.getDetails());

        creationTime = doctor.getAppointmentDate();

        appointmentET.setText(creationTime);
        numberET.setText(doctor.getNumber());
        emailET.setText(doctor.getEmail());

        noResultFoundTV.setVisibility(View.GONE);
        demoFields.setVisibility(View.VISIBLE);
    }


    @OnClick({R.id.searchButton, R.id.updateButton, R.id.appointmentET})
    public void onViewClicked(View view) {

        if (view.getId() == R.id.appointmentET) {

            appointmentET.setHint("");

            datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                    creationTime = day + "-" + (month + 1) + "-" + year;

                    appointmentET.setText(creationTime);
                }
            }, 2018, 7, 29);

            datePickerDialog.show();

        } else if (view.getId() == R.id.updateButton) {

            String firstName = firstNameET.getText().toString().trim();
            String lastName = lastNameET.getText().toString().trim();
            String number = numberET.getText().toString().trim();
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

                    numberET.setError("Please Enter Details!!");

                else if (email.length() == 0)

                    emailET.setError("Please Select Date!!");


                return;
            }

            doctor = new Doctor(id, firstName, lastName, details, appointment, number, email);
            boolean isUpdated = databaseManager.updateDoctor(doctor);

            String message = (isUpdated) ? "Updated!!" : "Failed to Updated!!";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        } else if (view.getId() == R.id.searchButton) {

            doctor = databaseManager.getDoctorByName(searchDoctorET.getText().toString().trim());

            if (doctor == null) {

                demoFields.setVisibility(View.GONE);
                noResultFoundTV.setVisibility(View.VISIBLE);
                noResultFoundTV.setText("No Result Found!");

                return;
            }

            initializeFields(doctor);
        }

    }
}
