package com.example.trlab.selfgallery;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

public class XiaHorizontalScrollView1 extends HorizontalScrollView {

    private static final String TAG = XiaHorizontalScrollView1.class.getName();

    Context mContext;

    public XiaHorizontalScrollView1(Context context) {
        super(context);
    }

    public XiaHorizontalScrollView1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XiaHorizontalScrollView1(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isRunnableScrolling) {
            return true;
        }
        return super.onTouchEvent(ev);
    }

    /*
     * the second animation
     */

    boolean isFling = false;
    private int mVelocityX = 0;
    private boolean isRunnableScrolling = false;
    private scrollRunnable runnable = null;

    @Override
    public void fling(int velocityX) {
        super.fling(velocityX);
        isFling = true;
        mVelocityX = velocityX;
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (clampedX && isFling) {
            isFling = false;

            if (runnable == null) {
                runnable = new scrollRunnable();
            }
            runnable.startScroll(mVelocityX);
        } else {
            if (mVelocityX > 0) {
                mVelocityX -= 3;
            } else {
                mVelocityX += 3;
            }
        }
    };

    private class scrollRunnable implements Runnable {
        View child;
        int scrollLength;
        int MaxVelocity = 8000;
        int MaxLength = 100;
        int duration = 300;
        int time = 0;

        public scrollRunnable() {
            if (child == null) {
                child = getChildAt(0);
            }
        }

        private void startScroll(int velocity) {

            android.util.Log.i("infor", "velocity: " + velocity + "");

            if (velocity > MaxVelocity) {
                velocity = MaxVelocity;
            }
            if (velocity < -MaxVelocity) {
                velocity = -MaxVelocity;
            }
            mVelocityX = velocity;
            scrollLength = mVelocityX * MaxLength / MaxVelocity;
            isRunnableScrolling = true;
            time = 0;
            post(this);
        }

        @Override
        public void run() {

            if (time < duration) {
                time += 10;
                int temp = (int) (scrollLength * Math.sin(time * Math.PI / duration));
                child.scrollTo(temp, 0);
                postDelayed(this, 10);
            } else {
                isRunnableScrolling = false;
                child.scrollTo(0, 0);
            }
        }

    }

}