package com.example.trlab.matrix;

import com.example.trlab.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class MatrixActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.matrix_layout);
		
		ImageView image = (ImageView) findViewById(R.id.mimage);
		
		Matrix matrix = new Matrix();
		Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.matrix)).getBitmap();
		
		matrix.setRotate(60);
		Bitmap bm = Bitmap.createBitmap(bitmap, 0,0,bitmap.getWidth(),bitmap.getHeight(), matrix, true);
		
		image.setImageBitmap(bm);
	}
	
}