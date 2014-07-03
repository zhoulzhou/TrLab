package com.example.trlab.pay;

import mp.MpUtils;

import com.example.trlab.utils.LogUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class PaymentStatusReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.d("get pay result broadcast");
        Bundle extras = intent.getExtras();   
        LogUtil.d( "- billing_status:  " + extras.getInt("billing_status"));
        LogUtil.d( "- credit_amount:   " + extras.getString("credit_amount"));
        LogUtil.d( "- credit_name:     " + extras.getString("credit_name"));
        LogUtil.d( "- message_id:      " + extras.getString("message_id") );
        LogUtil.d("- payment_code:    " + extras.getString("payment_code"));
        LogUtil.d( "- price_amount:    " + extras.getString("price_amount"));
        LogUtil.d("- price_currency:  " + extras.getString("price_currency"));
        LogUtil.d("- product_name:    " + extras.getString("product_name"));
        LogUtil.d("- service_id:      " + extras.getString("service_id"));
        LogUtil.d("- user_id:         " + extras.getString("user_id"));
     
        int billingStatus = extras.getInt("billing_status");
        int coins = -1;
        if(billingStatus == MpUtils.MESSAGE_STATUS_BILLED) {
            if (intent.getStringExtra("credit_amount") != null) {
                coins = Integer.parseInt(intent.getStringExtra("credit_amount"));
            }
//          Wallet.addCoins(context, coins);
           LogUtil.d("pay success coins= " + coins);
        }
      }
    
    
}