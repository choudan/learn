package com.yijia_use;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
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

import com.choosephoto.PubSelectedImgsAdapter;
import com.choosephoto.PubSelectedImgsAdapter.OnItemClickClass;
import com.download.doctorback.DoctorReply;
import com.download.doctorback.MyApplication;
import com.example.yijia.R;
import com.http.tool.HttpUrl;
import com.http.tool.NetTool;
import com.http.tool.PictureUtil;
import com.yijia_login.TestStart;

public class TakePhoto extends Activity {
	
	private static final int PERMIT = 0;
	private static final int REFUSE = 1;
	private static final int REQUEST_TAKE_PHOTO = 2;
	private Button Upload;
	private String retStr,back_user_picpath;
//	private TextView text;
	private EditText edit_content;
	private static String mCurrentPhotoPath="";// ͼƬ·��

	private GridView gridView2;	
	private ArrayList<String> file=new ArrayList<String>();
	private ArrayList<String> listretStr=new ArrayList<String>();
	
	private PubSelectedImgsAdapter pubSelectedImgsAdapter;
	private ProgressBar progressbar;

	public static String ABS_PATH="";
	private MyApplication app;
	
	private String imageFileName;
	
	private Handler handler = new Handler() {  
	    public void handleMessage(Message msg) //�˷�����UI�߳�������
	    {  
	    	progressbar.setVisibility(View.GONE);	    	
	    	switch(msg.what){
	    	case TestStart.UNCOMPLETED:
	        	Toast toast=Toast.makeText(TakePhoto.this, "ͼƬ�ϴ��쳣", Toast.LENGTH_LONG);
     	    	toast.show();    		
	    		break;
	    	case TestStart.COMPLETED:	    		
        		ABS_PATH=parseJSONArray(listretStr);
				new Thread(uploadUserWords).start();//ֻ��ͼƬ�ϴ��ɹ��Ż��ϴ�����
				
				Toast toast1=Toast.makeText(TakePhoto.this, "ͼƬ�ϴ��ɹ�", Toast.LENGTH_SHORT);
				toast1.show();
		        
				DoctorReply.instance.finish();//ֻ���ϴ��ɹ��Źر�֮ǰ��DoctorReply
				   	    	
		        startActivity(new Intent(TakePhoto.this, DoctorReply.class));//���¼���DoctorReply	
		        finish();
	    		break;
	    	case TestStart.FINISH:	    		
        		Toast toast2=Toast.makeText(TakePhoto.this, " ���Գɹ�", Toast.LENGTH_SHORT);
     	    	toast2.show();
	    		break;
	    	case TestStart.UNFINISH:
        		Toast toast3=Toast.makeText(TakePhoto.this, "����ʧ��", Toast.LENGTH_SHORT);
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
            //͸��״̬��
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);            
	        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//���õ�����
		}	
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
		
		setContentView(R.layout.photo_view_take);
        app = (MyApplication) getApplication(); 
				         
		gridView2=(GridView) findViewById(R.id.gridView2);
		
		Upload=(Button)findViewById(R.id.upload);
		Upload.setOnClickListener(listener);
						
		edit_content=(EditText)findViewById(R.id.edit_content);
		edit_content.setOnClickListener(listener);
		
		progressbar=(ProgressBar)findViewById(R.id.progressbar);
											   
	    showPicturePicker(TakePhoto.this);					  				  
	  }
	
	private OnClickListener listener = new OnClickListener(){
		public void onClick(View v){
			switch(v.getId()){
			case R.id.upload:         //�ϴ���Ƭ
				if(file.size()>0){
					new Thread(uploadPicThread).start();
					progressbar.setVisibility(View.VISIBLE);
					uneditable(edit_content);
				}
				else{
					Toast toast=Toast.makeText(TakePhoto.this, "�ϴ�ͼƬ����Ϊ��", Toast.LENGTH_SHORT);
					toast.show();
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
	
/*	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case REQUEST_TAKE_PHOTO:  
				if(data!=null){
					Toast.makeText(TakePhoto.this, "data��Ϊ��", Toast.LENGTH_SHORT).show();
				}else{
					PictureUtil.galleryAddPic(TakePhoto.this, mCurrentPhotoPath);
					save();
					file.add(path);
					pubSelectedImgsAdapter=new PubSelectedImgsAdapter(TakePhoto.this, file, new OnItemClickClass(){
						@Override
						public void OnItemClick(View v, String filepath) {
							file.remove(filepath);
							pubSelectedImgsAdapter.notifyDataSetChanged();
						}
					});
					gridView2.setAdapter(pubSelectedImgsAdapter);		
				}
//		        //�������ڱ��ص�ͼƬȡ������С����ʾ�ڽ�����  
//		        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/image.jpg");  
//		        Bitmap newBitmap = ImageTools.zoomBitmap(bitmap, bitmap.getWidth() / SCALE, bitmap.getHeight() / SCALE);  
//		        //����Bitmap�ڴ�ռ�ýϴ�������Ҫ�����ڴ棬����ᱨout of memory�쳣  
//		        bitmap.recycle();  
//		          
//		        ImageTools.savePhotoToSDCard(newBitmap, Environment.getExternalStorageDirectory().getAbsolutePath(), String.valueOf(System.currentTimeMillis()));  
		        break;  
			}
		}else{
			file.clear();
		}
	}*/
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent data) {	
		switch(requestCode){
		case REQUEST_TAKE_PHOTO:
			if(resultCode == Activity.RESULT_OK){
				if(data!=null)
					return;
				else if(!mCurrentPhotoPath.equals("")){						
					file.add(save(mCurrentPhotoPath));
					PictureUtil.galleryAddPic(this, mCurrentPhotoPath);
					Log.e("����", ""+file.size()+file.get(0));				
				}
//				else{
//					try {
//						File f = createImageFile();
//						mCurrentPhotoPath = f.getAbsolutePath();		
//						file.add(save(mCurrentPhotoPath));
//						Log.e("����", "ִ�е���");
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}		
//				}
			}
			pubSelectedImgsAdapter=new PubSelectedImgsAdapter(TakePhoto.this, file, new OnItemClickClass(){
				@Override
				public void OnItemClick(View v, String filepath) {
					file.remove(filepath);
					pubSelectedImgsAdapter.notifyDataSetChanged();
				}
			});
			gridView2.setAdapter(pubSelectedImgsAdapter);
			break;
		}	
	}
	
	/*
	 * ����һ����Χ�ڵĲ��ظ������������ͼƬ����
	 */
	private String Random(){
		String picname;
        Random random=new Random();       
        int randomInt=random.nextInt(10000); 
        picname=String.valueOf(randomInt);
        return picname;
    }
	
	private String save(String s) {
		String path="";
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
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
				Toast.makeText(this, "NO"+" "+b+" "+"ֵ"+mCurrentPhotoPath, Toast.LENGTH_SHORT).show();			
			}
		}	
		return path;
	}
	 
