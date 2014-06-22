package com.example.trlab.vp;

import com.example.trlab.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
//import android.view.WindowId;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SlideTitleView extends LinearLayout{
	private Context mContext;
	private ImageView mImage;
	
	private int mTabCount = 3;
	private int mScreenWidth;

	public SlideTitleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
		initParams();
	}
	
	public SlideTitleView(Context context) {
		super(context);
		initView(context);
		initParams();
	}
	
	private void initView(Context context){
		mContext = context;
		LayoutInflater inflater = LayoutInflater.from(mContext);
		mImage = (ImageView) inflater.inflate(R.layout.slide_title_view_layout, null);
	}
	
	private void initParams(){
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);
		mScreenWidth = dm.widthPixels;
	}
	
	public void setTotalCount(int count){
		mTabCount = count;
		int width = 0;
		width = mScreenWidth / mTabCount;
		LayoutParams params = new LayoutParams(width,LayoutParams.MATCH_PARENT);
		addView(mImage,params);
	}
	
	public void scroll(int position, float offsetPercent, int offsetPixels){
		int dest = (mScreenWidth - mImage.getWidth()) / (mTabCount - 1);
		scrollTo(-(position) * dest - (int) (offsetPercent * dest), 0);
	}
	
	public void setCurrentPosition(int position){
		scrollToPosition(position);
	}
	
	private void scrollToPosition(int position){
		int itemWidth = mScreenWidth / mTabCount;
		scrollTo(-(position * itemWidth), 0);
	}
	
}