package com.test.yuan.image;

import android.graphics.Rect;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private ImageView iv;
    private View rootView;
    private int actionBarHeight = 0;
    private int notifiHeight;

    private ImageView moveIv;

    private float difX;
    private float difY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootView = getLayoutInflater().inflate(R.layout.activity_main,null);//获取根布局
        rootView.post(new Runnable() {
            @Override
            public void run() {
                if(getSupportActionBar()!=null){
                    //获取actionBar的高度
                    actionBarHeight = getSupportActionBar().getHeight();
                    //获取通知栏的高度
                    Rect rect = new Rect();
                    getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                    notifiHeight = rect.top;
                    System.out.println("height:"+(actionBarHeight+notifiHeight));
                }else{
                    System.out.println("view has not a actionBar");
                }
            }
        });
        iv = (ImageView)findViewById(R.id.image);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionTag = MotionEventCompat.getActionMasked(event);
        int pointIndex = MotionEventCompat.getActionIndex(event);
        switch (actionTag){
            case MotionEvent.ACTION_DOWN:
                if(isPointInPic(event,pointIndex)){
                    moveIv = iv;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(moveIv!=null){
                    LinearLayout.LayoutParams llpm = (LinearLayout.LayoutParams)iv.getLayoutParams();
                    llpm.leftMargin = (int)(getTouchXPoint(event,pointIndex)-difX);  //左边距
                    llpm.topMargin = (int)(getTouchYPoint(event,pointIndex)-difY);//上边距
                    iv.setLayoutParams(llpm);
                }
                break;
            case MotionEvent.ACTION_UP:
                moveIv = null;
                break;
        }
        return super.onTouchEvent(event);
    }
    //返回触摸点的x坐标
    private float getTouchXPoint(MotionEvent event,int pointIndex){
        return MotionEventCompat.getX(event, pointIndex);
    }
    //返回触摸点的y坐标
    private float getTouchYPoint(MotionEvent event,int pointIndex){
        return MotionEventCompat.getY(event, pointIndex);
    }
    private boolean isPointInPic(MotionEvent event,int pointIndex){
        int x = (int)getTouchXPoint(event,pointIndex);
        int y = (int)getTouchYPoint(event,pointIndex);
        //图片初始位置
        LinearLayout.LayoutParams llp = (LinearLayout.LayoutParams)iv.getLayoutParams();
        int ivWidth = iv.getWidth();
        int ivHeight = iv.getHeight();
        int relx = x;
        int rely = (int)getTouchYPoint(event,pointIndex)-actionBarHeight-notifiHeight;
        //计算D点和C点的偏移
        difX = x-llp.leftMargin;
        difY = y-llp.topMargin;
        //判断点击的点是否在图片的范围内
        if((relx>llp.leftMargin)&&(relx<llp.leftMargin+ivWidth)&&
                (rely>llp.topMargin)&&(rely<llp.topMargin+ivHeight)){
            return true;
        }else{
            return false;
        }
    }
}
