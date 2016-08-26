package com.jewel.model;

/**
 Created by PC on 2016/4/28. A Pointer represents a point of contact between a user and a touch
 screen.
 */
public class Pointer
{

	private int mID;

	private long downTime;
	private float downX;
	private float downY;

	private long upTime;
	private float upX;
	private float upY;

	private float upXUpperLimit;
	private float upYUpperLimit;
	private float upXLowerLimit;
	private float upYLowerLimit;

	private boolean tapped;
	private boolean swipedUp;
	private boolean swipedDown;
	private boolean swipedLeft;
	private boolean swipedRight;

	/**
	 Creates a new Pointer.

	 @param pID              The Pointer's ID.
	 @param pDownTime        The time (in milliseconds) when the Pointer went down.
	 @param pDownX           The X coordinate (in pixels) where the Pointer went down.
	 @param pDownY           The Y coordinate (in pixels) where the Pointer went down.
	 @param pMovementLimitPx The distance (in pixels) the Pointer has to move to trigger a gesture.
	 */
	public Pointer(int pID, long pDownTime, float pDownX, float pDownY, float pMovementLimitPx)
	{
		this.mID = pID;
		this.downTime = pDownTime;
		this.downX = pDownX;
		this.downY = pDownY;

		upXUpperLimit = downX + pMovementLimitPx;
		upYUpperLimit = downY + pMovementLimitPx;
		upXLowerLimit = downX - pMovementLimitPx;
		upYLowerLimit = downY - pMovementLimitPx;
	}

	/**
	 Sets the time (in milliseconds) and coordinates (in pixels) when and where the Pointer went up.

	 @param pUpTime The time (in milliseconds) when the Pointer went up.
	 @param pUpX    The X coordinate (in pixels) where the Pointer went up.
	 @param pUpY    The Y coordinate (in pixels) where the Pointer went up.
	 */
	public void setUpInfo(long pUpTime, float pUpX, float pUpY)
	{
		this.upTime = pUpTime;
		this.upX = pUpX;
		this.upY = pUpY;

		this.tapped = upX < upXUpperLimit && upY < upYUpperLimit && upX > upXUpperLimit && upY > upYLowerLimit;

		this.swipedUp = upX < upXUpperLimit && upX > upXLowerLimit && upY <= upYLowerLimit;

		this.swipedDown = upX < upXUpperLimit && upX > upXLowerLimit && upY >= upYUpperLimit;

		this.swipedLeft = upY < upYUpperLimit && upY > upYLowerLimit && upX <= upXLowerLimit;

		this.swipedRight = upY < upYUpperLimit && upY > upYLowerLimit && upX >= upXUpperLimit;
	}

	/**
	 Returns the Pointer's ID.

	 @return The Pointer's ID.
	 */
	public int getId()
	{
		return mID;
	}

	/**
	 Returns the X coordinate (in pixels) where the Pointer went down.

	 @return The X coordinate (in pixels) where the Pointer went down.
	 */
	public float getDownX()
	{
		return downX;
	}

	/**
	 Returns the Y coordinate (in pixels) where the Pointer went down.

	 @return The Y coordinate (in pixels) where the Pointer went down.
	 */
	public float getDownY()
	{
		return downY;
	}

	/**
	 Returns the X coordinate (in pixels) where the Pointer went up.

	 @return The X coordinate (in pixels) where the Pointer went up.
	 */
	public float getUpX()
	{
		return upX;
	}

	/**
	 Returns the Y coordinate (in pixels) where the Pointer went up.

	 @return The Y coordinate (in pixels) where the Pointer went up.
	 */
	public float getUpY()
	{
		return upY;
	}

	/**
	 Returns whether the Pointer was down inside a given time limit.

	 @param pTimeLimit The time (in milliseconds) the Pointer has to perform a gesture.

	 @return Whether the Pointer was down inside a given time limit.
	 */
	public boolean downInsideTimeLimit(int pTimeLimit)
	{
		return (upTime - downTime) <= pTimeLimit;
	}

	/**
	 Returns whether the Pointer tapped.

	 @return Whether the Pointer tapped.
	 */
	public boolean getTapped()
	{
		return tapped;
	}

	/**
	 Returns whether the Pointer swiped up.

	 @return Whether the Pointer swiped up.
	 */
	public boolean getSwipedUp()
	{
		return swipedUp;
	}

	/**
	 Returns whether the Pointer swiped down.

	 @return Whether the Pointer swiped down.
	 */
	public boolean getSwipedDown()
	{
		return swipedDown;
	}

	/**
	 Returns whether the Pointer swiped left.

	 @return Whether the Pointer swiped left.
	 */
	public boolean getSwipedLeft()
	{
		return swipedLeft;
	}

	/**
	 Returns whether the Pointer swiped right.

	 @return Whether the Pointer swiped right.
	 */
	public boolean getSwipedRight()
	{
		return swipedRight;
	}

	private double distanceFormula(float pX1, float pY1, float pX2, float pY2)
	{
		return Math.sqrt(Math.pow(pX1 - pX2, 2) + Math.pow(pY1 - pY2, 2));
	}

	/**
	 * Returns whether the Pointer pinched in with another Pointer.
	 *
	 * @param pPointer Another Pointer.
	 * @param pMovementLimitPx The distance (in pixels) both Pointers have to move to
	 *                         trigger a gesture.
	 * @return Whether the Pointer pinched in with another Pointer.
	 */
	public boolean pinchedIn(Pointer pPointer, float pMovementLimitPx) {
		return (distanceFormula(downX, downY, pPointer.getDownX(), pPointer.getDownY()) +
				pMovementLimitPx) <=
				distanceFormula(upX, upY, pPointer.getUpX(), pPointer.getUpY());
	}

	/**
	 * Returns whether the Pointer pinched out with another Pointer.
	 *
	 * @param pPointer Another Pointer.
	 * @param pMovementLimitPx The distance (in pixels) both Pointers have to move to
	 *                         trigger a gesture.
	 * @return Whether the Pointer pinched out with another Pointer.
	 */
	public boolean pinchedOut(Pointer pPointer, float pMovementLimitPx) {
		return (distanceFormula(downX, downY, pPointer.getDownX(), pPointer.getDownY()) -
				pMovementLimitPx) >=
				distanceFormula(upX, upY, pPointer.getUpX(), pPointer.getUpY());
	}
}
