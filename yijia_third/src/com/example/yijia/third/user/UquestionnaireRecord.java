package com.example.yijia.third.user;

import java.util.ArrayList;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.base.BaseApp;
import com.example.yijia.third.bean.common.RecordBean;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

public class UquestionnaireRecord extends BaseActivity {
	private TextView date,question_first,question_second,question_third;
	private EditText answer_first,answer_second,answer_third;
	private LinearLayout second,third;
	private Button send;
	private int num;
	private int isFinished;
	private ArrayList<RecordBean> list;
	private String dateTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		setSubView(R.layout.u_uquestionnaire_record);
		
		list=getIntent().getExtras().getParcelableArrayList(Constant.RECORD_BEAN);
		isFinished=getIntent().getExtras().getInt(Constant.IS_FINISHED);
		dateTime=getIntent().getExtras().getString(Constant.SEND_TIME);
		num=list.size();		
		Log.e(TAG, "num: "+num+"    isFinished:"+isFinished+"  dateTime: "+dateTime);
		
		date=(TextView)findViewById(R.id.date);
		date.setText(dateTime);
		
		second=(LinearLayout)findViewById(R.id.second);
		third=(LinearLayout)findViewById(R.id.third);
		
		answer_first=(EditText)findViewById(R.id.answer_first);
		answer_second=(EditText)findViewById(R.id.answer_second);
		answer_third=(EditText)findViewById(R.id.answer_third);
		
		question_first=(TextView)findViewById(R.id.question_first);
		question_second=(TextView)findViewById(R.id.question_second);
		question_third=(TextView)findViewById(R.id.question_third);
		
		question_first.setText("医生"+list.get(0).getQuestionerName()+"提问： "+list.get(0).getQuestion());
		answer_first.setText(""+list.get(0).getAnswer());
				
		if(num==2){
			second.setVisibility(View.VISIBLE);
			question_second.setText("医生"+list.get(1).getQuestionerName()+"提问： "+list.get(1).getQuestion());
			answer_second.setText(list.get(1).getAnswer());		
		}else if(num==3){
			second.setVisibility(View.VISIBLE);
			third.setVisibility(View.VISIBLE);		
			question_second.setText("医生"+list.get(1).getQuestionerName()+"提问： "+list.get(1).getQuestion());
			answer_second.setText(list.get(1).getAnswer());
			question_third.setText("医生"+list.get(2).getQuestionerName()+"提问： "+list.get(2).getQuestion());
			answer_third.setText(list.get(2).getAnswer());		
		}
				
		send = (Button) findViewById(R.id.send);
		send.setOnClickListener(this);

		if (isFinished == 0) {
			answer_first.setOnClickListener(this);
			answer_second.setOnClickListener(this);
			answer_third.setOnClickListener(this);
		}else
			send.setVisibility(View.INVISIBLE);
		setEditable(false);
	}
		
	protected void setEditable(boolean value) {
		// TODO Auto-generated method stub
		setEditable(answer_first,value);
		setEditable(answer_second,value);
		setEditable(answer_third,value);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch(v.getId()){
		case R.id.back:
			finish();
			break;
		case R.id.send:	
			list=uploadData();
			setEditable(false);			
			break;
		case R.id.answer_first:
		case R.id.answer_second:
		case R.id.answer_third:
			setEditable(true);
			break;		
		}
	}

	private ArrayList<RecordBean> uploadData(){
		if(!TextUtils.isEmpty(answer_first.getText())
				||!TextUtils.isEmpty(answer_second.getText())
				||!TextUtils.isEmpty(answer_second.getText())){
			Log.e(TAG, "可以提交了");
			if((!TextUtils.isEmpty(answer_first.getText()))){
				list.get(0).setAnswer(""+answer_first.getText());	
				list.get(0).setIsAnswered(1);
			}else{
				list.get(0).setAnswer("");	
				list.get(0).setIsAnswered(0);				
			}
			if((!TextUtils.isEmpty(answer_second.getText()))){
				list.get(1).setAnswer(""+answer_second.getText());	
				list.get(1).setIsAnswered(1);				
			}else{
				list.get(1).setAnswer("");	
				list.get(1).setIsAnswered(0);							
			}
			if((!TextUtils.isEmpty(answer_third.getText()))){
				list.get(2).setAnswer(""+answer_third.getText());	
				list.get(2).setIsAnswered(1);									
			}else{
				list.get(2).setAnswer("");	
				list.get(2).setIsAnswered(0);													
			}
			for(RecordBean recordBean:list){
				recordBean.setAnswererId(((BaseApp)getApplication()).getRoleId());
				recordBean.setAnswererName(((BaseApp)getApplication()).getRoleName());
				Log.e(TAG, recordBean.toString());
			}			
		}else
			Toast.makeText(getApplicationContext(), "请填写问卷后再提交", Toast.LENGTH_SHORT).show();
		return list;
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
