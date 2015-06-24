package com.yijia_login;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.download.doctorback.MyApplication;
import com.example.yijia.R;

public class LogInSex extends BaseActivity {

private String sex;
private Button fanhui,xiayibu;
private RadioButton nan,nv;
private boolean click=false;
private MyApplication app;

//RadioButtonÊµÏÖµã»÷»»Í¼Æ¬±³¾°
@Override
public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            //Í¸Ã÷×´Ì¬À¸
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);            
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); //Òþ²ØÈí¼üÅÌ		
		}	
        setContentView(R.layout.activity_login_sex);
        
        app=(MyApplication)getApplication();
                
        fanhui=(Button)findViewById(R.id.button2);  
        fanhui.setOnClickListener(listener);
        
        nan=(RadioButton)findViewById(R.id.button3);
        nan.setOnClickListener(listener);
	    
        nv=(RadioButton)findViewById(R.id.button4);
        nv.setOnClickListener(listener);
        
        xiayibu=(Button)findViewById(R.id.button1);
        xiayibu.setOnClickListener(listener);     
        
        RadioGroup group = (RadioGroup)this.findViewById(R.id.radioGroup);
        group.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int radioButtonId = group.getCheckedRadioButtonId();
				if(radioButtonId==R.id.button3)									
					sex="ÄÐ";			
				else{
					sex="Å®";
				}
				click=true;
			}      	
        });
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
				 if(!click)      		 
     	    		 Toast.makeText(LogInSex.this, "ÇëÑ¡ÔñÐÔ±ð", Toast.LENGTH_SHORT).show();   	       
	    		 else{	
	    			 app.setRegisterSex(sex);
	    			 Intent intent=new Intent(LogInSex.this,LogInDate.class);	    			
	    			 startActivity(intent);
//	    			 finish();
	    			 }
				 break;			
			default:
				break;
			}
		}		
	};
}   
    
    
   
