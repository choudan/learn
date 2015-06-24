package com.download.doctorback;

import android.app.Application;

import com.example.yijia.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MyApplication extends Application {  
	private String ID="";//�û������ݿ�ID�����ݿ�Ƚϻ���
	private String XULIEHAO="";//URL�е�username���û���Ψһ��ʾ
	private String Doc_ID="";//����ҽ��ID
	private String NAME="";//ע����ɺ���û���������ο�DeviceControlActivity�е�onResume()����

	//ע����Ϣ,registerName��name������ǰ����Ҫ�������θ�������������Ҳ��ע������Ժ���һֱΪ�ա�
	//�����������ע�������ʹ��ʱ���û�����֮���������裬����������ֵ�Ƿ�Ϊ"",�������ֻ���Ҫ��Ҫ������������������ο�DeviceControlActivity�е�onResume()����
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
		.discCacheFileCount(100)// ����һ����ͼƬ  
		.writeDebugLogs()//  
		.build();//  
		ImageLoader.getInstance().init(config);  
	}
}  
