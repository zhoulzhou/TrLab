package com.example.trlab.vp;

import java.util.ArrayList;
import java.util.List;

import com.example.trlab.R;
import com.example.trlab.vp.VPActivity.MyPagerAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class VPTestActivity extends Activity{
	private TabView mTabView;
	private List<View> mViewLists;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vp_test_layout);
		mTabView = (TabView) findViewById(R.id.tab_view);
		
		mViewLists = new ArrayList<View>();
		LayoutInflater inflater = LayoutInflater.from(this);
		mViewLists.add(inflater.inflate(R.layout.vp_item, null));
		mViewLists.add(inflater.inflate(R.layout.vp_item1, null));
		mViewLists.add(inflater.inflate(R.layout.vp_item2, null));
		mTabView.setPagerAdapter(new MyPagerAdapter(mViewLists));
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
	
}