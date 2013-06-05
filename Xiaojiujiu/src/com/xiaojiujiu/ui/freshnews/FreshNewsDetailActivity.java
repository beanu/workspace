package com.xiaojiujiu.ui.freshnews;

import android.os.Bundle;
import com.xiaojiujiu.R;
import com.xiaojiujiu.base.MyActivity;
import com.xiaojiujiu.ui.UIUtil;

/**商家新鲜事详情页面
 * Created by beanu on 13-6-5.
 */
public class FreshNewsDetailActivity extends MyActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fresh_news_detail_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        enableSlideGestureDetector(true);
        setSlidingEventListener(new SlidingEventListener() {
            @Override
            public void leftSlidingEvent() {

            }

            @Override
            public void rightSlidingEvent() {
                finish();
                UIUtil.intentSlidOut(FreshNewsDetailActivity.this);
            }
        });
    }
}
