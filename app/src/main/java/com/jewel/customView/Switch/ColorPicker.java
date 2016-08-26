package com.jewel.customView.Switch;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jewel.customView.ColorPanelView;
import com.jewel.customView.impl.OnColorPickListener;
import com.jewelbao.customview.Dialog.ConfirmPopup;
import com.jewelbao.customview.Dialog.impl.OnConfirmListener;
import com.jewelbao.library.utils.Colors;
import com.jewelbao.library.utils.CompatUtils;
import com.jewelbao.library.utils.DensityUtil;

import java.util.Locale;

/**
 * 颜色选择器
 *
 * @author Jewel
 * @version 1.0
 * @since 2016/7/8 0008
 */
public class ColorPicker extends ConfirmPopup<LinearLayout> implements TextView.OnEditorActionListener {

	private static final int MULTI_ID = 0x1;
	private static final int BLACK_ID = 0x2;

	private String tipText = "颜色值(点击颜色值可编辑)--";
	private int defaultColor = Colors.WHITE;

	private ColorPanelView multiColorView, blackColorView;
	private EditText hexValView;
	private TextView tipView;

	private ColorStateList hexValDefaultColor;
	private OnColorPickListener onColorPickListener;

	public ColorPicker(Context context) {
		super(context);
	}

	public void setDefaultColor(int color) {
		this.defaultColor = color;
	}

	public void setOnColorPickListener(OnColorPickListener listener) {
		this.onColorPickListener = listener;
	}

	@Override
	protected LinearLayout getContentView(Context context) {
		LinearLayout rootLayout = new LinearLayout(context);
		rootLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
		rootLayout.setOrientation(LinearLayout.VERTICAL);
		// 配置顶部颜色条
		blackColorView = new ColorPanelView(context);
		//noinspection ResourceType
		blackColorView.setId(BLACK_ID);
		blackColorView.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, DensityUtil.dp2px(context, 25)));
		blackColorView.setPointerDrawable(CompatUtils.getDrawable(context, android.R.drawable.ic_lock_idle_lock));
		blackColorView.setLockPointerInBounds(false);
		blackColorView.setOnColorChangeListener((view, color) -> updateCurrentColor(color));

		rootLayout.addView(blackColorView);

		// 配置全颜色内容布局
		multiColorView = new ColorPanelView(context);
		//noinspection ResourceType
		multiColorView.setId(MULTI_ID);
		multiColorView.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, 0, 1.0F));
		multiColorView.setPointerDrawable(CompatUtils.getDrawable(context, android.R.drawable.ic_delete));
		multiColorView.setLockPointerInBounds(true);
		multiColorView.setOnColorChangeListener((view, color) -> updateCurrentColor(color));

		rootLayout.addView(multiColorView);

		LinearLayout hexValLayout = new LinearLayout(context);
		hexValLayout.setOrientation(LinearLayout.HORIZONTAL);
		hexValLayout.setGravity(Gravity.CENTER_VERTICAL);
		hexValLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, DensityUtil.dp2px(context, 50)));

		// 配置底部提示文字
		tipView = new TextView(context);
		tipView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
		tipView.setTextColor(Colors.BLACK);
		tipView.setShadowLayer(5, 0, 3, Colors.RED_DARK);
		tipView.setGravity(Gravity.CENTER);
		tipView.setPadding(0, 0, 0, 0);
		tipView.setText(tipText);

		hexValLayout.addView(tipView);

		// 配置底部编辑框
		hexValView = new EditText(context);
		hexValView.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 1.0f));
		hexValView.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
		hexValView.setImeOptions(EditorInfo.IME_ACTION_DONE);
		hexValView.setGravity(Gravity.CENTER);
		hexValView.setBackgroundColor(defaultColor);
		hexValView.setTextColor(Colors.BLACK);
		hexValView.setShadowLayer(5, 0, 2, Colors.YELLOW_LIGHT);//设置阴影，以便背景色为黑色系列时仍然看得见
		hexValView.setMaxEms(8);
		hexValView.setMinEms(6);
		hexValView.setPadding(0, 0, 0, 0);
		hexValView.setSingleLine(true);
		hexValView.setOnEditorActionListener(this);

		hexValDefaultColor = hexValView.getTextColors();

		hexValLayout.addView(hexValView);
		rootLayout.addView(hexValLayout);
		return rootLayout;
	}

	@Override
	protected boolean isFixedHeight() {
		return true;
	}

	@Override
	protected void setContentViewAfter(View contentView) {
		multiColorView.setColor(defaultColor);//将触发onColorChanged，故必须先待其他控件初始化完成后才能调用
		multiColorView.setBrightnessGradientView(blackColorView);
		if(onColorPickListener != null) {
			setConfirmListener(new OnConfirmListener() {
				@Override
				public void onConfirm() {
					onColorPickListener.onColorPicked(Color.parseColor("#" + hexValView.getText()));
				}

				@Override
				public void onCancel() {

				}
			});
		}
	}

	private void updateCurrentColor(int color) {
		String hexColorString = colorToString(color, false).toUpperCase(Locale.getDefault());
		hexValView.setText(hexColorString);
		hexValView.setTextColor(hexValDefaultColor);
		hexValView.setBackgroundColor(color);
		tipView.setText(tipText);
	}

	/**
	 * 颜色值转为6位十六进制颜色代码，不含"#"
	 *
	 * @param color        需要转换的颜色值
	 * @param includeAlpha 是否包含透明值
	 * @return String
	 */
	public String colorToString(int color, boolean includeAlpha) {
		String alpha = Integer.toHexString(Color.alpha(color));
		String red = Integer.toHexString(Color.red(color));
		String green = Integer.toHexString(Color.green(color));
		String blue = Integer.toHexString(Color.blue(color));

		if(alpha.length() == 1) {
			alpha = "0" + alpha;
		}
		if(red.length() == 1) {
			red = "0" + red;
		}
		if(green.length() == 1) {
			green = "0" + green;
		}
		if(blue.length() == 1) {
			blue = "0" + blue;
		}

		String colorString;
		if(includeAlpha) {
			colorString = alpha + red + green + blue;
		} else {
			colorString = red + green + blue;
		}
		return colorString;
	}

	@Override
	public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
		if(i == EditorInfo.IME_ACTION_DONE) {
			InputMethodManager inputMethodManager = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(textView.getWindowToken(), 0);
			String hexString = hexValView.getText().toString();
			int lenght = hexString.length();
			if(lenght == 6 || lenght == 8) {
				try {
					int color = Color.parseColor("#" + hexString);
					multiColorView.setColor(color);
					hexValView.setTextColor(hexValDefaultColor);
					tipView.setText(tipText);
				} catch(IllegalArgumentException e) {
					hexValView.setTextColor(Colors.RED_DARK);
					tipView.setText("颜色值不合法");
				}
			} else {
				hexValView.setTextColor(Colors.RED_DARK);
				tipView.setText("颜色值不合法");
			}
			return true;
		}
		return false;
	}
}
