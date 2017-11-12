package dreamline91.naver.com.checker.manage.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.SeekBar;
import android.widget.TextView;


import dreamline91.naver.com.checker.R;

/**
 * Created by dream on 2017-11-12.
 */

public class MListAddDialog extends Dialog implements SeekBar.OnSeekBarChangeListener {
    private TextView textFactor;
    private SeekBar seekBar;

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

        seekBar.setProgress(100);
    }

    private void setListener() {
        seekBar.setOnSeekBarChangeListener(this);
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
}
