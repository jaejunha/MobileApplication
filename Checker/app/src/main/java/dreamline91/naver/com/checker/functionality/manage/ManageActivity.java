package dreamline91.naver.com.checker.functionality.manage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;

import dreamline91.naver.com.checker.R;

/**
 * Created by dream on 2017-11-05.
 */

public class ManageActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener{

    private Context context;
    private Toast toast;
    private Spinner[] spinnerDate;
    private SpinnerAdapter spinnerAdapter;
    private Button buttonToday;
    private ImageView imageAdd, imageList, imageSetup;
    private ListView listManage;
    private ArrayList<String> list;
    private ListAdapter adapter;

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
        list = new ArrayList<String>();
        list.add("test");
        list.add("pest");
      //  adapter.notifyDataSetChanged();
        adapter = new ListAdapter(list);
    }

    public void findID() {
        spinnerDate[0] = (Spinner) findViewById(R.id.spinnerYear);
        spinnerDate[1] = (Spinner) findViewById(R.id.spinnerMonth);
        spinnerDate[2] = (Spinner) findViewById(R.id.spinnerDay);

        imageAdd = (ImageView) findViewById(R.id.imageAdd);
        imageList = (ImageView) findViewById(R.id.imageList);
        imageSetup = (ImageView) findViewById(R.id.imageSetup);

        buttonToday = (Button)findViewById(R.id.buttonToday);
        listManage = (ListView)findViewById(R.id.listManage);
    }

    public void setListener(){
        buttonToday.setOnClickListener(this);
        imageAdd.setOnTouchListener(this);
        imageList.setOnTouchListener(this);
        imageSetup.setOnTouchListener(this);
        listManage.setAdapter(adapter);
        for(int i=0;i<3;i++)
            spinnerDate[i].setOnTouchListener(this);
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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        String str="";
        switch (view.getId()){
            case R.id.spinnerYear:
                str = "스크롤에서 해당 되는 년도를 선택해주세요";
                if(toast == null)
                    toast = Toast.makeText(context,str,Toast.LENGTH_SHORT);
                else
                    toast.setText(str);
                toast.show();
                break;
            case R.id.spinnerMonth:
                str = "스크롤에서 해당 되는 달을 선택해주세요";
                if(toast == null)
                    toast = Toast.makeText(context,str,Toast.LENGTH_SHORT);
                else
                    toast.setText(str);
                toast.show();
                break;
            case R.id.spinnerDay:
                str = "스크롤에서 해당 되는 날짜를 선택해주세요";
                if(toast == null)
                    toast = Toast.makeText(context,str,Toast.LENGTH_SHORT);
                else
                    toast.setText(str);
                toast.show();
                break;
            case R.id.imageList:
                startActivity(new Intent(this, MListActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                break;
        }
        return false;
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

    public class ListAdapter extends BaseAdapter {

        ArrayList<String> object;

        public ListAdapter(ArrayList<String> object) {
            super();
            this.object = object;
        }

        @Override
        public int getCount() {
            return object.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.listview_manage, parent, false);
                holder = new ViewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.textView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String item = object.get(position);
            holder.text.setText(item);

            return convertView;
        }
    }

    public class ViewHolder {
        TextView text;
    }
}
