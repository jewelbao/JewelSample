package com.jewel.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.jewel.customView.TitleBar;
import com.jewel.sample.R;
import com.jewelbao.customview.utils.ProgressUtil;
import com.jewelbao.library.utils.ScreenUtil;

/**
 Created by PC on 2016/4/21.
 */
@SuppressWarnings("unused")
public abstract class JBaseAct extends AppCompatActivity
{
	private boolean debug = false;
	private TextView mEmptyView = null; // 空数据错误数据提示

	protected int screenWidth ;
	protected int screenHeight;
	//	public static BaseApplication mApplication;
	/* 自定义标题栏 */
	protected TitleBar mTitleBar;
	ViewStub stubContentView;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{

		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
			SystemBarTintManager tintManager = new SystemBarTintManager(this);
			tintManager.setStatusBarTintEnabled(true);
			tintManager.setStatusBarTintResource(R.color.holo_blue_bright);  //通知栏所需颜色
		}

		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
							 WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.base);

		screenWidth = ScreenUtil.getScreenWidth(this);
		screenHeight = ScreenUtil.getScreenHeight(this);

		stubContentView = (ViewStub) findViewById(R.id.stub);

		mTitleBar = (TitleBar) findViewById(R.id.titleBar);
		if(mTitleBar != null) {
			mTitleBar.setActivity(this);
			mTitleBar.setVisibility(View.GONE);
		}

		//		if(BuildConfig.DEBUG && debug)
		//			CrashHandler.getInstance().init(this);
	}

	/**
	 * 开启沉浸式状态栏,需要在Activity布局文件最外层控件加上属性：android:fitsSystemWindows="true"
	 * @param on 开关
	 */
	@TargetApi(19)
	protected void setTranslucentStatus(boolean on) {
		Window window = getWindow();
		WindowManager.LayoutParams winParams = window.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

		if(on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		window.setAttributes(winParams);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		BaseApp.getInstance().setCurrentActivity(this);
	}

	@Override
	protected void onPause()
	{
		super.onPause();
	}

	/**
	 用指定资源文件填充界面

	 @param resID layout资源ID
	 */
	public View setBaseContentView(int resID)
	{
		stubContentView.setLayoutResource(resID);
		return stubContentView.inflate();
	}

	/**
	 显示标题栏(默认只显示左边按钮,标题)

	 @param titleResID 标题资源ID
	 */
	public void showTitleBar(int titleResID)
	{
		mTitleBar.setVisibility(View.VISIBLE);
		mTitleBar.setTitle(titleResID);
		mTitleBar.showLeftButton();
	}

	/**
	 显示标题栏(默认只显示左边按钮,标题)

	 @param titleResID 标题资源ID
	 */
	public void showTitleBar(String titleResID)
	{
		mTitleBar.setVisibility(View.VISIBLE);
		mTitleBar.setTitle(titleResID);
		mTitleBar.showLeftButton();
	}

	/**
	 显示标题栏(默认只显示左边按钮,标题, 右边文字)
	 */
	public void showTitleBarWithRight(String title, int rightButtonRes,
									  View.OnClickListener listener, boolean rightText)
	{
		mTitleBar.setVisibility(View.VISIBLE);
		mTitleBar.setTitle(title);
		mTitleBar.showLeftButton();

		if(rightText)
		{
			mTitleBar.setRightButtonText(rightButtonRes);
			mTitleBar.setRightButtonBackground(android.R.color.transparent);
		} else
		{
			//			mTitleBar.setRightButtonSize(36, 36);
			mTitleBar.setRightButtonBackground(rightButtonRes);
		}
		mTitleBar.setRightButtonListener(listener);
		mTitleBar.showRightButton();
	}

	/**
	 显示标题栏(默认只显示左边按钮,标题, 右边文字)

	 @param titleResID 标题资源ID
	 */
	public void showTitleBarWithRight(int titleResID, int rightButtonRes,
									  View.OnClickListener listener, boolean rightText)
	{
		mTitleBar.setVisibility(View.VISIBLE);
		mTitleBar.setTitle(titleResID);
		mTitleBar.showLeftButton();

		if(rightText)
		{
			mTitleBar.setRightButtonText(rightButtonRes);
			mTitleBar.setRightButtonBackground(android.R.color.transparent);
		} else
		{
			//			mTitleBar.setRightButtonSize(36, 36);
			mTitleBar.setRightButtonBackground(rightButtonRes);
		}
		mTitleBar.setRightButtonListener(listener);
		mTitleBar.showRightButton();
	}

	/**
	 显示标题栏(全部)

	 @param titleResID     标题资源ID
	 @param rightButtonRes 右侧按钮资源ID
	 @param leftListener   左侧按钮监听事件
	 @param rightListener  右侧按钮监听事件
	 @param rightText      右侧按钮是否纯文字，是则rightButtonRes应该为String资源，否则为drawable资源
	 */
	public void showTitleBarAllAndChangeListener(int titleResID, int rightButtonRes,
												 View.OnClickListener leftListener,
												 View.OnClickListener rightListener,
												 boolean rightText)
	{
		mTitleBar.setVisibility(View.VISIBLE);
		mTitleBar.setTitle(titleResID);
		mTitleBar.showLeftButton();
		mTitleBar.setLeftButtonListener(leftListener);

		if(rightText)
		{
			mTitleBar.setRightButtonText(rightButtonRes);
			mTitleBar.setRightButtonBackground(android.R.color.transparent);
		} else
		{
			mTitleBar.setRightButtonSize(48, 48);
			mTitleBar.setRightButtonBackground(rightButtonRes);
		}
		mTitleBar.setRightButtonListener(rightListener);
		mTitleBar.showRightButton();
	}

	/**
	 @param leftListener   左侧按钮监听事件
	 */
	public void showTitleBarChangeLeftListener(View.OnClickListener leftListener)
	{
		//		mTitleBar.setVisibility(View.VISIBLE);
		//		mTitleBar.setTitle(titleResID);
		//		mTitleBar.showLeftButton();
		mTitleBar.setLeftButtonListener(leftListener);
	}

	/**
	 显示标题栏并更改背景(默认只显示左边按钮,标题背景, 标题颜色)

	 @param titleResID   标题文字资源
	 @param bgResID      标题背景资源
	 @param titleColorID 标题颜色资源
	 */
	public void showTitleBarWithBackground(int titleResID, int bgResID, int titleColorID,
										   int leftResID)
	{
		mTitleBar.setVisibility(View.VISIBLE);
		mTitleBar.setTitle(titleResID);
		mTitleBar.setTitleColor(titleColorID);
		mTitleBar.setLeftButtonBackground(leftResID);
		mTitleBar.showLeftButton();
		mTitleBar.setTitleBarBackground(bgResID);
	}

	/**
	 显示加载框

	 @param isShow 是否显示
	 */
	public void showProgress(boolean isShow)
	{
		if(isShow)
		{
			ProgressUtil.getInstance(Global.mContext).show();
		} else
		{
			ProgressUtil.getInstance(Global.mContext).dismiss();
		}
	}

	// 显示加载框
	public void showProgressInHandle(final boolean isShow)
	{
		runOnUiThread(() -> showProgress(isShow));
	}

	// 显示空数据提示
	public void showEmpty(boolean isShow)
	{
		if(isShow)
		{
			mEmptyView.setText(R.string.tips_empty);
			mEmptyView.setOnClickListener(null);
			mEmptyView.setVisibility(View.VISIBLE);
		} else
		{
			mEmptyView.setVisibility(View.GONE);
		}
	}

	// 显示错误提示
	public void showErrorView(View.OnClickListener listener)
	{
		mEmptyView.setVisibility(View.VISIBLE);
		mEmptyView.setText(R.string.service_error);
		mEmptyView.setOnClickListener(listener);
	}
}
