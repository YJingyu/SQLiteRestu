package wj.com.sqliterestu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by wj on 2017/8/11.
 */

public class MyDbHelper extends SQLiteOpenHelper {
    private Context mContext;
    public MyDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Stu(" +
                "id integer primary key autoincrement," +
                "stuName text)");
        Toast.makeText(mContext,"DB is create.",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
