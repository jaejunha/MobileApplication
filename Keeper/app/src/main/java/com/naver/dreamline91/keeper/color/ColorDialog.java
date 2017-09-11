package com.naver.dreamline91.keeper.color;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.naver.dreamline91.keeper.R;
import com.rarepebble.colorpicker.ColorPickerView;

/**
 * Created by dream on 2017-09-04.
 */

public class ColorDialog extends Dialog {
    private Context context;
    private Button buttonOK, buttonNO;

    private ColorPickerView picker;

    public ColorDialog(Context context) {
        super(context);
        this.context= context;
        setContentView(R.layout.color);
        setOwnerActivity((Activity)context);
        setCanceledOnTouchOutside(true);
        getWindow().setBackgroundDrawableResource(R.color.white);

        picker = (ColorPickerView)findViewById(R.id.colorPicker);

        SharedPreferences preferences = context.getSharedPreferences("dreamline91", Activity.MODE_PRIVATE);
        String color = preferences.getString("keeper_color","#00ff00").substring(1);
        picker.setColor(Integer.parseInt(color,16));
        preferences = null;

        findID();
        setListener();
    }

    public void findID(){
        buttonOK = (Button)findViewById(R.id.buttonOK);
        buttonNO = (Button)findViewById(R.id.buttonNO);
    }
    public void setListener(){
        buttonOK.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                String color = Integer.toHexString(picker.getColor());
                SharedPreferences preferences = context.getSharedPreferences("dreamline91", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("keeper_color","#"+color.substring(2));
                editor.putFloat("keeper_alpha",Integer.parseInt(color.substring(0,2),16)/255f);
                editor.commit();
                editor = null;
                preferences = null;
                ((ColorLoader)context).finish();
                dismiss();
                return false;
            }
        });

        buttonNO.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ((ColorLoader)context).finish();
                dismiss();
                return false;
            }
        });
    }


    public void showDialog() {
        show();
    }
}
