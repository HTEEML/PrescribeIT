package com.jarifjak.prescribeit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jarifjak.prescribeit.model.Doctor;
import com.jarifjak.prescribeit.utils.Constants;

import java.util.ArrayList;
import java.util.List;

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

    public List<Doctor> getAllDoctors() {

        database = databaseHelper.getReadableDatabase();
        List<Doctor> doctors = new ArrayList<>();

        String getAllDoctorsQuery = "SELECT " + Constants.FIRST_NAME + ", "
                                              + Constants.LAST_NAME + ", "
                                              + Constants.DETAILS + ", "
                                              + Constants.NUMBER + " FROM " + Constants.DOCTOR_TABLE_NAME;

        Cursor cursor = database.rawQuery(getAllDoctorsQuery, null);

        if (cursor.moveToFirst()) {

            do {

                Doctor d = new Doctor();

                d.setFirstName(cursor.getString(cursor.getColumnIndex(Constants.FIRST_NAME)));
                d.setLastName(cursor.getString(cursor.getColumnIndex(Constants.LAST_NAME)));
                d.setNumber(cursor.getString(cursor.getColumnIndex(Constants.NUMBER)));
                d.setDetails(cursor.getString(cursor.getColumnIndex(Constants.DETAILS)));

                doctors.add(d);

            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

        return doctors;
    }

}
