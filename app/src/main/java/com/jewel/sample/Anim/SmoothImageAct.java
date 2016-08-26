package com.jewel.sample.Anim;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jewel.base.BaseActivity;
import com.jewelbao.customview.Anim.SmoothImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 Created by PC on 2016/4/25.
 */
@SuppressWarnings("deprecation")
public class SmoothImageAct extends BaseActivity
{
	SmoothImageView imageView;
	//	int mPosition ;
	int mLocationX;
	int mLocationY;
	int mWidth;
	int mHeight;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		String url = getIntent().getStringExtra("image");
		//		mPosition = getIntent().getIntExtra("position", 0);
		mLocationX = getIntent().getIntExtra("locationX", 0);
		mLocationY = getIntent().getIntExtra("locationY", 0);
		mWidth = getIntent().getIntExtra("width", 0);
		mHeight = getIntent().getIntExtra("height", 0);

		imageView = new SmoothImageView(this);
		imageView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
		imageView.transformIn();
		imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
		imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		setContentView(imageView);
		url = "file://" + url;
		ImageLoader.getInstance().displayImage(url, imageView);

		imageView.setOnTransformListener(mode -> {
			if(mode == SmoothImageView.STATE_TRANSFORM_OUT)
			{
				finish();
				overridePendingTransition(0, 0);
			}
		});

		imageView.setOnClickListener(v -> imageView.transformOut());
	}

	@Override
	protected void onResume()
	{
		super.onResume();
	}
}