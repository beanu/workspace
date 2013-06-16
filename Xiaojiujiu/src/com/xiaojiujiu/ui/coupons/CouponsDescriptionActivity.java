package com.xiaojiujiu.ui.coupons;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;

import com.xiaojiujiu.R;
import com.xiaojiujiu.base.MyActivity;
import com.xiaojiujiu.ui.UIUtil;

/**
 * 优惠图文详情
 * 
 * @author beanu
 * 
 */
public class CouponsDescriptionActivity extends MyActivity {

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coupon_description_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        enableSlideGestureDetector(true);
        setSlidingEventListener(new SlidingEventListener() {
            @Override
            public void leftSlidingEvent() {

            }

            @Override
            public void rightSlidingEvent() {
                finish();
                UIUtil.intentSlidOut(CouponsDescriptionActivity.this);
            }
        });

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
