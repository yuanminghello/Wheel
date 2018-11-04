package com.test.yuan.newwheel;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * date:2018/11/04
 * author:袁明磊(123)
 * function:
 */
public class LuckPan extends SurfaceViewTemp {
     int mSpeed = 0;
     boolean isShouldEnd;

    int mRadius;
    Paint mArcPaint;
    Paint mTextPaint;
    RectF mRange;

    //字体的大小
    private float mTextSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics());

    //抽奖的项目
    private String[] mStrs = new String[] { "单反相机", "IPAD", "恭喜发财", "IPHONE",
            "妹子一只", "恭喜发财" };

   //字体颜色（没有体现）
    private int [] mColors = new int[] { 0xFFFFC300, 0xFFF17E01, 0xFFFFC300,
            0xFFF17E01, 0xFFFFC300, 0xFFF17E01};

    //抽奖时的图片效果图
    private int [] mBitmaps = new int[]{R.drawable.danfan, R.drawable.ipad, R.drawable.f015, R.drawable.iphone, R.drawable.meizi, R.drawable.f040};

    //工厂 将项目数量
    private Bitmap[] mImgs;

    //项目数量
    private int mItemCount = 6;

    //构造函数
    public LuckPan(Context context) {
        super(context);
    }
    //构造函数
    public LuckPan(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
     //开始角度
    float mStartAngle = 0;
    //画
    public void draw() {
        try {
            //锁定画纸
            mCanvas = mHolder.lockCanvas();
            //不为空画纸，就开始操作
            if (mCanvas != null) {
                //先画背景
                drawBg();
                //画arc
//               drawArc();
                //开始角度
                float tmpAngle = mStartAngle;

                mCanvas.save();
                mCanvas.translate(mCenter, mCenter);

                RectF r = new RectF(-1 * mRadius/2, -1 * mRadius/2, mRadius/2 , mRadius/2);
                //循环留个项目的完成
                for(int i = 0; i < mItemCount; i ++){
                    mArcPaint.setColor(mColors[i]);
                    mCanvas.drawArc(r, tmpAngle,  60, true, mArcPaint);
                    drawText2(tmpAngle, 60, mStrs[i]);
                    drawIcon2(tmpAngle, i);
                    mCanvas.rotate(60);
                }

                //恢复复原
                mCanvas.restore();

                //角度=角度+速度
                mStartAngle += mSpeed;
                //如果开始旋转 速度一直 - 1
                if (isShouldEnd){
                    mSpeed -= 1;
                }
                //当速度 < 0 时   ==0   停止
                if (mSpeed < 0){
                    mSpeed = 0;
                    isShouldEnd = false;
                }

//                calInExactArea(mStartAngle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null){
                //不锁定然后发布
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        super.surfaceCreated(holder);

        //画弧的笔
        mArcPaint = new Paint();
        //齿轮
        mArcPaint.setAntiAlias(true);
        //犹豫程度
        mArcPaint.setDither(true);
        //浮点矩形   Rect 矩形   区间Range
        mRange = new RectF(getPaddingLeft(), getPaddingLeft(), mRadius + getPaddingLeft(), mRadius + getPaddingLeft());

        //画文字的笔   大小  颜色
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(Color.WHITE);

        //工厂  位图
        mImgs = new Bitmap[mItemCount];
        for(int i = 0; i < mItemCount; i ++){
            //图片的分布
            mImgs[i]= BitmapFactory.decodeResource(getResources(), mBitmaps[i]);
        }
    }

    //中心点
    int mCenter;
    //边距
    int mPadding;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
         //获取测量整个大圆的宽和高 +（边距）
        int width = Math.min(getMeasuredHeight(), getMeasuredWidth());
        //直径 ：要旋转的位置-左右边距
        mRadius = width - getPaddingLeft() - getPaddingRight();
        //中心点占据的位置  半径=直径/2
        mCenter = width/2;
        //边距=指针始终要划过的距离
        mPadding = getPaddingLeft();
        //设置尺寸  宽+高
        setMeasuredDimension(width, width);
    }

    //背景图片
    Bitmap mBgBitmap;
    //画背景
    public void drawBg(){
        //获取背景图片
        mBgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg2);
        //颜色
        mCanvas.drawColor(0XFFFFFFFF);
        //画背景
        mCanvas.drawBitmap(mBgBitmap, null, new Rect(mPadding /2, mPadding /2 ,
                getMeasuredWidth() - mPadding/2, getMeasuredHeight() - mPadding /2), null);

    }
  //静止时
    public void drawText(float startAngle, float item, String title){
        Path  path = new Path();
        path.addArc(mRange, startAngle, item);
        float textWidth = mTextPaint.measureText(title);

        float h = (float) (Math.PI * mRadius /mItemCount/2 - textWidth/2);
        float v = mPadding * 3/2;
        mCanvas.drawTextOnPath(title, path, h, v, mTextPaint);
    }
    //旋转的方式得到
    public void drawText2(float startAngle, float gapAngle, String title){

        RectF r = new RectF(-1 * mRadius/2, -1 * mRadius/2, mRadius/2, mRadius/2);

        Path p = new Path();
        p.addArc(r, startAngle , gapAngle);

        float textWidth = mTextPaint.measureText(title);

        float hoffset = (float) (Math.PI * mRadius /mItemCount/2 - textWidth/2);
        float voffset = mPadding * 3/2;
        mCanvas.drawTextOnPath(title, p, hoffset, voffset, mTextPaint);

    }

    public void drawIcon(float startAngle, int i){
        int imgWidth = mRadius/2/4;
        float angle = (float)( (30 + startAngle) * (Math.PI / 180));

        int x = (int)(mCenter + mRadius /2 /2 * Math.cos(angle));
        int y = (int)( mCenter + mRadius /2/2 * Math.sin(angle));

        Rect rect = new Rect(x - imgWidth/2, y - imgWidth/2, x+ imgWidth/2, y + imgWidth/2);

        mCanvas.drawBitmap(mImgs[i], null, rect, null);

    }

    public void drawIcon2(float startAngle, int i){
        int imgWidth = mRadius/2/4;
        float angle = (float) ((mStartAngle + 30) * (Math.PI/180));
        int x = (int) (mRadius/4 * Math.cos(angle));
        int y = (int) (mRadius/4 * Math.sin(angle));
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mBitmaps[i]);
        Rect r = new Rect(x - imgWidth/2, y - imgWidth/2, x + imgWidth/2 , y+imgWidth/2);
        mCanvas.drawBitmap(bitmap, null, r, null);
    }

   //获取旋转 系统事件的  开始和结束  决定是否旋转
    @Override
    public void run() {
        while(isDrawing) {
            long start = System.currentTimeMillis();
            draw();
            long end = System.currentTimeMillis();
            try {
                if ((end - start)< 50){
                    Thread.sleep( 50 - (end-start));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }





    public boolean isStart() {
        return mSpeed != 0;
    }

    public boolean isShouldEnd() {
        return isShouldEnd;
    }
}
