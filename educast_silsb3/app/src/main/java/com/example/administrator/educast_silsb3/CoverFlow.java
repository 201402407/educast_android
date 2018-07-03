package com.example.administrator.educast_silsb3;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.ImageView;

public class CoverFlow extends Gallery {

    private Camera camera = new Camera();
    private int centerPoint;

    public static int maxRotationAngle = 55;
    public static int maxZoom = -60;

    public CoverFlow(Context context) {
        super(context);

        init();
    }

    public  CoverFlow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CoverFlow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init();
    }
    private void init() {
        this.setStaticTransformationsEnabled(true);
    }

    @Override
    protected boolean getChildStaticTransformation(View child, Transformation t) {
        final int childCenter = getCenterOfView(child);
        final int childWidth = child.getWidth();
        int rotationAngle = 0;

        t.clear(); // 초기화 해야 함.

        t.setTransformationType(Transformation.TYPE_MATRIX);

        if(childCenter == centerPoint) { // 같으면 변형시킬 필요가 없음. 한 가운데 있는 그림.
            transformImageBitmap((ImageView)child, t, 0);
        }
        else{
            rotationAngle = (int)(((float)(centerPoint - childCenter) / childWidth) * maxRotationAngle);
            if(Math.abs(rotationAngle) > maxRotationAngle) {
                rotationAngle = (rotationAngle < 0) ? -maxRotationAngle : maxRotationAngle; // 왼쪽은 -, 오른쪽은 +
            }
            transformImageBitmap((ImageView)child, t, rotationAngle);
        }
        // Transformation 이미지 객체 맵
        return true;
    }


    private void transformImageBitmap(ImageView child, Transformation t, int rotationAngle) {
        camera.save();
        final Matrix imageMatrix = t.getMatrix();
        final int imageHeight = child.getLayoutParams().height;
        final int imageWidth = child.getLayoutParams().width;
        final int rotation = Math.abs(rotationAngle);

        camera.translate(0, 0, 100);

        if(rotation < maxRotationAngle) { //
            float zoomAmount = (float)(maxZoom + (rotation * 1.5));
            camera.translate(0, 0, zoomAmount);
        }

        camera.rotateY(rotationAngle);
        camera.getMatrix(imageMatrix);

        // 이미지 이동
        imageMatrix.preTranslate(-(imageWidth / 2), -(imageHeight / 2));
        imageMatrix.postTranslate((imageWidth / 2), (imageHeight / 2));

        camera.restore();
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        centerPoint = getCenterOfCoverflow();

        super.onSizeChanged(w, h, oldw, oldh);
    }

    private static int getCenterOfView(View view) { // 이미지의 센터 기준점 찾는 법
        return view.getLeft() + view.getWidth() / 2;
    }

    // 전체의 중앙 값
    private int getCenterOfCoverflow() {
        return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
    }
}
