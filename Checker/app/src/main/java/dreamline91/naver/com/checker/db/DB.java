package dreamline91.naver.com.checker.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
}
