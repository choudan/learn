/**
 * 
 */
package com.example.yijia.third.sa;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.bean.common.Questionnaire;
import com.example.yijia.third.msa.MeditQuestionnaire;
import com.example.yijia.third.tool.Constant;
import com.example.yijia.wiget.swipelist.OnDeleteListioner;
import com.example.yijia.wiget.swipelist.SwipeListView;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class SquestionnaireList extends BaseActivity implements
		OnItemClickListener ,OnDeleteListioner{
	private ArrayList<Questionnaire> list;
	private SquestionnaireAdapter mAdapter;
	private SwipeListView list_questionnaire;
	private Button add;
	private Questionnaire deleteQuestionnaire;
	private int index;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.s_query_questionnaire_list);
		init();
	}

	private ArrayList<Questionnaire> getData() {
		// TODO Auto-generated method stub
		list = new ArrayList<Questionnaire>();
		for (int i = 0; i < 5; i++) {
			Questionnaire mQuestionnaire = new Questionnaire();
			mQuestionnaire.setQuestionnaire("圣体怎么样了？" + i);
			mQuestionnaire.setRealName("李时珍");
			mQuestionnaire.setQuestionnaireId(i * 5);
			mQuestionnaire.setContributorId(i);
			mQuestionnaire.setMine(true);
			list.add(mQuestionnaire);
		}
		for (int i = 5; i < 100; i++) {
			Questionnaire mQuestionnaire = new Questionnaire();
			mQuestionnaire.setQuestionnaire("圣体怎么样了？" + i);
			mQuestionnaire.setRealName("李时珍");
			mQuestionnaire.setQuestionnaireId(i * 5);
			mQuestionnaire.setContributorId(i);
			mQuestionnaire.setMine(false);
			list.add(mQuestionnaire);
		}
		Log.e(TAG, "已执行list" + list.size());
		return list;
	}
	
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		setBtnVisiable(false);
		setTittleText(this.getString(R.string.ques_repository));

		add = (Button) findViewById(R.id.add);
		add.setOnClickListener(this);

		list=getData();
		
		list_questionnaire = (SwipeListView) findViewById(R.id.list_questionnaire);
		mAdapter = new SquestionnaireAdapter(SquestionnaireList.this,list);
		mAdapter.setOnDeleteListioner(this);
		list_questionnaire.setAdapter(mAdapter);		
		list_questionnaire.setDeleteListioner(this);
		list_questionnaire.setOnItemClickListener(this);	
		
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.add:
			Intent intent = new Intent(SquestionnaireList.this,MeditQuestionnaire.class);
			intent.putExtra(Constant.ADD_OR_ITEM, true);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(SquestionnaireList.this,MeditQuestionnaire.class);
		Bundle bundle = new Bundle();
		bundle.putParcelable(Constant.QUESTIONNAIRE, list.get(arg2));
		intent.putExtra(Constant.ADD_OR_ITEM, false);
		intent.putExtras(bundle);
		startActivity(intent);	
	}		
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Constant.DELETE_RESQ) {
			if (resultCode == Constant.DELETE_RESP_CONFIRM) {
				Log.e(TAG, "index:  " + index);
				list.remove(index);
				list_questionnaire.deleteItem();
				mAdapter.notifyDataSetChanged();
				Log.e(TAG, "list.size():  " + list.size());
			} else if (resultCode == Constant.DELETE_RESP_CANSEL) {
			}
		}
	}

	@Override
	public void onDelete(int position) {
		// TODO Auto-generated method stub
		index=position;
		Log.e(TAG, "删除第  " + (index+1) +" 问卷");
		deleteQuestionnaire=list.get(position);
		Log.e(TAG, "删除  " + deleteQuestionnaire);
		Intent intent = new Intent();
		intent.setClass(SquestionnaireList.this,SdeleteQues.class);
		intent.putExtra(Constant.DELETE, deleteQuestionnaire);
		startActivityForResult(intent, Constant.DELETE_RESQ);
	}

	@Override
	public boolean isCandelete(int position) {
		// TODO Auto-generated method stub
		return true;
	}	
}
