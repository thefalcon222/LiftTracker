package com.example.ryan.lifttracker;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
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

import static com.example.ryan.lifttracker.constants.Constants.FRIDAY;
import static com.example.ryan.lifttracker.constants.Constants.INTERVAL_WEEK;
import static com.example.ryan.lifttracker.constants.Constants.MONDAY;
import static com.example.ryan.lifttracker.constants.Constants.SATURDAY;
import static com.example.ryan.lifttracker.constants.Constants.SUNDAY;
import static com.example.ryan.lifttracker.constants.Constants.THURSDAY;
import static com.example.ryan.lifttracker.constants.Constants.TUESDAY;
import static com.example.ryan.lifttracker.constants.Constants.WEDNESDAY;

/**
 * Created by Conor Ginnell
 *
 * AlarmSetActivity launches when the user selects Set Alarm.  It provides them
 * with an option to set alarms for different days of the week.  When these
 * days are selected, the AlarmManager sets an Alarm to go off at approximately
 * 12:00 P.M. on the specified days, and repeat that Alarm every specified day
 * at 12:00 P.M. until the user deselects the day.
 *
 * User preferences on which days are set are stored in SharedPreferences using
 * the days of the week as keys, and booleans as values.
 *
 * The Activity maintains an ArrayList of days of the week that are set.  This
 * may be redundant.  The Receiver also maintains an ArrayList of Pending
 * Intents, one for each day of the week.
 */

public class AlarmSetActivity extends AppCompatActivity{

    private CheckBox sunBox;
    private CheckBox monBox;
    private CheckBox tueBox;
    private CheckBox wedBox;
    private CheckBox thuBox;
    private CheckBox friBox;
    private CheckBox satBox;
    private Button closeButton;
    private static ArrayList<Boolean> alarmSetList;
    private static ArrayList<PendingIntent> pendingIntentList;
    private static AlarmManager alarmManager;
    private static SharedPreferences prefs;
    private SharedPreferences.Editor editor;


