package com.example.trlab.pay;
 
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
 
import mp.MpUtils;
import mp.PaymentActivity;
import mp.PaymentRequest;
import mp.PaymentResponse;
 
import com.example.trlab.Manifest;
/* 以下 references 代表您的一些提供辅助功能的 library， 比如存储虚拟货币金额. */
//import com.your.awesome.application.model.Wallet;
import com.example.trlab.R;
import com.example.trlab.utils.LogUtil;
 
public class PayTestActivity extends PaymentActivity {
    private static final String Service_Id = "dd70755aa9e8ffa37000a4b3814f68eb";  //
    private static final String In_App_Secret = "1f08bd6d3924b8b21e4695ca83b48de8"; //
    private static final String Secret = "9d0a0fa49c7ca54103218339a85925c2"; //
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_test_layout);

//        MpUtils.enablePaymentBroadcast(this, Manifest.permission.PAYMENT_BROADCAST_PERMISSION);
        String s = Service_Id + In_App_Secret;
        String md5 = md5(s);
        LogUtil.d("md5= " + md5 + " s= " + s + " equal = ? " + md5.equals("0e1bb3f5ea8c414cd8295e4a4fd767bc"));

        Button payButton = (Button) findViewById(R.id.payButton);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentRequest.PaymentRequestBuilder builder = new PaymentRequest.PaymentRequestBuilder();
                builder.setService(Service_Id, In_App_Secret);
                builder.setDisplayString("Level 1."); // 这个 level 1 会显示在用户的收据上。
                builder.setProductName("gamerId_level1"); // 不可重复够买的道具靠这个命名来存储。
                builder.setConsumable(false); // 不可重复够买的道具和物品可以在软件重装下恢复。
                builder.setIcon(R.drawable.ic_launcher);
                
                PaymentRequest pr = builder.build();
                makePayment(pr);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView infoText = (TextView) findViewById(R.id.infoText);
//        infoText.setText("Gold coins: " + Wallet.getCoins((Context) this));
    }
    
    public static String md5(String string) {
        byte[] hash = null;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }
}