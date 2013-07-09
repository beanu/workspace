package com.xiaojiujiu.ui.widget.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.actionbarsherlock.app.SherlockDialogFragment;

/** 优惠券类型 */
public class CouponTypeDialogFragment extends SherlockDialogFragment {

	public static CouponTypeDialogFragment newInstance(String message) {
		CouponTypeDialogFragment f = new CouponTypeDialogFragment();
		Bundle args = new Bundle();
		args.putString("message", message);
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, 0);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// 使用Builder类更方便
		AlertDialog.Builder builder = new AlertDialog.Builder(getSherlockActivity());
		RadioGroup group = new RadioGroup(getSherlockActivity());

		for (int i = 0; i < 2; i++) {
			RadioButton b = new RadioButton(getSherlockActivity());
			b.setText("b" + i);
			group.addView(b);
		}

		builder.setView(group);
		builder.setTitle("选择优惠类型");

		// .setPositiveButton(R.string.fire, new
		// DialogInterface.OnClickListener() {
		// public void onClick(DialogInterface dialog, int id) {
		// // 相当于确定
		// }
		// }).setNegativeButton(R.string.cancel, new
		// DialogInterface.OnClickListener() {
		// public void onClick(DialogInterface dialog, int id) {
		// // 相当于取消
		// }
		// });
		// 创建AlertDialog对象并返回
		return builder.create();
	}
}
