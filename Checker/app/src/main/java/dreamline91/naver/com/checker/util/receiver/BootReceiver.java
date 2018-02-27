package dreamline91.naver.com.checker.util.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import dreamline91.naver.com.checker.play.PlayActivity;

/**
 * Created by dream on 2018-02-26.
 */

public class BootReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
            context.startActivity(new Intent(context,PlayActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
