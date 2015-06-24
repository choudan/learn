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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.download.doctorback.MyApplication;
import com.example.yijia.R;
import com.http.tool.HttpUrl;
import com.http.tool.NetTool;

public class LogInRelationTime extends BaseActivity {
	private String doctor_relationtime,result,relationtime_start,relationtime_end;
	private TextView text_name,text_time;
	private Button fanhui,guanlian;
	private String name;
	public  int DOCTORID;
	private MyApplication app;
		private Handler handler = new Handler() {  
		    public void handleMessage(Message msg){ //此方法在UI线程中运行    
		    	switch(msg.what){
		    	case TestStart.COMPLETED:
		    		text_time.setText("服务开始时间"+"\n"+relationtime_start+"\n"+"\n"+"服务结束时间"+"\n"+relationtime_end);
		    		break;
		    	case TestStart.UNCOMPLETED:
		    		break;
		    	case TestStart.FINISH:
		    		finish();
		    		break;
		    	case TestStart.UNFINISH:
		    		break;
		    	default:
		    		break;
		    	}
		    }
		};  
	
		 @Override
		 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
	            //透明状态栏
	            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);            
	            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION); 
			}	
	        setContentView(R.layout.activity_login_relation_time);
	        
			DOCTORID=this.getIntent().getExtras().getInt(DoctorInfoList.DOCTODID);
			app = (MyApplication) getApplication(); 
			name=this.getIntent().getExtras().getString(DoctorInfoList.NAME);//bundle的getString()只能用一次，key值须初始化
	
	        initView();	 
	        initData();
	        Log.e("卧槽", name);
		} 
		 
		private void initData(){			
	        text_name.setText(name);	
			new Thread(runnableSearch).start();		 
		}
		 
		private void initView() {
			// TODO Auto-generated method stub
			text_name=(TextView)findViewById(R.id.name_detail);
		    text_time=(TextView)findViewById(R.id.time_detail);
		    
		    fanhui=(Button)findViewById(R.id.button2);
		    fanhui.setOnClickListener(listener);
		    
		    guanlian=(Button)findViewById(R.id.button1);
		    guanlian.setOnClickListener(listener);	    
		}
		
		private OnClickListener listener=new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch(v.getId()){
				case R.id.button2:
					new Thread(runnableCncel).start(); 
					finish();
					break;
				case R.id.button1:
					startActivity(new Intent(LogInRelationTime.this,LogInFinish.class));
					finish();
					break;
				default:
					break;
				}
			}	
		};
			
		Runnable runnableSearch = new Runnable(){
//	  		private String url ="http://182.92.224.124:8086/yijia/zm/doctor_relevance";		  		
	  		private String url =HttpUrl.IP+HttpUrl.DIR+"doctor_relevance";		  		
	  		private JSONObject dataJson,subDataJson;
	  		private int statusCode;
	  		public void run(){//复写Thread类中的run(),此方法包含线程的处理，在UI线程中调用，来启动线程。	
	  			Map<String, String> map = new HashMap<String, String>();
	  			
	  			map.put("doctor_id",String.valueOf(DOCTORID)); //服务器
	  			map.put("user_id",app.getID()); 
	  			try {
	  				 doctor_relationtime = NetTool.sendGetRequest(url, map, "utf-8");	  				
	  				 dataJson = new JSONObject(doctor_relationtime);
	  				 statusCode=dataJson.getInt("statusCode");//Toast据此提示
	  				 subDataJson=dataJson.getJSONObject("result");//到此解开第一层json
	  				 
	  				 relationtime_start=subDataJson.getString("relationtime_start");//数据库有问题
	  				 relationtime_end=subDataJson.getString("relationtime_end");
	  			} 	  			
	  			catch (Exception e) {
	  				e.printStackTrace();
	  			} 
	  			 Message msg = new Message();
	  			 if(statusCode==-1)
	  				 msg.what = TestStart.COMPLETED; 
	  			 else{
	  				 msg.what = TestStart.UNCOMPLETED;
	  			 }
	             handler.sendMessage(msg); 
			}
		};
		
		Runnable runnableCncel = new Runnable(){
//	  		private String url ="http://182.92.224.124:8086/yijia/zm/relevance_cancel";	
	  		private String url =HttpUrl.IP+HttpUrl.DIR+"relevance_cancel";	
	  		private int statusCode;
	  		public void run(){//复写Thread类中的run(),此方法包含线程的处理，在UI线程中调用，来启动线程。
	  			
	  			Map<String, String> map = new HashMap<String, String>();
	  			
	  			map.put("doctor_id",String.valueOf(DOCTORID)); 
	  			map.put("user_id",app.getID()); 
	  			try {
	  				result = NetTool.sendGetRequest(url, map, "utf-8");	 
	  				statusCode=new JSONObject(result).getInt("statusCode");
	  			} 	  			
	  			catch (Exception e) {
	  				e.printStackTrace();
	  			}
	  			 Message msg = new Message();
	  			 if(statusCode==-1)
	  				 msg.what = TestStart.FINISH; 
	  			 else{
	  				 msg.what = TestStart.UNFINISH;
	  			 }
	             handler.sendMessage(msg); 
	  		}
		};
	}