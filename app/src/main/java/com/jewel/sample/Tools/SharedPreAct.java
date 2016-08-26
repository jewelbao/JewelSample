package com.jewel.sample.Tools;

import android.os.Bundle;
import android.widget.TextView;

import com.jewel.base.JBaseAct;
import com.jewel.sample.R;
import com.jewelbao.library.utils.SharedPre;

/**
 * Created by PC on 2016/4/26.
 * 即时存储SharedPreference
 */
public class SharedPreAct extends JBaseAct {
	private static final String KEY = "test_key";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContentView(R.layout.activity_jumping_text);

		showTitleBar(R.string.title_jump_text);
	}

	@Override
	protected void onResume() {
		super.onResume();

		int howMany = SharedPre.getInt(KEY, 1);

		TextView textView = (TextView) findViewById(R.id.jumping_text_1);
		if(textView != null)
			textView.setText("SharedPre存储值:" + howMany);

		SharedPre.putInt(KEY, howMany + 1);

	}
}
