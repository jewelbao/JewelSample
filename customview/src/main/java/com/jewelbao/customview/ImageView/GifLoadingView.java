package com.jewelbao.customview.ImageView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;

import com.jewelbao.customview.R;

import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Jewel on 2016/6/22 0022.
 * gif动画View
 */
public class GifLoadingView extends BlurDialogFragment{

	private Dialog mDialog;
	private GradientDrawable gradientDrawable;
	private GifImageView mGifImageView;

	private int radius = 10;
	private float downScaleFactor = 5.0f;
	private int cornerRadius = 30;
	private int backgroundColor = Color.parseColor("#19890331");
	private int id = 0;

	private boolean dimming = true; // 是否模糊
	private boolean blurredActionBar = false;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		if(mDialog == null) {
			mDialog = new Dialog(getActivity(), R.style.DialogForGif);
			mDialog.setContentView(R.layout.dialog_gif);
			mDialog.setCanceledOnTouchOutside(true);
			mDialog.getWindow().setGravity(Gravity.CENTER);

			gradientDrawable = new GradientDrawable();
			gradientDrawable.setCornerRadius(cornerRadius);

			mGifImageView = (GifImageView) mDialog.findViewById(R.id.gifImageView);

			if(id == 0) {
				id = R.drawable.num0;
			}
			int color = getPixColor(BitmapFactory.decodeResource(getResources(), id));
			setBackgroundColor(color);
			mGifImageView.setImageResource(id);
			gradientDrawable.setColor(backgroundColor);
			if(Build.VERSION.SDK_INT >= 16) {
				mDialog.findViewById(R.id.background).setBackground(gradientDrawable);
			} else {
				mDialog.findViewById(R.id.background).setBackgroundColor(color);
			}
		}
		return mDialog;
	}

	@Override
	public void onDismiss(DialogInterface dialogInterface) {
		super.onDismiss(dialogInterface);
		mDialog = null;
	}

	@Override
	protected boolean isDimmingEnable() {
		return dimming;
	}

	@Override
	protected boolean isActionBarBlurred() {
		return blurredActionBar;
	}

	@Override
	protected float getDownScaleFactor() {
		return downScaleFactor;
	}

	@Override
	protected int getBlurRadius() {
		return radius;
	}

	@Override
	protected boolean isDebugEnable() {
		return false;
	}

	public void setResource(int id) {
		this.id = id;

		if(mDialog == null) {
			return;
		}

		int color = getPixColor(BitmapFactory.decodeResource(getResources(), id));
		// 设置默认背景
		setBackgroundColor(color);
		// 设置默认Gif
		mGifImageView.setImageResource(id);
		gradientDrawable.setColor(backgroundColor);
		if(Build.VERSION.SDK_INT >= 16) {
			mDialog.findViewById(R.id.background).setBackground(gradientDrawable);
		} else {
			mDialog.findViewById(R.id.background).setBackgroundColor(color);
		}
	}

	/**
	 * 设置对话框背景
	 * @param color
	 */
	private void setBackgroundColor(int color) {
		this.backgroundColor = color;
		gradientDrawable.setColor(color);
		if(Build.VERSION.SDK_INT >= 16) {
			mDialog.findViewById(R.id.background).setBackground(gradientDrawable);
		} else {
			mDialog.findViewById(R.id.background).setBackgroundColor(color);
		}
	}

	private int getPixColor(Bitmap src) {
		return src.getPixel(5, 6);
	}
}
