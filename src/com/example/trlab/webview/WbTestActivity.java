package com.example.trlab.webview;

import com.example.trlab.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WbTestActivity extends Activity{
	WebView mWeb ;
	ProgressBar mProgressBar;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wb_test_layout);
		
		mWeb = (WebView) findViewById(R.id.web);
		mProgressBar = (ProgressBar) findViewById(R.id.web_bar);
		
		mWeb.getSettings().setJavaScriptEnabled(true);
		mWeb.getSettings().setSupportZoom(true);
		mWeb.getSettings().setBuiltInZoomControls(true);
		mWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null) {
                	mWeb.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

        });
		mWeb.setWebChromeClient(new WebChromeClient(){

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				mProgressBar.setProgress(newProgress);
				if(newProgress == 100){
					mProgressBar.setVisibility(View.GONE);
				}
				super.onProgressChanged(view, newProgress);
				
			}
			
		});
		mWeb.loadUrl("http://www.sina.com");
	}
	
}