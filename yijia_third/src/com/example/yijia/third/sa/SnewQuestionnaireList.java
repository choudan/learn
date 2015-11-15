/**
 * 
 */
package com.example.yijia.third.sa;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.base.BaseApp;
import com.example.yijia.third.bean.common.Questionnaire;
import com.example.yijia.third.bean.common.SendQuestionnaire;
import com.example.yijia.third.tool.CalendarUtils;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class SnewQuestionnaireList extends BaseActivity {
//	implements OnItemClickListener {
	
	private ArrayList<Questionnaire> list;
	private SnewQuestionnaireListAdapter mAdapter;
	private ListView list_questionnaire;
	private Button send;
	private long userId;
	private SendQuestionnaire sendQuestionnaire;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.s_new_questionnaire_list);
		
		userId=getIntent().getLongExtra(Constant.USER_ID, -1);
		Log.e(TAG, "userId�ǣ�  "+userId);
		init();
	}

	private ArrayList<Questionnaire> getData() {
		// TODO Auto-generated method stub
		list = new ArrayList<Questionnaire>();
		for (int i = 0; i < 500; i++) {
			Questionnaire mQuestionnaire = new Questionnaire();
			mQuestionnaire.setQuestionnaire("ʥ����ô���ˣ�" + i);
			mQuestionnaire.setRealName("��ʱ��");
			mQuestionnaire.setQuestionnaireId(i * 5);
			list.add(mQuestionnaire);
		}
		Log.e(TAG, "��ִ��list" + list.size());
		return list;
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		setBtnVisiable(false);
		setTittleText(this.getString(R.string.ques_repository));

		send = (Button) findViewById(R.id.send);
		send.setOnClickListener(this);

		list=getData();
		
		list_questionnaire = (ListView) findViewById(R.id.list_questionnaire);
		mAdapter = new SnewQuestionnaireListAdapter(SnewQuestionnaireList.this,list,false);//����date�ı���ֻ����name�ı�����ʾ�ʾ�
		mAdapter.setFlag(true);//��ʾcheckbox
		list_questionnaire.setAdapter(mAdapter);		
//		list_questionnaire.setOnItemClickListener(this);
		Log.e(TAG, "��ִ��");
		
	}

	public String getListSelectededItemIds() {
		String changeUserIds = "";
		long[] ids = list_questionnaire.getCheckedItemIds();
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		if (ids.length > 0 && ids.length < 4) {// һ����෢3����
			for (int i = 0; i < ids.length; i++) {
				sb.append(ids[i] + ",");
				sb1.append(list.get((int) ids[i]).getQuestionnaireId() + ",");
			}
			Log.e(TAG, "sbѡ�е�λ���ǣ�  " + sb.toString());
			Log.e(TAG, "sb1��ɵ��û�ids�ǣ�  " + sb1.toString());
			changeUserIds = sb1.toString().substring(0, sb1.length() - 1);
		} else if (ids.length == 0) {
			Toast.makeText(getApplicationContext(), "������ѡ��һ ���ʾ�",Toast.LENGTH_SHORT).show();
			return null;
		} else if (ids.length >= 4) {
			Toast.makeText(getApplicationContext(), "һ������ܷ����������ʾ�",Toast.LENGTH_SHORT).show();
			return null;
		}
		return changeUserIds;
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
		case R.id.send:
			sendQuestionnaire=uploadData();
			if(sendQuestionnaire!=null){
				Log.e(TAG, "��ʼ�ϴ�... "+sendQuestionnaire.toString());				
			}
			
			break;
		default:
			break;
		}
	}

//	@Override
//	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//		// TODO Auto-generated method stub
//	}	
	
	protected SendQuestionnaire uploadData(){
		String id=getListSelectededItemIds();
		SendQuestionnaire sendQuestionnaire=new SendQuestionnaire();
		if(id!=null){
			sendQuestionnaire.setSendTime(CalendarUtils.dateTime());
			sendQuestionnaire.setQuestionnaireIds(id);
			sendQuestionnaire.setReceiveId(userId);
			sendQuestionnaire.setUserId(((BaseApp)getApplication()).getRoleId());
			Toast.makeText(getApplicationContext(), "���ͳɹ�", Toast.LENGTH_SHORT).show();
			return sendQuestionnaire;
		}else
			return null;
	}		
}
