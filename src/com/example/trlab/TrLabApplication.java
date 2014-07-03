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
        mp.MpUtils.enablePaymentBroadcast(this, Manifest.permission.PAYMENT_BROADCAST_PERMISSION);
    }
    
    public static Context getAppContext(){
        if(mAppContext == null){
            mAppContext = getAppContext();
        }
        return mAppContext;
    }
}