package com.example.trlab.webview;

import com.example.trlab.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private WebView contentWebView = null;
	private TextView msgView = null;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_layout);
		contentWebView = (WebView) findViewById(R.id.webview);
		msgView = (TextView) findViewById(R.id.msg);
//		msgView.setVisibility(View.GONE);
		// 启用javascript
		contentWebView.getSettings().setJavaScriptEnabled(true);
		// 从assets目录下面的加载html
		contentWebView.loadUrl("file:///android_asset/wst.html");

		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(btnClickListener);
		contentWebView.addJavascriptInterface(this, "wst");
		
	}

	OnClickListener btnClickListener = new Button.OnClickListener() {
		public void onClick(View v) {
			//无参数调用
			contentWebView.loadUrl("javascript:javacalljs()");
			//传递参数调用
			contentWebView.loadUrl("javascript:javacalljswithargs(" + "'from android client'" + ")");
		}
	};

	//下面的方法没有调用 ？？？
	public void startFunction() {
		Toast.makeText(this, "js调用了java函数", Toast.LENGTH_SHORT).show();
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				msgView.setText(msgView.getText() + "\njs调用了java函数");

			}
		});
	}

	public void startFunction(final String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				msgView.setText(msgView.getText() + "\njs调用了java函数传递参数：" + str);

			}
		});
	}
	
	 /**
     * ANDROID应用开发的时候可能会用到WEBVIEW这个组件，使用过程中可能会接触到WEBVIEWCLIENT与WEBCHROMECLIENT，那么这两个类到底有什么不同呢？
      WebViewClient主要帮助WebView处理各种通知、请求事件的，比如：

      onLoadResource
      onPageStart
      onPageFinish
      onReceiveError
      onReceivedHttpAuthRequest
      WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等比如

      onCloseWindow(关闭WebView)
      onCreateWindow()
      onJsAlert (WebView上alert无效，需要定制WebChromeClient处理弹出)
      onJsPrompt
      onJsConfirm
      onProgressChanged
      onReceivedIcon
      onReceivedTitle
            看上去他们有很多不同，实际使用的话，如果你的WebView只是用来处理一些html的页面内容，只用WebViewClient就行了，如果需要更丰富的处理效果，比如JS、进度条等，就要用到WebChromeClient。
            更多的时候，你可以这样


      WebView webView;
      webView= (WebView) findViewById(R.id.webview);
      webView.setWebChromeClient(new WebChromeClient());
      webView.setWebViewClient(new WebViewClient());
      webView.getSettings().setJavaScriptEnabled(true);
      webView.loadUrl(url);
            这样你的WebView理论上就能有大部分需要实现的特色了
            当然，有些更精彩的内容还是需要你自己添加的
     * */
}