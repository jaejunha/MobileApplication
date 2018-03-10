package dreamline91.naver.com.checker.functionality.manage.dialog;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.RadioGroup;
import android.widget.Spinner;


import java.util.ArrayList;

import dreamline91.naver.com.checker.R;
import dreamline91.naver.com.checker.util.db.DB;

/**
 * Created by dream on 2017-11-12.
 */

public class MListAddDialog extends Dialog {

    final int WEEK = 0;
    private Context editType;

    public MListAddDialog(@NonNull final Context context) {
        super(context);
        setContentView(R.layout.dialog_mlistadd);
        setCanceledOnTouchOutside(false);

        setBUttonList(context);
        setEditType(context);
        setSpinnerTime(context);
        setGroupWeek();
        setSpinnerMonth(context);
        setButtonSave(context);
        setButtonCancel();
    }

    private void setBUttonList(final Context context) {
        Button button_list = (Button)findViewById(R.id.button_list);
        button_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TypeListDialog(context).show();
            }
        });
    }

    public void setEditType(final Context context) {
        final AutoCompleteTextView edit_type = (AutoCompleteTextView) findViewById(R.id.edit_type);
        DB db = new DB(context);
        edit_type.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, db.selectType()));
        edit_type.setDropDownBackgroundResource(R.color.gray);
        edit_type.post(new Runnable(){
            @Override
            public void run() {
                int int_buttonSize = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());
                ViewGroup.LayoutParams params = edit_type.getLayoutParams();
                params.width = edit_type.getWidth()-int_buttonSize;
                edit_type.setLayoutParams(params);
            }
        });
        db.close();
    }

    private void setSpinnerTime(Context context) {

        final LinearLayout layout_week, layout_month;
        layout_week = (LinearLayout)findViewById(R.id.layout_week);
        layout_month = (LinearLayout)findViewById(R.id.layout_month);

        Spinner spinner_time = (Spinner)findViewById(R.id.spinner_time);
        ArrayList<String> array_time = new ArrayList<>();
        array_time.add("매주");
        array_time.add("매월");
        ArrayAdapter adapter_time = new ArrayAdapter(context,R.layout.spinner_text,array_time);
        adapter_time.setDropDownViewResource(R.layout.spinner_down);
        spinner_time.setAdapter(adapter_time);
        spinner_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==WEEK) {
                    layout_week.setVisibility(View.VISIBLE);
                    layout_month.setVisibility(View.GONE);
                }else{
                    layout_week.setVisibility(View.GONE);
                    layout_month.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setGroupWeek() {
        final LinearLayout layout_secondWeek = (LinearLayout)findViewById(R.id.layout_secondWeek);
        RadioGroup group_week = (RadioGroup)findViewById(R.id.group_week);
        group_week.check(R.id.radio_oneWeek);
        group_week.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(radioGroup.getCheckedRadioButtonId() == R.id.radio_oneWeek)
                    layout_secondWeek.setVisibility(View.GONE);
                else
                    layout_secondWeek.setVisibility(View.VISIBLE);
            }
        });
    }

    public void setSpinnerMonth(Context context) {
        Spinner spinner_month = (Spinner) findViewById(R.id.spinner_month);
        ArrayList<String> array_month = new ArrayList<>();
        for (int i = 1; i <= 12; i++)
            array_month.add(i + "월");
        ArrayAdapter adapter_month = new ArrayAdapter(context, R.layout.spinner_text, array_month);
        adapter_month.setDropDownViewResource(R.layout.spinner_down);
        spinner_month.setAdapter(adapter_month);
    }

    public void setButtonSave(final Context context){
        Button button_save = (Button)findViewById(R.id.button_save);
        button_save.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                DB db = new DB(context);
                AutoCompleteTextView edit_type = (AutoCompleteTextView)findViewById(R.id.edit_type);
                db.insertType("type",edit_type.getText().toString());
                db.close();
                dismiss();
            }
        });
    }

    public void setButtonCancel(){
        Button button_cancel = (Button)findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
