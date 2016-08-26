package com.jewelbao.customview.Anim;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import com.jewelbao.customview.R;
import com.jewelbao.customview.utils.BitmapUtils;

/**
 * Created by Administrator on 2016/2/24 0024.
 * com.jewelbao.customview.Anim 
 * JewelSample
 */
public class AnimView1 extends View
{

    /********************************
     * 渐显变量
     ***************************************/

    private Paint mBgPaint; // 背景画笔
    private Paint mFadeViewPaint; // 动画图片画笔

    private Bitmap mLine1, mLine2, mLine3, mLine4; //　四张要执行动画的图片
    private int animCurrent = 1; // 当前执行动画的图片
    private int[] animLine = new int[4]; // 开启动画的状态，对应上面图片

    private int delta5, delta10, delta25, delta30, delta35, delta40; // 变量值

    /*********************************
     * 旋转变量
     ****************************************/

    private Paint mRotationPaint;
    private Path mAnimPath; // 动画路径
    private PathMeasure mPathMeasure; // 路径测量

    private Bitmap mRotationBm1, mRotationBm2; // 圆形图片
    private float mBmOffsetX1, mBmOffsetX2, mBmOffsetY1, mBmOffsetY2;

    private float mDistance; // 图片移动的距离
    private float mScale; // 图片缩放系数(0,1)
    private boolean scaleLarger; // true放大/false缩小
    private float mPathLength;  // 路径的长度

    private float[] mPos; // 当前路径所在位置
    private float[] mTan;  // 当前路径所在正切
    private Matrix matrix;


    public AnimView1 (Context context)
    {
        super(context);
        initView();
    }

    public AnimView1 (Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initView();
    }

    public AnimView1 (Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView ()
    {
        initFadeViewData();
        initRotationViewData();
    }

    @Override
    protected void onDraw (Canvas canvas)
    {
        super.onDraw(canvas);
        drawFadeView(canvas);
        drawRotationView(canvas);
    }

    /**************************************************************/

    /**
     * 初始化旋转View基本数据
     */
    private void initRotationViewData ()
    {
        mRotationPaint = new Paint();
        mRotationPaint.setColor(Color.BLUE);
        mRotationPaint.setStrokeWidth(1);
        mRotationPaint.setStyle(Paint.Style.STROKE);
        // 设置间隔线效果
        DashPathEffect dashEffect = new DashPathEffect(new float[]{5, 5}, 1f);
        mRotationPaint.setPathEffect(dashEffect);

        mRotationBm1 = BitmapUtils.getCircularBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.yellow_square));
        mBmOffsetX1 = mRotationBm1.getWidth() / 2;
        mBmOffsetY1 = mRotationBm1.getHeight() / 2;

