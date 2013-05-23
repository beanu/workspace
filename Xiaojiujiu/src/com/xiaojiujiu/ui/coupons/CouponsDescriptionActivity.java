package com.xiaojiujiu.ui.coupons;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;

import com.beanu.arad.base.BaseActivity;
import com.xiaojiujiu.R;

/**
 * 优惠图文详情
 * 
 * @author beanu
 * 
 */
public class CouponsDescriptionActivity extends BaseActivity {

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coupon_description_activity);

		String url = getIntent().getStringExtra("url");
		if (url != null && !url.equals("")) {
			WebView webView = (WebView) findViewById(R.id.coupon_description_webView);
			webView.getSettings().setJavaScriptEnabled(true);
			// TODO 判断全局变量
			webView.getSettings().setLoadsImagesAutomatically(false);
			webView.getSettings().setBuiltInZoomControls(true);
			webView.loadUrl(url);
		}
	}

}
