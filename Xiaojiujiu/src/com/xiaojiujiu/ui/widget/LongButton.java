package com.xiaojiujiu.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xiaojiujiu.R;

/**
 * 长行的button
 * 
 * @author beanu
 * 
 */
public class LongButton extends RelativeLayout {

	private ImageView leftImage;
	private TextView textView;

	public LongButton(Context context) {
		super(context);
		init(context);
	}

	public LongButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LongButton);
		Drawable drawable = typedArray.getDrawable(R.styleable.LongButton_drawableLeft);
		if (drawable != null) {
			leftImage.setImageDrawable(drawable);
		}

		String text = typedArray.getString(R.styleable.LongButton_text);
		if (text == null) {
			int id = typedArray.getResourceId(R.styleable.LongButton_text, 0);
			text = context.getResources().getString(id);
		}
		textView.setText(text);
		typedArray.recycle();
	}

	private void init(Context context) {
		LayoutInflater.from(context).inflate(R.layout.module_button, this, true);
		leftImage = (ImageView) findViewById(R.id.module_button_tag);
		textView = (TextView) findViewById(R.id.module_button_textView);
	}

	public void setDrawableLeft(int resId) {
		leftImage.setImageResource(resId);
	}

	public void setText(String text) {
		textView.setText(text);
	}
}
