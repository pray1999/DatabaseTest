package com.example.databasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class MyDatabaseHelper extends SQLiteOpenHelper {
    //把建表语句定义成一个字符串常量，然后在onCreate()方法中又调用db.execSQL(CREATE_BOOK);执行这条建表语句
    public static final String CREATE_BOOK = "create table Book("
            +"id integer primary key autoincrement,"
//           建表时将id列设置为自增长，它的值会在入库的时候自动生成，不需要手动给他赋值
            +"author real,"
            +"price real,"
            +"pages integer,"
            +"name text)";

    public static final String CREATE_CATEGORY = "create table Category ("
            +"id integer primary key autoincrement,"
            +"category_name text,"
            +"category_code integer)";


    private Context mContext;
    public MyDatabaseHelper(Context context,  String name,  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
        Toast.makeText(mContext,"Create succeeded",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("drop table if exists Book");
    db.execSQL("drop table if exists Category");
    onCreate(db);
    }
}
