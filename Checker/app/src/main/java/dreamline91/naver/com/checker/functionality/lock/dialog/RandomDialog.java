package dreamline91.naver.com.checker.functionality.lock.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import dreamline91.naver.com.checker.R;

/**
 * Created by dream on 2018-03-14.
 */

public class RandomDialog extends Dialog {
    private Context context;

    public RandomDialog(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_random);
        setCanceledOnTouchOutside(false);
        this.context = context;

        setButtonAdd(context);
        setButtonCancel(context);
    }

    private void setButtonAdd(final Context context) {
        Button button_add = (Button)findViewById(R.id.button_add);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,RandomAddDialog.class);
                context.startActivity(intent);
            }
        });
    }

    private void setButtonCancel(Context context) {
        Button button_cancel = (Button)findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
