/**
 * 
 */
package com.example.yijia.third.uploadphoto;


import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.tool.Constant;
import com.example.yijia.third.tool.PictureUtil;
import com.example.yijia_third.R;

/**
 * @author Administrator
 *
 */
public class UimagePager extends BaseActivity{
	private ImageView mView;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);	
		init();
	}
	
	@Override
	protected void init() {
//		ArrayList<String> urls = getIntent().getStringArrayListExtra(Constant.IMAGE_URLS);
		setSubView(R.layout.u_image_pager);
		setTittleText(this.getString(R.string.upload_pic));
		
		String path = getIntent().getStringExtra(Constant.IMAGE_URLS);
		Log.e(TAG, "=-=-= path : "+path);
		mView = (ImageView) findViewById(R.id.image);
		mView.setOnClickListener(this);
		Bitmap bitmap=getBitmap(path);
		if(bitmap!=null)
			mView.setImageBitmap(bitmap);
	}
	
	protected Bitmap getBitmap(String filePath){
		Bitmap bitmap=null;		
		File file=PictureUtil.getFile(filePath);
		if(file.exists())
			bitmap = BitmapFactory.decodeFile(filePath);
		return bitmap;	
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch(v.getId()){
		case R.id.image:
			this.finish();
			break;
		case R.id.back:
			this.finish();
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
	
//	private class ImagePagerAdapter extends FragmentStatePagerAdapter {
//
//		public ArrayList<String> fileList;
//
//		public ImagePagerAdapter(FragmentManager fm, ArrayList<String> fileList) {
//			super(fm);
//			this.fileList = fileList;
//		}
//
//		@Override
//		public int getCount() {
//			return fileList == null ? 0 : fileList.size();
//		}
//
//		@Override
//		public Fragment getItem(int position) {
//			String url = fileList.get(position);
//			return ImageDetailFragment.newInstance(url);
//		}
//	}
}
