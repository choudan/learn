package com.choosephoto;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.choosephoto.PubSelectedImgsAdapter.OnItemClickClass;
import com.download.doctorback.DoctorReply;
import com.download.doctorback.MyApplication;
import com.example.yijia.R;
import com.http.tool.HttpUrl;
import com.http.tool.NetTool;
import com.http.tool.PictureUtil;
import com.yijia_login.TestStart;

public class AddUploadPhoto extends Activity {

	private GridView gridView2;
	private PubSelectedImgsAdapter pubSelectedImgsAdapter;
	
	private ArrayList<String> listfile=new ArrayList<String>();
	private ArrayList<String> compressfile=new ArrayList<String>();
	private ArrayList<String> listname=new ArrayList<String>();
	private ArrayList<String> listretStr=new ArrayList<String>();
	
	private Button Upload;
	private String retStr;
	private EditText edit_content;
	private TextView text;

	private ProgressBar progressbar;
	private MyApplication app;
	private Timer timer;
	private TimerTask task;
	private int COUNT=-1;
	//全局变量
	public static String ABS_PATH="";//将图片和文字联系起来
	private String back_user_picpath;

	private Handler handler = new Handler() {  
	    public void handleMessage(Message msg) //此方法在UI线程中运行
	    {  
	    	progressbar.setVisibility(View.GONE);
	    	switch(msg.what){
	    	case TestStart.FAIL:
	        	Toast toast=Toast.makeText(AddUploadPhoto.this, "图片上传异常", Toast.LENGTH_SHORT);
     	    	toast.show();    		
	    		break;
	    	case TestStart.SUCCESS:
        		ABS_PATH=parseJSONArray(listretStr);
				new Thread(uploadUserWords).start();
				
				Toast toast1=Toast.makeText(AddUploadPhoto.this, "图片上传成功", Toast.LENGTH_SHORT);
     	    	toast1.show();
	        	
//     	    	text.setText(retStr);//测试专用
     	    	
     	    	DoctorReply.instance.finish();
		        startActivity(new Intent(AddUploadPhoto.this, DoctorReply.class));	
		        finish();
	    		break;
	    	case TestStart.FINISH:	    		
        		Toast toast2=Toast.makeText(AddUploadPhoto.this, " 发言成功", Toast.LENGTH_SHORT);
     	    	toast2.show();
	    		break;
	    	case TestStart.UNFINISH:
        		Toast toast3=Toast.makeText(AddUploadPhoto.this, "发言失败", Toast.LENGTH_SHORT);
     	    	toast3.show();   		
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
	        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//设置导航栏
		}	
		setContentView(R.layout.photo_view);
		
		init();
		
		Bundle bundle= getIntent().getExtras();
	
		if (bundle!=null) 
			if (bundle.getStringArrayList("files")!=null) {
				
				listfile= bundle.getStringArrayList("files");
//				-----------------------------------------------------
													
				compressfile=save(listfile);	
				
//              -----------------------------------------------------				
//				listname=getName(listfile);
				listname=getName(compressfile);
				
				pubSelectedImgsAdapter=new PubSelectedImgsAdapter(AddUploadPhoto.this, listfile, new OnItemClickClass(){
					@Override
					public void OnItemClick(View v, String filepath) {
						
						listfile.remove(filepath);
						pubSelectedImgsAdapter.notifyDataSetChanged();
					}
				});
				gridView2.setAdapter(pubSelectedImgsAdapter);
			}else{			
				Toast.makeText(AddUploadPhoto.this, "图片选择错误", Toast.LENGTH_SHORT).show();
		 }
	 }
	
	Runnable compress=new Runnable(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}		
	};
	
	private String save(String s) {
		String path="";
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
		String timeStamp = format.format(new Date());
		boolean b=false;
		if (s != null) {
			try {	
				path=PictureUtil.getAlbumDir()+"/small_"+"yijia_" + timeStamp + ".jpg";		
				
				Bitmap bm = PictureUtil.getSmallBitmap(s);	
				
				FileOutputStream fos = new FileOutputStream(new File(
						PictureUtil.getAlbumDir(),"/small_"+"yijia_" + timeStamp + ".jpg"));
				
				b=bm.compress(Bitmap.CompressFormat.JPEG, 85, fos);				
				
				bm.recycle();
				
				fos.flush();    
				
				fos.close(); 
							
				Toast.makeText(this, "OK"+path, Toast.LENGTH_SHORT).show();
				
			} catch (Exception e) {
				Log.e("++++++++", "error"+b);
				Toast.makeText(this, "NO"+" "+b+" "+"值", Toast.LENGTH_SHORT).show();			
			}
		}	
		return path;
	}
	private ArrayList<String> save(ArrayList<String> s) {		
		String path="";
		Bitmap bm = null; 
		FileOutputStream fos=null;
		ArrayList<String> list=new ArrayList<String>();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String timeStamp = format.format(new Date());
		boolean b=false;
		if (s != null) {
			// TODO Auto-generated method stub			
			try {	
				for(int i=0;i<s.size();i++){
					path=PictureUtil.getAlbumDir()+"/small_"+"yijia_" + timeStamp+i + ".jpg";//图片最终保存的路径，只做返回值，无意义。
					list.add(path);
					bm = PictureUtil.getSmallBitmap(s.get(i));	
					fos = new FileOutputStream(new File(PictureUtil.getAlbumDir(),"/small_"+"yijia_" + timeStamp+i + ".jpg"));
					b=bm.compress(Bitmap.CompressFormat.JPEG, 85, fos);	
					bm.recycle();	
				}	
				fos.flush();    			
				fos.close(); 					
				Toast.makeText(this, "OK"+path, Toast.LENGTH_SHORT).show();				
			} catch (Exception e) {
				Log.e("++++++++", "error"+b);
			}
		}	
		return list;
	}
	
	
	 private void init(){
		 
		 app = (MyApplication) getApplication(); 
		 
		 gridView2=(GridView) findViewById(R.id.gridView2);
		 
		 Upload=(Button)findViewById(R.id.upload);
		 Upload.setOnClickListener(listener);
		 
		 edit_content=(EditText)findViewById(R.id.edit_content1);
		 edit_content.setOnClickListener(listener);
		 
		 text=(TextView)findViewById(R.id.text);
		 progressbar=(ProgressBar)findViewById(R.id.progressbar);		
	 }
	