    /**
     * Written by Conor Ginnell
     *
     * OnCreate Lifecycle method.  Reads from SharedPreferences to see which
     * days of the week are set.  If the SharedPreferences doesn't exist yet,
     * set to false by default.
     *
     * Identify CheckBoxes, preset their values, and set their onClickListeners
     *
     * @param savedInstanceState Saved Instance State
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarmset);

        //Open up Preferences Editor
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();

        //Create & Set alarmSetList
        if (alarmSetList == null) {
            //Log.d("BD Debug", "AlarmSetList was null on Create.");
            alarmSetList = new ArrayList<>(7);
            alarmSetList.add(prefs.getBoolean(SUNDAY, false));
            alarmSetList.add(prefs.getBoolean(MONDAY, false));
            alarmSetList.add(prefs.getBoolean(TUESDAY, false));
            alarmSetList.add(prefs.getBoolean(WEDNESDAY, false));
            alarmSetList.add(prefs.getBoolean(THURSDAY, false));
            alarmSetList.add(prefs.getBoolean(FRIDAY, false));
            alarmSetList.add(prefs.getBoolean(SATURDAY, false));
        }
        else {
            //Log.d("BD Debug", "AlarmSetList was not null on Create.");
        }

        //Create AlarmManager and pendingIntentList
        if (alarmManager == null) {
            //Log.d("BD Debug", "AlarmManager was null on Create.");
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }
        else {
            //Log.d("BD Debug", "AlarmManager was not null on Create.");
        }

        if (pendingIntentList == null) {
            //Log.d("BD Debug", "PendingIntentList was null on Create.");
            pendingIntentList = new ArrayList<>(7);
            Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
            for (int i = 0; i < 7; i++) {
                pendingIntentList.add(PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0));
            }
        }
        else {
            //Log.d("BD Debug", "PendingIntentList was not null on Create.");
        }

        // Identify UI Elements
        sunBox = (CheckBox)findViewById(R.id.sundayCheckBox);
        monBox = (CheckBox)findViewById(R.id.mondayCheckBox);
        tueBox = (CheckBox)findViewById(R.id.tuesdayCheckBox);
        wedBox = (CheckBox)findViewById(R.id.wednesdayCheckBox);
        thuBox = (CheckBox)findViewById(R.id.thursdayCheckBox);
        friBox = (CheckBox)findViewById(R.id.fridayCheckBox);
        satBox = (CheckBox)findViewById(R.id.saturdayCheckBox);
        closeButton = (Button)findViewById(R.id.closeButton);

        // Set onClickListeners
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
                editor.putBoolean(SUNDAY, alarmSetList.get(0));
                editor.apply();
                
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
                editor.putBoolean(MONDAY, alarmSetList.get(1));
                editor.apply();
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
                editor.putBoolean(TUESDAY, alarmSetList.get(2));
                editor.apply();
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
                editor.putBoolean(WEDNESDAY, alarmSetList.get(3));
                editor.apply();
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
                editor.putBoolean(THURSDAY, alarmSetList.get(4));
                editor.apply();
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
                editor.putBoolean(FRIDAY, alarmSetList.get(5));
                editor.apply();
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
                editor.putBoolean(SATURDAY, alarmSetList.get(6));
                editor.apply();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Read Preferences and pre-set checkmarks
        presetBox(SUNDAY, sunBox);
        presetBox(MONDAY, monBox);
        presetBox(TUESDAY, tueBox);
        presetBox(WEDNESDAY, wedBox);
        presetBox(THURSDAY, thuBox);
        presetBox(FRIDAY, friBox);
        presetBox(SATURDAY, satBox);
    }

    /**
     * Written by Conor Ginnell
     *
     * setAlarm sets alarms on the day given in the parameters.
     * @param dayNum integer from 0-6 corresponding to the day of the week.
     * @param weekDay integer from 1-7 corresponding to the day of the week.
     */
    public static void setAlarm(int dayNum, int weekDay) {

        alarmSetList.set(dayNum, true);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.DAY_OF_WEEK, weekDay);
        c.set(Calendar.HOUR_OF_DAY, 12);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),
                INTERVAL_WEEK, pendingIntentList.get(dayNum));
    }

    /**
     * Written by Conor Ginnell
     *
     * unsetAlarm unsets an alarm on the day given in the parameter.
     * @param dayNum integer from 0-6 corresponding to the day of the week.
     */
    public void unsetAlarm(int dayNum) {
        alarmSetList.set(dayNum, false);
        alarmManager.cancel(pendingIntentList.get(dayNum));
    }

    /**
     * Written by Conor Ginnell
     *
     * presetBox runs in onCreate and reads from SharedPreferences to preset
     * the CheckBox views to the corresponding selected days.  This ensures
     * that the user's preferences on what days alarms are set on is maintained
     * so that the user doesn't accidentally set more than one alarm.
     * @param key name of the day, e.g. SUNDAY, MONDAY, FRIDAY
     * @param c CheckBox to be preset (or not preset)
     */
    public void presetBox(String key, CheckBox c) {
        //Log.d("PresetBox Debug", "" + key + " is " + prefs.getBoolean(key, false));
        if (prefs.getBoolean(key, false)) {
            c.setChecked(true);
        }
        else {
            c.setChecked(false);
        }
    }

    /**
     * Written by Conor Ginnell
     *
     * Debugging method that reads SharedPreferences and outputs whether or not
     * each day is set.  Had some issues checking whether or not CheckBoxes
     * were selected or not.
     */
    public void checkSharedPreferences() {
        Log.d("DBSP", "Sunday: " + prefs.getBoolean(SUNDAY, false));
        Log.d("DBSP", "Monday: " + prefs.getBoolean(MONDAY, false));
        Log.d("DBSP", "Tuesday: " + prefs.getBoolean(TUESDAY, false));
        Log.d("DBSP", "Wednesday: " + prefs.getBoolean(WEDNESDAY, false));
        Log.d("DBSP", "Thursday: " + prefs.getBoolean(THURSDAY, false));
        Log.d("DBSP", "Friday: " + prefs.getBoolean(FRIDAY, false));
        Log.d("DBSP", "Saturday: " + prefs.getBoolean(SATURDAY, false));
    }

    /**
     * Written by Conor Ginnell
     *
     * BroadcastReceiver that runs on boot.
     * Resets alarms based on SharedPreferences.
     */
    public static class BootReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
                //Log.d("BR Debug", "Entered onReceive in bootReceiver");


                Notification noti = new Notification.Builder(context)
                        .setContentTitle("Hoist")
                        .setContentText("Phone Booted!")
                        .setSmallIcon(R.drawable.hoist)
                        .build();

                int mNotificationId = 002;
                NotificationManager mNotifyMgr = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
                mNotifyMgr.notify(mNotificationId, noti);


                if (prefs == null) {
                    //Log.d("BD Debug", "Prefs was null on Boot.");
                    prefs = PreferenceManager.getDefaultSharedPreferences(context);
                }
                if (alarmSetList == null) {
                    //Log.d("BD Debug", "AlarmSetList was null on Boot.");
                    alarmSetList = new ArrayList<>(7);
                    alarmSetList.add(prefs.getBoolean(SUNDAY, false));
                    alarmSetList.add(prefs.getBoolean(MONDAY, false));
                    alarmSetList.add(prefs.getBoolean(TUESDAY, false));
                    alarmSetList.add(prefs.getBoolean(WEDNESDAY, false));
                    alarmSetList.add(prefs.getBoolean(THURSDAY, false));
                    alarmSetList.add(prefs.getBoolean(FRIDAY, false));
                    alarmSetList.add(prefs.getBoolean(SATURDAY, false));
                }
                if (alarmManager == null) {
                    //Log.d("BD Debug", "AlarmManager was null on Boot.");
                    alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                }
                if (pendingIntentList == null) {
                    //Log.d("BD Debug", "PendingIntentList was null on Boot.");
                    pendingIntentList = new ArrayList<>(7);
                    Intent arIntent = new Intent(context, AlarmReceiver.class);
                    for (int i = 0; i < 7; i++) {
                        pendingIntentList.add(PendingIntent.getBroadcast(
                                context, 0, arIntent, 0));
                    }
                }
                if(prefs.getBoolean(SUNDAY, false)) {
                    //Log.d("BD Debug", "Setting Sunday alarm in Boot.");
                    setAlarm(0, Calendar.SUNDAY);
                }
                if(prefs.getBoolean(MONDAY, false)) {
                    //Log.d("BD Debug", "Setting Monday alarm in Boot.");
                    setAlarm(1, Calendar.MONDAY);
                }
                if(prefs.getBoolean(TUESDAY, false)) {
                    //Log.d("BD Debug", "Setting Tuesday alarm in Boot.");
                    setAlarm(2, Calendar.TUESDAY);
                }
                if(prefs.getBoolean(WEDNESDAY, false)) {
                    //Log.d("BD Debug", "Setting Wednesday alarm in Boot.");
                    setAlarm(3, Calendar.WEDNESDAY);
                }
                if(prefs.getBoolean(THURSDAY, false)) {
                    //Log.d("BD Debug", "Setting Thursday alarm in Boot.");
                    setAlarm(4, Calendar.THURSDAY);
                }
                if(prefs.getBoolean(FRIDAY, false)) {
                    //Log.d("BD Debug", "Setting Friday alarm in Boot.");
                    setAlarm(5, Calendar.FRIDAY);
                }
                if(prefs.getBoolean(SATURDAY, false)) {
                    //Log.d("BD Debug", "Setting Saturday alarm in Boot.");
                    setAlarm(6, Calendar.SATURDAY);
                }
            }
        }
    }
}
