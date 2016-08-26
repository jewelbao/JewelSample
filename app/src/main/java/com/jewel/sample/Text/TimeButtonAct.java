package com.jewel.sample.Text;

import android.os.Build;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.jewel.base.BaseActivity;
import com.jewel.base.BaseApp;
import com.jewel.sample.R;
import com.jewelbao.customview.Button.TimeButton;

/**
 * Created by Jewel on 2016/5/20 0020.
 * 倒计时按钮Sample
 */
@SuppressWarnings("deprecation")
public class TimeButtonAct extends BaseActivity
{
	private TimeButton button;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		button = new TimeButton(this);
		setBaseContentView(button);

		showTitleBar(R.string.title_time_text);

		button.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 200));
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			button.setTextColor(getResources().getColor(R.color.colorAccent, getTheme()));
		} else  {
			button.setTextColor(getResources().getColor(R.color.colorAccent));
		}
		button.setTextBefore(button.getTextBefore());
		button.setOnClickListener(v -> button.start());

		button.setTimeMap(BaseApp.timeMap);
		button.onCreate();
	}

	@Override
	protected void onDestroy() {
		BaseApp.timeMap = button.getTimeMap();
		button.onDestroy();
		super.onDestroy();
	}
}
