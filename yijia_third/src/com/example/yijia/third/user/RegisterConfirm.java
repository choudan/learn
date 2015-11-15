package com.example.yijia.third.user;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yijia.third.asyn.inst.AsynRegister;
import com.example.yijia.third.base.RegisterBaseActivity;
import com.example.yijia.third.bean.user.UserInfo;
import com.example.yijia.third.bean.user.UserRegister;
import com.example.yijia.third.tool.Constant;
import com.example.yijia.third.tool.LogUtils;
import com.example.yijia_third.R;

public class RegisterConfirm extends RegisterBaseActivity {
	private Button confirm;
//	private UserInfo userInfo;
	private TextView name,sex,age,height,weight,weight_index,tel,password;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		trtStatus();
		
		setSubView(R.layout.u_register_confirm,false);
		
		initTittleBar("新经络仪");
				
//		userInfo=getIntent().getParcelableExtra(Constant.USER_INFO_KEY);
		Log.e(TAG, userInfo.toString());
		
		confirm = (Button) findViewById(R.id.confirm);
		confirm.setOnClickListener(this);	
		
		name = (TextView) findViewById(R.id.name);	
		name.setText(userInfo.getRealName());
		
		sex = (TextView) findViewById(R.id.sex);
		if(userInfo.getSex()==1)
			sex.setText("男");
		else
			sex.setText("女");
			
		age = (TextView) findViewById(R.id.age);	
		age.setText(userInfo.getBirthday());
		
		height = (TextView) findViewById(R.id.height);
		height.setText(""+userInfo.getLength());
		
		weight = (TextView) findViewById(R.id.weight);
		weight.setText(""+userInfo.getWeight());
		
		weight_index = (TextView) findViewById(R.id.weight_index);
		weight_index.setText(""+userInfo.getWeight_index());
		
		tel = (TextView) findViewById(R.id.tel);
		tel.setText(userInfo.getTelephone());
		
		password = (TextView) findViewById(R.id.password);
		password.setText(userInfo.getPassword());

	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch(view.getId()){
		case R.id.confirm:
			interactive();
//			Intent intent=new Intent(RegisterConfirm.this,Uuser.class);
//			startActivity(intent);
			break;
		}
	}

	@Override
	protected void hanlderUi() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void interactive() {
		// TODO Auto-generated method stub
		AsynRegister asynRegister = new AsynRegister(this ,getRegisterBegin(userInfo)){
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
//				if(result){
//					ToastUtils.showMessage("注册成功");
//					Intent intent = new Intent();
//					intent.setAction(Constant.EXIT);
//					intent.setClass(RegisterConfirm.this, Uuser.class);
//					sendBroadcast(intent);	
//					startActivity(intent);
//				} 
			}	
		};
		asynRegister.execute();
	}
	
	/**
	 * 登录成功后，只将最后一次登录成功的用户信息保存在本地
	 */
	public void saveAccount(){
//		if(userInfo.getTelephone().length()>0||userInfo.getTelephone()!=null){
//			ClientUser clientUser = new ClientUser(userInfo.getTelephone());
//			clientUser.setAppToken(userInfo.getTelephone());
//			clientUser.setPassword(userInfo.getPassword());	
//			clientUser.setBirthday(userInfo.getBirthday());
//			clientUser.setRealName(userInfo.getRealName());
//			clientUser.setSex(userInfo.getSex());
//			clientUser.setRole(Constant.USER_ROLE);
//			AppManager.setClientUser(clientUser);
//			PreferenceUtils.savePreference(PreferenceSetting.SAVE_CLIENTER, clientUser.toString(), true);
//			PreferenceUtils.savePreference(PreferenceSetting.SETTINGS_LOAD_AUTO, true, true);
//		} 
	}
	
	public UserRegister getRegisterBegin(UserInfo userInfo){
		UserRegister userRegister = new UserRegister();
		userRegister.setDevId(""+System.currentTimeMillis());
		userRegister.setRegisterType(Constant.REGISTER_TYPE);
		userRegister.setUserInfo(userInfo);
		LogUtils.getInstance().println("userRegister", userRegister.toString());
		return userRegister;
	}
}
