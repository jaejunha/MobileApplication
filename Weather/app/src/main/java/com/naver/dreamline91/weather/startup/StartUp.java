package com.naver.dreamline91.weather.startup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.naver.dreamline91.weather.service.WeatherService;

/**
 * Created by dream on 2017-09-19.
 */

public class StartUp extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startService(new Intent(this, WeatherService.class));
        finish();
    }
}
