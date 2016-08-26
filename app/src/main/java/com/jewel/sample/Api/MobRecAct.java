package com.jewel.sample.Api;

import android.os.Bundle;

import com.jewel.base.JBaseAct;
import com.jewel.sample.R;

/**
 * Created by Jewel on 2016/5/24 0024.
 * Mob录制视频API
 */
public class MobRecAct extends JBaseAct {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContentView(R.layout.activity_jumping_text);

		showTitleBar(R.string.title_jump_text);
	}

}
