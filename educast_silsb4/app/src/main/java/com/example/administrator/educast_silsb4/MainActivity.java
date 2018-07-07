package com.example.administrator.educast_silsb4;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.opengl.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";


    ImageView teamImg;
    TextView team_name;
    TextView head_coach;
    TextView home_ground;
    TextView establish;

    public static int defalutselection = 2;
    TeamDatabase database;
    ImageAdapter imgAdapter;
    int dataCount; // record의 개수
    int[] dataIds; // record의 ID
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 커버플로우 어댑터 설정
        CoverFlow coverFlow = (CoverFlow)findViewById(R.id.coverFlow);
        imgAdapter = new ImageAdapter(this); // 이미지 어댑터 생성.
        coverFlow.setAdapter(imgAdapter); // 어댑터 설정.

        // CoverFlow에 속성 설정.
        coverFlow.setSpacing(-150);
        coverFlow.setSelection(defalutselection, true); // 몇 번째인지, true/false(애니메이션)
        coverFlow.setAnimationDuration(3000); // 애니메이션 시간 설정

        // 이미지가 한가운데에 온 경우의 리스너
        coverFlow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "팀 선택 : " + position, Toast.LENGTH_SHORT).show();

                // DB로부터 데이터를 가져오는 함수.
                selectedItemFromDatabase(position);
            }

            // 아무것도 가운데에 없는 경우
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // View 생성
        teamImg = (ImageView) findViewById(R.id.teamImage);
        team_name = (TextView) findViewById(R.id.teamName);
        head_coach = (TextView) findViewById(R.id.headCoach);
        home_ground = (TextView) findViewById(R.id.homeGround);
        establish = (TextView) findViewById(R.id.foundation);

        // DB 오픈하기 위한 명령
        if(database != null) {
            database.close();
            database = null;
        }

        // DB 오픈
        database = TeamDatabase.getInstance(this);
        boolean isOpen = database.open();

        if(isOpen) {
            Log.d(TAG, "데이터베이스 오픈 성공");
        }
        else{
            Log.d(TAG, "데이터베이스 오픈 실패");
        }

        selectData();

        defalutData(defalutselection);
    }

    // 처음 앱 켰을때 나오는거
        private void defalutData(int position) {
        if(dataIds == null || position >= dataIds.length) {
            Log.d(TAG, "잘못된 인덱스 : " + position);
            return;
        }

        int _id = dataIds[position];

        String SQL = "select _id, NAME, HEADCOACH, HOMEGROUD, FOUNDATION, IMAGE_NAME from " +
                database.TABLE_TEAM_INFO + " where _id = " + _id;

        Cursor cursor = database.rawQuery(SQL);
        int count = cursor.getCount();

        Log.d(TAG, "조회한 데이터 개수 : " + count);
        if(count < 1) {
            return;
        }

        cursor.moveToNext();

        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        String headcoach = cursor.getString(2);
        String homeground = cursor.getString(3);
        String foundation  = cursor.getString(4);
        String imageName = cursor.getString(5);

        Log.d(TAG, "_id : " + _id + ", name : " + name + ", headcoach : " + headcoach + ", homeground : " + homeground + ", foundation : " + foundation + ", imageNmae : " + imageName);

        int imageResId = getResources().getIdentifier(imageName, "drawable", "com.example.administrator.educast_silsb4.app");

        Log.d(TAG, "이미지 리소스 ID : " + imageResId);

        teamImg.setImageResource(imageResId);
        team_name.setText(name);
        head_coach.setText(headcoach);
        home_ground.setText(homeground);
        establish.setText(foundation);
    }

    // 종료 시 함수.
    @Override
    protected void onDestroy() {

        // null이 아니면 DB close를 반드시 해줘야 한다.
        if(database != null) {
            database.close();
            database = null;
        }

        super.onDestroy();
    }

    private void selectedItemFromDatabase(int position) {
        if(dataIds == null || position >= dataIds.length) {
            Log.d(TAG, "잘못된 인덱스 : " + position);
            return;
        }

        int _id = dataIds[position];

        String SQL = "select _id, NAME, HEADCOACH, HOMEGROUD, FOUNDATION, IMAGE_NAME from " +
                database.TABLE_TEAM_INFO + " where _id = " + _id;

        Cursor cursor = database.rawQuery(SQL);
        int count = cursor.getCount();

        Log.d(TAG, "조회한 데이터 개수 : " + count);
        if(count < 1) {
            return;
        }

        cursor.moveToNext();

        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        String headcoach = cursor.getString(2);
        String homeground = cursor.getString(3);
        String foundation  = cursor.getString(4);
        String imageName = cursor.getString(5);

        Log.d(TAG, "_id : " + _id + ", name : " + name + ", headcoach : " + headcoach + ", homeground : " + homeground + ", foundation : " + foundation + ", imageNmae : " + imageName);

        int imageResId = getResources().getIdentifier(imageName, "drawable", "com.example.administrator.educast_silsb4.app");

        Log.d(TAG, "이미지 리소스 ID : " + imageResId);

        teamImg.setImageResource(imageResId);
        team_name.setText(name);
        head_coach.setText(headcoach);
        home_ground.setText(homeground);
        establish.setText(foundation);
    }

    private void selectData() {
        String SQL = "select _id, NAME, HEADCOACH, HOMEGROUD, FOUNDATION, IMAGE_NAME from "
                + database.TABLE_TEAM_INFO;
        Cursor cursor = database.rawQuery(SQL);
        if(cursor == null) {
            Log.d(TAG, "ddzasd");
        }

        Log.d(TAG, " dd " + cursor.getString(1));
        Log.d(TAG, "? " + cursor.getCount());
        dataCount = cursor.getCount(); // 레코드의 개수
        Log.d(TAG, "데이터 갯수 : " + dataCount);
        if(dataCount < 1) {
            return;
        }

        int cursorIdx = 0; // 인덱스
        Integer[] imageIds = new Integer[dataCount];
        dataIds = new int[dataCount];

        while (cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            String name = cursor.getString(1);
            String headcoach = cursor.getString(2);
            String homeground = cursor.getString(3);
            String foundation = cursor.getString(4);
            String imageName = cursor.getString(5);
            Log.d(TAG, "_id : " + _id + ", name : " + name + ", headcoach : " + headcoach + ", homeground : " + homeground + ", foundation : " + foundation+ ", imageNmae : " + imageName);

            int imageResId = getResources().getIdentifier(imageName, "drawable", "com.example.administrator.educast_silsb4.app");

             Log.d(TAG, "이미지 리소스 ID : " + imageResId);

            dataIds[cursorIdx] = _id;
            imageIds[cursorIdx] = new Integer(imageResId);
            cursorIdx++;
        }

        imgAdapter.setImageIds(imageIds);
        imgAdapter.notifyDataSetChanged();
    }
    // 어댑터 클래스
    public class ImageAdapter extends BaseAdapter {

        private Context mContext;

        private Integer[] mImageIds; // 어차피 setImageIds 함수를 통해 설정하므로
        // private ImageView[] mImages; // 이미지 뷰 배열.

        public ImageAdapter(Context context) {
            mContext = context;
           // mImages = new ImageView[mImageIds.length];
        }

        public void setImageIds(Integer[] Ids) {
            mImageIds = Ids;
        }

        public int getCount() {
            if(mImageIds == null) {
                return 0;
            }

            return mImageIds.length;
         //   return mImages.length;
        }

        public Object getItem(int position) {
            if(mImageIds == null) {
                return null;
            }
            if(position < 0 || position >= mImageIds.length) {
                return null;
            }
            return mImageIds[position];
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            ImageView imgV = null;

            if(convertView == null) {
                imgV = new ImageView(mContext);
            }
            else {
                imgV = (ImageView) convertView;
            }

            //ImageView imgV = new ImageView(mContext);
            imgV.setImageResource(mImageIds[position]);
            imgV.setLayoutParams(new CoverFlow.LayoutParams(500, 280));
            imgV.setScaleType(ImageView.ScaleType.CENTER_INSIDE); // 안쪽으로 보이게.

            BitmapDrawable drawable = (BitmapDrawable)imgV.getDrawable();
            drawable.setAntiAlias(true);

            return imgV;
        }
    }
}