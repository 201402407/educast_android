package com.example.silsb3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "testDB";
    String sql;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { // db 생성할 때. 생성자 생성 시 자동 호출.
        sql = "CREATE TABLE test (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, age TEXT);";
        db.execSQL(sql);
        db.execSQL("INSERT INTO test VALUES(null, '이해원', '24');");
        db.execSQL("INSERT INTO test VALUES(null, '화이투', '25');");
        db.execSQL("INSERT INTO test VALUES(null, '이해삼', '33');");
        db.execSQL("INSERT INTO test VALUES(null, '이상해', '42');");
        db.execSQL("INSERT INTO test VALUES(null, '오해영', '15');");

    }

    public void onOpen(SQLiteDatabase db) { // db 열릴 때. 생략 가능

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS test");
        onCreate(db);
    }
}
