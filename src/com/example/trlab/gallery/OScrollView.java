package com.example.trlab.gallery;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

public class OScrollView extends ScrollView implements OnGestureListener{
    private int mFingerScrollHeight = 0;
    private float mDistanceX;
    private float mVelocityX;
    private float mDistanceY;
    private float mVelocityY;
    
    private int mTabIndex = -1;
    private int mScrollToTopIndex = -1;
    
    private int mTouchSlop;
    private float mDownX;
    private float mDownY;
    
    private boolean mIsFirstMeasure = true;
    
    private List<View> mEffectViews = new ArrayList<View>();

    public OScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    public OScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }
    
    public OScrollView(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }
    
    public void addEffectView(View v){
        mEffectViews.add(v);
    }
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            mDownX = ev.getX();
            mDownY = ev.getY();
        }
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            float x = ev.getX();
            float y = ev.getY();
            final int xDiff = (int) Math.abs(mDownX - x);
            final int yDiff = (int) Math.abs(mDownY - y);
            if (yDiff > mTouchSlop && 4 * Math.abs(xDiff) < Math.abs(yDiff)) {
                return true;
            } else if (xDiff > mTouchSlop && Math.abs(xDiff) >= Math.abs(yDiff)) {
                return false;
            }

        }
        return super.onInterceptTouchEvent(ev);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            mDownX = ev.getX();
            mDownY = ev.getY();
        }
        return super.onTouchEvent(ev);

    }

    @Override
    public boolean onDown(MotionEvent arg0) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        mVelocityX = Math.abs(velocityX);
        mVelocityY = Math.abs(velocityY);
        return false;
    }

    @Override
    public void onLongPress(MotionEvent arg0) {
        
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        mDistanceX = Math.abs(distanceX);
        mDistanceY = Math.abs(distanceY);
        return false;
    }

    @Override
    public void onShowPress(MotionEvent arg0) {
        
    }

    @Override
    public boolean onSingleTapUp(MotionEvent arg0) {
        return false;
    }
    
    public float getmDistanceX() {
        return mDistanceX;
    }

    public float getmVelocityY() {
        return mVelocityY;
    }

    public float getmDistanceY() {
        return mDistanceY;
    }

    public float getmVelocityX() {
        return mVelocityX;
    }

    /**
     * 获取指头所占高度，默认100px
     * 
     * @return
     */
    public int getFingerScrollHeight() {
        return mFingerScrollHeight;
    }

    /**
     * 设置指头所占高度，扩大触发横向滚动的纵向范围，保证滚动效果良好
     * 
     * @param fingerScrollHeight
     */
    public void setFingerScrollHeight(int fingerScrollHeight) {
        if (fingerScrollHeight < 0) {
            return;
        }
        this.mFingerScrollHeight = fingerScrollHeight;
    }

    @Override
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mTabIndex == mScrollToTopIndex) {
            return;
        }
        scrollTo(0, 0);
    }

    /**
     * 设置切换tab时当前tab标记
     * @param tabIndex
     */
    public void setTabIndex(int tabIndex) {
        this.mTabIndex = tabIndex;
    }

    /**
     * 设置DetailRecommendView所在tab用于防止切换tab跳动
     * @param index
     */
    public void setDetailRecommendTabIndex(int index) {
        this.mScrollToTopIndex = index;
    }
    
}