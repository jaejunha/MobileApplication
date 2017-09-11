package com.naver.dreamline91.keeper.startup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.naver.dreamline91.keeper.service.KeepingService;

/**
 * Created by dream on 2017-09-01.
 */

public class StartUp extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startService(new Intent(this, KeepingService.class));
        finish();
    }
}
