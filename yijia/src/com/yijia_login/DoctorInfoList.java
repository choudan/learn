package com.yijia_login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.download.doctorback.MyApplication;
import com.example.yijia.R;
import com.http.tool.HttpUrl;
import com.http.tool.NetTool;

public class DoctorInfoList extends BaseActivity {
	/* bundle的getString(),key值须初始化
	 * @param args
	 * luojing 
	 */
	 private ListView mListView;
	 private DoctorInfoViewAdapter mAdapter;
	 private List<DoctorInfoEntity> mDataArrays;
	 int flag=1;
	 public static String NAME="name",DOCTODID="doctid";
	 private MyApplication app;
	 private ProgressDialog progressDialog = null;

	 protected void onCreate(Bundle savedInstanceState) 
	 {
		 super.onCreate(savedInstanceState);
		 if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);            
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION); //隐藏软键盘		
		 }	
	     setContentView(R.layout.activity_doctorlist); 	
	          
	     new Thread(runnableSearch).start();
 		 
	     initView();
	     
//	     initData();	    	     
	 }
	
	 protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		return new AlertDialog.Builder(DoctorInfoList.this).setTitle("正在加载搜索结果...").setMessage("稍等啦").create();
	 }
	 
	  
	private void initView(){
		// TODO Auto-generated method stub
		app = (MyApplication) getApplication();
		Log.e("++++", app.getRegisterSearchDoc());
		
		mListView = (ListView) findViewById(R.id.doctor_listview);		
		mListView.setOnItemClickListener(mListViewListener);
       
		progressDialog=ProgressDialog.show(this, "正在加载搜索结果...", "稍等啦");

		mDataArrays = new ArrayList<DoctorInfoEntity>();
		
		Button fanhui=(Button)findViewById(R.id.button2);	        
		fanhui.setOnClickListener(new OnClickListener() {
		   	   @Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				finish();
			}
		});		
	}	
	/*
	 * 加载搜索数据
	 */
	public void initData(){
		mAdapter=new DoctorInfoViewAdapter(this, mDataArrays);	
		mListView.setAdapter(mAdapter);
	}
	
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			progressDialog.dismiss();
			if(msg.what==TestStart.COMPLETED){
				initData();
			}else if(msg.what==TestStart.UNCOMPLETED){
				Toast.makeText(getApplicationContext(), "无此医生，请重新输入医生姓名", Toast.LENGTH_SHORT).show();
				finish();
			}				
		}		
	};
	
	Runnable runnableSearch = new Runnable(){
//  		private String url ="http://182.92.224.124:8086/yijia/zm/search_doctor";
  		private String url =HttpUrl.IP+HttpUrl.DIR+"search_doctor";
  		private int statusCode;
  		private String doctor_string;
  		private JSONArray array;
 		public void run(){//复写Thread类中的run(),此方法包含线程的处理，在UI线程中调用，来启动线程。		  			
 			Map<String, String> map = new HashMap<String, String>();  			
	  		map.put("doctor_name",app.getRegisterSearchDoc());  			
  			try {
  				 doctor_string = NetTool.sendGetRequest(url, map, "utf-8");	
  				 statusCode=new JSONObject(doctor_string).getInt("statusCode");
  				 array=new JSONObject(doctor_string).getJSONArray("result");
  				 for(int i=0;i<array.length();i++){
					 DoctorInfoEntity entity=new DoctorInfoEntity();
					 entity.setName(array.getJSONObject(i).getString("true_name"));
					 entity.setDepartment(array.getJSONObject(i).getString("dept"));
					 entity.setHospital(array.getJSONObject(i).getString("hospital"));
					 entity.setDoctor_id(array.getJSONObject(i).getInt("ID"));
					 mDataArrays.add(entity);
  				 }			 
	  		}catch (Exception e) {
	  				e.printStackTrace();
	  		} 
  			Message msg=new Message();
  			if(statusCode==-1)
  				msg.what=TestStart.COMPLETED;
  			else{
  				msg.what=TestStart.UNCOMPLETED;
  			}
  			handler.sendMessage(msg);
	  	}
	 };
	
	//实现点击item才能看到医生信息
	OnItemClickListener mListViewListener = new OnItemClickListener() {
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) { 
		String name=mDataArrays.get(position).getName();
		String hos=mDataArrays.get(position).getHospital();
		String dep=mDataArrays.get(position).getDepartment();
		int ID=mDataArrays.get(position).getDoctor_id();
		
		Intent intent = new Intent();
		Bundle bundle =new Bundle();
		bundle.putString(NAME,name+"\n"+hos+dep);
		bundle.putInt(DOCTODID, ID);
		intent.putExtras(bundle);
    	intent.setClass(DoctorInfoList.this,LogInRelationTime.class);    	
		startActivity(intent);
     }
  };

//	@Override
//	protected void onDestroy() {
//		// TODO Auto-generated method stub
//		super.onDestroy();
//		progressDialog.dismiss();
//	}  
}
