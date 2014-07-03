package com.example.trlab.utils;

import android.util.Log;

public class LogUtil{
    private static boolean logEnable = true;
    
    public static void d(String str){
        if (logEnable) {
            Log.d("zhou", str);
        }
    }
}