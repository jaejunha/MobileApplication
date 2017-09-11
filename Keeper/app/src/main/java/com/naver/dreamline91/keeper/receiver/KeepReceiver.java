package com.naver.dreamline91.keeper.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.naver.dreamline91.keeper.Keeper;

/**
 * Created by dream on 2017-09-02.
 */

public class KeepReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startActivity(new Intent(context, Keeper.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}