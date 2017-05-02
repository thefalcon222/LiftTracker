package com.example.ryan.lifttracker.database;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;

/**
 * Created by Andrew on 5/2/2017.
 */

public class DBController {

    private SQLiteDatabase db;
    private WorkoutDB db_helper;
    private volatile Boolean processing = false;

    public DBController(Context context, Application application) {
        db_helper = new WorkoutDB(context);
    }


    // You need to call this open up the database
    public void OpenDB() {
        db = db_helper.getWritableDatabase();
    }

    public void CloseDB() {
        db.close();
        db = null;
    }

    private boolean IsFree() {
        return !processing;
    }

    public void InsertWorkouts(final ArrayList<ContentValues> workout_list) {

        Log.d("db", "insert workouts called id db open ?" + db.isOpen() + " IsFree to write? " + IsFree());

        for (int i = 0; i < workout_list.size(); i++) {
            db.insert(DBConstants.TABLE_NAME, null, workout_list.get(i));
        }
    }

}
