package com.jewelbao.customview.Dialog;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jewelbao.customview.Dialog.impl.OnConfirmListener;
import com.jewelbao.customview.Dialog.popup.BottomPopup;
import com.jewelbao.customview.R;
import com.jewelbao.library.utils.Colors;
import com.jewelbao.library.utils.DensityUtil;

/**
 * 带确定取消按钮的弹框基类
 *
 * @author Jewel
 * @version 1.0
 * @since 2016/7/8 0008
 */
public abstract class ConfirmPopup<V extends View> extends BottomPopup<View> implements View.OnClickListener {

	protected static final String TAG_SUBMIT = "submit";
	protected static final String TAG_CANCEL = "cancel";

	private Context context;
	private OnConfirmListener confirmListener;

	/**
	 * 取消按钮文字颜色
	 */
	private int cancelTextColor = Color.BLACK;
	/**
	 * 取消按钮文字
	 */
	private CharSequence cancelText = "";
	/**
	 * 取消按钮显示标志
	 */
	private boolean cancelVisible = true;

	/**
	 * 确定按钮文字颜色
	 */
	private int submitTextColor = Color.BLACK;
	/**
	 * 确定按钮文字
	 */
	private CharSequence submitText = "";

	/**
	 * 顶部分割线颜色
	 */
	private int topLineColor = Colors.GRAY_LIGHT;
	/**
	 * 顶部分割线显示标志
	 */
	private boolean topLineVisible = true;


	protected void setConfirmListener(OnConfirmListener listener) {
		this.confirmListener = listener;
	}

	public ConfirmPopup(Context context) {
		super(context);
		this.context = context;
		cancelText = context.getString(R.string.cancel);
		submitText = context.getString(R.string.submit);
	}

	protected abstract V getContentView(Context context);

	@Override
	protected View getView() {
		// 设置对话框属性
		LinearLayout rootLayout = new LinearLayout(context);
		rootLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
		rootLayout.setBackgroundColor(Color.WHITE);
		rootLayout.setOrientation(LinearLayout.VERTICAL);
		rootLayout.setGravity(Gravity.CENTER);
		rootLayout.setPadding(0, 0, 0, 0);
		rootLayout.setClipToPadding(false);

		// 设置顶部布局属性
		RelativeLayout topLayout = new RelativeLayout(context);
		topLayout.setLayoutParams(new RelativeLayout.LayoutParams(MATCH_PARENT, DensityUtil.dp2px(context, 40)));
		topLayout.setBackgroundColor(Color.WHITE);
		topLayout.setGravity(Gravity.CENTER_VERTICAL);

		// 设置左边按钮属性
		Button cancelButton = new Button(context);
		cancelButton.setVisibility(cancelVisible ? View.VISIBLE : View.GONE);
		cancelButton.setTag(TAG_CANCEL);
		RelativeLayout.LayoutParams cancelButtonParam = new RelativeLayout.LayoutParams(DensityUtil.dp2px(context, 60), DensityUtil.dp2px(context, 30));
		cancelButtonParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
		cancelButtonParam.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		cancelButtonParam.leftMargin = 10;
		cancelButton.setLayoutParams(cancelButtonParam);
		cancelButton.setBackgroundResource(R.drawable.bg_corner);
		cancelButton.setGravity(Gravity.CENTER);
		if(!TextUtils.isEmpty(cancelText)) {
			cancelButton.setText(cancelText);
		}
		cancelButton.setTextColor(cancelTextColor);
		cancelButton.setOnClickListener(this);

		topLayout.addView(cancelButton);

		// 设置右边按钮属性
		Button submitButton = new Button(context);
		submitButton.setTag(TAG_SUBMIT);
		RelativeLayout.LayoutParams submitButtonParam = new RelativeLayout.LayoutParams(DensityUtil.dp2px(context, 60), DensityUtil.dp2px(context, 30));
		submitButtonParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		submitButtonParam.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		submitButtonParam.rightMargin = 10;
		submitButton.setLayoutParams(submitButtonParam);
		submitButton.setBackgroundResource(R.drawable.bg_corner);
		submitButton.setGravity(Gravity.CENTER);
		if(!TextUtils.isEmpty(submitText)) {
			submitButton.setText(submitText);
		}
		submitButton.setTextColor(submitTextColor);
		submitButton.setOnClickListener(this);

		topLayout.addView(submitButton);

		rootLayout.addView(topLayout);

		// 设置分割线
		if(topLineVisible) {
			View lineView = new View(context);
			lineView.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, 1));
			lineView.setBackgroundColor(topLineColor);
			rootLayout.addView(lineView);
		}

		rootLayout.addView(getContentView(context));

		return rootLayout;
	}

	@Override
	public void onClick(View view) {
		if(confirmListener != null) {
			if(view.getTag().toString().equals(TAG_SUBMIT)) {
				confirmListener.onConfirm();
			} else {
				confirmListener.onCancel();
			}
		}
		dismiss();
	}

	public void setCancelTextColor(int cancelTextColor) {
		this.cancelTextColor = cancelTextColor;
	}

	public void setCancelText(CharSequence cancelText) {
		this.cancelText = cancelText;
	}

	public void setCancelVisible(boolean cancelVisible) {
		this.cancelVisible = cancelVisible;
	}

	public void setSubmitTextColor(int submitTextColor) {
		this.submitTextColor = submitTextColor;
	}

	public void setSubmitText(CharSequence submitText) {
		this.submitText = submitText;
	}

	public void setTopLineColor(int topLineColor) {
		this.topLineColor = topLineColor;
	}

	public void setTopLineVisible(boolean topLineVisible) {
		this.topLineVisible = topLineVisible;
	}
}
