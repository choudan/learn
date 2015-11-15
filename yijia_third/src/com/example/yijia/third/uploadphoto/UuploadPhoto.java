package com.example.yijia.third.uploadphoto;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.tool.Constant;
import com.example.yijia.third.tool.PictureUtil;
import com.example.yijia.third.uploadphoto.GridAdapter.OnGridItemClick;
import com.example.yijia_third.R;

public class UuploadPhoto extends BaseActivity {
	private ArrayList<String> list;
	private GridAdapter madapter;
	private EditText edit_content;
	private GridView photo_gridview;
	private Button upload;
	private ArrayList<String> smalllist;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		setSubView(R.layout.u_upload_photo);
		setTittleText(this.getString(R.string.upload_pic));
		
		list=getIntent().getExtras().getStringArrayList(Constant.IMAGE_URLS);
		Log.e(TAG, "=-=-= list.size() "+list.size());
		
		upload=(Button)findViewById(R.id.upload);
		upload.setOnClickListener(this);
						
		edit_content=(EditText)findViewById(R.id.edit_content);
		edit_content.setOnClickListener(this);
		
		photo_gridview=(GridView) findViewById(R.id.photo_gridview);
		madapter = new GridAdapter(this,list);
		madapter.setOnGridItemClick(new OnGridItemClick(){
			@Override
			public void onGridClick(View view, String path) {
				// TODO Auto-generated method stub
				Intent intent = new Intent ();
				intent.setClass(UuploadPhoto.this, UimagePager.class);
				Bundle bundle = new Bundle();
//				bundle.putStringArrayList(Constant.IMAGE_URLS, list);
				bundle.putString(Constant.IMAGE_URLS, path);
				intent.putExtras(bundle);
				startActivity(intent);
			}		
		});
		photo_gridview.setAdapter(madapter);
		
		smalllist=new ArrayList<String>();
		setEditable(edit_content, false);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch(v.getId()){
		case R.id.upload:
			interactive();
			break;
		case R.id.back:
			break;
		case R.id.edit_content:
			setEditable(edit_content, true);
			break;
			default:
				break;
		}
	}

	@Override
	protected void hanlderUi() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interactive() {
		// TODO Auto-generated method stub
		String[] strs=PictureUtil.getArray(list);
		Log.e(TAG, "=-=-= strs.length: "+strs.length);
		new CompressAsynk().execute(strs);
	}
		
	protected  class CompressAsynk extends AsyncTask<String, Integer, String>{	
		@Override
		protected String doInBackground(String...params) {
			// TODO Auto-generated method stub
			String smallPath=null;
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					Bitmap bitmap = PictureUtil.getFloatBitmap(params[i]);
					if(bitmap!=null)
						Log.e(TAG, "=-=-= bitmap.getByteCount(): "+bitmap.getByteCount());
					else
						Log.e(TAG, "=-=-= 获取位图文件bitmap失败  ");						
					try {
						smallPath = PictureUtil.compressImage(bitmap, "/yijia_small_", ".jpg",80);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}				
			}
			return smallPath;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result!=null)
				smalllist.add(result);
			Log.e(TAG, "=-=-= 路径： "+result);				
		}
	}
 }     
