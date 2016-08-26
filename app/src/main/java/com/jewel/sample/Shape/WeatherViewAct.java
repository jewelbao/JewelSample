package com.jewel.sample.Shape;

import android.os.Bundle;

import com.jewel.base.JBaseAct;
import com.jewel.sample.R;

/**
 Created by PC on 2016/4/27.
 */
public class WeatherViewAct extends JBaseAct
{

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setBaseContentView(R.layout.activity_weather);

		showTitleBar(R.string.title_shape_weather);
	}
}
