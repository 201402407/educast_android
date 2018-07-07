package com.example.administrator.educast_silsb4;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TeamDatabase {
    public static String DATABASE_NAME = "team.db";
    public static String TABLE_TEAM_INFO = "TEAM_INFO";
    public static int DATABASE_VERSION = 1;
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private static TeamDatabase database;

    private Context context;
    private TeamDatabase(Context context) {
        this.context = context;
    }

    // 싱글턴 패턴.
    public static TeamDatabase getInstance(Context context) {
        if(database == null) {
            database = new TeamDatabase(context);
        }

        return database;
    }

    // DB 여는 함수
    public boolean open() {
        dbHelper =  new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase(); // DB를 Writable 형태로 연다.

        return true;
    }

    // DB 닫는 함수
    public void close() {
        db.close();
        database = null;
    }

    public Cursor rawQuery(String SQL) {
        Cursor cursor = null;
        try{
            cursor = db.rawQuery(SQL, null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return cursor;
    }

    public boolean execSQL(String SQL) {
        try{
            db.execSQL(SQL);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private  class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            String DROP_SQL = "DROP TABLE IF EXISTS " + TABLE_TEAM_INFO; // 만약 테이블이 존재하면 지움.
            try {
                _db.execSQL(DROP_SQL);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            String CREATE_SQL = "CREATE TABLE " + TABLE_TEAM_INFO + "(" +
                    " _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    " NAME TEXT, " +
                    " HEADCOACH TEXT, " +
                    " HOMEGROUND TEXT, " +
                    " FOUNDATION TEXT, " +
                    " IMAGE_NAME TEXT" +
                    ")";

            try {
                _db.execSQL(CREATE_SQL);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            insertData(_db, "한화 이글스", "김성근", "대전", "2018년", "camera_img1");
            insertData(_db, "두산 베어스", "범죄두", "잠실", "2022년", "camera_img2");
            insertData(_db, "LG 트윈스", "박용택", "서울", "2228년", "camera_img3");
            insertData(_db, "KIA 타이거즈", "이종범", "광주", "2138년", "camera_img4");
            insertData(_db, "넥센 히어로즈", "신재영", "고척", "2010년", "camera_img5");
        }

        private void insertData(SQLiteDatabase _db, String name, String coach, String homeGround, String foundation, String Image_Name) {
            try{
                _db.execSQL("insert into " + TABLE_TEAM_INFO + " (NAME, HEADCOACH, HOMEGROUND, FOUNDATION, IMAGE_NAME) "
                        + "values ('" + name + "', '" + coach + "', '" + homeGround + "', '" + foundation + "', '" + Image_Name + "');");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 자동 호출. 데이터를 열 때 호출.
        public void onOpen(SQLiteDatabase _db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            if (oldVersion < 2) {

            }

        }
    }
}
