package com.jewel.sample.Anim;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jewel.base.BaseActivity;
import com.jewel.sample.R;
import com.jewelbao.library.utils.FileUtils;
import com.jewelbao.library.utils.PathUtil;
import com.litesuits.android.async.SimpleTask;
import com.litesuits.android.async.TaskExecutor;

import java.io.File;

/**
 Created by PC on 2016/4/25.
 */
@SuppressWarnings("deprecation")
public class SmoothImageAct0 extends BaseActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final ImageView imageView = new ImageView(this);
		setBaseContentView(imageView);
		showTitleBar(R.string.title_image_weixin);

		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100, 100);
		layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		imageView.setLayoutParams(layoutParams);
		imageView.setImageResource(R.drawable.title);

		SimpleTask createPathTask = new SimpleTask(){
			@Override
			protected Object doInBackground()
			{
				PathUtil.getInstance(SmoothImageAct0.this).initGeneratePath();
				return null;
			}
		};
		SimpleTask copeFileTask = new SimpleTask(){
			@Override
			protected Object doInBackground()
			{
				FileUtils.copyAssetToSDCard(getAssets(), "title.jpg", PathUtil.getInstance(SmoothImageAct0.this).getCachePath().getAbsolutePath() + File.separator + "title.jpg");
				return null;
			}

			@Override
			protected void onPostExecute(Object o)
			{
				imageView.setOnClickListener(v -> {
					Intent intent = new Intent(SmoothImageAct0.this, SmoothImageAct.class);
					intent.putExtra("image", PathUtil.getInstance(SmoothImageAct0.this).getCachePath().getAbsolutePath()+ File.separator + "title.jpg"); //非必须
//						intent.putExtra("position", position);
					int[] location = new int[2];
					imageView.getLocationOnScreen(location);
					intent.putExtra("locationX", location[0]);//必须
					intent.putExtra("locationY", location[1]);//必须
					intent.putExtra("width", imageView.getWidth());//必须
					intent.putExtra("height", imageView.getHeight());//必须
					startActivity(intent);
					overridePendingTransition(0, 0);
				});
			}
		};
		TaskExecutor.newOrderedExecutor().put(createPathTask).put(copeFileTask).start();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
}
