package com.example.trlab.os;

import com.example.trlab.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class HOverScrollViewTestActivity extends Activity implements View.OnClickListener{

    private HOverScrollView sv;
    private ImageView iv;
    private SeekBar sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hoverscroll_layout_test);
        sv = (HOverScrollView) findViewById(R.id.sv);
        iv = (ImageView)findViewById(R.id.iv);

        findViewById(R.id.btn_type).setOnClickListener(this);
        findViewById(R.id.btn_damk).setOnClickListener(this);
        sb = (SeekBar) findViewById(R.id.seekBar1);
        sb.setProgress(10);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.btn_type:
                break;
        }
    }


}    