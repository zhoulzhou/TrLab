package com.example.trlab.gallery;

import com.example.trlab.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;

public class GalleryAdapter extends BaseAdapter {
	private Context mContext;
	int[] mImageIds = new int[] { R.drawable.a2, R.drawable.a3,
			R.drawable.a4, R.drawable.a5, R.drawable.a6 };

	public GalleryAdapter(Context c) {
		// 声明 ImageAdapter
		mContext = c;
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
		// 给ImageView设置资源
		i.setLayoutParams(new Gallery.LayoutParams(  
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)); 
//		i.setLayoutParams(new Gallery.LayoutParams(600, 800));
		// 设置布局 图片200×200显示
		i.setScaleType(ImageView.ScaleType.FIT_XY);
		// 设置比例类型
		return i;
	}
}