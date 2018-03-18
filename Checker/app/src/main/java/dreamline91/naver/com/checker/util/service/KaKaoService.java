package dreamline91.naver.com.checker.util.service;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.Calendar;

import dreamline91.naver.com.checker.util.db.DB;

/**
 * Created by dream on 2018-03-18.
 */

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class KaKaoService extends NotificationListenerService{

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if(sbn.getPackageName().equals("com.kakao.talk")) {

            Notification notification = sbn.getNotification();
            Bundle bundle_extra = notification.extras;

            DB db = new DB(getApplicationContext());
            db.insertKakao(sbn.getPostTime(),bundle_extra.getString(Notification.EXTRA_TITLE),bundle_extra.getString(Notification.EXTRA_TEXT));
            db.close();
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        // Nothing to do
    }

}
