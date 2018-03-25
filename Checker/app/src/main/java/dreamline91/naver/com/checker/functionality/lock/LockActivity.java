package dreamline91.naver.com.checker.functionality.lock;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Random;

import dreamline91.naver.com.checker.R;
import dreamline91.naver.com.checker.functionality.lock.dialog.RandomDialog;
import dreamline91.naver.com.checker.util.db.DB;
import dreamline91.naver.com.checker.util.object.Kakao;
import dreamline91.naver.com.checker.util.object.RandomText;

/**
 * Created by dream on 2018-02-27.
 */

public class LockActivity extends Activity {

    private final int INTENT_ALBUM = 0;
    private final int INTENT_CROP = 1;

    private int int_startCursor;
    private int int_currentCursor;
    private int int_endCursor;

    private int int_screenWidth;
    private int int_screenHeight;

    private TextView text_randomTitle;
    private TextView text_randomLink;
    private TextView text_randomContent;
    private ImageView image_random;
    private TextView text_calendar;
    private TextView text_battery;
    private TextView text_kakao;
    private Button button_delete;


    private BroadcastReceiver receiver_battery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        getDeviceSize();
        setBackground();
        loadBackground();
        setTextCalendar();
        setTextBattery();
        setRandom();
        setTextKakao();
        setButtonDelete();
        setButtonRandom();
        setButtonBackground();
        slideEvent();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setBackground() {
        LinearLayout layout_lock = (LinearLayout) findViewById(R.id.layout_lock);
        String string_backgroundPath = loadBackground();
        if (string_backgroundPath.equals("") == false)
            layout_lock.setBackground(makeBitmap(string_backgroundPath));
    }

    private String loadBackground() {
        SharedPreferences preference = getSharedPreferences("checker", Activity.MODE_PRIVATE);
        return preference.getString("string_backgroundPath", "");
    }

    private void setTextCalendar() {
        text_calendar = (TextView) findViewById(R.id.text_calendar);
        refreshCalendar();
    }

