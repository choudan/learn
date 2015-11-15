/**
 * 
 */
package com.example.yijia.third.uploadphoto;

import java.io.File;
import java.util.ArrayList;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.tool.Constant;
import com.example.yijia.third.uploadphoto.AllGridAdapter.OnItemClick;
import com.example.yijia_third.R;

/**
 * @author Administrator
 *
 */
public class UallPhoto extends BaseActivity {
	private GridView imgGridView;
	private Button confirm;
	private AllGridAdapter mAdapter;
	private ArrayList<String> list,uploadList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		setSubView(R.layout.u_all_photo);
		
		confirm=(Button)findViewById(R.id.confirm);
		confirm.setOnClickListener(this);
		
		list=listAlldir();
		Log.e(TAG, "=-=-= "+list.size());
		Log.e(TAG, "=-=-= "+list.get(0));
		
		uploadList = new ArrayList<String>();
		
		imgGridView = (GridView) findViewById(R.id.imgGridView);
		mAdapter = new AllGridAdapter(this, list, new OnItemClick() {
			@Override
			public void onItemClick(View view, int arg0,
					boolean isChecked) {
				// TODO Auto-generated method stub
				String photoPath=list.get(arg0);
				Log.e(TAG, "=-=-= photoPath: "+photoPath);
				if(isChecked)
					uploadList.add(photoPath);
				else{
					if (uploadList.contains(photoPath))
						uploadList.remove(photoPath);				
				}
				Log.e(TAG, "=-=-= uploadList.size(): "+uploadList.size());
				
			}
		});
		
		imgGridView.setAdapter(mAdapter);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch(v.getId()){
		case R.id.confirm:
			if(uploadList.size()<1){
				Toast.makeText(getApplicationContext(), "请选择要上传的图片", Toast.LENGTH_SHORT).show();
			}else if(uploadList.size()>9){
				Toast.makeText(getApplicationContext(), "一次最多能上传9张图片", Toast.LENGTH_SHORT).show();				
			}else
				sendfiles();
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
		
	}
	
	/**
	 * 获取全部图片地址
	 * @return
	 */
	public ArrayList<String>  listAlldir(){
    	Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    	Uri uri = intent.getData();
    	ArrayList<String> list = new ArrayList<String>();
    	String[] proj ={MediaStore.Images.Media.DATA};
    	Cursor cursor = this.getContentResolver().query(uri, proj, null, null, null);//managedQuery(uri, proj, null, null, null);
    	while(cursor.moveToNext()){
    		String path =cursor.getString(0);
    		list.add(new File(path).getAbsolutePath());
    	}
    	cursor.close();
    	Log.e(TAG, "=-=-= list.size(): "+list.size());
		return list;
    }

	/**
	 * 把选中的文档目录以list的形式传过去即可
	 * 
	 */
	public void sendfiles(){
		Intent intent =new Intent(this, UuploadPhoto.class);
		Bundle bundle=new Bundle();
		bundle.putStringArrayList(Constant.IMAGE_URLS, uploadList);
		intent.putExtras(bundle);
		startActivity(intent);
		finish();
	}
}
