package com.naver.dreamline91.weather.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import com.naver.dreamline91.weather.notification.WeatherNotification;

/**
 * Created by dream on 2017-09-19.
 */

public class WeatherService  extends Service {

    private Context context;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(1, new WeatherNotification(context).show());
        return START_STICKY;
    }
}