	 private OnClickListener listener = new OnClickListener(){
	   	public void onClick(View v){    		
	   		switch(v.getId()){
			case R.id.upload:      //上传照片
				if(listfile.size()>0){
					new Thread(uploadPicThread).start();
					progressbar.setVisibility(View.VISIBLE);
					uneditable(edit_content);
				}else{
					Toast.makeText(AddUploadPhoto.this, "上传图片不能为空", Toast.LENGTH_SHORT).show();
				}
		        break; 	
			case R.id.edit_content:
				edit_content.setHint(null);
				break;
			default:
				break;
	       	}	
	   	}
	 }; 
	 
	 private void uneditable(EditText edit){  	 
   	     edit.setEnabled(false);
   	     edit.setCursorVisible(false);
    }
	 
	 Runnable uploadPicThread = new Runnable(){
		private String url =HttpUrl.IP+HttpUrl.DIR+"upload_img";								
		public void run(){	
		    String id=app.getXULIEHAO();			
			try {  
				/* for(int i=0;i<listfile.size();i++) {
					retStr = NetTool.sendFile(url, id+listname.get(i),listfile.get(i));
					listretStr.add(retStr);			        		
				 }	*/		 
				for(int i=0;i<listfile.size();i++) {
					retStr = NetTool.sendFile(url, id+listname.get(i),compressfile.get(i));
					listretStr.add(retStr);			        		
				 }			 
				 Message msg = new Message(); 
				 if(parseJSONArray(listretStr).isEmpty())
					 msg.what = TestStart.FAIL; 
				 else{
					 msg.what = TestStart.SUCCESS;  
				 }
				 handler.sendMessage(msg); 
			}catch (Exception e) {
				e.printStackTrace();
			}			       
		}						
	};	
		
	private  String parseJSONArray(ArrayList<String> s){
		JSONObject jsonbject;
		JSONArray jsonarray;
		String jsonpicpath,picpath;
		StringBuffer pic=null;
		ArrayList<String> path=new ArrayList<String>();
		try{
			for(int i=0;i<s.size();i++){
				jsonbject=new JSONObject(s.get(i));
				jsonpicpath=jsonbject.getString("result");
				jsonarray=new JSONArray(jsonpicpath);
				picpath=jsonarray.getString(0);
				path.add(picpath);
			}
			String[] stringPath = path.toArray(new String[path.size()]);
			pic=new StringBuffer(stringPath[0]);
			for(int i=1;i<stringPath.length;i++){
			    	pic.append(",");
			    	pic.append(stringPath[i]);	
			}															    
		}
		catch(Exception e) {				
		}
		return pic.toString();
	}
		
	private ArrayList<String> getName(ArrayList<String> s){
		 ArrayList<String> listname=new ArrayList<String>();
		 for(int i=0;i<s.size();i++){
		  String[] namearray=s.get(i).split("/");
		  String name=namearray[namearray.length-1];
		  listname.add(name);
		 }
		 return listname;
	}
		
	/*
 	 * 提交发言
 	 */	
 	Runnable uploadUserWords = new Runnable(){
  		private String url =HttpUrl.IP+HttpUrl.DIR+"user_back";				       
        private JSONObject jsonUpload;
        private int statusCode;
  		public void run()
  		{						 			
  			Map<String, String> map = new HashMap<String, String>();
  			map.put("userid", app.getID());     //测试时，注意修改。
 			map.put("doctorid",app.getDoc_ID());
 			map.put("user_words",edit_content.getText().toString().trim());
 			map.put("user_picpath",AddUploadPhoto.ABS_PATH);			
 			
  			try {
  				 back_user_picpath = NetTool.sendGetRequest(url, map, "utf-8"); 								
  				 jsonUpload=new JSONObject(back_user_picpath);	
  				 statusCode=jsonUpload.getInt("statusCode");
  				 Message msg = new Message();  
  				 if(statusCode==-1){
  					 msg.what=TestStart.FINISH;	 	  	             
  				 }else	  	         {
  					 msg.what=TestStart.UNFINISH;
  				 }
  				 handler.sendMessage(msg); 
  			} catch (Exception e) {
  				e.printStackTrace();
  			}  			  			
  		}					  		
	 };
 }     
