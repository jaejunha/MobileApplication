package dreamline91.naver.com.checker.functionality.lock.dialog;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import dreamline91.naver.com.checker.R;
import dreamline91.naver.com.checker.util.db.DB;

/**
 * Created by dream on 2018-03-14.
 */

public class RandomAddDialog extends Activity {

    private EditText edit_title;
    private EditText edit_link;
    private EditText edit_image;
    private EditText edit_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_randomadd);
        setFinishOnTouchOutside(false);

        setEditTitle();
        setEditLink();
        setScrolImage();
        setEditImage();
        setButtonImage();
        setEditContent();
        setButtonSave();
        setButtonCancel();
    }

    private void setEditTitle() {
        edit_title = (EditText)findViewById(R.id.edit_title);
    }

    private void setEditLink() {
        edit_link = (EditText)findViewById(R.id.edit_link);
    }

    private void setEditContent(){
        edit_content = (EditText)findViewById(R.id.edit_content);
    }

    private void setScrolImage() {
        final HorizontalScrollView scroll_image = (HorizontalScrollView) findViewById(R.id.scroll_image);
        scroll_image.post(new Runnable(){
            @Override
            public void run() {
                int int_buttonSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getApplicationContext().getResources().getDisplayMetrics());
                ViewGroup.LayoutParams params = scroll_image.getLayoutParams();
                params.width = scroll_image.getWidth()-int_buttonSize;
                scroll_image.setLayoutParams(params);
            }
        });
    }

    private void setEditImage() {
        edit_image = (EditText) findViewById(R.id.edit_image);
        edit_image.post(new Runnable(){
            @Override
            public void run() {
                int int_buttonSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getApplicationContext().getResources().getDisplayMetrics());
                ViewGroup.LayoutParams params = edit_image.getLayoutParams();
                params.width = edit_image.getWidth()-int_buttonSize;
                edit_image.setLayoutParams(params);
            }
        });
    }

    private void setButtonImage() {
        Button button_image = (Button)findViewById(R.id.button_image);
        button_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,0);
            }
        });
    }

    private void setButtonSave() {
        Button button_save = (Button)findViewById(R.id.button_save);
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string_title = edit_title.getText().toString();
                String string_link = edit_link.getText().toString();
                String string_image = edit_image.getText().toString();
                String string_content = edit_content.getText().toString();

                DB db = new DB(getApplicationContext());
                if(db.existRandom(string_title) == false) {
                    db.insertRandom(string_title, string_link, string_image, string_content);
                    db.close();
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"제목이 중복됩니다",Toast.LENGTH_LONG).show();
                    db.close();
                }
            }
        });
    }

    private void setButtonCancel() {
        Button button_cancel = (Button)findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
            edit_image.setText(getPath(data.getData()));
    }

    private String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            return uri.getPath();
        } else {
            cursor.moveToFirst();
            int int_index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(int_index);
        }
    }
}
