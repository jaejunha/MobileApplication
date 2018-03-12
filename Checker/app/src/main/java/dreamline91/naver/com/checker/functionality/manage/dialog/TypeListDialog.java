package dreamline91.naver.com.checker.functionality.manage.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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
import android.widget.TextView;

import java.util.ArrayList;

import dreamline91.naver.com.checker.R;
import dreamline91.naver.com.checker.util.db.DB;

/**
 * Created by dream on 2017-11-12.
 */

public class TypeListDialog extends Dialog {

    private ArrayAdapter<String> adapter_type;
    private ArrayList<String> array_type;
    private final static int color_customGray = 66;
    private String string_selector;

    public TypeListDialog(@NonNull final Context context) {
        super(context);
        setContentView(R.layout.dialog_typelist);
        setCanceledOnTouchOutside(false);

        setListType(context);
        setButtonDelete(context);
        setButtonCancel(context);
    }
    public void setListType(final Context context) {
        final ListView list_type = (ListView) findViewById(R.id.list_type);
        DB db = new DB(context);
        array_type = new ArrayList<String>();
        for(String type : db.selectType())
            array_type.add(type);
        adapter_type = new ArrayAdapter<String>(context, R.layout.list_text, array_type);
        list_type.setAdapter(adapter_type);
        list_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                View view_child;
                for (int j = 0, int_count = adapterView.getCount(); j < int_count; j++) {
                    view_child = adapterView.getChildAt(j);
                    view_child.setBackgroundColor(Color.WHITE);
                    ((TextView)view_child.findViewById(R.id.text_item)).setTextColor(context.getResources().getColor(R.color.darkGray));
                }
                view.setBackgroundColor(Color.rgb(color_customGray, color_customGray, color_customGray));
                ((TextView)view.findViewById(R.id.text_item)).setTextColor(context.getResources().getColor(R.color.white));
                string_selector = ((TextView)view.findViewById(R.id.text_item)).getText().toString();
            }
        });
        db.close();
    }
    private void setButtonDelete(final Context context){
        Button button_delete = (Button)findViewById(R.id.button_delete);
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DB db = new DB(context);
                db.deleteType(string_selector);
                array_type.remove(string_selector);
                adapter_type.notifyDataSetChanged();
                string_selector = "";
                db.close();
                if(array_type.size() == 0)
                    dismiss();
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
