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

public class One extends BaseActivity {
private EditText xuliehao;
private String XULIEHAO;
private MyApplication app;

	  @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
	            //Í¸Ã÷×´Ì¬À¸
	            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);            
	            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);	
			}	
	        setContentView(R.layout.one); 
	        app = (MyApplication) getApplication(); 
	        
	        Button yizhuce=(Button)findViewById(R.id.button1);
	        xuliehao=(EditText)findViewById(R.id.edit);
	        
	        yizhuce.setOnClickListener(new OnClickListener() {
		   	   @Override
			public void onClick(View v){
				// TODO Auto-generated method stub
		   		XULIEHAO=xuliehao.getText().toString();
		   		if(XULIEHAO.equals("")) {  
					Toast toast=Toast.makeText(One.this, "×¢²áÐòÁÐºÅ²»ÄÜÎª¿Õ", Toast.LENGTH_SHORT);
					toast.show();
		   		}else{
		   			app.setXULIEHAO(XULIEHAO);
		   			startActivity(new Intent(One.this,TestStart.class));
		   			finish();
		   		}		   				   		
			}
	     }); 	        
	  }
  }
