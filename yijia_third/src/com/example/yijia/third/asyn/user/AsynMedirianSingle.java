package com.example.yijia.third.asyn.user;

import java.util.HashMap;

import android.content.Context;

import com.example.yijia.third.base.BaseAsyn;
import com.example.yijia.third.bean.user.MedirianSingle;
import com.example.yijia.third.tool.BeanUtils;

/**
 * @author 丑旦
 * @date 创建时间：2015/10/28 下午10:50:29
 * @version 1.0
 * @parameter
 * @since
 * @return
 * 
 */
public class AsynMedirianSingle extends BaseAsyn {
	private MedirianSingle bean;

	public AsynMedirianSingle(Context mContext, MedirianSingle bean) {
		super(mContext);
		// TODO Auto-generated constructor stub
		this.bean = bean;
	}

	protected HashMap<String, String> getMap() {
		// TODO Auto-generated method stub		
		try {
			return BeanUtils.convertBeanToMap(bean);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.getMap();
	}

	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return "/intf/medirian/single";
	}
}
