package com.example.yijia.third.asyn.user;

import java.util.HashMap;

import android.content.Context;

import com.example.yijia.third.base.BaseAsyn;
import com.example.yijia.third.bean.common.InfoQuery;
import com.example.yijia.third.tool.BeanUtils;

/**
 * @author  丑旦 
 * @date 创建时间：2015/11/13 下午9:00:57
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 *
 */
public class AsynInfoQuery extends BaseAsyn {
	private InfoQuery infoQuery;
	
	public AsynInfoQuery(Context mContext,InfoQuery infoQuery) {
		super(mContext);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected HashMap<String, String> getMap() {
		// TODO Auto-generated method stub		
		try {
			return BeanUtils.convertBeanToMap(infoQuery);
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
		return "/info/query";
	}

}
