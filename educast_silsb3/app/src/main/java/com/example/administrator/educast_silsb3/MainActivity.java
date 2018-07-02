package com.example.administrator.educast_silsb3;

import android.opengl.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView image01 = (ImageView) findViewById(R.id.img01);
        ImageView image02 = (ImageView) findViewById(R.id.img02);
        ImageView image03 = (ImageView) findViewById(R.id.img03);
        ImageView image04 = (ImageView) findViewById(R.id.img04);
        ImageView image05 = (ImageView) findViewById(R.id.img05);
        ImageView image06 = (ImageView) findViewById(R.id.img06);
        ImageView image07 = (ImageView) findViewById(R.id.img07);
        ImageView image08 = (ImageView) findViewById(R.id.img08);

        image01.setScaleType(ImageView.ScaleType.CENTER); // ImageView의 중앙에 배치.

        // 이미지를 뷰의 크기와 동일하게 하면서 축소/확대(가로세로비율 동일)를 한다. 남으면 자름.
        image02.setScaleType(ImageView.ScaleType.CENTER_CROP);

        // 이미지를 뷰의 크기와 동일하게 하면서 축소/확대(가로세로비율 동일)를 한다. 남으면 줄임.
        image03.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


        image04.setScaleType(ImageView.ScaleType.FIT_CENTER);

        // 우측 하단에 배치
        image05.setScaleType(ImageView.ScaleType.FIT_END);

        // FIT_END와 반대(좌측 상단)
        image06.setScaleType(ImageView.ScaleType.FIT_START);

        // 가로세로 비율을 이미지 뷰의 크기에 맞게 확대/축소 하는 방법.
        image07.setScaleType(ImageView.ScaleType.FIT_XY);


        image08.setScaleType(ImageView.ScaleType.MATRIX);

      //  Matrix matrix = new Matrix();
       // matrix.postRotate(30);

       // image08.setImageMatrix(matrix);
    }
}