    private void refreshCalendar() {
        Calendar cal = Calendar.getInstance();
        String string_calendar;
        String[] string_week = {"일", "월", "화", "수", "목", "금", "토"};
        string_calendar = String.format("%04d.%2d.%2d.%s", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE), string_week[cal.get(Calendar.DAY_OF_WEEK) - 1]);
        text_calendar.setText(string_calendar);
    }

    private void setTextBattery() {
        text_battery = (TextView) findViewById(R.id.text_battery);
        receiver_battery = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                    int int_level = intent.getIntExtra("level", 0);
                    int int_scale = intent.getIntExtra("scale", 100);

                    text_battery.setText(String.format("배터리 : %2d%%", (int_level * 100 / int_scale)));
                }
            }
        };
    }


    private void setRandom() {
        image_random = (ImageView) findViewById(R.id.image_random);
        text_randomTitle = (TextView) findViewById(R.id.text_randomTitle);
        text_randomLink = (TextView) findViewById(R.id.text_randomLink);
        text_randomContent = (TextView) findViewById(R.id.text_randomContent);

        Button button_randomNext = (Button)findViewById(R.id.button_randomNext);
        button_randomNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshRandom();
            }
        });
    }

    private void setTextKakao() {
        text_kakao = (TextView) findViewById(R.id.text_kakao);
    }

    private void setButtonDelete() {
        button_delete = (Button) findViewById(R.id.button_delete);
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DB db = new DB(getApplicationContext());
                db.deleteKakao();
                db.close();
                text_kakao.setText("전달된 카카오 메시지가 없습니다");
                button_delete.setEnabled(false);
            }
        });
    }

    private void setButtonRandom() {
        ImageButton button_random = (ImageButton) findViewById(R.id.button_random);
        button_random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RandomDialog.class));
            }
        });
    }

    private void setButtonBackground() {
        ImageButton button_background = (ImageButton) findViewById(R.id.button_background);
        button_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "배경화면으로 사용할 이미지를 선택해주세요", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, INTENT_ALBUM);
            }
        });
    }

    private void slideEvent() {
        final LinearLayout linearLayout = findViewById(R.id.layout_lock);
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        int_startCursor = (int) motionEvent.getRawX();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        int_currentCursor = (int) motionEvent.getRawX();
                        if (int_currentCursor - int_startCursor >= 0)
                            linearLayout.setX(int_currentCursor - int_startCursor);
                        return true;
                    case MotionEvent.ACTION_UP:
                        int_endCursor = (int) motionEvent.getRawX();
                        if ((int_endCursor - int_startCursor) > (int_screenWidth / 3.5))
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case INTENT_ALBUM:
                    String string_folder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/checker/";
                    new File(string_folder).mkdirs();
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(data.getData(), "image/*");
                    intent.putExtra("crop", "true");
                    intent.putExtra("aspectX", int_screenWidth);
                    intent.putExtra("aspectY", int_screenHeight);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(string_folder, "background.jpg")));
                    intent.putExtra("scale", "true");
                    startActivityForResult(intent, INTENT_CROP);
                    break;
                case INTENT_CROP:
                    try {
                        LinearLayout layout_lock = (LinearLayout) findViewById(R.id.layout_lock);
                        String string_path = getPath(data.getData());
                        layout_lock.setBackground(makeBitmap(string_path));
                        saveBackground(string_path);

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "배경화면 바꾸기에 실패했습니다", Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
    }

    private void saveBackground(String string_path) {
        SharedPreferences preference = getSharedPreferences("checker", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString("string_backgroundPath", string_path);
        editor.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private BitmapDrawable makeBitmap(String path) {

        Bitmap bitmap_background = BitmapFactory.decodeFile(path);
        if (bitmap_background == null)   //called when background image was deleted
            return null;
        Bitmap bitmap_resized;
        if (bitmap_background.getWidth() > bitmap_background.getHeight()) {
            bitmap_resized = Bitmap.createScaledBitmap(bitmap_background, int_screenHeight, int_screenWidth, true);
            bitmap_background.recycle();
        } else {
            bitmap_resized = Bitmap.createScaledBitmap(bitmap_background, int_screenWidth, int_screenHeight, true);
            bitmap_background.recycle();
        }
        try {
            ExifInterface exif = new ExifInterface(path);
            return new BitmapDrawable(rotateBitmap(bitmap_resized, exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)));
        } catch (Exception e) {
            return new BitmapDrawable(bitmap_resized);
        }
    }

    /* reference : https://stackoverflow.com/questions/20478765/how-to-get-the-correct-orientation-of-the-image-selected-from-the-default-image */
    private Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bitmap_rotate = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bitmap_rotate;
        } catch (OutOfMemoryError e) {
            return bitmap;
        }
    }
    /* reference : https://stackoverflow.com/questions/20478765/how-to-get-the-correct-orientation-of-the-image-selected-from-the-default-image */

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

    public void getDeviceSize() {
        DisplayMetrics display = getApplicationContext().getResources().getDisplayMetrics();
        int_screenWidth = display.widthPixels;
        int_screenHeight = display.heightPixels;
    }

    protected void onResume() {
        super.onResume();
        refreshCalendar();
        registerReceiver(receiver_battery, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        refreshKakao();
        refreshRandom();
    }

    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver_battery);
    }

    public void refreshKakao() {
        DB db = new DB(getApplicationContext());
        Calendar calendar_post = Calendar.getInstance();
        String string_time;
        StringBuffer string_text = new StringBuffer();
        ArrayList<Kakao> array_kakao = db.selectKakao();
        if (array_kakao.size() > 0) {
            button_delete.setEnabled(true);
            Collections.reverse(array_kakao);
            for (Kakao kakao : array_kakao) {
                calendar_post.setTimeInMillis(kakao.getTime());
                string_time = String.format("%02d:%02d", calendar_post.get(Calendar.HOUR_OF_DAY), calendar_post.get(Calendar.MINUTE));
                string_text.append(string_time + " [" + kakao.getTitle() + "] " + kakao.getContent() + "\n");
            }
            text_kakao.setText(string_text.toString());
        } else {
            button_delete.setEnabled(false);
        }
        db.close();
    }

    private void refreshRandom() {
        text_randomLink.setOnClickListener(null);
        text_randomLink.setPaintFlags(text_randomLink.getPaintFlags());
        image_random.setBackground(getDrawable(R.drawable.border_white));

        DB db = new DB(this);
        ArrayList<RandomText> array_random = db.selectRandom();
        if (array_random.size() > 0) {
            final RandomText random = array_random.get(new Random().nextInt(array_random.size()));
            text_randomTitle.setText(random.getTitle());
            text_randomLink.setText(random.getLink());
            text_randomContent.setText(random.getContent());
            if(random.getLink().equals("") == false){
                text_randomLink.setPaintFlags(text_randomLink.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
                text_randomLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            String string_url;
                            if(random.getLink().toLowerCase().contains("http") == false)
                                string_url = "http://"+random.getLink();
                            else
                                string_url = random.getLink();
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(string_url));
                            startActivity(intent);
                        }catch(Exception e){
                            Toast.makeText(getApplicationContext(),"링크 주소가 잘못됬습니다",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
            if(random.getImage().equals("") == false)
                image_random.setBackground(makeBitmap(random.getImage()));
        }else{
            text_randomTitle.setText("지정한 문구가 없습니다");
            text_randomLink.setText("");
            text_randomContent.setText("");
        }
        db.close();
    }
}
