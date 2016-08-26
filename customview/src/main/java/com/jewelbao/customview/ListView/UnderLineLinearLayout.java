package com.jewelbao.customview.ListView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.jewelbao.customview.R;

/**
 * Created by Jewel on 2016/6/13 0013.
 * 时间轴View，适用于简单数量的时间轴布局
 */
@SuppressWarnings("unused")
public class UnderLineLinearLayout extends LinearLayout {

	private Bitmap mIcon;

	private int lineMarginSide = 10;
	private int lineDynamicDimen = 0;
	private int lineStrokeWidth = 2;
	private int lineColor = 0xff3dd1a5;

	private int pointSize = 8;
	private int pointColor = 0xff3dd1a5;

	private Paint linePaint;
	private Paint pointPaint;

	// 第一个点的位置
	private int firstX;
	private int firstY;

	// 最后一个点的位置
	private int lastX;
	private int lastY;

	private int orientation = VERTICAL;

	private Context mContext;

	// 画线开关
	private boolean drawLine = true;

	public UnderLineLinearLayout(Context context) {
		this(context, null);
	}

	public UnderLineLinearLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public UnderLineLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.UnderLineLinearLayout);
		lineMarginSide = ta.getDimensionPixelOffset(R.styleable.UnderLineLinearLayout_line_margin_side, lineMarginSide);
		lineDynamicDimen = ta.getDimensionPixelOffset(R.styleable.UnderLineLinearLayout_line_dynamic_dimen, lineDynamicDimen);
		lineStrokeWidth = ta.getDimensionPixelOffset(R.styleable.UnderLineLinearLayout_line_stroke_width, lineStrokeWidth);
		lineColor = ta.getColor(R.styleable.UnderLineLinearLayout_line_color, lineColor);
		pointSize = ta.getDimensionPixelSize(R.styleable.UnderLineLinearLayout_point_size, pointSize);
		pointColor = ta.getDimensionPixelOffset(R.styleable.UnderLineLinearLayout_point_color, pointColor);

		int iconRes = ta.getResourceId(R.styleable.UnderLineLinearLayout_icon_src, R.drawable.ic_expandable_view_plus);
		@SuppressWarnings("deprecation")
		BitmapDrawable temp = (BitmapDrawable) context.getResources().getDrawable(iconRes);
		if(temp != null) mIcon = temp.getBitmap();

		orientation = getOrientation();

		ta.recycle();

		initView(context);
	}

	private void initView(Context context) {
		this.mContext = context;

		linePaint = new Paint();
		linePaint.setAntiAlias(true);
		linePaint.setDither(true);
		linePaint.setColor(lineColor);
		linePaint.setStrokeWidth(lineStrokeWidth);
		linePaint.setStyle(Paint.Style.FILL_AND_STROKE);

		pointPaint = new Paint();
		pointPaint.setAntiAlias(true);
		pointPaint.setDither(true);
		pointPaint.setColor(pointColor);
		pointPaint.setStyle(Paint.Style.FILL);
	}

	@Override
	public void setOrientation(int orientation) {
		super.setOrientation(orientation);
		this.orientation = orientation;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(drawLine) {
			drawTimeLine(canvas);
		}
	}

	private void drawTimeLine(Canvas canvas) {
		int childCount = getChildCount();

		if(childCount > 0) {

			if(childCount > 1) { //大于1，证明至少有2个，也就是第一个和第二个之间连成线，第一个和最后一个分别有点/icon
				switch(orientation) {
					case VERTICAL:
						drawFirstChildViewVertical(canvas);
						drawLastChildViewVertical(canvas);
						drawBetweenLineVertical(canvas);
						break;
					case HORIZONTAL:
						drawFirstChildViewHorizontal(canvas);
						drawLastChildViewHorizontal(canvas);
						drawBetweenLineHorizontal(canvas);
						break;
					default:
						break;
				}
			} else if(childCount == 1) {
				switch(orientation) {
					case VERTICAL:
						drawFirstChildViewVertical(canvas);
						break;
					case HORIZONTAL:
						drawFirstChildViewHorizontal(canvas);
						break;
					default:
						break;
				}
			}
		}
	}

	private void drawFirstChildViewVertical(Canvas canvas) {
		View firstView = getChildAt(0);
		if(firstView != null) {
			int top = firstView.getTop();
			// 记录值
			firstX = lineMarginSide;
			firstY = top + firstView.getPaddingTop() + lineDynamicDimen;
			// 画圆
			canvas.drawCircle(firstX, firstY, pointSize, pointPaint);
		}
	}

	private void drawLastChildViewVertical(Canvas canvas) {
		View lastView = getChildAt(getChildCount() - 1);
		if(lastView != null) {
			int top = lastView.getTop();
			// 记录值
			lastX = lineMarginSide - (mIcon.getWidth() >> 1);
			lastY = top + lastView.getPaddingTop() + lineDynamicDimen;
			// 画图
			canvas.drawBitmap(mIcon, lastX, lastY, null);
		}
	}

	private void drawBetweenLineVertical(Canvas canvas) {
		for(int i = 0; i < getChildCount() - 1; i++) {
			// 画线
			canvas.drawLine(lineMarginSide, firstY, lineMarginSide, lastY, linePaint);
			// 画圆
			if(getChildAt(i) != null && i != 0) {
				int top = getChildAt(i).getTop();
				// 记录值
				int Y = top + getChildAt(i).getPaddingTop() + lineDynamicDimen;
				canvas.drawCircle(lineMarginSide, Y, pointSize, pointPaint);
			}
		}
	}

	private void drawFirstChildViewHorizontal(Canvas canvas) {
		if(getChildAt(0) != null) {
			int left = getChildAt(0).getLeft();
			//记录值
			firstX = left + getChildAt(0).getPaddingLeft() + lineDynamicDimen;
			firstY = lineMarginSide;
			//画一个圆
			canvas.drawCircle(firstX, firstY, pointSize, pointPaint);
		}
	}

	private void drawLastChildViewHorizontal(Canvas canvas) {
		if(getChildAt(getChildCount() - 1) != null) {
			int left = getChildAt(getChildCount() - 1).getLeft();
			//记录值
			lastX = left + getChildAt(getChildCount() - 1).getPaddingLeft() + lineDynamicDimen;
			lastY = lineMarginSide - (mIcon.getWidth() >> 1);
			//画一个图
			canvas.drawBitmap(mIcon, lastX, lastY, null);
		}
	}

	private void drawBetweenLineHorizontal(Canvas canvas) {
		for(int i = 0; i < getChildCount() - 1; i++) {
			//画剩下的
			canvas.drawLine(firstX, lineMarginSide, lastX, lineMarginSide, linePaint);
			//画了线，就画圆
			if(getChildAt(i) != null && i != 0) {
				int left = getChildAt(i).getLeft();
				//记录值
				int x = left + getChildAt(i).getPaddingLeft() + lineDynamicDimen;
				canvas.drawCircle(x, lineMarginSide, pointSize, pointPaint);
			}
		}
	}

	public int getLineStrokeWidth() {
		return lineStrokeWidth;
	}

	public void setLineStrokeWidth(int lineStrokeWidth) {
		this.lineStrokeWidth = lineStrokeWidth;
	}

	public boolean isDrawLine() {
		return drawLine;
	}

	public void setDrawLine(boolean drawLine) {
		this.drawLine = drawLine;
	}

	public Paint getLinePaint() {
		return linePaint;
	}

	public void setLinePaint(Paint linePaint) {
		this.linePaint = linePaint;
	}

	public int getPointSize() {
		return pointSize;
	}

	public void setPointSize(int pointSize) {
		this.pointSize = pointSize;
	}

	public int getPointColor() {
		return pointColor;
	}

	public void setPointColor(int pointColor) {
		this.pointColor = pointColor;
	}

	public Paint getPointPaint() {
		return pointPaint;
	}

	public void setPointPaint(Paint pointPaint) {
		this.pointPaint = pointPaint;
	}

	public int getLineColor() {
		return lineColor;
	}

	public void setLineColor(int lineColor) {
		this.lineColor = lineColor;
	}

	public int getLineMarginSide() {
		return lineMarginSide;
	}

	public void setLineMarginSide(int lineMarginSide) {
		this.lineMarginSide = lineMarginSide;
	}

	public int getLineDynamicDimen() {
		return lineDynamicDimen;
	}

	public void setLineDynamicDimen(int lineDynamicDimen) {
		this.lineDynamicDimen = lineDynamicDimen;
	}

	public Bitmap getIcon() {
		return mIcon;
	}

	@SuppressWarnings("deprecation")
	public void setIcon(int resID) {
		if(resID == 0) return;
		BitmapDrawable temp = (BitmapDrawable) mContext.getResources().getDrawable(resID);
		if(temp != null) mIcon = temp.getBitmap();
	}
}
