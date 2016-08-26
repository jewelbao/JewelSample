package com.jewel.customView;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jewel.base.Common;
import com.jewel.base.Constant;
import com.jewel.base.Global;
import com.jewel.sample.R;

/**
 * Created by Kevin on 2015/9/11.
 * 标题栏
 */
public class TitleBar extends LinearLayout {

    private Activity currentActivity;
    //    private LayoutInflater mInflater;
    private View mRootView;

    // 标题布局
    private LinearLayout layout_title;
    // 左边图案
    private TextView tv_left;
    // 右边图案
    private TextView tv_right;
    // 标题文字
    private TextView tv_title;
    // 标题底部横线
//	private View view_bottom_line;

    public static final int ID;

    static {
        ID = 1;
    }

    public TitleBar(Context context) {
        super(context);
        if (isInEditMode())
            return;

        init();
    }

    public TitleBar(Context context, AttributeSet attr) {
        super(context, attr);
        if (isInEditMode())
            return;

        init();
    }

    // 初始化
    private void init() {
//		setId(ID);
        mRootView = Global.mInflater.inflate(R.layout.layout_titlebar, null);
        layout_title = (LinearLayout) mRootView.findViewById(R.id.rl_title);
        tv_title = (TextView) mRootView.findViewById(R.id.tv_title);
        tv_left = (TextView) mRootView.findViewById(R.id.tv_left);
        tv_right = (TextView) mRootView.findViewById(R.id.tv_right);
//		view_bottom_line = (View) mRootView.findViewById(R.id.view_bottom_line);

        hideLeftButton();
        hideRightButton();

        // 默认左边按钮的功能为结束当前活动
        tv_left.setOnClickListener(mBackListener);

        addView(mRootView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        mRootView.post(() -> {
			LayoutParams params = (LayoutParams) mRootView.getLayoutParams();
			params.height = (int) getResources().getDimension(R.dimen.titleBar_height);
			mRootView.setLayoutParams(params);
		});
    }

    /**
     * 结束当前活动事件
     */
    private OnClickListener mBackListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (currentActivity != null) {
                currentActivity.finish();
                currentActivity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
            }
        }
    };

    /**
     * 隐藏左边按钮
     */
    public void hideLeftButton() {
        if (tv_left.isShown())
            tv_left.setVisibility(View.INVISIBLE);
    }

    /**
     * 显示左边按钮
     */
    public void showLeftButton() {
        if (!tv_left.isShown())
            tv_left.setVisibility(View.VISIBLE);
    }

    /**
     * 设置左边按钮事件
     *
     * @param listener 监听事件
     */
    public void setLeftButtonListener(OnClickListener listener) {
        tv_left.setOnClickListener(listener);
    }

    /**
     * 设置左边按钮背景
     *
     * @param resID 左侧控件背景资源
     */
    public void setLeftButtonBackground(int resID) {
        Common.setTextDrawable(tv_left, resID, Constant.LEFT);
//        tv_left.setBackgroundResource(resID);
    }

    /**
     * 设置标题栏背景
     *
     * @param resID 标题栏背景资源
     */
    public void setTitleBarBackground(int resID) {
        layout_title.setBackgroundResource(resID);
    }

    /**
     * 设置标题
     *
     * @param resID 标题文字资源
     */
    public void setTitle(int resID) {
        tv_title.setText(resID);
    }

    public void  setTitle(String title) {
        tv_title.setText(title);
    }

    /**
     * 设置标题颜色
     *
     * @param colorID 标题字体颜色
     */
    @SuppressWarnings("deprecation")
    public void setTitleColor(int colorID) {
        tv_title.setTextColor(getResources().getColor(colorID));
    }

    /**
     * 获取标题View
     *
     * @return 标题TextView
     */
    public TextView getTitle() {
        return this.tv_title;
    }

    /**
     * 关联当前活动
     *
     * @param currentActivity 当前活动
     */
    public void setActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }


    /**
     * 隐藏右边按钮
     */
    public void hideRightButton() {
        if (tv_right.isShown())
            tv_right.setVisibility(View.INVISIBLE);
    }

    /**
     * 显示右边按钮
     */
    public void showRightButton() {
        if (!tv_right.isShown())
            tv_right.setVisibility(View.VISIBLE);
    }

    /**
     * 设置右边按钮事件
     *
     * @param listener 监听事件
     */
    public void setRightButtonListener(OnClickListener listener) {
        tv_right.setOnClickListener(listener);
    }

    @SuppressWarnings("unused")
    public View getRightButton() {
        return tv_right;
    }

    /**
     * 设置右边按钮背景
     *
     * @param resID 背景资源ID
     */
    public void setRightButtonBackground(int resID) {
        Common.setTextDrawable(tv_right, resID, Constant.RIGHT);
//        tv_right.setBackgroundResource(resID);
    }

    /**
     * 设置右边按钮文字
     *
     * @param textID 文字资源ID
     */
    public void setRightButtonText(int textID) {
        tv_right.setText(textID);
    }

    /**
     * 设置右边按钮文字大小
     *
     * @param size 文字大小
     */
    @SuppressWarnings("unused")
    public void setRightButtonTextSize(float size) {
        tv_right.setTextSize(size);
    }

    /**
     * 设置右边按钮宽高
     *
     * @param width 宽
     * @param height 高
     */
    public void setRightButtonSize(int width, int height) {
        LayoutParams params = (LayoutParams) tv_right.getLayoutParams();
        params.width = width;
        params.height = height;
        tv_right.setLayoutParams(params);
    }
}
