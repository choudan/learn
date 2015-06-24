package com.yijia_login;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.download.doctorback.MyApplication;
import com.example.yijia.R;

public class LogInLength extends BaseActivity {

	private EditText shengao;
	private EditText tizhong;
	private String length,weight;
	private MyApplication app;
	int flag;
	float s,z;
	
	 @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);            
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); //隐藏软键盘		
		}	
        setContentView(R.layout.activity_highweight);
        app=(MyApplication)getApplication();	
        
        Button fanhui=(Button)findViewById(R.id.button2);
	    fanhui.setOnClickListener(new OnClickListener() {
		   	@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
//				Intent intent=new Intent(LogInLength.this,LogInDate.class);
//				startActivity(intent);
				finish();
			}
	  });
	
		    
      shengao = (EditText)findViewById(R.id.EditText02);            
      tizhong = (EditText)findViewById(R.id.editText1);
           
	  Button xiayibu=(Button)findViewById(R.id.button1);     
      xiayibu.setOnClickListener(new OnClickListener() {
		    	@Override
	    		public void onClick(View v) 
	    		{	 
	    	   			// TODO Auto-generated method stub
		    		length=shengao.getText().toString().trim();
		    		weight=tizhong.getText().toString().trim();
	    		 	 if(length.equals("")||weight.equals("") )
	    	      		{flag=0;}  
	    		 	
	    		 	 else 
	    		 	    {
	    		 		 flag=1;
	    		 	     s = Float.parseFloat(shengao.getText().toString().trim()); 
	    		 	     z = Float.parseFloat(tizhong.getText().toString().trim());
	    		 	    
	    	 	          if(s<30||s>300){		
			   				 flag=2;
			   				initToast1();
		    	          } 
			   	          if(z<20||z>500){
			   	        	flag=2;
			   	        	initToast2();
	    		 	        }
	    		 	     }	 
	    		 	 if(flag==0)
	    		        {
	    	      		 Toast toast3=Toast.makeText(LogInLength.this, "身高或体重不能为空", Toast.LENGTH_SHORT);
   	    	    	     toast3.show();
	    	      		}  
	    		 	 
	    		 	 if(flag==1){ 	 
	    		 		app.setRegisterLgh(length);
	    		 	    app.setRegisterWgt(weight);
	    	   			Intent intent=new Intent(LogInLength.this,LogInHealth.class);
	    		    	startActivity(intent);
	    	   		 }  	     	
	    		}
	       });
	    }


	private void initToast1() {
		// TODO Auto-generated method stub
		{  
	        View toastRoot = getLayoutInflater().inflate(R.layout.toast, null);  
	        TextView message = (TextView) toastRoot.findViewById(R.id.message);  
	        message.setText("超出范围，请重新输入您的身高");  
	  
	        Toast toastStart = new Toast(this);  
	        toastStart.setGravity(Gravity.CENTER, 0, -150);  
	        toastStart.setDuration(Toast.LENGTH_SHORT);  
	        toastStart.setView(toastRoot);  
	        toastStart.show();  
	    }  
	}
	private void initToast2() {
		// TODO Auto-generated method stub
		{  
	        View toastRoot = getLayoutInflater().inflate(R.layout.toast, null);  
	        TextView message = (TextView) toastRoot.findViewById(R.id.message);  
	        message.setText("超出范围，请重新输入您的体重");  
	  
	        Toast toastStart = new Toast(this);  
	        toastStart.setGravity(Gravity.CENTER, 0, 160);  
	        toastStart.setDuration(Toast.LENGTH_SHORT);  
	        toastStart.setView(toastRoot);  
	        toastStart.show();  
	    }  
	}	
}

