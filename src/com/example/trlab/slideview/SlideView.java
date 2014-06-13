package  com.example.trlab.slideview;

import com.example.trlab.R;
import com.example.trlab.slideview.ScrollImageView.OnImageFlingListener;
import com.example.trlab.slideview.ScrollImageView.OnLoadFinishListener;
import com.example.trlab.utils.DisplayUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

public class SlideView extends RelativeLayout{
    private Context mContext;
    private ScrollImageView mScrollImageView;
    private SlideProgressView mSlideProgressView;
    
    public SlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }
    
    public SlideView(Context context) {
        super(context);
    }
    
    public void setImage(int resId){
        mScrollImageView.setImage(resId);
    }
    
    private void initViews(Context context){
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        inflater.inflate(R.layout.slide_view_layout, this);
        
        mScrollImageView = (ScrollImageView) findViewById(R.id.wallpaper_image_view);
        mSlideProgressView = (SlideProgressView) findViewById(R.id.sliding_button);
        
        mScrollImageView.setOnImageFlingListener(new OnImageFlingListener() {
            
            @Override
            public void onImageFling(float percent) {
//                log("slideview " + " onimagefling percent= " + percent);
                mSlideProgressView.onScroll(percent);
            }
        });
        
        mScrollImageView.setOnLoadFinishListener(new OnLoadFinishListener() {
            
            @Override
            public void onLoadFinish(int width) {
                log("slideview " + " width= " + width + " WIDTH= " + DisplayUtil.WIDTH);
                if(width > (DisplayUtil.WIDTH + 30)){
                    mSlideProgressView.setVisibility(View.VISIBLE);
                }
            }
        });
        
    }
    
    private void log(String s){
        Log.d("zhou",s);
    }
    
}