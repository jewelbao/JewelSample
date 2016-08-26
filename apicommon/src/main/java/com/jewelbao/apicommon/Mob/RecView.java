package com.jewelbao.apicommon.Mob;

import android.content.Context;
import android.util.AttributeSet;

import cn.sharerec.recorder.impl.SrecGLSurfaceView;

/**
 * Created by Jewel on 2016/5/24 0024.
 */
public class RecView extends SrecGLSurfaceView{

	public RecView(Context context) {
		super(context);
	}

	public RecView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
	}

	@Override
	protected String getShareRecAppkey() {
		return ApiData.MOB_KEY;
	}

	@Override
	protected String getShareRecAppSecret() {
		return ApiData.MOB_SECRET;
	}
}
