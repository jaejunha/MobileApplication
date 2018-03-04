package dreamline91.naver.com.checker.functionality.lock;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import dreamline91.naver.com.checker.R;

/**
 * Created by dream on 2018-02-27.
 */

public class LockActivity extends Activity {

    private int int_startCursor;
    private int int_currentCursor;
    private int int_endCursor;

    private int int_screenWidth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        slideEvent();
    }

    private void slideEvent() {
        WindowManager windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        int_screenWidth = display.getWidth();
        final LinearLayout linearLayout = findViewById(R.id.linearLayout);
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        int_startCursor = (int)motionEvent.getRawX();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        int_currentCursor = (int)motionEvent.getRawX();
                        if(int_currentCursor-int_startCursor>=0)
                            linearLayout.setX(int_currentCursor-int_startCursor);
                        return true;
                    case MotionEvent.ACTION_UP:
                        int_endCursor = (int)motionEvent.getRawX();
                        if((int_endCursor-int_startCursor)>(int_screenWidth/3.5))
                            finish();
                        else {
                            linearLayout.setX(0);
                            int_startCursor = 0;
                            int_currentCursor = 0;
                        }
                        return false;
                }
                return false;
            }
        });
    }
}
