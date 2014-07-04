package com.example.trlab.pay;

import com.example.trlab.R;
import com.example.trlab.utils.LogUtil;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BCTestActivity extends Activity{
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_test_layout);
        mContext = this;
        Button btn = (Button) findViewById(R.id.payButton);
        btn.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.test.bc");
                mContext.sendBroadcast(intent);
                LogUtil.d("send bc");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    
    public static class TestBroadcast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtil.d("GET RECEIVER");
        }
        
    }
    
}