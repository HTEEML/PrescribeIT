package com.jarifjak.prescribeit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.jarifjak.prescribeit.model.Doctor;
import com.jarifjak.prescribeit.utils.Constants;

public class DatabaseManager {

    private final DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public DatabaseManager(Context context) {

        databaseHelper = new DatabaseHelper(context);

    }

    public boolean insertDoctor(Doctor doctor) {

        database = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(Constants.FIRST_NAME, doctor.getFirstName());
        contentValues.put(Constants.LAST_NAME, doctor.getLastName());
        contentValues.put(Constants.DETAILS, doctor.getDetails());
        contentValues.put(Constants.APPOINTMENT_DATE, doctor.getAppointmentDate());
        contentValues.put(Constants.NUMBER, doctor.getNumber());
        contentValues.put(Constants.EMAIL, doctor.getEmail());

        long isInserted = database.insert(Constants.DOCTOR_TABLE_NAME, null, contentValues);

        database.close();

        return isInserted > 0;
    }

}
