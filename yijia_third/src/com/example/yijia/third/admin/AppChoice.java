package com.example.yijia.third.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

public class AppChoice extends BaseActivity implements OnClickListener{

	private Button wechat_btn,institution_btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setSubView(R.layout.admin_app_choice,false);
		init();
	}

	protected void init(){
		setBtnVisiable(false);
		setTittleText(this.getString(R.string.app_choice));
		
		wechat_btn=(Button)findViewById(R.id.wechat_btn);
		wechat_btn.setOnClickListener(this);
		
		institution_btn=(Button)findViewById(R.id.institution_btn);		
		institution_btn.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.wechat_btn:
			Toast.makeText(getApplicationContext(), Constant.APP_CHOICE_NO_WECHAT, Toast.LENGTH_SHORT).show();
			break;
		case R.id.institution_btn:
			Intent intent=new Intent();
			intent.setClass(AppChoice.this, MainActivity.class);
			startActivity(intent);
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
