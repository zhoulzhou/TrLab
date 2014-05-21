package com.example.trlab.spinner;

import java.util.List;

import com.example.trlab.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SpinnerAdapter extends BaseAdapter{
	private String[] mTexts;
//	private List<String> mTexts;
	private Context mContext;
	
	public SpinnerAdapter(Context context, String[] strings){
		mContext = context;
		mTexts = strings;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mTexts.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mTexts[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(mContext);
		arg1 = inflater.inflate(R.layout.spinner_item, null);
		if (arg1 != null) {
			TextView tv = (TextView) arg1.findViewById(R.id.item_text);
			tv.setText(mTexts[arg0]);
		}
		return arg1;
	}
	
}