package com.xiaojiujiu.ui.user;

import android.os.Bundle;
import com.xiaojiujiu.R;
import com.xiaojiujiu.base.MyActivity;
import com.xiaojiujiu.ui.UIUtil;

/**
 * Created by beanu on 13-6-2.
 */
public class SettingActivity extends MyActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_setting_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        enableSlideGestureDetector(true);
        setSlidingEventListener(new SlidingEventListener() {
            @Override
            public void leftSlidingEvent() {
            }

            @Override
            public void rightSlidingEvent() {
                if (!isFinishing())
                    finish();
                UIUtil.intentSlidOut(SettingActivity.this);
            }
        });
    }

}
