package com.jewelbao.customview.Weather;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import com.jewelbao.customview.R;

/**
 Created by PC on 2016/4/27.
 */
public class SkyconView extends View
{
	protected boolean isStatic;
	boolean isAnimated;
	int strokeColor;
	int bgColor;

	public SkyconView(Context context)
	{
		super(context);
		extractAttributes(context);
	}

	public SkyconView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		extractAttributes(context);
	}

	private void extractAttributes(Context context) {
		TypedArray a = context.obtainStyledAttributes(null, R.styleable.weatherView);

		// get attributes from layout
		isStatic = a.getBoolean(R.styleable.weatherView_isStatic, this.isStatic);
		strokeColor = a.getColor(R.styleable.weatherView_strokeColor, this.strokeColor);

		if(strokeColor == 0){
			strokeColor = Color.BLACK;
		}

		bgColor = a.getColor(R.styleable.weatherView_bgColor, this.bgColor);

		if(bgColor == 0) {
			bgColor = Color.WHITE;
		}

		a.recycle();
	}

	public boolean isStatic() {
		return isStatic;
	}

	public void setIsStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	public boolean isAnimated() {
		return isAnimated;
	}

	public void setIsAnimated(boolean isAnimated) {
		this.isAnimated = isAnimated;
	}

	public int getStrokeColor() {
		return strokeColor;
	}

	public void setStrokeColor(int strokeColor) {
		this.strokeColor = strokeColor;
	}

	public int getBgColor() {
		return bgColor;
	}

	public void setBgColor(int bgColor) {
		this.bgColor = bgColor;
	}
}
