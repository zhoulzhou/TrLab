package com.example.trlab.ad;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.trlab.R;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class AdView extends RelativeLayout {
	private Context mContext;
	private AdViewPager mPager;
	private AdPointView mPointView;
	private AdPagerAdapter mAdapter;
	
	List<AdItem> mAdItems = new ArrayList<AdItem>();
	List<View> mViewLists = new ArrayList<View>();
	
	private int mCurrIndex;
	private Timer mTimer;
	private boolean mIsCarouselStarted = false;
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what == 0){
				mCurrIndex ++;
				mPager.setCurrentItem(mCurrIndex);
			}
		}
		
	};

	public AdView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initViews(context);
	}
	
	public AdView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews(context);
	}
	
	public AdView(Context context) {
		super(context);
		initViews(context);
	}
	
	private void initViews(Context context){
		mContext = context;
		LayoutInflater inflater = LayoutInflater.from(mContext);
		inflater.inflate(R.layout.ad_view_layout,this);
		
		mPager = (AdViewPager) findViewById(R.id.tab_pager);
		mPointView = (AdPointView) findViewById(R.id.page_point);
		mPager.setOffscreenPageLimit(5);
		mPointView.updateIndicator(0, 5);
		
		initPager();
	}
	
	public void startCarousel(){
		if(mIsCarouselStarted){
			return ;
		}
		mTimer = new Timer();
		mTimer.scheduleAtFixedRate(new TimerTask(){

			@Override
			public void run() {
				mHandler.sendEmptyMessage(0);
			}
			
		}, 6000, 6000);
		mIsCarouselStarted = true;
	}
	
	public void stopCarousel(){
		mHandler.removeMessages(0);
		if(mTimer != null && mIsCarouselStarted){
			mTimer.cancel();
		}
		
		mIsCarouselStarted = false;
	}
	
	public void clean() {
        if (mAdapter != null) {
//        	mAdapter.clean();
        }
    }
	
	private void initPager(){
		mViewLists = new ArrayList<View>();
		LayoutInflater inflater = LayoutInflater.from(mContext);
		mViewLists.add(inflater.inflate(R.layout.vp_item, null));
		mViewLists.add(inflater.inflate(R.layout.vp_item1, null));
		mViewLists.add(inflater.inflate(R.layout.vp_item2, null));
		mViewLists.add(inflater.inflate(R.layout.vp_item, null));
		mViewLists.add(inflater.inflate(R.layout.vp_item1, null));
		mPager.setAdapter(new TestPagerAdapter(mViewLists));
		mPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				mCurrIndex = arg0;
				mPointView.updateIndicator(mCurrIndex, 5);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}
	
	public class TestPagerAdapter extends PagerAdapter{
		private List<View> mViews;
		
		public TestPagerAdapter(List<View> views){
			mViews = views;
		}

		@Override
		public int getCount() {
			return mViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
//			super.destroyItem(container, position, object);
			container.removeView(mViews.get(position));
		}

		@Override
		public void finishUpdate(ViewGroup container) {
			super.finishUpdate(container);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
//			return super.instantiateItem(container, position);
			container.addView(mViews.get(position));
			return mViews.get(position);
		}
	}
}