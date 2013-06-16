package com.xiaojiujiu.ui.user;

import android.os.Bundle;

import com.xiaojiujiu.R;
import com.xiaojiujiu.base.MyActivity;
import com.xiaojiujiu.ui.UIUtil;

/**
 * 注册页面
 *
 * @author beanu
 */
public class RegisterActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        enableSlideGestureDetector(true);
        setSlidingEventListener(new SlidingEventListener() {
            @Override
            public void leftSlidingEvent() {

            }

            @Override
            public void rightSlidingEvent() {
                finish();
                UIUtil.intentSlidOut(RegisterActivity.this);
            }
        });
    }

}
