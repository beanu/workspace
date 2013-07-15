package com.xiaojiujiu.ui.widget.dialog;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.xiaojiujiu.AppHolder;
import com.xiaojiujiu.R;
import com.xiaojiujiu.entity.Category;

/** 优惠券类型 */
public class CouponTypeDialogFragment extends SherlockDialogFragment {

	public interface OnCouponTypeSelectedListener {
		public void onSelected(String id);
	}

	OnCouponTypeSelectedListener mListener;

	public static CouponTypeDialogFragment newInstance(String message) {
		CouponTypeDialogFragment f = new CouponTypeDialogFragment();
		Bundle args = new Bundle();
		args.putString("message", message);
		f.setArguments(args);
		return f;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnCouponTypeSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnCouponTypeSelectedListener");
		}
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

		List<Category> list = AppHolder.getInstance().couponType;
		for (int i = 0; i < list.size(); i++) {
			RadioButton b = new RadioButton(getSherlockActivity());
			b.setText(list.get(i).getCategoryName());
			b.setTextColor(getResources().getColor(R.color.black));
			b.setTag(list.get(i).getCategoryID());
			group.addView(b);
		}

		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton button = (RadioButton) group.findViewById(checkedId);
				int id = (Integer) button.getTag();
				mListener.onSelected(String.valueOf(id));
				dismiss();
			}
		});
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
