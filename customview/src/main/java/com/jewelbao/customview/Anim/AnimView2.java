package com.jewelbao.customview.Anim;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Administrator on 2016/2/26 0026.
 * com.jewelbao.customview.Anim 
 * JewelSample
 */
public class AnimView2 extends SurfaceView implements SurfaceHolder.Callback
{

    private SurfaceHolder holder;

    public AnimView2 (Context context)
    {
        super(context);
    }

    public AnimView2 (Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public AnimView2 (Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void surfaceCreated (SurfaceHolder holder)
    {

    }

    @Override
    public void surfaceChanged (SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceDestroyed (SurfaceHolder holder)
    {

    }
}
