package com.example.myspinner;

import java.util.ArrayList;

import com.example.trlab.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Test extends Activity{

	private MySpinner spinner = null;
	private ArrayList<String> data = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		
		spinner = (MySpinner) findViewById(R.id.spinner_btn);
		data = new ArrayList<String>();
		String[] item = { "   -", "大学", "中学中学", "小学", "企业", "地区", "商业"};
		for( int i = 0; i < 7; i++ ){
			data.add(item[i]);
		}
		spinner.setData(data);
		spinner.setOnItemSelectedListener(new SpinnerOnItemSelectedListener());
	}
	
	class SpinnerOnItemSelectedListener implements OnItemSelectedListener{

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			System.out.println("Data--->" + data.get(arg2));
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			
		}
		
	}

	
}