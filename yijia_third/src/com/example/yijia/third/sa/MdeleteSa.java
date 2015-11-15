package com.example.yijia.third.sa;

import android.os.Bundle;
import android.util.Log;

import com.example.yijia.third.tool.Constant;
import com.example.yijia.wiget.dialog.DeleteDialog;

/**
 * @author 丑旦
 * @date 创建时间：2015-8-21 下午10:23:58
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class MdeleteSa extends DeleteDialog {	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		init();
		String name=getIntent().getStringExtra(Constant.SA_NAME);
		Log.e(TAG, "要删除的医生是： "+name);
		setTittle("是否删除医生"+name+"？");
		setLeftBtnContent("是");
		setRightBtnContent("否");
	}
}
