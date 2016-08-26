package com.jewelbao.customview.utils;

import android.content.Context;

import com.jewelbao.customview.Anim.ShapeLoadingView;

/**
 Created by Administrator on 2016/2/17 0017. com.jewelbao.library.utils  进度弹框工具
 */
@Deprecated
public class ProgressUtil
{

	private static ShapeLoadingView loadingView;
	private static ProgressUtil progressUtil;

	private ProgressUtil()
	{
	}

	public static ProgressUtil getInstance(Context context)
	{
		if(progressUtil == null)
		{
			progressUtil = new ProgressUtil();
		}
		if(loadingView == null)
		{
			loadingView = new ShapeLoadingView(context);
		}
		return progressUtil;
	}

	public void show()
	{
		if(loadingView != null)
		{
			loadingView.show();
		}
	}

	public void dismiss()
	{
		if(loadingView != null)
		{
			loadingView.dismiss();
			loadingView = null;
		}
	}

	public boolean isShow() {
		return loadingView.isShow();
	}
}
