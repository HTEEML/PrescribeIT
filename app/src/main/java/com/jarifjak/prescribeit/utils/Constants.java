package com.jarifjak.prescribeit.utils;

public interface Constants {

    //DB related

    String DB_NAME = "PrescribeIt";
    int DB_VERSION = 1;


    //Table telated

    String DOCTOR_TABLE_NAME = "doctors";

    String ID = "id";
    String FIRST_NAME = "first_name";
    String LAST_NAME = "last_name";
    String DETAILS = "details";
    String APPOINTMENT_DATE = "appointment";
    String NUMBER = "number";
    String EMAIL = "email";

    //MedicalHistory related

    int REQUEST_IMAGE_CAPTURE = 1;
    int PERMISSION_REQUEST_CODE = 2;
}
