package com.example.sumedhsp2.esep;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class NotificationService extends Service {
    String username;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void onCreate(){
        super.onCreate();
        notify1();
    }
    public void notify1(){
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction("RSSPullService");
        PendingIntent pendingIntent;
        Intent myintent = new Intent(getApplicationContext(), MainActivity.class);
        // myintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //myintent.putExtra("UserMainActivity", table);
        //myintent.putExtra("USER_NAME", username);
        //myintent.putExtra("username_main_activity", username);
        myintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, myintent, PendingIntent.FLAG_UPDATE_CURRENT);

        Context context = getApplicationContext();

        Notification.Builder builder;
        builder = new Notification.Builder(context)
                .setContentTitle("ALERT!!")
                .setContentText("Peak Time Reached! Click to switch grids")
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.logo_launcher_adapter_legacy);

        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }
    public void onDestroy(){
        super.onDestroy();
    }
}
