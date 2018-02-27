package dreamline91.naver.com.checker.util.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import dreamline91.naver.com.checker.util.receiver.LockReceiver;

/**
 * Created by dream on 2018-02-28.
 */

public class LockService extends Service {

    private LockReceiver lockReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override

    public void onCreate() {
        super.onCreate();
        lockReceiver = new LockReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(lockReceiver, filter);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        if(lockReceiver != null)
            unregisterReceiver(lockReceiver);
    }
}
