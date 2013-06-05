package com.xiaojiujiu.ui.entitycard;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.beanu.arad.utils.MessageUtil;
import com.google.zxing.CaptureActivity;
import com.xiaojiujiu.R;
import com.xiaojiujiu.base.MyActivity;
import com.xiaojiujiu.ui.UIUtil;

/**
 * 添加实体卡类
 * Created by beanu on 13-6-2.
 */
public class AddEntityCardActivity extends MyActivity implements View.OnClickListener {

    private ImageView entity_card_add_scan;
    private EditText code_editText;
    static final private int GET_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entity_card_add_activity);

        entity_card_add_scan = (ImageView) findViewById(R.id.entity_card_add_scan);
        entity_card_add_scan.setOnClickListener(this);
        code_editText=(EditText)findViewById(R.id.entity_card_add_code_textView);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        enableSlideGestureDetector(true);
        setSlidingEventListener(new SlidingEventListener() {
            @Override
            public void leftSlidingEvent() {

            }

            @Override
            public void rightSlidingEvent() {
                finish();
                UIUtil.intentSlidOut(AddEntityCardActivity.this);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.entity_card_add_scan:
                startActivityForResult(new Intent(this, CaptureActivity.class), GET_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 当前只有一个返回值
        if (resultCode == RESULT_OK && requestCode == GET_CODE) {
            String result = data.getExtras().getString("result");
            if (TextUtils.isEmpty(result)) {
                MessageUtil.showShortToast(this,"请重新扫描");
                return;
            }

            code_editText.setText(result);
        }
    }
}
