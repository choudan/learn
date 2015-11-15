package com.example.yijia.third.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.yijia.third.asyn.user.AsynAcquireCode;
import com.example.yijia.third.asyn.user.AsynValidateCode;
import com.example.yijia.third.base.RegisterBaseActivity;
import com.example.yijia.third.tool.ToastUtils;
import com.example.yijia_third.R;

public class RegisterTel extends RegisterBaseActivity {
	private Button back,next_step,request_identity_code,skip;
	private EditText tel,identity_code,password;
//	private UserInfo userInfo;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		trtStatus();
		
		setSubView(R.layout.u_register_tel,false);
		
		initTittleBar("新经络仪");		
				
//		userInfo=getIntent().getParcelableExtra(Constant.USER_INFO_KEY);
		Log.e(TAG, userInfo.toString());
		
		back = (Button) findViewById(R.id.back);
		back.setOnClickListener(this);
		
		next_step = (Button) findViewById(R.id.next_step);
		next_step.setOnClickListener(this);		
		
		
		skip = (Button) findViewById(R.id.skip);
		skip.setOnClickListener(this);	
		
		request_identity_code = (Button) findViewById(R.id.request_identity_code);
		request_identity_code.setOnClickListener(this);		
		
		tel = (EditText) findViewById(R.id.tel);
		tel.setOnClickListener(this);		
		
		identity_code = (EditText) findViewById(R.id.identity_code);
		identity_code.setOnClickListener(this);		
		
		password = (EditText) findViewById(R.id.password);
		password.setOnClickListener(this);		
		
		setEditable(tel,false);
		setEditable(identity_code,false);
		setEditable(password,false);

	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch(view.getId()){
		case R.id.tel:
			setEditable(tel,true);		
			
			break;
		case R.id.identity_code:
			break;
		case R.id.password:
			setEditable(password,true);
			
			break;
		case R.id.request_identity_code:
			if (!TextUtils.isEmpty(tel.getText())) {
				setEditable(identity_code, true);
				requestCode(tel.getText().toString());
			} else {
				ToastUtils.showMessage("请填写有效手机号");
			}
			
			break;
		case R.id.skip:
			intent=new Intent(RegisterTel.this,RegisterConfirm.class);
//			intent.putExtra(Constant.USER_INFO_KEY, userInfo);
			startActivity(intent);
			
			break;
		case R.id.back:
			finish();
			break;
		case R.id.next_step:
			validateCode(identity_code.getText().toString());
			if(checkInput(password)&&checkInput(tel)){			
				intent=new Intent(RegisterTel.this,RegisterConfirm.class);
				userInfo.setTelephone(tel.getText().toString());
				userInfo.setPassword(password.getText().toString());
//				intent.putExtra(Constant.USER_INFO_KEY, userInfo);
				startActivity(intent);
			}			
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

	}
	
	//验证码
	protected void requestCode(String tel){
		AsynAcquireCode asynAcquireCode = new AsynAcquireCode(this ,tel){
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
			}		
		};
		asynAcquireCode.execute();
	}
	
	//验证验证码
	protected void validateCode(String code){
		AsynValidateCode asynValidateCode = new AsynValidateCode(this ,code){
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
			}		
		};
		asynValidateCode.execute();
	}
}
