package com.jewel.sample.Text;

import android.os.Bundle;

import com.jewel.base.JBaseAct;
import com.jewel.sample.R;
import com.jewelbao.customview.TextView.MarqueeView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Jewel on 2016/6/9 0009.
 * 上下跑马灯
 */
public class MarqueeTextAct extends JBaseAct {


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContentView(R.layout.activity_marquee);

		showTitleBar(R.string.title_marquee_text);

		MarqueeView marqueeView = (MarqueeView) findViewById(R.id.marqueeView);
		MarqueeView marqueeView1 = (MarqueeView) findViewById(R.id.marqueeView1);
		MarqueeView marqueeView2 = (MarqueeView) findViewById(R.id.marqueeView2);
		MarqueeView marqueeView3 = (MarqueeView) findViewById(R.id.marqueeView3);
		MarqueeView marqueeView4 = (MarqueeView) findViewById(R.id.marqueeView4);

		List<String> messages = Arrays.asList(getResources().getStringArray(R.array.anim_item));
		if(marqueeView != null && !messages.isEmpty())
			marqueeView.startWithList(messages);

		if(marqueeView1 != null) {
			marqueeView1.startWithMessage("快乐不是梦！梦并不会快乐！想想快乐的真谛！那都是爱哎哎哎！");
		}
		if(marqueeView2 != null) {
			marqueeView2.startWithFixedWidth("快乐不是梦！梦并不会快乐！想想快乐的真谛！那都是爱哎哎哎！", 720);
		}
		if(marqueeView3 != null) {
			marqueeView3.startWithMessage("快乐不是梦！梦并不会快乐！想想快乐的真谛！那都是爱哎哎哎！");
		}
		if(marqueeView4 != null) {
			marqueeView4.startWithMessage("快乐不是梦！梦并不会快乐！想想快乐的真谛！那都是爱哎哎哎！");
		}

	}
}
