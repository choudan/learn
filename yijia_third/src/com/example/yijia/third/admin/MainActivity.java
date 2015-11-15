package com.example.yijia.third.admin;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.yijia.third.admin.FragmentQuery.OnBtnClickListener;
import com.example.yijia.third.admin.FragmentSettlement.OnBtnSettleClickListener;
import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.code.QueryCode;
import com.example.yijia.third.inst.AqueryInst;
import com.example.yijia.third.msa.AqueryMsaType;
import com.example.yijia.third.settlement.Assign;
import com.example.yijia.third.settlement.Astatistics;
import com.example.yijia.third.tool.Constant;
import com.example.yijia.third.user.AqueryLogoutUser;
import com.example.yijia_third.R;

/**
 * @author 丑旦
 * @category 待后期整理fragment的切换
 *
 */
public class MainActivity extends BaseActivity implements OnClickListener,
		OnBtnClickListener,OnBtnSettleClickListener {
	private RadioButton add, check, settlement;
	private FragmentAdd fragmentAdd;
	private FragmentQuery fragmentQuery;
	private FragmentSettlement fragmentSettlement;
	private AqueryInst queryInst;
	private AqueryMsaType queryMsa;
	private QueryCode queryCode;
	
	private Astatistics settlementFinanceStaff;
	private Assign assign;
 	private long exitTime = 0;
 	public FragmentManager fm;
	private final static String TAG=MainActivity.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		trtStatus();
		setContentView(R.layout.admin_main);
		init();
	}

	protected void trtStatus() {
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			// 透明状态栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
	}
	
	protected void init() {		
		add = (RadioButton) findViewById(R.id.add);
		add.setOnClickListener(this);
		add.setChecked(true);
		check = (RadioButton) findViewById(R.id.check);
		check.setOnClickListener(this);
		settlement = (RadioButton) findViewById(R.id.settlement);
		settlement.setOnClickListener(this);	
		
		setDefault();
	}

	private void setDefault() {
		fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		fragmentAdd = new FragmentAdd();
		ft.replace(R.id.content, fragmentAdd);
		ft.commit();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		FragmentTransaction ft = fm.beginTransaction();
		switch (v.getId()) {
		case R.id.add:
			Toast.makeText(getApplicationContext(), "11", Toast.LENGTH_SHORT).show();
			fragmentAdd = new FragmentAdd();
			ft.replace(R.id.content, fragmentAdd);
			
			break;
		case R.id.check:
			Toast.makeText(getApplicationContext(), "22", Toast.LENGTH_SHORT).show();
			fragmentQuery = new FragmentQuery();
			ft.add(R.id.content, fragmentQuery,"O");			
			ft.hide(fragmentAdd);
			
			break;
		case R.id.settlement:
			Toast.makeText(getApplicationContext(), "33", Toast.LENGTH_SHORT).show();
			fragmentSettlement = new FragmentSettlement();
			ft.add(R.id.content, fragmentSettlement,"ON");
			ft.hide(fragmentAdd);
			
			break;	
		default:
			break;
		}
		ft.addToBackStack(null);
		ft.commit();
	}
	
	@Override
	public void onBtnSettleClick(View v) {
		// TODO Auto-generated method stub
		FragmentTransaction ft = fm.beginTransaction();
		ft.hide(fragmentSettlement);
		switch(v.getId()){
		case R.id.assign:
			assign=new Assign();
			ft.add(R.id.content, assign,"TW");			
			break;
		case R.id.statistics:
			settlementFinanceStaff=new Astatistics();
			ft.add(R.id.content, settlementFinanceStaff,"TW");
			break;
			default:
				break;
		}
		ft.addToBackStack(null);
		ft.commit();
	}
	
	@Override
	public void onBtnClick(View v) {
		// TODO Auto-generated method stub
		FragmentTransaction ft = fm.beginTransaction();
		Bundle bundle=new Bundle();
		switch (v.getId()) {
		case R.id.query_inst:			
			queryInst = new AqueryInst();
			ft.replace(R.id.content, queryInst);
			break;
		case R.id.query_msa:			
			bundle.putBoolean(Constant.QUERY_TYPE, true);
			queryMsa = new AqueryMsaType();
			queryMsa.setArguments(bundle);
			ft.replace(R.id.content, queryMsa);
			break;
		case R.id.query_code:			
			queryCode = new QueryCode();
			ft.replace(R.id.content, queryCode);
			break;
		case R.id.service_content:			
			bundle.putBoolean(Constant.QUERY_TYPE, false);
			queryMsa = new AqueryMsaType();
			queryMsa.setArguments(bundle);
			ft.replace(R.id.content, queryMsa);			
			break;
		case R.id.logout_user:			
			Intent intent=new Intent(MainActivity.this,AqueryLogoutUser.class);
			startActivity(intent);
			break;
			default:
				break;
		}
		ft.addToBackStack(null);
		ft.commit();
	}

	// 2秒连按2次Back键退出APP
	private void exit() {
		super.finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(MainActivity.this, "再按一次返回键关闭程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				exit();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		getFragmentManager().popBackStack();	
		Log.e(TAG, "onRestart() 已执行");		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		fm = getFragmentManager();
		Log.e(TAG, "onResume() 已执行");
	}

	@Override
	protected void hanlderUi() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interactive() {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
	 * menu; this adds items to the action bar if it is present.
	 * getMenuInflater().inflate(R.menu.main, menu); return true; }
	 * 
	 * @Override public boolean onOptionsItemSelected(MenuItem item) { // Handle
	 * action bar item clicks here. The action bar will // automatically handle
	 * clicks on the Home/Up button, so long // as you specify a parent activity
	 * in AndroidManifest.xml. int id = item.getItemId(); if (id ==
	 * R.id.action_settings) { return true; } return
	 * super.onOptionsItemSelected(item); }
	 */
}
