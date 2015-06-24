package com.yijia_login;


import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.download.doctorback.MyApplication;
import com.example.yijia.R;

public class LogInDate extends BaseActivity {
 private int mYear, mMonth, mDay;
 EditText txtDate;  
 private String date;
 private MyApplication app;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);            
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); //隐藏软键盘		
		}	
        setContentView(R.layout.activity_login_date);
        app=(MyApplication)getApplication();  
        
        Button fanhui=(Button)findViewById(R.id.button2);
        
	    fanhui.setOnClickListener(new OnClickListener() {
	   	   @Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				finish();
			}
	    });
	     
  	  txtDate = (EditText)findViewById(R.id.editText2);
  	  
  	  Button xiayibu=(Button)findViewById(R.id.button1);
	  xiayibu.setOnClickListener(new OnClickListener() 
	  {
   	    @Override 
		public void onClick(View v) 
		{
			// TODO Auto-generated method stub
   	    date=txtDate.getText().toString().trim();
   		if(date.equals("")){
 	    	Toast toast=Toast.makeText(LogInDate.this, "出生日期不能为空", Toast.LENGTH_SHORT);
 	    	toast.show();
 	    }
   		else { 
   			  app.setRegisterBirth(date);
   			  Intent intent=new Intent(LogInDate.this,LogInLength.class);
			  startActivity(intent);
		    }
		}
		}) ;
  
	    txtDate.setOnClickListener(new OnClickListener(){
        @Override
        public void onClick(View arg0) 
        {
        	InputMethodManager inputMethodManager = (InputMethodManager) 
            getSystemService(Context.INPUT_METHOD_SERVICE); 
        	inputMethodManager.hideSoftInputFromWindow(LogInDate.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        	showDialog(0);
        }
        });
    }
   
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {  
    	@Override
         public void onDateSet( DatePicker view, int year, int monthOfYear,
         int dayOfMonth){
                mYear = year;
                String mm;  
                String dd;
                
                mMonth = monthOfYear + 1; 
                mm = String.valueOf(mMonth);
                if (mm.length() < 2) mm = "0" + mm;
                
                mDay = dayOfMonth;  
                dd = String.valueOf(mDay);
                if (dd.length() < 2) dd = "0" + dd;
              
                txtDate.setText(String.valueOf(mYear) + "/" + mm + "/" + dd);
    	  }             
        };
        
      protected Dialog onCreateDialog(int id) {
      final Calendar c = Calendar.getInstance(); 
         mYear  = c.get(Calendar.YEAR); 
         mMonth  = c.get(Calendar.MONTH); 
         mDay  = c.get(Calendar.DAY_OF_MONTH); 
         
      switch (id) {     
      case 0:  
          return new DatePickerDialog(this,  
                  mDateSetListener,  
                  mYear, mMonth, mDay);  
      }  
      return null;  
  }  
}


