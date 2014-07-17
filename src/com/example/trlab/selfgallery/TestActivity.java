package com.example.trlab.selfgallery;

import java.util.ArrayList;

import com.example.trlab.R;
import com.example.trlab.utils.LogUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

public class TestActivity extends Activity{
	private ArrayList<String> imageList = new ArrayList<String>();
	private PreviewGallery gallery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_layout_test);
        gallery = (PreviewGallery) findViewById(R.id.pregaller);
        
//        imageList.add("http://aswegetmarried.in/images/illustrations/calendar.png");
//		imageList.add("http://aswegetmarried.in/images/illustrations/time.png");
//		imageList.add("http://aswegetmarried.in/images/illustrations/map.png");
//		imageList.add("http://aswegetmarried.in/images/illustrations/gift.png");
//		
		imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/c8f0d103497d424899b74f934d9cedaa.png.short.h1440.webp");
		imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/9fafffbb1a194de7b568debdd7aa9708.png.short.h1440.webp");
		imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/854fe1d9332748859bddfb4784cb0921.png.short.h1440.webp");
		imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/f30c2a52878146d099085217b8f78e45.png.short.h1440.webp");
		imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/d0acc76b7a3d4c129738ebef9665571c.png.short.h1440.webp");
		imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/0282b80613d44dd4b0ddbd0645e4920e.png.short.h1440.webp");
		
		gallery.setImage(imageList);
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if(gallery != null){
            LogUtil.d("clear ");
            gallery.clearAllViews();
        }
    }
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        // TODO Auto-generated method stub
//        return false;
//    }
    
    
    
}
