package com.jewelbao.customview.Anim;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import com.jewelbao.customview.R;
import com.jewelbao.customview.utils.BitmapUtils;

/**
 Created by Administrator on 2016/2/19 0019. com.jewelbao.customview.Progress  JewelSample
 */
@Deprecated
public class ShapeLoadingView extends View
{
	private Context context;
	private Paint mPaint;

	private Path mCirclePath;
	private PathMeasure mPathMeasure;
	private Matrix matrix;
	private float pathLength;

	private float[] mPos, mTan; // 当前位置坐标和正切坐标

	private Bitmap[] bitmap;
	private float[] mShapeOriginalCircleDistance; // 每张图片在圆形路径上的初始距离
	private float[][] mShapeCurrentPos; // 每张图片的当前坐标
	private float[] mShapeStepArr; //
	private float[] mShapeDistanceArr; // 每张图片的移动距离
	private boolean[] mShapeRotationOver; // 每张图片是否旋转了一圈
	private int bm_offsetX, bm_offsetY;

	private Path[] mOutPath; // sin路径集合
	private PathMeasure[] mOutPathMeasure; // sin路径测量集合
	//    private Matrix[] mOutPathMatrix; // sin Matrix集合
	private float[] mOutPathLength; // sin路径长度集合
	private float[] mOutShapeDistanceArr; // 每张图片在sin路径上的初始距离
	//    private boolean[] mOutShapeAnimInit; // 是否已经初始化


	public ShapeLoadingView(Context context)
	{
		super(context);
		this.context = context;
		init();
	}

