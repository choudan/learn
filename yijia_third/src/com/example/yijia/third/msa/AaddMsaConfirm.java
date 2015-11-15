package com.example.yijia.third.msa;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.bean.user.UserInfo;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

public class AaddMsaConfirm extends BaseActivity implements OnClickListener{
	private Button confirm;
	private TextView name,password,sex,age,email,tel,wechat_account,
			hosiptal,dept,qq,doctor_license,alipay,introduction,service_type;
	private UserInfo userInfo;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setSubView(R.layout.a_add_msa_confirm);
		userInfo=getIntent().getExtras().getParcelable(Constant.USER_INFO_KEY);
		Log.e(TAG, "userInfo  "+userInfo.toString());
		init();
	}
	
	protected void init(){
		setTittleText(this.getString(R.string.add_main_service));
		setBtnVisiable(false);
		
		name=(TextView)findViewById(R.id.name);
		name.setText(userInfo.getRealName());
		
		password=(TextView)findViewById(R.id.password);	
		password.setText(userInfo.getPassword());
		
		sex=(TextView)findViewById(R.id.sex);
		sex.setText(userInfo.getSex());
		
		age=(TextView)findViewById(R.id.age);
		age.setText(userInfo.getBirthday());
		
		email=(TextView)findViewById(R.id.email);
		email.setText(userInfo.getEmail());
		
		tel=(TextView)findViewById(R.id.tel);
		tel.setText(userInfo.getTelephone());
		
		wechat_account=(TextView)findViewById(R.id.wechat_account);
		wechat_account.setText(userInfo.getWechat());
		
		hosiptal=(TextView)findViewById(R.id.hosiptal);
		hosiptal.setText(userInfo.getHospital());
		
		dept=(TextView)findViewById(R.id.dept);
		dept.setText(userInfo.getDept());
		
		qq=(TextView)findViewById(R.id.qq);
		qq.setText(userInfo.getQq());
		
		doctor_license=(TextView)findViewById(R.id.doctor_license);
		doctor_license.setText(userInfo.getLicense());
		
		alipay=(TextView)findViewById(R.id.alipay);
		alipay.setText(userInfo.getAlipay());
		
		introduction=(TextView)findViewById(R.id.introduction);
		introduction.setText(userInfo.getIntroduction());
		
		service_type=(TextView)findViewById(R.id.service_type);
		service_type.setText(userInfo.getBoundServiceType());
				
		confirm=(Button)findViewById(R.id.confirm);
		confirm.setOnClickListener(this);
		
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.back:
			finish();
			break;
		case R.id.confirm:	
//			ÉÏ´«
			Log.e(TAG, "userInfo  "+userInfo.toString());
			finish();
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
}
