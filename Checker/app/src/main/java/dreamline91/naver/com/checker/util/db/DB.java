package dreamline91.naver.com.checker.util.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

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

    public void createTables(){
        db.execSQL("CREATE TABLE IF NOT EXISTS type(no INTEGER PRIMARY KEY, name VARCHAR(20))");
    }
    public void insertType(String name){
        String string_table = "type";
        try {
            db.execSQL("INSERT INTO " + string_table + "('name') VALUES('" + name + "')");
        }catch(android.database.sqlite.SQLiteConstraintException e){
            //nothing to do
        }
    }

    public void deleteType(String name){
        String string_table = "type";
        db.execSQL("DELETE FROM "+string_table+" WHERE name='"+name+"'");
    }

    public String[] selectType(){
        String[] array_types;
        int int_counter;
        Cursor cursor = db.rawQuery("SELECT * FROM type",null);
        int_counter = cursor.getCount();
        array_types = new String[int_counter];
        for(int i=0;i<int_counter;i++){
            cursor.moveToNext();
            array_types[i]=cursor.getString(cursor.getColumnIndex("name"));
        }
        return array_types;
    }
}
