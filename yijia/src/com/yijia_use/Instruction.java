package com.yijia_use;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.yijia.R;
import com.yijia_login.LogInDate;
import com.yijia_login.LogInSex;

public class Instruction extends Activity {

	   @Override
	    protected void onCreate(Bundle savedInstanceState) 
	    {
	        super.onCreate(savedInstanceState);
	        if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
	            //Í¸Ã÷×´Ì¬À¸
	            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);            
	            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); //Òþ²ØÈí¼üÅÌ		
			}	
	        setContentView(R.layout.instruction);
	    
	        Button fanhui=(Button)findViewById(R.id.button2);      
		    fanhui.setOnClickListener(new OnClickListener() {
		   	   @Override
				public void onClick(View v) 
				{
					// TODO Auto-generated method stub
					finish();
				}
	        });
	    }
}
