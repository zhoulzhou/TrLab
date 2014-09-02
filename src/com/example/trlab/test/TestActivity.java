package com.example.trlab.test;

import java.util.ArrayList;
import java.util.List;

import com.example.trlab.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class TestActivity extends Activity {
    private List<Object> list = new ArrayList<Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main_layout);

        initData();
        ListView listView = (ListView) findViewById(R.id.list);
        TestListAdapter adapter = new TestListAdapter(this, list);
        listView.setAdapter(adapter);
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
}