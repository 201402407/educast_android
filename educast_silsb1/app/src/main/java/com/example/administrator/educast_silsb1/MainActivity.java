package com.example.administrator.educast_silsb1;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String dbName, tbName;
    SQLiteDatabase sDB;

    TextView resultTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText dbinputName = (EditText)findViewById(R.id.dbNameET);
        final EditText tbinputName =  (EditText) findViewById(R.id.tbNameET);

        Button createDBbtn = (Button) findViewById(R.id.btnCreateDB);

        resultTxt = (TextView) findViewById(R.id.tv01);

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
                int count = insertMember(tbName);
                msgOutput(count + "개의 레코드가 추가 되었습니다.");
            }
        });

    }

    // 테이블 생성 과정
    private void createTB(String name) {
        msgOutput("[" + name + "] 테이블 생성 중");
        // 테이블 생성 SQL문. () 안은 SQL문.
        sDB.execSQL("create table if not exists " + name + "(" // 만약 table중 name이 존재하지 않으면
        + " no integer PRIMARY KEY autoincrement, " // 키를 int(숫자)로 설정해 자동으로 증가하게 하고
        + " name text, " // 이름과
        + " address text, " // 주소와
        + " tel text);" ); // 전화번호를 text 형식으로 저장하겠다.
        msgOutput("[" + name + "] 테이블 생성 완료");
    }

    private int insertMember(String name) {
        msgOutput("[" + name + "] 테이블에 멤버 추가");

        int count = 2;
        // 레코드 추가시 사용하는 SQL 명령어 : insert into 테이블명(필드명) values(값1, 값2, ...)
        sDB.execSQL("insert into " + name + "(name, address, tel) values('이해원', '대전', '010-6572-0153');");
        sDB.execSQL("insert into " + name + "(name, address, tel) values('이해투', '서울', '010-1234-1452');");
        return count;
    }
    // DB 생성 과정
    private void createDB(String name) {
        msgOutput("DB 생성 중 : [" + name + "]");
        try{
            sDB = openOrCreateDatabase(name, MODE_PRIVATE,null); // DB생성.
            // 1) DB 이름 2) 모드 3) null(factory)
            msgOutput("[" + name + "] DB 생성 완료");
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void msgOutput(String msg) {
        Log.d("MainActivity", msg); // 1)위치 2) 메세지
        resultTxt.append("\n" + msg);
    }

}
