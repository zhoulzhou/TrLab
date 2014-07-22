package com.example.trlab.os;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.Scroller;

/**
 * HorizontalScrollView反弹效果的实现
 * 
 */
public class HOverScrollView extends HorizontalScrollView {
    private static final float DAMP_COEFFICIENT = 2;
    private static final int ELASTIC_DELAY = 500;
    private float damk = DAMP_COEFFICIENT;
    private int delay = ELASTIC_DELAY;
    private View inner;

    private float x;

    private Rect normal = new Rect();
    private Scroller mScroller;

    public HOverScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            inner = getChildAt(0);
        }
        mScroller = new Scroller(getContext());
    }
    
    public float getDamk() {
        return damk;
    }

    public void setDamk(float damk) {
        this.damk = damk;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (inner == null) {
            return super.onTouchEvent(ev);
        } else {
            commOnTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
        }
    }
    
    public void commOnTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
        case MotionEvent.ACTION_DOWN:
            x = ev.getX();
            break;
        case MotionEvent.ACTION_UP:

            if (isNeedAnimation()) {
               animation();
            }
            break;
        case MotionEvent.ACTION_MOVE:
            final float preX = x;
            float nowX = ev.getX();
            int deltaX = (int) ((preX - nowX) / damk);
            // 滚动
            scrollBy(deltaX, 0);

            x = nowX;
            // 当滚动到最上或者最下时就不会再滚动，这时移动布局
            if (isNeedMove()) {
                if (normal.isEmpty()) {
                    // 保存正常的布局位置
                    normal.set(inner.getLeft(), inner.getTop(), inner.getRight(), inner.getBottom());
                }
                inner.layout(inner.getLeft() - deltaX, inner.getTop(), inner.getRight() - deltaX, inner.getBottom());
            }
            break;
        default:
            break;
        }
    }

    // 开启动画移动

    public void animation() {
        // 开启移动动画
        TranslateAnimation ta = new TranslateAnimation(0, 0, inner.getTop(), normal.top);
        ta.setDuration(200);
        inner.startAnimation(ta);
        // 设置回到正常的布局位置
        inner.layout(normal.left, normal.top, normal.right, normal.bottom);

        normal.setEmpty();

    }

    // 是否需要开启动画
    public boolean isNeedAnimation() {
        return !normal.isEmpty();
    }

    // 是否需要移动布局
    public boolean isNeedMove() {

        int offset = inner.getMeasuredWidth() - getWidth();
        int scrollX = getScrollX();
        if (scrollX == 0 || scrollX == offset) {
            return true;
        }
        return false;
    }

}
