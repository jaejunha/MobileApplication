package dreamline91.naver.com.checker.functionality.manage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import dreamline91.naver.com.checker.R;
import dreamline91.naver.com.checker.functionality.manage.dialog.MListAddDialog;

/**
 * Created by dream on 2017-11-12.
 */

public class MListActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private ImageView imageAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mlist);

        makeObject();
        findID();
        setListener();
    }

    private void makeObject() {
    }

    private void findID() {
        imageAdd = (ImageView)findViewById(R.id.imageAdd);
    }

    private void setListener() {
        imageAdd.setOnTouchListener(this);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()){
            case R.id.imageAdd:
                new MListAddDialog(this).show();
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ManageActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}
