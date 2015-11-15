package com.example.yijia.third.base;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.IBinder;
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

import com.example.yijia.third.tool.ActivityTaskUtils;
import com.example.yijia_third.R;

/**
 * @author 丑旦
 * @date 创建时间：2015-8-23 上午11:41:25
 * @version 1.0
 * @parameter
 * @since
 * @return
 */

public abstract class BaseActivity extends Activity implements OnClickListener {
	private InternalReceiver mInternalReceiver;
	public FragmentManager fm;
	public final String TAG = this.getClass().getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		trtStatus();
		setContentView(R.layout.base_activity);
		Log.e(TAG, "---------onCreat ");
		getBackBtn().setOnClickListener(this);
		getEditBtn().setOnClickListener(this);
		getTextView();
		Log.e(TAG, "--------ActivityTaskUtils--------"+new ActivityTaskUtils(this));
	}

	protected void trtStatus() {
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			// 透明状态栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
	}

	/**
	 * 无数据跳转
	 */
	protected void forward(Class<?> cls) {
		Intent intent = new Intent(getApplicationContext(), cls);
		startActivity(intent);
	}
	
	/**
	 *  由编辑状态切换到不可编辑状态
	 */
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

	protected void setVisiable(View v) {
		if (v.getVisibility() == View.GONE)
			v.setVisibility(View.VISIBLE);
		else
			v.setVisibility(View.GONE);
	}

	protected Button getBackBtn() {
		return (Button) findViewById(R.id.back);
	}

	protected Button getEditBtn() {
		return (Button) findViewById(R.id.edit);
	}

	protected TextView getTextView() {
		return (TextView) findViewById(R.id.username);
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

	/**
	 * 加载布局
	 */
	public void setSubView(int layoutResId) {
		LinearLayout llContent = (LinearLayout) findViewById(R.id.substance);
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(layoutResId, null);
		llContent.addView(v);
	}

	/**
	 * 标题
	 */
	protected boolean setTittleText(String str) {
		TextView username = (TextView) findViewById(R.id.username);
		if (username != null) {
			username.setText(str);
			return true;
		} else
			return false;
	}

	/**
	 * 控件显隐切换
	 */
	protected boolean setBtnVisiable(boolean visiable) {
		Button edit = getEditBtn();
		if (visiable) {
			edit.setVisibility(View.VISIBLE);
			return true;
		} else {
			edit.setVisibility(View.INVISIBLE);
			return false;
		}
	}

	/**
	 * 隐藏软键盘(可能有问题)
	 */
	public void hideSoftKeyboard(){
		InputMethodManager mInputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		if(mInputMethodManager!=null){
			View localView = getCurrentFocus();
			if(localView!=null&&localView.getWindowToken()!=null){
				IBinder windoToken = localView.getWindowToken();
				mInputMethodManager.hideSoftInputFromInputMethod(windoToken, 0);
			}
		}
	}
	
	/**
	 * 动态注册广播
	 */
	protected final void registerReceiver(String[] actionArray){
		if(actionArray==null){
			return;
		}
		IntentFilter filter = new IntentFilter();
		for(String action:actionArray)
			filter.addAction(action);
		if(mInternalReceiver==null)
			mInternalReceiver = new InternalReceiver();
		registerReceiver(mInternalReceiver, filter);
	}
	
	private class InternalReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(intent == null||intent.getAction()==null){
				return;
			}
			handleReceiver(context,intent);
		}

	}
	
	/**
	 * 处理广播
	 */
	protected void handleReceiver(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if(intent == null){
			return;
		}	
	}		
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Log.e(TAG, ""+new ActivityTaskUtils(this));
		super.onDestroy();
//		unregisterReceiver(mInternalReceiver);
		Log.e(TAG, "---------onDestroy ");
	}
	

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.e(TAG, "---------onPause ");
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.e(TAG, "---------onRestart ");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		fm = getFragmentManager();
		Log.e(TAG, "---------onResume ");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.e(TAG, "---------onStart ");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.e(TAG, "---------onStop ");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.back)
			finish();
	}

	public void switchFragment(Fragment from, Fragment to, Bundle bundle) {
		String fromTag = from.getClass().getSimpleName();
		Log.e(TAG, "=-=-=fromTag=-=-=" + fromTag);
		String toTag = to.getClass().getSimpleName();
		Log.e(TAG, "=-=-=toTag=-=-=" + toTag);
		Fragment fromFragment = fm.findFragmentByTag(fromTag);
		Fragment toFragment = fm.findFragmentByTag(toTag);
		if (toFragment == null) {
			toFragment = to;
			if (bundle != null)
				toFragment.setArguments(bundle);
		}
		FragmentTransaction ft = fm.beginTransaction();
		if (!toFragment.isAdded()) 
			ft.hide(fromFragment).add(R.id.content, toFragment, toTag);
		else{
			ft.hide(fromFragment).show(toFragment);
			Log.e(TAG, "=-=-=show=-=-="+toFragment);			
		}
		ft.commit();
	}	
	
	protected abstract void init();

	protected abstract void hanlderUi();

	protected abstract void interactive();
}
