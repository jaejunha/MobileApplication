package dreamline91.naver.com.checker.util.db;

import android.content.Context;
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
    }

    public void insertType(String table, String name){
        db.execSQL("CREATE TABLE IF NOT EXISTS "+table+"(name VARCHAR(20) PRIMARY KEY)");
        try {
            db.execSQL("INSERT INTO " + table + "('name') VALUES('" + name + "')");
        }catch(android.database.sqlite.SQLiteConstraintException e){
            //nothing to do
        }
    }
}
