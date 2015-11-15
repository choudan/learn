package com.example.yijia.third.asyn.inst;

import java.util.HashMap;

import android.content.Context;

import com.example.yijia.third.base.BaseAsyn;
import com.example.yijia.third.bean.user.UserRegister;
import com.example.yijia.third.tool.BeanUtils;

/**
 * @author  丑旦 
 * @date 创建时间：2015/11/13 上午9:49:50
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 *
 */
public class AsynRegister extends BaseAsyn {
	private UserRegister userRegister;
	//UserRegister中UserInfo的处理
	public AsynRegister(Context mContext , UserRegister userRegister) {
		super(mContext);
		// TODO Auto-generated constructor stub
		this.userRegister = userRegister;
	}
	
	@Override
	protected HashMap<String, String> getMap() {
		// TODO Auto-generated method stub
//		HashMap<String, String> map = super.getMap();		
		try {
			return BeanUtils.convertBeanToMap(userRegister);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return null;
	}

	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return "/intf/infoDetail/register";
	}
}
