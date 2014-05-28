package com.example.trlab.an;

import com.example.trlab.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class AnTestActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.an_test_layout);
		
		ImageView im = (ImageView) findViewById(R.id.an_image);
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.myanim);
		im.startAnimation(anim);
//		anim.setRepeatMode(Animation.RESTART);
//		anim.setRepeatCount(Animation.INFINITE);
//		anim.start();
	}
	
}