package com.example.trlab.vp;

import com.example.trlab.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TabView extends LinearLayout{
	private Context mContext;
	private ViewPager mPager;
	private SlideTitleView mSlideImage;
	private LinearLayout mSlideText;
//	private PagerAdapter mAdapter;
	private LayoutInflater mInflater;
	
	private int mTabTopTextSizeResId;
	private int mTabTopTextColorResId;
	
	private int mCurIndex;
	
	private OnPagerStateChangedListener mPagerStateChangedListener;

	public TabView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews(context);
		initParams(attrs);
	}
	
	public TabView(Context context){
		super(context);
		initViews(context);
	}
	
	private void initViews(Context context){
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
		mInflater.inflate(R.layout.tab_view_layout, this);
		mSlideImage = (SlideTitleView) findViewById(R.id.slide_title);
		mPager = (ViewPager) findViewById(R.id.tab_pager);
		mSlideText = (LinearLayout) findViewById(R.id.tab_title);
		
	}
	
	private void initParams(AttributeSet attrs){
		TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.tab_view);
		int titleResId = typedArray.getResourceId(R.styleable.tab_view_tab_title_str_array, R.array.tab_title_array);
		String[] titleArray = mContext.getResources().getStringArray(titleResId);
		int tabTopBgResId = typedArray.getResourceId(R.styleable.tab_view_top_bg, R.drawable.actionbar_tab_bg);
		mTabTopTextColorResId = typedArray.getResourceId(R.styleable.tab_view_tab_item_text_color, R.color.tab_top_text_color);
		mTabTopTextSizeResId = typedArray.getResourceId(R.styleable.tab_view_tab_item_text_size, R.dimen.tab_top_text_size);
		mSlideImage.setTotalCount(titleArray.length);
		mSlideText.setBackgroundResource(tabTopBgResId);
		addTabTitle(titleArray);
		typedArray.recycle();
		typedArray = null;
	}
	
	private void addTabTitle(String[] titleArray){
		int len = titleArray.length;
		for(int i=0; i<len; i++){
			final int index = i;
			TextView view = (TextView) mInflater.inflate(R.layout.tab_title_item_layout, null);
			view.setTextSize(mContext.getResources().getDimension(mTabTopTextSizeResId));
			view.setTextColor(mContext.getResources().getColor(mTabTopTextColorResId));
			view.setSingleLine(true);
			view.setEllipsize(TruncateAt.END);
			view.setGravity(Gravity.CENTER);
			view.setText(titleArray[i]);
			if(i == 0){
				view.setTextColor(mContext.getResources().getColor(R.color.tab_top_text_color));
			}
			LayoutParams params = new LayoutParams(0,LayoutParams.MATCH_PARENT);
			params.weight = 1.0f;
			view.setPadding(0, 0, 0, dpTpPx(2));
			mSlideText.addView(view, params);
			view.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					setCurrenIndex(index,true);
				}
				
			});
		}
	}
	
    private void setCurrenIndex(int index, boolean needAnimation){
    	if(index <0 || index > mPager.getAdapter().getCount() || index == mCurIndex){
    		return ;
    	}
    	int oldIndex = mCurIndex;
    	if(!needAnimation){
    		mCurIndex = index;
    	}
    	((TextView) mSlideText.getChildAt(index)).setTextColor(mContext.getResources().getColor(R.color.tab_title_color));
        ((TextView) mSlideText.getChildAt(oldIndex)).setTextColor(mContext.getResources().getColor(R.color.tab_top_text_color));
        mPager.setCurrentItem(index);
        mSlideImage.setCurrentPosition(index);
    }
    
    public void setPagerAdapter(PagerAdapter adapter){
    	mPager.setAdapter(adapter);
    }
    
    public void setOnPagerChangedListener(OnPagerStateChangedListener listener){
    	mPagerStateChangedListener = listener;
    	mPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int curIndex) {
				int oldIndex = mCurIndex;
                mCurIndex = curIndex;
                ((TextView) mSlideText.getChildAt(curIndex)).setTextColor(mContext.getResources().getColor(R.color.tab_title_color));
                ((TextView) mSlideText.getChildAt(oldIndex)).setTextColor(mContext.getResources().getColor(R.color.tab_top_text_color));
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				mSlideImage.scroll(position, positionOffset, positionOffsetPixels);
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				if(mPagerStateChangedListener != null){
					mPagerStateChangedListener.onPagerStateChanged(mCurIndex, state);
				}
			}
		});
    }
    
    public interface OnPagerStateChangedListener {
        public void onPagerStateChanged(int curIndex, int state);
    }

    private  int dpTpPx(int dpValue) {
    	 DisplayMetrics outMetrics = new DisplayMetrics();
         WindowManager mWindowManage = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
         mWindowManage.getDefaultDisplay().getMetrics(outMetrics);
        return (int) (dpValue * outMetrics.density);
    }
}