package dreamline91.naver.com.checker.functionality.manage.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import dreamline91.naver.com.checker.R;
import dreamline91.naver.com.checker.util.db.DB;
import dreamline91.naver.com.checker.util.dialog.TextBoxDialog;

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
        setButtonModify(context);
        setButtonCancel(context);
        setReceiver(context);
    }

    public void setListType(final Context context) {
        string_selector = "";

        final ListView list_type = (ListView) findViewById(R.id.list_type);
        DB db = new DB(context);
        array_type = new ArrayList<String>();
        for (String type : db.selectType())
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
                    ((TextView) view_child.findViewById(R.id.text_item)).setTextColor(context.getResources().getColor(R.color.darkGray));
                }
                view.setBackgroundColor(Color.rgb(color_customGray, color_customGray, color_customGray));
                ((TextView) view.findViewById(R.id.text_item)).setTextColor(context.getResources().getColor(R.color.white));
                string_selector = ((TextView) view.findViewById(R.id.text_item)).getText().toString();
            }
        });
        db.close();
    }

    public void setButtonModify(final Context context) {
        Button button_modify = (Button) findViewById(R.id.button_modify);
        button_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (string_selector.equals("") == false)
                    new TextBoxDialog(context, string_selector).show();
                else
                    Toast.makeText(context, "수정할 항목을 선택해주세요", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void setButtonDelete(final Context context) {
        Button button_delete = (Button) findViewById(R.id.button_delete);
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (string_selector.equals("") == false) {
                    DB db = new DB(context);
                    db.deleteType(string_selector);
                    array_type.remove(string_selector);
                    adapter_type.notifyDataSetChanged();
                    string_selector = "";
                    db.close();
                    if (array_type.size() == 0) {
                        Toast.makeText(context, "모든 항목이 삭제되었습니다", Toast.LENGTH_LONG).show();
                        dismiss();
                    }
                } else
                    Toast.makeText(context, "삭제할 항목을 선택해주세요", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setButtonCancel(Context context) {
        Button button_cancel = (Button) findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void setReceiver(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction("SEND_TEXT");
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int int_index = array_type.indexOf(string_selector);
                String string_change = intent.getStringExtra("text");
                array_type.set(int_index, string_change);
                DB db = new DB(context);
                db.updateType(string_selector, string_change);
                db.close();
                string_selector = string_change;
                adapter_type.notifyDataSetChanged();
            }
        };
        context.registerReceiver(receiver, filter);
    }
}
