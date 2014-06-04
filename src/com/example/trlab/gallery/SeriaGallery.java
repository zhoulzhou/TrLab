package com.example.trlab.gallery;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

public class SeriaGallery extends Gallery{

    @SuppressWarnings("deprecation")
    public SeriaGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
        setSpacing(0);
        setHorizontalFadingEdgeEnabled(true);
        setUnselectedAlpha(1);
        setDrawingCacheEnabled(true);
        setChildrenDrawingCacheEnabled(true);
        setAnimationCacheEnabled(true);
        setAlwaysDrawnWithCacheEnabled(true);
        setAnimationDuration(300);
    }
    
    private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2){
        log("e2 x= " + e2.getX()  + " e1 x= " + e1.getX());
        return e2.getX() > e1.getX();
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        int kEvent;
        if(isScrollingLeft(e1, e2)){
            log("scroll left");
            kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
        }else{
            log("scroll right");
            kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
        }
        onKeyDown(kEvent, null);
        return true;
        
    }
    
    private void log(String s){
        Log.e("thor", s);
    }
    
}