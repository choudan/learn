package com.example.yijia.third.asyn.common;

import android.content.Context;

import com.example.yijia.third.base.BaseAsyn;
import com.example.yijia.third.bean.common.InfoEdit;

/**
 * @author  �� 
 * @date ����ʱ�䣺2015/11/13 ����1:47:19
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 *
 */
public class AsynInfoEdit extends BaseAsyn {
	//InfoEdit��UserInfo�Ĵ���
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
