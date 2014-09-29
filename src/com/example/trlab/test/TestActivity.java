package com.example.trlab.test;

import java.util.ArrayList;
import java.util.List;

import com.example.trlab.R;
import com.example.trlab.utils.LogUtil;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class TestActivity extends Activity {
    private List<Object> list = new ArrayList<Object>();
    private ArrayList<Long> mMaterIdList = new ArrayList<Long>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main_layout);

        initData();
        ListView listView = (ListView) findViewById(R.id.list);
        TestListAdapter adapter = new TestListAdapter(this, list);
        listView.setAdapter(adapter);
        
//        md5();
        
//        spiltString();
        
        subString();
    }
    
    private void subString(){
        String result = "http://www.badi.com/sldj?sk=2&masterids=34,123,4323";
        int startIndex = result.lastIndexOf("=");
        String ids = result.substring(startIndex + 1);
        LogUtil.d("ids= " + ids);
    }
    
    private void spiltString(){
        String s = "2323441,2324343,54646";
        String[] sS = s.split(",");
        for(String s1 : sS){
            LogUtil.d("s1= " + s1.toString());
            mMaterIdList.add(Long.valueOf(s1));
        }
        LogUtil.d("sS= " + sS.toString());
        LogUtil.d("mMaterIdList= " + mMaterIdList.toString());
    }
    
    private void initData(){
        list.add("first");
        for (int i = 0; i < 6; i++) {
            TestData data = new TestData();
            data.name = " " + i;
            list.add(data);
        }
        
        list.add("second");
        for (int i = 0; i < 6; i++) {
            TestData data = new TestData();
            data.name = " " + i;
            list.add(data);
        }
        
        list.add("third");
        for (int i = 0; i < 6; i++) {
            TestData data = new TestData();
            data.name = " " + i;
            list.add(data);
        }
        
    }
    
    private void md5(){
        String s = "D5:89:64:DE:2E:D0:F1:14:5A:F9:9A:BA:E5:07:2F:6D";
        String s1 = s.replaceAll(":", "");
        String s2 = s1.toLowerCase();
        LogUtil.d("s2= " + s2);
    }
}