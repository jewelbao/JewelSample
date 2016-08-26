package com.jewelbao.library.utils.Jumping;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.os.Build;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.text.style.SuperscriptSpan;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 Created by PC on 2016/4/22.
 */
final class JumpingBeansSpan extends SuperscriptSpan implements ValueAnimator.AnimatorUpdateListener
{
	private final WeakReference<TextView> textView;
	private final int delay;
	private final int loopDuration;
	private final float animatedRange;
	private int shift;
	private ValueAnimator jumpAnimator;

	public JumpingBeansSpan(@NonNull TextView textView, @IntRange(from = 1) int loopDuration,
							@IntRange(from = 0) int position,
							@IntRange(from = 0) int waveCharOffset,
							@FloatRange(from = 0, to = 1, fromInclusive = false) float animatedRange)
	{
		this.textView = new WeakReference<TextView>(textView);
		this.delay = waveCharOffset * position;
		this.loopDuration = loopDuration;
		this.animatedRange = animatedRange;
	}

	@Override
	public void updateMeasureState(TextPaint tp)
	{
		initIfNecessary(tp.ascent());
		tp.baselineShift = shift;
	}

	@Override
	public void updateDrawState(TextPaint tp)
	{
		initIfNecessary(tp.ascent());
		tp.baselineShift = shift;
	}

	private void initIfNecessary(float ascent)
	{
		if(jumpAnimator != null)
		{
			return;
		}

		this.shift = 0;
		int maxShift = (int) ascent / 2;
		jumpAnimator = ValueAnimator.ofInt(0, maxShift);
		jumpAnimator.setDuration(loopDuration).setStartDelay(delay);
		jumpAnimator.setInterpolator(new JumpInterpolator(animatedRange));
		jumpAnimator.setRepeatCount(ValueAnimator.INFINITE);
		jumpAnimator.setRepeatMode(ValueAnimator.RESTART);
		jumpAnimator.addUpdateListener(this);
		jumpAnimator.start();
	}

	@Override
	public void onAnimationUpdate(ValueAnimator animation)
	{
		TextView view = textView.get();
		if(view != null)
		{
			updateAnimationFor(animation, view);
		} else
		{
			cleanupAndComplainAboutUserBeingAFool();
		}
	}

	private void updateAnimationFor(ValueAnimator animation, TextView view)
	{
		if(isAttachedToHierarchy(view))
		{
			shift = (int) animation.getAnimatedValue();
			view.invalidate();
		}
	}

	private boolean isAttachedToHierarchy(TextView view)
	{
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
		{
			return view.isAttachedToWindow();
		}
		return view
				.getParent() != null;  // Best-effort fallback (without adding support-v4 just for this...)
	}

	private void cleanupAndComplainAboutUserBeingAFool()
	{
		// The textview has been destroyed and teardown() hasn't been called
		teardown();
		Log.w("JumpingBeans",
			  "!!! Remember to call JumpingBeans.stopJumping() when appropriate !!!");
	}

	public void teardown()
	{
		if(jumpAnimator != null)
		{
			jumpAnimator.cancel();
			jumpAnimator.removeAllListeners();
		}
		if(textView.get() != null)
		{
			textView.clear();
		}
	}


	/**
	 A tweaked {@link android.view.animation.AccelerateDecelerateInterpolator} that covers the full
	 range in a fraction of its input range, and holds on the final value on the rest of the input
	 range. By default, this fraction is 65% of the full range.

	 @see JumpingBeans.Builder#DEFAULT_ANIMATION_DUTY_CYCLE
	 */
	private static class JumpInterpolator implements TimeInterpolator
	{

		private final float animRange;

		public JumpInterpolator(float animatedRange)
		{
			this.animRange = animatedRange;
		}

		@Override
		public float getInterpolation(float input)
		{
			if(input > animRange)
			{
				return 0f;
			}
			double radians = (input / animRange) * Math.PI;
			return (float) Math.sin(radians);
		}
	}
}
