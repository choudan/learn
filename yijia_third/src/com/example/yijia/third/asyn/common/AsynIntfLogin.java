package com.example.yijia.third.asyn.common;

import java.util.HashMap;

import android.content.Context;

import com.example.yijia.third.base.BaseAsyn;

/**
 * @author  丑旦 
 * @date 创建时间：2015/10/22 下午11:20:13
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 *
 */
public class AsynIntfLogin extends BaseAsyn{
	private String userName,password;

	public AsynIntfLogin(Context mContext,String userName,String password) {
		super(mContext);
		// TODO Auto-generated constructor stub
		this.userName = userName;
		this.password = password;
	}

	@Override
	protected HashMap<String, String> getMap() {
		// TODO Auto-generated method stub
		HashMap<String, String> map = super.getMap();
		map.put("userName", userName);
		map.put("password", password);
		return map;
	}

	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return "/intf/login";
	}
}
