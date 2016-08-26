package com.jewel.sample.Test;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

import com.jewel.base.BaseActivity;
import com.jewel.sample.R;
import com.jewel.util.ShakeDetector;
import com.jewelbao.customview.ImageView.PictureView;

/**
 Created by PC on 2016/4/27.
 */
@SuppressWarnings("deprecation")
public class ShakeDetectorAct extends BaseActivity implements ShakeDetector.Listener
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		ShakeDetector shakeDetector = new ShakeDetector(this);
		shakeDetector.start(sensorManager);

//		TextView textView = new TextView(this);
//		textView.setGravity(Gravity.CENTER);
//		textView.setText("Fuck Xiao Mi, SHIT!!!");

		PictureView view = new PictureView(this);
		view.setImageResource(R.mipmap.ic_launcher);

		setBaseContentView(view);
	}

	@Override
	public void hearShake()
	{
		Toast.makeText(this, "Don't shake me, bro!", Toast.LENGTH_SHORT).show();
	}
}
