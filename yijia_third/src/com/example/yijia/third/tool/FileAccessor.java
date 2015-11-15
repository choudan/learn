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
 * �ļ�����������
 */
public class FileAccessor {
	public static final String TAG = FileAccessor.class.getName();
	public static String EXTERNAL_STOREPATH = getExternalStorePath();
	public static String APPS_ROOT_DIR = getExternalStorePath()+"/yijia";
	public static String CAMERA_PATH = getExternalStorePath()+"/DCIM/yijia";//�����ļ�������
	public static String MESSAGE_IMAGE = getExternalStorePath()+"/yijia/image";
	public static String MESSAGE_AVATAR = getExternalStorePath()+"/yijia/avatar";
	public static String CRASH = getExternalStorePath()+"/yijia/crash";
	
	/**
	 * @author Administrator
	 * ��ʼ��Ӧ���ļ���Ŀ¼
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
	 * �洢��·��
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
	 * �Ƿ��д洢��
	 */
	public static boolean isExistExternalStore() {
		// TODO Auto-generated method stub
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			return true;
		}else
			return false;
	}
	
	/**
	 * ��ȡ�ļ���
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
	 * ��ȡͷ��洢�ļ���
	 * @author Administrator
	 * @param  
	 */
	public static File getAvatarPathName(){
		if(!isExistExternalStore()){
			Toast.makeText(BaseApp.getInstance(), "SD�� ������", Toast.LENGTH_SHORT).show();
			return null;
		}
		File directory = new File(MESSAGE_AVATAR);
		if(!directory.exists()&&!directory.mkdirs()){
			Toast.makeText(BaseApp.getInstance(), "ͷ���ļ��д���ʧ��...", Toast.LENGTH_SHORT).show();		
			return null;
		}
		return directory;
	}
	
	/**
	 * ��ȡͷ��洢�ļ���
	 * @author Administrator
	 * @param  
	 */
	public static File getImagePathName(){
		if(!isExistExternalStore()){
			Toast.makeText(BaseApp.getInstance(), "SD�� ������", Toast.LENGTH_SHORT).show();
			return null;
		}
		File directory = new File(MESSAGE_IMAGE);
		if(!directory.exists()&&!directory.mkdirs()){
			Toast.makeText(BaseApp.getInstance(), "ͼƬ�ļ��д���ʧ��...", Toast.LENGTH_SHORT).show();		
			return null;
		}
		return directory;
	}
 }
