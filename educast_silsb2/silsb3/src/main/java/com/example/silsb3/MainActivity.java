package com.example.silsb3;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private DatabaseHelper databaseHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list = (ListView) findViewById(R.id.listView);

        // public SimpleCursorAdapter(Context, Item의 디자인 레이아웃 전체, cursor 객체, from(문자열 형태. 커서에 들어있는 필드 중 화면에 보여줄 필드), UI객체(아이템 id))

        boolean isOpen = openDatabase();

        if(isOpen) {
            Cursor cursor = execRawQuery();
            // startManagingCursor(cursor); // 권장되는 방법 X. 액티비티 상태에 따른 활성화/비활성화 메소드.

            String[] column = new String[] {"name", "age"};
            int[] to = new int[] {R.id.nameTxt, R.id.ageTxt};
            SimpleCursorAdapter simpleAdapter = new SimpleCursorAdapter(this, R.layout.item_layout, cursor, column, to);

            list.setAdapter(simpleAdapter); // 직접 어댑터 ListView에 연결.
        }

    }

    // DB가 열릴 때 진행
    private boolean openDatabase() {

        Log.d(TAG, "데이터베이스 생성 및 오픈!!!");
        databaseHelper = new DatabaseHelper(this);
        db = databaseHelper.getWritableDatabase();

        return true;
    }

    // 실제 데이터를 조회할 수 있는 함수.
    private Cursor execRawQuery() {
        Log.d(TAG, "execRawQuery 시작!!");
        String sql = "select _id, name, age from test where age > ?";
        String args[] = {"20"};

        Cursor cursor = db.rawQuery(sql, args); // sql문과 args(조건)


        return cursor;
    }
}
