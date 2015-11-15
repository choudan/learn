package com.example.yijia.third.user;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.base.BaseApp;
import com.example.yijia.third.bean.common.OrderAlterNotify;
import com.example.yijia.third.bean.common.Record;
import com.example.yijia.third.bean.common.RecordBean;
import com.example.yijia.third.bean.user.DeviceInfo;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

public class Uuser extends BaseActivity implements OnItemClickListener{
	private Button info,questionnaire,communication,waveform,dev_info,oeder_check,direction_for_use;
	private LinearLayout dev_info_detail;
	private TextView questionnaire_num,oeder_check_num,activate_time,service_deadline,avg_time;
	private int orderState,unfinishedSurvey;
	private ListView list_questionnaire,upload_record;
	private UboldRecordAdapter adapter;
	private ArrayList<Record> list;
	private ArrayAdapter<String> mAdapter;
	private DeviceInfo deviceInfo;
	private OrderAlterNotify orderAlter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		trtStatus();
		
		setSubView(R.layout.u_common_user);
		
		String name= ((BaseApp)getApplication()).getRoleName();				
		Log.e(TAG, "myapp中的name是：  "+name);
		if(name!=null)
			setTittleText(name);
		
		unfinishedSurvey=getIntent().getIntExtra(Constant.UNFINISHED_SURVEY, -1);
		orderState=getIntent().getIntExtra(Constant.ORDER_STATE, -1);
		
		questionnaire_num=(TextView)findViewById(R.id.questionnaire_num);
		oeder_check_num=(TextView)findViewById(R.id.oeder_check_num);
		
		if(orderState>0){
			orderAlter=geOrderAltertData();//联网查询变动内容
			if(orderAlter.getType()==0){//表示服务变更
				Intent intent = new Intent();
				intent.setClass(Uuser.this, UuserServiceAlter.class);
				intent.putExtra(Constant.ORDER_ALTER, orderAlter);	
				startActivity(intent);
			}else{
				oeder_check_num.setVisibility(View.VISIBLE);
				oeder_check_num.setText(""+orderState);							
			}
		}
			
		if(unfinishedSurvey>0){
			questionnaire_num.setVisibility(View.VISIBLE);
			questionnaire_num.setText(""+unfinishedSurvey);
		}
		
		list=geRecordtData();
						
		info=(Button)findViewById(R.id.info);
		info.setOnClickListener(this);
		questionnaire=(Button)findViewById(R.id.questionnaire);
		questionnaire.setOnClickListener(this);
		communication=(Button)findViewById(R.id.communication);
		communication.setOnClickListener(this);
		waveform=(Button)findViewById(R.id.waveform);
		waveform.setOnClickListener(this);
		dev_info=(Button)findViewById(R.id.dev_info);
		dev_info.setOnClickListener(this);
		oeder_check=(Button)findViewById(R.id.oeder_check);
		oeder_check.setOnClickListener(this);
		direction_for_use=(Button)findViewById(R.id.direction_for_use);
		direction_for_use.setOnClickListener(this);	
		
		list_questionnaire=(ListView)findViewById(R.id.list_questionnaire);
		adapter=new UboldRecordAdapter(this,list);
		list_questionnaire.setAdapter(adapter);
		list_questionnaire.setOnItemClickListener(this);
		
		dev_info_detail=(LinearLayout)findViewById(R.id.dev_info_detail);
		
		activate_time = (TextView) findViewById(R.id.activate_time);
		service_deadline = (TextView) findViewById(R.id.service_deadline);
		avg_time = (TextView) findViewById(R.id.avg_time);

		upload_record = (ListView) findViewById(R.id.upload_record);
		mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, getUseRecord());
		upload_record.setAdapter(mAdapter);
		
		activate_time.setText(deviceInfo.getStartTime());
		service_deadline.setText(deviceInfo.getServiceDeadline());
		avg_time.setText(deviceInfo.getMonthAvg()+"次/月");
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
	
	private ArrayList<Record> geRecordtData() {
		// TODO Auto-generated method stub		
		list=new ArrayList<Record>();
		for(int i=0;i<10;i++){
			Record record=new Record();
			record.setIsFinished(0);
			ArrayList<RecordBean> listRecordBean=new ArrayList<RecordBean>();
			for(int j=0;j<3;j++){
				RecordBean recordBean=new RecordBean();
				recordBean.setAnswer("");
				recordBean.setAnswererName("");
				recordBean.setQuestion("身体如何？");
				recordBean.setQuestionerName("李瑾");	
				recordBean.setQuestionnaireId(j);
				recordBean.setQuestionerId(j);
				listRecordBean.add(recordBean);
			}
			record.setList(listRecordBean);
			record.setRecordId(i);
			record.setSendTime("2015-08-"+i);
			list.add(record);
		}
		for(int i=0;i<50;i++){
			Record record=new Record();
			record.setIsFinished(1);
			ArrayList<RecordBean> listRecordBean=new ArrayList<RecordBean>();
			for(int j=0;j<3;j++){
				RecordBean recordBean=new RecordBean();
				recordBean.setAnswer("好");
				recordBean.setAnswererName("峰仔");
				recordBean.setQuestion("睡觉怎么样？");
				recordBean.setQuestionerName("李瑾");
				recordBean.setQuestionerId(j);
				listRecordBean.add(recordBean);
			}
			record.setList(listRecordBean);
			record.setRecordId(i);
			record.setSendTime("2015-07-"+i);
			list.add(record);
		}
		return list;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		super.onClick(v);
		switch(v.getId()){
		case R.id.info:
			intent.setClass(getApplicationContext(), UuserInfo.class);
			startActivity(intent);
			
			break;
		case R.id.questionnaire:
			setVisiable(list_questionnaire);
			questionnaire_num.setVisibility(View.INVISIBLE);
			
			break;
		case R.id.communication:
			intent.setClass(getApplicationContext(), UScommunication.class);
			startActivity(intent);
			
			break;
		case R.id.waveform:
			intent.setClass(getApplicationContext(), Waveform.class);
			startActivity(intent);
			
			break;
		case R.id.dev_info:
			setVisiable(dev_info_detail);
			
			break;
		case R.id.oeder_check:
			oeder_check_num.setVisibility(View.INVISIBLE);
			intent.setClass(getApplicationContext(), UuserOrderList.class);
			startActivity(intent);		
			
			break;
		case R.id.direction_for_use:
			
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Log.e(TAG, "点击的是第 "+arg2+" 条问卷记录");
		Intent intent = new Intent(Uuser.this,UquestionnaireRecord.class);
		Bundle bundle=new Bundle();
		bundle.putInt(Constant.IS_FINISHED, list.get(arg2).getIsFinished());
		bundle.putParcelableArrayList(Constant.RECORD_BEAN, list.get(arg2).getList());
		bundle.putString(Constant.SEND_TIME, list.get(arg2).getSendTime());
		startActivity(intent.putExtras(bundle));
	}
	
	private OrderAlterNotify geOrderAltertData(){
		orderAlter=new OrderAlterNotify();
		orderAlter.setOrderId(1);
		orderAlter.setDelayMonth(2);
		orderAlter.setDelayReason("就是想送服不服就是想送服不服就是想送服不服就是想送服不服就是想送服不服就是想送服不服就是想送服不服");
		orderAlter.setDelayTakeEffect("2015-09-30");
		orderAlter.setType(0);//服务变更
		return orderAlter;
	}

}
