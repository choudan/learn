package com.example.yijia.third.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.yijia.third.base.RegisterBaseActivity;
import com.example.yijia_third.R;

public class RegisterBegin extends RegisterBaseActivity {
	private Button confirm;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		trtStatus();
		
		setSubView(R.layout.u_register_begin,false);
		
		initTittleBar("ÐÂ¾­ÂçÒÇ");
		
		confirm = (Button) findViewById(R.id.confirm);
		confirm.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (view.getId() == R.id.confirm) {
			Intent intent = new Intent(RegisterBegin.this, RegisterName.class);
			startActivity(intent);
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
