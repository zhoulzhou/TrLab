package com.example.trlab.selfgallery;

import com.example.trlab.utils.DisplayUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public class OppoSlideContainer extends ViewGroup {
			
	private final int SNAP_VELOCITY = 600;
	private final int TOUCH_STATE_REST = 0;
	private final int TOUCH_STATE_SCROLLING = 1;
	private final int ITEM_SPACE = DisplayUtil.dpToPx(20);  
	
	private int mCurScreen;
	private final int mDefaultScreen = 0;
	private float mLastMotionX;
	private Scroller mScroller;
	private VelocityTracker mVelocityTracker;
	private int mTouchState = TOUCH_STATE_REST;
	private int mTouchSlop;
	
	private boolean snapStatus = true;
	private boolean touchStatus = false;
	private boolean mEndSnap = false;
	
	public OppoSlideContainer(Context context) {
		super(context);
		mScroller = new Scroller(context);
		mCurScreen = mDefaultScreen;
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}

	public OppoSlideContainer(Context paramContext, AttributeSet paramAttributeSet) {
		this(paramContext, paramAttributeSet, 0);
	}

	public OppoSlideContainer(Context context, AttributeSet attributeSet,
			int paramInt) {
		super(context, attributeSet, paramInt);
		mScroller = new Scroller(context);
		mCurScreen = mDefaultScreen;
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}

	
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int childLeft = 0;
		final int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			final View childView = getChildAt(i);
			if (childView.getVisibility() != View.GONE) {
				final int childWidth = childView.getMeasuredWidth(); 
				childView.layout(childLeft, 0, childLeft + childWidth,
						childView.getMeasuredHeight());
				childLeft += childWidth + ITEM_SPACE;
			}
		}
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}
		scrollTo(mCurScreen * getScreenWidth(), 0);
	}


	public void computeScroll() {
		if (!mScroller.computeScrollOffset()){
			return;
		}
		int i = mScroller.getCurrX();
		int j = mScroller.getCurrY();
		scrollTo(i, j);
		postInvalidate();
	}

	public boolean onInterceptTouchEvent(MotionEvent ev) {
		final int action = ev.getAction();
		if ((action == MotionEvent.ACTION_MOVE)
				&& (mTouchState != TOUCH_STATE_REST)) {
			return true;
		}

		final float x = ev.getX();

		switch (action) {
    		case MotionEvent.ACTION_MOVE:
    			final int xDiff = (int) Math.abs(mLastMotionX - x);
    			if (xDiff > mTouchSlop) {
    				mTouchState = TOUCH_STATE_SCROLLING;
    			}
    			break;
    
    		case MotionEvent.ACTION_DOWN:
    			mLastMotionX = x;
    			mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST
    					: TOUCH_STATE_SCROLLING;
    			break;
    
    		case MotionEvent.ACTION_CANCEL:
    		case MotionEvent.ACTION_UP:
    			mTouchState = TOUCH_STATE_REST;
    			break;
		}
		return mTouchState != TOUCH_STATE_REST;
	}
	
	public boolean onTouchEvent(MotionEvent event){
		super.onTouchEvent(event);
		int action = event.getAction();		
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);
		touchStatus = true;
		final float x = event.getX();
		
		switch (action & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				rawX = event.getX();
				downPos = rawX;	
				if (!mScroller.isFinished()) {
					mScroller.abortAnimation();
				}
				mLastMotionX = x;
				break;
			case MotionEvent.ACTION_MOVE:	
				int deltaX = (int) (mLastMotionX - x);
				mLastMotionX = x;
				scrollBy(deltaX, 0);	
				break;
			case MotionEvent.ACTION_UP:
				mVelocityTracker.computeCurrentVelocity(1000);
				int velocityX = (int) mVelocityTracker.getXVelocity();
				if (velocityX > SNAP_VELOCITY) {
					snapToScreen(mCurScreen - 1);
				} 
				else if (velocityX < -SNAP_VELOCITY) {
					snapToScreen(mCurScreen + 1);
				} 
				else {
					snapToDestination();
				}
				if (mVelocityTracker != null) {
					mVelocityTracker.recycle();
					mVelocityTracker = null;
				}
				mTouchState = TOUCH_STATE_REST;
				break; 
			case MotionEvent.ACTION_CANCEL:
				mTouchState = TOUCH_STATE_REST;
				break;
		}
		return true;  
	}

	float rawX = 0;
    float downPos = -1.0f;
    
    public int getScreenWidth(){
//    	return Displaymanager.screenWidth + ITEM_SPACE;
        return 0;
    }

	public void snapToDestination() {
		int destScreen = getCurScreen();
		int scrollX = getScrollX();
		View view = getChildAt(destScreen);
		if(null != view){
			int left = view.getLeft();
			int dis = left - scrollX;
			if(40 <= Math.abs(dis)){  
				if(dis < 0){  
					destScreen = getCurScreen() + 1;
				}
				else{
					destScreen = getCurScreen() - 1;
				}
			}
			snapToScreen(destScreen);
		}
	}

	public void snapToScreen(int whichScreen) {
	    int scrollState = 0;
		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
		if (!snapStatus){
			whichScreen = mCurScreen;
		}
		int destX = getScreenWidth() * whichScreen;
		scrollState = whichScreen - mCurScreen;
		int scrollX = getScrollX();
		int disX = destX - scrollX;
		int duration = (int) (Math.abs(disX) * 0.6);
		if(1500 < duration){
			duration = 1500;
		}
		mScroller.startScroll(scrollX, 0, disX, 0, duration);
		mCurScreen = whichScreen;
		invalidate();
		if(scrollStateListener != null){   
            if(scrollState > 0){
                scrollStateListener.scrollLeft();
            }
            else if(0 > scrollState){ 
                scrollStateListener.scrollRight();
            }
        }
	}

	public void setTouchStatus(boolean state){
		this.touchStatus = state;
	}
	
	public void setSnapStatus(boolean state){
		snapStatus = state;
	}

	public int getCurScreen() {
		return mCurScreen;
	}
	
	public void setCurScreen(int index) {
		mCurScreen = index;
	}

	public boolean getEndSnapStatus() {
		return mEndSnap;
	}

	public boolean getTouchStatus() {
		return touchStatus;
	}

	private ScrollStateListener scrollStateListener;
    
    public void setScrollStateListener(ScrollStateListener listener){
        this.scrollStateListener = listener;
    }
    
    public interface ScrollStateListener{
        public void scrollLeft();
        public void scrollRight();
    }	
}