        mRotationBm2 = BitmapUtils.getCircularBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.blue_square));
        mBmOffsetX2 = mRotationBm2.getWidth() / 2;
        mBmOffsetY2 = mRotationBm2.getHeight() / 2;

        mDistance = 0;
        mScale = 1;
        scaleLarger = false;
        mPos = new float[2];
        mTan = new float[2];
        matrix = new Matrix();
    }

    private void drawRotationView (Canvas canvas)
    {
        if (mAnimPath == null)
        {
            mAnimPath = new Path();
            mAnimPath.addCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - mBmOffsetX1, Path.Direction.CW); // 新增矩形内的椭圆路径
            mAnimPath.close(); // 闭环

            mPathMeasure = new PathMeasure(mAnimPath, false); // 根据上面的路径获取测量对象
            mPathLength = mPathMeasure.getLength();
        }


        canvas.drawPath(mAnimPath, mRotationPaint);
        mDistance += 10;

        if (mDistance < mPathLength)
        {
            mPathMeasure.getPosTan(mDistance, mPos, mTan); // 计算并获取Pos和Tan
            matrix.reset();
            if (scaleLarger)
            {
                mScale += 0.05;
                if (mScale >= 1)
                {
                    scaleLarger = !scaleLarger;
                }
            } else
            {
                mScale -= 0.05;
                if (mScale <= 0)
                {
                    scaleLarger = !scaleLarger;
                }
            }
            matrix.postScale(mScale, mScale);
            matrix.postTranslate(mPos[0] - mBmOffsetX1 * mScale, mPos[1] - mBmOffsetY1 * mScale); // 乘以mScale是为了保证图片中心点在圆形Path上
            canvas.drawBitmap(mRotationBm1, matrix, null);

            matrix.reset();
            if (mDistance + mPathLength / 2 <= mPathLength)
            {
                mPathMeasure.getPosTan(mDistance + mPathLength / 2, mPos, mTan); // 计算并获取Pos和Tan
            } else
            {
                mPathMeasure.getPosTan(mDistance - mPathLength / 2, mPos, mTan); // 计算并获取Pos和Tan
            }
            matrix.postTranslate(mPos[0] - mBmOffsetX2, mPos[1] - mBmOffsetY2);
            canvas.drawBitmap(mRotationBm2, matrix, null);
        } else
        {
            mDistance = 0;
            canvas.drawBitmap(mRotationBm1, matrix, null);
            canvas.drawBitmap(mRotationBm2, matrix, null);
        }

        invalidate();
    }

    /**************************************************************/

    /**
     * 初始化渐显View基本数据
     */
    private void initFadeViewData ()
    {
        mBgPaint = new Paint();
        mBgPaint.setStrokeWidth(2);

        delta5 = (int) getResources().getDimension(R.dimen.dimen_5);
        delta10 = (int) getResources().getDimension(R.dimen.dimen_10);
        delta25 = (int) getResources().getDimension(R.dimen.dimen_25);
        delta30 = (int) getResources().getDimension(R.dimen.dimen_30);
        delta35 = (int) getResources().getDimension(R.dimen.dimen_35);
        delta40 = (int) getResources().getDimension(R.dimen.dimen_40);


        mFadeViewPaint = new Paint();
        mFadeViewPaint.setAlpha(0);

        mLine1 = BitmapFactory.decodeResource(getResources(), R.mipmap.blue_square);
        mLine2 = BitmapFactory.decodeResource(getResources(), R.mipmap.dark_green_square);
        mLine3 = BitmapFactory.decodeResource(getResources(), R.mipmap.pink_square);
        mLine4 = BitmapFactory.decodeResource(getResources(), R.mipmap.yellow_square);

        for (int i = 0; i < animLine.length; i++)
        {
            animLine[i] = i + 1;
        }
    }

    // 绘制渐显View
    private void drawFadeView (Canvas canvas)
    {
        // 画个背景图
        mBgPaint.setColor(Color.BLUE);
        mBgPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(delta5, delta5, getWidth() - delta5, getHeight() - delta5, mBgPaint);

        // 画个前景图只描红边
        mBgPaint.setStyle(Paint.Style.STROKE);
        mBgPaint.setColor(Color.RED);
        canvas.drawRect(delta10, delta10, getWidth() - delta10, getHeight() - delta10, mBgPaint);

        if (animLine[0] == animCurrent)
        { // 执行第1个图片
            canvas.drawBitmap(mLine1, getWidth() / 2 - mLine1.getWidth() / 2, delta10, mFadeViewPaint);
            fade(mFadeViewPaint, animLine[1]);
        }

        if (animLine[1] == animCurrent)
        { // 执行第2个图片
            canvas.drawBitmap(mLine1, getWidth() / 2 - mLine1.getWidth() / 2, delta10, null);
            canvas.drawBitmap(mLine2, getWidth() / 2 - mLine1.getWidth() / 2, mLine1.getHeight() + delta10, mFadeViewPaint);
            fade(mFadeViewPaint, animLine[2]);
        }

        if (animLine[2] == animCurrent)
        { // 执行第3个图片
            canvas.drawBitmap(mLine1, getWidth() / 2 - mLine1.getWidth() / 2, delta10, null);
            canvas.drawBitmap(mLine2, getWidth() / 2 - mLine1.getWidth() / 2, mLine1.getHeight() + delta10, null);
            canvas.drawBitmap(mLine3, getWidth() / 2 - mLine1.getWidth() / 2, mLine1.getHeight() + mLine2.getHeight() + delta10, mFadeViewPaint);
            fade(mFadeViewPaint, animLine[3]);
        }

        if (animLine[3] == animCurrent)
        { // 执行第4个图片
            canvas.drawBitmap(mLine1, getWidth() / 2 - mLine1.getWidth() / 2, delta10, null);
            canvas.drawBitmap(mLine2, getWidth() / 2 - mLine1.getWidth() / 2, mLine1.getHeight() + delta10, null);
            canvas.drawBitmap(mLine3, getWidth() / 2 - mLine1.getWidth() / 2, mLine1.getHeight() + mLine2.getHeight() + delta10, null);
            canvas.drawBitmap(mLine4, getWidth() / 2 - mLine1.getWidth() / 2, mLine1.getHeight() + mLine2.getHeight() + mLine3.getHeight() + delta10, mFadeViewPaint);
            fade(mFadeViewPaint, animLine[0]);
        }
    }

    /**
     * 自动执行渐显动画
     *
     * @param paint
     * @param nextAnim
     */
    private void fade (Paint paint, int nextAnim)
    {
        if (paint.getAlpha() != 255)
        {
            int newAlpha = paint.getAlpha() + 5;
            if (newAlpha >= 255)
            {
                newAlpha = 255;
            }

            paint.setAlpha(newAlpha);
        } else
        {
            paint.setAlpha(0);
//            if (nextAnim != animLine[0]) // 如果执行到最后一个图片，则不再重新执行，如果想要一直循环执行，去掉此判断即可
//            {
                animCurrent = nextAnim;
//            }
        }

        invalidate();
    }
}
