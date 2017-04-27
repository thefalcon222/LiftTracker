package com.example.ryan.lifttracker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ryan on 4/25/2017.
 */

public class WorkoutDB extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "workouts.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES = "create table " +
            DBConstants.TABLE_NAME +
            "( " + DBConstants.WORKOUT_ID+ " integer primary key autoincrement, "
            + DBConstants.COLUMN_DATE_NAME +" text,"
            + DBConstants.COLUMN_WORKOUT_NAME+" text,"
            +DBConstants.COLUMN_DESCRIPTION_NAME +" text,"
            +DBConstants.COLUMN_REPS_NAME +" text";


    public WorkoutDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
