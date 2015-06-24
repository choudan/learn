package com.download.doctorback;

import android.app.Application;

import com.example.yijia.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MyApplication extends Application {  
	private String ID="";//用户的数据库ID，数据库比较混乱
	private String XULIEHAO="";//URL中的username，用户的唯一标示
	private String Doc_ID="";//关联医生ID
	private String NAME="";//注册完成后的用户名，具体参看DeviceControlActivity中的onResume()方法

	//注册信息,registerName与name的区别，前者主要用来初次给经络仪命名，也即注册完成以后它一直为空。
	//后者用来存放注册后正常使用时的用户名。之所以这样设，是用这两个值是否为"",来区分手机端要不要给经络仪命名，具体参看DeviceControlActivity中的onResume()方法
	private String registerName="",registerSex="",registerBirth="",
			registerLgh="",registerWgt="",registerHealth="",registerDis="";
	private String registerSearchDoc="";
	
	public String getNAME() {
		return NAME;
	}
	
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getDoc_ID() {
		return Doc_ID;
	}
	
	public void setDoc_ID(String doc_ID) {
		Doc_ID = doc_ID;
	}
	public String getRegisterSearchDoc() {
		return registerSearchDoc;
	}

	public void setRegisterSearchDoc(String registerSearchDoc) {
		this.registerSearchDoc = registerSearchDoc;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public String getRegisterSex() {
		return registerSex;
	}

	public void setRegisterSex(String registerSex) {
		this.registerSex = registerSex;
	}

	public String getRegisterBirth() {
		return registerBirth;
	}

	public void setRegisterBirth(String registerBirth) {
		this.registerBirth = registerBirth;
	}

	public String getRegisterLgh() {
		return registerLgh;
	}

	public void setRegisterLgh(String registerLgh) {
		this.registerLgh = registerLgh;
	}

	public String getRegisterWgt() {
		return registerWgt;
	}

	public void setRegisterWgt(String registerWgt) {
		this.registerWgt = registerWgt;
	}

	public String getRegisterHealth() {
		return registerHealth;
	}

	public void setRegisterHealth(String registerHealth) {
		this.registerHealth = registerHealth;
	}

	public String getRegisterDis() {
		return registerDis;
	}

	public void setRegisterDis(String registerDis) {
		this.registerDis = registerDis;
	}
    
	public String getID() {
		return ID;
	}
	
	public void setID(String iD) {
		ID = iD;
	}  
    
	public String getXULIEHAO() {
		return XULIEHAO;
	}
	
	public void setXULIEHAO(String xULIEHAO) {
		XULIEHAO = xULIEHAO;
	}
	
	@Override  
	public void onCreate() {  
		super.onCreate();  
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder() //  
		.showImageForEmptyUri(R.drawable.health) //  
		.showImageOnFail(R.drawable.health) //  
		.cacheInMemory(true) //  
		.cacheOnDisk(true) //  
		.build();//  
		ImageLoaderConfiguration config = new ImageLoaderConfiguration//  
				.Builder(getApplicationContext())//  
		.defaultDisplayImageOptions(defaultOptions)//  
		.discCacheSize(50 * 1024 * 1024)//  
		.discCacheFileCount(100)// 缓存一百张图片  
		.writeDebugLogs()//  
		.build();//  
		ImageLoader.getInstance().init(config);  
	}
}  
