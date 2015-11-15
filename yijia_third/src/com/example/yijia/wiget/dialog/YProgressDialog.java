package com.example.yijia.wiget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.yijia_third.R;

/**
 * @author 丑旦
 * @date 创建时间：2015-10-1 下午10:40:18
 * @version 1.0
 * @parameter
 * @since
 * @return
 * 
 */
public class YProgressDialog extends Dialog {
	private TextView mTextView;
	private View mImageView;
	private AsyncTask<?, ?, ?> mAsyncTask;

	private final OnCancelListener mCancelListener = new OnCancelListener() {
		@Override
		public void onCancel(DialogInterface dialog) {
			if (mAsyncTask != null) {
				mAsyncTask.cancel(true);
			}
		}
	};

	public YProgressDialog(Context context) {
		super(context, R.style.Theme_Light_CustomDialog_Blue);
		// TODO Auto-generated constructor stub
		mAsyncTask = null;
		setCancelable(true);
		setContentView(R.layout.progress_diloag);
		mTextView = (TextView) findViewById(R.id.textview);
		mImageView = findViewById(R.id.imageview);
		setOnCancelListener(mCancelListener);
	}

	public YProgressDialog(Context context, int resid) {
		this(context);
		mTextView.setText(resid);
	}

	public YProgressDialog(Context context, CharSequence text) {
		this(context);
		mTextView.setText(text);
	}

	public YProgressDialog(Context context, AsyncTask<?, ?, ?> asyncTask) {
		this(context);
		this.mAsyncTask = asyncTask;
	}

	public YProgressDialog(Context context, CharSequence text,
			AsyncTask<?, ?, ?> asyncTask) {
		this(context);
		mTextView.setText(text);
		this.mAsyncTask = asyncTask;
	}

	public final void setText(CharSequence text) {
		mTextView.setText(text);
	}

	public final void dismiss() {
		super.dismiss();
	}

	public final void show() {
		super.show();
		Animation loadAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.loading);
		mImageView.startAnimation(loadAnimation);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}	
}
