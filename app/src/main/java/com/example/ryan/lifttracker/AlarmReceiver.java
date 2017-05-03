package com.example.ryan.lifttracker;

/**
 * Created by Conor Ginnell
 *
 * Receives an Alarm set by the AlarmManager in AlarmSetActivity.java
 *
 * Should go off at 12:00 P.M. on days specified by the user.  When it goes
 * off, send a push notification to the user reminding them to either
 * (a) work out
 * (b) track their work-out
 * (c) both (a) and (b)
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {

    /**
     * Written by Conor Ginnell
     *
     * BroadcastReceiver onReceive LifeCycle Method.
     * Builds and fires push notification.
     * @param context Context of Received Alarm
     * @param intent Intent of Received Alarm
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BC Debug", "AlarmReceiver received message");
        Notification noti = new Notification.Builder(context)
                .setContentTitle("Hoist")
                .setContentText("It's Hoist! Did you work out today?")
                .setSmallIcon(R.drawable.hoist)
                .build();

        int mNotificationId = 001;
        NotificationManager mNotifyMgr = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, noti);
    }
}
