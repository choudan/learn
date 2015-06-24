package com.yijia_login;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.download.doctorback.MyApplication;
import com.example.yijia.R;
import com.http.tool.HttpUrl;
import com.http.tool.NetTool;

public class LogInDisease extends BaseActivity {	
	private int num=2000;
	private TextView text;
	private CharSequence temp;
	private int selectionStart;
	private int selectionEnd;
	private EditText jiwang;
	private String disease,test;
	
	private MyApplication app;

	private Handler handler = new Handler() {  
		public void handleMessage(Message msg){ //此方法在UI线程中运行       
			if (msg.what == TestStart.COMPLETED) {  
            	Intent intent=new Intent(LogInDisease.this,LogInSearch.class);		       			
    			startActivity(intent);	 
	    	}else{
	    		Toast.makeText(LogInDisease.this, "注册失败", Toast.LENGTH_SHORT).show();
	    	}
	    }
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);            
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);	
		}	
        setContentView(R.layout.activity_login_disease);
        app = (MyApplication) getApplication(); 
                
        jiwang = (EditText)findViewById(R.id.editText1);  
        jiwang.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2000)}); 
        	        
        text =(TextView)findViewById(R.id.TextView01);
        text.setText(0+"/"+num);
               
        
        Button fanhui=(Button)findViewById(R.id.button2);
	    fanhui.setOnClickListener(new OnClickListener() {   	
		    @Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
		    }
		});
		    
	    Button xiayibu=(Button)findViewById(R.id.button1);
        xiayibu.setOnClickListener(new OnClickListener() {
    	 @Override
    		public void onClick(View v){
    			// TODO Auto-generated method stub
	    		disease=jiwang.getText().toString().trim();
	    		if(disease.equals(""))
	    			Toast.makeText(LogInDisease.this, "疾病情况不能为空", Toast.LENGTH_SHORT).show();	     	       
	       		else{        			
	       			new Thread(runnableRegister).start();
	       		}
	    	}
	    });
        
	    jiwang.addTextChangedListener(new TextWatcher() {    
	    	 public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	       	  }

	    	 public void onTextChanged(CharSequence s, int start, int before, int count) {
	       	   temp = s;
	       	  }
	    	 @Override
	    	 public void afterTextChanged(Editable s) {
	       		// TODO Auto-generated method stub
	       		 int number = s.length();
	       		   text.setText(number+"/"+num);
	       		   selectionStart = jiwang.getSelectionStart();
	       		   selectionEnd = jiwang.getSelectionEnd();
	       		 if (temp.length() > num){
	       		    s.delete(selectionStart - 1, selectionEnd);
	       		    int tempSelection = selectionEnd;
	       		    jiwang.setText(s);
	       		    jiwang.setSelection(tempSelection);//设置光标在最后
	       		 }
	       	 }
	       });  
	 	}
	 
	 	Runnable runnableRegister = new Runnable(){
	  		private String url =HttpUrl.IP+HttpUrl.DIR+"save_register_info";		  			  			  	
	  		private JSONObject subjson;
	  		private int statusCode;
	  		public void run()//复写Thread类中的run(),此方法包含线程的处理，在UI线程中调用，来启动线程。
	  		{	
	  			Map<String, String> map = new HashMap<String, String>();
	  			map.put("username", app.getXULIEHAO());    //测试专用
	  			map.put("true_name", app.getRegisterName());
	  			map.put("sex",app.getRegisterSex());
	  			map.put("birthday",app.getRegisterBirth());
	  			map.put("height",app.getRegisterLgh());
	  			map.put("weight",app.getRegisterWgt());
	  			map.put("past_health",app.getRegisterHealth());
	  			map.put("disease", disease);
	  			try {
  					test = NetTool.sendGetRequest(url, map, "utf-8");
  					statusCode=new JSONObject(test).getInt("statusCode");
  					subjson=new JSONObject(test).getJSONObject("result");
  					app.setID(subjson.getString("ID"));
	  			} 	  			
	  			catch (Exception e) {
	  				e.printStackTrace();
	  			}			
	  			 /*
	 	  		 * 测试专用	
	 	  		 */
 	  			 Message msg = new Message(); 
 	  			 if(statusCode==-1)
 	  				 msg.what = TestStart.COMPLETED; 
 	  			 else{
 	  				 msg.what = TestStart.UNCOMPLETED;   				 
 	  			 }
 	             handler.sendMessage(msg); 
 	             Log.e("》》》》》》》》》》》》》》", msg.what+"  "+statusCode+"  "+test);
	  		}
	 	};
	}   