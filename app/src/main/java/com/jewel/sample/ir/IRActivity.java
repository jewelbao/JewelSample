package com.jewel.sample.ir;

import android.os.Bundle;

import com.jewel.base.JBaseAct;
import com.jewel.sample.R;

public class IRActivity extends JBaseAct {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_ir);
        showTitleBar(R.string.app_name);

        InfraredEmitter infraredEmitter = new InfraredEmitter(this);
        infraredEmitter.RC6(36, 2148541968L);
    }
}
