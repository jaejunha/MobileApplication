package dreamline91.naver.com.checker.manage;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.Calendar;

import dreamline91.naver.com.checker.R;

/**
 * Created by dream on 2017-11-05.
 */

public class Manage extends AppCompatActivity implements View.OnClickListener{

    private Context context;

    private Spinner[] spinnerDate;
    private SpinnerAdapter spinnerAdapter;
    private Button buttonToday;
    private ListView listManage;

    private final int SIZE_EACH_DROP = 100;
    private final int SIZE_MAX_DROP = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        makeObject();
        findID();
        setListener();
        initSpinner();
    }

    public void makeObject(){
        context = this;

        spinnerDate = new Spinner[3];
    }

    public void findID() {
        spinnerDate[0] = (Spinner) findViewById(R.id.spinnerYear);
        spinnerDate[1] = (Spinner) findViewById(R.id.spinnerMonth);
        spinnerDate[2] = (Spinner) findViewById(R.id.spinnerDay);

        buttonToday = (Button)findViewById(R.id.buttonToday);
        listManage = (ListView)findViewById(R.id.listManage);
    }

    public void setListener(){
        buttonToday.setOnClickListener(this);
    }

    public void initSpinner() {

        Calendar cal = Calendar.getInstance();
        String[] stringList = new String[cal.get(Calendar.YEAR) - 2015];
        for (int i = 0; i < stringList.length; i++) stringList[i] = (i + 2016) + "년";
        spinnerAdapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item, stringList);
        spinnerDate[0].setAdapter(spinnerAdapter);
        spinnerDate[0].setSelection(cal.get(Calendar.YEAR) - 2016);

        stringList = new String[12];
        for (int i = 0; i < 12; i++) stringList[i] = (i + 1) + "월";
        spinnerAdapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item, stringList);
        spinnerDate[1].setAdapter(spinnerAdapter);
        spinnerDate[1].setSelection(cal.get(Calendar.MONTH));
        final int todayDate = cal.get(Calendar.DATE);

        spinnerDate[0].setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        spinnerDate[2].setSelection(0);
                        //updateListObject();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        spinnerDate[1].setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(spinnerDate[0].getSelectedItemPosition() + 2016, spinnerDate[1].getSelectedItemPosition(), 1);
                        String stringList[] = new String[cal.getActualMaximum(Calendar.DAY_OF_MONTH)];
                        for (int i = 0; i < stringList.length; i++) stringList[i] = (i + 1) + "일";
                        spinnerAdapter = new SpinnerAdapter(context, android.R.layout.simple_spinner_item, stringList);
                        spinnerDate[2].setAdapter(spinnerAdapter);
                        if (stringList.length >= todayDate)
                            spinnerDate[2].setSelection(todayDate - 1);
                        //updateListObject();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        spinnerDate[2].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //updateListObject();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /* reference : https://stackoverflow.com/questions/20597584/how-to-limit-the-height-of-spinner-drop-down-view-in-android */
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            android.widget.ListPopupWindow popupWindow[];
            popupWindow = new android.widget.ListPopupWindow[3];
            for(int i=0;i<3;i++) {
                popupWindow[i] = (android.widget.ListPopupWindow) popup.get(spinnerDate[i]);
                popupWindow[i].setHeight(SIZE_MAX_DROP);
            }
        }
        catch (Exception e) {
        }
        /* reference : https://stackoverflow.com/questions/20597584/how-to-limit-the-height-of-spinner-drop-down-view-in-android */
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonToday:
                initSpinner();
                break;
        }
    }

    public class SpinnerAdapter extends ArrayAdapter<String> {

        public SpinnerAdapter(Context context, int resource, String string[]) {
            super(context, resource, string);
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            View v = super.getView(position, convertView, parent);
            ((TextView) v).setGravity(Gravity.RIGHT);
            ((TextView) v).setTextColor(getResources().getColor(R.color.darkGray));

            return v;

        }

        public View getDropDownView(int position, View convertView, ViewGroup parent) {

            View v = super.getDropDownView(position, convertView, parent);
            v.setBackgroundResource(R.color.white);
            v.setMinimumHeight(SIZE_EACH_DROP);
            ((TextView) v).setGravity(Gravity.RIGHT);
            ((TextView) v).setTextColor(getResources().getColor(R.color.darkGray));

            return v;
        }
    }
}
