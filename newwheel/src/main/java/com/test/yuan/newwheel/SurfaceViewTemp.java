package com.test.yuan.newwheel;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * date:2018/11/04
 * author:袁明磊(123)
 * function:
 */
public class SurfaceViewTemp extends SurfaceView implements SurfaceHolder.Callback,Runnable {


    SurfaceHolder mHolder;
    boolean isDrawing;
    Canvas mCanvas;

    public SurfaceViewTemp(Context context) {
        this(context,null);
    }

    public SurfaceViewTemp(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SurfaceViewTemp(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mHolder = getHolder();
        mHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        isDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void run() {

    }
}
