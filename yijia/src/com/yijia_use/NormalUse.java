package com.yijia_use;

import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.download.doctorback.DoctorReply;
import com.download.doctorback.MyApplication;
import com.example.bluetooth.le.DeviceScanActivity;
import com.example.yijia.R;

public class NormalUse extends Activity {
	private TextView username;
	private long exitTime = 0;  
	private MyApplication app;

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		init();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);            
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION); 	
		}	
        setContentView(R.layout.normal_use);              
                
        init();       
    }
	
	//2秒连按2次Back键退出APP
	private void exit() {			 
		super.finish();	
		DeviceScanActivity.instance.finish();
	}
	   	
	@Override  
	public boolean onKeyDown(int keyCode, KeyEvent event) {  
	    if(keyCode == KeyEvent.KEYCODE_BACK  
	            && event.getAction() == KeyEvent.ACTION_DOWN){  
	        if((System.currentTimeMillis()-exitTime) > 2000){  
	            Toast.makeText(NormalUse.this, "再按一次返回键关闭程序", Toast.LENGTH_SHORT).show();  
	            exitTime = System.currentTimeMillis();  
	        } else {  
	        	exit();
			}  
	        return true;  
	    }  
	    return super.onKeyDown(keyCode, event);  
	}   
	
	private void init() {
		// TODO Auto-generated method stub
		app = (MyApplication) getApplication(); 
		
		username=(TextView) findViewById(R.id.username);
    	username.setText(app.getNAME()); 

		ImageButton docreply=(ImageButton)findViewById(R.id.doc_rep);
		docreply.setOnClickListener(listener);
		docreply.getBackground().setAlpha(0);
		
		ImageButton information=(ImageButton)findViewById(R.id.user_info);
		information.setOnClickListener(listener);
		information.getBackground().setAlpha(0);
		
		ImageButton waveform=(ImageButton)findViewById(R.id.wave_form);
		waveform.setOnClickListener(listener);
		waveform.getBackground().setAlpha(0);
		
		
		ImageButton shuomingshu=(ImageButton)findViewById(R.id.user_instructions);
		shuomingshu.setOnClickListener(listener);
		shuomingshu.getBackground().setAlpha(0);
    }

	private OnClickListener listener = new OnClickListener(){
    	public void onClick(View v){
    		switch(v.getId()){
    		case R.id.doc_rep:
    			 Intent intent01=new Intent(NormalUse.this,DoctorReply.class);
  			     startActivity(intent01);
  			   break;  
    		case R.id.user_info:
   			     Intent intent02=new Intent(NormalUse.this,UserInformation.class);
 			     startActivity(intent02);
 			    break;  
    		case R.id.wave_form:
   			     Intent intent03=new Intent(NormalUse.this,Waveform.class);
 			     startActivity(intent03);
 			    break;  
    		case R.id.user_instructions:
   			     Intent intent04=new Intent(NormalUse.this,Instruction.class);
 			     startActivity(intent04);
 			    break;    
 			 default:
 				 break;
    		}
    	}
    };
}
   	    
	    	
	











