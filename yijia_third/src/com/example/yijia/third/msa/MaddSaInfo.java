/**
 * 
 */
package com.example.yijia.third.msa;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.bean.user.UserInfo;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class MaddSaInfo extends BaseActivity implements OnClickListener {
	private Button confirm;
	private EditText name, password, sex, age, hosiptal, dept, tel,
			doctor_license, wechat_account, alipay, qq;
	private UserInfo userInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.m_add_sa_info);
		init();
	}

	protected void init() {
		// TODO Auto-generated method stub
		setBtnVisiable(false);
		setTittleText(this.getString(R.string.add_sa));

		name = (EditText) findViewById(R.id.name);
		name.setOnClickListener(this);
		
		password = (EditText) findViewById(R.id.password);
		password.setOnClickListener(this);

		sex = (EditText) findViewById(R.id.sex);
		sex.setOnClickListener(this);

		age = (EditText) findViewById(R.id.age);
		age.setOnClickListener(this);

		hosiptal = (EditText) findViewById(R.id.hosiptal);
		hosiptal.setOnClickListener(this);

		dept = (EditText) findViewById(R.id.dept);
		dept.setOnClickListener(this);

		tel = (EditText) findViewById(R.id.tel);
		tel.setOnClickListener(this);

		wechat_account = (EditText) findViewById(R.id.wechat_account);
		wechat_account.setOnClickListener(this);

		qq = (EditText) findViewById(R.id.qq);
		qq.setOnClickListener(this);

		doctor_license = (EditText) findViewById(R.id.doctor_license);
		doctor_license.setOnClickListener(this);

		alipay = (EditText) findViewById(R.id.alipay);
		alipay.setOnClickListener(this);

		confirm = (Button) findViewById(R.id.confirm);
		confirm.setOnClickListener(this);

		setEditable(false);
	}

	private UserInfo getUserInfo() {
		userInfo = new UserInfo();

		userInfo.setRealName(name.getText().toString());
		userInfo.setPassword(password.getText().toString());
		userInfo.setSex(Integer.parseInt(sex.getText().toString()));
		userInfo.setBirthday(age.getText().toString());
		userInfo.setHospital(hosiptal.getText().toString());
		userInfo.setDept(dept.getText().toString());
		userInfo.setTelephone(tel.getText().toString());
		userInfo.setWechat(wechat_account.getText().toString());
		userInfo.setQq(qq.getText().toString());
		userInfo.setLicense(doctor_license.getText().toString());
		userInfo.setAlipay(alipay.getText().toString());

		return userInfo;
	}

	private void setEditable(boolean flag) {
		setEditable(name, flag);
		setEditable(password, flag);
		setEditable(sex, flag);
		setEditable(age, flag);
		setEditable(hosiptal, flag);
		setEditable(dept, flag);
		setEditable(tel, flag);
		setEditable(doctor_license, flag);
		setEditable(wechat_account, flag);
		setEditable(alipay, flag);
		setEditable(qq, flag);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.confirm:
			Log.e(TAG, "提交");
			if (!TextUtils.isEmpty(name.getText())
					&& !TextUtils.isEmpty(password.getText())
					&& !TextUtils.isEmpty(sex.getText())
					&& !TextUtils.isEmpty(age.getText())
					&& !TextUtils.isEmpty(hosiptal.getText())
					&& !TextUtils.isEmpty(dept.getText())
					&& !TextUtils.isEmpty(tel.getText())
					&& !TextUtils.isEmpty(doctor_license.getText())
					&& !TextUtils.isEmpty(wechat_account.getText())
					&& !TextUtils.isEmpty(alipay.getText())
					&& !TextUtils.isEmpty(qq.getText())) {
				getUserInfo();
				Log.e(TAG, getUserInfo().toString());
			} else {
				Toast.makeText(getApplicationContext(), "请将信息补充完整",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.back:
			finish();
			break;
		case R.id.name:
		case R.id.password:
		case R.id.sex:
		case R.id.age:
		case R.id.hosiptal:
		case R.id.dept:
		case R.id.tel:
		case R.id.doctor_license:
		case R.id.wechat_account:
		case R.id.alipay:
		case R.id.qq:
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
