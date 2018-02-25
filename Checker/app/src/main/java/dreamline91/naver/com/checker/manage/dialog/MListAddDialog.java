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

public class MListAddDialog extends Dialog implements View.OnClickListener {

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

    }

    private void setListener() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
        }
    }
}
