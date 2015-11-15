package com.example.yijia.third.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yijia.third.base.RegisterBaseActivity;
import com.example.yijia_third.R;

public class RegisterName extends RegisterBaseActivity {
	private Button back,next_step;
	private EditText name;
	private TextView input_name_prompt;
//	private UserInfo userInfo;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
//		userInfo=new UserInfo();	
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		trtStatus();
		
		setSubView(R.layout.u_register_name,false);
		
		initTittleBar("新经络仪");		
				
		back = (Button) findViewById(R.id.back);
		back.setOnClickListener(this);
		
		next_step = (Button) findViewById(R.id.next_step);
		next_step.setOnClickListener(this);		
		
		name = (EditText) findViewById(R.id.name);
		
		input_name_prompt=(TextView)findViewById(R.id.input_name_prompt);
		SpannableStringBuilder style=new SpannableStringBuilder(this.getString(R.string.input_name_prompt));   
        style.setSpan(new ForegroundColorSpan(Color.RED),29,40,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);     //设置指定位置文字的颜色
        input_name_prompt.setText(style); 
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (view.getId() == R.id.next_step) {
			if(checkInput(name)){
				Intent intent = new Intent(RegisterName.this, RegisterSex.class);//名字
				userInfo.setRealName(name.getText().toString());
//				intent.putExtra(Constant.USER_INFO_KEY, userInfo);
				startActivity(intent);								
			}
		}else if(view.getId() == R.id.back)
			finish();
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
