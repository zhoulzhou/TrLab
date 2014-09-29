/********************************************************************************
 ** Copyright (C), 2008-2012, OPPO Mobile Comm Corp., Ltd
 ** All rights reserved.
 ** VENDOR_EDIT
 ** File: - OppoSwitch.java
 ** Description: 
 **     An OppoSwitch is a two-state toggle switch widget with scroll effect that 
 **     can select between two options of oppo style
 **     
 ** Version: 1.0
 ** Date: 2013/01/17
 ** Author: Jianhui.Yu@Plf.MidWare.SDK
 ** 
 ** ------------------------------- Revision History: ----------------------------
 ** <author>						<data>		<version>	<desc>
 ** ------------------------------------------------------------------------------
 ** Jianhui.Yu@Plf.MidWare.SDK	2013/01/17	1.0			build this moudle
 ********************************************************************************/

package com.example.trlab.onoff;

import com.example.trlab.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;
import android.widget.Scroller;

/**
 * An OppoSwitch is a two-state toggle switch widget that can select between two
 * options. The user may drag the "thumb" back and forth to choose the selected
 * option, or simply tap to toggle as if it were a checkbox.
 * 
 */
@SuppressLint("Recycle")
@SuppressWarnings("unused")
public class OppoSwitch extends CompoundButton {

	private static final String TAG = "OppoSwitch";
	private static final boolean DEBUG = false;

	private static final int TOUCH_MODE_IDLE = 0;
	private static final int TOUCH_MODE_DOWN = 1;
	private static final int TOUCH_MODE_DRAGGING = 2;

	// Enum for the "typeface" XML parameter.
	private static final int SANS = 1;
	private static final int SERIF = 2;
	private static final int MONOSPACE = 3;

	private static final int DURATION_SCROLL_START = 100;
	private static final int DURATION_SCROLL_END = 200;
	private static final int VELOCITY_FLING_FAST = 1800;

	private Drawable mThumbDrawable;
	private Drawable mTrackDrawable;
	private int mSwitchMinWidth;
	private int mSwitchPadding;
	private int mThumbOuter;
	private int mThumbInner;
	private int mThumbCross;
	private int mThumbPadding;

	private int mTouchMode;
	private int mTouchSlop;
	private float mTouchX;
	private float mTouchY;
	private float mFastFlingVelocity;
	private VelocityTracker mVelocityTracker = VelocityTracker.obtain();

	private float mThumbPosition;
	private int mSwitchWidth;
	private int mSwitchHeight;
	private int mThumbWidth; // Does not include padding

	private int mSwitchLeft;
	private int mSwitchTop;
	private int mSwitchRight;
	private int mSwitchBottom;

	private final Rect mTempRect = new Rect();

	private boolean mScrollStart = false;
	private boolean mScrollEnd = false;
	private boolean mScrolling = false;
    private boolean mNoSound = false;
	private Scroller mScroller = null;
	private GestureDetector mGestureDetector = null;

	private static final int[] CHECKED_STATE_SET = { android.R.attr.state_checked };

	/**
	 * Construct a new OppoSwitch with default styling.
	 * 
	 * @param context
	 *            The Context that will determine this widget's theming.
	 */
	public OppoSwitch(Context context) {
		this(context, null);
	}

	/**
	 * Construct a new OppoSwitch with default styling, overriding specific
	 * style attributes as requested.
	 * 
	 * @param context
	 *            The Context that will determine this widget's theming.
	 * @param attrs
	 *            Specification of attributes that should deviate from default
	 *            styling.
	 */
	public OppoSwitch(Context context, AttributeSet attrs) {
		this(context, attrs, R.attr.oppoSwitchStyle);
	}

	/**
	 * Construct a new OppoSwitch with a default style determined by the given
	 * theme attribute, overriding specific style attributes as requested.
	 * 
	 * @param context
	 *            The Context that will determine this widget's theming.
	 * @param attrs
	 *            Specification of attributes that should deviate from the
	 *            default styling.
	 * @param defStyle
	 *            An attribute ID within the active theme containing a reference
	 *            to the default style for this widget. e.g.
	 *            android.R.attr.switchStyle.
	 */
	public OppoSwitch(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.Switch, defStyle, 0);

