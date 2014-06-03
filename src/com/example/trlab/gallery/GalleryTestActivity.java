package com.example.trlab.gallery;

import com.example.trlab.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Gallery;

public class GalleryTestActivity extends Activity{
	int[] resIds = new int[] { R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4,
			R.drawable.a5,R.drawable.a6
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery_test_layout);
		
		Gallery gallery = (Gallery) findViewById(R.id.gallery);
		GalleryAdapter adapter = new GalleryAdapter(this);
		gallery.setAdapter(adapter);
		
	}
	
}