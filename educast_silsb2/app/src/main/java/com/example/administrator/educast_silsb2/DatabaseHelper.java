package com.example.administrator.educast_silsb2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    final static String TAG = "DatabaseHelper";

    // 생성자
    public DatabaseHelper(Context context) { // db파일 생성하는 객체.
        super(context, "Member.db", null, 1); // version은 현재 버전. 자동으로 만들어진다.
    }

    // 파일이 처음 만들어질 때 발생하는 메소드. 반드시 재정의. 맨 처음 호출.
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG,"onCreate()");
        String sql = "CREATE TABLE member (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, id TEXT, pw TEXT)";
        db.execSQL(sql);
    }

    // 데이터베이스 업그레이드 해주는 함수. 반드시 재정의
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade() 호출");
        String sql = "DROP TABLE IF EXISTS member";
        db.execSQL(sql);
        onCreate(db);
    }
}
