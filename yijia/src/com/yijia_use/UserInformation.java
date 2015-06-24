package com.yijia_use;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.download.doctorback.MyApplication;
import com.example.yijia.R;
import com.http.tool.HttpUrl;
import com.http.tool.NetTool;
import com.yijia_login.TestStart;

public class UserInformation extends Activity {
	private boolean ipEnable=false;
	private Button wancheng;
	private EditText text_shengao, text_tizhong,text_jiwang,text_jibing;				  
	private TextView weight_index;
	private ImageButton back,edit;
	private TextView textname,textsex,textbirthday,textindex;  //测试专用
	private ProgressDialog progressDialog = null;
	private ScrollView mScrollView0,mScrollView1,mScrollView2;
	private RelativeLayout relativeLayout1;
	
	private String num="";
	private String retStrEdit = "";
	private String JSONInfo;
	private String name="";
	private String birthday="";
	private String sex="";
	private String weight="";
	private String length="";
	private String health="";
	private String disease="";
	private MyApplication app;
	/**
	 * 安卓4.0(KitKat)版本以后,主线程中不再能请求网络访问   （用Thread为什么不行，调用全局变量mThread为什么也不行）
	 */
	private Handler handler = new Handler() {  
        public void handleMessage(Message msg) //此方法在UI线程中运行
        {  
        	progressDialog.dismiss();
//        	removeDialog(3); 
            switch (msg.what) { 
            case TestStart.COMPLETED:
    	    	textname.setText(name);
    			textbirthday.setText(birthday);
    			textsex.setText(sex);
    			text_shengao.setText(length);
    	        text_tizhong.setText(weight);
    	        text_jiwang.setText(health);
    	        text_jibing.setText(disease);
    	        textindex.setText(weightIndex(text_tizhong,text_shengao));
    	        break;
            case TestStart.UNCOMPLETED:
            	Toast.makeText(UserInformation.this, "网络请求错误",Toast.LENGTH_SHORT).show();
            	finish();
            	break;
            case TestStart.SUCCESS:
            	relativeLayout1.setVisibility(View.GONE);
            	num=weightIndex(text_tizhong,text_shengao);          		
          		weight_index.setText(num);
         			 	        
          		uneditable(text_shengao);
          		uneditable(text_tizhong);
          		uneditable(text_jiwang);
          		uneditable(text_jibing);
            	Toast.makeText(UserInformation.this, "保存成功",Toast.LENGTH_SHORT).show();
            	break;
            case TestStart.FAIL:
            	Toast.makeText(UserInformation.this, "网络请求错误",Toast.LENGTH_SHORT).show();
            	finish();
            	break;
            case TestStart.UNFINISH:
            	Toast.makeText(getApplicationContext(), "身高或体重超出正常范围", Toast.LENGTH_SHORT).show();
            case TestStart.DISMISS:
            	Toast.makeText(getApplicationContext(), "服务器出差错啦", Toast.LENGTH_SHORT).show();          	
            default:
            	break;
            }           
        }  
    };  
	
	@Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);            
		}	
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); //隐藏软键盘		
        setContentView(R.layout.user_information);
        
        app=(MyApplication) this.getApplication();
        progressDialog=ProgressDialog.show(this, "正在加载个人信息...", "稍等啦");
