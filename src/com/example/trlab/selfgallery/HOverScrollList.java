package com.example.trlab.selfgallery;

import java.util.ArrayList;

import com.example.trlab.R;
import com.example.trlab.utils.DisplayUtil;
import com.example.trlab.utils.LogUtil;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * HorizontalScrollView反弹效果的实现
 * 
 */
public class HOverScrollList extends HorizontalScrollView {
    private static final float DAMP_COEFFICIENT = 2;
    private static final int ELASTIC_DELAY = 500;
    private float damk = DAMP_COEFFICIENT;
    private int delay = ELASTIC_DELAY;
    private View inner;

    private float x;

    private Rect normal = new Rect();
    private Scroller mScroller;
    private LayoutInflater mInflater;
    private Context mContext;
    private LinearLayout mContainer;
    
    private ArrayList<String> mImageList = new ArrayList<String>();
    View mItem;
    private int mItemWidth = DisplayUtil.dpToPx(223);
    private int mItemHeight = DisplayUtil.dpToPx(392);

    public HOverScrollList(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
//        initViews();
    }
    
    private void initViews(){
        mContainer =   (LinearLayout) mInflater.inflate(R.layout.pre_gallery_layout, null);
        addView (mContainer);
    }
    
    public void setView(View v){
        if(mContainer != null){
            mContainer.addView(v,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        }
    }
    
    public void setImage(ArrayList<String> imageList){
        if(mImageList != null){
            mImageList.clear();
        }
        mImageList.addAll(imageList);
        if(mContainer != null){
           if(mImageList != null && mImageList.size() > 0){
                for (int i = 0; i < mImageList.size(); i++) {
                    mItem = (View) mInflater.inflate(R.layout.pre_gallery_layout_item, null);
                    ImageView image = (ImageView) mItem.findViewById(R.id.pregallery_item_image);
                    mContainer.addView(mItem, i, new LinearLayout.LayoutParams(mItemWidth,mItemHeight));

//                    if (i < Math.max(mCurrentDisplayPosition,3)) {
                       
                        UrlImageViewHelper.setUrlDrawable(image,mImageList.get(i), R.drawable.ic_launcher);
//                    }
                    image.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            LogUtil.d("click image");
                        }
                    });
                  
                }
           }
        }
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
	protected int computeHorizontalScrollOffset() {
		// TODO Auto-generated method stub
		int position = getScrollX() / mItemWidth  + 1;
		LogUtil.d("position= " + position );
		return super.computeHorizontalScrollOffset();
	}

	// 没用？？ 应该是mScroller没有调用startscroll();
	@Override
    public void computeScroll() {
        
        if (mScroller.computeScrollOffset()) {
        	scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
        	int currX = mScroller.getCurrX();
        	int position = currX / mItemWidth + 1;
        	LogUtil.d("position= " + position);
        	postInvalidate();
        }
        
        super.computeScroll();
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
            int deltaX = (int) ((preX - nowX));
            // 滚动   本身有滑动处理 这个是没用的
//            scrollBy(deltaX, 0);

            x = nowX;
            // 当滚动到最上或者最下时就不会再滚动，这时移动布局
            LogUtil.d("isNeedMove " + isNeedMove());
            if (isNeedMove()) {
                if (normal.isEmpty()) {
                    // 保存正常的布局位置
                    normal.set(inner.getLeft(), inner.getTop(), inner.getRight(), inner.getBottom());
                }
                LogUtil.d("deltaX= " + deltaX);
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
        LogUtil.d("offset= " + offset + " scrollX= " + scrollX);
        if (scrollX == 0 || scrollX == offset) {
            return true;
        }
        return false;
    }

}
