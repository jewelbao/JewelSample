package com.jewel.sample.Test;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;

import com.jewel.sample.R;

/**
 * Created by PC on 2016/3/3.
 */
public class CoordinatorLayoutAct extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

//		Toolbar toolbar = (Toolbar) this.findViewById(R.id.tool_bar);
//		setSupportActionBar(toolbar);
//		ActionBar actionBar = getSupportActionBar();
//		actionBar.setHomeAsUpIndicator(android.R.drawable.ic_input_delete);
//		actionBar.setDisplayHomeAsUpEnabled(true);

		CollapsingToolbarLayout collapsingToolbar =
				(CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
		if(collapsingToolbar != null)
			collapsingToolbar.setTitle("详情界面");

//		List<String> data = new ArrayList<>();
//		for(int i = 0; i < 20; i++)
//		{
//			data.add(i + "");
//		}
//
//		ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
//														data);
//		list.setAdapter(adapter);
	}

}
