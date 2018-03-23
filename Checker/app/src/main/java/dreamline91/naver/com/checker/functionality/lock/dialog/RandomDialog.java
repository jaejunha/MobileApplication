package dreamline91.naver.com.checker.functionality.lock.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
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

        setListRandom(context);
        setButtonAdd(context);
        setButtonCancel(context);
    }

    private void setListRandom(final Context context) {
        string_selector = "";

        list_random = (ListView)findViewById(R.id.list_random);
        DB db = new DB(context);
        StringBuffer string_temp = new StringBuffer();
        array_random = new ArrayList<String>();
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
        adapter_random = new ArrayAdapter<String>(context, R.layout.list_text, array_random);
        list_random.setAdapter(adapter_random);
        list_random.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                string_selector = ((TextView)view.findViewById(R.id.text_item)).getText().toString();         ;
                string_selector = string_selector.trim().substring(1).trim().substring(1).trim();
            }
        });
        db.close();
    }

    private void setButtonAdd(final Context context) {
        Button button_add = (Button)findViewById(R.id.button_add);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,RandomAddDialog.class);
                context.startActivity(intent);
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
