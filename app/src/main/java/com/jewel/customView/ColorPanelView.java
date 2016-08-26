package com.jewel.customView;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.jewel.customView.impl.OnColorChangeListener;
import com.jewelbao.library.utils.Colors;

/**
 * 颜色选择面板
 *
 * @author Jewel
 * @version 1.0
 * @since 2016/7/8 0008
 */
public class ColorPanelView extends View {

	private static final int[] GRAD_COLORS = new int[]{
			Colors.RED, Colors.YELLOW, Colors.GREEN, Colors.CYAN, Colors.BLUE, Colors.FUCHSIA, Colors.RED
	};

	private static final int[] GRAD_ALPHA = new int[]{Colors.WHITE, Colors.TRANSPARENT};

	private Shader mShader;
	private ColorPanelView mBrightnessGradientView;

	private Paint mPaint;
	private Paint mPaintBackground;

	private RectF mGradientRect = new RectF();
	private float[] mHSV = new float[]{1f, 1f, 1f};

	private int[] mSelectedColorGradient = new int[]{0, Colors.BLACK};
	private int mSelectedColor = 0;
	private float mRadius = 0;
	private boolean mIsBrightnessGradient = false;

	private int lastX = Integer.MAX_VALUE;
	private int lastY;

	private Drawable mPointerDrawable;
	private int mPointerHeight;
	private int mPointerWidth;
	private boolean mLockPointerInBounds = false;

	private OnColorChangeListener onColorChangeListener;

	public ColorPanelView(Context context) {
		super(context);
		init();
	}

	public ColorPanelView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ColorPanelView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void init() {
		setClickable(true);
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaintBackground = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaintBackground.setColor(Colors.WHITE);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			setLayerType(View.LAYER_TYPE_SOFTWARE, isInEditMode() ? null : mPaint);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int desiredWidth = 0;
		int desiredHeight = 0;

		if(mPointerDrawable != null) {
			desiredHeight = mPointerDrawable.getIntrinsicHeight();
			//this is nonsense, but at least have something than 0
			desiredWidth = mPointerDrawable.getIntrinsicWidth();
		}

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		int width, height;

		// 测量宽度
		if(widthMode == MeasureSpec.EXACTLY) {
			// 必须为此宽度
			width = widthSize;
		} else if(widthMode == MeasureSpec.AT_MOST) {
			// 不能大过最小的值
			width = Math.min(desiredWidth, widthSize);
		} else {
			// 可以换成任意值
			width = desiredWidth;
		}

		// 测量高度，同上
		if(heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		} else if(heightMode == MeasureSpec.AT_MOST) {
			height = Math.min(desiredHeight, heightSize);
		} else {
			height = desiredHeight;
		}

		setMeasuredDimension(width, height);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(mShader != null) {
			canvas.drawRoundRect(mGradientRect, mRadius, mRadius, mPaintBackground);
			canvas.drawRoundRect(mGradientRect, mRadius, mRadius, mPaint);
		}

		if(mPointerDrawable != null) {
			int viewHeight = getHeight();
			int pointerWidth = mPointerWidth >> 1;
			int pointerHeight = mPointerHeight >> 1;

			float tX, tY;
			if(!mIsBrightnessGradient) {
				tX = lastX - pointerWidth;
				tY = lastY - pointerHeight;
			} else {
				tX = lastX - pointerWidth;
				tY = mPointerHeight != mPointerDrawable.getIntrinsicHeight() ? (viewHeight >> 1) - pointerHeight : 0;
			}

			if(mLockPointerInBounds) {
				tX = Math.max(mGradientRect.left, Math.min(tX, mGradientRect.right - mPointerWidth));
				tY = Math.max(mGradientRect.top, Math.min(tY, mGradientRect.bottom - mPointerHeight));
			} else {
				tX = Math.max(mGradientRect.left - pointerWidth, Math.min(tX, mGradientRect.right - pointerWidth));
				tY = Math.max(mGradientRect.top - pointerWidth, Math.min(tY, mGradientRect.bottom - pointerHeight));
			}

			canvas.translate(tX, tY);
			mPointerDrawable.draw(canvas);
			canvas.translate(-tX, -tY);
		}
	}

