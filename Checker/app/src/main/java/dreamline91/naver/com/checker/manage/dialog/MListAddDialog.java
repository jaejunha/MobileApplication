package dreamline91.naver.com.checker.manage.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import dreamline91.naver.com.checker.R;

/**
 * Created by dream on 2017-11-12.
 */

public class MListAddDialog extends Dialog implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    private TextView textFactor;
    private SeekBar seekBar;
    private Button buttonUp;
    private Button buttonDown;

    public MListAddDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_mlistadd);
        setCanceledOnTouchOutside(false);

        makeObject();
        findID();
        setListener();
    }

    private void makeObject() {

    }

    private void findID() {
        textFactor = (TextView)findViewById(R.id.textFactor);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        buttonUp = (Button)findViewById(R.id.buttonUp);
        buttonDown = (Button)findViewById(R.id.buttonDown);

        seekBar.setProgress(100);
    }

    private void setListener() {
        seekBar.setOnSeekBarChangeListener(this);
        buttonDown.setOnClickListener(this);
        buttonUp.setOnClickListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        textFactor.setText("factor : "+String.format("%02d%%",i));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonUp:
                seekBar.setProgress(seekBar.getProgress()+1>100?100:seekBar.getProgress()+1);
                break;
            case R.id.buttonDown:
                seekBar.setProgress(seekBar.getProgress()-1<0?0:seekBar.getProgress()-1);
                break;
        }
    }
}
