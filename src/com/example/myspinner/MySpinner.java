package com.example.myspinner;

import java.util.ArrayList;

import com.example.trlab.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 自定义实现的spinner
 */
public class MySpinner extends LinearLayout {
    private LayoutInflater inflater;
	private Context context = null;
	private OnItemSelectedListener listener = null;
	private ArrayList<String> data = null;
	private SpinnerDialogWindow dialog = null;
	private TextView spinnerText;
	private ImageView spinnerImage;
	/**
	 * 构造方法
	 * @param context
	 */
	public MySpinner(Context context) {
		this(context, null);
	}
	
	public MySpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public MySpinner(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	private void init( Context context ){
		this.context = context;
		inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.my_spinner_layout, this);
		spinnerText = (TextView) findViewById(R.id.my_spinner_text);
		spinnerImage = (ImageView) findViewById(R.id.my_spinner_image);
		data = new ArrayList<String>();
		setOnClickListener( new SpinnerButtonOnClickListener() );
	}
	
	public void setText(String text){
		spinnerText.setText(text);
	}
	
	/**
	 * 设置spinner的监听器，用于回调，务必在显示下拉列表前载入
	 * @param listener
	 */
	public void setOnItemSelectedListener( OnItemSelectedListener listener ){
		this.listener = listener;
	}
	
	/**
	      这个方法在显示前调用，装载入数据
	 * @param data
	 */
	public void setData( ArrayList<String> data ){
		this.data = data;
	}
	
	class SpinnerButtonOnClickListener implements OnClickListener{

		public void onClick(View v) {
			if(dialog == null){
				dialog = new SpinnerDialogWindow(context);
			}
			if(!dialog.isShowing()){
				dialog.show();
			}
		}
		
	}
	
	class SpinnerDialogWindow extends Dialog{
		private LayoutInflater inflater = null;
		
		private ListView listView = null;
		private TextView titleView = null;
		private SpinnerDialogAdapter adapter = null;

		public SpinnerDialogWindow(Context context) {
			super(context, R.style.base_dialog);
			// TODO Auto-generated constructor stub
           inflater = LayoutInflater.from( context );
			
		   adapter = new SpinnerDialogAdapter();
			
			View view = inflater.inflate(R.layout.my_spinner_dialog, null);
			titleView = (TextView) view.findViewById(R.id.title);
			listView = (ListView)view.findViewById(R.id.my_spinner_list);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener( new SpinnerListOnItemClickListener() );
			
//			setWidth(MySpinner.this.getLayoutParams().width);
//			setHeight(LayoutParams.WRAP_CONTENT);
			setFocusable(true);		//得到焦点
			setContentView(view);
		}
		
		public void show() {
			initDialog();
			super.show();
		}
		
		private void initDialog() {
			Window window = getWindow();
			DisplayMetrics metric = new DisplayMetrics();
			window.getWindowManager().getDefaultDisplay().getMetrics(metric);
			int width = metric.widthPixels;
			WindowManager.LayoutParams params = window.getAttributes();
			params.width = width;
			params.x = 0;
			params.y = 0;
			window.setAttributes(params);
			setCanceledOnTouchOutside(true);
		}

		/**
		 * 适配器
		 */
		private final class SpinnerDialogAdapter extends BaseAdapter {

			public int getCount() {
				return data.size();
			}

			public Object getItem(int position) {
				return data.get(position);
			}

			public long getItemId(int position) {
				return position;
			}

			public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder holder = null;
				if (convertView == null) {
					holder = new ViewHolder();
					convertView = inflater.inflate(R.layout.my_spinner_dialog_item, null);
					holder.txt = (TextView) convertView.findViewById(R.id.my_spinner_item_text);
					holder.image = (ImageView) convertView.findViewById(R.id.my_spinner_item_image);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				holder.txt.setText(data.get(position));
//				holder.image.setImageResource();
				return convertView;
			}
			
		}
		
		/**
		 * holder类
		 */
		private final class ViewHolder {
			TextView txt;
			ImageView image;
		}
		
		class SpinnerListOnItemClickListener implements OnItemClickListener{

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				TextView mTextView = (TextView) view.findViewById(R.id.my_spinner_item_text);
				String content = mTextView.getText().toString();
				MySpinner.this.setText(content);
				listener.onItemSelected(parent, view, position, id);
				SpinnerDialogWindow.this.dismiss();
			}
			
		}
	}
	
	public void clear(){
		
	}
	
	class SpinnerDropDownPopupWindow extends PopupWindow{
		
		private LayoutInflater inflater = null;
		
		private ListView listView = null;
		
		private SpinnerDropdownAdapter adapter = null;
		
		public SpinnerDropDownPopupWindow( Context context ){
			super(context);
			inflater = LayoutInflater.from( context );
			
			adapter = new SpinnerDropdownAdapter();
			
			View view = inflater.inflate(R.layout.my_spinner, null);
			listView = (ListView)view.findViewById(R.id.my_spinner_list);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener( new SpinnerListOnItemClickListener() );
			
			setWidth(MySpinner.this.getLayoutParams().width);
			setHeight(LayoutParams.WRAP_CONTENT);
			// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
			setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			setFocusable(true);		//得到焦点
			setOutsideTouchable(true);	//点击布局外失去焦点
			setContentView(view);
		}
		
		
		
		public void showAsDropDown(View view) {
//			showAsDropDown(view, 0, 0);
			showAtLocation(view, Gravity.CENTER, 0, 0);
//			showAsDropDown(view);
			update();		//刷新
		}



		/**
		 * 适配器
		 */
		private final class SpinnerDropdownAdapter extends BaseAdapter {

			public int getCount() {
				return data.size();
			}

			public Object getItem(int position) {
				return data.get(position);
			}

			public long getItemId(int position) {
				return position;
			}

			public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder holder = null;
				if (convertView == null) {
					holder = new ViewHolder();
					convertView = inflater.inflate(R.layout.my_spinner_item, null);
					holder.txt = (TextView) convertView.findViewById(R.id.my_spinner_item_text);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				holder.txt.setText(data.get(position));
				
				return convertView;
			}
			
		}
		
		/**
		 * holder类
		 */
		private final class ViewHolder {
			TextView txt;
		}
		
		class SpinnerListOnItemClickListener implements OnItemClickListener{

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				TextView mTextView = (TextView) view.findViewById(R.id.my_spinner_item_text);
				String content = mTextView.getText().toString();
//				MySpinner.this.setText(content);
				listener.onItemSelected(parent, view, position, id);
				SpinnerDropDownPopupWindow.this.dismiss();
			}
			
		}

	}

}