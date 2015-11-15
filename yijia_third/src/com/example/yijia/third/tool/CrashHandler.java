package com.example.yijia.third.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Properties;
import java.util.TreeSet;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

/**
 * @author  丑旦 
 * @date 创建时间：2015-10-1 上午11:45:46
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 *
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler{

	private static final String TAG = "yijia.UncaughtExceptionHandler";
	private static final String VERSION_NAME = "versionName";
	private static final String VERSION_CODE = "versionCode";
	private static final String STACK_TRACE = "STACK_TRACE";
	
	/** 文件扩展名*/
	private static final String CARSH_REPORTER_EXTENSION = ".cr";
	private static CrashHandler instance;
	/** 系统默认处理类*/
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	/** 创建*/
	private Context mContext;
	/** 使用Properties来保存设备的信息和错误堆栈信息*/
	private Properties mDeviceCrashInfo = new Properties();
	
	/**
	 * 私有化构造器，保证只有一个CrashHandler实例
	 */
	private CrashHandler(){
		
	}
	
	public static CrashHandler getInstance(){
		if(instance==null)
			instance =new CrashHandler();
		return instance;
	}
	
	public void init(Context mContext){
		this.mContext = mContext;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	
	/**
	 * 发生UncaughtException,进入该函数
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		// TODO Auto-generated method stub
		if(!handleException(ex)&&mDefaultHandler!=null){
			//没有处理则让系统默认处理
			mDefaultHandler.uncaughtException(thread, ex);
		}
	}

	/**
	 * 自定义错误处理，发送错误报告到服务器
	 * @author Administrator
	 * @param ex
	 * @return true:处理了该异常；否则为false
	 */
	private boolean handleException(Throwable ex) {
		// TODO Auto-generated method stub
		if(ex==null){
			Log.e(TAG, "=-=-=CrashHandler...ex==null");
			return true;
		}
		final String msg = ex.getLocalizedMessage();
		if(msg ==null)
			return false;
		
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				Looper.prepare();
				Toast.makeText(mContext, "我要挂了，你快去找工程师...", Toast.LENGTH_SHORT).show();
				System.exit(0);
				Looper.loop();
			}			
		}.start();
		//收集设备信息
		collectCrashDeviceInfo(mContext);
		//保存错误报告
		saveCrashInfoToFile(ex);
		return true;
	}
	
	/**
	 * 收集崩溃信息
	 * @author Administrator
	 * @param ex
	 * @return true:处理了该异常；否则为false
	 */
	public void collectCrashDeviceInfo(Context mContext) {
		// TODO Auto-generated method stub
		try {
			PackageManager pm = mContext.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
			if(pi!=null){
				mDeviceCrashInfo.put(VERSION_NAME, pi.versionName==null?"not set":pi.versionName);
				mDeviceCrashInfo.put(VERSION_CODE, ""+pi.versionCode);
			}
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, "收集包信息发生错误..."+e.getMessage());
		}
		
		//获取设备信息
		Field[]fields = Build.class.getDeclaredFields();
		for(Field field:fields){
			field.setAccessible(true);
			try {
				mDeviceCrashInfo.put(field.getName(), ""+field.get(null));
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
	
	/**
	 * 保存错误到文件
	 * @author Administrator
	 * @param ex
	 * @return String 报告文件名
	 */
	public String saveCrashInfoToFile(Throwable ex) {
		// TODO Auto-generated method stub
		Writer info = new StringWriter();
		PrintWriter printWriter = new PrintWriter(info);
		ex.printStackTrace(printWriter);
		Throwable casue = ex.getCause();
		while (casue != null) {
			casue.printStackTrace(printWriter);
			casue = casue.getCause();
		}
		String result = info.toString();
		printWriter.close();
		mDeviceCrashInfo.put("EXCEPTION", ex.getLocalizedMessage());
		mDeviceCrashInfo.put(STACK_TRACE, result);
		try {
			Time t = new Time("GTM+8");
			t.setToNow();
			int date = t.year * 10000 + t.month * 100 + t.monthDay;
			int time = t.hour * 10000 + t.minute * 100 + t.second;
			String fileName = "crash-" + date + "-" + time + CARSH_REPORTER_EXTENSION;
			if (FileAccessor.isExistExternalStore()) {
				File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Yijia");
				if (!dir.exists()) {
					dir.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(new File(dir,fileName), true);
				fos.write(result.getBytes());
				fos.close();
			} else {
				FileOutputStream trace = mContext.openFileOutput(fileName,Context.MODE_PRIVATE);
				mDeviceCrashInfo.store(trace, "");
				trace.flush();
				trace.close();
			}
			return fileName;
		} catch (Exception e) {
			Log.e(TAG, "写入错误报告出错了..." + e.getMessage());
		}
		return null;
	}
	
	/**
	 * 启动程序，包含新产生的和以前没发送的
	 * @author Administrator
	 * @param Context
	 * @return  
	 */
	public void sendPreviousReportsToServer() {
		// TODO Auto-generated method stub
		sendCrashReportToServer(mContext);
	}
	
	/**
	 * 发送错误报告到服务器，包含新产生的和以前没发送的
	 * @author Administrator
	 * @param Context
	 * @return  
	 */
	private void sendCrashReportToServer(Context ctx){
		String []crFiles = getCrashReportFiles(ctx);
		if(crFiles!=null&&crFiles.length>0){
			TreeSet<String> sortedFiles = new TreeSet<String>();
			sortedFiles.addAll(Arrays.asList(crFiles));
			for(String fielName : sortedFiles){
				File cr = new File(ctx.getFilesDir(),fielName);
				postReport(cr);
				cr.delete();
			}
		}
	}
	
	/**
	 * 发送错误报告到服务器
	 * @author Administrator
	 * @param Context
	 * @return  
	 */
	private void postReport(File cr) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 获取报告文件名
	 * @author Administrator
	 * @param Context
	 * @return String[] 报告文件名
	 */
	private String[] getCrashReportFiles(Context ctx){
		File filesDir = ctx.getFilesDir();
		FilenameFilter filter = new FilenameFilter(){
			@Override
			public boolean accept(File file, String s) {
				// TODO Auto-generated method stub				
				return s.endsWith(CARSH_REPORTER_EXTENSION);
			}			
		};
		return filesDir.list(filter);
	}
}
