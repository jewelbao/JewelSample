package com.jewelbao.library.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Looper;
import android.os.SystemClock;


/**
 Created by PC on 2016/3/11.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler
{
	/**
	 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.

	 @param ex

	 @return true:如果处理了该异常信息;否则返回false.
	 */
	private boolean handleException(Thread thread, Throwable ex)
	{
		if(ex == null)
		{
			return false;
		}

		final StringBuffer sb = new StringBuffer();
		sb.append(thread + ", Cause By:" + ex).append("\r\n\r\n");
		JLog.e(sb.toString());

		StackTraceElement[] elements = ex.getStackTrace();
		for(int i = 0; i < elements.length; ++i)
		{
			sb.append(elements[i].toString() + "\r\n");
		}

		//记录下关键错误信息，可以存至本地并上传至服务器
		new Thread()
		{
			@Override
			public void run()
			{
				Looper.prepare();
				new AlertDialog.Builder(mContext).setCancelable(true).setMessage(sb.toString()).setPositiveButton(
						"关闭", new DialogInterface.OnClickListener(){
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								((Activity)mContext).finish();
								//退出程序
								android.os.Process.killProcess(android.os.Process.myPid());
								System.exit(1);
							}
						})
												 .create().show();
				Looper.loop();
			}
		}.start();

		return true;
	}

	private static CrashHandler INSTANCE = new CrashHandler();
	private Context mContext;
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	//	private boolean hasShow = false;

	private CrashHandler()
	{
	}

	public static CrashHandler getInstance()
	{
		return INSTANCE;
	}

	public void init(Context ctx)
	{
		mContext = ctx;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(final Thread thread, final Throwable ex)
	{

		AsyncTask.execute(new Runnable()
		{
			@Override
			public void run()
			{
				handleException(thread, ex);
			}
		});
		SystemClock.sleep(100000);
		((Activity)mContext).finish();
		//退出程序
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(1);
	}
}
