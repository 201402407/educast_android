package com.example.administrator.educast_silsb4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class myCamera extends View {

    Context mContext;
    Bitmap mBitmap;
    Canvas mCanvas;
    Paint paint;

    Bitmap shoes;

    Camera camera = new Camera();
    public myCamera(Context context) {
        super(context);

        init(context);
    }

    public myCamera(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        mContext = context;

        paint = new Paint();

        shoes = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.camera_img1);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas();
        mCanvas.setBitmap(mBitmap);

        mCanvas.drawBitmap(shoes,0, 0, paint);

        camera.save(); // 현재 상태 스택에 저장. camera : 해당 물체를 보는 여러 각도?


        Matrix matrix = new Matrix();
        camera.rotateY(45.0f); // 대상을 바라보는 카메라를 y축을 중심으로 40도 회전시키겠다.
        camera.translate(0,0,500); // translate(x, y, z) -> 인자는 좌표, z는 확대/축소. -가 확대.
        camera.getMatrix(matrix); // camera에 설정 되어있는 matrix를 get하겠다.
        camera.restore();

        Bitmap cShoes = Bitmap.createBitmap(shoes, 0, 0, shoes.getWidth(), shoes.getHeight(), matrix, true);
        mCanvas.drawBitmap(cShoes, 100, 600, paint);

    }
}
