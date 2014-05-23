package com.example.trlab.vp;

import java.util.ArrayList;
import java.util.List;

import com.example.trlab.R;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class VPActivity extends Activity{
	private ViewPager mPager;
	private TextView mTabOne, mTabTwo, mTabThree;
	private ImageView mImage;
	
	private List<View> mViewLists;
	private int mOffSet;
	private int mCurTab;
	private int mbmpW;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vp_layout);
		
		mTabOne = (TextView) findViewById(R.id.tab_one);
		mTabTwo = (TextView) findViewById(R.id.tab_two);
		mTabThree = (TextView) findViewById(R.id.tab_three);
		
		mTabOne.setOnClickListener(new TabOnClickListener(0));
		mTabTwo.setOnClickListener(new TabOnClickListener(1));
		mTabThree.setOnClickListener(new TabOnClickListener(2));
		
		initImageView();
		initVP();
	}
	
	private void initVP(){
		mPager = (ViewPager) findViewById(R.id.pager);
		mViewLists = new ArrayList<View>();
		LayoutInflater inflater = LayoutInflater.from(this);
		mViewLists.add(inflater.inflate(R.layout.vp_item, null));
		mViewLists.add(inflater.inflate(R.layout.vp_item1, null));
		mViewLists.add(inflater.inflate(R.layout.vp_item2, null));
		mPager.setAdapter(new MyPagerAdapter(mViewLists));
		mPager.setCurrentItem(1);
		mPager.setOnPageChangeListener(new MyPagerChangeListener());
	}
	
	private void initImageView(){
		mImage =(ImageView) findViewById(R.id.image_indicator);
		mbmpW = BitmapFactory.decodeResource(getResources(), R.drawable.search_btn_normal).getWidth();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		mOffSet = (screenW /3 - mbmpW)/2;
		Matrix matrix = new Matrix();
		matrix.postTranslate(mOffSet, 0);
		mImage.setImageMatrix(matrix);
	}
	
	
	
	public class MyPagerChangeListener implements OnPageChangeListener{
		int one = mOffSet * 2 + mbmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量

		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}

		@Override
		public void onPageSelected(int arg0) {
			Animation animation = new TranslateAnimation(0, 0, 0, 0);
			  switch (arg0) {
			  case 0:
				if (mCurTab == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
				} else if (mCurTab == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
				}
			  break;
			  
			  case 1:
				if (mCurTab == 0) {
					animation = new TranslateAnimation(mOffSet, one, 0, 0);
				} else if (mCurTab == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
				}
			  break;
			  
			  case 2:
				if (mCurTab == 0) {
					animation = new TranslateAnimation(mOffSet, two, 0, 0);
				} else if (mCurTab == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
				}
			  break;
			  }
			  
			  mCurTab = arg0;
			  animation.setFillAfter(true);// True:图片停在动画结束位置
			  animation.setDuration(300);
			  mImage.startAnimation(animation);
		}
		
	}
	
	public class MyPagerAdapter extends PagerAdapter{
		private List<View> mViews;
		
		public MyPagerAdapter(List<View> views){
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
	
	public class TabOnClickListener implements View.OnClickListener{
		private int mIndex;
		
		public TabOnClickListener(int index){
			mIndex = index;
		}

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(mIndex);
		}
		
	}
	
}