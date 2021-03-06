package com.example.solarise.models;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CoffeeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        CoffeeHelper notificationHelper = new CoffeeHelper(context);
        notificationHelper.createNotification();

    }
}