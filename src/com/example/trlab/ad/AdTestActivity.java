package com.example.trlab.ad;

import com.example.trlab.R;

import android.app.Activity;
import android.os.Bundle;

public class AdTestActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ad_test_layout);
		
		AdView adView = (AdView) findViewById(R.id.ad_view);
		adView.startCarousel();
	}
	
}