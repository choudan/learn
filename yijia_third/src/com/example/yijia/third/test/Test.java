package com.example.yijia.third.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.yijia.third.bean.user.UserInfo;
import com.example.yijia.third.bean.user.UserRegister;
import com.example.yijia.third.tool.DataPaser;
import com.example.yijia.wiget.dialog.YAlterDialog;
import com.example.yijia.wiget.dialog.YAlterDialog.OnDialogListener;
import com.example.yijia.wiget.dialog.YProgressDialog;
import com.example.yijia_third.R;
import com.google.gson.Gson;

/**
 * @author  丑旦 
 * @date 创建时间：2015/10/9 下午8:24:25
 * @version 1.0 
 * @parameter  
 * @since  
 * @return 测试代码 
 *
 */
public class Test extends Activity implements OnDialogListener{
	YProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		Button test = (Button)findViewById(R.id.test);
		Button cancel = (Button)findViewById(R.id.cancel);
		Button three = (Button)findViewById(R.id.three);
		UserInfo userInfo = new UserInfo();
		userInfo.setSex(1);
		UserRegister register = new UserRegister();
		register.setDevId("123456789");
		register.setRegisterType(1);
		register.setUserInfo(userInfo);
		DataPaser<UserRegister> d = new DataPaser<UserRegister>();
		System.out.println(d.toJson(register));	
		Gson gson = new Gson();
		System.out.println(gson.toJson(register));
		cancel.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				YAlterDialog dialog = new YAlterDialog(Test.this,3);
//				dialog.setCanceledOnTouchOutside(true);
//				dialog.show();
				if(dialog!=null)
					dialog.dismiss();
			}		
		});
		
		test.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				YAlterDialog dialog = new YAlterDialog(Test.this,3);
//				dialog.setCanceledOnTouchOutside(true);
//				dialog.show();
				dialog = new YProgressDialog(Test.this);
				dialog.show();
			}		
		});
		
		three.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				YAlterDialog dialog = new YAlterDialog(Test.this,3,"biao","shi","fou");
				dialog.setCanceledOnTouchOutside(true);
				dialog.show();
			}		
		});
	}

	@Override
	public void confirm() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		System.out.println("2");
		Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void prompt() {
		// TODO Auto-generated method stub
		System.out.println("3");
		Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
		
	}

}
