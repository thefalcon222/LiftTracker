package com.example.ryan.lifttracker;

/**
 * Created by conor on 5/2/2017.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by conor on 5/2/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
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