//        onCreateDialog(3).show();
        new Thread(runnableInfo).start();  
        init();
    }
	
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		return new AlertDialog.Builder(UserInformation.this).setTitle("正在加载搜索结果...").setMessage("稍等啦").create();
	 }
	 	
	private void init(){
		relativeLayout1=(RelativeLayout)findViewById(R.id.relativeLayout1);
		
		back=(ImageButton)findViewById(R.id.btn_back);
		back.setOnClickListener(listener);
		
		edit=(ImageButton)findViewById(R.id.btn_edit);
		edit.setOnClickListener(listener);
		
		
		wancheng=(Button)findViewById(R.id.btn_wancheng);
		wancheng.setOnClickListener(listener);
		
		weight_index=(TextView)findViewById(R.id.weight_index);	 
		textname=(TextView) findViewById(R.id.name);
		textsex=(TextView) findViewById(R.id.TextView01);
		textbirthday=(TextView) findViewById(R.id.TextView02);
		textindex=(TextView) findViewById(R.id.weight_index);
		
		text_shengao=(EditText)findViewById(R.id.edit_length); 		        
		text_tizhong=(EditText)findViewById(R.id.edit_weight); 	  	        
		text_jiwang=(EditText)findViewById(R.id.edit_health);	  	        
		text_jibing=(EditText)findViewById(R.id.edit_disease);  	          	                	                
		
		//*******************焦点问题，有待处理************************
		mScrollView0=(ScrollView)findViewById(R.id.myScrollView0);
		mScrollView1=(ScrollView)findViewById(R.id.myScrollView1);
		mScrollView2=(ScrollView)findViewById(R.id.myScrollView2);	
	}
	
	private OnClickListener listener = new OnClickListener(){
       	
		 public void onClick(View v){ 			
       		switch(v.getId()){
       		case R.id.btn_back:
       			finish();
       			break;
       		case R.id.btn_edit:
       			 relativeLayout1.setVisibility(View.VISIBLE);
	 	         
	 	         editable(text_shengao);
	 	         editable(text_tizhong);
	 	         editable(text_jiwang);
	 	         editable(text_jibing);	
	 	         	 	        
    			 break;  
       		case R.id.btn_wancheng:	
          		new Thread(runnableEdit).start();         	
     	        
	     		break;
       		default:
				break;
       		}       		
       	  }
       };
   
     private void editable(EditText edit){ 
    	 edit.setEnabled(true);
   	 	 edit.setCursorVisible(true);
		 edit.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);  
		 edit.setGravity(Gravity.TOP);
		 edit.setSelection(edit.getText().length());
		 edit.setSingleLine(false);
		 edit.setHorizontallyScrolling(false); 
	     InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		 if(ipEnable)
			 imm.showSoftInput(edit, 0); //第二次点击后显示软键盘  
	     ipEnable = true;		     
     }
     
     private void uneditable(EditText edit){  	 
   	     edit.setCursorVisible(false);
   	     edit.setEnabled(false);
     }
         
     private String weightIndex(EditText weight,EditText length){
		String index;
		float a,b,c;
  		DecimalFormat df = new DecimalFormat("########.0");
	    a=Float.parseFloat(weight.getText().toString());
	    b=Float.parseFloat(length.getText().toString());
	    c=a/b/b*10000;
	    index=String.valueOf(Double.parseDouble(df.format(c)));
    	return index;
     }
   
     Runnable runnableEdit = new Runnable(){
// 		private String url ="http://182.92.224.124:8086/yijia/zm/edit_userinfo";	
 		private String url =HttpUrl.IP+HttpUrl.DIR+"edit_userinfo";	
 		private int statusCode;
 		public void run()
 		{						
 			disease=text_jibing.getText().toString();
	        health=text_jiwang.getText().toString();
	        weight=text_tizhong.getText().toString();
	        length=text_shengao.getText().toString();
	        Message msg = new Message(); 
	        if(Integer.parseInt(weight)<500&&Integer.parseInt(weight)>20&&Integer.parseInt(length)<300&&Integer.parseInt(length)>30){
	        	Map<String, String> map = new HashMap<String, String>();
	        	map.put("userId", app.getID());
	        	map.put("height", length);
	        	map.put("weight", weight);
	        	map.put("past_health", health);
	        	map.put("disease", disease);
	        	try {
	        		retStrEdit = NetTool.sendGetRequest(url, map, "utf-8");
	        		JSONObject dataJson=new JSONObject(retStrEdit);
	        		statusCode=dataJson.getInt("statusCode");
	        		
	        		if(statusCode==-1)
	        			msg.what = TestStart.SUCCESS;
	        		else{
	        			msg.what = TestStart.FAIL;
	        		}
	        	} catch (Exception e) {
	        		e.printStackTrace();
	        		msg.what = TestStart.DISMISS;
	        	}		        	
	        }else{
	        	msg.what=TestStart.UNFINISH;
	        }
	        handler.sendMessage(msg); 						
 		}				
 	}; 
 	
 	Runnable runnableInfo = new Runnable(){
//  		private String url ="http://182.92.224.124:8086/yijia/zm/download_userinfo";		
  		private String url =HttpUrl.IP+HttpUrl.DIR+"download_userinfo";		
  		private int statusCode;		
  		private JSONObject dataJson,subDataJson;
  		public void run(){						
  			Message msg = new Message(); 
  			Map<String, String> map = new HashMap<String, String>();
  			map.put("userId", app.getID());  			
  			try {
  				JSONInfo = NetTool.sendGetRequest(url, map, "utf-8");
  				dataJson = new JSONObject(JSONInfo);
  				statusCode=dataJson.getInt("statusCode");//Toast据此提示
  				subDataJson=dataJson.getJSONObject("result");//到此解开第一层json
  				
  				name=subDataJson.getString("true_name");//开始解第二层json
  				sex=subDataJson.getString("sex");
  				birthday=subDataJson.getString("birthday");//体重指数前台计算
  				weight=subDataJson.getString("weight");
  				length=subDataJson.getString("height");
  				health=subDataJson.getString("past_health");
  				disease=subDataJson.getString("disease");
  				//更新UI	
  				if(statusCode==-1)
  					msg.what = TestStart.COMPLETED;
  				else{
  					msg.what = TestStart.UNCOMPLETED;
  				}
  			} catch (Exception e) {
  				e.printStackTrace();
  				msg.what = TestStart.DISMISS;
  			}  			 					
  			handler.sendMessage(msg); 						
  		}				
  	}; 
}  