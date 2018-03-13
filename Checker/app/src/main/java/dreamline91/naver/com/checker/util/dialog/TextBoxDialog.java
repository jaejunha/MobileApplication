package dreamline91.naver.com.checker.util.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import dreamline91.naver.com.checker.R;

/**
 * Created by dream on 2018-03-13.
 */

public class TextBoxDialog extends Dialog {
    private EditText edit_content;

    public TextBoxDialog(@NonNull Context context, String content) {
        super(context);
        setContentView(R.layout.dialog_textbox);
        setCanceledOnTouchOutside(false);

        setEditContent(content);
        setButtonSave(context);
        setButtonCancel(context);

    }

    private void setEditContent(String content) {
        edit_content = (EditText)findViewById(R.id.edit_content);
        edit_content.setText(content);
    }

    public void setButtonSave(final Context context) {
        Button button_save = (Button) findViewById(R.id.button_save);
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.sendBroadcast(new Intent("SEND_TEXT").putExtra("text", edit_content.getText().toString()));
                dismiss();
            }
        });
    }

    public void setButtonCancel(final Context context) {
        Button button_cancel = (Button) findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
