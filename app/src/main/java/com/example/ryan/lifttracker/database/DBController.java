package com.example.ryan.lifttracker.database;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.ryan.lifttracker.socketio.SocketIO;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import static android.R.attr.x;

/**
 * Created by Andrey on 4/8/2017.
 */

public class DBController {

    private SQLiteDatabase db;
    private WorkoutDB db_helper;
    private volatile Boolean processing = false;
    private Context context;
    private Socket mSocket;

    public DBController(Context context, Application application) {
        db_helper = new WorkoutDB(context);
        this.context = context;

        SocketIO app = (SocketIO)application;
        mSocket = app.getSocket();
        mSocket.connect();
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

        Log.d("db","insert workouts called id db open ?"+db.isOpen()+" IsFree to write? "+IsFree());

        for (int i = 0; i < workout_list.size(); i++)
        {
            db.insert(DBConstants.TABLE_NAME,null, workout_list.get(i));
        }

        this.UploadWorkouts();
    }

    //TODO this needs to upload workouts instead of steps
    private void UploadWorkouts() {

        Log.d("db","Upload workouts called"+" isFree "+IsFree());

        if (db.isOpen() && IsFree() ) {

            new AsyncTask<Void, Void, Void>() {

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                Cursor cursor = null;
                Float total = new Float(0f);
                JSONObject data;

                @Override
                protected Void doInBackground(Void... params) {
                    if (!mSocket.connected())
                        mSocket.connect();

                    JSONObject data;
                    JSONObject data2 = new JSONObject();

                    processing = true;

                    cursor = db.rawQuery("SELECT * FROM " + DBConstants.TABLE_NAME, null);


                    if (cursor.moveToFirst()) {
                        do {

                            //TODO needs to be made relevant to workouts
                            /*
                            Float delta = cursor.getFloat(cursor.getColumnIndex(DBConstants.COLUMN_NAME_INCREMENT_VALUE));
                            total = cursor.getFloat(cursor.getColumnIndex(DBConstants.COLUMN_NAME_TOTAL_VALUE));
                            String date = cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_NAME_ENTRY_DATE));
                            Integer _id = cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_NAME_ENTRY_ID));
                            Long epoch = cursor.getLong(cursor.getColumnIndex(DBConstants.COLUMN_NAME_ENTRY_DATE_EPOCH));
                            */

                            data = new JSONObject();
                            try {
                                //TODO needs to be made relevant to workouts
                                /*
                                data.put("username", prefs.getString("username", ""));
                                data.put("delta", delta + "");
                                data.put("total", total + "");
                                data.put("date", date + "");
                                data.put("epoch", epoch);
                                data.put("id", _id + "");
                                data.put("type", "workouts");
                                */
                                data2 = data;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            if (mSocket != null) {
                                mSocket.emit("workouts", data);


                            }

                        } while (cursor.moveToNext());
                    }
                    cursor.close();

                    processing = false;
                    mSocket.emit("workouts_upload_done", data2);


                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {


                }

            }.execute();

        }
    }

}
