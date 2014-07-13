package com.example.trlab.selfgallery;

import java.util.ArrayList;

import com.example.trlab.R;
import com.example.trlab.utils.LogUtil;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
public class PreGallery extends HorizontalScrollView implements View.OnTouchListener {

    private Context mContext;

    private static final int SWIPE_PAGE_ON_FACTOR = 10;

    private int mActiveItem;

    private float mPrevScrollX;

    private boolean mStart;

    private int mItemWidth;
    
    View mItem;
    
    private LayoutInflater mInflater;
    private LinearLayout mContainer;
    private int mDisplayPosition ;
    
    private RecycleBin mRecycle = new RecycleBin();
    
    private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			
		}
    	
    };

    public PreGallery(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext=context;
        mItemWidth = 100; // or whatever your item width is.
        mInflater = LayoutInflater.from(mContext);
        setOnTouchListener(this);
        initViews();
    }
    
    private void initViews(){
        mContainer =   (LinearLayout) mInflater.inflate(R.layout.pre_gallery_layout, null);
        addView (mContainer);
    }
    
    public void setImage(ArrayList<String> imageList){
        if(mContainer != null){
           if(imageList != null && imageList.size() > 0){
				for (int i = 0; i < imageList.size(); i++) {
					mItem = (View) mInflater.inflate(R.layout.pre_gallery_layout_item, null);
					ImageView image = (ImageView) mItem.findViewById(R.id.pregallery_item_image);
					if (i < 3) { // screenwidth/image.width + 1;
						UrlImageViewHelper.setUrlDrawable(image,imageList.get(i), R.drawable.ic_launcher);
					}
					mRecycle.put(i, image);
					mContainer.addView(mItem, i, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
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

    private int getMaxItemCount() {
        return ((LinearLayout) getChildAt(0)).getChildCount();
    }

    private LinearLayout getLinearLayout() {
        return (LinearLayout) getChildAt(0);
    }

    /**
     * Centers the current view the best it can.
     */
    public void centerCurrentItem() {
        if (getMaxItemCount() == 0) {
            return;
        }

        int currentX = getScrollX();
        View targetChild;
        int currentChild = -1;

        do {
            currentChild++;
            targetChild = getLinearLayout().getChildAt(currentChild);
        } while (currentChild < getMaxItemCount() && targetChild.getLeft() < currentX);

        if (mActiveItem != currentChild) {
            mActiveItem = currentChild;
            scrollToActiveItem();
        }
    }

    /**
     * Scrolls the list view to the currently active child.
     */
    private void scrollToActiveItem() {
        int maxItemCount = getMaxItemCount();
        if (maxItemCount == 0) {
            return;
        }

        int targetItem = Math.min(maxItemCount - 1, mActiveItem);
        targetItem = Math.max(0, targetItem);

        mActiveItem = targetItem;

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
    public void setCurrentItemAndCenter(int currentItem) {
        mActiveItem = currentItem;
        scrollToActiveItem();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int) event.getRawX();

        boolean handled = false;
        switch (event.getAction()) {
        case MotionEvent.ACTION_MOVE:
            if (mStart) {
                mPrevScrollX = x;
                mStart = false;
            }

            break;
        case MotionEvent.ACTION_UP:
            mStart = true;
            int minFactor = mItemWidth / SWIPE_PAGE_ON_FACTOR;

            if ((mPrevScrollX - (float) x) > minFactor) {
                if (mActiveItem < getMaxItemCount() - 1) {
                    mActiveItem = mActiveItem + 1;
                }
            }
            else if (((float) x - mPrevScrollX) > minFactor) {
                if (mActiveItem > 0) {
                    mActiveItem = mActiveItem - 1;
                }
            }

            scrollToActiveItem();

            handled = true;
            break;
        }

        return handled;
    }
    
    public void clearAllViews(){
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
                final View view = scrapHeap.valueAt(i);
                if (view != null) {
                    removeDetachedView(view, false);
                }
            }
            scrapHeap.clear();
        }
    }

}
