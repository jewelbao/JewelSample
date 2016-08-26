package com.jewel.sample.ListView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jewel.base.JBaseAct;
import com.jewel.sample.R;
import com.jewelbao.customview.ListView.UnderLineLinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jewel on 2016/6/13 0013.
 */
public class UnderLineTimeAct extends JBaseAct {

	@Bind(R.id.underline_layout_vertical)
	UnderLineLinearLayout underlineLayoutVertical;
	@Bind(R.id.underline_layout_horizontal)
	UnderLineLinearLayout underlineLayoutHorizontal;

	@Bind(R.id.add)
	Button add;
	@Bind(R.id.sub)
	Button sub;
	@Bind(R.id.orientation)
	Button orientation;

	boolean isVertical = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContentView(R.layout.activity_under_line_timestamp);
		showTitleBar(R.string.title_underLineTime);

		ButterKnife.bind(this);
	}

	@OnClick(R.id.add)
	public void add() {
		if(isVertical) {
			addItemVertical();
		} else {
			addItemHorizontal();
		}
	}

	@OnClick(R.id.sub)
	public void sub() {
		if(isVertical) {
			subItemVertical();
		} else {
			subItemHorizontal();
		}
	}

	@OnClick(R.id.orientation)
	public void changeOrientation() {
		isVertical = !isVertical;
		String orientationText = isVertical ? "vertical" : "horizontal";
		String addText = isVertical ? "add(Vertical)" : "add(horizontal)";
		String subText = isVertical ? "sub(Vertical)" : "sub(horizontal)";
		orientation.setText(orientationText);
		add.setText(addText);
		sub.setText(subText);
	}

	int VerticalCount = 0;

	private void addItemVertical() {
		View v = LayoutInflater.from(this).inflate(R.layout.underline_item_vertical, underlineLayoutVertical, false);
		((TextView) v.findViewById(R.id.tx_action)).setText("this is test " + VerticalCount);
		((TextView) v.findViewById(R.id.tx_action_time)).setText("2016-01-21");
		((TextView) v.findViewById(R.id.tx_action_status)).setText("finish");
		underlineLayoutVertical.addView(v);
		VerticalCount++;
	}

	private void subItemVertical() {
		if (underlineLayoutVertical.getChildCount() > 0) {
			underlineLayoutVertical.removeViews(underlineLayoutVertical.getChildCount() - 1, 1);
			VerticalCount--;
		}
	}

	int horizontalCount = 0;

	private void addItemHorizontal() {
		View v = LayoutInflater.from(this).inflate(R.layout.underline_item_horizontal, underlineLayoutHorizontal, false);
		((TextView) v.findViewById(R.id.tx_action)).setText("this is test " + horizontalCount);
		((TextView) v.findViewById(R.id.tx_action_time)).setText("2016-01-21");
		underlineLayoutHorizontal.addView(v);
		horizontalCount++;
	}

	private void subItemHorizontal() {
		if (underlineLayoutHorizontal.getChildCount() > 0) {
			underlineLayoutHorizontal.removeViews(underlineLayoutHorizontal.getChildCount() - 1, 1);
			horizontalCount--;
		}
	}
}
