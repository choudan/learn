/**
 * 
 */
package com.example.yijia.third.sa;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.bean.user.User;
import com.example.yijia.third.settlement.column.MSsettlement;
import com.example.yijia.third.tool.Constant;
import com.example.yijia.third.user.QueryUserInfo;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class Ssa extends BaseActivity implements OnClickListener,
		OnItemClickListener {
	private Button sa_info, sa_team, settlement_record,ques_repository,answered,unanswered;
	private LinearLayout list_user;
	private ListView list_answered,list_unanswered;
	private ArrayList<User> list;
	private ArrayList<String> list_name;
	private ArrayAdapter<String> mAdapter;
//	private LinearLayout settlement_pillars;
//	private RelativeLayout pillars;
//	private Button right_arrow, left_arrow;
//	private MsettlementColumn settlementColumn;
//	private SettlementSum settlementSum;
//	private String year;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.s_common_sa);
		init();
	}

	protected void init() {
		// TODO Auto-generated method stub
		setBtnVisiable(false);
		setTittleText(this.getString(R.string.fullname));			
				
		sa_info = (Button) findViewById(R.id.sa_info);
		sa_info.setOnClickListener(this);
			
		sa_team = (Button) findViewById(R.id.sa_team);
		sa_team.setOnClickListener(this);
		
		ques_repository = (Button) findViewById(R.id.ques_repository);
		ques_repository.setOnClickListener(this);
		
		settlement_record = (Button) findViewById(R.id.settlement_record);
		settlement_record.setOnClickListener(this);
		
		answered = (Button) findViewById(R.id.answered);
		answered.setOnClickListener(this);
		
		unanswered = (Button) findViewById(R.id.unanswered);
		unanswered.setOnClickListener(this);

		list_user = (LinearLayout) findViewById(R.id.list_user);
//		settlement_pillars = (LinearLayout) findViewById(R.id.settlement_pillars);

		list_answered = (ListView) findViewById(R.id.list_answered);
		mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, getStringData(getUserData()));
		list_answered.setAdapter(mAdapter);
		list_answered.setOnItemClickListener(this);
		
		list_unanswered = (ListView) findViewById(R.id.list_unanswered);
		mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, getStringData(getUserData()));
		list_unanswered.setAdapter(mAdapter);
		list_unanswered.setOnItemClickListener(this);
		
//		columnView();
	}
	
//	private void columnView(){
//		pillars = (RelativeLayout) findViewById(R.id.pillars);
//		
//		right_arrow = (Button) findViewById(R.id.right_arrow);
//		right_arrow.setOnClickListener(this);
//		left_arrow = (Button) findViewById(R.id.left_arrow);
//		left_arrow.setOnClickListener(this);
//		
//		Calendar calendar = Calendar.getInstance();
//		String year = String.valueOf(calendar.get(Calendar.YEAR));
//		this.year=year;
//		Log.e(TAG, "=-=-= year: " + year);
//
//		settlementColumn = new MsettlementColumn(this);
//		pillars.addView(settlementColumn);
//
//		settlementSum = getData();// 获取数据
//		Log.e(TAG, "=-=-= settlementSum: "+settlementSum.toString());
//		postRefresh(settlementSum);
//	}
	
	
	private ArrayList<User> getUserData() {
		// TODO Auto-generated method stub
		list = new ArrayList<User>();
		User service1 = new User();
		service1.setUserId(1);
		service1.setRealName("张三");
		User service2 = new User();
		service2.setUserId(2);
		service2.setRealName("丁柳");
		list.add(service1);
		list.add(service2);
		Log.e(TAG, "list.size()   " + list.size());
		return list;
	}

	private ArrayList<String> getStringData(ArrayList<User> list) {
		// TODO Auto-generated method stub
		list_name = new ArrayList<String>();
		for (User simpleUser : list)
			list_name.add(simpleUser.getRealName());
		Log.e(TAG, "list.size()   " + list.size());
		return list_name;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.sa_info:
			Intent intent=new Intent(Ssa.this,SsaInfo.class);
			startActivity(intent);
			
			break;
		case R.id.sa_team:
			setVisiable(list_user);	
			
			break;
		case R.id.settlement_record:		
			Intent intent0 = new Intent(Ssa.this,MSsettlement.class);
			startActivity(intent0);
				
			break;
		case R.id.ques_repository:
			Intent intent1=new Intent(Ssa.this,SquestionnaireList.class);
			startActivity(intent1);
						
			break;
		case R.id.answered:
			setVisiable(list_answered);	
			
			break;
		case R.id.unanswered: 
			setVisiable(list_unanswered);
			
			break;			
		case R.id.back:
			finish();
			
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Ssa.this, QueryUserInfo.class);
		Bundle mBundle = new Bundle();
		mBundle.putParcelable(Constant.SIMPLE_USER, list.get(arg2));
		intent.putExtras(mBundle);
		startActivity(intent);
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
