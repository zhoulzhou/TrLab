package com.example.trlab.os;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

public class MainActivity extends Activity {    
    
    @Override    
    public void onCreate(Bundle savedInstanceState) {    
        super.onCreate(savedInstanceState);    
            
        LinearLayout linearLayout = new LinearLayout(this);    
        linearLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));       
        setContentView(linearLayout);        
        BounceListView bounceListView = new BounceListView(this);    
            
        String[] data = new String[30];    
        for (int i = 0; i < data.length; i++) {    
            data[i] = "回弹效果 " + i;    
        }    
            
        ArrayAdapter<String> arrayAdapter =     
                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, data);    
            
        bounceListView.setAdapter(arrayAdapter);    
            
        linearLayout.addView(bounceListView);    
    }           
}    