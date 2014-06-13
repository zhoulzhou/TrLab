package com.example.trlab.slideview;

import com.example.trlab.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class TestActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_test_layout);
        
        SlideView slideView = (SlideView) findViewById(R.id.slide_view);
        slideView.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View arg0) {
                Log.d("zhou","click");
            }
        });
        
        slideView.setImage(R.drawable.road);
    }
    
}