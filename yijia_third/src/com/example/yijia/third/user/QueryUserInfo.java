/**
 * 
 */
package com.example.yijia.third.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.base.BaseApp;
import com.example.yijia.third.bean.user.DeviceInfo;
import com.example.yijia.third.bean.user.User;
import com.example.yijia.third.sa.SnewQuestionnaireList;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class QueryUserInfo extends BaseActivity implements OnClickListener {
	private Button user_info, questionnaire_record, communication, waveform,
			dev_info, back,new_questionnaire,questionnaire_history;
	private LinearLayout user_info_detail, dev_info_detail;
	private ListView upload_record;
	private ArrayAdapter<String> mAdapter;
	private TextView username,activate_time,service_deadline,avg_time;
	private User user;
	private DeviceInfo deviceInfo;
	private LinearLayout questionnaire_sa;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.a_query_user);

		user = getIntent().getExtras().getParcelable(Constant.SIMPLE_USER);
		// ≤‚ ‘
		Log.e(TAG, "userid «£∫  " + user.getUserId());
		init();
	}

	protected void init() {
		// TODO Auto-generated method stub
		setBtnVisiable(false);
		setTittleText(user.getRealName());
		
		user_info = (Button) findViewById(R.id.user_info);
		user_info.setOnClickListener(this);
		questionnaire_record = (Button) findViewById(R.id.questionnaire_record);
		questionnaire_record.setOnClickListener(this);
		communication = (Button) findViewById(R.id.communication);
		communication.setOnClickListener(this);
		waveform = (Button) findViewById(R.id.waveform);
		waveform.setOnClickListener(this);
		dev_info = (Button) findViewById(R.id.dev_info);
		dev_info.setOnClickListener(this);
		back = (Button) findViewById(R.id.back);
		back.setOnClickListener(this);	
		new_questionnaire = (Button) findViewById(R.id.new_questionnaire);
		new_questionnaire.setOnClickListener(this);	
		questionnaire_history = (Button) findViewById(R.id.questionnaire_history);
		questionnaire_history.setOnClickListener(this);	
		
		username = (TextView) findViewById(R.id.username);
		username.setText(user.getRealName());
		
		activate_time = (TextView) findViewById(R.id.activate_time);
		service_deadline = (TextView) findViewById(R.id.service_deadline);
		avg_time = (TextView) findViewById(R.id.avg_time);

		upload_record = (ListView) findViewById(R.id.upload_record);
		mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, getUseRecord());
		upload_record.setAdapter(mAdapter);
		
		activate_time.setText(deviceInfo.getStartTime());
		service_deadline.setText(deviceInfo.getServiceDeadline());
		avg_time.setText(deviceInfo.getMonthAvg()+"¥Œ/‘¬");
		
		user_info_detail = (LinearLayout) findViewById(R.id.user_info_detail);
		dev_info_detail = (LinearLayout) findViewById(R.id.dev_info_detail);
		questionnaire_sa = (LinearLayout) findViewById(R.id.questionnaire_sa);
	}

	private String[] getUseRecord() {
		// TODO Auto-generated method stub
		return getData().getUseRecord().split(",");
	}
	
	private DeviceInfo getData(){
		deviceInfo = new DeviceInfo();
		deviceInfo.setMonthAvg(5);
		deviceInfo.setStartTime("2014-08-20");
		deviceInfo.setServiceDeadline("2015-08-20");	
		deviceInfo.setUseRecord("2014-08-21,201-08-25,2014-08-27,2014-08-29,2015-01-01,2015-02-01");
		return deviceInfo;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.user_info:
			setVisiable(user_info_detail);
			
			break;
		case R.id.questionnaire_record:
			if(((BaseApp)getApplication()).getRole()==3)
				setVisiable(questionnaire_sa);
			else{
				Intent intent1 = new Intent(QueryUserInfo.this,QuestionnaireRecord.class);
				startActivity(intent1);			
			}
			break;
		case R.id.communication:
			Intent intent2 = new Intent(QueryUserInfo.this,UScommunication.class);
			startActivity(intent2);
			break;

		case R.id.waveform:
			Intent intent3 = new Intent(QueryUserInfo.this, Waveform.class);
			startActivity(intent3);

			break;
		case R.id.dev_info:
			setVisiable(dev_info_detail);
			
			break;
		case R.id.new_questionnaire:
			Intent intent4 = new Intent(QueryUserInfo.this,SnewQuestionnaireList.class);
			intent4.putExtra(Constant.USER_ID, user.getUserId());
			startActivity(intent4);			
			Log.e(TAG, "user.getUserId()£∫  "+user.getUserId());
			
			break;
		case R.id.questionnaire_history:
			Intent intent1 = new Intent(QueryUserInfo.this,QuestionnaireRecord.class);
			startActivity(intent1);			
			
			break;
		case R.id.back:
			finish();
		default:
			break;
		}
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
