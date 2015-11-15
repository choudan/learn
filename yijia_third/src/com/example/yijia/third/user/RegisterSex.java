package com.example.yijia.third.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.example.yijia.third.base.RegisterBaseActivity;
import com.example.yijia_third.R;

public class RegisterSex extends RegisterBaseActivity {

	private Button back, next_step;
	private boolean click = false;
//	private UserInfo userInfo;

	// RadioButton实现点击换图片背景
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		super.onClick(view);
		switch (view.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.next_step:
			if (!click)
				Toast.makeText(RegisterSex.this, "请选择性别", Toast.LENGTH_SHORT).show();
			else {
				Intent intent = new Intent(RegisterSex.this, RegisterDate.class);
//				intent.putExtra(Constant.USER_INFO_KEY, userInfo);
				startActivity(intent);
			}
			break;
		}
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub		
		trtStatus();
		
		setSubView(R.layout.u_register_sex, false);
		
		initTittleBar("新经络仪");
		
//		userInfo=getIntent().getParcelableExtra(Constant.USER_INFO_KEY);
		Log.e(TAG, userInfo.toString());

		back = (Button) findViewById(R.id.back);
		back.setOnClickListener(this);

		next_step = (Button) findViewById(R.id.next_step);
		next_step.setOnClickListener(this);

		RadioGroup group = (RadioGroup) this.findViewById(R.id.radioGroup);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int radioButtonId = group.getCheckedRadioButtonId();
				if (radioButtonId == R.id.man)
					userInfo.setSex(1);
				else 
					userInfo.setSex(2);				
				click = true;
			}
		});
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
