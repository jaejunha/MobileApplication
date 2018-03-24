package dreamline91.naver.com.checker.util.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import dreamline91.naver.com.checker.util.object.Kakao;
import dreamline91.naver.com.checker.util.object.RandomText;

/**
 * Created by dream on 2017-11-05.
 */

public class DB extends SQLiteOpenHelper {
    private static final String DB_NAME = "checker.db";
    private SQLiteDatabase db;
    private Context context;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public synchronized void close() {
        db.close();
        super.close();
    }

    public void finalize() throws Throwable {
        close();
        super.finalize();
    }

    public DB(Context context) {
        super(context, DB_NAME, null, 1);
        db = getReadableDatabase();
        this.context = context;
        createTables();
    }

    public void createTables() {
        db.execSQL("CREATE TABLE IF NOT EXISTS type(name VARCHAR(20) PRIMARY KEY)");
        db.execSQL("CREATE TABLE IF NOT EXISTS kakao(time TEXT, title TEXT, content TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS random(title VARCHAR(20) PRIMARY KEY, content TEXT, link VARCHAR(100), image VARCHAR(100))");
    }

    public void insertType(String name) {
        try {
            db.execSQL("INSERT INTO type('name') VALUES('" + name + "')");
        } catch (android.database.sqlite.SQLiteConstraintException e) {
            //nothing to do
        }
    }

    public void deleteType(String name) {
        db.execSQL("DELETE FROM type WHERE name='" + name + "'");
    }

    public String[] selectType() {
        String[] array_types;
        int int_counter;
        Cursor cursor = db.rawQuery("SELECT * FROM type", null);
        int_counter = cursor.getCount();
        array_types = new String[int_counter];
        for (int i = 0; i < int_counter; i++) {
            cursor.moveToNext();
            array_types[i] = cursor.getString(cursor.getColumnIndex("name"));
        }
        return array_types;
    }

    public void updateType(String origin, String change) {
        db.execSQL("UPDATE type SET name='" + change + "' WHERE name='" + origin + "'");
    }

    public void insertKakao(long time, String title, String text) {
        try {
            db.execSQL("INSERT INTO kakao('time', 'title', 'content') VALUES('" + String.format("%d", time) + "', '" + title + "','" + text + "')");
        } catch (android.database.sqlite.SQLiteConstraintException e) {
            //nothing to do
        }
    }

    public ArrayList<Kakao> selectKakao() {
        ArrayList<Kakao> array_kakaos = new ArrayList<>();
        int int_counter;
        Cursor cursor = db.rawQuery("SELECT * FROM kakao", null);
        int_counter = cursor.getCount();
        if(int_counter>0){
            for (int i = 0; i < int_counter; i++) {
                cursor.moveToNext();
                array_kakaos.add(new Kakao(Long.parseLong(cursor.getString(0)),cursor.getString(1),cursor.getString(2)));
            }
        }
        return array_kakaos;
    }

    public void deleteKakao() {
        db.execSQL("DELETE FROM kakao");
    }

    public boolean existRandom(String title){
        int int_counter;
        Cursor cursor = db.rawQuery("SELECT * FROM random WHERE title='"+title+"'", null);
        int_counter = cursor.getCount();
        if(int_counter>0)
            return true;
        return false;
    }

    public void insertRandom(String title, String link, String path, String content){
        try {
            db.execSQL("INSERT INTO random('title', 'link', 'image', 'content') VALUES('"+title+"','"+link+"', '"+path+"', '"+content+"')");
        } catch (android.database.sqlite.SQLiteConstraintException e) {
            //nothing to do
        }
    }

    public ArrayList<RandomText> selectRandom(){
        ArrayList<RandomText> array_randoms = new ArrayList<>();
        int int_counter;
        Cursor cursor = db.rawQuery("SELECT * FROM random", null);
        int_counter = cursor.getCount();
        if(int_counter>0){
            for (int i = 0; i < int_counter; i++) {
                cursor.moveToNext();
                array_randoms.add(new RandomText(cursor.getString(cursor.getColumnIndex("title")),cursor.getString(cursor.getColumnIndex("link")),cursor.getString(cursor.getColumnIndex("image")),cursor.getString(cursor.getColumnIndex("content"))));
            }
        }
        return array_randoms;
    }

    public void deleteRandom(String title) {
        db.execSQL("DELETE FROM random WHERE title='" + title + "'");
    }
}
