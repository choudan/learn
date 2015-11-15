package com.example.yijia.third.inst;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia_third.R;

public class AaddInst extends BaseActivity implements OnClickListener{
	private Button confirm;
	private EditText institution_name,institution_admin,password,sex,
			age,email,tel,wechat_account;
	private boolean editable=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setSubView(R.layout.a_add_inst);
		init();
	}
	
	protected void init(){
		setTittleText(this.getString(R.string.add_institution));
		setBtnVisiable(false);
		
		institution_name=(EditText)findViewById(R.id.institution_name);
		institution_name.setOnClickListener(this);
		institution_admin=(EditText)findViewById(R.id.institution_admin);
		institution_admin.setOnClickListener(this);
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
		
		setEditable(editable);
		
		confirm=(Button)findViewById(R.id.confirm);
		confirm.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.back:
			finish();
			break;
		case R.id.confirm:
			if(editable){
				setEditable(true);
				editable=false;
			}else{
				setEditable(false);
				editable=true;				
			}
			break;
		case R.id.institution_name:
		case R.id.institution_admin:
		case R.id.password:
		case R.id.sex:
		case R.id.age:
		case R.id.email:
		case R.id.tel:
		case R.id.wechat_account:
			if(true)
				setEditable(true);	
			break;
		}
	}			
	
	private void setEditable(boolean flag) {
		setEditable(institution_name,flag);
		setEditable(institution_admin,flag);
		setEditable(password,flag);
		setEditable(sex,flag);
		setEditable(age,flag);	
		setEditable(email,flag);	
		setEditable(tel,flag);	
		setEditable(wechat_account,flag);			
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
