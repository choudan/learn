package com.example.yijia.third.asyn.user;

import java.util.HashMap;

import android.content.Context;

import com.example.yijia.third.base.BaseAsyn;

/**
 * @author  丑旦 
 * @date 创建时间：2015-11-14 下午5:17:17
 * @version 1.0 
 * @description: TODO
 * @parameter  
 * @since  
 * @return 验证码
 *
 */
public class AsynAcquireCode extends BaseAsyn{
	private String telephone;
	
	public AsynAcquireCode(Context mContext,String telephone) {
		super(mContext);
		// TODO Auto-generated constructor stub
		this.telephone = telephone;
	}

	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub	
		return "/acquire/code";
	}

	@Override
	protected HashMap<String, String> getMap() {
		// TODO Auto-generated method stub
		HashMap<String, String> map = super.getMap();
		map.put("telephone", telephone);
		return map;
	}	
}

