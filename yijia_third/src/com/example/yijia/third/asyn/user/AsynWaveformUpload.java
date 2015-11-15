package com.example.yijia.third.asyn.user;

import java.util.HashMap;

import android.content.Context;

import com.example.yijia.third.base.BaseAsyn;

/**
 * @author  丑旦 
 * @date 创建时间：2015/10/24 上午12:04:10
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 *
 */
public class AsynWaveformUpload extends BaseAsyn{
	private String uploadData;
	
	public AsynWaveformUpload(Context mContext,String uploadData) {
		super(mContext);
		// TODO Auto-generated constructor stub
		this.uploadData = uploadData;
	}

	@Override
	protected HashMap<String, String> getMap() {
		// TODO Auto-generated method stub
		HashMap<String, String> map = super.getMap();
		map.put("uploadData", uploadData);
		return map;
	}

	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return "/waveform/upload";
	}
}

