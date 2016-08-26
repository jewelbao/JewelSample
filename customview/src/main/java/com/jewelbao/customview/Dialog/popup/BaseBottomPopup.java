package com.jewelbao.customview.Dialog.popup;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import com.jewelbao.library.utils.JLog;


/**
 * 弹窗（禁止扩展）
 * @author Jewel
 * @since 2016/7/7 0007
 * @version 1.0
 */
public class BaseBottomPopup {

	private Dialog dialog;
	private FrameLayout contentLayout;

	private int animRes = com.jewelbao.library.R.style.Animation_Slide;

	protected BaseBottomPopup(Context context) {
		init(context);
	}

	private void init(Context context) {
		contentLayout = new FrameLayout(context);
		contentLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		contentLayout.setFocusable(true);
		contentLayout.setFocusableInTouchMode(true);

		dialog = new Dialog(context);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);

		Window window = dialog.getWindow();
		window.setGravity(Gravity.BOTTOM);
		window.setWindowAnimations(animRes);
		window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		//android.util.AndroidRuntimeException: requestFeature() must be called before adding content
		window.requestFeature(Window.FEATURE_NO_TITLE);
		window.setContentView(contentLayout);
	}

	protected void setAnimation(@StyleRes int animRes) {
		Window window = dialog.getWindow();
		window.setWindowAnimations(animRes);
	}

	protected boolean isShowing() {
		return dialog.isShowing();
	}

	protected void show() {
		dialog.show();
	}

	protected void dismiss() {
		dialog.dismiss();
	}

	protected void setContentView(View view) {
		contentLayout.removeAllViews();
		contentLayout.addView(view);
	}

	protected void setSize(int width, int height) {
		JLog.d(String.format("will set popup width to %s and height to %s", width, height));
		ViewGroup.LayoutParams params = contentLayout.getLayoutParams();
		if(params == null) {
			params = new ViewGroup.LayoutParams(width, height);
		} else {
			params.width = width;
			params.height = height;
		}
		contentLayout.setLayoutParams(params);
	}

	protected void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
		dialog.setOnDismissListener(onDismissListener);
	}
}
