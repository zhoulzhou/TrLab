package com.example.trlab.ad;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;

public class AdPagerAdapter extends PagerAdapter{
	private Context mContext;
	private List<AdItem> mAdItems;
	
	public AdPagerAdapter(Context context, List<AdItem> items){
		mContext = context;
		mAdItems = items;
	}

	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return false;
	}
	
}