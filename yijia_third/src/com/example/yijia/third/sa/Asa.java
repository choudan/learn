/**
 * 
 */
package com.example.yijia.third.sa;

import java.util.ArrayList;
import java.util.Calendar;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.bean.common.SettlementSum;
import com.example.yijia.third.bean.user.User;
import com.example.yijia.third.bean.user.UserInfo;
import com.example.yijia.third.settlement.column.MsettlementColumn;
import com.example.yijia.third.tool.Constant;
import com.example.yijia.third.user.QueryUserInfo;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class Asa extends BaseActivity implements OnClickListener,
		OnItemClickListener {
	private Button sa_info, sa_team, settlement_record, back;
	private RelativeLayout sa_info_detail;
	private ListView list_user;
	private ArrayList<User> list;
	private ArrayList<String> list_name;
	private ArrayAdapter<String> mAdapter;
	private TextView username, name, sex, age, hosiptal, dept, tel,
			doctor_license, wechat_account, alipay, qq;
	private UserInfo userInfo;
	private long saId;
	private String doc_name;
	
	private LinearLayout settlement_pillars;
	private Button right_arrow, left_arrow;
	private MsettlementColumn settlementColumn;
	private SettlementSum settlementSum;
	private String year;
	private RelativeLayout pillars;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.a_query_sa);

		Bundle mBundle = getIntent().getExtras();
		saId = mBundle.getLong(Constant.SA_ID);
		doc_name = mBundle.getString(Constant.SA_NAME);		
		// 测试
		Log.e(TAG, "saId是：  " + saId + "\n name是：  " + doc_name);
		getUserInfo();
		init();
	}

	protected void init() {
		// TODO Auto-generated method stub
		setBtnVisiable(false);
		setTittleText(this.getString(R.string.query_sa));

		username = (TextView) findViewById(R.id.username);
		username.setText(doc_name);

		name = (TextView) findViewById(R.id.name);
		name.setText(doc_name);

		sex = (TextView) findViewById(R.id.sex);
		sex.setText(userInfo.getSex());

		age = (TextView) findViewById(R.id.age);
		age.setText(userInfo.getBirthday());

		hosiptal = (TextView) findViewById(R.id.hosiptal);
		hosiptal.setText(userInfo.getHospital());

		dept = (TextView) findViewById(R.id.dept);
		dept.setText(userInfo.getDept());

		tel = (TextView) findViewById(R.id.tel);
		tel.setText(userInfo.getTelephone());

		wechat_account = (TextView) findViewById(R.id.wechat_account);
		wechat_account.setText(userInfo.getWechat());

		qq = (TextView) findViewById(R.id.qq);
		qq.setText(userInfo.getQq());

		doctor_license = (TextView) findViewById(R.id.doctor_license);
		doctor_license.setText(userInfo.getLicense());

		alipay = (TextView) findViewById(R.id.alipay);
		alipay.setText(userInfo.getAlipay());

		sa_info = (Button) findViewById(R.id.sa_info);
		sa_info.setOnClickListener(this);
		sa_info.setText(doc_name + "信息");

		back = (Button) findViewById(R.id.back);
		back.setOnClickListener(this);

		sa_team = (Button) findViewById(R.id.sa_team);
		sa_team.setOnClickListener(this);

		settlement_record = (Button) findViewById(R.id.settlement_record);
		settlement_record.setOnClickListener(this);

		sa_info_detail = (RelativeLayout) findViewById(R.id.sa_info_detail);
		settlement_pillars = (LinearLayout) findViewById(R.id.settlement_pillars);
		settlement_pillars.setVisibility(View.GONE);
		
		list_user = (ListView) findViewById(R.id.list_user);
		mAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, getStringData(getUserData()));
		list_user.setAdapter(mAdapter);
		list_user.setOnItemClickListener(this);
		
		columnView();
	}

	private UserInfo getUserInfo() {
		userInfo = new UserInfo();
		userInfo.setUserId(1);
		userInfo.setRealName(doc_name);
		userInfo.setPassword("134078");
		userInfo.setSex(1);
		userInfo.setBirthday("203-07-15");
		userInfo.setHospital("皇家医院");
		userInfo.setDept("针灸科");
		userInfo.setTelephone("18744010000");
		userInfo.setEmail("18744010000@qq.com");
		userInfo.setWechat("18744010000");
		userInfo.setQq("87440100");
		userInfo.setLicense("07440100");
		userInfo.setAlipay("18744010000");
		userInfo.setAlipay("18744010000");
		userInfo.setIntroduction("神医");
		return userInfo;
	}

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
			setVisiable(sa_info_detail);
			
			break;
		case R.id.sa_team:
			setVisiable(list_user);
			
			break;
		case R.id.settlement_record:
			setVisiable(settlement_pillars);
			
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
		if (arg0.getId() == R.id.list_user) {
			Intent intent = new Intent(Asa.this, QueryUserInfo.class);
			Bundle mBundle = new Bundle();
			mBundle.putParcelable(Constant.SIMPLE_USER, list.get(arg2));
			intent.putExtras(mBundle);
			startActivity(intent);
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
	
	private void columnView(){
		pillars = (RelativeLayout) findViewById(R.id.pillars);
		
		right_arrow = (Button) findViewById(R.id.right_arrow);
		right_arrow.setOnClickListener(this);
		left_arrow = (Button) findViewById(R.id.left_arrow);
		left_arrow.setOnClickListener(this);
		
		Calendar calendar = Calendar.getInstance();
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		this.year=year;
		Log.e(TAG, "=-=-= year: " + year);

		settlementColumn = new MsettlementColumn(this);
		pillars.addView(settlementColumn);

		settlementSum = getData();// 获取数据
		Log.e(TAG, "=-=-= settlementSum: "+settlementSum.toString());
		postRefresh(settlementSum);
	}
	
	public SettlementSum getData() {
		// list=new ArrayList<SettlementSum>();
		SettlementSum settlementSum = new SettlementSum();
		settlementSum.setSettlementMonth("1,2,3,4,5,6,7,8,9,10,11,12");
		settlementSum.setTotalSum("2000,3000,4000,2000,2000,3000,2000,3000,0,2000,3000,3000");
		return settlementSum;
	}
	
	public String getYear(String yearBase, int i) {
		int ye = Integer.parseInt(yearBase);
		int result = ye + i;
		String year = String.valueOf(result);
		return year;
	}

	// 数据源必须是12个成对出现,线程里面更新
	public void postRefresh(SettlementSum settlementSum) {
		String[] month = (year + "," + settlementSum.getSettlementMonth()).split(",");
		Log.e(TAG, "=-=-= month.length: " + month.length);
		settlementColumn.updateXtittle(month);

		String[] sum = settlementSum.getTotalSum().split(",");
		Log.e(TAG, "=-=-= sum.length: " + sum.length);
		settlementColumn.updateData(sum);
	}

}
