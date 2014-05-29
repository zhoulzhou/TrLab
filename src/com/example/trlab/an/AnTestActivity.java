package com.example.trlab.an;

import com.example.trlab.R;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class AnTestActivity extends Activity{
	ImageView im;
	ImageView dot;
	Handler handler;
	AnimationDrawable ad;
	final AnimatorSet set = new AnimatorSet() ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.an_test_layout);
		
		 
		
	   im = (ImageView) findViewById(R.id.an_image);
	   dot = (ImageView) findViewById(R.id.dot);
//	   dot.setVisibility(View.GONE);   
		
//		Animation anim = AnimationUtils.loadAnimation(this, R.anim.myanim);
//		im.startAnimation(anim);
//		anim.setRepeatMode(Animation.RESTART);
//		anim.setRepeatCount(Animation.INFINITE);
//		anim.start();
	   
	    
       ObjectAnimator anim = ObjectAnimator.ofFloat(dot, "alpha", 0f, 1f);  
       anim.setDuration(2000);  
       ObjectAnimator anim2 = ObjectAnimator.ofFloat(dot, "alpha", 1f, 0f);  
       anim2.setDuration(2000);  
       set.play(anim).before(anim2) ;  
       set.addListener(new AnimatorListener() {
		
		@Override
		public void onAnimationStart(Animator animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animator animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationEnd(Animator animation) {
			// TODO Auto-generated method stub
			Log.d("zhou","dot end");
			startFrame();
		}
		
		@Override
		public void onAnimationCancel(Animator animation) {
			// TODO Auto-generated method stub
			
		}
	});
       
       startFrame();
		
	}
	
	private void startFrame(){
	   im.setBackgroundResource(R.drawable.wifianim);
	   ad = (AnimationDrawable) im.getBackground();
       ad.start();
		
		int duration = 0;
		for (int i=0; i<ad.getNumberOfFrames(); i++){
			duration += ad.getDuration(i);
			Log.d("zhou", "duration= " + duration);
		}
		
	   handler = new Handler();

		handler.postDelayed(new Runnable() {
             public void run() {
				// 此处调用第二个动画播放方法
				Log.d("zhou", "end");
				ad.stop();
				dot.setVisibility(View.VISIBLE);
				set.start(); 
             }
             }, duration); 
	}
	
}