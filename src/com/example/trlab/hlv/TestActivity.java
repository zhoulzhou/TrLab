package com.example.trlab.hlv;

import java.util.ArrayList;

import com.example.trlab.R;
import com.example.trlab.selfgallery.HOverScrollList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class TestActivity extends Activity{
    private HorizontalListView mHlvCustomListWithDividerAndFadingEdge;
    private ArrayList<String> imageList = new ArrayList<String>();
    HOverScrollList hoScroll;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.hlv_test_layout);
        
//        hoScroll = (HOverScrollList) findViewById(R.id.hoscroll);
        
        mHlvCustomListWithDividerAndFadingEdge = (HorizontalListView) findViewById(R.id.hoscroll);
        
        
        imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/c8f0d103497d424899b74f934d9cedaa.png.short.h1440.webp");
        imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/9fafffbb1a194de7b568debdd7aa9708.png.short.h1440.webp");
        imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/854fe1d9332748859bddfb4784cb0921.png.short.h1440.webp");
        imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/f30c2a52878146d099085217b8f78e45.png.short.h1440.webp");
        imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/d0acc76b7a3d4c129738ebef9665571c.png.short.h1440.webp");
        imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/0282b80613d44dd4b0ddbd0645e4920e.png.short.h1440.webp");
        
        CustomArrayAdapter adapter = new CustomArrayAdapter(this, imageList);
        
        ImageView image = new ImageView(this);
        image.setImageResource(R.drawable.ic_launcher);

        // Assign adapter to HorizontalListView
        mHlvCustomListWithDividerAndFadingEdge.setAdapter(adapter);
        
//        hoScroll.setView(mHlvCustomListWithDividerAndFadingEdge);
    }
    
}