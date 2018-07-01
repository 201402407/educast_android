package com.example.administrator.educast_silsb1;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    String dbName, tbName;
    SQLiteDatabase sDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText dbinputName = (EditText)findViewById(R.id.dbNameET);
        final EditText tbinputName =  (EditText) findViewById(R.id.tbNameET);

        Button createDBbtn = (Button) findViewById(R.id.btnCreateDB);
        createDBbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbName = dbinputName.getText().toString();
                createDB(dbName);
            }
        });
        Button createTBbtn = (Button) findViewById(R.id.btnCreateTB);
        createTBbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tbName = tbinputName.getText().toString();
                createTB(tbName);
            }
        });

    }

    // 테이블 생성 과정
    private void createTB(String name) {
        // 테이블 생성 SQL문. () 안은 SQL문.
        sDB.execSQL("create table if not exists" + name + "(" // 만약 table중 name이 존재하지 않으면
        + "no integer PRIMARY KEY autoincrement, " // 키를 int(숫자)로 설정해 자동으로 증가하게 하고
        + "name text, " // 이름과
        + "address text, " // 주소와
        + "tel text);" ); // 전화번호를 text 형식으로 저장하겠다.
    }

    // DB 생성 과정
    private void createDB(String name) {
        println("DB 생성 이름 : " + name);
        try{
            sDB = openOrCreateDatabase(name, MODE_PRIVATE,null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void println(String s) {
        Log.d("Activity", s);
    }
}
