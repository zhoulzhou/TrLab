package com.example.trlab.pay;
 
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private static final String Service_Id = "af0e4cae0042618149997a59eba0c74b";  //
    private static final String In_App_Secret = "06e3c29f71dcb2e9787c550f3a97f39f"; //
    private static final String Secret = "38779e81d0e7c965cf72a0b4b8ac8eab"; //
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_test_layout);

//        MpUtils.enablePaymentBroadcast(this, Manifest.permission.PAYMENT_BROADCAST_PERMISSION);
        String s = Service_Id + In_App_Secret;
        String md5 = md5(s);
        LogUtil.d("xxx md5=  " + md5);
        
        String sig = "df1ee0f0846019f11d9adb5165b14e9e";
        String sig1 = "billing_type=DCBconfirmation_code=country=VNcurrency=VNDkeyword=message_id=b5133fd70072bd681d1a42aae02dde4boperator=China+Unicompayment_code=1408328573617a2price=15000.0price_wo_vat=13636.36product_name=TH2014081802223700007609sender=service_id=15666d6cb1a368cf1d91d832e6496a3bshortcode=status=OKtest=trueuser_id=460012245326066user_share=0.3a38779e81d0e7c965cf72a0b4b8ac8eab";
        String sig2 = "billing_type=DCB&confirmation_code=&country=CN&currency=RMB&keyword=&message_id=00ed4ac607c428878c8a88b2c3f3bae8&operator=China Unicom&payment_code=1408423566288a14&price=2.0&price_wo_vat=2.0&product_name=111&sender=&service_id=15666d6cb1a368cf1d91d832e6496a3b&shortcode=&status=OK&test=true&user_id=460008322146009&user_share=0.35";
        sig2 = sig2 + "38779e81d0e7c965cf72a0b4b8ac8eab";
        String sig3 = sig2.replace("&", "");
        LogUtil.d("sig3 = " + sig3);
        LogUtil.d("sig1 = " + sig1);
        LogUtil.d("sig1 == sig3 ? " + sig1.equals(sig3));
        String sig4 = "billing_type=DCBconfirmation_code=country=CNcurrency=RMBkeyword=message_id=00ed4ac607c428878c8a88b2c3f3bae8operator=China Unicompayment_code=1408423566288a14price=2.0price_wo_vat=2.0product_name=111sender=service_id=15666d6cb1a368cf1d91d832e6496a3bshortcode=status=OKtest=trueuser_id=460008322146009user_share=0.3538779e81d0e7c965cf72a0b4b8ac8eab";
        String md5s = md5(sig3);
        LogUtil.d("md5s= " + md5s  + " equal = ? " + md5s.equals(sig));

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
        
        getPayId("sd2sa,nda1");
        
        StringBuilder sb = new StringBuilder();
        String sigO = "billing_type=MT&confirmation_code=80326&country=IN&currency=INR&keyword=TXT&message_id=e1b1549139584908faf2b880fcc4a515&operator=Vodafone&payment_code=1416886807837a2&price=10.0&price_wo_vat=8.4&product_name=TH2014112503395400007638&revenue=3.528&sender=XRnXYluwGPyPY0XsXqVFo9ZYUWrlyjEttGfFC8adqsg&service_id=e59b7038105914ba794260e1e5bd95a2&shortcode=9287090010&sig=da54021abfa18f9cb1e5bf2e32bdc9aa&sku=null&status=OK&user_id=404119146121686&user_share=0.42";
//        int index = sigO.indexOf("sig=");
//        String next = sigO.substring(index);
//        String sigBefore = sigO.substring(0, index);
//        sb.append(sigBefore);
//        int index0 = next.indexOf("=");
//        int index1 = next.indexOf("&");
//        String sigF = next.substring(index0+1,index1);
//        String sigAfter = next.substring(index1 + 1);
//        sb.append(sigAfter);
//        LogUtil.d("sigBefore= " + sigBefore);
//        LogUtil.d("sigAfter= " + sigAfter);
//        sb.append("f6237096e13efb2deeb8e932e84550a5");
//        String sigS = sb.toString().replace("&", "");
//        String md5F = md5(sigS);
//        LogUtil.d("= ? " + md5F.equals(sigF));
        
        Pattern p=Pattern.compile("&sig=[^&]*"); 
        Matcher m=p.matcher(sigO); 
        String sigEx = null ;
        while(m.find()) { 
            sigEx = m.group();
            LogUtil.d("s= " + sigEx);
        }
        String sigFX = sigEx.substring(sigEx.indexOf("=")+1);
        String sigExF = sigO.replace(sigEx, "");
        sb.append(sigExF);
        sb.append("f6237096e13efb2deeb8e932e84550a5");
        String md5FX = md5(sb.toString().replace("&", ""));
        LogUtil.d("= ? " + md5FX.equals(sigFX));
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
    
    private ArrayList<String> getPayId(String ids){
        ArrayList al = new ArrayList<String>();
        int index = ids.indexOf(",");
        al.add(ids.substring(0, index));
        al.add(ids.substring(index + 1));
        LogUtil.d("al= " + al.toString());
        return al;
    }
}