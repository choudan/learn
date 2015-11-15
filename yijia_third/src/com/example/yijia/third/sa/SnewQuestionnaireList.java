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
		Log.e(TAG, "userId是：  "+userId);
		init();
	}

	private ArrayList<Questionnaire> getData() {
		// TODO Auto-generated method stub
		list = new ArrayList<Questionnaire>();
		for (int i = 0; i < 500; i++) {
			Questionnaire mQuestionnaire = new Questionnaire();
			mQuestionnaire.setQuestionnaire("圣体怎么样了？" + i);
			mQuestionnaire.setRealName("李时珍");
			mQuestionnaire.setQuestionnaireId(i * 5);
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

		send = (Button) findViewById(R.id.send);
		send.setOnClickListener(this);

		list=getData();
		
		list_questionnaire = (ListView) findViewById(R.id.list_questionnaire);
		mAdapter = new SnewQuestionnaireListAdapter(SnewQuestionnaireList.this,list,false);//隐藏date文本框，只留下name文本框显示问卷
		mAdapter.setFlag(true);//显示checkbox
		list_questionnaire.setAdapter(mAdapter);		
//		list_questionnaire.setOnItemClickListener(this);
		Log.e(TAG, "已执行");
		
	}

	public String getListSelectededItemIds() {
		String changeUserIds = "";
		long[] ids = list_questionnaire.getCheckedItemIds();
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		if (ids.length > 0 && ids.length < 4) {// 一次最多发3道题
			for (int i = 0; i < ids.length; i++) {
				sb.append(ids[i] + ",");
				sb1.append(list.get((int) ids[i]).getQuestionnaireId() + ",");
			}
			Log.e(TAG, "sb选中的位置是：  " + sb.toString());
			Log.e(TAG, "sb1组成的用户ids是：  " + sb1.toString());
			changeUserIds = sb1.toString().substring(0, sb1.length() - 1);
		} else if (ids.length == 0) {
			Toast.makeText(getApplicationContext(), "请至少选择一 个问卷",Toast.LENGTH_SHORT).show();
			return null;
		} else if (ids.length >= 4) {
			Toast.makeText(getApplicationContext(), "一次最多能发送三条个问卷",Toast.LENGTH_SHORT).show();
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
				Log.e(TAG, "开始上传... "+sendQuestionnaire.toString());				
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
			Toast.makeText(getApplicationContext(), "发送成功", Toast.LENGTH_SHORT).show();
			return sendQuestionnaire;
		}else
			return null;
	}		
}
