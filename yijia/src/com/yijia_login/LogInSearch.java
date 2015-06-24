package com.yijia_login;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.download.doctorback.MyApplication;
import com.example.yijia.R;

public class LogInSearch extends BaseActivity {

	/*
	 * Android系统中的视图组件并不是线程安全的，如果要更新视图，必须在主线程(UI线程、单线程)中更新，主线程一般负责视图组件的更新操作
	 * 不可以在子线程中执行更新的操作。
	 * 参考安卓的消息循环机制，解决方法：Thread + Handler,实现非UI线程更新UI界面(开辟新线程时，就要做此考虑)
	 */
	private Button fanhui,sousuo;
	private EditText yisheng;
	public String result,jsondetail,jsonvalid,doctor_name,doctor_hospital,doctor_department,doctor_id,doctor_string;
	private MyApplication app; 	 
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	     super.onCreate(savedInstanceState);
	     if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
	        //透明状态栏
	         getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);            
	         getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//设置导航栏
		 }	
	     setContentView(R.layout.activity_login_search);
	     init();   
	 }
	 
	 private void init(){		 
		 fanhui=(Button)findViewById(R.id.button2);
		 fanhui.setOnClickListener(listener);
		 
		 yisheng=(EditText)findViewById(R.id.editText1);
		 
		 sousuo=(Button)findViewById(R.id.button1);
		 sousuo.setOnClickListener(listener);
		 
		 app = (MyApplication) getApplication();
	 }
	 
	 private OnClickListener listener=new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.button2:
				finish();
				break;
			case R.id.button1:
				doctor_name=yisheng.getText().toString();
				if(doctor_name.equals(""))
					Toast.makeText(LogInSearch.this, "请输入医生姓名", Toast.LENGTH_SHORT).show();
				else{
					app.setRegisterSearchDoc(doctor_name);
					Intent intent = new Intent();            		            	
	            	intent.setClass(LogInSearch.this,DoctorInfoList.class); 
	            	startActivity(intent);
				}
			}		
		}		 
	 }; 
  }