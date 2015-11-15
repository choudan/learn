package com.example.yijia.third.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yijia.third.bean.user.UserInfo;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

/**
 * @author 丑旦
 * @date 创建时间：2015-8-23 上午11:41:25
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public abstract class RegisterBaseActivity extends Activity implements
		OnClickListener {
	protected final String TAG = this.getClass().getName();
	protected static UserInfo userInfo = new UserInfo();

	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.e(TAG, "接收exit广播,关闭 RegisterBaseActivity");
			finish();
			userInfo = null;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		trtStatus();
		setContentView(R.layout.base_activity);
	}

	protected void trtStatus() {
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			// 透明状态栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
	}
	
	protected void initTittleBar(String str){
		Button back = (Button) findViewById(R.id.back);
		back.setVisibility(View.INVISIBLE);
		Button edit = (Button) findViewById(R.id.edit);
		edit.setVisibility(View.INVISIBLE);
		setTittleText(str);		
	}

	protected boolean setTittleText(String str) {
		TextView username = (TextView) findViewById(R.id.username);
		if (username != null) {
			username.setText(str);
			return true;
		} else
			return false;
	}

	protected boolean checkInput(EditText edit) {
		if (!TextUtils.isEmpty(edit.getText()))
			return true;
		else
			Toast.makeText(getApplicationContext(), "请将内容填写完整",Toast.LENGTH_SHORT).show();
		return false;
	}

	// 由编辑状态切换到不可编辑状态
	protected void setEditable(EditText editText, boolean value) {
		if (value) {
			InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			editText.setFocusableInTouchMode(true);
			editText.requestFocus();
			imm.showSoftInput(editText, 0);
		} else {
			editText.setFocusableInTouchMode(false);
			editText.clearFocus();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		// 在当前的activity中注册广播
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constant.EXIT);
		this.registerReceiver(this.broadcastReceiver, filter);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		this.unregisterReceiver(this.broadcastReceiver);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub

	}

	public void setSubView(int layoutResId, boolean flag) {
		if (flag) {
			LinearLayout llContent = (LinearLayout) findViewById(R.id.substance);
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = inflater.inflate(layoutResId, null);
			llContent.addView(v);
			Log.e(TAG, "-------布局是LinearLayout");
		} else {
			RelativeLayout llContent = (RelativeLayout) findViewById(R.id.substance_relative);
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = inflater.inflate(layoutResId, null);
			llContent.addView(v);
			Log.e(TAG, "-------布局是RelativeLayout");
		}
	}
	
	protected abstract void init();

	protected abstract void hanlderUi();

	protected abstract void interactive();

}
