package com.example.ryan.lifttracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
    ArrayList<Boolean> alarmSetList;
    ArrayList<PendingIntent> pendingIntentList;
    AlarmManager alarmManager;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarmset);

        //Create alarmSetList
        alarmSetList = new ArrayList<>(7);

        //Create AlarmManager and pendingIntentList
        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        pendingIntentList = new ArrayList<>(7);
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        for (PendingIntent pi : pendingIntentList) {
            pi = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
        }

        //Open up Preferences Editor
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();

        sunBox = (CheckBox)findViewById(R.id.sundayCheckBox);
        monBox = (CheckBox)findViewById(R.id.mondayCheckBox);
        tueBox = (CheckBox)findViewById(R.id.tuesdayCheckBox);
        wedBox = (CheckBox)findViewById(R.id.wednesdayCheckBox);
        thuBox = (CheckBox)findViewById(R.id.thursdayCheckBox);
        friBox = (CheckBox)findViewById(R.id.fridayCheckBox);
        satBox = (CheckBox)findViewById(R.id.saturdayCheckBox);

        sunBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = v.isSelected();
                if (selected) {
                    setAlarm(0, Calendar.SUNDAY);
                }
                else {
                    unsetAlarm(0);
                }
                editor.putBoolean("Sunday", alarmSetList.get(0));
                editor.apply();
            }
        });

        monBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = v.isSelected();
                if (selected) {
                    setAlarm(1, Calendar.MONDAY);
                }
                else {
                    unsetAlarm(1);
                }
                editor.putBoolean("Monday", alarmSetList.get(1));
                editor.apply();
            }
        });

        tueBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = v.isSelected();
                if (selected) {
                    setAlarm(2, Calendar.TUESDAY);
                }
                else {
                    unsetAlarm(2);
                }
                editor.putBoolean("Tuesday", alarmSetList.get(2));
                editor.apply();
            }
        });

        wedBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = v.isSelected();
                if (selected) {
                    setAlarm(3, Calendar.WEDNESDAY);
                }
                else {
                    unsetAlarm(3);
                }
                editor.putBoolean("Wednesday", alarmSetList.get(3));
                editor.apply();
            }
        });

        thuBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = v.isSelected();
                if (selected) {
                    setAlarm(4, Calendar.THURSDAY);
                }
                else {
                    unsetAlarm(4);
                }
                editor.putBoolean("Thursday", alarmSetList.get(4));
                editor.apply();
            }
        });

        friBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = v.isSelected();
                if (selected) {
                    setAlarm(5, Calendar.FRIDAY);
                }
                else {
                    unsetAlarm(5);
                }
                editor.putBoolean("Friday", alarmSetList.get(5));
                editor.apply();
            }
        });

        satBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = v.isSelected();
                if (selected) {
                    setAlarm(6, Calendar.SATURDAY);
                }
                else {
                    unsetAlarm(6);
                }
                editor.putBoolean("Saturday", alarmSetList.get(7));
                editor.apply();
            }
        });

        //Read Preferences and pre-set checkmarks
    }

    public void setAlarm(int dayNum, int calendarDay) {

        alarmSetList.set(dayNum, true);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.DAY_OF_WEEK, calendarDay);
        c.set(Calendar.HOUR_OF_DAY, 12);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),
                7 * AlarmManager.INTERVAL_DAY, pendingIntentList.get(dayNum));
    }

    public void unsetAlarm(int dayNum) {
        alarmSetList.set(dayNum, false);
        alarmManager.cancel(pendingIntentList.get(dayNum));
    }
}
