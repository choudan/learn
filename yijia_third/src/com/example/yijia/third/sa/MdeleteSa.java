package com.example.yijia.third.sa;

import android.os.Bundle;
import android.util.Log;

import com.example.yijia.third.tool.Constant;
import com.example.yijia.wiget.dialog.DeleteDialog;

/**
 * @author ��
 * @date ����ʱ�䣺2015-8-21 ����10:23:58
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
		Log.e(TAG, "Ҫɾ����ҽ���ǣ� "+name);
		setTittle("�Ƿ�ɾ��ҽ��"+name+"��");
		setLeftBtnContent("��");
		setRightBtnContent("��");
	}
}
