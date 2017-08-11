package wj.com.sqliterestu;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button create = (Button)findViewById(R.id.button_create);
        final Button insert = (Button)findViewById(R.id.button_insert);
        final EditText insertText = (EditText)findViewById(R.id.edit_insert);
        Button query = (Button)findViewById(R.id.button_query);
        final TextView queryText = (TextView)findViewById(R.id.text_query);
        Button delete = (Button)findViewById(R.id.button_delete);
        final EditText deleteText = (EditText)findViewById(R.id.edit_delete);
        Button update = (Button)findViewById(R.id.button_update);
        final EditText updateText = (EditText)findViewById(R.id.edit_update_text);
        final EditText updateId = (EditText)findViewById(R.id.edit_update_id);

        //创建

        // 获取SQLiteOpenHelper实例
        // 第一个参数是Context无需多言
        // 第二个参数是要创建的数据库名
        // 第三个参数是查询数据返回的自定义Cursor这里我们传入Null就行
        // 第四个参数则是版本号
        final MyDbHelper myDbHelper = new MyDbHelper(MainActivity.this,"Stu.db",null,1);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //执行该方法后会自动检测是否存在该数据库，若无则执行onCreate方法
                // 同时判断版本号，若版本更新了，则会执行onUpgrade方法
                myDbHelper.getWritableDatabase();
            }
        });

        //插入
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (insertText.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this,"插入失败",Toast.LENGTH_SHORT).show();
                }else{
                    //获取SQLiteDatabase实例
                    SQLiteDatabase db = myDbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("stuName",insertText.getText().toString());
                    //插入
                    //第一个参数为表名
                    //第二个参数用于制定一个字段让某些可为空的列自动赋null
                    //第三个参数是一个ContentValues对象
                    db.insert("Stu",null,values);
                    values.clear();
                    Toast.makeText(MainActivity.this,"插入成功",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //删除
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDbHelper.getWritableDatabase();
                //delete返回值为数据库受影响行数（int类型），通过返回值判断删除结果
                //第一个参数是表名
                //第二个参数与第三个参数是约束条件
                if (db.delete("Stu","id = ?",new String[]{deleteText.getText().toString()})==0){
                    Toast.makeText(MainActivity.this,"删除失败",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                }

            }
        });

        //更新
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("stuName",updateText.getText().toString());
                //通过Update方法的返回值判断更新结果
                //第一个参数为表名
                //第二个参数为ConetentValues对象
                //第三个与第四个参数为约束条件
                if (db.update("Stu",values,"id = ?",new String[]{updateId.getText().toString()})==0){
                    Toast.makeText(MainActivity.this,"更新失败",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"更新成功",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //查询
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryText.setText("");
                SQLiteDatabase db = myDbHelper.getWritableDatabase();
                //查询，并把结果存到一个Cursor对象
                Cursor cursor = db.query("Stu",null,null,null,null,null,null);
                if (cursor.moveToFirst()){
                    do {
                        queryText.append("id: "+cursor.getString(cursor.getColumnIndex("id"))+"\n");
                        queryText.append("name:"+cursor.getString(cursor.getColumnIndex("stuName"))+"\n");
                    }while(cursor.moveToNext());
                }
                cursor.close();
            }
        });

    }
}
