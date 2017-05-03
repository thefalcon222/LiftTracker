package com.example.ryan.lifttracker.constants;

import android.app.AlarmManager;

/**
 * Written by Conor Ginnell
 *
 * Contains constants used throughout the app, especially strings
 * representing the days of the week.
 */

public class Constants {

    public static final String SUNDAY = "Sunday";
    public static final String MONDAY = "Monday";
    public static final String TUESDAY = "Tuesday";
    public static final String WEDNESDAY = "Wednesday";
    public static final String THURSDAY = "Thursday";
    public static final String FRIDAY = "Friday";
    public static final String SATURDAY = "Saturday";
    public static final long INTERVAL_WEEK = 7 * AlarmManager.INTERVAL_DAY;

    public static final String email_from = "id_rather_be_lifting@yahoo.com";
    public static final String email_to = "mobile_was_a_mistake@yahoo.com";
    public static final String email_subject = "Hoist Workout Set";
}
