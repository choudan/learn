package com.example.yijia.third.base;

import java.util.ArrayList;

import android.content.Context;

/**
 * @author  丑旦 
 * @date 创建时间：2015-10-1 下午11:23:33
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 *
 */
public abstract class BaseAsynPic extends AsynModel{

	protected BaseAsynPic(Context mContext) {
		super(mContext);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected String doInBackground(Integer... arg0) {
		// TODO Auto-generated method stub
		String content = interactive(getUrl(),getText(), getArrayList());
		return content;
	}
	
	protected abstract String getUrl();

	protected abstract String getText();
	
	protected abstract ArrayList<String> getArrayList();
	
}
