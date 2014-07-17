package com.example.trlab.selfgallery;
 
import java.util.ArrayList;

import com.example.trlab.R;
 
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;
 
public class VPTestActivity extends Activity implements OnPageChangeListener {
 
    //图像资源
    private int[] Resources=new int[]{R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher};
    //适配器
    private ViewPagerAdapter mAdapter;
    //View数组
    private ArrayList<View> Views;
    //ViewPager
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.vpgallery_test_layout);
        //初始化Views
        Views = new ArrayList<View>(Resources.length);
        for(int i=0;i<Resources.length;i++) {
            ImageView  image= new  ImageView(this);
            image.setImageResource(Resources[i]);
//            image.setscaletype(ImageView.scaletype.fit_xy);
            image.setAdjustViewBounds(true);
            
            Views.add(image);
        }
            ViewPager mviewpager= (ViewPager)findViewById(R.id.ViewPager);
            mviewpager.setOffscreenPageLimit(3); 
            mviewpager.setPageMargin(20);
            mAdapter = new ViewPagerAdapter(Views);
            mviewpager.setAdapter(mAdapter);
            mviewpager.setOnPageChangeListener(this);
    }
    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void onPageSelected(int arg0) {
        // TODO Auto-generated method stub
        
    }
    
//    viewPagerContainer.setOnTouchListener(new OnTouchListener() {          
//        
//        @Override
//        public boolean onTouch(View v, MotionEvent event) {          
//            // dispatch the events to the ViewPager, to solve the problem that we can swipe only the middle view.          
//            return mViewPager.dispatchTouchEvent(event);          
//        }          
//    });
    
    public class MyOnPageChangeListener implements OnPageChangeListener {         
        
        @Override
        public void onPageSelected(int position) {         
//            indexText.setText(new StringBuilder().append(position + 1).append("/")         
//                                                 .append(3));         
        }         
                   
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {         
            // to refresh frameLayout         
//            if (viewPagerContainer != null) {         
//                viewPagerContainer.invalidate();         
//            }         
        }         
                   
        @Override
        public void onPageScrollStateChanged(int arg0) {         
        }         
    }
}
                          