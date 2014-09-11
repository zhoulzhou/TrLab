package com.example.trlab.test;

import java.util.List;

import com.example.trlab.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TestListAdapter extends BaseAdapter{
    private Context mContext;
    private List<Object> mDataList;
    public static final int COLUM_NUM = 3;

    public TestListAdapter(Context context, List<Object> list){
        mDataList = list;
        mContext = context;
    }
    
    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        if(mDataList.get(position) instanceof String){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.test_list_title_layout, null);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            title.setText((String) mDataList.get(position));
        }else {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.test_list_item_layout, null);
            
        }
        
        return convertView;
    }
    
    private class ViewHolder{
        public int type;//用于标识是否是当前的view
        LinearLayout[] items = new LinearLayout[COLUM_NUM];
    }
    
    protected int getRealPos(int position, int index) {
        int factor = COLUM_NUM;
//        switch (type) {
//            case Constants.TYPE_WALLPAPER :
//                factor = COLUM_NUM_WALLPAPER;
//                break;
//            case Constants.TYPE_FONT :
//                factor = COLUM_NUM_FONT;
//                break;
//            default:
//                break;
//        }
        return position * factor + index;
    }
}