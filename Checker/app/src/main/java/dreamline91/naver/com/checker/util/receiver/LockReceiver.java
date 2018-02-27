package dreamline91.naver.com.checker.util.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import dreamline91.naver.com.checker.functionality.lock.LockActivity;

/**
 * Created by dream on 2018-02-27.
 */

public class LockReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.intent.action.SCREEN_OFF"))
            context.startActivity(new Intent(context,LockActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
