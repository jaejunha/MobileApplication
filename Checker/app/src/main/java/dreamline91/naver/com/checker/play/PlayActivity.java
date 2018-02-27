package dreamline91.naver.com.checker.play;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import dreamline91.naver.com.checker.functionality.manage.ManageActivity;
import dreamline91.naver.com.checker.util.service.LockService;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this,LockService.class));
        startActivity(new Intent(this, ManageActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}
