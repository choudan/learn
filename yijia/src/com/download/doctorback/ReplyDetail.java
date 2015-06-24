package com.download.doctorback;

import android.app.Activity;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.yijia.R;

public class ReplyDetail extends Activity{

	private TextView mTextWords,mTextPrescription,mTextDate;
	private Bundle bundestring;
	private String doctor_back,doctor_xuewei,doctor_date;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);            
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}	
        setContentView(R.layout.reply_detail);
        
        bundestring=this.getIntent().getExtras();                  
        initView();
    }
     
    public void initView()
    {	
    	TextView mTextView1 = (TextView) findViewById(R.id.doctor_reply);
    	TextView mTextView2 = (TextView) findViewById(R.id.jingluo_reply);
    	mTextWords = (TextView) findViewById(R.id.repil_detail);
    	mTextPrescription = (TextView) findViewById(R.id.prescription_detail);
    	mTextDate = (TextView) findViewById(R.id.date);
    	
        mTextView1.setText("医生反馈");
    	mTextView2.setText("经络处方");
    	
    	doctor_back=bundestring.getString("doctor_back");
        doctor_xuewei=bundestring.getString("doctor_xuewei");
        doctor_date=bundestring.getString("doctor_date");         
        
    	mTextDate.setText(doctor_date);
    	mTextWords.setText(doctor_back);
        
    	String[]xuewei=doctor_xuewei.split(",");
   	    StringBuffer buf_xuewei=new StringBuffer();

    	for(int i=0;i<xuewei.length;i++){   		 		 
    		buf_xuewei.append(xuewei[i]+"\n");            	     	   	        	       	             	    
    	}
    	mTextPrescription.setText(buf_xuewei.toString());
    }     	 
}