package com.example.trlab.test;

import java.util.List;

import com.example.trlab.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TestListAdapter extends BaseAdapter{
    private Context mContext;
    private List<Object> mDataList;

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
    
}