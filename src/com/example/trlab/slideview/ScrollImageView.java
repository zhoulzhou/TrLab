package com.example.trlab.slideview;

import com.example.trlab.R;
import com.example.trlab.utils.DisplayUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

public class ScrollImageView extends HorizontalScrollView{
    private Context mContext;
    private ImageView mImage;
    private OnLoadFinishListener mOnLoadFinishListener;
    private int mImageWidth;
    private int mSpaceWidth;
    private float mPercent = 0;
    private boolean mIsMeasured;
    private OnImageFlingListener mOnImageFlingListener;
    private boolean mIsLoadFinish;
    
    public ScrollImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }
    
    public ScrollImageView(Context context) {
        super(context);
        initViews(context);
    }
    
    private void initViews(Context context){
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        inflater.inflate(R.layout.scroll_image_view_layout, this);
        
        mImage = (ImageView) findViewById(R.id.content);
       
        setHorizontalScrollBarEnabled(false);
        setOverScrollMode(HorizontalScrollView.OVER_SCROLL_NEVER);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(l > mSpaceWidth){
            mPercent = ((float) (l - mSpaceWidth)) / (float) mSpaceWidth;
        }else{
            mPercent = -((float) (mSpaceWidth - l)) / (float) mSpaceWidth;
        }
        if(mOnImageFlingListener != null && mIsMeasured){
           mOnImageFlingListener.onImageFling(mPercent);    
        }
        
        mIsMeasured = true;
    }
    
    public interface OnImageFlingListener{
        public void onImageFling(float percent);
    }
    
    public void setOnImageFlingListener(OnImageFlingListener listener){
        mOnImageFlingListener = listener;
    }
    
    public void setImage(int resId){
        mIsLoadFinish = false;
        mImage.setBackgroundResource(resId);
        mIsLoadFinish = true;
        
        if(mOnLoadFinishListener != null){
            mOnLoadFinishListener.onLoadFinish(DisplayUtil.WIDTH * 2);
        }
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        mImage.setOnClickListener(l);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mImageWidth = mImage.getWidth();
        mSpaceWidth = (mImageWidth - DisplayUtil.WIDTH) / 2;
        if (mIsLoadFinish) {
            scrollBy(mSpaceWidth, 0);
            mIsLoadFinish = false;
        }
    }
    
    public float getScrollPercent(){
        return mPercent;
    }
    
    public interface OnLoadFinishListener{
        public void onLoadFinish(int width);
    }
    
    public void setOnLoadFinishListener(OnLoadFinishListener listener){
        mOnLoadFinishListener = listener;
    }
    
}