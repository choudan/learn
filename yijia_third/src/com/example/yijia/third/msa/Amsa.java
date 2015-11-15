/**
 * 
 */
package com.example.yijia.third.msa;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.base.BaseApp;
import com.example.yijia.third.bean.common.SettlementSum;
import com.example.yijia.third.bean.msa.Msa;
import com.example.yijia.third.bean.sa.Sa;
import com.example.yijia.third.bean.user.UserInfo;
import com.example.yijia.third.sa.AquerySaAdapter;
import com.example.yijia.third.sa.Asa;
import com.example.yijia.third.settlement.column.MsettlementColumn;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class Amsa extends BaseActivity implements OnClickListener,
		OnItemClickListener {
	private Button msa_info, msa_team, settlement_record, ques_repository,
			msa_delete, next_step,back;
	private RelativeLayout msa_info_detail;
	private LinearLayout msa_delete_detail;
	private ListView list_sa;
	private AquerySaAdapter mAdapter;
	private ArrayList<Sa> list;
	private TextView name, password, sex, age, hosiptal, dept, tel, email,
			wechat_account, qq, doctor_license, alipay, introduction,
			service_type;
	private TextView msa_name,msa_team_total,msa_team_present;
	private UserInfo userInfo;
	private Msa msa;
	
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
		setSubView(R.layout.a_query_msa);	

		Bundle mBundle = getIntent().getExtras();
		msa =mBundle.getParcelable(Constant.MSA);
		// 测试
		Log.e(TAG, "传过来的msaId是： "+msa.getId());
		//数据源
		userInfo=getUserInfo();	
		init();
	}

	protected void init() {
		// TODO Auto-generated method stub	
		setBtnVisiable(false);
		setTittleText(msa.getRealName());
		setTittleText(this.getString(R.string.query_sa));
		
		name=(TextView)findViewById(R.id.name);
		name.setText(msa.getRealName());
		password=(TextView)findViewById(R.id.password);
		password.setText(userInfo.getPassword());
		sex=(TextView)findViewById(R.id.sex);
		sex.setText(userInfo.getSex());
		age=(TextView)findViewById(R.id.age);
		age.setText(userInfo.getBirthday());
		hosiptal=(TextView)findViewById(R.id.hosiptal);
		hosiptal.setText(userInfo.getHospital());
		dept=(TextView)findViewById(R.id.dept);
		dept.setText(userInfo.getDept());
		tel=(TextView)findViewById(R.id.tel);
		tel.setText(userInfo.getTelephone());
		email=(TextView)findViewById(R.id.email);
		email.setText(userInfo.getEmail());
		wechat_account=(TextView)findViewById(R.id.wechat_account);
		wechat_account.setText(userInfo.getWechat());
		qq=(TextView)findViewById(R.id.qq);
		qq.setText(userInfo.getQq());
		doctor_license=(TextView)findViewById(R.id.doctor_license);
		doctor_license.setText(userInfo.getLicense());
		alipay=(TextView)findViewById(R.id.alipay);
		alipay.setText(userInfo.getAlipay());
		introduction=(TextView)findViewById(R.id.introduction);
		introduction.setText(userInfo.getIntroduction());
		service_type=(TextView)findViewById(R.id.service_type);
		service_type.setText(msa.getBoundServiceType());
		
		msa_name=(TextView)findViewById(R.id.msa_name);
		msa_name.setText(this.getString(R.string.msa_name)+msa.getRealName());
		msa_team_total=(TextView)findViewById(R.id.msa_team_total);
		msa_team_total.setText(this.getString(R.string.total_user)+msa.getTotalNum());
		msa_team_present=(TextView)findViewById(R.id.msa_team_present);
		msa_team_present.setText(this.getString(R.string.present_user)+msa.getPresentNum());
		
		msa_info = (Button) findViewById(R.id.msa_info);
		msa_info.setOnClickListener(this);
		msa_info.setText(msa.getRealName()+"信息");		
		
		msa_team = (Button) findViewById(R.id.msa_team);
		msa_team.setOnClickListener(this);
		settlement_record = (Button) findViewById(R.id.settlement_record);
		settlement_record.setOnClickListener(this);
		ques_repository = (Button) findViewById(R.id.ques_repository);
		ques_repository.setOnClickListener(this);
		msa_delete = (Button) findViewById(R.id.msa_delete);
		msa_delete.setOnClickListener(this);
		next_step = (Button) findViewById(R.id.next_step);
		next_step.setOnClickListener(this);
		back = (Button) findViewById(R.id.back);
		back.setOnClickListener(this);
		

		msa_info_detail = (RelativeLayout) findViewById(R.id.msa_info_detail);
		settlement_pillars = (LinearLayout) findViewById(R.id.settlement_pillars);
		settlement_pillars.setVisibility(View.GONE);
		
		msa_delete_detail = (LinearLayout) findViewById(R.id.msa_delete_detail);

		list_sa = (ListView) findViewById(R.id.list_sa);
		mAdapter = new AquerySaAdapter(this, getSaData());
		list_sa.setAdapter(mAdapter);
		list_sa.setOnItemClickListener(this);
		
		columnView();
	}

	private UserInfo getUserInfo(){
		userInfo=new UserInfo();
		userInfo.setUserId(1);
		userInfo.setRealName(msa.getRealName());
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
	
	private ArrayList<Sa> getSaData() {
		// TODO Auto-generated method stub
		list = new ArrayList<Sa>();
		Sa service1 = new Sa();
		service1.setId(1);
		service1.setRealName("张三");
		service1.setPresentNum(25);
		Sa service2 = new Sa();
		service2.setId(2);
		service2.setRealName("丁柳");
		service2.setPresentNum(57);
		list.add(service1);
		list.add(service2);
		Log.e(TAG, "list.size()   " + list.size());
		return list;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.msa_info:
			setVisiable(msa_info_detail);	
			
			break;
		case R.id.msa_team:
			setVisiable(list_sa);	
			
			break;
		case R.id.settlement_record:
			setVisiable(settlement_pillars);	
			
		case R.id.ques_repository:
			Intent intent = new Intent(Amsa.this, AquestionnaireList.class);
			Bundle mbundle = new Bundle();
			mbundle.putLong(Constant.MSA_ID, msa.getId());
			mbundle.putLong(Constant.ROLE_ID,((BaseApp) getApplication()).getRoleId());
			intent.putExtras(mbundle);
			startActivity(intent);
			break;
		case R.id.msa_delete:
			setVisiable(msa_delete_detail);	
			
			break;
		case R.id.next_step:
			Log.e(TAG, "下一步传输的msaId是： "+msa.getId());
			Intent intent1 = new Intent(Amsa.this, AdeleteMsaActivity.class);
			Bundle mbundle1 = new Bundle();
			mbundle1.putLong(Constant.MSA_ID, msa.getId());
			mbundle1.putLong(Constant.ROLE_ID,((BaseApp) getApplication()).getRoleId());
			intent1.putExtras(mbundle1);
			startActivity(intent1);
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
		if (arg0.getId() == R.id.list_sa) {
			Intent intent = new Intent(Amsa.this, Asa.class);
			Bundle mBundle = new Bundle();
			mBundle.putString(Constant.SA_NAME, list.get(arg2).getRealName());
			mBundle.putLong(Constant.SA_ID, list.get(arg2).getId());
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
