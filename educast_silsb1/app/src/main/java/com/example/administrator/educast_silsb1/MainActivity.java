package com.example.administrator.educast_silsb1;

import android.content.ContentValues;
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

        Button insertBtn = (Button) findViewById(R.id.btnInsert);
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tbName = tbinputName.getText().toString();
                insertMemberParam(tbName);
                msgOutput("레코드 추가 완료.");
            }
        });

        Button updateBtn = (Button) findViewById(R.id.btnUpdate);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tbName = tbinputName.getText().toString();
                updateMemberParam(tbName);
                msgOutput("레코드 수정 완료.");
            }
        });

        Button deleteBtn = (Button) findViewById(R.id.btnDelete);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tbName = tbinputName.getText().toString();
                deleteMemberParam(tbName);
                msgOutput("레코드 삭제 완료.");
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

    // 레코드 추가하는 함수
    private int insertMember(String name) {
        msgOutput("[" + name + "] 테이블에 멤버 추가");

        int count = 2;
        // 레코드 추가시 사용하는 SQL 명령어 : insert into 테이블명(필드명) values(값1, 값2, ...)
        sDB.execSQL("insert into " + name + "(name, address, tel) values('이해원', '대전', '010-6572-0153');");
        sDB.execSQL("insert into " + name + "(name, address, tel) values('이해투', '서울', '010-1234-1452');");
        return count;
    }

    // 인수(ContentValues)를 이용해서 table 데이터 값 추가하기.
    private int insertMemberParam(String name) {
        msgOutput("인수를 이용한 레코드 추가하기!ㅋ");
        int count = 1;
        ContentValues data = new ContentValues();

        data.put("name", "임꺽정");
        data.put("address", "울산");
        data.put("tel", "010-5551-2345");
        sDB.insert(name, null, data);
        // sDB.insert(table 이름, nullColumnHack(테이블 칸마다 빈 칸이 있을 수 있으므로 빈 칸을 null로 정의한다. = null적기), data)


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

    // 레코드 수정하기 (DB의 table 데이터 값 수정)
    private int updateMemberParam(String name) {
        msgOutput("레코드 수정하기");

        int count = 1;

        ContentValues data = new ContentValues();
        data.put("address", "부산");
        String[] whereArgs = {"임꺽정"};

        sDB.update(name, data, "name=?", whereArgs);
        // sDB.update(table 이름, ContentValues(수정할 값), 조건절(수정하고 싶은 부분의 키), argument(수정하고 싶은 부분의 값))
        return count;
    }

    // 레코드 삭제하기 (DB의 table 데이터 값 삭제)
    private int deleteMemberParam(String name) {
        msgOutput("레코드 삭제하기");

        String[] whereArgs = {"임꺽정"};

        int i = sDB.delete(name, "name=?", whereArgs); // 임꺽정 값 삭제
        return i;
    }
    private void msgOutput(String msg) {
        Log.d("MainActivity", msg); // 1)위치 2) 메세지
        resultTxt.append("\n" + msg);
    }

}