		mThumbDrawable = a.getDrawable(R.styleable.Switch_thumb);
		mTrackDrawable = a.getDrawable(R.styleable.Switch_track);
		mSwitchMinWidth = a.getDimensionPixelSize(
				R.styleable.Switch_switchMinWidth, 0);
		mSwitchPadding = a.getDimensionPixelSize(
				R.styleable.Switch_switchPadding, 0);

		a.recycle();

		TypedArray b = context.obtainStyledAttributes(attrs,
				R.styleable.OppoSwitch, defStyle, 0);

		mThumbOuter = b.getDimensionPixelSize(
				R.styleable.OppoSwitch_thumbOuter, 0);
		mThumbInner = b.getDimensionPixelSize(
				R.styleable.OppoSwitch_thumbInner, 0);
		mThumbCross = b.getDimensionPixelSize(
				R.styleable.OppoSwitch_thumbCross, 0);
		mThumbPadding = b.getDimensionPixelSize(
				R.styleable.OppoSwitch_thumbPadding, 0);

		b.recycle();

		ViewConfiguration config = ViewConfiguration.get(context);
		mTouchSlop = config.getScaledTouchSlop();

		mFastFlingVelocity = context.getResources().getDisplayMetrics().density
				* VELOCITY_FLING_FAST;
		mGestureDetector = new GestureDetector(context,
				new SwitchOnGestureListener());
		mScroller = new Scroller(context);

