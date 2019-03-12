package com.example.sumedhsp2.esep;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Service Stops", "Ohhhh");

        context.startService(new Intent(context, MainActivity.class));

    }
}
