package com.xiaojiujiu.ui.widget.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/** 提示dialog */
public class AlertDialogFragment extends DialogFragment {

	private OnClickListener positiveListener;
	private OnClickListener negativeListener;

	public static AlertDialogFragment newInstance(String title, String message, String positiveButtonText,
			String negativeButtonText) {
		AlertDialogFragment f = new AlertDialogFragment();

		Bundle args = new Bundle();
		args.putString("title", title);
		args.putString("message", message);
		args.putString("positiveButtonText", positiveButtonText);
		args.putString("negativeButtonText", negativeButtonText);
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
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(getArguments().getString("title")).setMessage(getArguments().getString("message"))
				.setPositiveButton(getArguments().getString("positiveButtonText"), positiveListener)
				.setNegativeButton(getArguments().getString("negativeButtonText"), negativeListener);
		return builder.create();
	}

	public void setPositiveListener(OnClickListener positiveListener) {
		this.positiveListener = positiveListener;
	}

	public void setNegativeListener(OnClickListener negativeListener) {
		this.negativeListener = negativeListener;
	}

}