		// Refresh display with current params
		refreshDrawableState();
		setChecked(isChecked());
	}

	/**
	 * Set the outer length of the thumb.
	 * 
	 * @param pixels
	 *            Length of outer in pixels
	 * 
	 *            attr ref oppo.R.styleable#OppoSwitch_thumbOuter
	 */
	public void setThumbOuter(int pixels) {
		mThumbOuter = pixels;
		requestLayout();
	}

	/**
	 * Get the outer length of the thumb.
	 * 
	 * @return Length of outer in pixels
	 * 
	 *         attr ref oppo.R.styleable#OppoSwitch_thumbOuter
	 */
	public int getThumbOuter() {
		return mThumbOuter;
	}

	/**
	 * Set the inner length of the thumb.
	 * 
	 * @param pixels
	 *            Length of inner in pixels
	 * 
	 *            attr ref oppo.R.styleable#OppoSwitch_thumbInner
	 */
	public void setThumbInner(int pixels) {
		mThumbInner = pixels;
		requestLayout();
	}

	/**
	 * Get the inner length of the thumb.
	 * 
	 * @return Length of inner in pixels
	 * 
	 *         attr ref oppo.R.styleable#OppoSwitch_thumbInner
	 */
	public int getThumbInner() {
		return mThumbInner;
	}

	/**
	 * Set the width of cross between the drawable track and thumb.
	 * 
	 * @param pixels
	 *            Width of cross in pixels
	 * 
	 *            attr ref oppo.R.styleable#OppoSwitch_thumbCross
	 */
	public void setThumbCross(int pixels) {
		mThumbCross = pixels;
		requestLayout();
	}

	/**
	 * Get the width of cross between the drawable track and thumb.
	 * 
	 * @return Width of cross in pixels
	 * 
	 *         attr ref oppo.R.styleable#OppoSwitch_thumbCross
	 */
	public int getThumbCross() {
		return mThumbCross;
	}

	/**
	 * Set the left padding of the thumb.
	 * 
	 * @param pixels
	 *            Left padding in pixels
	 * 
	 *            attr ref oppo.R.styleable#OppoSwitch_thumbPadding
	 */
	public void setThumbPadding(int pixels) {
		mThumbPadding = pixels;
		requestLayout();
	}

	/**
	 * Get the left padding of the thumb.
	 * 
	 * @return Left padding in pixels
	 * 
	 *         attr ref oppo.R.styleable#OppoSwitch_thumbPadding
	 */
	public int getThumbPadding() {
		return mThumbPadding;
	}

	/**
	 * Set the amount of horizontal padding between the switch and the
	 * associated text.
	 * 
	 * @param pixels
	 *            Amount of padding in pixels
	 * 
	 * @attr ref android.R.styleable#Switch_switchPadding
	 */
	public void setSwitchPadding(int pixels) {
		mSwitchPadding = pixels;
		requestLayout();
	}

	/**
	 * Get the amount of horizontal padding between the switch and the
	 * associated text.
	 * 
	 * @return Amount of padding in pixels
	 * 
	 * @attr ref android.R.styleable#Switch_switchPadding
	 */
	public int getSwitchPadding() {
		return mSwitchPadding;
	}

	/**
	 * Set the minimum width of the switch in pixels. The switch's width will be
	 * the maximum of this value and its measured width as determined by the
	 * switch drawables and text used.
	 * 
	 * @param pixels
	 *            Minimum width of the switch in pixels
	 * 
	 * @attr ref android.R.styleable#Switch_switchMinWidth
	 */
	public void setSwitchMinWidth(int pixels) {
		mSwitchMinWidth = pixels;
		requestLayout();
	}

	/**
	 * Get the minimum width of the switch in pixels. The switch's width will be
	 * the maximum of this value and its measured width as determined by the
	 * switch drawables and text used.
	 * 
	 * @return Minimum width of the switch in pixels
	 * 
	 * @attr ref android.R.styleable#Switch_switchMinWidth
	 */
	public int getSwitchMinWidth() {
		return mSwitchMinWidth;
	}

	/**
	 * Set the drawable used for the track that the switch slides within.
	 * 
	 * @param track
	 *            Track drawable
	 * 
	 * @attr ref android.R.styleable#Switch_track
	 */
	public void setTrackDrawable(Drawable track) {
		mTrackDrawable = track;
		requestLayout();
	}

	/**
	 * Set the drawable used for the track that the switch slides within.
	 * 
	 * @param resId
	 *            Resource ID of a track drawable
	 * 
	 * @attr ref android.R.styleable#Switch_track
	 */
	public void setTrackResource(int resId) {
		setTrackDrawable(getContext().getResources().getDrawable(resId));
	}

	/**
	 * Get the drawable used for the track that the switch slides within.
	 * 
	 * @return Track drawable
	 * 
	 * @attr ref android.R.styleable#Switch_track
	 */
	public Drawable getTrackDrawable() {
		return mTrackDrawable;
	}

	/**
	 * Set the drawable used for the switch "thumb" - the piece that the user
	 * can physically touch and drag along the track.
	 * 
	 * @param thumb
	 *            Thumb drawable
	 * 
	 * @attr ref android.R.styleable#Switch_thumb
	 */
	public void setThumbDrawable(Drawable thumb) {
		mThumbDrawable = thumb;
		requestLayout();
	}

	/**
	 * Set the drawable used for the switch "thumb" - the piece that the user
	 * can physically touch and drag along the track.
	 * 
	 * @param resId
	 *            Resource ID of a thumb drawable
	 * 
	 * @attr ref android.R.styleable#Switch_thumb
	 */
	public void setThumbResource(int resId) {
		setThumbDrawable(getContext().getResources().getDrawable(resId));
	}

	/**
	 * Get the drawable used for the switch "thumb" - the piece that the user
	 * can physically touch and drag along the track.
	 * 
	 * @return Thumb drawable
	 * 
	 * @attr ref android.R.styleable#Switch_thumb
	 */
	public Drawable getThumbDrawable() {
		return mThumbDrawable;
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		mTrackDrawable.getPadding(mTempRect);
		int trackWidth = mTrackDrawable.getIntrinsicWidth() + mTempRect.left
				+ mTempRect.right;
		int trackHeight = mTrackDrawable.getIntrinsicHeight() + mTempRect.top
				+ mTempRect.bottom;
		trackWidth = Math.max(mSwitchMinWidth, trackWidth);

		mThumbDrawable.getPadding(mTempRect);
		int thumbWidth = mThumbDrawable.getIntrinsicWidth() + mTempRect.left
				+ mTempRect.right;
		int thumbHeight = mThumbDrawable.getIntrinsicHeight() + mTempRect.top
				+ mTempRect.bottom;
		thumbWidth = Math.max(mSwitchMinWidth, thumbWidth);

		final int switchWidth = trackWidth + thumbWidth - mThumbInner;
		final int switchHeight = Math.max(trackHeight, thumbHeight);
		mThumbPadding = (trackHeight - thumbHeight) / 2;
		mSwitchWidth = switchWidth;
		mSwitchHeight = switchHeight;
		mThumbWidth = mThumbDrawable.getIntrinsicWidth();

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int measuredHeight = getMeasuredHeight();
		if (measuredHeight < switchHeight) {
			setMeasuredDimension(getMeasuredWidthAndState(), switchHeight);
		}
	}

	/**
	 * @return true if (x, y) is within the target area of the switch thumb
	 */
	private boolean hitThumb(float x, float y) {
		mThumbDrawable.getPadding(mTempRect);
		final int thumbTop = mSwitchTop - mTouchSlop;
		final int thumbLeft = mSwitchLeft + getThumbPos() - mTouchSlop
				+ mThumbInner;
		final int thumbRight = thumbLeft + mThumbWidth - mThumbInner
				+ mTempRect.left + mTempRect.right + mTouchSlop;
		final int thumbBottom = mSwitchBottom + mTouchSlop;
		return x > thumbLeft && x < thumbRight && y > thumbTop
				&& y < thumbBottom;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (mScrolling || !isEnabled()) {
			return true;
		}
		mVelocityTracker.addMovement(ev);
		if (mGestureDetector.onTouchEvent(ev)) {
			return true;
		}
		final int action = ev.getActionMasked();
		switch (action) {
		case MotionEvent.ACTION_DOWN: {
			final float x = ev.getX();
			final float y = ev.getY();
			if (hitThumb(x, y)) {
				mTouchMode = TOUCH_MODE_DOWN;
				mTouchX = x;
				mTouchY = y;
			}
			break;
		}

		case MotionEvent.ACTION_MOVE: {
			switch (mTouchMode) {
			case TOUCH_MODE_IDLE:
				// Didn't target the thumb, treat normally.
				break;

			case TOUCH_MODE_DOWN: {
				final float x = ev.getX();
				final float y = ev.getY();
				if (Math.abs(x - mTouchX) > mTouchSlop
						|| Math.abs(y - mTouchY) > mTouchSlop) {
					mTouchMode = TOUCH_MODE_DRAGGING;
					getParent().requestDisallowInterceptTouchEvent(true);
					mTouchX = x;
					mTouchY = y;
					return true;
				}
				break;
			}

			case TOUCH_MODE_DRAGGING: {
				final float x = ev.getX();
				final float dx = x - mTouchX;
				float newPos = Math.max(
						-mThumbOuter,
						Math.min(mThumbPosition + dx, getThumbScrollRange()
								+ mThumbOuter));
				if (newPos != mThumbPosition) {
					mThumbPosition = newPos;
					mTouchX = x;
					invalidate();
					if (isOverspeed(mFastFlingVelocity)) {
						mTouchMode = TOUCH_MODE_IDLE;
						animateThumbChangeCheckedState();
					}
				}
				return true;
			}
			}
			break;
		}

		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL: {
			if (mTouchMode == TOUCH_MODE_DRAGGING) {
				stopDrag(ev);
				return true;
			}
			mTouchMode = TOUCH_MODE_IDLE;
			mVelocityTracker.clear();
			break;
		}
		}

		return super.onTouchEvent(ev);
	}

	private void cancelSuperTouch(MotionEvent ev) {
		MotionEvent cancel = MotionEvent.obtain(ev);
		cancel.setAction(MotionEvent.ACTION_CANCEL);
		super.onTouchEvent(cancel);
		cancel.recycle();
	}

	/**
	 * Called from onTouchEvent to end a drag operation.
	 * 
	 * @param ev
	 *            Event that triggered the end of drag mode - ACTION_UP or
	 *            ACTION_CANCEL
	 */
	private void stopDrag(MotionEvent ev) {
		mTouchMode = TOUCH_MODE_IDLE;
		// Up and not canceled, also checks the switch has not been disabled
		// during the drag
		boolean commitChange = ev.getAction() == MotionEvent.ACTION_UP;

		cancelSuperTouch(ev);

		commitChange &= needChangeCheckedState();

		if (commitChange) {
			animateThumbChangeCheckedState();
		} else {
			animateThumbRestoreCheckedState();
		}
	}

	private void animateThumbChangeCheckedState() {
		mScrollStart = true;
		smoothScrollTo(getThumbPos(), -mThumbOuter, DURATION_SCROLL_START);
	}

	private void animateThumbRestoreCheckedState() {
		mScrollEnd = true;
		smoothScrollTo(getThumbPos(), getThumbScrollRange(),
				DURATION_SCROLL_END);
	}

	@Override
	public void setChecked(boolean checked) {
		if (!mNoSound && mScrolling && isChecked() != checked && isEnabled()) {
			playSoundEffect(SoundEffectConstants.CLICK);
		}
        mNoSound = false;
		super.setChecked(checked);
		invalidate();
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);

		mThumbPosition = getThumbScrollRange();

		int switchRight = getWidth() - getPaddingRight();
		int switchLeft = switchRight - mSwitchWidth - mThumbOuter;
		int switchTop = 0;
		int switchBottom = 0;
		switch (getGravity() & Gravity.VERTICAL_GRAVITY_MASK) {
		default:
		case Gravity.TOP:
			switchTop = getPaddingTop();
			switchBottom = switchTop + mSwitchHeight;
			break;

		case Gravity.CENTER_VERTICAL:
			switchTop = (getPaddingTop() + getHeight() - getPaddingBottom())
					/ 2 - mSwitchHeight / 2;
			switchBottom = switchTop + mSwitchHeight;
			break;

		case Gravity.BOTTOM:
			switchBottom = getHeight() - getPaddingBottom();
			switchTop = switchBottom - mSwitchHeight;
			break;
		}

		mSwitchLeft = switchLeft;
		mSwitchTop = switchTop;
		mSwitchBottom = switchBottom;
		mSwitchRight = switchRight;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// Draw the switch
		int switchLeft = mSwitchLeft;
		int switchTop = mSwitchTop;
		int switchRight = mSwitchRight;
		int switchBottom = mSwitchBottom;

		mTrackDrawable.getPadding(mTempRect);
		int switchInnerLeft = switchLeft + mTempRect.left + mThumbPadding;
		int switchInnerRight = switchRight - mTempRect.right;
		canvas.clipRect(switchInnerLeft, switchTop, switchInnerRight,
				switchBottom);

		mThumbDrawable.getPadding(mTempRect);
		int thumbPos = getThumbPos();
		int thumbLeft = switchInnerLeft + thumbPos - mThumbCross
				- mTempRect.left;
		int thumbRight = thumbLeft + mThumbWidth + mTempRect.left
				+ mTempRect.right;
		int thumbTop = switchTop + mThumbPadding;
		int thumbBottom = switchBottom - mThumbPadding;

		mThumbDrawable.setBounds(thumbLeft, thumbTop, thumbRight, thumbBottom);
		mThumbDrawable.draw(canvas);

		canvas.save();

		int trackLeft = switchLeft;
		int trackRight = switchLeft + getThumbScrollRange() + mThumbOuter;
		int trackTop = switchTop;
		int trackBottom = switchBottom;

		mTrackDrawable.setBounds(trackLeft, trackTop, trackRight, trackBottom);
		mTrackDrawable.draw(canvas);

		canvas.restore();
	}

	@Override
	public int getCompoundPaddingRight() {
		int padding = super.getCompoundPaddingRight() + mSwitchWidth
				+ mThumbOuter;
		if (!TextUtils.isEmpty(getText())) {
			padding += mSwitchPadding;
		}
		return padding;
	}

	private int getThumbScrollRange() {
		if (mTrackDrawable == null) {
			return 0;
		}
		mTrackDrawable.getPadding(mTempRect);
		return mSwitchWidth - mThumbWidth - mTempRect.left - mTempRect.right;
	}

	@Override
	protected int[] onCreateDrawableState(int extraSpace) {
		final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
		if (isChecked()) {
			mergeDrawableStates(drawableState, CHECKED_STATE_SET);
		}
		return drawableState;
	}

	@Override
	protected void drawableStateChanged() {
		super.drawableStateChanged();

		int[] myDrawableState = getDrawableState();

		// Set the state of the Drawable
		// Drawable may be null when checked state is set from XML, from super
		// constructor
		if (mThumbDrawable != null) {
			mThumbDrawable.setState(myDrawableState);
		}
		if (mTrackDrawable != null) {
			mTrackDrawable.setState(myDrawableState);
		}
		invalidate();
	}

	@Override
	protected boolean verifyDrawable(Drawable who) {
		return super.verifyDrawable(who) || who == mThumbDrawable
				|| who == mTrackDrawable;
	}

	@Override
	public void jumpDrawablesToCurrentState() {
		super.jumpDrawablesToCurrentState();
		mThumbDrawable.jumpToCurrentState();
		mTrackDrawable.jumpToCurrentState();
	}

	@Override
	public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
		super.onInitializeAccessibilityEvent(event);
		event.setClassName(OppoSwitch.class.getName());
	}

	@Override
	public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
		super.onInitializeAccessibilityNodeInfo(info);
		info.setClassName(OppoSwitch.class.getName());
	}

	@Override
	public void toggle() {
		// don't setChecked immediately when performClick
	}

	@Override
	public void computeScroll() {
		if (mScroller == null || !mScrolling) {
			return;
		}
		if (mScroller.computeScrollOffset()) {
			mThumbPosition = mScroller.getCurrX();
			invalidate();
		} else if (mScrollEnd) {
			mScrollEnd = false;
			mScrollStart = false;
			setScrolling(false);
		} else if (mScrollStart) {
			setChecked(!isChecked());
			animateThumbRestoreCheckedState();
		}
	}

    /**
     * Change checked state with scroll effect.
     */
    public void changeChecked() {
        if (!mScrolling) {
            mNoSound = true;
            animateThumbChangeCheckedState();
        }
    }

	private void setScrolling(boolean scrolling) {
		mScrolling = scrolling;
	}

	private void smoothScrollTo(int start, int end, int duration) {
		if (mScroller == null) {
			return;
		}
		if (!mScroller.isFinished()) {
			return;
		}
		mScroller.startScroll(start, 0, end - start, 0, duration);
		setScrolling(true);
		invalidate();
	}

	private int getThumbPos() {
		return (int) (mThumbPosition + 0.5f);
	}

	private boolean needChangeCheckedState() {
		return mThumbPosition < getThumbScrollRange()
				- (mThumbWidth - mThumbOuter) / 2
				|| mThumbPosition > getThumbScrollRange();
	}

	private boolean isOverspeed(float velocity) {
		mVelocityTracker.computeCurrentVelocity(1000);
		float xvel = mVelocityTracker.getXVelocity();
		return Math.abs(xvel) > velocity;
	}

	private class SwitchOnGestureListener extends SimpleOnGestureListener {
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			animateThumbChangeCheckedState();
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			if (Math.abs(velocityX) > mFastFlingVelocity) {
				animateThumbChangeCheckedState();
				return true;
			}
			return false;
		}
	}
}
