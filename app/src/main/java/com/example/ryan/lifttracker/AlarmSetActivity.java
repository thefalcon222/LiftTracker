package com.example.ryan.lifttracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.Calendar;

import static android.Manifest.permission_group.CALENDAR;
import static android.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * Created by conor on 5/2/2017.
 */

public class AlarmSetActivity extends AppCompatActivity{

    CheckBox sunBox;
    CheckBox monBox;
    CheckBox tueBox;
    CheckBox wedBox;
    CheckBox thuBox;
    CheckBox friBox;
    CheckBox satBox;
    Button closeButton;
    ArrayList<Boolean> alarmSetList;
    ArrayList<PendingIntent> pendingIntentList;
    AlarmManager alarmManager;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarmset);

        //Open up Preferences Editor
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();

        //Create & Set alarmSetList
        alarmSetList = new ArrayList<>(7);
        alarmSetList.add(prefs.getBoolean("Sunday", false));
        alarmSetList.add(prefs.getBoolean("Monday", false));
        alarmSetList.add(prefs.getBoolean("Tuesday", false));
        alarmSetList.add(prefs.getBoolean("Wednesday", false));
        alarmSetList.add(prefs.getBoolean("Thursday", false));
        alarmSetList.add(prefs.getBoolean("Friday", false));
        alarmSetList.add(prefs.getBoolean("Saturday", false));

        //Create AlarmManager and pendingIntentList
        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        pendingIntentList = new ArrayList<>(7);
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        for (int i = 0; i < 7; i++) {
            pendingIntentList.add(PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0));
        }

        sunBox = (CheckBox)findViewById(R.id.sundayCheckBox);
        monBox = (CheckBox)findViewById(R.id.mondayCheckBox);
        tueBox = (CheckBox)findViewById(R.id.tuesdayCheckBox);
        wedBox = (CheckBox)findViewById(R.id.wednesdayCheckBox);
        thuBox = (CheckBox)findViewById(R.id.thursdayCheckBox);
        friBox = (CheckBox)findViewById(R.id.fridayCheckBox);
        satBox = (CheckBox)findViewById(R.id.saturdayCheckBox);
        closeButton = (Button)findViewById(R.id.closeButton);

        sunBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = ((CheckBox) v).isChecked();
                if (selected) {
                    //Log.d("DBSP", "selected is " + selected);
                    setAlarm(0, Calendar.SUNDAY);
                }
                else {
                    //Log.d("DBSP", "selected is " + selected);
                    unsetAlarm(0);
                }
                editor.putBoolean("Sunday", alarmSetList.get(0));
                editor.commit();
                //checkSharedPreferences();
            }
        });

        monBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = ((CheckBox) v).isChecked();
                if (selected) {
                    setAlarm(1, Calendar.MONDAY);
                }
                else {
                    unsetAlarm(1);
                }
                editor.putBoolean("Monday", alarmSetList.get(1));
                editor.commit();
            }
        });

        tueBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = ((CheckBox) v).isChecked();
                if (selected) {
                    setAlarm(2, Calendar.TUESDAY);
                }
                else {
                    unsetAlarm(2);
                }
                editor.putBoolean("Tuesday", alarmSetList.get(2));
                editor.commit();
            }
        });

        wedBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = ((CheckBox) v).isChecked();
                if (selected) {
                    setAlarm(3, Calendar.WEDNESDAY);
                }
                else {
                    unsetAlarm(3);
                }
                editor.putBoolean("Wednesday", alarmSetList.get(3));
                editor.commit();
            }
        });

        thuBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = ((CheckBox) v).isChecked();
                if (selected) {
                    setAlarm(4, Calendar.THURSDAY);
                }
                else {
                    unsetAlarm(4);
                }
                editor.putBoolean("Thursday", alarmSetList.get(4));
                editor.commit();
            }
        });

        friBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = ((CheckBox) v).isChecked();
                if (selected) {
                    setAlarm(5, Calendar.FRIDAY);
                }
                else {
                    unsetAlarm(5);
                }
                editor.putBoolean("Friday", alarmSetList.get(5));
                editor.commit();
            }
        });

        satBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = ((CheckBox) v).isChecked();
                if (selected) {
                    setAlarm(6, Calendar.SATURDAY);
                }
                else {
                    unsetAlarm(6);
                }
                editor.putBoolean("Saturday", alarmSetList.get(6));
                editor.commit();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Read Preferences and pre-set checkmarks
        presetBox("Sunday", sunBox);
        presetBox("Monday", monBox);
        presetBox("Tuesday", tueBox);
        presetBox("Wednesday", wedBox);
        presetBox("Thursday", thuBox);
        presetBox("Friday", friBox);
        presetBox("Saturday", satBox);
    }

    public void setAlarm(int dayNum, int calendarDay) {

        alarmSetList.set(dayNum, true);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.DAY_OF_WEEK, calendarDay);
        c.set(Calendar.HOUR_OF_DAY, 12);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),
                7 * AlarmManager.INTERVAL_DAY, pendingIntentList.get(dayNum));

        checkSharedPreferences();
    }

    public void unsetAlarm(int dayNum) {
        alarmSetList.set(dayNum, false);
        alarmManager.cancel(pendingIntentList.get(dayNum));

        checkSharedPreferences();
    }

    public void presetBox(String key, CheckBox c) {
        Log.d("PresetBox Debug", "" + key + " is " + prefs.getBoolean(key, false));
        if (prefs.getBoolean(key, false)) {
            c.setChecked(true);
        }
        else {
            c.setChecked(false);
        }
    }

    public void checkSharedPreferences() {
        Log.d("DBSP", "Sunday: " + prefs.getBoolean("Sunday", false));
        Log.d("DBSP", "Monday: " + prefs.getBoolean("Monday", false));
        Log.d("DBSP", "Tuesday: " + prefs.getBoolean("Tuesday", false));
        Log.d("DBSP", "Wednesday: " + prefs.getBoolean("Wednesday", false));
        Log.d("DBSP", "Thursday: " + prefs.getBoolean("Thursday", false));
        Log.d("DBSP", "Friday: " + prefs.getBoolean("Friday", false));
        Log.d("DBSP", "Saturday: " + prefs.getBoolean("Saturday", false));
    }
}
