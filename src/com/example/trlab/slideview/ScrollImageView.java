package com.example.trlab.slideview;

import com.example.trlab.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

public class ScrollImageView extends HorizontalScrollView{
    private Context mContext;
    private ImageView mImage;
    private OnLoadFinishListener mOnLoadFinishListener;
    
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
        
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }
    
    public interface OnLoadFinishListener{
        public void onLoadFinish(int width);
    }
    
    public void setOnLoadFinishListener(OnLoadFinishListener listener){
        mOnLoadFinishListener = listener;
    }
    
}