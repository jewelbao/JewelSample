package com.jewel.sample.Anim;

import android.os.Bundle;

import com.jewel.base.JBaseAct;
import com.jewel.sample.R;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/2/24 0024.
 * com.jewel.sample.Anim 
 * JewelSample
 */
public class AnimViewAct extends JBaseAct
{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_anim_view);

        showTitleBar(R.string.title_anim_view);

        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
