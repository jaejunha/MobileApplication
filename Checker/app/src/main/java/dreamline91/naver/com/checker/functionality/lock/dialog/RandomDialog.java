package dreamline91.naver.com.checker.functionality.lock.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import dreamline91.naver.com.checker.R;
import dreamline91.naver.com.checker.util.db.DB;
import dreamline91.naver.com.checker.util.object.RandomText;

/**
 * Created by dream on 2018-03-14.
 */

public class RandomDialog extends Dialog {

    private Context context;
    private BroadcastReceiver receiver;

    private ArrayList<String> array_random;
    private ListView list_random;
    private ArrayAdapter<String> adapter_random;
    private String string_selector;
    private final static int color_customGray = 66;

    public RandomDialog(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_random);
        setCanceledOnTouchOutside(false);
        this.context = context;
        
        setListRandom();
        setButtonAdd();
        setButtonModify();
        setButtonDelete();
        setButtonCancel();
        setReceiver();
        setDismiss();
    }

    private void setListRandom() {
        string_selector = "";

        list_random = (ListView)findViewById(R.id.list_random);
        array_random = new ArrayList<>();
        initArray();
        adapter_random = new ArrayAdapter<String>(context, R.layout.list_text, array_random);
        list_random.setAdapter(adapter_random);
        list_random.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                View view_child;
                try {
                    for (int j = 0, int_count = adapterView.getCount(); j < int_count; j++) {
                        view_child = adapterView.getChildAt(j);
                        view_child.setBackgroundColor(Color.WHITE);
                        ((TextView) view_child.findViewById(R.id.text_item)).setTextColor(context.getResources().getColor(R.color.darkGray));
                    }
                }catch (Exception e){
                    //do nothing
                }
                view.setBackgroundColor(Color.rgb(color_customGray, color_customGray, color_customGray));
                ((TextView)view.findViewById(R.id.text_item)).setTextColor(context.getResources().getColor(R.color.white));
                string_selector = ((TextView)view.findViewById(R.id.text_item)).getText().toString();         ;
                string_selector = getIndex(string_selector);
            }
        });
    }

    private void initArray(){
        array_random.clear();
        DB db = new DB(context);
        StringBuffer string_temp = new StringBuffer();
        for(RandomText random : db.selectRandom()){
            string_temp.delete(0,string_temp.length());
            if(random.getImage().equals(""))
                string_temp.append(" X     ");
            else
                string_temp.append(" O     ");

            if(random.getLink().equals(""))
                string_temp.append(" X     ");
            else
                string_temp.append(" O     ");

            string_temp.append(random.getTitle());
            array_random.add(string_temp.toString());
        }
        db.close();
    }

    private String getIndex(String string){
        return string.trim().substring(1).trim().substring(1).trim();
    }

    private void setButtonAdd() {
        Button button_add = (Button)findViewById(R.id.button_add);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RandomAddDialog.class);
                intent.putExtra("title","");
                context.startActivity(intent);
            }
        });
    }

    private void setButtonModify() {
        Button button_modify = (Button)findViewById(R.id.button_modify);
        button_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (string_selector.equals("") == false) {
                    Intent intent = new Intent(context, RandomAddDialog.class);
                    intent.putExtra("title",string_selector);
                    context.startActivity(intent);
                }
                else
                    Toast.makeText(context, "수정할 항목을 선택해주세요", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setButtonDelete() {
        Button button_delete = (Button) findViewById(R.id.button_delete);
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (string_selector.equals("") == false) {
                    DB db = new DB(context);
                    db.deleteRandom(string_selector);
                    for(String str: array_random){
                        if(getIndex(str).equals(string_selector)){
                            array_random.remove(str);
                            break;
                        }
                    }
                    adapter_random.notifyDataSetChanged();
                    string_selector = "";
                    initColor();
                    db.close();
                    if (array_random.size() == 0) {
                        Toast.makeText(context, "모든 항목이 삭제되었습니다", Toast.LENGTH_LONG).show();
                        dismiss();
                    }
                } else
                    Toast.makeText(context, "삭제할 항목을 선택해주세요", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setButtonCancel() {
        Button button_cancel = (Button)findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void initColor(){
        View view_child;
        for (int j = 0, int_count = list_random.getChildCount(); j < int_count; j++) {
            view_child = list_random.getChildAt(j);
            view_child.setBackgroundColor(Color.WHITE);
            ((TextView)view_child.findViewById(R.id.text_item)).setTextColor(context.getResources().getColor(R.color.darkGray));
        }
    }

    private void setReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("SEND_RANDOM");
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                initArray();
                adapter_random.notifyDataSetChanged();
                initColor();
                string_selector="";
            }
        };
        context.registerReceiver(receiver, filter);
    }

    private void setDismiss() {
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                context.unregisterReceiver(receiver);
            }
        });
    }
}
