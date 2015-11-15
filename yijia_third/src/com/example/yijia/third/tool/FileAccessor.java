/**
 * 
 */
package com.example.yijia.third.tool;

import java.io.File;

import com.example.yijia.third.base.BaseApp;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

/**
 * @author Administrator
 * 文件操作工具类
 */
public class FileAccessor {
	public static final String TAG = FileAccessor.class.getName();
	public static String EXTERNAL_STOREPATH = getExternalStorePath();
	public static String APPS_ROOT_DIR = getExternalStorePath()+"/yijia";
	public static String CAMERA_PATH = getExternalStorePath()+"/DCIM/yijia";//查明文件夹作用
	public static String MESSAGE_IMAGE = getExternalStorePath()+"/yijia/image";
	public static String MESSAGE_AVATAR = getExternalStorePath()+"/yijia/avatar";
	public static String CRASH = getExternalStorePath()+"/yijia/crash";
	
	/**
	 * @author Administrator
	 * 初始化应用文件夹目录
	 */
	public static void init(){
		File rootDir = new File(APPS_ROOT_DIR);
		if(!rootDir.exists())
			rootDir.mkdir();
		
		File imageDir = new File(MESSAGE_IMAGE);
		if(!imageDir.exists())
			imageDir.mkdir();
		
		File avatarDir = new File(MESSAGE_AVATAR);
		if(!avatarDir.exists())
			avatarDir.mkdir();
		
		File crashDir = new File(CRASH);
		if(!crashDir.exists())
			crashDir.mkdir();
				
	}
	
	/**
	 * @author Administrator
	 * 存储卡路径
	 */
	public static String getExternalStorePath() {
		// TODO Auto-generated method stub
		if(isExistExternalStore()){
			Log.e(TAG, "=-=-=getExternalStorePath..."+Environment.getExternalStorageDirectory().getAbsolutePath());
			return Environment.getExternalStorageDirectory().getAbsolutePath();
		}
		return null;
	}

	/**
	 * @author Administrator
	 * 是否有存储卡
	 */
	public static boolean isExistExternalStore() {
		// TODO Auto-generated method stub
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			return true;
		}else
			return false;
	}
	
	/**
	 * 获取文件名
	 * @author Administrator
	 * @param pathName
	 */
	public static String getFileName(String pathName){
		int start = pathName.lastIndexOf("/");
		if(start!=-1){
			return pathName.substring(start+1, pathName.length());
		}
		return pathName;
	}
	
	/**
	 * 获取头像存储文件夹
	 * @author Administrator
	 * @param  
	 */
	public static File getAvatarPathName(){
		if(!isExistExternalStore()){
			Toast.makeText(BaseApp.getInstance(), "SD卡 不存在", Toast.LENGTH_SHORT).show();
			return null;
		}
		File directory = new File(MESSAGE_AVATAR);
		if(!directory.exists()&&!directory.mkdirs()){
			Toast.makeText(BaseApp.getInstance(), "头像文件夹创建失败...", Toast.LENGTH_SHORT).show();		
			return null;
		}
		return directory;
	}
	
	/**
	 * 获取头像存储文件夹
	 * @author Administrator
	 * @param  
	 */
	public static File getImagePathName(){
		if(!isExistExternalStore()){
			Toast.makeText(BaseApp.getInstance(), "SD卡 不存在", Toast.LENGTH_SHORT).show();
			return null;
		}
		File directory = new File(MESSAGE_IMAGE);
		if(!directory.exists()&&!directory.mkdirs()){
			Toast.makeText(BaseApp.getInstance(), "图片文件夹创建失败...", Toast.LENGTH_SHORT).show();		
			return null;
		}
		return directory;
	}
 }
