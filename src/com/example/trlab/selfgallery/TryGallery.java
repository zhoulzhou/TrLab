package com.example.trlab.selfgallery;

import java.util.ArrayList;

import com.example.trlab.R;
import com.example.trlab.utils.DisplayUtil;
import com.example.trlab.utils.LogUtil;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
public class TryGallery extends LinearLayout {

    private Context mContext;

    private static final int SWIPE_PAGE_ON_FACTOR = 60;
    private static final int SCROLL_NEXT = 0;

    private int mActiveItem = -1;
//    private int mUrlPosition = -1;
//    private int mUrlSize;

    private float mPrevScrollX;

    private boolean mStart;

    private int mItemCount ;
    
    private int mItemWidth = DisplayUtil.dpToPx(223);
    private int mItemHeight = DisplayUtil.dpToPx(392);
    
    private VelocityTracker mVelocityTracker;
    private int mTouchSlop;
    private int mMinimumVelocity;
    private int mMaxmumVelocity;
    private final int SNAP_VELOCITY = 10;
    
    View mItem;
    
    private LayoutInflater mInflater;
    private LinearLayout mContainer;
    private int mCurrentDisplayPosition ;
    private ArrayList<String> mImageList = new ArrayList<String>();
    
    private RecycleBin mRecycle = new RecycleBin();
    
    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            if(SCROLL_NEXT == what){
                if(mActiveItem >= mCurrentDisplayPosition && mActiveItem < mItemCount){
                    ImageView image = (ImageView) mRecycle.get(mActiveItem);
                    if(image != null){
                      UrlImageViewHelper.setUrlDrawable(image,mImageList.get(mActiveItem), R.drawable.ic_launcher);
                    }
                }
            }
        }
        
    };
    
    public TryGallery(Context context){
        super(context);

        mContext=context;
        mItemWidth = 900; // or whatever your item width is.
        mInflater = LayoutInflater.from(mContext);
//        setOnTouchListener(this);
        mScroller = new Scroller(context); 
        initViews();
    }

    public TryGallery(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext=context;
        mItemWidth = 900; // or whatever your item width is.
        mInflater = LayoutInflater.from(mContext);
//        setOnTouchListener(this);
        mScroller = new Scroller(context); 
        initViews();
    }
    
    private Scroller mScroller; 
  //调用此方法滚动到目标位置  
    public void smoothScrollTo(int fx, int fy) {  
        int dx = fx - mScroller.getFinalX();  
        int dy = fy - mScroller.getFinalY();  
        smoothScrollBy(dx, dy);  
    }  
  
    //调用此方法设置滚动的相对偏移  
    public void smoothScrollBy(int dx, int dy) {  
  
        //设置mScroller的滚动偏移量  
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);  
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果  
    }  
      
    @Override  
    public void computeScroll() {  
      
        //先判断mScroller滚动是否完成  
        if (mScroller.computeScrollOffset()) {  
          
            //这里调用View的scrollTo()完成实际的滚动  
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());  
              
            //必须调用该方法，否则不一定能看到滚动效果  
            postInvalidate();  
        }  
        super.computeScroll();  
    }  

    
    private void initViews(){
        mContainer =   (LinearLayout) mInflater.inflate(R.layout.pre_gallery_layout, null);
        addView (mContainer);
        mCurrentDisplayPosition = getScreenWidth(mContext)/mItemWidth +1;
        LogUtil.d("mActiveItem= " + mActiveItem + " mCurrentDisplayPosition= " + mCurrentDisplayPosition + " screenWidth= " + getScreenWidth(mContext));
        final ViewConfiguration viewConfiguration = ViewConfiguration.get(mContext);
        mTouchSlop = viewConfiguration.getScaledTouchSlop();
        mMinimumVelocity = viewConfiguration.getMinimumFlingVelocity();
        mMaxmumVelocity = viewConfiguration.getMaximumFlingVelocity();
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

                    if (i < Math.max(mCurrentDisplayPosition,3)) {
                       
                        UrlImageViewHelper.setUrlDrawable(image,mImageList.get(i), R.drawable.ic_launcher);
                    }
                    mRecycle.put(i, image);
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
    
    

//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
////        super.onLayout(changed, l, t, r, b);
//        int childLeft = -1;
//        final int count = mItemCount;
//        //水平从左到右放置
//        for (int i = 0; i < count; i++) {
//            final View child = mRecycle.get(i);
//            if (child.getVisibility() != View.GONE) {
//                final int childWidth = child.getMeasuredWidth();
//                if(childLeft==-1)
//                {
//                    childLeft=-childWidth;
//                }
//                child.layout(childLeft, 0, childLeft + childWidth, child.getMeasuredHeight());
//                childLeft += childWidth;
//            }
//        }
//    }

    private int getMaxItemCount() {
//        if(mImageList != null){
//            mUrlSize = mImageList.size();
//        }
        return ((LinearLayout) getChildAt(0)).getChildCount();
    }

    private LinearLayout getLinearLayout() {
        return (LinearLayout) getChildAt(0);
    }

    /**
     * Centers the current view the best it can.
     */
//    public void centerCurrentItem() {
//        if (getMaxItemCount() == 0) {
//            return;
//        }
//
//        int currentX = getScrollX();
//        View targetChild;
//        int currentChild = -1;
//
//        do {
//            currentChild++;
//            targetChild = getLinearLayout().getChildAt(currentChild);
//        } while (currentChild < getMaxItemCount() && targetChild.getLeft() < currentX);
//
//        if (mActiveItem != currentChild) {
//            mActiveItem = currentChild;
//            scrollToActiveItem();
//        }
//    }

    /**
     * Scrolls the list view to the currently active child.
     */
    private void scrollToActiveItem() {
        mItemCount = getMaxItemCount();
        if (mItemCount == 0) {
            return;
        }
        
        int targetItem = Math.min(mItemCount - 1, mActiveItem);
        targetItem = Math.max(0, targetItem);
        
        mActiveItem = targetItem;
        mHandler.sendEmptyMessage(SCROLL_NEXT);
        // Scroll so that the target child is centered
        View targetView = getLinearLayout().getChildAt(targetItem);

        int targetLeft = targetView.getLeft();
        int childWidth = targetView.getRight() - targetLeft;

        int width = getWidth() - getPaddingLeft() - getPaddingRight();
        int targetScroll = targetLeft - ((width - childWidth) / 2);

        smoothScrollTo(targetScroll, 0);
    }

    /**
     * Sets the current item and centers it.
     * @param currentItem The new current item.
     */
//    public void setCurrentItemAndCenter(int currentItem) {
//        mActiveItem = currentItem;
//        scrollToActiveItem();
//    }

//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        int x = (int) event.getX();
//
//        boolean handled = false;
//        obtainVelocityTracker(event);
//        switch (event.getAction()) {
//        case MotionEvent.ACTION_DOWN:
//            mPrevScrollX = event.getX();
//            break;
//        case MotionEvent.ACTION_MOVE:
////            int deltX =  (int) (mPrevScrollX - x);
////            LogUtil.d("deltX= " + deltX + " x= " + x + " mPrevScrollX= " +mPrevScrollX );
////            mPrevScrollX = x;
////            int minFactor = mItemWidth / SWIPE_PAGE_ON_FACTOR;
////            
////            if (deltX > minFactor) {
////                if (mActiveItem < getMaxItemCount() - 1) {
////                    mActiveItem = mActiveItem + 1;
////                }
////            }
////            else if (deltX > minFactor) {
////                if (mActiveItem > 0) {
////                    mActiveItem = mActiveItem - 1;
////                }
////            }
////            
////            scrollToActiveItem();
//
//            break;
//        case MotionEvent.ACTION_UP:
//            mStart = true;
//            mVelocityTracker.computeCurrentVelocity(1000, mMaxmumVelocity);
//            int initXVelocity = (int) mVelocityTracker.getXVelocity();
//            
//            if(initXVelocity > SNAP_VELOCITY){//足够的才能向左 
//                if (mActiveItem > 0) {
//                    mActiveItem = mActiveItem - 1;
//                }
//            }else if(initXVelocity < -SNAP_VELOCITY){//足够的才能向右 
//                if (mActiveItem < getMaxItemCount() - 1) {
//                    mActiveItem = mActiveItem + 1;
//                }
//            }
//            scrollToActiveItem();
//            releaseVelocityTracker();
//
//            handled = true;
//            break;
//        }
//
//        return handled;
//    }
    
    
    private void obtainVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    private void releaseVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }
    
    private int getScreenWidth(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager mWindowManage = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mWindowManage.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }
    
    public void clearAllViews(){
        mHandler.removeCallbacksAndMessages(null);
        UrlImageViewHelper.cleanup(mContext);
        if(mRecycle != null){
            mRecycle.clear();
        }
        
        this.removeAllViews();
    }
    
    class RecycleBin {
        private final SparseArray<View> mScrapHeap = new SparseArray<View>();

        public void put(final int position, final View v) {
            mScrapHeap.put(position, v);
        }

        View get(final int position) {
            final View result = mScrapHeap.get(position);
            if (result != null) {
                // System.out.println(" HIT");
                mScrapHeap.delete(position);
            } else {
                // System.out.println(" MISS");
            }
            return result;
        }

        View peek(final int position) {
            return mScrapHeap.get(position);
        }

        void clear() {
            final SparseArray<View> scrapHeap = mScrapHeap;
            final int count = scrapHeap.size();
            for (int i = 0; i < count; i++) {
                final ImageView view = (ImageView) scrapHeap.valueAt(i);
                if (view != null) {
                    removeDetachedView(view, false);
                }
            }
            scrapHeap.clear();
            mScrapHeap.clear();
        }
    }
    
    
    private View inner;  
    private float x;  
    private Rect normal = new Rect();  
      
    @Override  
    protected void onFinishInflate() {  
        if (getChildCount() > 0) {  
            inner = getChildAt(0);  
        }  
        System.out.println("getChildCount():" + getChildCount());  
    }  
      
//    @Override  
//    public boolean onTouchEvent(MotionEvent ev) {  
////        if (inner == null) {  
////            return super.onTouchEvent(ev);  
////        } else {  
//            commOnTouchEvent(ev);  
////        }  
//  
//        return super.onTouchEvent(ev);  
//    }  
  
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
            int deltaX = (int) (preX - nowX);  
            // 滚动   
            smoothScrollBy(deltaX, 0);  
  
            x = nowX;  
            // 当滚动到最左或者最右时就不会再滚动，这时移动布局   
//            if (isNeedMove()) {  
//                if (normal.isEmpty()) {  
//                    // 保存正常的布局位置   
//                    normal.set(inner.getLeft(), inner.getTop(), inner.getRight(), inner.getBottom());  
//                }  
//                // 移动布局   
//                inner.layout(inner.getLeft() - deltaX/2, inner.getTop() , inner.getRight()- deltaX/2, inner.getBottom() );  
//            }  
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