package com.jewelbao.customview.utils;

import android.app.FragmentManager;

import com.jewelbao.customview.ImageView.GifLoadingView;

/**
 * Created by Jewel on 2016/6/22 0022.
 * Gif图片加载兑换框工具
 */
public class LoadingUtil {

	private static GifLoadingView loadingView;

	private static LoadingUtil instance;


	private LoadingUtil()
	{
		if(loadingView == null) {
			loadingView = new GifLoadingView();
		}
	}

	public static LoadingUtil getInstance()
	{
		if(instance == null)
		{
			synchronized(LoadingUtil.class)
			{
				if(instance == null)
				{
					instance = new LoadingUtil();
				}
			}
		}
		return instance;
	}

	public void show(FragmentManager context, int gifResID) {
		loadingView.setResource(gifResID);
		loadingView.show(context, "");
	}
}
