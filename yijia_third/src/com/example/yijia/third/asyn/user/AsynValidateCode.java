package com.example.yijia.third.asyn.user;

import java.util.HashMap;

import android.content.Context;

import com.example.yijia.third.base.BaseAsyn;

/**
 * @author  丑旦 
 * @date 创建时间：2015-11-14 下午5:22:20
 * @version 1.0 
 * @description: TODO
 * @parameter  
 * @since  
 * @return 验证验证码
 *
 */
public class AsynValidateCode extends BaseAsyn{
	private String identityCode;
	
	public AsynValidateCode(Context mContext,String identityCode) {
		super(mContext);
		// TODO Auto-generated constructor stub
		this.identityCode = identityCode;
	}

	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return "/validate/code";
	}

	@Override
	protected HashMap<String, String> getMap() {
		// TODO Auto-generated method stub
		HashMap<String, String> map = super.getMap();
		map.put("identityCode", identityCode);
		return map;
	}
	
}

