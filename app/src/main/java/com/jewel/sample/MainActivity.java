package com.jewel.sample;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.jewel.base.Common;
import com.jewel.base.JBaseAct;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class MainActivity extends JBaseAct
{
	/**
	 * 类功能标题
	 */
	String[] items;

	@Bind(R.id.gridview)
	GridView gridview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseContentView(R.layout.activity_main);

		showTitleBar(R.string.app_name);

		ButterKnife.bind(this);

		items = getResources().getStringArray(R.array.grid_item);


		gridview.setAdapter(new ArrayAdapter<>(this,
				android.R.layout.simple_list_item_1, items));
	}

	public void gotoActivity(int position) throws ClassNotFoundException {
		// 通过反射机制获得类对象
		Bundle bundle = new Bundle();
		bundle.putInt(ListItemAct.KEY, position);
		Common.gotoActivityWithData(this, ListItemAct.class,
				bundle, false);
	}

	@OnItemClick(R.id.gridview)
	void onItemClick(int position) {
		try {
			gotoActivity(position);
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
