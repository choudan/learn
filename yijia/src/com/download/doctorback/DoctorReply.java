package com.download.doctorback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yijia.R;
import com.http.tool.HttpUrl;
import com.http.tool.NetTool;
import com.yijia_login.TestStart;
import com.yijia_use.SelectPic;

public class DoctorReply extends Activity {
	/*
	 * @param args
	 * @luojing
	 * list也能直接全部输出，只不过输出会自动加上[],如[list.get(0),list.get(1),list.get(2)]
	 */
	 private Button mBtnSend;
	 private EditText mEditTextContent;
	 private ListView mListView;
	 private  ListItemAdapter mAdapter;
	 private ArrayList<ItemEntity> mDataArrays=new ArrayList<ItemEntity>();
	 private String retStrDoctorReply,back_user_picpath,content;

	 private int page=1;
	 
	 private ProgressDialog progressDialog = null;
	 public static DoctorReply instance=null;//声明DoctorReply的引用，this初始化，即可在别的Activity中调用

//   RefreshableView此框架太过繁琐，且不稳定。  support.v4(19.1)之后提供了SwipeRefreshLayout控件，简单、稳定、实用
//	 private RefreshableView refreshableView;
	 private SwipeRefreshLayout mSwipeRefreshLayout;
	 private MyApplication app;
	 
	//此方法在UI线程中运行
	 private Handler handler = new Handler() {  
	    public void handleMessage(Message msg) {  
	    	progressDialog.dismiss();	
//	    	removeDialog(0);
	    	switch(msg.what){
	    	case TestStart.COMPLETED:		       		            		        			        	
	    		mAdapter.notifyDataSetChanged();
	        	Toast toast=Toast.makeText(DoctorReply.this, "发言成功", Toast.LENGTH_SHORT);
  	    	    toast.show();	 
  	    	    break;
	    	case TestStart.UNCOMPLETED:	
	        	initData();
	            break;
	    	case TestStart.FINISH: 	    		
	    		mAdapter.notifyDataSetChanged();
		        setListViewPos(0);//刷新后，ListView显示停留在第一条item上
	    		if(page==1){
	    			Toast.makeText(getApplicationContext(), ""+mDataArrays.size(), Toast.LENGTH_SHORT).show();
	    		}else{
	    			mSwipeRefreshLayout.setRefreshing(false);//刷新成功后，关闭    			
	    		}
		        break;
	    	case TestStart.UNFINISH:	    		
	    		Toast.makeText(DoctorReply.this, "下载失败", Toast.LENGTH_SHORT).show();
	    		mSwipeRefreshLayout.setRefreshing(false);//刷新成功后，关闭    			
	    		finish();
				break;	
	    	case TestStart.FAIL:
	    		Toast.makeText(DoctorReply.this, "暂无医生反馈", Toast.LENGTH_SHORT).show();
	    		mSwipeRefreshLayout.setRefreshing(false);//刷新成功后，关闭    			
	    		break;
			default:
				Toast toast2=Toast.makeText(DoctorReply.this, "联网失败", Toast.LENGTH_SHORT);
				toast2.show();	
				break;
	    	}
	    }
	};  
	 
	 protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
	        //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);            
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION); 	
		 }	
