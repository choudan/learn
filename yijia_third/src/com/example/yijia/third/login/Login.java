package com.example.yijia.third.login;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.yijia.third.base.BaseApp;
import com.example.yijia.third.bean.common.ClientUser;
import com.example.yijia.third.bean.common.LoginBean;
import com.example.yijia.third.tool.AppManager;
import com.example.yijia.third.tool.LogUtils;
import com.example.yijia.third.tool.PreferenceSetting;
import com.example.yijia.third.tool.PreferenceUtils;
import com.example.yijia.third.tool.ToastUtils;
import com.example.yijia_third.R;
/**
 * @author 丑旦
 * @date 创建时间：2015-8-12 上午8:08:23
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class Login extends Activity implements OnClickListener {
	private Button login_btn, connect_btn;
	private EditText account, password;
	private LoginBean loginBean;
	private final String TAG = this.getClass().getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_login);
		init();
	}

	private void init() {	
		login_btn = (Button) findViewById(R.id.login_btn);
		login_btn.setOnClickListener(this);
		connect_btn = (Button) findViewById(R.id.connect_btn);
		connect_btn.setOnClickListener(this);

		account = (EditText) findViewById(R.id.account);
		account.setOnClickListener(this);
		
		password = (EditText) findViewById(R.id.password);
		password.setOnClickListener(this);
		
		loginBean=getData();
		Log.e(TAG, loginBean.toString());
		((BaseApp)getApplication()).setRoleId(loginBean.getRoleId());
		((BaseApp)getApplication()).setRoleName(loginBean.getRealName());
		
		setEditable(false);
	}

	private boolean isFirstUse(String appToken,String password) {
		String account = PreferenceUtils.getSharedPreferences().getString(
				PreferenceSetting.SAVE_CLIENTER.getmId(), (String)PreferenceSetting.SAVE_CLIENTER
						.getmDefaultValue());
		LogUtils.getInstance().println("account", ""+account);
		if(account.equals("")||account==null){
			return true;
		}else{
			ClientUser clientUser = AppManager.getClientUser();
			if(clientUser.getAppToken().equals(appToken)&&clientUser.getPassword().equals(password))
				LogUtils.getInstance().println("用户名和密码正确", "欢迎回来");	
			else
				return true;								
		}
		return false;
	}
	
	/**
	 * 登录成功后，只将最后一次登录成功的用户信息保存在本地
	 */
	public void saveAccount(){
		String accountNum = account.getText().toString().trim();
		String pas = password.getText().toString().trim();
		ClientUser clientUser = new ClientUser(accountNum);
		clientUser.setAppToken(accountNum);
		clientUser.setPassword(pas);
		AppManager.setClientUser(clientUser);
		PreferenceUtils.savePreference(PreferenceSetting.SAVE_CLIENTER, clientUser.toString(), true);
		PreferenceUtils.savePreference(PreferenceSetting.SETTINGS_LOAD_AUTO, true, true);
	}
	
	/**
	 * 验证身份
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_btn:
			if(!TextUtils.isEmpty(account.getText())&&!TextUtils.isEmpty(password.getText())){
				boolean firstUse = isFirstUse(account.getText().toString(),password.getText().toString());
				LogUtils.getInstance().println("firstUse", ""+firstUse);
//				if(firstUse){
//					//调用登录线程,更新本地保存信息
//					AsynIntfLogin asynIntfLogin = new AsynIntfLogin(this,account.getText().toString(),password.getText().toString()){
//						@Override
//						protected void onPostExecute(String result) {
//							// TODO Auto-generated method stub
//							super.onPostExecute(result);
//							saveAccount();//注意信息的完整性，逻辑处理
//						}					
//					};
//					asynIntfLogin.execute();
//				}										
			}else
				ToastUtils.showMessage(this.getString(R.string.input_error));
			
//			if(!TextUtils.isEmpty(account.getText())){
//				if (account.getText().toString().equals("0")) {
//					((BaseApp)getApplication()).setRole(0);
//					startActivity(new Intent(Login.this, AppChoice.class));
//				} else if (account.getText().toString().equals("1")) {
//					((BaseApp)getApplication()).setRole(1);
//					startActivity(new Intent(Login.this, Iinst.class));
//					
//				} else if (account.getText().toString().equals("2")) {
//					((BaseApp)getApplication()).setRole(2);
//					startActivity(new Intent(Login.this, Mmsa.class));
//					
//				} else if (account.getText().toString().equals("3")) {
//					((BaseApp)getApplication()).setRole(3);
//					startActivity(new Intent(Login.this, Ssa.class));
//					
//				} else if (account.getText().toString().equals("4")) {
//					((BaseApp)getApplication()).setRole(4);
//					Intent intent = new Intent();
//					intent.setClass(Login.this, Uuser.class);
//					intent.putExtra(Constant.UNFINISHED_SURVEY,loginBean.getUnfinishedSurvey());
//					intent.putExtra(Constant.ORDER_STATE, loginBean.getOrderState());
//					startActivity(intent);
//				}
//				finish();		
//			}else{
//				Toast.makeText(getApplicationContext(), "请输入用户名和密码", Toast.LENGTH_SHORT).show();
//			}
									
			break;
		case R.id.connect_btn:
			
			break;
		case R.id.account:
			setEditable(account,true);
			break;
		case R.id.password:
			setEditable(password,true);
			break;
		default:
			break;
		}
	}
	
	protected void setEditable(boolean value){
		setEditable(account,value);
		setEditable(password,value);
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
	
	private LoginBean getData(){
		loginBean=new LoginBean();
		loginBean.setRoleId(25888);
		loginBean.setRealName("定远");
		loginBean.setAccountState(0);
		loginBean.setRole(4);
		loginBean.setIsPaid(1);
		loginBean.setUnfinishedSurvey(1);
		loginBean.setOrderState(1);
		loginBean.setLoadState(1);
		return loginBean;
	}
}
