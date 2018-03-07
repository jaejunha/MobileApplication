package dreamline91.naver.com.checker.functionality.manage.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;


import java.util.ArrayList;

import dreamline91.naver.com.checker.R;

/**
 * Created by dream on 2017-11-12.
 */

public class MListAddDialog extends Dialog {

    final int WEEK = 0;
    final int MONTH = 1;

    public MListAddDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_mlistadd);
        setCanceledOnTouchOutside(false);

        setSpinnerTime(context);
        setGroupWeek();
        setSpinnerMonth(context);
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
}
