package com.example.yijia.third.sa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.yijia.third.base.BaseApp;
import com.example.yijia.third.bean.common.Questionnaire;
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
public class SdeleteQues extends DeleteDialog {
	private Questionnaire questionnaire;
	private final String TAG= this.getClass().getName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
		questionnaire = getIntent().getParcelableExtra(Constant.DELETE);
		Log.e(TAG, "Ҫɾ�����ʾ��ǣ� "+questionnaire.toString());
		
		if(((BaseApp)getApplication()).getRole()==3){
			if(questionnaire.isMine()) 
				setTittle("�Ƿ�ɾ�����ʾ�");
			else
				setTittle("����Ȩɾ�����ʾ�");					
		}else
			setTittle("�Ƿ�ɾ�����ʾ�");			
		setLeftBtnContent("��");
		setRightBtnContent("��");
	}

	@Override
	public void confirm() {
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		Log.e(TAG, "questionnaire.isMine():  "+questionnaire.isMine());
		if(((BaseApp)getApplication()).getRole()==3){
			if(questionnaire.isMine())
				setResult(Constant.DELETE_RESP_CONFIRM, intent);//����룬Activity.RESULT_OK				
			else
				cancel();			
		}else
			setResult(Constant.DELETE_RESP_CONFIRM, intent);//����룬Activity.RESULT_OK							
    	this.finish();  
	}
}
