package com.example.trlab;

import com.example.trlab.utils.DisplayUtil;

import android.app.Application;
import android.content.Context;

public class TrLabApplication extends Application{
    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        DisplayUtil.initDensity(this);
    }
    
    public static Context getAppContext(){
        if(mAppContext == null){
            mAppContext = getAppContext();
        }
        return mAppContext;
    }
}