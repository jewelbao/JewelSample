package com.jewelbao.customview.utils;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.jewelbao.customview.R;

/**
 Created by Kevin on 2016/3/21.
 悬浮窗口创建管理工具
 */
public class FloatViewManager
{
	public static final int QUEUE_SHOWVIEW = 0; // 底部显示标志

	private Context mContext;
	private WindowManager windowManager = null;
	private WindowManager.LayoutParams windowParams = null;
	private static FloatViewManager instance = null;

	private static boolean[] saveStates = new boolean[3];

	private ImageView img_showControl = null;

	public static FloatViewManager getInstance(Context context)
	{
		if(instance == null)
		{
			instance = new FloatViewManager(context);
		}
		return instance;
	}

	private FloatViewManager(Context context)
	{
		this.mContext = context;
		// 获取WindowManager
		windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		// 设置LayoutParams(全局变量）相关参数
		windowParams = new WindowManager.LayoutParams();

		windowParams.type = WindowManager.LayoutParams.TYPE_PHONE; // 设置window messageType
		windowParams.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明
		// 设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作
//		windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
		windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		for(int i = 0; i < 3; i++)
			saveStates[i] = false;
	}

	private void setWindowParams(int width, int height)
	{
		// 设置悬浮窗口长宽数据
		windowParams.width = width;
		windowParams.height = height;
	}

	/**
	 移除悬浮窗

	 @param queueID view对应的ID {@link FloatViewManager#QUEUE_SHOWVIEW}
	 */
	public void removeFloatView(int queueID)
	{
		switch(queueID)
		{
			case QUEUE_SHOWVIEW:
				if(img_showControl != null)
				{
					windowManager.removeView(img_showControl);
					img_showControl = null;
				}
				break;
			default:
				break;
		}
	}

	public void removeAllView()
	{
		if(img_showControl != null)
		{
			windowManager.removeView(img_showControl);
			img_showControl = null;
		}
	}

	public void savedInstanceState()
	{
		saveStates = hasView();
	}

	public boolean[] getSaveStates() {
		return saveStates;
	}

	public boolean[] hasView()
	{
		boolean[] hasView = new boolean[3];
		if(img_showControl != null)
		{
			hasView[QUEUE_SHOWVIEW] = true;
		} else
		{
			hasView[QUEUE_SHOWVIEW] = false;
		}
		return hasView;
	}

	//============================底部显示按钮相关方法===================================//

	/**
	 显示底部按钮(将自动生成View)

	 @param show
	 */
	public void showBottomControl(boolean show)
	{
		if(show)
		{
			createShowControlView();
		} else
		{
			removeFloatView(QUEUE_SHOWVIEW);
		}
	}

	/**
	 生成View内部方法
	 */
	private View createShowControlView()
	{
		if(img_showControl == null)
		{
			img_showControl = new ImageView(mContext);
			img_showControl.setImageResource(R.drawable.ic_expandable_view_chevron);

			setWindowParams(WindowManager.LayoutParams.WRAP_CONTENT,
							WindowManager.LayoutParams.WRAP_CONTENT);
			windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
			// 调整悬浮窗口
			windowParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
			// 显示myFloatView图像
			windowManager.addView(img_showControl, windowParams);
		}

		img_showControl.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				// TODO: 2016/4/25  点击悬浮图片的事件
			}
		});
		return img_showControl;
	}
}
