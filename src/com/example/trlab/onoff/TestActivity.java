package com.example.trlab.onoff;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.trlab.R;
import com.example.trlab.utils.LogUtil;

import android.app.Activity;
import android.os.Bundle;

public class TestActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onoff);
        nCheck();
    }
    
    
    private Pattern getPattern(String reg) {
        return Pattern.compile(reg);
    }
    
    private final String nREG = "\n";
    private void nCheck(){
        String s = "aa\n\n\n\n\nsfdadswe\n\n\n\nfsdadsasdfgd\n\nsf";
        Pattern regex = getPattern(nREG);
        Matcher m = regex.matcher(s);
        if(m.find()){
            MatchResult mr = m.toMatchResult();
            LogUtil.d("MRresult= " + mr.group());
        }
        boolean result = regex.matcher(s).matches();
        LogUtil.d("result= " + result);
    }
}