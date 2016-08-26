package com.jewel.util;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.jewel.model.Pointer;

import java.util.ArrayList;

/**
 Created by PC on 2016/4/28. An Airy is a gesture listener.
 */
@SuppressWarnings("unused")
public class Airy implements View.OnTouchListener
{
	private static final int TIME_LIMIT = 300;
	private static final int MOVEMENT_LIMIT_UP = 48;
	/**
	 The gesture ID for an unknown gesture.
	 */
	public static final int UNKNOWN_GESTURE = 0;
	/**
	 The gesture ID for a one finger tap.
	 */
	public static final int ONE_FINGER_TAP = 1;
	/**
	 The gesture ID for a one finger swipe up.
	 */
	public static final int ONE_FINGER_SWIPE_UP = 2;
	/**
	 The gesture ID for a one finger swipe down.
	 */
	public static final int ONE_FINGER_SWIPE_DOWN = 3;
	/**
	 The gesture ID for a one finger swipe left.
	 */
	public static final int ONE_FINGER_SWIPE_LEFT = 4;
	/**
	 The gesture ID for a one finger swipe right.
	 */
	public static final int ONE_FINGER_SWIPE_RIGHT = 5;

	/**
	 The gesture ID for a two finger tap.
	 */
	public static final int TWO_FINGER_TAP = 6;
	/**
	 The gesture ID for a two finger swipe up.
	 */
	public static final int TWO_FINGER_SWIPE_UP = 7;
	/**
	 The gesture ID for a two finger swipe down.
	 */
	public static final int TWO_FINGER_SWIPE_DOWN = 8;
	/**
	 The gesture ID for a two finger swipe left.
	 */
	public static final int TWO_FINGER_SWIPE_LEFT = 9;
	/**
	 The gesture ID for a two finger swipe right.
	 */
	public static final int TWO_FINGER_SWIPE_RIGHT = 10;
	/**
	 The gesture ID for a two finger pinch in.
	 */
	public static final int TWO_FINGER_PINCH_IN = 11;
	/**
	 The gesture ID for a two finger pinch out.
	 */
	public static final int TWO_FINGER_PINCH_OUT = 12;

	private float movementLimitPx;

	private ArrayList<Pointer> mPointers;

	/**
	 Creates a new Airy.

	 @param activity An Activity.
	 */
	public Airy(Activity activity)
	{
		DisplayMetrics displayMetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

		movementLimitPx = MOVEMENT_LIMIT_UP * displayMetrics.densityDpi;
	}


	/**
	 The function called on a touch.

	 @param pView        The View a touch was performed on.
	 @param pMotionEvent The MotionEvent of a performed touch.

	 @return true
	 */
	@Override
	public boolean onTouch(View pView, MotionEvent pMotionEvent)
	{
		int mActionIndex = pMotionEvent.getActionIndex();

		int mPointerID = pMotionEvent.getPointerId(mActionIndex);
		long mEventTime = pMotionEvent.getEventTime();
		float mX = pMotionEvent.getX(mActionIndex);
		float mY = pMotionEvent.getY(mActionIndex);

		switch(pMotionEvent.getActionMasked())
		{
			case MotionEvent.ACTION_DOWN:
				mPointers = new ArrayList<>();
				mPointers.add(new Pointer(mPointerID, mEventTime, mX, mY, movementLimitPx));
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				mPointers.add(new Pointer(mPointerID, mEventTime, mX, mY, movementLimitPx));
				break;
			case MotionEvent.ACTION_POINTER_UP:
				for(int index = mPointers.size() - 1; index >= 0; index--)
				{
					Pointer pointer = mPointers.get(index);

					if(pointer.getId() == mPointerID)
					{
						pointer.setUpInfo(mEventTime, mX, mY);
					}
				}
				break;
			case MotionEvent.ACTION_UP:
				for(int index = mPointers.size() - 1; index >= 0; index--)
				{
					Pointer pointer = mPointers.get(index);

					if(pointer.getId() == mPointerID)
					{
						pointer.setUpInfo(mEventTime, mX, mY);
					}
				}
				onGesture(pView, getGestureID());
				break;
		}
		return true;
	}

	/**
	 The function called on a gesture.

	 @param pView      The View a gesture was performed on.
	 @param pGestureId The ID of a performed gesture.
	 */
	public void onGesture(View pView, int pGestureId)
	{
	}

	private int getGestureID()
	{
		int totalPointerCount = mPointers.size();

		if(totalPointerCount == 1)
		{
			Pointer pointer = mPointers.get(0);

			if(pointer.downInsideTimeLimit(TIME_LIMIT))
			{
				if(pointer.getTapped())
				{
					return ONE_FINGER_TAP;
				} else if(pointer.getSwipedUp())
				{
					return ONE_FINGER_SWIPE_UP;
				} else if(pointer.getSwipedDown())
				{
					return ONE_FINGER_SWIPE_DOWN;
				} else if(pointer.getSwipedLeft())
				{
					return ONE_FINGER_SWIPE_LEFT;
				} else if(pointer.getSwipedRight())
				{
					return ONE_FINGER_SWIPE_RIGHT;
				} else
				{
					return UNKNOWN_GESTURE;
				}
			} else
			{
				return UNKNOWN_GESTURE;
			}
		} else if(totalPointerCount == 2) {
			Pointer pointer1 = mPointers.get(0);
			Pointer pointer2 = mPointers.get(1);

			if(pointer1.downInsideTimeLimit(TIME_LIMIT) && pointer2.downInsideTimeLimit(TIME_LIMIT))
			{
				if (pointer1.getTapped() &&
						pointer2.getTapped()) {

					return TWO_FINGER_TAP;

				} else if (pointer1.getSwipedUp() &&
						pointer2.getSwipedUp()) {

					return TWO_FINGER_SWIPE_UP;

				} else if (pointer1.getSwipedDown() &&
						pointer2.getSwipedDown()) {

					return TWO_FINGER_SWIPE_DOWN;

				} else if (pointer1.getSwipedLeft() &&
						pointer2.getSwipedLeft()) {

					return TWO_FINGER_SWIPE_LEFT;

				} else if (pointer1.getSwipedRight() &&
						pointer2.getSwipedRight()) {

					return TWO_FINGER_SWIPE_RIGHT;
				} else if (pointer1.pinchedIn(pointer2, movementLimitPx)) {
					return TWO_FINGER_PINCH_IN;
				} else if (pointer1.pinchedOut(pointer2, movementLimitPx)) {
					return TWO_FINGER_PINCH_OUT;
				} else {
					return UNKNOWN_GESTURE;
				}
			} else {
				return UNKNOWN_GESTURE;
			}
		} else {
			return UNKNOWN_GESTURE;
		}
	}
}
