package com.example.trlab.selfview;

import com.example.trlab.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TipView extends LinearLayout{
	private Context mContext;
	private TextView mTipText;

	public TipView(Context context) {
		super(context);
		mContext = context;
		initView();
	}
	
	private void initView(){
		LayoutInflater inflater = LayoutInflater.from(mContext);
		inflater.inflate(R.layout.tip_view, this);
		mTipText = (TextView) findViewById(R.id.tip_text);
	}
	
	public void setTipText(String tip){
		if(mTipText != null){
			mTipText.setText(tip);
		}
	}
	
}