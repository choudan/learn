/**
 * 
 */
package com.example.yijia.third.inst;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.bean.common.Service;
import com.example.yijia.third.msa.AqueryMsaTypeAdapter;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class Iinst extends BaseActivity implements OnClickListener,OnItemClickListener {
	private Button inst_info, msa_sa,user,code,user_statistics;
	private ListView msa_sa_choice;
	private AqueryMsaTypeAdapter mAdapter;
	private ArrayList<Service> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.i_common_inst);

//		user = getIntent().getExtras().getParcelable(Constant.SIMPLE_USER);
//		// 测试
//		Log.e(TAG, "userid是：  " + user.getUserId());
		
		init();
	}

	protected void init() {
		// TODO Auto-generated method stub
		setBtnVisiable(false);
		setTittleText("");//标题
		
		inst_info = (Button) findViewById(R.id.inst_info);
		inst_info.setOnClickListener(this);
		msa_sa = (Button) findViewById(R.id.msa_sa);
		msa_sa.setOnClickListener(this);
 
		user = (Button) findViewById(R.id.user);
		user.setOnClickListener(this);
		code = (Button) findViewById(R.id.code);
		code.setOnClickListener(this);	
		user_statistics = (Button) findViewById(R.id.user_statistics);
		user_statistics.setOnClickListener(this);	
		
		msa_sa_choice=(ListView)findViewById(R.id.msa_sa_choice);	
		mAdapter = new AqueryMsaTypeAdapter(this, getData());
		msa_sa_choice.setAdapter(mAdapter);
		msa_sa_choice.setOnItemClickListener(this);
	}

	private ArrayList<Service> getData() {
		// TODO Auto-generated method stub
		list = new ArrayList<Service>();
		Service service1 = new Service();
		service1.setServiceId(1);
		service1.setPrice(600);
		service1.setServiceName("医生咨询");
		service1.setTypes("机构");
		service1.setSaBouns(300);
		service1.setMsaBonus(60);
		service1.setAdminBouns(240);
		service1.setIntroduction("华佗，名医");
		Service service2 = new Service();
		service2.setServiceId(2);
		service2.setPrice(1200);
		service2.setServiceName("专家咨询");
		service2.setTypes("个人");
		service2.setSaBouns(300);
		service2.setMsaBonus(60);
		service2.setAdminBouns(240);
		service2.setIntroduction("专家，专家");
		list.add(service1);
		list.add(service2);
		Log.e(TAG, "list.size()   "+list.size());
		return list;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		switch (v.getId()) {
		case R.id.inst_info:
			intent.setClass(getApplicationContext(), IqueryInstInfo.class);
			startActivity(intent);
			break;
		case R.id.msa_sa:
			setVisiable(msa_sa_choice);
			break;
		case R.id.user:
			intent.setClass(getApplicationContext(), IqueryUserList.class);
			startActivity(intent);						
			break;
		case R.id.code:
			intent.setClass(getApplicationContext(), IqueryCodeStateList.class);
			startActivity(intent);								
			break;
		case R.id.user_statistics:
			intent.setClass(getApplicationContext(), IqueryUserStatistics.class);
			startActivity(intent);								
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, IqueryDocExpList.class);
		Bundle bundle = new Bundle();
		bundle.putLong(Constant.SERVICE_ID, list.get(arg2).getServiceId());
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
