package com.jewel.sample.Text;

import android.os.Bundle;
import android.widget.TextView;

import com.jewel.base.JBaseAct;
import com.jewel.sample.R;
import com.jewelbao.library.utils.Jumping.JumpingBeans;

/**
 * Created by PC on 2016/4/22.
 * 跳动的文字
 */
public class JumpTextAct extends JBaseAct {
	private JumpingBeans jumpingBeans1, jumpingBeans2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContentView(R.layout.activity_jumping_text);

		showTitleBar(R.string.title_jump_text);
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Here you can see that we don't duplicate trailing dots on the text (we reuse
		// them or, if it's an ellipsis character, replace it with three dots and animate
		// those instead)
		final TextView textView1 = (TextView) findViewById(R.id.jumping_text_1);
		if(textView1 != null)
			jumpingBeans1 = JumpingBeans.with(textView1)
					.appendJumpingDots() // has bug when onResume() run to this, "..." will create repeat, like first "word..." and next come to "word......"
					.build();

		// Note that, even though we access textView2's text when starting to work on
		// the animation builder, we don't alter it in any way, so we're ok
		final TextView textView2 = (TextView) findViewById(R.id.jumping_text_2);
		if(textView2 != null)
			jumpingBeans2 = JumpingBeans.with(textView2)
					.makeTextJump(0, textView2.getText().toString().indexOf(' '))
					.setIsWave(false)
					.setLoopDuration(1000)
					.build();
	}

	@Override
	protected void onPause() {
		super.onPause();
		jumpingBeans1.stopJumping();
		jumpingBeans2.stopJumping();
	}
}
