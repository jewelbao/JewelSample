package com.jewelbao.library.utils.GPU;

import android.opengl.GLSurfaceView;

import com.jewelbao.library.utils.JLog;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 Created by jewel on 2016/3/8.
 */
public class GPURenderer implements GLSurfaceView.Renderer
{
	IGpuInfo getGpuInfo;
	public void getGpuInfoFrom(IGpuInfo getGpuInfo)
	{
		this.getGpuInfo = getGpuInfo;
	}
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		String mVendor = gl.glGetString(GL10.GL_RENDERER);
		if(getGpuInfo != null) {
			getGpuInfo.getGPU(mVendor);
		}
		JLog.e("SystemInfo", "GL_RENDERER = " +gl.glGetString(GL10.GL_RENDERER));
		JLog.e("SystemInfo", "GL_VENDOR = " + gl.glGetString(GL10.GL_VENDOR));
		JLog.e("SystemInfo", "GL_VERSION = " + gl.glGetString(GL10.GL_VERSION));
		JLog.e("SystemInfo", "GL_EXTENSIONS = " + gl.glGetString(GL10.GL_EXTENSIONS));
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{

	}

	@Override
	public void onDrawFrame(GL10 gl)
	{

	}
}
