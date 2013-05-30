package com.xiaojiujiu.ui;

import com.xiaojiujiu.R;

import android.app.Activity;

public class UIUtil {

	public static void intentSlidIn(Activity activity) {
		activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}

	public static void intentSlidOut(Activity activity) {
		activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
}