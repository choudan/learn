package com.example.yijia.third.base;

import java.util.HashMap;

import com.example.yijia.third.tool.ToastUtils;

import android.content.Context;

/**
 * @author  �� 
 * @date ����ʱ�䣺2015-10-1 ����10:47:41
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 *
 */
public abstract class BaseAsyn extends AsynModel {
	public BaseAsyn(Context mContext) {
		super(mContext);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String doInBackground(Integer... params) {
		// TODO Auto-generated method stub
		String data = interactive(getUrl(), getMap());
		return data;
	}
	

	@Override
	protected void onPostExecute(String data) {
		// TODO Auto-generated method stub
		super.onPostExecute(data);
		if(data == null){
			ToastUtils.showMessage("resultΪ�գ��޷�����...");					
		}
	}

	/**
	 * @author Administrator
	 * ��Ź�������
	 */
	protected HashMap<String, String> getMap(){
		return new HashMap<String, String>();
	};

	protected abstract String getUrl();
}
