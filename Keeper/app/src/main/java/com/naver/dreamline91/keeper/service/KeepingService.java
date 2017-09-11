package com.naver.dreamline91.keeper.service;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.naver.dreamline91.keeper.notification.KeepingNotification;
import com.naver.dreamline91.keeper.receiver.KeepReceiver;

/**
 * Created by dream on 2017-09-01.
 */

public class KeepingService extends Service {
    private Context context;
    private View filter;
    private IBinder binder;
    private BroadcastReceiver lockReceiver;

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        /*필터*/
        filter = new View(context);
        makeFilter(context);
        SharedPreferences preferences = context.getSharedPreferences("dreamline91", Activity.MODE_PRIVATE);
        if (preferences.getBoolean("main_filter", false) == false)
            filter.setVisibility(View.GONE);
        binder = new FilterBinder();
        /*필터*/

        /*잠금 화면*/
        lockReceiver = new KeepReceiver();
        context.registerReceiver(lockReceiver,new IntentFilter(Intent.ACTION_SCREEN_OFF));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        context.unregisterReceiver(lockReceiver);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(1, new KeepingNotification(context).show());
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }

    public class FilterBinder extends Binder {
        public KeepingService getService() {
            return KeepingService.this;
        }
    }

    @Override
    public void onRebind(Intent intent) {
        // TODO: Return the communication channel to the service.
        super.onRebind(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void adjustFilter() {
        SharedPreferences preferences = context.getSharedPreferences("dreamline91", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (preferences.getBoolean("keeper_filter", false) == false) {
            editor.putBoolean("keeper_filter", true);
            filter.setBackgroundColor(Color.parseColor(preferences.getString("keeper_color","#00ff00")));
            filter.setAlpha(preferences.getFloat("keeper_alpha",.5f));
            filter.setVisibility(View.VISIBLE);
        } else {
            editor.putBoolean("keeper_filter", false);
            filter.setVisibility(View.GONE);
        }
        editor.commit();
        KeepingNotification keepingNotification = new KeepingNotification(context);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, keepingNotification.show());
        keepingNotification = null;
        editor = null;
        preferences = null;
    }

    public void makeFilter(Context context) {
         /* reference : http://www.androidside.com/bbs/board.php?bo_table=b49&wr_id=137999 */
        android.view.WindowManager.LayoutParams layoutParams = new android.view.WindowManager.LayoutParams();
        layoutParams.flags =
                android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        | android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | android.view.WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

        layoutParams.format = PixelFormat.TRANSLUCENT;
        layoutParams.windowAnimations = android.R.style.Animation_Toast;
        layoutParams.type = android.view.WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.x = 0;
        layoutParams.y = 0;
        layoutParams.verticalWeight = 1.0F;
        layoutParams.horizontalWeight = 1.0F;
        layoutParams.verticalMargin = 0.0F;
        layoutParams.horizontalMargin = 0.0F;

        WindowManager localWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        localWindowManager.addView(filter, layoutParams);
        SharedPreferences preferences = context.getSharedPreferences("dreamline91", Activity.MODE_PRIVATE);
        //색 설정에 따라
        filter.setBackgroundColor(Color.parseColor(preferences.getString("keeper_color","#00ff00")));
        //투명도 설정에 따라
        filter.setAlpha(preferences.getFloat("keeper_alpha",.5f));
        preferences = null;
        /* reference : http://www.androidside.com/bbs/board.php?bo_table=b49&wr_id=137999 */
    }
}