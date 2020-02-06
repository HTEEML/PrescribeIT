package com.jarifjak.prescribeit.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jarifjak.prescribeit.model.Doctor;
import com.jarifjak.prescribeit.model.MedicalHistory;
import com.jarifjak.prescribeit.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private final DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public DatabaseManager(Context context) {

        databaseHelper = new DatabaseHelper(context);

    }


    /**
     * Doctor Related
     **/


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

        String getAllDoctorsQuery = "SELECT " + Constants.ID + ", "
                + Constants.FIRST_NAME + ", "
                + Constants.LAST_NAME + ", "
                + Constants.DETAILS + ", "
                + Constants.APPOINTMENT_DATE + ", "
                + Constants.NUMBER + ", "
                + Constants.EMAIL + " FROM " + Constants.DOCTOR_TABLE_NAME;

        Cursor cursor = database.rawQuery(getAllDoctorsQuery, null);

        if (cursor.moveToFirst()) {

            do {

                Doctor d = new Doctor();

                d.setId(cursor.getInt(cursor.getColumnIndex(Constants.ID)));
                d.setFirstName(cursor.getString(cursor.getColumnIndex(Constants.FIRST_NAME)));
                d.setLastName(cursor.getString(cursor.getColumnIndex(Constants.LAST_NAME)));
                d.setDetails(cursor.getString(cursor.getColumnIndex(Constants.DETAILS)));
                d.setAppointmentDate(cursor.getString(cursor.getColumnIndex(Constants.APPOINTMENT_DATE)));
                d.setNumber(cursor.getString(cursor.getColumnIndex(Constants.NUMBER)));
                d.setEmail(cursor.getString(cursor.getColumnIndex(Constants.EMAIL)));

                doctors.add(d);

            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

        return doctors;
    }

    public boolean updateDoctor(Doctor doctor) {

        database = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(Constants.FIRST_NAME, doctor.getFirstName());
        contentValues.put(Constants.LAST_NAME, doctor.getLastName());
        contentValues.put(Constants.DETAILS, doctor.getDetails());
        contentValues.put(Constants.APPOINTMENT_DATE, doctor.getAppointmentDate());
        contentValues.put(Constants.NUMBER, doctor.getNumber());
        contentValues.put(Constants.EMAIL, doctor.getEmail());

        String whereClause = Constants.ID + " = ?";
        String[] whereArgs = {String.valueOf(doctor.getId())};

        int isUpdated = database.update(Constants.DOCTOR_TABLE_NAME, contentValues, whereClause, whereArgs);

        database.close();

        return isUpdated > 0;
    }


    public Doctor getDoctorByName(String name) {

        database = databaseHelper.getReadableDatabase();

        Doctor doctor = new Doctor();

        String query = "SELECT * FROM " + Constants.DOCTOR_TABLE_NAME + " WHERE " + Constants.FIRST_NAME +
                " LIKE '%" + name + "%';";

        Cursor cursor = database.rawQuery(query, null);

        if (cursor.getCount() != 0) {

            if (cursor.moveToFirst()) {

                int doctorId = cursor.getInt(cursor.getColumnIndex(Constants.ID));
                String firstName = cursor.getString(cursor.getColumnIndex(Constants.FIRST_NAME));
                String lastName = cursor.getString(cursor.getColumnIndex(Constants.LAST_NAME));
                String details = cursor.getString(cursor.getColumnIndex(Constants.DETAILS));
                String appointment = cursor.getString(cursor.getColumnIndex(Constants.APPOINTMENT_DATE));
                String number = cursor.getString(cursor.getColumnIndex(Constants.NUMBER));
                String email = cursor.getString(cursor.getColumnIndex(Constants.EMAIL));


                doctor = new Doctor(doctorId, firstName, lastName, details, appointment, number, email);

                cursor.close();
                database.close();

            }

            return doctor;
        }

        query = "SELECT * FROM " + Constants.DOCTOR_TABLE_NAME + " WHERE " + Constants.LAST_NAME +
                " LIKE '%" + name + "%';";

        cursor = database.rawQuery(query, null);

        if (cursor.getCount() != 0) {

            if (cursor.moveToFirst()) {

                int doctorId = cursor.getInt(cursor.getColumnIndex(Constants.ID));
                String firstName = cursor.getString(cursor.getColumnIndex(Constants.FIRST_NAME));
                String lastName = cursor.getString(cursor.getColumnIndex(Constants.LAST_NAME));
                String details = cursor.getString(cursor.getColumnIndex(Constants.DETAILS));
                String appointment = cursor.getString(cursor.getColumnIndex(Constants.APPOINTMENT_DATE));
                String number = cursor.getString(cursor.getColumnIndex(Constants.NUMBER));
                String email = cursor.getString(cursor.getColumnIndex(Constants.EMAIL));


                doctor = new Doctor(doctorId, firstName, lastName, details, appointment, number, email);

                cursor.close();
                database.close();

            }

            return doctor;
        }

        database.close();
        return null;

    }

    /**
     * Medical History Related
     **/


    public boolean insertMedicalHistory(MedicalHistory medicalHistory) {

        database = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(Constants.IMAGE_PATH, medicalHistory.getImagePath());
        contentValues.put(Constants.DOCTOR_NAME, medicalHistory.getDoctorName());
        contentValues.put(Constants.DETAILS, medicalHistory.getDetails());
        contentValues.put(Constants.DATE, medicalHistory.getDate());

        long isInserted = database.insert(Constants.M_HISTORY_TABLE_NAME, null, contentValues);

        database.close();

        return isInserted > 0;
    }

    public List<MedicalHistory> getAllMedicalHistory() {

        database = databaseHelper.getReadableDatabase();
        List<MedicalHistory> medicalHistories = new ArrayList<>();

        String getAllMHistoriesQuery = "SELECT * FROM " + Constants.M_HISTORY_TABLE_NAME;

        Cursor cursor = database.rawQuery(getAllMHistoriesQuery, null);

        if (cursor.moveToFirst()) {

            do {

                MedicalHistory m = new MedicalHistory();

                m.setId(cursor.getInt(cursor.getColumnIndex(Constants.ID)));
                m.setDoctorName(cursor.getString(cursor.getColumnIndex(Constants.DOCTOR_NAME)));
                m.setImagePath(cursor.getString(cursor.getColumnIndex(Constants.IMAGE_PATH)));
                m.setDetails(cursor.getString(cursor.getColumnIndex(Constants.DETAILS)));
                m.setDate(cursor.getString(cursor.getColumnIndex(Constants.DATE)));

                medicalHistories.add(m);

            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

        return medicalHistories;

    }

}
