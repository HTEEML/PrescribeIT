package com.jarifjak.prescribeit.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.jarifjak.prescribeit.utils.Constants;

public class DatabaseHelper extends SQLiteOpenHelper {

    private final Context context;

    public DatabaseHelper(Context context) {

        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String doctorTableQuery = "CREATE TABLE IF NOT EXISTS " + Constants.DOCTOR_TABLE_NAME + "("
                + Constants.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constants.FIRST_NAME + " TEXT,"
                + Constants.LAST_NAME + " TEXT,"
                + Constants.DETAILS + " TEXT,"
                + Constants.APPOINTMENT_DATE + " TEXT,"
                + Constants.NUMBER + " TEXT,"
                + Constants.EMAIL + " TEXT)";

        String mHistoryTableQuery = "CREATE TABLE IF NOT EXISTS " + Constants.M_HISTORY_TABLE_NAME + "("
                + Constants.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constants.IMAGE_PATH + " TEXT,"
                + Constants.DOCTOR_NAME + " TEXT,"
                + Constants.DETAILS + " TEXT,"
                + Constants.DATE + " TEXT)";

        db.execSQL(doctorTableQuery);
        db.execSQL(mHistoryTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Constants.DOCTOR_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.M_HISTORY_TABLE_NAME);

        onCreate(db);
    }

}
