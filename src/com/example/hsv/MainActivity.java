package com.example.hsv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;

import com.example.hsv.HSVAdapter;
import com.example.hsv.HSVLayout;
import com.example.trlab.R;

/**
 * 2013.10.12 类似图片浏览的例子
 * 
 * @author Administrator
 * 
 */
public class MainActivity extends Activity {
    private String UPDATE_IMAGE_ACTION = "update";
	private HSVLayout movieLayout = null;
	private HSVAdapter adapter = null;
	private IntentFilter intentFilter = null;
	private BroadcastReceiver receiver = null;
	private int nCount = 0;
	// pic in the drawable
	private Integer[] images = { R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher };
	
	private ArrayList<String> imageList = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hsv_layout);
		movieLayout = (HSVLayout) findViewById(R.id.movieLayout);
		adapter = new HSVAdapter(this);
		
		imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/c8f0d103497d424899b74f934d9cedaa.png.short.h1440.webp");
		imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/9fafffbb1a194de7b568debdd7aa9708.png.short.h1440.webp");
		imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/854fe1d9332748859bddfb4784cb0921.png.short.h1440.webp");
		imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/f30c2a52878146d099085217b8f78e45.png.short.h1440.webp");
		imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/d0acc76b7a3d4c129738ebef9665571c.png.short.h1440.webp");
		imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/0282b80613d44dd4b0ddbd0645e4920e.png.short.h1440.webp");
//		
		imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/c8f0d103497d424899b74f934d9cedaa.png.short.h1440.webp");
		imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/9fafffbb1a194de7b568debdd7aa9708.png.short.h1440.webp");
		imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/854fe1d9332748859bddfb4784cb0921.png.short.h1440.webp");
		imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/f30c2a52878146d099085217b8f78e45.png.short.h1440.webp");
		imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/d0acc76b7a3d4c129738ebef9665571c.png.short.h1440.webp");
		imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/0282b80613d44dd4b0ddbd0645e4920e.png.short.h1440.webp");
//		
		imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/c8f0d103497d424899b74f934d9cedaa.png.short.h1440.webp");
		imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/9fafffbb1a194de7b568debdd7aa9708.png.short.h1440.webp");
		imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/854fe1d9332748859bddfb4784cb0921.png.short.h1440.webp");
		imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/f30c2a52878146d099085217b8f78e45.png.short.h1440.webp");
		imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/d0acc76b7a3d4c129738ebef9665571c.png.short.h1440.webp");
		imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/0282b80613d44dd4b0ddbd0645e4920e.png.short.h1440.webp");
//		
		imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/c8f0d103497d424899b74f934d9cedaa.png.short.h1440.webp");
		imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/9fafffbb1a194de7b568debdd7aa9708.png.short.h1440.webp");
		imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/854fe1d9332748859bddfb4784cb0921.png.short.h1440.webp");
		imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/f30c2a52878146d099085217b8f78e45.png.short.h1440.webp");
		imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/d0acc76b7a3d4c129738ebef9665571c.png.short.h1440.webp");
		imageList.add("http://storefs1.wanyol.com:8090/uploadFiles/PImages/201407/11/0282b80613d44dd4b0ddbd0645e4920e.png.short.h1440.webp");
//		
		
		
//		for (int i = 0; i < images.length; i++) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("image", images[i]);
//			// map.put("image", getResources().getDrawable(images[i]));
//			map.put("index", (i+1));
//			adapter.addObject(map);
//		}
		adapter.addObject(imageList);
		movieLayout.setAdapter(adapter);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}


}