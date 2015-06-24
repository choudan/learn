package com.yijia_login;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.download.doctorback.MyApplication;
import com.example.yijia.R;

public class LogInName extends BaseActivity {

	private EditText register_username;
	private String content="������������ʵ����������������������������ʾ�;���������,�����������޷����ġ�";
	private String username;
	public  final static String LIST_KEY = "login";  
	private MyApplication app;

    @Override
   public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            //͸��״̬��
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);            
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); //���������		
		}	
        setContentView(R.layout.activity_login_name);
        app=(MyApplication)getApplication();
        
        TextView text=(TextView)findViewById(R.id.TextView01);
        Button fanhui=(Button)findViewById(R.id.button2);
        SpannableStringBuilder style=new SpannableStringBuilder(content);   
        style.setSpan(new ForegroundColorSpan(Color.RED),29,40,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);     //����ָ��λ�����ֵ���ɫ
        text.setText(style); 
   	    fanhui.setOnClickListener(new OnClickListener() {
   	   	   @Override
	   		public void onClick(View v){
	   			// TODO Auto-generated method stub
	   	   		finish();
	   		}
        });   	     	
   	     register_username = (EditText)findViewById(R.id.editText1);
   	     
   	     Button xiayibu=(Button)findViewById(R.id.button1);
   	     xiayibu.setOnClickListener(new OnClickListener(){
	      	 @Override  	 
	   		public void onClick(View v) {
	   			// TODO Auto-generated method stub
	      		username=register_username.getText().toString().trim();
	      		if(username.equals("")){
	    	    	Toast toast=Toast.makeText(LogInName.this, "�û�������Ϊ��", Toast.LENGTH_SHORT);
	    	    	toast.show();
	    	       }
	      		else{ 
	      			app.setRegisterName(username);
	      			Intent intent=new Intent();
	      			intent.setClass(LogInName.this,LogInSex.class);
	   			    startActivity(intent);
	   		    }
	   		}
   		});
   }	  
}
   	    
	    	
	