//		 getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		 setContentView(R.layout.doctor_reply); 
	     app=(MyApplication) this.getApplication();	     	     
	     
	     initView();
	     
	     initData();
	     
		 mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener(){
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				mSwipeRefreshLayout.setRefreshing(true);
				
				page++;	
				
				new Thread(runnableDownloadReply).start();	
		
				mSwipeRefreshLayout.setColorScheme(android.R.color.holo_blue_light, android.R.color.background_light, android.R.color.holo_blue_light, android.R.color.background_light);				
			}			 
		 });		 		 	 
	 }
	
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		return new AlertDialog.Builder(DoctorReply.this).setTitle("正在加载聊天记录...").setMessage("稍等啦").create();
	}
	
	public void initData(){	
		progressDialog=ProgressDialog.show(this, "正在加载聊天记录...", "稍等啦");
//		onCreateDialog(0).show();
	    new Thread(runnableDownloadReply).start();
	    mAdapter = new ListItemAdapter(DoctorReply.this, mDataArrays);
		mListView.setAdapter(mAdapter);	
    }	

	 
	private void initView(){
		// TODO Auto-generated method stub
		mListView = (ListView) findViewById(R.id.listview); 
		mListView.setOnItemClickListener(mListViewListener);	
		
		mBtnSend = (Button) findViewById(R.id.btn_add);
		mBtnSend.setOnClickListener(listener);
		
		mEditTextContent = (EditText) findViewById(R.id.et_message);
		mEditTextContent.setOnEditorActionListener(mWriteListener);  
		mEditTextContent.setCursorVisible(false);
		mEditTextContent.setOnClickListener(listener);
		
		mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshable_view);
		
		instance=this;//初始化常量，在B中关闭A
	}		    

	private void send(){
		content = mEditTextContent.getText().toString().trim();
		if (content.length() > 0){
			ItemEntity entity = new ItemEntity();

			entity.setDate(getDate());
			entity.setType(0);
			entity.setUserWords(content);
			entity.setImageUrls(null);
			
			mDataArrays.add(entity);
			
			mAdapter.notifyDataSetChanged();
			
			mEditTextContent.setText("");
			
			mListView.setSelection(mListView.getCount()-1);
			
			new Thread(upWords).start();
			
		}else{
			Toast toast=Toast.makeText(DoctorReply.this, "发送内容不能为空", Toast.LENGTH_SHORT);
			toast.show();
		}
	}
	
    private String getDate() {   
//    	HH：返回的是24小时制的时间，hh：返回的是12小时制的时间    　　　 
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");       
        String date=sDateFormat.format(new java.util.Date()); 
        
//    	获取系统时间的时制
//      ContentResolver cv = this.getContentResolver();
//      String strTimeFormat = android.provider.Settings.System.getString(cv,
//                               android.provider.Settings.System.TIME_12_24);    
        return date;
    }
    
	private OnClickListener listener = new OnClickListener(){	 	 
	@Override
	   public void onClick(View v){
			switch(v.getId()){
			case R.id.btn_add:
				 Intent intent = new Intent(DoctorReply.this,SelectPic.class);  
		         startActivity(intent); 
		         break;	
			case R.id.et_message:
	 	   		 mEditTextContent.setCursorVisible(true); 
	 	   		 break;
	 	   	default:
	 	   		break;
			}
	    }
    };
	
	private TextView.OnEditorActionListener mWriteListener = new TextView.OnEditorActionListener(){		
		public boolean onEditorAction(TextView view, int actionId,KeyEvent event){
			if (actionId == EditorInfo.IME_ACTION_SEND)				
				send();						
			return true;
		}
	};
	
	//ListView回到第一个item
	private void setListViewPos(int pos) {  
	    if (android.os.Build.VERSION.SDK_INT >= 8) {  
	        mListView.smoothScrollToPosition(pos);  
	    } else {  
	        mListView.setSelection(pos);  
	    }  
	}  
	
	
	// 下载反馈     
    Runnable runnableDownloadReply = new Runnable(){
 		private String url =HttpUrl.IP+HttpUrl.DIR+"com_records";				       
// 		private String url ="http://182.92.224.124:8086/yijia/zm/com_records";				       
        private JSONObject jsonReply,subjsonReply,thjsonReply;       
        private ArrayList<ItemEntity> moreData;
        private JSONArray array;
        private int statusCode;
        public void run()
 		{						 			
 			Map<String, String> map = new HashMap<String, String>();
 			map.put("userid", app.getID());
			map.put("page", String.valueOf(page));	
 			try {
 				 retStrDoctorReply = NetTool.sendGetRequest(url, map, "utf-8"); 	
 				 
 				 jsonReply=new JSONObject(retStrDoctorReply); 				 
 				 statusCode=jsonReply.getInt("statusCode");//Toast据此提示
 				 
 				 subjsonReply=jsonReply.getJSONObject("result");//第一层
 				 array=subjsonReply.getJSONArray("list");
 				
 				 if(page==1){					 
 					 for(int i=0;i<array.length();i++){
 						 thjsonReply=array.getJSONObject(i);
 						 ItemEntity entity=new ItemEntity();
 						 entity.setType(thjsonReply.getInt("type"));
 						 entity.setUserWords(thjsonReply.getString("user_words"));
 						 entity.setDate(thjsonReply.getString("date"));
 						 entity.setImageUrls(parseJsonPicpath(thjsonReply.getString("user_picpath")));//处理				 						
 						 entity.setDoc_time(thjsonReply.getString("doc_time"));
 						 entity.setDocXuewei(thjsonReply.getString("doc_xuewei"));
 						 entity.setDocBack(thjsonReply.getString("doc_back"));	
 						 mDataArrays.add(entity);
 					 }
 					Collections.reverse(mDataArrays);//JSON产生的根源数组,反转,无返回值									 
 				 }else{
 					 moreData=new ArrayList<ItemEntity>();
 					 for(int i=0;i<array.length();i++){
 						 thjsonReply=array.getJSONObject(i);
 						 ItemEntity entity=new ItemEntity();
 						 entity.setType(thjsonReply.getInt("type"));
 						 entity.setUserWords(thjsonReply.getString("user_words"));
 						 entity.setDate(thjsonReply.getString("date"));
 						 entity.setImageUrls(parseJsonPicpath(thjsonReply.getString("user_picpath")));//处理				 						
 						 entity.setDoc_time(thjsonReply.getString("doc_time"));
 						 entity.setDocXuewei(thjsonReply.getString("doc_xuewei"));
 						 entity.setDocBack(thjsonReply.getString("doc_back"));
 						 moreData.add(entity);
 					 }
 					Collections.reverse(moreData);
 					mDataArrays.addAll(0, moreData);				 
 				 }

//				mAdapter.notifyDataSetChanged();
//		        setListViewPos(0);  					 				  				 
 			 	Log.e("丑旦", "mDataArrays的长度"+mDataArrays.size()+"   "+mDataArrays.get(0).getType());
 				 
 			} catch (Exception e) {
 				 e.printStackTrace();
 			} 			
 			Message msg = new Message(); 
 			if(statusCode==-1){
 				if(mDataArrays.size()>0)
 					msg.what=TestStart.FINISH;
 				else{
 					msg.what=TestStart.FAIL;					
 				}
 			}
 			else{
 				msg.what=TestStart.UNFINISH;  				 
 			} 			 
 			handler.sendMessage(msg); 
 		}				
 	}; 
 	
 	
 	 //新注册用户提交发言（文字） 	 
 	 Runnable upWords = new Runnable(){
  		private String url =HttpUrl.IP+HttpUrl.DIR+"user_back";				       
//  		private String url ="http://182.92.224.124:8086/yijia/zm/user_back";				       
        private JSONObject jsonUpload;
        private int statusCode;
  		public void run()
  		{						 			
  			Map<String, String> map = new HashMap<String, String>();
  			map.put("userid", app.getID());  //测试时，注意修改。
 			map.put("doctorid",app.getDoc_ID());
 			map.put("user_words",content);
 			map.put("user_picpath","");			
 			
  			try {
  				 back_user_picpath = NetTool.sendGetRequest(url, map, "utf-8"); 								
  				 jsonUpload=new JSONObject(back_user_picpath);	
  				 statusCode=jsonUpload.getInt("statusCode");
  			} catch (Exception e) {
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
 		 	 	
 	//解析图片回显路径
 	private ArrayList<String> parseJsonPicpath (String s) {	
 		String[] str;
		ArrayList<String> list_path = new ArrayList<String>();  
		if(s.contains(",")){
			str=s.split(",");
			for(String one_path:str){
				list_path.add(one_path);			
			}
		}else if(!s.equals("")){			
			 list_path.add(s);
			}else{
				list_path=null;
			}
		return list_path;
 	}	
  		
	//实现点击日期才能看到医生经络处方
	//listview 越界问题
	OnItemClickListener mListViewListener = new OnItemClickListener() {
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
	{  
    	if(mDataArrays.get(position).getType()==1)
    	{	    		
    		Intent intent = new Intent(DoctorReply.this,ReplyDetail.class);
    		Bundle bunde=new Bundle();
    		bunde.putString("doctor_back",mDataArrays.get(position).getDocBack());   		
    		bunde.putString("doctor_xuewei",mDataArrays.get(position).getDocXuewei());   		
    		bunde.putString("doctor_date",mDataArrays.get(position).getDoc_time());   		

        	intent.putExtras(bunde);
    		startActivity(intent);	
    	}else{
    		mEditTextContent.setCursorVisible(false);
    	}    		
     }
  };	
}	
	