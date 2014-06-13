package com.example.trlab.slideview;

import com.example.trlab.R;
import com.example.trlab.utils.DisplayUtil;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class SlideProgressView extends LinearLayout{
    private Context mContext;
    private TextView mTextView;
    
    private int mSpaceWidth;
    private int mTotalWidth;
    private int mBtnWidth;
    private int mMargin = (int) (2 * DisplayUtil.DENSITY);
    private boolean mIsInitScroll;

    public SlideProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }
    
    public SlideProgressView(Context context) {
        super(context);
    }
    
    private void initViews(Context context){
        mContext = context;
        setOrientation(LinearLayout.HORIZONTAL);
        setBackgroundResource(R.drawable.pageindicator_bg);
        LayoutParams params = new LayoutParams((int) (77 * DisplayUtil.DENSITY), (int) (6.67 * DisplayUtil.DENSITY));
        setLayoutParams(params);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        inflater.inflate(R.layout.slide_progress_view_layout, this);
        
        mTextView = (TextView) findViewById(R.id.sliding_btn);
        
    }
    
    public void onScroll(float percent){
        int scrollX = -(mSpaceWidth + (int) (percent * mSpaceWidth));
        if(Math.abs(scrollX + mBtnWidth ) > mTotalWidth){
            return ;
        }
        
        if(Math.abs(scrollX) < mMargin){
            scrollX = scrollX > 0 ? mMargin : -mMargin;
        }
        scrollTo(scrollX, 0);
        mIsInitScroll = true;
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) (77 * DisplayUtil.DENSITY), (int) (6.67 * DisplayUtil.DENSITY));
        mTotalWidth = getWidth() - 8;
        mBtnWidth = mTextView.getWidth();
        mSpaceWidth = (mTotalWidth - mBtnWidth) / 2;
        if(!mIsInitScroll){
            scrollTo(-mSpaceWidth, 0);
        }
    }
    
}