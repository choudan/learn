package com.yijia_login;


import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

import com.example.yijia.R;

public class LogIn extends BaseActivity {
    @Override
	public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            //Í¸Ã÷×´Ì¬À¸
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);            
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION); 		
		}	
        setContentView(R.layout.activity_login_begin);
                                          
        Button kaishi=(Button)findViewById(R.id.button3);
   	    kaishi.setOnClickListener(new OnClickListener() 
	    {
   	      @Override
		  public void onClick(View v) {
			// TODO Auto-generated method stub  		  
   			   Intent intent=new Intent(LogIn.this,LogInName.class);
			   startActivity(intent);		  
		 }
	  });
   } 
}
