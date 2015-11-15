/**
 * 
 */
package com.example.yijia.third.uploadphoto;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.example.yijia.third.tool.Constant;
import com.example.yijia.third.tool.PictureUtil;
import com.example.yijia.wiget.dialog.DeleteDialog;

/**
 * @author Administrator
 * 
 */
public class UtakePhotoDialog extends DeleteDialog  {
	private String photoPath;
	private ArrayList<String> listPhotoPath;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTittle("颐佳将要访问你的相机？");
		setLeftBtnContent("同意");
		setRightBtnContent("拒绝");
	}
	
	@Override
	public void confirm() {
		// TODO Auto-generated method stub
		photoPath = takePhoto();
		Log.e(TAG, "=-=-=photoPath: " + photoPath);
		PictureUtil.galleryAddPic(getApplicationContext(),photoPath);// 大图片添加到图库		
	}

	public String takePhoto() {
		// TODO Auto-generated method stub
		String path = null;
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		try {
			File pic = PictureUtil.createImageFile();
			path = pic.getAbsolutePath();// 照片存放路径
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(pic));
			startActivityForResult(intent, Constant.TAKE_PHOTO_REQUEST);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Constant.TAKE_PHOTO_REQUEST) {
			if (resultCode == Activity.RESULT_OK) {
				Log.e(TAG, "=-=-= onActivityResult：  " + resultCode);			
				Intent intent = new Intent(UtakePhotoDialog.this, UuploadPhoto.class);
				listPhotoPath = new ArrayList<String>();
				listPhotoPath.add(photoPath);
				Bundle bundle = new Bundle();
				bundle.putStringArrayList(Constant.IMAGE_URLS, listPhotoPath);
				intent.putExtras(bundle);
				startActivity(intent);
				this.finish();
			}
		}
	}
}
