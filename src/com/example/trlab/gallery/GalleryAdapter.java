package com.example.trlab.gallery;

import com.example.trlab.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;

public class GalleryAdapter extends BaseAdapter {
	private Context mContext;
	 int mGalleryItemBackground;  
	 
	int[] mImageIds = new int[] { R.drawable.a2, R.drawable.a3,
			R.drawable.a4, R.drawable.a5, R.drawable.a6 };

	public GalleryAdapter(Context c) {
		// 声明 ImageAdapter
		mContext = c;
		//TypedArray实例是个属性的容器，context.obtainStyledAttributes()方法返回得到  
        TypedArray a = mContext.obtainStyledAttributes(R.styleable.HelloGallery);  
        mGalleryItemBackground = a.getResourceId  
        (R.styleable.HelloGallery_android_galleryItemBackground, 0);  
        a.recycle(); 
	}

	public int getCount() { // 获取图片的个数
		return mImageIds.length;
	}

	public Object getItem(int position) {
		// 获取图片在库中的位置
		return position;
	}

	public long getItemId(int position) {
		// 获取图片在库中的位置
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView i = new ImageView(mContext);
		i.setImageResource(mImageIds[position]);
		 //给生成的ImageView设置Id，不设置的话Id都是-1   
        i.setId(mImageIds[position]);  
		// 给ImageView设置资源
		i.setLayoutParams(new Gallery.LayoutParams(  
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)); 
//		i.setLayoutParams(new Gallery.LayoutParams(600, 800));
		// 设置布局 图片200×200显示
		i.setScaleType(ImageView.ScaleType.FIT_XY);
		// 设置比例类型
		
//		 // 设置居中对齐  
//        i.setScaleType(ImageView.ScaleType.CENTER);
		
		//感觉像是加了相框似的
		 i.setBackgroundResource(mGalleryItemBackground);  
		return i;
	}
}