package com.example.trlab.selfgallery;

import java.util.ArrayList;

import com.example.trlab.R;

import android.app.Activity;
import android.os.Bundle;

public class TestActivity extends Activity{
	private ArrayList<String> imageList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_layout_test);
        PreGallery gallery = (PreGallery) findViewById(R.id.pregaller);
        
        imageList.add("http://aswegetmarried.in/images/illustrations/calendar.png");
		imageList.add("http://aswegetmarried.in/images/illustrations/time.png");
		imageList.add("http://aswegetmarried.in/images/illustrations/map.png");
		imageList.add("http://aswegetmarried.in/images/illustrations/gift.png");
		
		gallery.setImage(imageList);
    }
    
}
