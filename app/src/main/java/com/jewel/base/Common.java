package com.jewel.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import com.jewel.sample.R;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;

/**
 * Created by Kevin on 2015/9/13.
 * 基础公共方法，主要为与活动有关的工具
 */
public class Common {

	/**
	 * 界面跳转
	 *
	 * @param currentActivity 当前活动
	 * @param targetActivity  目标活动
	 * @param finish          是否结束当前活动
	 */
	@SuppressWarnings("unused")
	public static void gotoActivity(Activity currentActivity,
									Class<?> targetActivity, boolean finish) {
		Intent intent = new Intent();
		intent.setClass(currentActivity, targetActivity);
		currentActivity.startActivity(intent);
		currentActivity.overridePendingTransition(R.anim.slide_left_in,
				R.anim.slide_left_out);

		if(finish) {
			currentActivity.finish();
		}
	}

	/**
	 * 带Bundle数据传递的界面跳转
	 *
	 * @param currentActivity 当前活动
	 * @param targetActivity  目标活动
	 * @param bundle          需要传递的数据
	 * @param finish          是否结束当前活动
	 */
	public static void gotoActivityWithData(Activity currentActivity,
											Class<?> targetActivity, Bundle bundle, boolean finish) {
		Intent intent = new Intent();
		intent.setClass(currentActivity, targetActivity);
		intent.putExtras(bundle);
		currentActivity.startActivity(intent);
		currentActivity.overridePendingTransition(R.anim.slide_left_in,
				R.anim.slide_left_out);

		if(finish) {
			currentActivity.finish();
		}
	}

	/**
	 * 界面跳转并返回结果
	 *
	 * @param currentActivity 当前活动
	 * @param targetActivity  目标活动
	 * @param RequestCode     请求码
	 * @param finish          是否结束当前活动
	 */
	@SuppressWarnings("unused")
	public static void gotoActivityForResult(Activity currentActivity,
											 Class<?> targetActivity, int RequestCode, boolean finish) {
		Intent intent = new Intent();
		intent.setClass(currentActivity, targetActivity);
		currentActivity.startActivityForResult(intent, RequestCode);
		currentActivity.overridePendingTransition(R.anim.slide_left_in,
				R.anim.slide_left_out);

		if(finish) {
			currentActivity.finish();
		}
	}

	/**
	 * 带Bundle数据传递的界面跳转并返回结果
	 *
	 * @param currentActivity 当前活动
	 * @param targetActivity  目标活动
	 * @param bundle          需要传递的数据
	 * @param RequestCode     请求码
	 * @param finish          是否结束当前活动
	 */
	@SuppressWarnings("unused")
	public static void gotoActivityWithDataForResult(Activity currentActivity,
													 Class<?> targetActivity, Bundle bundle, int RequestCode,
													 boolean finish) {
		Intent intent = new Intent();
		intent.setClass(currentActivity, targetActivity);
		intent.putExtras(bundle);
		currentActivity.startActivityForResult(intent, RequestCode);
		currentActivity.overridePendingTransition(R.anim.slide_left_in,
				R.anim.slide_left_out);

		if(finish) {
			currentActivity.finish();
		}
	}

	/**
	 * 跳转页面--结束中间活动
	 *
	 * @param currentActivity 当前活动
	 * @param targetActivity  目标活动
	 * @param refresh         是否刷新要跳转的界面
	 * @param finish          是否结束当前活动
	 */
	@SuppressWarnings("unused")
	public static void gotoActivityWithFinishOtherAll(Activity currentActivity,
													  Class<?> targetActivity, boolean refresh, boolean finish) {
		Intent intent = new Intent();
		intent.setClass(currentActivity, targetActivity);
		if(!refresh) {
			intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);// 设置不要刷新将要跳到的界面
		}
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// 它可以关掉所要到的界面中间的activity
		currentActivity.startActivity(intent);
		currentActivity.overridePendingTransition(R.anim.slide_left_in,
				R.anim.slide_left_out);

		if(finish) {
			currentActivity.finish();
		}
	}

	/**
	 * 结束当前活动
	 *
	 * @param mActivity activity
	 * @param isFinish  是否结束当前活动
	 */
	public static void finishActivity(Activity mActivity, boolean isFinish) {
		Activity parent = mActivity.getParent();
		Activity topActivity = mActivity;
		while(parent != null) {
			topActivity = parent;
			parent = parent.getParent();
		}

		topActivity.overridePendingTransition(R.anim.slide_right_in,
				R.anim.slide_right_out);
		if(isFinish) {
			topActivity.finish();
		}
	}


//    public static void gotoActivity(String packageName, String clazzName, boolean finish) {
//        Intent intent = new Intent();
//
//        ComponentName cn = new ComponentName(packageName, clazzName);
//        intent.setComponent(cn);
//        intent.setAction("android.intent.action.VIEW");
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        Global.mContext.startActivity(intent);
//    }

	/**
	 * 设置TextView图片
	 *
	 * @param tv         TextView
	 * @param drawableID 图片资源
	 * @param direction  方向：上下左右对应Global.TOP,BOTTOM,LEFT, RIGHT
	 */
	public static void setTextDrawable(TextView tv, int drawableID, int direction) {
		@SuppressWarnings("deprecation") Drawable img = tv.getResources().getDrawable(drawableID);
		if(img != null) {
			img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
			switch(direction) {
				case Constant.LEFT:
					tv.setCompoundDrawables(img, null, null, null);
					break;
				case Constant.TOP:
					tv.setCompoundDrawables(null, img, null, null);
					break;
				case Constant.RIGHT:
					tv.setCompoundDrawables(null, null, img, null);
					break;
				case Constant.BOTTOM:
					tv.setCompoundDrawables(null, null, null, img);
					break;
			}
		}
	}

	/**
	 * 拨打电话
	 *
	 * @param context 上下文
	 * @param phone 手机号
	 */
	@SuppressWarnings("unused")
	public static void callPhone(Context context, String phone) {
		Intent intent = new Intent(Intent.ACTION_DIAL);
		Uri data = Uri.parse("tel:" + phone);
		intent.setData(data);
		context.startActivity(intent);
	}

	/**
	 * 获取版本号
	 *
	 * @return 当前应用的版本号
	 */
	@SuppressWarnings("unused")
	public static String getVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			String version = info.versionName;
			return "V" + version;
		} catch(Exception e) {
			e.printStackTrace();
			return "null";
		}
	}

	/**
	 * 获取版本代码
	 *
	 * @return 当前应用的版本代码
	 */
	@SuppressWarnings("unused")
	public static int getVersionCode(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			return info.versionCode;
		} catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 获取设备IMEI
	 *
	 * @param context 上下文
	 * @return 手机IMEI码
	 */
	@SuppressWarnings("unused")
	public static String getIMEI(Context context) {
		TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return manager.getDeviceId();
	}

	public static void showHint(Context activity, String msg) {
		SnackbarManager.show(Snackbar.with(activity).text(msg));
	}
}
