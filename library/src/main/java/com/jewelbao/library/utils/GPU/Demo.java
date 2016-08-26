package com.jewelbao.library.utils.GPU;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.widget.LinearLayout;

/**
 Created by jewel on 2016/4/25.
 */
public class Demo extends Activity implements IGpuInfo
{
	LinearLayout layout;
	GLSurfaceView view;
	GPURenderer renderer = new GPURenderer();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		layout = new LinearLayout(this);
		setContentView(layout);

		renderer.getGpuInfoFrom(this);
		view = new GLSurfaceView(this);
		view.setEGLConfigChooser(false);
		view.setRenderer(renderer);
		layout.addView(view);
	}

	@Override
	public void getGPU(String GPUInfo)
	{
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				// TODO: 2016/4/25 when get GPU info , have to remove GLSurfaceView
				layout.removeView(view);
				// TODO: 2016/4/25  do some here , if you want to get GPU info
			}
		});
	}
}
