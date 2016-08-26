package com.jewel.sample.Tools;

import android.os.Bundle;

import com.jewel.base.JBaseAct;
import com.jewel.customView.Switch.ColorPicker;
import com.jewel.sample.R;
import com.jewelbao.library.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Jewel
 * @version 1.0
 * @since 2016/7/9 0009
 */
public class SwitchDialogAct extends JBaseAct {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContentView(R.layout.activity_dialog_tool);

		showTitleBar(R.string.title_tools_switch);

		ButterKnife.bind(this);
	}

	@OnClick(R.id.btn_color_picker)
	void picker() {
		ColorPicker picker = new ColorPicker(this);
		picker.setDefaultColor(0xaaaaaa);
		picker.setOnColorPickListener(pickedColor -> ToastUtils.showToast(picker.colorToString(pickedColor, true)));
		picker.show();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
}
