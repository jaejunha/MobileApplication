package com.naver.dreamline91.keeper.color;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by dream on 2017-09-04.
 */

public class ColorLoader extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ColorDialog(this).showDialog();
    }
}
