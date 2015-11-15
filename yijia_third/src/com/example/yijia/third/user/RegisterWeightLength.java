package com.example.yijia.third.user;

import java.text.DecimalFormat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yijia.third.base.RegisterBaseActivity;
import com.example.yijia_third.R;

public class RegisterWeightLength extends RegisterBaseActivity {
	private Button back, next_step;
	private EditText length,weight;
//	private UserInfo userInfo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		trtStatus();
		
		setSubView(R.layout.u_register_weight_length, false);
		
		initTittleBar("新经络仪");
		
//		userInfo=getIntent().getParcelableExtra(Constant.USER_INFO_KEY);
		Log.e(TAG, userInfo.toString());

		back = (Button) findViewById(R.id.back);
		back.setOnClickListener(this);

		next_step = (Button) findViewById(R.id.next_step);
		next_step.setOnClickListener(this);

		length = (EditText) findViewById(R.id.length);
		weight = (EditText) findViewById(R.id.weight);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (view.getId() == R.id.next_step) {
			if (!checkInput(length)) 
				Toast.makeText(getApplicationContext(), "身高不能为空", Toast.LENGTH_SHORT).show();
			else {
				if(Float.parseFloat(length.getText().toString())<80||Float.parseFloat(length.getText().toString())>300)
					Toast.makeText(getApplicationContext(), "身高超出正常范围", Toast.LENGTH_SHORT).show();
				else
					userInfo.setLength(Integer.parseInt(length.getText().toString()));
			}
			
			if(!checkInput(weight))
				Toast.makeText(getApplicationContext(), "体重不能为空", Toast.LENGTH_SHORT).show();			
			else {
				if(Float.parseFloat(weight.getText().toString())<20||Float.parseFloat(weight.getText().toString())>500)
					Toast.makeText(getApplicationContext(), "体重超出正常范围", Toast.LENGTH_SHORT).show();
				else
					userInfo.setWeight(Integer.parseInt(weight.getText().toString()));				
			}
			
			if(checkInput(weight)&&checkInput(length)){
				userInfo.setWeight_index(weightIndex(weight,length));
				Intent intent = new Intent(RegisterWeightLength.this,RegisterTel.class);// 名字
//				intent.putExtra(Constant.USER_INFO_KEY, userInfo);
				startActivity(intent);			
			}	
		} else if (view.getId() == R.id.back)
			finish();	
	}

	private double weightIndex(EditText weight, EditText length) {
		double index;
		double a, b, c;
		DecimalFormat df = new DecimalFormat("########.0");
		a = Double.parseDouble(weight.getText().toString());
		b = Double.parseDouble(length.getText().toString());
		c = a / b / b * 10000;
		index=Double.parseDouble(df.format(c));
		return index;
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
