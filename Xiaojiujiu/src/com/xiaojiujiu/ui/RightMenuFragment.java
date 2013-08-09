package com.xiaojiujiu.ui;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.base.BaseFragment;
import com.beanu.arad.utils.MessageUtil;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;
import com.xiaojiujiu.AppHolder;
import com.xiaojiujiu.R;
import com.xiaojiujiu.ui.user.*;
import com.xiaojiujiu.ui.user.SettingActivity;

/**
 * 右边菜单栏
 * 
 * @author beanu
 */

@EFragment(R.layout.right)
public class RightMenuFragment extends BaseFragment {

	@ViewById RelativeLayout user_layout;
	@ViewById TextView right_setting;
	@ViewById TextView user_name;
	@ViewById TextView user_level;
	@ViewById ImageView user_avatar;
	@ViewById TextView user_collect;

	private final int requestCode = 1;

	@AfterViews
	void init() {
		if (AppHolder.getInstance().user.getUserName() != null) {
			user_name.setText(AppHolder.getInstance().user.getUserName());
			Arad.imageLoader.display(AppHolder.getInstance().user.getUserImageSmall(), user_avatar,
					R.drawable.default_avatar);
		}
	}

	@Click({ R.id.user_name, R.id.user_level })
	void loginClicked() {
		if (AppHolder.getInstance().user.getUserName() != null) {
			// TODO
			MessageUtil.showShortToast(getActivity(), "已经登陆");
		} else {
			Intent user_login = new Intent(getActivity(), LoginActivity_.class);
			startActivityForResult(user_login, requestCode);
			UIUtil.intentSlidIn(getActivity());
		}
	}

	@Click
	void right_setting() {
		Intent setting_intent = new Intent(getActivity(), SettingActivity.class);
		startActivity(setting_intent);
		UIUtil.intentSlidIn(getActivity());
	}

	@Click({ R.id.user_collect, R.id.user_myecard })
	void OnClick(View v) {
		switch (v.getId()) {
		case R.id.user_collect:
			Intent setting_intent = new Intent(getActivity(), MyCollectActivity_.class);
			startActivity(setting_intent);
			UIUtil.intentSlidIn(getActivity());
			break;
		case R.id.user_myecard:

			Intent ecard_intent = new Intent(getActivity(), MyEcardActivity_.class);
			startActivity(ecard_intent);
			UIUtil.intentSlidIn(getActivity());

			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == FragmentActivity.RESULT_OK) {
			if (requestCode == this.requestCode) {
				user_name.setText(AppHolder.getInstance().user.getUserName());
				Arad.imageLoader.display(AppHolder.getInstance().user.getUserImageSmall(), user_avatar,
						R.drawable.default_avatar);
			}
		}
	}

}
