package com.example.yijia.third.base;

import android.app.Application;
import android.net.http.AndroidHttpClient;
import android.util.Log;

import com.example.yijia.third.common.GloablInit;
import com.example.yijia.third.tool.HttpUtils;

public class BaseApp extends Application {
	private long roleId;
	private int role;
	private String roleName = "";//���Ƴ�
	public static AndroidHttpClient mHttpClient;
	public HttpUtils httpUtils = new HttpUtils();
	private static BaseApp instance;
	
	//���ò�������ʼ��,ע�ʹ���������	
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		mHttpClient=getHttpClient();
		GloablInit.getInstance().init(this);
	}
	
	public static BaseApp getInstance(){
		if(instance==null){
			Log.e("=-=-=MyApp=-=-=", "=-=-= instanceΪ��...");
			return null;
		}
		return instance;
	}
	
	public synchronized AndroidHttpClient getHttpClient(){		
		if(mHttpClient==null){
			mHttpClient=httpUtils.createHttpClient();				
			Log.e("=-=-=MyApp=-=-=", "=-=-= mHttpClient�Ѿ�����...");
		}		
		return mHttpClient;	
	}
	
	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
		httpUtils.shutdownHttpClient();
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		httpUtils.shutdownHttpClient();
	}

	
	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
