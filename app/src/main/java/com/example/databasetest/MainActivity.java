package com.example.databasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/*

* 命令行查看数据库数据：
* cmd 窗口：
* 1.adb shell
* 2.$ su
* 3.# cd /data/data/com.example.databasetest/databases/ 进入目录
* 4.# ls 数据库列表
* 5.# sqlite3 BookStore.db(要查看的数据库的名字)
* 6.# .table 查看表单
* 7.# .schema 查看建表过程语句
* 8.# .exit (退出此数据库的查询)
* 9.# select * from Book  (表名)数据库语句查询
*
* */

public class MainActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//       版本为1
//        dbHelper = new MyDatabaseHelper(this,"BookStore.db",null,1);
//        版本数大于1即可让onUpgrade()方法得到执行
        dbHelper = new MyDatabaseHelper(this,"BookStore.db",null,2);
//数据库增加数据
        Button addData = (Button)findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                //开始组装第一条数据
                values.put("name","The Da Vinci Code");
                values.put("author","Dan Brown");
                values.put("pages",454);
                values.put("price",16.96);
                db.insert("Book",null,values);
                //插入第一条数据
                values.clear();
                //开始组装第二条数据
                values.put("name","The Lost Symbol");
                values.put("author","Dan Brown");
                values.put("pages",510);
                values.put("price",19.95);
                db.insert("Book",null,values);
                //插入第二条数据

//                直接使用SQL语句完成添加数据的操作，难道不是放在这吗？闪退了。。。
//                db.execSQL("insert into Book(name,autohr,pages,price) values(?,?,?,?)",
//                        new String[]{"The Little Prince", "July","700","39.99"});
//                db.execSQL("insert into Book(name,autohr,pages,price) values(?,?,?,?)",
//                        new String[]{"The Little Princess", "July","630","35.96"});
//                更新数据的方法：
//                db.execSQL("update Book set price = ? where name = ? ",new String[] {"10.99","The DaVinci Code"});
//                删除数据的方法：
//                db.execSQL("delete from Book where pages > ? ", new String[]{"500"});
//                查询数据的方法：
//                db.rawQuery("select * from Book",null);


            }
        });
        Button createDatabase = (Button)findViewById(R.id.create_database);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });
//        数据库改数据
        Button updateData = (Button)findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
// 点击事件中构建一个 ContentValues对象，并且只给它指定了一组数据，说明只是想把价格这一列的数据更新
// 然后调用了SQLiteDatabase的update()方法去执行具体的更新操作，可以看到，这里使用了第三、第四个参数来指定具体更新哪几行。第三个参数对应的是SQL语句的where部分，表示更新所有name等于？的行，而？是一个占位符，可以通过第四个参数提供的一个字符串数组为第三个参数中的每个占位符指定相应的内容。
                ContentValues values = new ContentValues();
                values.put("price",10.99);
//  更新书名为："The Da Vinci Code"的价格
                db.update("Book",values,"name=?",new String[] {"The Da Vinci Code"});
            }
        });
//        数据库删除数据
        Button deleteButton = (Button)findViewById(R.id.delete_data);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
//   指明去删除Book表中的数据，并且通过第二、第三个参数来指定仅删除那些页数超过500页的书。
                db.delete("Book","price = ?",new String[]{"16.96"});
            }
        });
//        数据库查询数据

        Button queryButton = (Button)findViewById(R.id.query_data);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
//                查询Book表中所有数据
// 这里的query()方法非常简单，只是使用了第一个参数指明去查询Book表，后面的参数全部为null。这就表示希望查询这张表中的所有数据
                Cursor cursor = db.query("Book",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do{
//                        遍历Cursor对象，取出数据并打印
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("MainActivity","book name is "+name);
                        Log.d("MainActivity","book author is "+author);
                        Log.d("MainActivity","book pages is "+pages);
                        Log.d("MainActivity","book price is "+price);
                    }while(cursor.moveToNext());
                }
                cursor.close();
            }
        });


    }
}