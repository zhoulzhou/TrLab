package com.example.trlab.selfgallery;

import java.util.ArrayList;

import com.example.trlab.R;
import com.example.trlab.utils.LogUtil;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import android.content.Context;
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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
public class PreviewGallery extends HorizontalScrollView implements View.OnTouchListener {

    private Context mContext;

    private static final int SWIPE_PAGE_ON_FACTOR = 60;
    private static final int SCROLL_NEXT = 0;

    private int mActiveItem = -1;
//    private int mUrlPosition = -1;
//    private int mUrlSize;

    private float mPrevScrollX;

    private boolean mStart;

    private int mItemWidth;
    private int mItemCount ;
    
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

    public PreviewGallery(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext=context;
        mItemWidth = 900; // or whatever your item width is.
        mInflater = LayoutInflater.from(mContext);
        setOnTouchListener(this);
        initViews();
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
           if(imageList != null && imageList.size() > 0){
				for (int i = 0; i < imageList.size(); i++) {
				    mItem = (View) mInflater.inflate(R.layout.pre_gallery_layout_item, null);
                    ImageView image = (ImageView) mItem.findViewById(R.id.pregallery_item_image);
                    mContainer.addView(mItem, i, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));

                    if (i < Math.max(mCurrentDisplayPosition,3)) {
                       
                        UrlImageViewHelper.setUrlDrawable(image,imageList.get(i), R.drawable.ic_launcher);
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

        super.smoothScrollTo(targetScroll, 0);
    }

    /**
     * Sets the current item and centers it.
     * @param currentItem The new current item.
     */
//    public void setCurrentItemAndCenter(int currentItem) {
//        mActiveItem = currentItem;
//        scrollToActiveItem();
//    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int) event.getX();

        boolean handled = false;
        obtainVelocityTracker(event);
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            mPrevScrollX = event.getX();
            break;
        case MotionEvent.ACTION_MOVE:
//            int deltX =  (int) (mPrevScrollX - x);
//            LogUtil.d("deltX= " + deltX + " x= " + x + " mPrevScrollX= " +mPrevScrollX );
//            mPrevScrollX = x;
//            int minFactor = mItemWidth / SWIPE_PAGE_ON_FACTOR;
//            
//            if (deltX > minFactor) {
//                if (mActiveItem < getMaxItemCount() - 1) {
//                    mActiveItem = mActiveItem + 1;
//                }
//            }
//            else if (deltX > minFactor) {
//                if (mActiveItem > 0) {
//                    mActiveItem = mActiveItem - 1;
//                }
//            }
//            
//            scrollToActiveItem();

            break;
        case MotionEvent.ACTION_UP:
            mStart = true;
            mVelocityTracker.computeCurrentVelocity(1000, mMaxmumVelocity);
            int initXVelocity = (int) mVelocityTracker.getXVelocity();
            
            if(initXVelocity > SNAP_VELOCITY){//足够的才能向左 
                if (mActiveItem > 0) {
                    mActiveItem = mActiveItem - 1;
                }
            }else if(initXVelocity < -SNAP_VELOCITY){//足够的才能向右 
                if (mActiveItem < getMaxItemCount() - 1) {
                    mActiveItem = mActiveItem + 1;
                }
            }
            scrollToActiveItem();
            releaseVelocityTracker();

            handled = true;
            break;
        }

        return handled;
    }
    
    
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

}