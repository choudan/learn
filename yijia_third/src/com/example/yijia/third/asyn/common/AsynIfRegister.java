package com.example.yijia.third.asyn.common;

import java.util.HashMap;

import android.content.Context;

import com.example.yijia.third.base.BaseAsyn;

/**
 * @author  丑旦 
 * @date 创建时间：2015/10/24 上午12:00:20
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 *
 */
public class AsynIfRegister extends BaseAsyn {
	private String serialNum; 
	
	public AsynIfRegister(Context mContext,String serialNum) {
		super(mContext);
		// TODO Auto-generated constructor stub
		this.serialNum = serialNum;
	}
	
	@Override
	protected HashMap<String, String> getMap() {
		// TODO Auto-generated method stub
		HashMap<String, String> map = super.getMap();
		map.put("serialNum", serialNum);
		return map;
	}

	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return "/if/register";
	}

}
