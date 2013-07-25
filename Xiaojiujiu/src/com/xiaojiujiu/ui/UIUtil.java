package com.xiaojiujiu.ui;

import android.app.Activity;
import android.content.DialogInterface.OnClickListener;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.xiaojiujiu.R;
import com.xiaojiujiu.ui.widget.dialog.AlertDialogFragment;
import com.xiaojiujiu.ui.widget.dialog.MessageDialogFragment;

public class UIUtil {

	public static void intentSlidIn(Activity activity) {
		activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}

	public static void intentSlidOut(Activity activity) {
		activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	/** 显示一个信息dialog fm =getSupportFragmentManager() */
	public static void showMessageDialog(FragmentManager fm, String message) {
		FragmentTransaction ft = fm.beginTransaction();
		Fragment prev = fm.findFragmentByTag("dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);
		ft.commit();
		MessageDialogFragment dialog = MessageDialogFragment.newInstance(message);
		dialog.show(fm, "dialog");

	}

	/** 显示一个等待dialog */
	public static void showWaitDialog(FragmentManager fm) {
		FragmentTransaction ft = fm.beginTransaction();
		Fragment prev = fm.findFragmentByTag("dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);
		ft.commit();
		MessageDialogFragment dialog = MessageDialogFragment.newInstance("请稍等...");
		dialog.show(fm, "dialog");

	}

	/** AlertDialog */
	public static void showAlertDialog(FragmentManager fm, String title, String message, String positiveButtonText,
			String negativeButtonText, OnClickListener positiveListener, OnClickListener negativeListener) {
		FragmentTransaction ft = fm.beginTransaction();
		Fragment prev = fm.findFragmentByTag("dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);
		ft.commit();

		AlertDialogFragment dialog = AlertDialogFragment.newInstance(title, message, positiveButtonText,
				negativeButtonText);
		dialog.setNegativeListener(negativeListener);
		dialog.setPositiveListener(positiveListener);
		dialog.show(fm, "dialog");

	}


	/** 隐藏一个等待dialog fm=getSupportFragmentManager() */
	public static void hideDialog(FragmentManager fm) {
		DialogFragment prev = (DialogFragment) fm.findFragmentByTag("dialog");
		if (prev != null) {
			prev.dismiss();
		}
	}
}
