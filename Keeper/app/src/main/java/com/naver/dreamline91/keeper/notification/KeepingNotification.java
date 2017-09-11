package com.naver.dreamline91.keeper.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.widget.RemoteViews;

import com.naver.dreamline91.keeper.R;
import com.naver.dreamline91.keeper.color.ColorLoader;
import com.naver.dreamline91.keeper.filter.FilterSwitch;

/**
 * Created by dream on 2017-09-01.
 */

public class KeepingNotification {
    private Context context;
    final int FILTER = 1;
    final int COLOR = 2;

    public KeepingNotification(Context context){
        this.context = context;
    }

    public Notification show() {
        String str = "";
        Notification.Builder builder = new Notification.Builder(context);
        builder.setTicker("Keeper가 실행 중입니다");
        builder.setSmallIcon(R.mipmap.ic_launcher);

        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.notification);
        SharedPreferences preferences = context.getSharedPreferences("dreamline91", Activity.MODE_PRIVATE);
        if (preferences.getBoolean("keeper_filter", false) == false) {
            remoteView.setViewVisibility(R.id.viewFilter, View.GONE);
            str += "그린필터 OFF (투명도 : 0%)";
        } else {
            int width =  (int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 22, Resources.getSystem().getDisplayMetrics()));
            int height =  (int)(TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 27, Resources.getSystem().getDisplayMetrics()));
            Bitmap bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_4444);
            for (int x = 0; x < width; x++)
                for (int y = 0; y < height; y++)
                    bitmap.setPixel(x, y, Color.parseColor(preferences.getString("keeper_color","#00ff00")));
            remoteView.setImageViewBitmap(R.id.viewFilter, bitmap);
            remoteView.setViewVisibility(R.id.viewFilter, View.VISIBLE);
            str += "그린필터 ON";
            str +=" (투명도 : "+String.format("%d",(int)(preferences.getFloat("keeper_alpha",.5f)*100))+"%)";
        }
        remoteView.setTextViewText(R.id.text, str);

        PendingIntent pendingFilter = PendingIntent.getActivity(context, FILTER, new Intent(context, FilterSwitch.class), 0);
        PendingIntent pendingColor = PendingIntent.getActivity(context, COLOR, new Intent(context, ColorLoader.class), 0);
        remoteView.setOnClickPendingIntent(R.id.layoutFilter, pendingFilter);
        remoteView.setOnClickPendingIntent(R.id.layoutTool, pendingColor);

        Notification notification = builder.build();
        notification.contentView = remoteView;
        preferences = null;
        return notification;
    }
}
