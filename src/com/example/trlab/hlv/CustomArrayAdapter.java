package com.example.trlab.hlv;

import java.util.ArrayList;

import com.example.trlab.R;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/** An array adapter that knows how to render views when given CustomData classes */
public class CustomArrayAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext ;
    private ArrayList<String> mImageUrls = new ArrayList<String>();

    public CustomArrayAdapter(Context context, ArrayList<String> imageUrls) {
//        super(context, R.layout.custom_data_view, values);
        mContext = context;
        if(mImageUrls != null){
            mImageUrls.clear();
        }
        mImageUrls.addAll(imageUrls);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;

        if (convertView == null) {
            // Inflate the view since it does not exist
            convertView = mInflater.inflate(R.layout.pre_gallery_layout_item, null);

            // Create and save off the holder in the tag so we get quick access to inner fields
            // This must be done for performance reasons
            holder = new Holder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.pregallery_item_image);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        // Populate the text
        UrlImageViewHelper.setUrlDrawable(holder.imageView,mImageUrls.get(position), R.drawable.ic_launcher);

        // Set the color
//        convertView.setBackgroundColor(getItem(position).getBackgroundColor());

        return convertView;
    }

    /** View holder for the views we need access to */
    private static class Holder {
        public ImageView imageView;
    }

    @Override
    public int getCount() {
        return mImageUrls.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
