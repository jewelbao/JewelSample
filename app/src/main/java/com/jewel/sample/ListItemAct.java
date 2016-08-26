package com.jewel.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jewel.base.BaseActivity;
import com.jewel.base.Common;

/**
 Created by Administrator on 2016/2/15 0015. com.jewel.sample  JewelSample
 */
@SuppressWarnings("deprecation")
public class ListItemAct extends BaseActivity implements AdapterView.OnItemClickListener
{
	public static final String KEY = "Item";

	ListView listView;

	/** 类名 */
	String[] clsName;
	/** 类功能标题 */
	String[] items;

	/** 类名ID */
	int[] clsID = {
			R.array.list_class, R.array.text_class, R.array.component_class, R.array.shape_class, R.array.api_class,
			R.array.anim_class, R.array.tools_class, R.array.test_class};

	/** 类标题ID */
	int[] itemID = {
			R.array.list_item, R.array.text_item, R.array.component_item, R.array.shape_item, R.array.api_item,
			R.array.anim_item, R.array.tools_item, R.array.test_item};

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		listView = new ListView(this);
		setBaseContentView(listView);

		int item_pos = getIntent().getIntExtra(KEY, 0);
		/* 标题 */
		String[] title = getResources().getStringArray(R.array.grid_item);

		showTitleBar(title[item_pos]);

		clsName = getResources().getStringArray(clsID[item_pos]);
		items = getResources().getStringArray(itemID[item_pos]);

		listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items));
		listView.setOnItemClickListener(this);
	}

	@SuppressWarnings("rawtypes")
	public void gotoActivity(int position) throws ClassNotFoundException
	{
		// 通过反射机制获得类对象
		Class cls = Class.forName(clsName[position]);
		Bundle bundle = new Bundle();
		bundle.putInt("KEY", position);
		Common.gotoActivityWithData(this, cls, bundle, false);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		try
		{
			gotoActivity(position);
		} catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}
