package dreamline91.naver.com.checker.functionality.lock;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

import dreamline91.naver.com.checker.R;

/**
 * Created by dream on 2018-02-27.
 */

public class LockActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        makeObject();
        findID();
        setListener();
    }

    private void setListener() {

    }

    private void findID() {

    }

    private void makeObject() {

    }
}
