package com.naver.dreamline91.weather.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.RemoteViews;

import com.naver.dreamline91.weather.R;

/**
 * Created by dream on 2017-09-19.
 */

public class WeatherNotification {
    private Context context;

    public WeatherNotification(Context context){
        this.context = context;
    }

    public Notification show() {
        String str = "";
        Notification.Builder builder = new Notification.Builder(context);
        builder.setTicker("Weather가 실행 중입니다");
        builder.setSmallIcon(R.mipmap.ic_launcher);

        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.notification);
/*        SharedPreferences preferences = context.getSharedPreferences("dreamline91", Activity.MODE_PRIVATE);
        if (preferences.getBoolean("keeper_filter", false) == false) {
            remoteView.setViewVisibility(R.id.viewFilter, View.GONE);
            str += "그린필터 OFF (투명도 : 0%)";
        } else {
            remoteView.setViewVisibility(R.id.viewFilter, View.VISIBLE);
            str += "그린필터 ON";
            str +=" (투명도 : "+String.format("%d",(int)(preferences.getFloat("keeper_alpha",.5f)*100))+"%)";
        }
        remoteView.setTextViewText(R.id.text, str);

        PendingIntent pendingFilter = PendingIntent.getActivity(context, FILTER, new Intent(context, FilterSwitch.class), 0);
        PendingIntent pendingColor = PendingIntent.getActivity(context, COLOR, new Intent(context, ColorLoader.class), 0);
        remoteView.setOnClickPendingIntent(R.id.layoutFilter, pendingFilter);
        remoteView.setOnClickPendingIntent(R.id.layoutTool, pendingColor);
*/
        Notification notification = builder.build();
        notification.contentView = remoteView;
 //       preferences = null;
        return notification;
    }
}
