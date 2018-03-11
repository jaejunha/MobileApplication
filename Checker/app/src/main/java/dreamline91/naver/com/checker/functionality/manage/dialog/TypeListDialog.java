package dreamline91.naver.com.checker.functionality.manage.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;

import dreamline91.naver.com.checker.R;
import dreamline91.naver.com.checker.util.db.DB;

/**
 * Created by dream on 2017-11-12.
 */

public class TypeListDialog extends Dialog {

    private final static int color_customGray = 66;
    private int int_selector;
    private Context listType;

    public TypeListDialog(@NonNull final Context context) {
        super(context);
        setContentView(R.layout.dialog_typelist);
        setCanceledOnTouchOutside(false);

        setListType(context);
        setButtonCancel(context);
    }
    public void setListType(Context context) {
        int_selector = -1;
        ListView list_type = (ListView) findViewById(R.id.list_type);
        DB db = new DB(context);
        list_type.setAdapter(new ArrayAdapter<String>(context, R.layout.list_text, db.selectType()));
        list_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                for (int j = 0, int_count = adapterView.getCount(); j < int_count; j++)
                    adapterView.getChildAt(j).setBackgroundColor(Color.WHITE);
                view.setBackgroundColor(Color.rgb(color_customGray, color_customGray, color_customGray));
                int_selector = i;
            }
        });
        db.close();
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
