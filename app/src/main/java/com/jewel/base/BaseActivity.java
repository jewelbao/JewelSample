package com.jewel.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jewel.customView.TitleBar;
import com.jewel.sample.R;

/**
 * Created by Kevin on 2015/9/11.
 * 实现基础界面绘制和界面控制，使用 {@link com.jewel.base.JBaseAct}代替
 */
@Deprecated
public class BaseActivity extends FragmentActivity {
	protected LayoutInflater mInflater;
	/** 最外层布局 */
	protected RelativeLayout mBaseLayout = null;
	/** 自定义标题栏 */
	private TitleBar mTitleBar = null;
	/** 主内容布局 */
	protected RelativeLayout mContentLayout = null;
	/** 空数据错误数据提示 */
	private TextView mEmptyView = null;
	/** 加载圈 */
	private View mLoadingLayout = null;

	public static BaseApp mApplication;

	protected static final int mContentLayoutID;
	protected static final int mEmptyViewID;

	static {
		mContentLayoutID = 2;
		mEmptyViewID = 3;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
			SystemBarTintManager tintManager = new SystemBarTintManager(this);
			tintManager.setStatusBarTintEnabled(true);
			tintManager.setStatusBarTintResource(R.color.holo_blue_bright);  //通知栏所需颜色
		}

		super.onCreate(savedInstanceState);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		mApplication = (BaseApp) getApplication();
		mApplication.setCurrentActivity(this);

		mInflater = LayoutInflater.from(this);

		mBaseLayout = new RelativeLayout(this);
		//noinspection deprecation
		mBaseLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

		addTitleBar();
		addContentLayout();
		addEmptyView();

		setContentView(mBaseLayout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
		mBaseLayout.setFitsSystemWindows(true);
	}

