package com.example.yijia.third.msa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.bean.user.UserInfo;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

public class AaddMsa extends BaseActivity implements OnClickListener{
	private Button next_step;
	private EditText name,password,sex,age,email,tel,wechat_account,
			hosiptal,dept,qq,doctor_license,alipay,introduction;
	private LinearLayout type;
	private String headImgUrl;
	private UserInfo userInfo;	
	private boolean editable=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setSubView(R.layout.a_add_msa);
		init();
	}
	
	protected void init(){
		setTittleText(this.getString(R.string.add_main_service));
		setBtnVisiable(false);
		
		name=(EditText)findViewById(R.id.name);
		name.setOnClickListener(this);
		
		password=(EditText)findViewById(R.id.password);	
		password.setOnClickListener(this);
		
		sex=(EditText)findViewById(R.id.sex);
		sex.setOnClickListener(this);
		
		age=(EditText)findViewById(R.id.age);
		age.setOnClickListener(this);
		
		email=(EditText)findViewById(R.id.email);
		email.setOnClickListener(this);
		
		tel=(EditText)findViewById(R.id.tel);
		tel.setOnClickListener(this);
		
		wechat_account=(EditText)findViewById(R.id.wechat_account);
		wechat_account.setOnClickListener(this);
		
		hosiptal=(EditText)findViewById(R.id.hosiptal);
		hosiptal.setOnClickListener(this);
		
		dept=(EditText)findViewById(R.id.dept);
		dept.setOnClickListener(this);
		
		qq=(EditText)findViewById(R.id.qq);
		qq.setOnClickListener(this);
		
		doctor_license=(EditText)findViewById(R.id.doctor_license);
		doctor_license.setOnClickListener(this);
		
		alipay=(EditText)findViewById(R.id.alipay);
		alipay.setOnClickListener(this);
		
		introduction=(EditText)findViewById(R.id.introduction);
		introduction.setOnClickListener(this);
		
		type=(LinearLayout)findViewById(R.id.type);
		type.setVisibility(View.GONE);
		
		next_step=(Button)findViewById(R.id.next_step);
		next_step.setOnClickListener(this);
		
		setEditable(editable);
	}
	
	private void setEditable(boolean flag) {
		setEditable(name,flag);
		setEditable(password,flag);
		setEditable(sex,flag);
		setEditable(age,flag);
		setEditable(email,flag);	
		setEditable(tel,flag);	
		setEditable(wechat_account,flag);	
		setEditable(hosiptal,flag);			
		setEditable(dept,flag);			
		setEditable(qq,flag);			
		setEditable(doctor_license,flag);			
		setEditable(alipay,flag);			
		setEditable(introduction,flag);			
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.back:
			finish();
			break;
		case R.id.next_step:
			if(editable){
				setEditable(true);
				editable=false;
			}else{
				setEditable(false);
				editable=true;				
			}
			userInfo=makeBean();
			Intent intent=new Intent(AaddMsa.this,AaddMsaType.class);
			Bundle mbundle=new Bundle();
			mbundle.putParcelable(Constant.USER_INFO_KEY, userInfo);
			intent.putExtras(mbundle);
			startActivity(intent);
			finish();
			break;
		case R.id.name:
		case R.id.password:
		case R.id.sex:
		case R.id.age:
		case R.id.email:
		case R.id.tel:
		case R.id.wechat_account:
		case R.id.hosiptal:
		case R.id.dept:
		case R.id.qq:
		case R.id.doctor_license:
		case R.id.alipay:
		case R.id.introduction:
			if(true)
				setEditable(true);	
			break;			
		}
	}			
	
	public UserInfo makeBean(){
		UserInfo userInfo=new UserInfo();
		userInfo.setPassword(password.getText().toString());
		userInfo.setAlipay(alipay.getText().toString());
		userInfo.setBirthday(age.getText().toString());
		userInfo.setDept(dept.getText().toString());
		userInfo.setEmail(email.getText().toString());
		userInfo.setHeadImgUrl(headImgUrl);
		userInfo.setHospital(hosiptal.getText().toString());
		userInfo.setLicense(doctor_license.getText().toString());
		userInfo.setQq(qq.getText().toString());
		userInfo.setRealName(name.getText().toString());
		userInfo.setSex(Integer.parseInt(sex.getText().toString()));
		userInfo.setTelephone(tel.getText().toString());
		userInfo.setWechat(wechat_account.getText().toString());
		userInfo.setIntroduction(introduction.getText().toString());
		return userInfo;
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