	@Override
	public void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);

		mGradientRect.set(getPaddingLeft(), getPaddingTop(), right - left - getPaddingRight(), bottom - top - getPaddingBottom());

		if(changed) {
			buildShader();
		}

		if(mPointerDrawable != null) {
			int height = (int) mGradientRect.height();
			int pointerH = mPointerDrawable.getIntrinsicHeight();
			int pointerW = mPointerDrawable.getIntrinsicWidth();

			mPointerHeight = pointerH;
			mPointerWidth = pointerW;

			if(height < pointerH) {
				mPointerHeight = height;
				mPointerWidth = (int) (pointerW * (height / (float) pointerH));
			}
			mPointerDrawable.setBounds(0, 0, mPointerWidth, mPointerHeight);
			updatePointerPosition();
		}
	}

	private void buildShader() {
		if(mIsBrightnessGradient) {
			mShader = new LinearGradient(mGradientRect.left, mGradientRect.top, mGradientRect.right, mGradientRect.top, mSelectedColorGradient, null, Shader.TileMode.CLAMP);
		} else {
			LinearGradient gradientShader = new LinearGradient(mGradientRect.left, mGradientRect.top, mGradientRect.right, mGradientRect.top, GRAD_COLORS, null, Shader.TileMode.CLAMP);
			LinearGradient alphaShader = new LinearGradient(0, mGradientRect.top + (mGradientRect.height() / 3), 0, mGradientRect.bottom, GRAD_ALPHA, null, Shader.TileMode.CLAMP);
			mShader = new ComposeShader(alphaShader, gradientShader, PorterDuff.Mode.MULTIPLY);
		}
		mPaint.setShader(mShader);
	}

	private void updatePointerPosition() {
		if(mGradientRect.width() != 0 && mGradientRect.height() != 0) {
			if(!mIsBrightnessGradient) {
				lastX = (int) (mGradientRect.left + ((mHSV[0] * mGradientRect.width()) / 360));
				lastY = (int) (mGradientRect.top + (mGradientRect.height() * (1 - mHSV[1])));
			} else {
				lastX = (int) (mGradientRect.left + (mGradientRect.width() * (1 - mHSV[2])));
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		lastX = (int) event.getX();
		lastY = (int) event.getY();

		updateColorSelection(lastX, lastY);
		invalidate();

		switch(event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				getParent().requestDisallowInterceptTouchEvent(true); // 拦截触摸事件
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				getParent().requestDisallowInterceptTouchEvent(false); // 还原触摸事件
				break;
		}

		return super.onTouchEvent(event);
	}

	/**
	 * 更新颜色
	 *
	 * @param x X坐标
	 * @param y Y坐标
	 */
	private void updateColorSelection(int x, int y) {
		x = (int) Math.max(mGradientRect.left, Math.min(x, mGradientRect.right));
		y = (int) Math.max(mGradientRect.top, Math.min(y, mGradientRect.bottom));

		if(mIsBrightnessGradient) {
			mHSV[2] = 1 - (1.f / mGradientRect.width() * (x - mGradientRect.left));
		} else {
			mHSV[0] = (x - mGradientRect.left) * 360f / mGradientRect.width();
			mHSV[1] = 1 - (1.f / mGradientRect.height() * (y - mGradientRect.top));
			mHSV[2] = 1f;
		}
		mSelectedColor = Color.HSVToColor(mHSV);

		dispatchColorChanged(mSelectedColor);
	}

	/**
	 * Get start color for gradient
	 *
	 * @param HSV
	 * @return
	 */
	private int getColorForGradient(float[] HSV) {
		if(HSV[2] != 1f) {
			float oldV = HSV[2];
			HSV[2] = 1f;
			int color = Color.HSVToColor(HSV);
			HSV[2] = oldV;
			return color;
		} else {
			return Color.HSVToColor(HSV);
		}
	}

	private void dispatchColorChanged(int color) {
		if(mBrightnessGradientView != null) {
			mBrightnessGradientView.setColor(color, false);
		}
		if(onColorChangeListener != null) {
			onColorChangeListener.onColorChanged(this, color);
		}
	}

	protected void setColor(int selectedColor, boolean updatePointers) {
		Color.colorToHSV(selectedColor, mHSV);
		if(mIsBrightnessGradient) {
			mSelectedColorGradient[0] = getColorForGradient(mHSV);
			mSelectedColor = Color.HSVToColor(mHSV);
			buildShader();
			if(lastX != Integer.MIN_VALUE) {
				mHSV[2] = 1 - (1.f / mGradientRect.width() * (lastX - mGradientRect.left));
			}
			selectedColor = Color.HSVToColor(mHSV);
		}
		if(updatePointers) {
			updatePointerPosition();
		}
		mSelectedColor = selectedColor;
		invalidate();
		dispatchColorChanged(mSelectedColor);
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();
		SavedState savedState = new SavedState(superState);
		savedState.isBrightnessGradient = mIsBrightnessGradient;
		savedState.color = mSelectedColor;
		return savedState;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		if(!(state instanceof SavedState)) {
			super.onRestoreInstanceState(state);
			return;
		}

		SavedState savedState = (SavedState) state;
		super.onRestoreInstanceState(savedState.getSuperState());

		mIsBrightnessGradient = savedState.isBrightnessGradient;
		setColor(savedState.color, true);
	}

	/**
	 * 设置颜色选择监听
	 *
	 * @param listener
	 */
	public void setOnColorChangeListener(OnColorChangeListener listener) {
		this.onColorChangeListener = listener;
	}

	public void setPointerDrawable(Drawable pointerDrawable) {
		if(mPointerDrawable != pointerDrawable) {
			mPointerDrawable = pointerDrawable;
			requestLayout();
		}
	}

	public void setLockPointerInBounds(boolean lock) {
		if(lock != mLockPointerInBounds) {
			mLockPointerInBounds = lock;
			invalidate();
		}
	}

	public void setColor(int selectedColor) {
		setColor(selectedColor, true);
	}

	public int getSelectedColor() {
		return mSelectedColor;
	}

	/**
	 * Switch view into brightness gradient only
	 *
	 * @param isBrightnessGradient
	 */
	public void setIsBrightnessGradient(boolean isBrightnessGradient) {
		mIsBrightnessGradient = isBrightnessGradient;
	}

	/**
	 * Add reference for brightness view
	 *
	 * @param brightnessGradientView
	 */
	public void setBrightnessGradientView(ColorPanelView brightnessGradientView) {
		if(mBrightnessGradientView != brightnessGradientView) {
			mBrightnessGradientView = brightnessGradientView;

			if(mBrightnessGradientView != null) {
				mBrightnessGradientView.setIsBrightnessGradient(true);
				mBrightnessGradientView.setColor(mSelectedColor);
			}
		}
	}

	private static class SavedState extends BaseSavedState {

		int color;
		boolean isBrightnessGradient;

		private SavedState(Parcel source) {
			super(source);
			color = source.readInt();
			isBrightnessGradient = source.readInt() == 1;
		}

		SavedState(Parcelable superState) {
			super(superState);
		}

		@Override
		public void writeToParcel(Parcel out, int flags) {
			super.writeToParcel(out, flags);
			out.writeInt(color);
			out.writeInt(isBrightnessGradient ? 1 : 0);
		}

		public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
			public SavedState createFromParcel(Parcel in) {
				return new SavedState(in);
			}

			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}
		};
	}
}
