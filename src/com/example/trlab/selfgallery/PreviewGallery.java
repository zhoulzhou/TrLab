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
import android.util.Log;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewDebug;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
public class PreviewGallery extends HorizontalScrollView {

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
    
    private Scroller mScroller;
    
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

    public PreviewGallery(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext=context;
        mItemWidth = 900; // or whatever your item width is.
        mInflater = LayoutInflater.from(mContext);
//        setOnTouchListener(this);
        mScroller = new Scroller(context);
        initViews();
    }
    
    public void smoothScrollT(int fx, int fy) {  
        int dx = fx - mScroller.getFinalX();  
        int dy = fy - mScroller.getFinalY();  
        smoothScrollB(dx, dy);  
    }  
  
    //调用此方法设置滚动的相对偏移  
    public void smoothScrollB(int dx, int dy) {  
  
        //设置mScroller的滚动偏移量  
//        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);  
        mScroller.startScroll(getScrollX(), 0, dx, 0,Math.abs(dx) * 2);
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
    
    private int getCenterItem(){
        int targetItem = Math.min(mItemCount - 1, mActiveItem);
        return targetItem = Math.max(0, targetItem);
    }

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

//        smoothScrollT(targetScroll, 0);
       mScroller.startScroll(getScrollX(), 0, targetScroll, 0,Math.abs(targetScroll) * 2);
        
        //此时需要手动刷新View 否则没效果
        invalidate();
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
    
    @Override
    public void fling(int velocityX) {
        super.fling(velocityX * 3);
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
      
    @Override  
    public boolean onTouchEvent(MotionEvent ev) {  
        if (inner == null) {  
            return super.onTouchEvent(ev);  
        } else {  
            commOnTouchEvent(ev);  
        }  
  
        return super.onTouchEvent(ev);  
    }  
  
    public void commOnTouchEvent(MotionEvent ev) {
        obtainVelocityTracker(ev);
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN :
                x = ev.getX();
                break;
            case MotionEvent.ACTION_UP :

//                mVelocityTracker.computeCurrentVelocity(1000, mMaxmumVelocity);
//                int initXVelocity = (int) mVelocityTracker.getXVelocity();
//                if (initXVelocity > SNAP_VELOCITY) {
//                    if (mActiveItem > 0) {
//                        mActiveItem = mActiveItem - 1;
//                    }
//                } else if (initXVelocity < -SNAP_VELOCITY) {
//                    if (mActiveItem < getMaxItemCount() - 1) {
//                        mActiveItem = mActiveItem + 1;
//                    }
//                }
//                scrollToActiveItem();

                if (isNeedAnimation()) {
                    animation();
                }

                break;
            case MotionEvent.ACTION_MOVE :
                final float preX = x;
                float nowX = ev.getX();
//                int deltaX = (int) (preX - nowX);
                int deltaX = (int) (nowX - preX);
                // 滚动   
                smoothScrollBy(deltaX, 0);

                x = nowX;
//                 当滚动到最左或者最右时就不会再滚动，这时移动布局   
                if (isNeedMove()) {
                    if (normal.isEmpty()) {
                        // 保存正常的布局位置   
                        normal.set(inner.getLeft(), inner.getTop(), inner.getRight(), inner.getBottom());
                    }
                    // 移动布局   
                    inner.layout(
                        inner.getLeft() - deltaX / 2, inner.getTop(), inner.getRight() - deltaX / 2, inner.getBottom());
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
    
//    int MAX_SCROLL_HEIGHT = DisplayUtil.WIDTH;
//    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
//        int scrollY, int scrollRangeX, int scrollRangeY,
//        int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
//
//       int newScrollX = scrollX + deltaX;
//
//       int newScrollY = scrollY + deltaY;
//
//       // Clamp values if at the limits and record
//       final int left = -maxOverScrollX - MAX_SCROLL_HEIGHT;
//       final int right = maxOverScrollX + scrollRangeX + MAX_SCROLL_HEIGHT;
//       final int top = -maxOverScrollY;
//       final int bottom = maxOverScrollY + scrollRangeY;
//
//       boolean clampedX = false;
//       if (newScrollX > right) {
//        newScrollX = right;
//        clampedX = true;
//       } else if (newScrollX < left) {
//        newScrollX = left;
//        clampedX = true;
//       }
//
//       boolean clampedY = false;
//       if (newScrollY > bottom) {
//        newScrollY = bottom;
//        clampedY = true;
//       } else if (newScrollY < top) {
//        newScrollY = top;
//        clampedY = true;
//       }
//
//       onOverScrolled(newScrollX, newScrollY, clampedX, clampedY);
//
//       return clampedX || clampedY;
//      }
    
//    private float mLastionMotionX = 0 ;
//public boolean onTouchEvent(MotionEvent event){
//        
//        if (mVelocityTracker == null) {
//            
//            
//            mVelocityTracker = VelocityTracker.obtain();
//        }
//        
//        mVelocityTracker.addMovement(event);
//        
//        super.onTouchEvent(event);
//        
//        //手指位置地点
//        float x = event.getX();
//        float y = event.getY();
//        
//        
//        switch(event.getAction()){
//        case MotionEvent.ACTION_DOWN:
//            //如果屏幕的动画还没结束，你就按下了，我们就结束该动画
//            if(mScroller != null){
//                if(!mScroller.isFinished()){
//                    mScroller.abortAnimation();
//                }
//            }
//            
//            mLastionMotionX = x ;
//            break ;
//        case MotionEvent.ACTION_MOVE:
//            int detaX = (int)(mLastionMotionX - x );
//            scrollBy(detaX, 0);
////            
////            Log.e(TAG, "--- MotionEvent.ACTION_MOVE--> detaX is " + detaX );
//            mLastionMotionX = x ;
//            
//            break ;
//        case MotionEvent.ACTION_UP:
//            
//            final VelocityTracker velocityTracker = mVelocityTracker  ;
//            velocityTracker.computeCurrentVelocity(1000);
//            
//            int velocityX = (int) velocityTracker.getXVelocity() ;
//            
////            Log.e(TAG , "---velocityX---" + velocityX);
//            
//            //滑动速率达到了一个标准(快速向右滑屏，返回上一个屏幕) 马上进行切屏处理
//            if (velocityX > SNAP_VELOCITY && mActiveItem > 0) {
//                // Fling enough to move left
////                Log.e(TAG, "snap left");
//                if (mActiveItem > 0) {
//                    mActiveItem = mActiveItem - 1;
//                }
//                
//            }
//            //快速向左滑屏，返回下一个屏幕)
//            else if(velocityX < -SNAP_VELOCITY ){
////                Log.e(TAG, "snap right");
//                
//                if (mActiveItem < getMaxItemCount() - 1) {
//                    mActiveItem = mActiveItem + 1;
//                }
//            }
//            //以上为快速移动的 ，强制切换屏幕
//            else{
//                //我们是缓慢移动的，因此先判断是保留在本屏幕还是到下一屏幕
////                snapToDestination();
//            }
//            scrollToActiveItem();
//            if (mVelocityTracker != null) {
//                mVelocityTracker.recycle();
//                mVelocityTracker = null;
//            }
//            
////            mTouchState = TOUCH_STATE_REST ;
//            
//            break;
//        case MotionEvent.ACTION_CANCEL:
////            mTouchState = TOUCH_STATE_REST ;
//            break;
//        }
//        
//        return true ;
//    }

}