/**
 * 
 */
package com.example.yijia.third.msa;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class AquestionnaireDetail extends BaseActivity {
	private TextView msa_name,questionnaire;
	private String name,content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.a_query_questionnaire_detail);
		
		content=getIntent().getStringExtra(Constant.QUESTIONNAIRE);
		name=getIntent().getStringExtra(Constant.MSA_NAME);
//		测试
		Log.e(TAG, "name是：  "+name+"\n content是：  "+content);
		init();		
	}



	@Override
	protected void init() {
		// TODO Auto-generated method stub
		setBtnVisiable(false);
		setTittleText(this.getString(R.string.ques_repository));
		
		msa_name=(TextView)findViewById(R.id.msa_name);
		msa_name.setText(name);
		
		questionnaire=(TextView)findViewById(R.id.questionnaire);
		questionnaire.setText(content);
		
		Log.e(TAG, "已执行");		
	}

	@Override
	protected void hanlderUi() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interactive() {
		// TODO Auto-generated method stub
		
	}

}