	public ShapeLoadingView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.context = context;
		init();
	}

	public ShapeLoadingView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		this.context = context;
		init();
	}

	private void init()
	{
		mPaint = new Paint();
		mPaint.setColor(Color.TRANSPARENT); // 画笔透明
		mPaint.setAntiAlias(false); // 设置是否使用抗锯齿功能，会消耗较大资源，绘制图形速度会变慢。
		mPaint.setDither(false); // 设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
		mPaint.setStrokeWidth(1); // 描边宽度
		mPaint.setStyle(Paint.Style.STROKE); // 画笔使用描边

		bitmap = new Bitmap[6];
		bitmap[0] = BitmapUtils.getCircularBitmap(
				BitmapFactory.decodeResource(getResources(), R.mipmap.yellow_square));
		bitmap[1] = BitmapUtils.getCircularBitmap(
				BitmapFactory.decodeResource(getResources(), R.mipmap.blue_square));
		bitmap[2] = BitmapUtils.getCircularBitmap(
				BitmapFactory.decodeResource(getResources(), R.mipmap.dark_green_square));
		bitmap[3] = BitmapUtils.getCircularBitmap(
				BitmapFactory.decodeResource(getResources(), R.mipmap.dark_yellow_square));
		bitmap[4] = BitmapUtils.getCircularBitmap(
				BitmapFactory.decodeResource(getResources(), R.mipmap.light_green_square));
		bitmap[5] = BitmapUtils.getCircularBitmap(
				BitmapFactory.decodeResource(getResources(), R.mipmap.pink_square));

		bm_offsetX = bitmap[1].getWidth() / 2;
		bm_offsetY = bitmap[1].getHeight() / 2;

		//        mShapeStep = 1;
		//        mDistance = 0;
		mPos = new float[2];
		mTan = new float[2];

		mShapeOriginalCircleDistance = new float[6];
		mShapeStepArr = new float[6];
		mShapeCurrentPos = new float[6][2];
		mShapeDistanceArr = new float[6];
		mShapeRotationOver = new boolean[6];
		//        mOutShapeAnimInit = new boolean[6];

		mOutPath = new Path[6];
		mOutPathMeasure = new PathMeasure[6];
		//        mOutPathMatrix = new Matrix[6];
		mOutPathLength = new float[6];
		mOutShapeDistanceArr = new float[6];

		matrix = new Matrix();

		setMinimumWidth((int) getResources().getDimension(R.dimen.dimen_120));
		setMinimumHeight((int) getResources().getDimension(R.dimen.dimen_120));
	}

	private void initShapeOriginalPos(float pathMeasureLength)
	{
		for(int i = 0; i < 6; i++)
		{ // 初始化每个图片的起始距离(均匀分布)
			mShapeOriginalCircleDistance[i] = (i * pathMeasureLength) / 6;
			mShapeStepArr[i] = 0f;
			mShapeDistanceArr[i] = 0f;
			mShapeRotationOver[i] = false;
			//            mOutShapeAnimInit[i] = false;
		}
	}

	private void initRotationPath()
	{
		mCirclePath = new Path();
		mCirclePath.addCircle(getWidth() / 2, getHeight() / 2, getWidth() / 4, Path.Direction.CW);
		mCirclePath.close();

		mPathMeasure = new PathMeasure(mCirclePath, true);
		pathLength = mPathMeasure.getLength();

		for(int i = 0; i < 6; i++)
		{
			mOutShapeDistanceArr[i] = 0f;
		}
	}

	private void initOutPath(int i)
	{
		mOutPath[i] = new Path();
		mOutPathMeasure[i] = new PathMeasure();
		mOutPath[i].moveTo(mShapeCurrentPos[i][0], mShapeCurrentPos[i][1]);
		mOutPath[i].lineTo(getWidth() / 2, getHeight() / 2);
		mOutPath[i].close();
		mOutPathMeasure[i].setPath(mOutPath[i], false);
		mOutPathLength[i] = mOutPathMeasure[i].getLength();
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		if(getVisibility() == GONE)
		{
			return;
		}

		if(mCirclePath == null)
		{
			initRotationPath();
			initShapeOriginalPos(pathLength);
		}

		canvas.drawPath(mCirclePath, mPaint);

		runAnim(canvas, 0, bitmap[0]);
		runAnim(canvas, 1, bitmap[1]);
		runAnim(canvas, 2, bitmap[2]);
		runAnim(canvas, 3, bitmap[3]);
		runAnim(canvas, 4, bitmap[4]);
		runAnim(canvas, 5, bitmap[5]);

		invalidate();
	}

	private void runAnim(Canvas canvas, int i, Bitmap bitmap)
	{
		if(!mShapeRotationOver[i])
		{
			// 如果所有图片没有旋转一圈，则更新
			mShapeCurrentPos[i] = drawShape(canvas, i, bitmap);
		} else
		{
			if(mOutPath[i] == null)
			{
				initOutPath(i);
			}
			drawOutShape(canvas, i, bitmap);
		}
	}

	/**
	 返回每张图片的当前坐标

	 @param canvas
	 @param i      图片存储位置
	 @param bitmap 图片

	 @return 当前坐标
	 */
	private float[] drawShape(Canvas canvas, int i, Bitmap bitmap)
	{
		float distance = mShapeOriginalCircleDistance[i] + mShapeStepArr[i];
		float posArray[] = new float[2]; // 坐标
		if(distance <= pathLength)
		{
			mShapeStepArr[i] += 30f;
			mShapeDistanceArr[i] += 30f;
		} else
		{
			distance = 0;
			mShapeOriginalCircleDistance[i] = 0f;
			mShapeStepArr[i] = 0f;
		}

		if(mShapeDistanceArr[i] >= pathLength)
		{ // 如果图片跑完了一圈，则标志旋转动画停止
			mShapeRotationOver[i] = true;
			mShapeDistanceArr[i] = 0f;
		}

		mPathMeasure.getPosTan(distance, mPos, mTan);

		matrix.reset();
		matrix.postTranslate(mPos[0] - bm_offsetX, mPos[1] - bm_offsetY);
		canvas.drawBitmap(bitmap, matrix, null);

		posArray[0] = mPos[0];
		posArray[1] = mPos[1];

		return posArray;
	}

	private void drawOutShape(Canvas canvas, int i, Bitmap bitmap)
	{
		float distance = mOutShapeDistanceArr[i] + 20f;
		if(distance < mOutPathLength[i])
		{
			mOutShapeDistanceArr[i] = distance;
		} else
		{
			mOutShapeDistanceArr[i] = 0f;
			mShapeRotationOver[i] = false;
		}
		mOutPathMeasure[i].getPosTan(distance, mPos, mTan);
		matrix.reset();
		matrix.postTranslate(mPos[0] - bm_offsetX, mPos[1] - bm_offsetY);
		canvas.drawBitmap(bitmap, matrix, null);
	}

	WindowManager window;
	WindowManager.LayoutParams params;
	boolean isShown = false;

	private void getWindowManager()
	{
		window = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		params = new WindowManager.LayoutParams();
		params.width = WindowManager.LayoutParams.MATCH_PARENT;
		params.height = WindowManager.LayoutParams.MATCH_PARENT;
		params.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
		params.type = WindowManager.LayoutParams.TYPE_TOAST;
		params.format = PixelFormat.TRANSPARENT;
	}

	public void show()
	{
		if(window == null)
		{
			getWindowManager();
		}
		if(!isShown)
		{
			window.addView(this, params);
		}
		isShown = true;
	}

	public void dismiss()
	{
		if(isShown)
		{
			window.removeView(this);
		}
		isShown = false;
	}

	public boolean isShow()
	{
		return isShown;
	}
}
