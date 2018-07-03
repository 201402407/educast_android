package com.example.administrator.educast_silsb3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class MyView extends View {
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mPaint;

    public MyView(Context context) {
        super(context);

        mPaint = new Paint();
    }

    // View안에 이미 존재.
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        createMemoryBitmap(w, h);
        mDrawing();
    }

    // 메모리랑 Canvas랑 연결(생성)
    private  void createMemoryBitmap(int w, int h) {
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888); // width, height, Config(설정) 기본적으로 8888
        mCanvas = new Canvas();
        mCanvas.setBitmap(mBitmap);
    }

    // 실제 그림
    private void mDrawing() {
        mCanvas.drawColor(Color.WHITE);
        mPaint.setColor(Color.BLUE);
        // 사각형 그리기
        mCanvas.drawRect(200, 200, 400, 400, mPaint);
    }

    // 실제 화면에 그리는 캔버스. 더블 버퍼링 방법 : 메모리 상에 이미지를 그리고 실제 화면에 전달.
    protected  void onDraw(Canvas canvas) {
        if(mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
    }
}
