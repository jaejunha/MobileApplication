package com.naver.dreamline91.keeper;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by dream on 2017-09-02.
 */

public class Keeper extends Activity {

    private ScreenReceiver screenReceiver;
    private BatteryReceiver batteryReceiver;

    private TextView textDate, textRandom, textBattery, textUsage;
    private ProgressBar progressBattery;

    private int year, month, day, today, total, now, update;
    /*나중에 커스텀으로 수정*/
    String random[] = {"dreamline91@naver.com로 연락주시면 사례하겠습니다"};
    /*나중에 커스텀으로 수정*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keeper);

           /*전체 화면*/
        /* reference : http://blog.naver.com/benghun/40186080539 */
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY);
        /* reference : http://blog.naver.com/benghun/40186080539 */

        findID();

        screenReceiver = new ScreenReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(screenReceiver, filter);

        Calendar cal = Calendar.getInstance();
        String week[] = {"월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일"};
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
        day = cal.get(Calendar.DATE);
        now = year*10000+month*100+day;
        textDate.setText(String.format("%4d.%d.%d.%s", year, month, day, week[(cal.get(Calendar.DAY_OF_WEEK) + 5) % 7]));

        textRandom.setText(random[(int) (Math.random() * random.length)]);

        SharedPreferences preferences = getSharedPreferences("dreamline91", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        today = preferences.getInt("keeper_today", 0);
        total = preferences.getInt("keeper_total", 0);
        update = preferences.getInt("keeper_update", 0);
        if(update == 0)
            update = now;
        else{
            if(now == update){
                if(preferences.getBoolean("keeper_use",false) == true) {
                    today += cal.get(Calendar.HOUR_OF_DAY) * 3600 + cal.get(Calendar.MINUTE) * 60 + cal.get(Calendar.SECOND);
                    today -= preferences.getInt("keeper_hour", 0) * 3600 + preferences.getInt("keeper_min", 0) * 60 + preferences.getInt("keeper_sec", 0);
                    editor.putInt("keeper_today", today);
                    editor.putBoolean("keeper_use",false);
                }
            }else{
                if(preferences.getBoolean("keeper_use",false) == true) {
                    today += 24 * 3600 - preferences.getInt("keeper_hour", 0) * 3600 + preferences.getInt("keeper_min", 0) * 60 + preferences.getInt("keeper_sec", 0);
                    if (total != 0)
                        total = (int) (today * 0.2f + total * 0.8f);
                    else
                        total = today;
                /* 밑의 today 위치 변경하면 안됨 */
                    today = cal.get(Calendar.HOUR)*3600+cal.get(Calendar.MINUTE)*60+cal.get(Calendar.SECOND);
                    editor.putInt("keeper_total", total);
                    editor.putInt("keeper_today", today);
                    editor.putBoolean("keeper_use",false);
                }
            }
            update = now;
        }
        editor.putInt("keeper_update",update);
        editor.commit();

        textUsage.setText("핸드폰 "+(today/60)/60+"시간 "+(today/60)%60+"분 사용 (가중치 평균 : "+(total/60)/60+"시간 "+(total/60)%60+"분)");

        editor = null;
        preferences = null;
        cal = null;
    }

    public void findID() {
        textDate = (TextView) findViewById(R.id.textDate);
        textRandom = (TextView) findViewById(R.id.textRandom);
        textBattery = (TextView) findViewById(R.id.textBattery);
        textUsage = (TextView) findViewById(R.id.textUsage);

        progressBattery = (ProgressBar) findViewById(R.id.progressBattery);
    }

    public class ScreenReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == Intent.ACTION_SCREEN_ON) {
                batteryReceiver = new BatteryReceiver();
                registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            } else {
                textRandom.setText(random[(int) (Math.random() * random.length)]);
                if (batteryReceiver != null) {
                    unregisterReceiver(batteryReceiver);
                    batteryReceiver = null;
                }
            }
        }
    }

    public class BatteryReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            progressBattery.setRotation((-intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) / 100f * 360f) - 90f);
            progressBattery.setProgress(intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1));
            if (intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0) > 0)
                textBattery.setText("충전중\n" + intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) + "%");
            else
                textBattery.setText(intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) + "%");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(screenReceiver);
        if (batteryReceiver != null)
            unregisterReceiver(batteryReceiver);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        using();
        finish();
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        using();
        finish();
    }

    public void using(){
        Calendar cal = Calendar.getInstance();
        SharedPreferences preferences = getSharedPreferences("dreamline91", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        int update = preferences.getInt("keeper_update",0);
        if((now != update) && now != 0) {
            today += 24 * 3600 - preferences.getInt("keeper_hour", 0) * 3600 + preferences.getInt("keeper_min", 0) * 60 + preferences.getInt("keeper_sec", 0);
            if (total != 0)
                total = (int) (today * 0.2f + total * 0.8f);
            else
                total = today;
                /* 밑의 today 위치 변경하면 안됨 */
            today = cal.get(Calendar.HOUR)*3600+cal.get(Calendar.MINUTE)*60+cal.get(Calendar.SECOND);
            editor.putInt("keeper_total", total);
            editor.putInt("keeper_today", today);
        }
        editor.putInt("keeper_update",update);
        editor.putInt("keeper_hour",cal.get(Calendar.HOUR_OF_DAY));
        editor.putInt("keeper_min",cal.get(Calendar.MINUTE));
        editor.putInt("keeper_sec",cal.get(Calendar.SECOND));
        editor.putBoolean("keeper_use",true);
        editor.commit();
        editor = null;
        preferences = null;
        cal = null;
    }
}

