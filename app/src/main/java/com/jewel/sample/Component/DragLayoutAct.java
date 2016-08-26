package com.jewel.sample.Component;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jewel.base.JBaseAct;
import com.jewel.fragment.DragLayoutFragment1;
import com.jewel.fragment.DragLayoutFragment3;
import com.jewel.sample.R;
import com.jewelbao.customview.ViewGroup.taobao.DragLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Jewel
 * @version 1.0
 * @since 2016/8/22 0022
 */
public class DragLayoutAct extends JBaseAct {

	@Bind(R.id.first)
	FrameLayout first;
	@Bind(R.id.second)
	FrameLayout second;
	@Bind(R.id.draglayout)
	DragLayout draglayout;

//	@Bind(R.id.banner_guide_content)
//	BGABanner banner;
//	@Bind(R.id.tv_introduce)
//	TextView tvIntroduce;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContentView(R.layout.activity_drag);

		showTitleBar(R.string.title_component_scroll);

		ButterKnife.bind(this);

		initView();
//		banner.setData(Arrays.asList(R.mipmap.blue_square, R.mipmap.dark_green_square, R.mipmap.dark_yellow_square), null);
//		banner.setAdapter((banner1, view, model, position) -> ((ImageView) view).setImageResource((int) model));
//		banner.setOnItemClickListener((banner1, view, model, position) -> JToast.showLong(DragLayoutAct.this, "position:" + position));
//		changePartOfTextColor(tvIntroduce, "");
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		DragLayoutFragment1 fragment1 = new DragLayoutFragment1();
		DragLayoutFragment3 fragment3 = new DragLayoutFragment3();

		getSupportFragmentManager().beginTransaction()
				.add(R.id.first, fragment1).add(R.id.second, fragment3)
				.commit();

		DragLayout.ShowNextPageNotifier nextIntf = () -> fragment3.initView();
		draglayout = (DragLayout) findViewById(R.id.draglayout);
		draglayout.setNextPageListener(nextIntf);
	}

	/**
	 * 改变部分文字颜色
	 *
	 * @param textView
	 * @param id
	 */
	private void changePartOfTextColor(TextView textView, String id) {
		SpannableStringBuilder builder = new SpannableStringBuilder("[" + getPlatformId(id) + "]");
		//ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
		ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
		int redColorLen = builder.length();
		builder.append(textView.getText().toString());
		builder.setSpan(redSpan, 0, redColorLen, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		textView.setText(builder);
	}

	/**
	 * 获取平台id
	 */
	public static String getPlatformId(String id) {
		switch(id) {
			case "2":
				return "京东自营";
			case "3":
				return "唯品会";
			case "4":
				return "天猫";
			case "1":
				return "聚美优品";
			default:
				return "XXXXX";
		}
	}
}
