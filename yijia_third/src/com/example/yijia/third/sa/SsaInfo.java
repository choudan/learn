/**
 * 
 */
package com.example.yijia.third.sa;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.bean.user.UserInfo;
import com.example.yijia.third.inst.IeditAlipay;
import com.example.yijia.third.inst.IinputCode;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class SsaInfo extends BaseActivity implements OnClickListener{
//	private Button edit_tel, edit_pas, edit_alipay,confirm;
	private Button edit_tel, edit_alipay,confirm;
	private LinearLayout edit_tel_alipay;
	private EditText name, sex, age, hosiptal, dept,
			doctor_license, wechat_account, qq;
	private UserInfo userInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.s_query_sa_info);
		userInfo=setUserInfo();
		init();
	}

	protected void init() {
		// TODO Auto-generated method stub
		setBtnVisiable(true);
		setTittleText(this.getString(R.string.sa_info));
			
		name=(EditText)findViewById(R.id.name);
		name.setText(userInfo.getRealName());

		sex=(EditText)findViewById(R.id.sex);
		sex.setText(userInfo.getSex());
		
		age=(EditText)findViewById(R.id.age);
		age.setText(userInfo.getBirthday());
		
		hosiptal=(EditText)findViewById(R.id.hosiptal);
		hosiptal.setText(userInfo.getHospital());
		
		dept=(EditText)findViewById(R.id.dept);
		dept.setText(userInfo.getDept());
			
		wechat_account=(EditText)findViewById(R.id.wechat_account);
		wechat_account.setText(userInfo.getWechat());
		
		qq=(EditText)findViewById(R.id.qq);
		qq.setText(userInfo.getQq());
		
		doctor_license=(EditText)findViewById(R.id.doctor_license);
		doctor_license.setText(userInfo.getLicense());
		
		edit_tel = (Button) findViewById(R.id.edit_tel);
		edit_tel.setOnClickListener(this);
		
//		edit_pas = (Button) findViewById(R.id.edit_pas);
//		edit_pas.setOnClickListener(this);
		
		edit_alipay = (Button) findViewById(R.id.edit_alipay);
		edit_alipay.setOnClickListener(this);
		
		confirm = (Button) findViewById(R.id.confirm);
		confirm.setOnClickListener(this);		

		edit_tel_alipay = (LinearLayout) findViewById(R.id.edit_tel_alipay);
		
		setEditable(false);
	}
	
	private void setEditable(boolean flag) {
		setEditable(name, flag);
		setEditable(sex, flag);
		setEditable(age, flag);
		setEditable(hosiptal, flag);
		setEditable(dept, flag);
		setEditable(wechat_account, flag);
		setEditable(qq, flag);
		setEditable(doctor_license, flag);
	}

	private UserInfo setUserInfo(){
		userInfo=new UserInfo();
		userInfo.setUserId(1);
		userInfo.setRealName("扁鹊");
		userInfo.setPassword("134078");
		userInfo.setSex(1);
		userInfo.setBirthday("203-07-15");
		userInfo.setHospital("皇家医院");
		userInfo.setDept("针灸科");
		userInfo.setTelephone("18744010000");
		userInfo.setEmail("18744010000@qq.com");
		userInfo.setWechat("18744010000");
		userInfo.setQq("87440100");
		userInfo.setLicense("07440100");
		userInfo.setAlipay("18744010000");
		userInfo.setAlipay("18744010000");
		userInfo.setIntroduction("神医");
		return userInfo;
	}

	private UserInfo getUserInfo() {
		userInfo = new UserInfo();
		userInfo.setRealName(name.getText().toString());
		userInfo.setSex(Integer.parseInt(sex.getText().toString()));
		userInfo.setBirthday(age.getText().toString());
		userInfo.setHospital(hosiptal.getText().toString());
		userInfo.setDept(dept.getText().toString());
		userInfo.setWechat(wechat_account.getText().toString());
		userInfo.setQq(qq.getText().toString());
		userInfo.setLicense(doctor_license.getText().toString());
		return userInfo;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.edit_tel:
			Intent intent = new Intent(SsaInfo.this,IinputCode.class);
			startActivity(intent);			
			Log.e(TAG, "验证手机号...");
			
			break;
//		case R.id.edit_pas:
//			Log.e(TAG, "申请手机验证");
//		
//			break;
		case R.id.edit_alipay:
			Intent intent1 = new Intent(SsaInfo.this,IeditAlipay.class);
			startActivity(intent1);			
			Log.e(TAG, "验证手机号...");
			
			break;
		case R.id.confirm:
			if (!TextUtils.isEmpty(name.getText())
					&& !TextUtils.isEmpty(sex.getText())
					&& !TextUtils.isEmpty(age.getText())
					&& !TextUtils.isEmpty(hosiptal.getText())
					&& !TextUtils.isEmpty(dept.getText())
					&& !TextUtils.isEmpty(doctor_license.getText())
					&& !TextUtils.isEmpty(wechat_account.getText())
					&& !TextUtils.isEmpty(qq.getText())) {
				getUserInfo();
				Log.e(TAG, getUserInfo().toString());
				setEditable(false);
				edit_tel_alipay.setVisibility(View.INVISIBLE);
				Log.e(TAG, "联网提交...");
			}else
				Toast.makeText(getApplicationContext(), "请将信息补充完整", Toast.LENGTH_SHORT).show();									
			break;
		case R.id.back:
			finish();
			break;
		case R.id.edit:
			edit_tel_alipay.setVisibility(View.VISIBLE);
			setEditable(true);
		default:
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
