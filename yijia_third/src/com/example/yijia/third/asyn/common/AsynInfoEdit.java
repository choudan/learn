package com.example.yijia.third.asyn.common;

import android.content.Context;

import com.example.yijia.third.base.BaseAsyn;
import com.example.yijia.third.bean.common.InfoEdit;

/**
 * @author  丑旦 
 * @date 创建时间：2015/11/13 下午1:47:19
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 *
 */
public class AsynInfoEdit extends BaseAsyn {
	//InfoEdit中UserInfo的处理
	public AsynInfoEdit(Context mContext,InfoEdit infoEdit) {
		super(mContext);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return "/info/edit";
	}

}
