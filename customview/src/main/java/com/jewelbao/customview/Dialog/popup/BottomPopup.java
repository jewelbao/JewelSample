package com.jewelbao.customview.Dialog.popup;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StyleRes;
import android.view.View;
import android.view.ViewGroup;

import com.jewelbao.library.utils.JLog;
import com.jewelbao.library.utils.ScreenUtil;

/**
 * 底部弹窗基类
 * @author Jewel
 * @version 1.0
 * @since 2016/7/8 0008
 */
public abstract class BottomPopup<V extends View> {

	protected static final int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
	protected static final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;
	private BaseBottomPopup baseBottomPopup;

	protected abstract V getView();

	public BottomPopup(Context context) {
		baseBottomPopup = new BaseBottomPopup(context);
		if(isFixedHeight()) {
			baseBottomPopup.setSize(ScreenUtil.getScreenWidth(context), ScreenUtil.getScreenHeight(context)/2);
		} else {
			baseBottomPopup.setSize(ScreenUtil.getScreenWidth(context), WRAP_CONTENT);
		}
	}

	/**
	 * 设置弹窗内容视图之前的逻辑，若有需要，可重载设置。默认无作为。
	 */
	protected void setContentViewBefore() {}

	/**
	 * 设置弹窗内容视图之后的逻辑，若有需要，可重载设置。默认无作为。
	 */
	protected void setContentViewAfter(View view) {}

	/**
	 * 是否固定高度为屏幕的一半高度,默认为false,表示当前高度自适应。
	 * @return false
	 */
	protected boolean isFixedHeight() {
		return false;
	}

	public void show() {
		JLog.d("do something before dialog to show");
		prepare();
		baseBottomPopup.show();
		JLog.d("dialog is showing!");
	}

	/**
	 * 弹窗前的准备工作
	 */
	private void prepare() {
		setContentViewBefore();
		V view = getView();
		baseBottomPopup.setContentView(view);
		setContentViewAfter(view);
	}

	public void dismiss() {
		baseBottomPopup.dismiss();
		JLog.d("dialog dismiss");
	}

	/**
	 * 设置弹窗动画
	 * @param animRes 动画资源
	 */
	public void setAnimation(@StyleRes int animRes) {
		baseBottomPopup.setAnimation(animRes);
	}

	/**
	 * 设置窗口大小
	 * @param width 宽度
	 * @param height 高度
	 */
	public void setSize(int width, int height) {
		baseBottomPopup.setSize(width, height);
	}

	/**
	 * 窗口是否已显示
	 * @return boolean
	 */
	public boolean isShowing() {
		return baseBottomPopup.isShowing();
	}

	/**
	 * 窗口消失监听
	 * @param onDismissListener 监听接口
	 */
	public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
		baseBottomPopup.setOnDismissListener(onDismissListener);
	}
}
