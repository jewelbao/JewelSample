package com.jewel.sample.Anim;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;

import com.jewel.base.JBaseAct;
import com.jewel.sample.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/2/15 0015.
 * com.jewel.sample.Anim 
 * 购物车动画
 */
public class ShopAnimAct extends JBaseAct
{


    int[] start_location = new int[2];

    Drawable drawable;
    @Bind(R.id.img1)
    ImageView img1;
    @Bind(R.id.button1)
    Button button1;
    @Bind(R.id.img2)
    ImageView img2;
    @Bind(R.id.button2)
    Button button2;
    @Bind(R.id.img3)
    ImageView img3;
    @Bind(R.id.button3)
    Button button3;
    @Bind(R.id.button)
    Button button;

    // 正在执行的动画数量
    private int number = 0;
    // 是否完成清理
    private boolean isClean = false;
    // 动画层
    private FrameLayout animation_viewGroup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_shopanim);

        showTitleBar(R.string.title_anim_shop);

        ButterKnife.bind(this);


        animation_viewGroup = createAnimLayout();

        button1.setOnClickListener(v -> {
			img1.getLocationInWindow(start_location);// 获取点击商品图片的位置
			drawable = img1.getDrawable();// 复制一个新的商品图标
			doAnim(drawable, start_location);
		});

        button2.setOnClickListener(v -> {
			img2.getLocationInWindow(start_location);// 获取点击商品图片的位置
			drawable = img2.getDrawable();// 复制一个新的商品图标
			doAnim(drawable, start_location);
		});

        button3.setOnClickListener(v -> {
			img3.getLocationInWindow(start_location);// 获取点击商品图片的位置
			drawable = img3.getDrawable();// 复制一个新的商品图标
			doAnim(drawable, start_location);
		});

    }

    /**
     * 执行动画
     *
     * @param drawable 动画drawable
     * @param start_location 开始位置坐标
     */
    private void doAnim(Drawable drawable, int[] start_location) {

        if (!isClean) {
            setAnim(drawable, start_location);
        } else {
            try {
                animation_viewGroup.removeAllViews();
                isClean = false;
                setAnim(drawable, start_location);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                isClean = true;
            }
        }
    }

    /**
     * 创建动画层
     *
     * @return FrameLayout
     */
    private FrameLayout createAnimLayout() {
        ViewGroup rootView = (ViewGroup) getWindow().getDecorView();
        FrameLayout animLayout = new FrameLayout(this);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(params);
        animLayout.setBackgroundResource(android.R.color.transparent);// 完全透明的白色
        rootView.addView(animLayout);
        return animLayout;
    }

    /***
     * 将执行动画的view 添加到动画层（an）
     *
     * @param vg ViewGroup
     * @param view the view want to anim
     * @param location 位置坐标
     * @return the view
     */
    private View addViewToAnimLayout(ViewGroup vg, View view, int[] location) {
        int x = location[0];
        int y = location[1];
        vg.addView(view);
        LayoutParams params = new LayoutParams(dp2pix(this, 90), dp2pix(this,
                90));
        params.leftMargin = x;
        params.topMargin = y;
        view.setPadding(5, 5, 5, 5);
        view.setLayoutParams(params);
        return view;
    }

    /**
     * dp转换成pix
     *
     * @param context 上下文
     * @param dp dp
     * @return px
     */
    private int dp2pix(Context context, int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /***
     * 设置动画效果
     *
     * @param drawable drawable
     * @param start_location 位置坐标
     */
    private void setAnim(Drawable drawable, int[] start_location) {
        Animation scaleAnimation = new ScaleAnimation(1.5f, 0.5f, 1.5f, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.1f, Animation.RELATIVE_TO_SELF,
                0.1f);
        int animationDuration = 1000;
        scaleAnimation.setDuration(animationDuration);
        scaleAnimation.setFillAfter(true);

        final ImageView iv = new ImageView(this);
        iv.setImageDrawable(drawable);
        final View view = addViewToAnimLayout(animation_viewGroup, iv,
                start_location);
        view.setAlpha(0.6f);

        int[] end_location = new int[2];
        button.getLocationInWindow(end_location);
        int endX = end_location[0];
        int endY = end_location[1] - start_location[1];

        Animation translateAnim = new TranslateAnimation(0, endX, 0, endY);
        Animation mRotateAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        translateAnim.setDuration(animationDuration);
        mRotateAnimation.setDuration(animationDuration);

        // 动画添加顺序很重要
        AnimationSet animSet = new AnimationSet(true);
        animSet.setFillAfter(true);
        animSet.addAnimation(mRotateAnimation);
        animSet.addAnimation(scaleAnimation);
        animSet.addAnimation(translateAnim);

        animSet.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                number++;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                number--;
                if (number == 0) {
                    isClean = true;
                    runOnUiThread(() -> {
						try {
							animation_viewGroup.removeAllViews();
						} catch (Exception e) {
                            e.printStackTrace();
						}
						isClean = false;
					});
                }
            }
        });
        // 开始动画
        view.startAnimation(animSet);

    }

    @Override
    public void onLowMemory() {
        isClean = true;
        try {
            animation_viewGroup.removeAllViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
        isClean = false;
        super.onLowMemory();
    }

}
