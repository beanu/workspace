package com.xiaojiujiu.ui.entitycard;

import android.os.Bundle;
import com.xiaojiujiu.R;
import com.xiaojiujiu.base.MyActivity;
import com.xiaojiujiu.ui.UIUtil;

/**
 * Created by beanu on 13-6-2.
 */
public class EntityCardDetailActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entity_card_detail_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        enableSlideGestureDetector(true);
        setSlidingEventListener(new SlidingEventListener() {
            @Override
            public void leftSlidingEvent() {

            }

            @Override
            public void rightSlidingEvent() {
                finish();
                UIUtil.intentSlidOut(EntityCardDetailActivity.this);
            }
        });
    }
}
