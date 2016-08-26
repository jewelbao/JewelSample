package com.jewel.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;

import com.jewelbao.library.utils.SharedPre;
import com.jewelbao.library.utils.ToastUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.HashMap;
import java.util.Map;

/**
 Created by Administrator on 2016/2/15 0015. com.jewel.base  JewelSample
 */
public class BaseApp extends Application
{
	private static BaseApp application;
	public static BaseApp getInstance()
	{
		return application;
	}

	private Activity mActivity; // 当前活动

	public static Map<String, Long> timeMap = new HashMap<>();

	@Override
	public void onCreate()
	{
		super.onCreate();
		application = this;
		Global.mContext = getApplicationContext();
		Global.mInflater = LayoutInflater.from(this);

		// 即时存储读取的SharedPre初始化
		SharedPre.init(getApplicationContext(), getPackageName());
		ToastUtils.init(getApplicationContext());
//		BlockCanary.install(this, new AppBlockCanaryContext()).start();

		initImageLoader(getApplicationContext());
	}

	private void initImageLoader(Context context)
	{
		ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
		config.threadPriority(Thread.NORM_PRIORITY - 2);
		config.denyCacheImageMultipleSizesInMemory();
		config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
		config.diskCacheSize(50 * 1024 * 1024);
		config.tasksProcessingOrder(QueueProcessingType.LIFO);
		config.writeDebugLogs();

		ImageLoader.getInstance().init(config.build());
	}

	// 设置当前活动
	public void setCurrentActivity(Activity activity)
	{
		this.mActivity = activity;
	}

	// 获取当前活动
	public Activity getActivity()
	{
		return mActivity;
	}
}