	/**
	 * 开启沉浸式状态栏,需要在Activity布局文件最外层控件加上属性：android:fitsSystemWindows="true"或者在Activity#onCreate()中设置setFitsSystemWindows(Boolean)
	 *
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

	/** 添加标题栏 */
	private void addTitleBar() {
		// 标题设置
		mTitleBar = new TitleBar(this);
		mTitleBar.setId(TitleBar.ID);
		mTitleBar.setActivity(this);
		mTitleBar.setVisibility(View.GONE);
		// 添加标题栏
		mBaseLayout.addView(mTitleBar, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
	}

	/* 添加主内容布局 */
	private void addContentLayout() {
		// 主内容界面设置
		mContentLayout = new RelativeLayout(this);
		mContentLayout.setId(mContentLayoutID);
		mContentLayout.setPadding(0, 0, 0, 0);

		RelativeLayout.LayoutParams contentParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		contentParams.addRule(RelativeLayout.BELOW, mTitleBar.getId());
		// 添加主内容界面
		mBaseLayout.addView(mContentLayout, contentParams);
	}

	@SuppressWarnings("unused")
	protected void addBottomView(View view) {
		RelativeLayout.LayoutParams contentParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		contentParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		// 添加主内容界面
		mBaseLayout.addView(view, contentParams);
	}

	// 添加无数据布局
	private void addEmptyView() {
		mEmptyView = new TextView(this);
		mEmptyView.setId(mEmptyViewID);
		mEmptyView.setText(R.string.tips_empty);
		mEmptyView.setGravity(Gravity.CENTER);

		mEmptyView.setVisibility(View.GONE);

		RelativeLayout.LayoutParams contentParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		contentParams.addRule(RelativeLayout.CENTER_IN_PARENT, mEmptyView.getId());
		// 添加主内容界面
		mBaseLayout.addView(mEmptyView, contentParams);

	}

	@Override
	protected void onResume() {
		super.onResume();
		mApplication.setCurrentActivity(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	/**
	 * 用指定contentView填充界面
	 *
	 * @param contentView 视图view
	 */
	public void setBaseContentView(View contentView) {
		mContentLayout.removeAllViews();
		mContentLayout.addView(contentView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
	}

	/**
	 * 用指定资源文件填充界面
	 *
	 * @param resID layout resource
	 */
	public void setBaseContentView(int resID) {
		setBaseContentView(mInflater.inflate(resID, null));
	}

	/**
	 * 设置界面（忽略标题栏）
	 *
	 * @param layoutResID layout resource
	 * @see android.app.Activity#setContentView(int)
	 */
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
	}

	/**
	 * 设置界面（忽略标题栏）
	 *
	 * @param view   view
	 * @param params LayoutParams
	 */
	@Override
	public void setContentView(View view, ViewGroup.LayoutParams params) {
		super.setContentView(view, params);
	}

	@Override
	public void setContentView(View view) {
		super.setContentView(view);
	}

	@SuppressWarnings("unused")
	public TitleBar getTitleBar() {
		return mTitleBar;
	}

	/**
	 * 显示标题栏(默认只显示左边按钮,标题)
	 *
	 * @param titleResID title resource id
	 */
	public void showTitleBar(Object titleResID) {
		mTitleBar.setVisibility(View.VISIBLE);
		if(titleResID instanceof Integer) {
			mTitleBar.setTitle((int) titleResID);
		} else {
			mTitleBar.setTitle((String) titleResID);
		}
		mTitleBar.showLeftButton();
	}

	/**
	 * 显示标题栏(默认只显示左边按钮,标题, 右边文字)
	 *
	 * @param titleResID title resource id
	 */
	@SuppressWarnings("unused")
	public void showTitleBarWithRight(int titleResID, int rightButtonRes, View.OnClickListener listener, boolean rightText) {
		mTitleBar.setVisibility(View.VISIBLE);
		mTitleBar.setTitle(titleResID);
		mTitleBar.showLeftButton();

		if(rightText) {
			mTitleBar.setRightButtonText(rightButtonRes);
			mTitleBar.setRightButtonBackground(android.R.color.transparent);
		} else {
			mTitleBar.setRightButtonSize(36, 36);
			mTitleBar.setRightButtonBackground(rightButtonRes);
		}
		mTitleBar.setRightButtonListener(listener);
		mTitleBar.showRightButton();
	}

	/**
	 * 显示标题栏(全部)
	 *
	 * @param titleResID     标题资源ID
	 * @param rightButtonRes 右侧按钮资源ID
	 * @param leftListener   左侧按钮监听事件
	 * @param rightListener  右侧按钮监听事件
	 * @param rightText      右侧按钮是否纯文字，是则rightButtonRes应该为String资源，否则为drawable资源
	 */
	@SuppressWarnings("unused")
	public void showTitleBarAllAndChangeListener(int titleResID, int rightButtonRes, View.OnClickListener leftListener, View.OnClickListener rightListener, boolean rightText) {
		mTitleBar.setVisibility(View.VISIBLE);
		mTitleBar.setTitle(titleResID);
		mTitleBar.showLeftButton();
		mTitleBar.setLeftButtonListener(leftListener);

		if(rightText) {
			mTitleBar.setRightButtonText(rightButtonRes);
			mTitleBar.setRightButtonBackground(android.R.color.transparent);
		} else {
			mTitleBar.setRightButtonSize(48, 48);
			mTitleBar.setRightButtonBackground(rightButtonRes);
		}
		mTitleBar.setRightButtonListener(rightListener);
		mTitleBar.showRightButton();
	}

	/**
	 * 显示标题栏并更改背景(默认只显示左边按钮,标题背景, 标题颜色)
	 *
	 * @param titleResID title resource id
	 * @param bgResID title background resource id
	 * @param titleColorID title text color resource id
	 */
	@SuppressWarnings("unused")
	public void showTitleBarWithBackground(int titleResID, int bgResID, int titleColorID, int leftResID) {
		mTitleBar.setVisibility(View.VISIBLE);
		mTitleBar.setTitle(titleResID);
		mTitleBar.setTitleColor(titleColorID);
		mTitleBar.setLeftButtonBackground(leftResID);
		mTitleBar.showLeftButton();
		mTitleBar.setTitleBarBackground(bgResID);
	}

	/**
	 * 显示加载框
	 *
	 * @param isShow 是否显示
	 */
	public void showProgress(boolean isShow) {
		if(isShow) {
			mLoadingLayout.setVisibility(View.VISIBLE);
		} else {
			mLoadingLayout.setVisibility(View.GONE);
		}
	}

	/**
	 * 显示加载框
	 */
	@SuppressWarnings("unused")
	public void showProgressInHandle(final boolean isShow) {
		runOnUiThread(() -> showProgress(isShow));
	}

	/**
	 * 显示空数据提示
	 */
	@SuppressWarnings("unused")
	public void showEmpty(boolean isShow) {
		if(isShow) {
			mEmptyView.setText(R.string.tips_empty);
			mEmptyView.setOnClickListener(null);
			mEmptyView.setVisibility(View.VISIBLE);
		} else {
			mEmptyView.setVisibility(View.GONE);
		}
	}

	/**
	 * 显示错误提示
	 */
	@SuppressWarnings("unused")
	public void showErrorView(View.OnClickListener listener) {
		mEmptyView.setVisibility(View.VISIBLE);
		mEmptyView.setText(R.string.service_error);
		mEmptyView.setOnClickListener(listener);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Common.finishActivity(this, true);
	}
}