	public void showPicturePicker(Context context){
		   final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		   builder.setTitle("�ü��������������");
		   builder.setItems(new String[]{"����","�ܾ�"}, new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case PERMIT:
						takePhoto();
						break;
						
					case REFUSE:
						builder.create().dismiss();
						break;
	
					default:
						break;
					}
				}
			});			
		   builder.create().show();
		}
	
	
	/**
	 * ����
	 */
	private void takePhoto() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		try {
			// ָ�����������Ƭ��λ��
			File f = createImageFile();
			mCurrentPhotoPath = f.getAbsolutePath();		
			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
			startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �ѳ����������Ƭ�ŵ� SD���� PicturesĿ¼�� yijia �ļ�����
	 * ��Ƭ����������Ϊ��yijia_20130125_173729.jpg
	 * 
	 * @return
	 * @throws IOException
	 */
	@SuppressLint("SimpleDateFormat")
	private File createImageFile() throws IOException {

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String timeStamp = format.format(new Date());
		imageFileName = "yijia_" + timeStamp + ".jpg";

		File image = new File(PictureUtil.getAlbumDir(), imageFileName);
		return image;
	}
	
	Runnable uploadPicThread = new Runnable(){
//		private String url ="http://182.92.224.124:8086/yijia/zm/upload_img/";								
		private String url =HttpUrl.IP+HttpUrl.DIR+"upload_img";								
		public void run(){	
		    String id=app.getXULIEHAO();
			try {  
				 for(int i=0;i<file.size();i++){
					retStr = NetTool.sendFile(url, id+Random()+Random()+file.get(i),file.get(i));
					listretStr.add(retStr);			        		
				 }				 
			} catch (Exception e) {
					e.printStackTrace();
				}			       
			  /*
 	  		 * ����ר��	
 	  		 */
  			 Message msg = new Message(); 
  			 if(parseJSONArray(listretStr).isEmpty())
  			   msg.what = TestStart.UNCOMPLETED; 
  			 else{
               msg.what = TestStart.COMPLETED;  
             }
             handler.sendMessage(msg); 
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
			
	/*
 	 * �ύ����
 	 */	
 	Runnable uploadUserWords = new Runnable(){
//  		private String url ="http://182.92.224.124:8086/yijia/zm/user_back";				       
  		private String url =HttpUrl.IP+HttpUrl.DIR+"user_back";				       
        private JSONObject jsonUpload;
        private int statusCode;
  		public void run()
  		{						 			
  			Map<String, String> map = new HashMap<String, String>();
  			map.put("userid", app.getID());             //����ʱ��ע���޸ġ�
 			map.put("doctorid",app.getDoc_ID());
 			map.put("user_words",edit_content.getText().toString());
 			map.put("user_picpath",TakePhoto.ABS_PATH);			
 			
  			try {
  				 back_user_picpath = NetTool.sendGetRequest(url, map, "utf-8"); 								
  				 jsonUpload=new JSONObject(back_user_picpath);	
  				 statusCode=jsonUpload.getInt("statusCode");
  			} catch (Exception e) {
  				e.printStackTrace();
  		}  			
  			  /*
  	  		 * ����ר��	
  	  		 */
  	  		Message msg = new Message();  
  	        if(statusCode==-1){
  	        	msg.what=TestStart.FINISH;
  	        }else{
  	        	msg.what=TestStart.UNFINISH;
  	        }	  	  		 	 	  	             
  	         handler.sendMessage(msg); 
  		}					  		
 	};
 }     

	
	
	
