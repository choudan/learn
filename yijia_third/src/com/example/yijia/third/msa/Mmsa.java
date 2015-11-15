/**
 * 
 */
package com.example.yijia.third.msa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.base.BaseApp;
import com.example.yijia.third.settlement.column.MSsettlement;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class Mmsa extends BaseActivity implements OnClickListener {
	private Button info, add_sa, query_sa, assign_user,
			settlement_record, ques_repository;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.m_common_msa);	
		init();
	}

	protected void init() {
		// TODO Auto-generated method stub	
		setBtnVisiable(false);
		setTittleText("姓名");//从app中获取姓名
		
		info=(Button)findViewById(R.id.info);
		info.setOnClickListener(this);
		add_sa=(Button)findViewById(R.id.add_sa);
		add_sa.setOnClickListener(this);
		query_sa=(Button)findViewById(R.id.query_sa);
		query_sa.setOnClickListener(this);
		assign_user=(Button)findViewById(R.id.assign_user);
		assign_user.setOnClickListener(this);
		settlement_record=(Button)findViewById(R.id.settlement_record);
		settlement_record.setOnClickListener(this);
		ques_repository=(Button)findViewById(R.id.ques_repository);
		ques_repository.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		switch (v.getId()) {		
		case R.id.info:
			intent.setClass(getApplicationContext(), MmsaInfo.class);
			startActivity(intent);
			
			break;
		case R.id.add_sa:
			intent.setClass(getApplicationContext(), MaddSaInfo.class);
			startActivity(intent);
		
			break;
		case R.id.query_sa:
			intent.setClass(getApplicationContext(), MquerySaList.class);
//			intent.putExtra(Constant.ASSIGN_OR_QUERY, false);//区分分配还是查看，进入到该页面。false是查看
			startActivity(intent);
		
			break;
		case R.id.assign_user:
			intent.setClass(getApplicationContext(), MassignState.class);
			startActivity(intent);
			
			break;
		case R.id.settlement_record:
			intent.setClass(getApplicationContext(),MSsettlement.class);
			startActivity(intent);
						
			break;
		case R.id.ques_repository:
			intent.setClass(getApplicationContext(), MquestionnaireSwipeList.class);
			Bundle mbundle = new Bundle();
			mbundle.putLong(Constant.MSA_ID, ((BaseApp) getApplication()).getRoleId());
			mbundle.putLong(Constant.ROLE_ID,((BaseApp) getApplication()).getRoleId());
			intent.putExtras(mbundle);
			startActivity(intent);
			break;
		case R.id.back:
			finish();
			break;
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
