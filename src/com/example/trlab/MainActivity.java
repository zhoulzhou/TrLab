package com.example.trlab;

import java.util.ArrayList;
import java.util.List;

import com.example.trlab.selfview.TipView;
import com.example.trlab.spinner.SpinnerAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
	private Spinner mSpinner;
	private BaseAdapter mAdapter;
	private ListView mList;
	TextView tv;
	TestAdapter adapter;
	Button btn1, btn2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		initSpinner();
	}
	
	
	private void fomatString(){
        String s = "http://storefsdev.wanyol.com:8090/";

        String s1 = "http://storefs1.wanyol.com:8090/uploadFiles/PThemes/201405/30/b925b42440684b879bbe2a20e87a9f34.theme";
        String s2 = s1.substring(0, s1.indexOf("uploadFiles"));
        String s3 = s1.replaceAll(s2, s);
        log("s2= " + s2);
        log("s3= " + s3);
    }
	
	private void initTipView(){
		TipView tipView = new TipView(this);
		tipView.setTipText("卖弄");
//		setContentView(tipView);
		
		initList();
		mList.addHeaderView(tipView);
		
	}
	
	private  void initList(){
		btn1 = (Button ) findViewById(R.id.btn1);
		btn1.setOnClickListener(this);
		btn2 = (Button) findViewById(R.id.btn2);
		btn2.setOnClickListener(this);
		mList = (ListView ) findViewById(R.id.list);
		tv = new TextView(this);
		tv.setText("HeaderView");
		tv.setHeight(300);
		TextView tv1 = new TextView(this);
		tv1.setText("second header view");
		LinearLayout header = new LinearLayout(this);
		header.addView(tv);
		mList.addHeaderView(header);
		mList.addHeaderView(tv1);
		adapter = new TestAdapter(this);
		mList.setAdapter(adapter);
		showTypeView(0);
	}
	
	private void showTypeView(int index){
		if(index == 0){
			if(tv != null){
//				mList.addHeaderView(tv);
//				mList.setAdapter(adapter);
				tv.setVisibility(View.VISIBLE);
			}
		}else{
			tv.setVisibility(View.GONE);
//			mList.removeHeaderView(tv);
//			mList.setAdapter(adapter);
			
		}
	}
	
	
	public class TestAdapter extends BaseAdapter{
		private Context mContext;
		public TestAdapter(Context context){
			mContext = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 10;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = LayoutInflater.from(mContext);
			arg1 = inflater.inflate(R.layout.list_item, null);
			TextView tv = (TextView) arg1.findViewById(R.id.item_text);
			tv.setText("list item test");
			return arg1;
		}
		
	}
	

	private void initSpinner(){
		mSpinner = (Spinner) findViewById(R.id.search_type);
		mSpinner.setPromptId(R.string.type_tip);
		
		final String[] types = getResources().getStringArray(R.array.plantes);
		List<String> strings = new ArrayList<String>();
		for(int i=0;i<types.length;i++){
			strings.add(types[i]);
		}
//		mAdapter = ArrayAdapter.createFromResource(this, R.array.plantes,  R.layout.simple_spinner_item);
//		mAdapter = new SpinnerAdapter(this,types) {
//            @Override
//            public View getDropDownView(int position, View convertView, ViewGroup parent) {
//                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
//                convertView = inflater.inflate(R.layout.spinner_item_layout, null);
//                TextView label = (TextView) convertView.findViewById(R.id.spinner_item_label);
//                label.setText(types[position]);
//                return convertView;
//            }
//        };
        mAdapter = new SpinnerAdapter(this,types);
//		 ((ArrayAdapter) mAdapter).setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 
		 
		mSpinner.setAdapter(mAdapter);
//		mSpinner.setPromptId(R.string.type_tip);
		mSpinner.setSelection(0,true);
//		mSpinner.setVisibility(View.VISIBLE);
		
		mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				log("arg0= " + arg0 + " arg1= " + arg1 + " arg2= " + arg2 + " arg3= " + arg3);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				log("nothing");
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void log(String s){
		Log.d("zhou",s);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0 == btn1){
			showTypeView(0);
		}else if(arg0 == btn2){
			showTypeView(2);
		}
	}

}
