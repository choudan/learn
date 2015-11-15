/**
 * 
 */
package com.example.yijia.third.msa;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.bean.common.Questionnaire;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class AquestionnaireList extends BaseActivity implements OnItemClickListener{
	private ArrayList<Questionnaire> list;
	private AquestionnaireAdapter mAdapter;
	private ListView list_code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.a_query_code_state_detail);
		
		Bundle mBundle=getIntent().getExtras();
		long msaId=mBundle.getLong(Constant.MSA_ID);
		String name=mBundle.getString(Constant.MSA_NAME);
//		测试
		Log.e(TAG, "msaId是：  "+msaId+"\n name是：  "+name);
		init();		
	}

	private ArrayList<Questionnaire> getData() {
		// TODO Auto-generated method stub
		list = new ArrayList<Questionnaire>();
		for(int i=0;i<5;i++){
			Questionnaire mQuestionnaire=new Questionnaire();
			mQuestionnaire.setQuestionnaire("圣体怎么样了？");
			mQuestionnaire.setRealName("李时珍");
			list.add(mQuestionnaire);
		}
		Log.e(TAG, "已执行list"+list.size());	
		return list;
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		setBtnVisiable(false);
		setTittleText(this.getString(R.string.ques_repository));
		
		list=getData();
		
		list_code= (ListView)findViewById(R.id.list_code);	
		mAdapter = new AquestionnaireAdapter(AquestionnaireList.this, list);
		list_code.setAdapter(mAdapter);
		list_code.setOnItemClickListener(this);
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(AquestionnaireList.this,AquestionnaireDetail.class);
		intent.putExtra(Constant.MSA_NAME, list.get(arg2).getRealName());
		intent.putExtra(Constant.QUESTIONNAIRE, list.get(arg2).getQuestionnaire());	
		startActivity(intent);
	}

}
