package com.example.trlab.gallery;

import com.example.trlab.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

public class GalleryLayout extends LinearLayout{
    private Context mContext;
    private SeriaGallery mGallery;
    private BaseAdapter mAdapter;

    public GalleryLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }
    
    private void initViews(Context context){
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        inflater.inflate(R.layout.gallery_layout, this);
        
        mGallery = (SeriaGallery) findViewById(R.id.sgallery);
        mGallery.setAlwaysDrawnWithCacheEnabled(true);
        mGallery.setAnimationCacheEnabled(true);
    }
    
    public void setAdapter(BaseAdapter adapter){
        mAdapter = adapter;
        mGallery.setAdapter(mAdapter);
        mGallery.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                
            }
        });
    }
    
    public void setCurIndex(int index){
        int count = mGallery.getCount();
        if(count == 0){
            return ;
        }
        if(index > count){
            index = count;
        }
        try{
            mGallery.setSelection(index, false);
        }catch(Exception e){
            
        }
    }
}