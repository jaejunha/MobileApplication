package com.naver.dreamline91.keeper.filter;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.RequiresApi;

import com.naver.dreamline91.keeper.service.KeepingService;

/**
 * Created by dream on 2017-09-01.
 */

public class FilterSwitch  extends Activity {

    private KeepingService keepingService;
    private ServiceConnection connection = new ServiceConnection() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            keepingService = ((KeepingService.FilterBinder) service).getService();
            keepingService.adjustFilter();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            keepingService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (keepingService == null)
            this.bindService(new Intent(this, KeepingService.class), connection, Context.BIND_AUTO_CREATE);
        finish();
    }

    @Override
    protected void onDestroy() {
        this.unbindService(connection);
        connection = null;
        keepingService = null;
        super.onDestroy();
    }
}

