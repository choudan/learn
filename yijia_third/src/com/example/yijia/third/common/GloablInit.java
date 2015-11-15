package com.example.yijia.third.common;

import android.content.Context;

import com.example.yijia.third.tool.AppManager;
import com.example.yijia.third.tool.FileAccessor;
import com.example.yijia_third.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * @author  丑旦 
 * @date 创建时间：2015/10/22 下午9:25:14
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 *
 */
public class GloablInit {
	private static GloablInit instance;
		
	private GloablInit() {
		super();
		instance = this;
	}

	public static GloablInit getInstance(){
		if(instance == null)
			return new GloablInit();
		else
			return instance;
	}
	
	public void init(Context context){
		initImageLoader(context);
		FileAccessor.init();
		AppManager.setContext(context);
//		CrashHandler.getInstance().init(context);
	}
	
	public void initImageLoader(Context context){
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder() //
		.showImageForEmptyUri(R.drawable.ic_launcher) //
		.showImageOnFail(R.drawable.ic_launcher) //
		.cacheInMemory(true) //
		.cacheOnDisk(true) //
		.build();//
		ImageLoaderConfiguration config = new ImageLoaderConfiguration//
				.Builder(context)//
		.defaultDisplayImageOptions(defaultOptions)//
		.diskCacheSize(50 * 1024 * 1024)// 50 Mb sd卡(本地)缓存的最大值
		.diskCacheFileCount(100)// 可以缓存的文件数量
		.writeDebugLogs()//
		.build();//
		ImageLoader.getInstance().init(config);	
	}
}
