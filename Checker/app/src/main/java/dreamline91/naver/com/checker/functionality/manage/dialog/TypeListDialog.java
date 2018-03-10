package dreamline91.naver.com.checker.functionality.manage.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;

import dreamline91.naver.com.checker.R;
import dreamline91.naver.com.checker.util.db.DB;

/**
 * Created by dream on 2017-11-12.
 */

public class TypeListDialog extends Dialog {

    public TypeListDialog(@NonNull final Context context) {
        super(context);
        setContentView(R.layout.dialog_typelist);
        setCanceledOnTouchOutside(false);

        setButtonCancel(context);
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
