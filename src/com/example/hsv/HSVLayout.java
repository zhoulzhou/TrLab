package com.example.hsv;

import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class HSVLayout extends LinearLayout {
	private String UPDATE_IMAGE_ACTION = "update";
	private HSVAdapter adapter;
	private Context context;

	public HSVLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public void setAdapter(HSVAdapter adapter) {
		this.adapter = adapter;
		for (int i = 0; i < adapter.getCount(); i++) {
			final int position = i;
			View view = adapter.getView(i, null, null);
			view.setPadding(10, 0, 10, 0);
			// 为视图设定点击监听器
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(context, "您选择了" + position,
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent.setAction(UPDATE_IMAGE_ACTION);
					intent.putExtra("index", position);
					context.sendBroadcast(intent);
					
				}
			});
			this.setOrientation(HORIZONTAL);
			this.addView(view, new LinearLayout.LayoutParams(
					/*LayoutParams.WRAP_CONTENT*/300, LayoutParams.WRAP_CONTENT));
		}
	}
}