/**
 * 
 */
package com.example.yijia.third.inst;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.bean.user.UserInfo;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class IqueryInstInfo extends BaseActivity implements OnClickListener {
//	private Button edit_tel, edit_pas, confirm;
	private Button edit_tel, confirm;
	private LinearLayout edit_tel_pas;
	private EditText institution_name, institution_admin, password, sex, age,
			email, tel, wechat_account;
	private UserInfo userInfo;
	private boolean editable=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.i_inst_info);
		init();
	}

	protected void init() {
		// TODO Auto-generated method stub
		userInfo = getData();
		Log.e(TAG, userInfo.toString());

		setBtnVisiable(true);
		setTittleText(userInfo.getRealName());// 标题
		
		edit_tel_pas=(LinearLayout)findViewById(R.id.edit_tel_pas);

		institution_name = (EditText) findViewById(R.id.institution_name);
		institution_name.setText(userInfo.getInstName());

		institution_admin = (EditText) findViewById(R.id.institution_admin);
		institution_admin.setText(userInfo.getRealName());

		password = (EditText) findViewById(R.id.password);
		password.setText(userInfo.getPassword());

		sex = (EditText) findViewById(R.id.sex);
		sex.setText(userInfo.getSex());

		age = (EditText) findViewById(R.id.age);
		age.setText(userInfo.getBirthday());

		email = (EditText) findViewById(R.id.email);
		email.setText(userInfo.getEmail());

		tel = (EditText) findViewById(R.id.tel);
		tel.setText(userInfo.getTelephone());
		
		wechat_account = (EditText) findViewById(R.id.wechat_account);
		wechat_account.setText(userInfo.getWechat());

		edit_tel = (Button) findViewById(R.id.edit_tel);
		edit_tel.setOnClickListener(this);
//		edit_pas = (Button) findViewById(R.id.edit_pas);
//		edit_pas.setOnClickListener(this);
		confirm = (Button) findViewById(R.id.confirm);
		confirm.setOnClickListener(this);
		
		setEditable(editable);
	}
	
	private void setEditable(boolean flag) {
		setEditable(institution_name,flag);
		setEditable(institution_admin,flag);
		setEditable(sex,flag);
		setEditable(age,flag);	
		setEditable(email,flag);	
		setEditable(wechat_account,flag);			
		setEditable(tel,false);
		setEditable(password,false);
	}
	
	private UserInfo getData() {
		// TODO Auto-generated method stub
		UserInfo userInfo = new UserInfo();
		userInfo.setPassword("124568");
		userInfo.setAlipay("1789456");
		userInfo.setBirthday("1986-08-08");
		userInfo.setDept("内科");
		userInfo.setEmail("18745678@qq.com");
		userInfo.setHeadImgUrl("http://lll");
		userInfo.setHospital("301总院");
		userInfo.setLicense("178945828");
		userInfo.setQq("456789123");
		userInfo.setRealName("陈总");
		userInfo.setSex(1);
		userInfo.setTelephone("18744024567");
		userInfo.setWechat("1456789");
		userInfo.setIntroduction("神医在世");
		return userInfo;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.edit_tel:
//验证手机号		
			Intent intent = new Intent(IqueryInstInfo.this,IinputCode.class);
			startActivity(intent);			
			Log.e(TAG, "验证手机号...");
			break;
//		case R.id.edit_pas:
////验证手机号			
//		
//			Log.e(TAG, "验证手机号...");
//			break;
		case R.id.confirm:
			setEditable(false);
//			提交数据
			
			Log.e(TAG, "提交数据...");
			break;
		case R.id.back:			
			finish();
			break;
		case R.id.edit:
			edit_tel_pas.setVisibility(View.VISIBLE);
			setEditable(true);
			break;
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
