/**
 * 
 */
package com.example.yijia.third.msa;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.bean.msa.Msa;
import com.example.yijia.third.bean.user.UserInfo;
import com.example.yijia.third.inst.IeditAlipay;
import com.example.yijia.third.inst.IinputCode;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class MmsaInfo extends BaseActivity implements OnClickListener {
	private Button edit_tel, edit_alipay, confirm;
//	private Button edit_tel, edit_pas, edit_alipay, confirm;
	private LinearLayout edit_tel_pas_pay;
	private EditText name, sex, age, hosiptal, dept, email, wechat_account, qq,
			doctor_license, introduction;
	private TextView service_type;
	private UserInfo userInfo;
	private Msa msa;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.m_query_msa_info);
		userInfo = getUserInfo();
		msa = getMsa();
		init();
	}

	protected void init() {
		// TODO Auto-generated method stub
		setBtnVisiable(true);
		setTittleText(this.getString(R.string.user_info));

		name = (EditText) findViewById(R.id.name);
		name.setText(userInfo.getRealName());

		sex = (EditText) findViewById(R.id.sex);
		sex.setText(userInfo.getSex());

		age = (EditText) findViewById(R.id.age);
		age.setText(userInfo.getBirthday());

		hosiptal = (EditText) findViewById(R.id.hosiptal);
		hosiptal.setText(userInfo.getHospital());

		dept = (EditText) findViewById(R.id.dept);
		dept.setText(userInfo.getDept());

		email = (EditText) findViewById(R.id.email);
		email.setText(userInfo.getEmail());

		wechat_account = (EditText) findViewById(R.id.wechat_account);
		wechat_account.setText(userInfo.getWechat());

		qq = (EditText) findViewById(R.id.qq);
		qq.setText(userInfo.getQq());

		doctor_license = (EditText) findViewById(R.id.doctor_license);
		doctor_license.setText(userInfo.getLicense());

		introduction = (EditText) findViewById(R.id.introduction);
		introduction.setText(userInfo.getIntroduction());

		service_type = (TextView) findViewById(R.id.service_type);
		service_type.setText(msa.getBoundServiceType());

		edit_tel_pas_pay = (LinearLayout) findViewById(R.id.edit_tel_pas_pay);
		edit_tel_pas_pay.setVisibility(View.INVISIBLE);

		edit_tel = (Button) findViewById(R.id.edit_tel);
		edit_tel.setOnClickListener(this);

//		edit_pas = (Button) findViewById(R.id.edit_pas);
//		edit_pas.setOnClickListener(this);

		edit_alipay = (Button) findViewById(R.id.edit_alipay);
		edit_alipay.setOnClickListener(this);

		confirm = (Button) findViewById(R.id.confirm);
		confirm.setOnClickListener(this);

		setEditable(false);
	}

	private void setEditable(boolean flag) {
		setEditable(name, flag);
		setEditable(sex, flag);
		setEditable(age, flag);
		setEditable(hosiptal, flag);
		setEditable(dept, flag);
		setEditable(email, flag);
		setEditable(wechat_account, flag);
		setEditable(qq, flag);
		setEditable(doctor_license, flag);
		setEditable(introduction, flag);
	}

	private UserInfo getUserInfo() {
		userInfo = new UserInfo();
		userInfo.setUserId(1);
		userInfo.setRealName("����");
		userInfo.setPassword("134078");
		userInfo.setSex(1);
		userInfo.setBirthday("203-07-15");
		userInfo.setHospital("�ʼ�ҽԺ");
		userInfo.setDept("��Ŀ�");
		userInfo.setTelephone("18744010000");
		userInfo.setEmail("18744010000@qq.com");
		userInfo.setWechat("18744010000");
		userInfo.setQq("87440100");
		userInfo.setLicense("07440100");
		userInfo.setAlipay("18744010000");
		userInfo.setAlipay("18744010000");
		userInfo.setIntroduction("��ҽ");
		return userInfo;
	}

	private Msa getMsa() {
		msa = new Msa();
		msa.setBoundServiceType("ҽ����ѯ     ר����ѯ");
		return msa;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
//		������֤
		case R.id.edit_tel:
			Intent intent = new Intent(MmsaInfo.this,IinputCode.class);
			startActivity(intent);			
			Log.e(TAG, "��֤�ֻ���...");
			
			break;
//		case R.id.edit_pas:
//			break;
		case R.id.edit_alipay:
			Intent intent1 = new Intent(MmsaInfo.this,IeditAlipay.class);
			startActivity(intent1);			
			Log.e(TAG, "��֤�ֻ���...");
			
			break;	
			
		case R.id.edit:
			setEditable(true);
			edit_tel_pas_pay.setVisibility(View.VISIBLE);
			
			break;
		case R.id.confirm:
			if (!TextUtils.isEmpty(name.getText())
					&& !TextUtils.isEmpty(sex.getText())
					&& !TextUtils.isEmpty(age.getText())
					&& !TextUtils.isEmpty(hosiptal.getText())
					&& !TextUtils.isEmpty(dept.getText())
					&& !TextUtils.isEmpty(introduction.getText())
					&& !TextUtils.isEmpty(doctor_license.getText())
					&& !TextUtils.isEmpty(wechat_account.getText())
					&& !TextUtils.isEmpty(email.getText())
					&& !TextUtils.isEmpty(qq.getText())) {
				getUserInfo();
				setEditable(false);
				Log.e(TAG, getUserInfo().toString());
			} else {
				Toast.makeText(getApplicationContext(), "�뽫��Ϣ��������",Toast.LENGTH_SHORT).show();
			}
			
			break;
		case R.id.back:
			finish();
			
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
