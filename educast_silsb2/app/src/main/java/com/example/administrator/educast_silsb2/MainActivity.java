package com.example.administrator.educast_silsb2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    final static String TAG = "MainActivity";

    DatabaseHelper databaseHelper;

    Button insertBtn, deleteBtn, updateBtn, selectBtn;
    TextView selectTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this); // MainActivity 자신이 context.

        insertBtn = (Button) findViewById(R.id.btnInsert);
        deleteBtn = (Button) findViewById(R.id.btnDelete);
        selectBtn = (Button) findViewById(R.id.btnSelect);
        updateBtn = (Button) findViewById(R.id.btnUpdate);

        selectTxt = (TextView) findViewById(R.id.tvSelect);

        insertBtn.setOnClickListener(listener);
        deleteBtn.setOnClickListener(listener);
        selectBtn.setOnClickListener(listener);
        updateBtn.setOnClickListener(listener);
    }

        View.OnClickListener listener = new View.OnClickListener(){
            SQLiteDatabase sDB;
            ContentValues data;

            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnInsert:
                        try {
                            sDB = databaseHelper.getWritableDatabase();
                        //    data = new ContentValues();
                         //   data.put("name", "유재석");
                          //  data.put("id", "test");
                           // data.put("pw", "test1234");

                            // sDB.insert("member", null, data); 직접 넣기
                            // 표준 SQL 방식
                            String sql = "INSERT INTO member (_id, name, id, pw) VALUES(null,'유재석', 'test', 'test1234')"; // null 쓴 이유는 _id 키값이므로
                            sDB.execSQL(sql);
                            String sql1 = "INSERT INTO member (_id, name, id, pw) VALUES(null,'유뜡이', 'te123st', '4test1234')"; // null 쓴 이유는 _id 키값이므로
                            sDB.execSQL(sql1);
                            databaseHelper.close(); // 닫아야함
                            Toast.makeText(getBaseContext(),"레코드 추가 완료 !!", Toast.LENGTH_SHORT).show();

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(getBaseContext(), "레코드 추가 오류 !!", Toast.LENGTH_SHORT).show();
                            // 여기서 getApplicationContext() 대신에 getBaseContext()쓰는 이유?

                        }
                        break;

                    case R.id.btnDelete:
                        try {
                            sDB = databaseHelper.getWritableDatabase();

                            //sDB.delete("member", null, null); // 조건 없이 내용 전부 제거

                            String sql = "DELETE FROM member;";
                            sDB.execSQL(sql);

                            databaseHelper.close();
                            Toast.makeText(getBaseContext(), "삭제 완료!!", Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getBaseContext(), "삭제 오류!!", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case R.id.btnUpdate:
                        try {
                            sDB = databaseHelper.getWritableDatabase();

             //               data = new ContentValues();
             //               data.put("name", "홍길동");
             //               String[] whereArgs = {"유재석"};
             //               sDB.update("member", data, "name=?", whereArgs);

                            String sql = "UPDATE member SET name='홍길동' WHERE name='유재석';";
                            sDB.execSQL(sql);

                            databaseHelper.close();
                            Toast.makeText(getBaseContext(), "업데이트 완료!!", Toast.LENGTH_SHORT).show();

                        }
                        catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(getBaseContext(), "업데이트 오류!!", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case R.id.btnSelect: // DB 조회하는 경우 ( 전체 출력 )
                        try{
                            sDB = databaseHelper.getReadableDatabase();

                            String[] fields = {"name", "id", "pw"};
                            Cursor cursor = sDB.query("member", fields, null, null, null, null, null);

                         //   String sql = "SELECT name, id, pw FROM member";
                            // SELECT 조회할 필드명 FROM DB이름
                         //   Cursor cursor = sDB.rawQuery(sql, null); -> select시, 결과값이 있을 때 사용하는 커서 객체 리턴하는 rawQuery 사용.
                            // 결과 값이 없는 경우는 execSQL() 사용.
                            // DB 사용 시 insert update delete query 이용한다.

                            StringBuffer sb = new StringBuffer();
                            while(cursor.moveToNext()) {
                                String name = cursor.getString(0);
                                String id = cursor.getString(1);
                                String pw = cursor.getString(2);

                                sb.append("name : " + name).append(", id : " + id).append(", pw : " + pw + "\n");


                            }
                            selectTxt.setText(sb.toString());

                            cursor.close();
                            databaseHelper.close();
                            Toast.makeText(getBaseContext(), "조회 완료!!", Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(getBaseContext(), "조회 오류!!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }

            }

    };
}
