package com.example.yijia.third.settlement;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.yijia.third.admin.BaseFragment;
import com.example.yijia.third.bean.common.SettlementTask;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

public class Assign extends BaseFragment {
	private Button pre_settlement, history_settlement,
			msa_sa_settlement_detail, this_settlement, settlement_date;
	private LinearLayout pre_settlement_choice;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {		
//		setView();
		view = inflater.inflate(R.layout.a_settlement_thi, container, false);
		init();
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.pre_settlement) {
			setVisiable(pre_settlement_choice);
		} else if (v.getId() == R.id.msa_sa_settlement_detail) {
			SettlementTask settlementTask=new SettlementTask();
			settlementTask.setSettlementTaskId(-1);
			Intent intent=new Intent(getActivity(),AmsaSettlementDetail.class);
			Bundle bundle=new Bundle();
			bundle.putInt(Constant.MSA_SETTLEMENT_VIEW_TYPE, 0);
			bundle.putParcelable(Constant.SETTLEMENT_TASK, settlementTask);//本期结算
			intent.putExtras(bundle);
			startActivity(intent);			
		} else if (v.getId() == R.id.this_settlement) {
//			Intent intent=new Intent(getActivity(),MsaSettlementDetail.class);
			
			SettlementTask settlementTask=new SettlementTask();
			settlementTask.setSettlementTaskId(-1);
			Intent intent=new Intent();
			intent.setClassName(getActivity(), AmsaSettlementDetail.class.getName());	
			Bundle bundle=new Bundle();
			bundle.putInt(Constant.MSA_SETTLEMENT_VIEW_TYPE, 1);//0表示查看主服务结算，1表示查看服务结算
			bundle.putParcelable(Constant.SETTLEMENT_TASK, settlementTask);
			intent.putExtras(bundle); 
			startActivity(intent);		
		} else if (v.getId() == R.id.settlement_date) {
			Intent intent = new Intent(getActivity(),AsettlementDate.class);
			startActivity(intent);
			
//			Toast.makeText(getActivity(), "暂未开放", Toast.LENGTH_SHORT).show();			
		} else if (v.getId() == R.id.history_settlement) {
			FragmentTransaction ft = fm.beginTransaction();
			AsettlementHistory settlementHistory=new AsettlementHistory();
			ft.hide(this);
			ft.add(R.id.content, settlementHistory,"SIX");
			ft.addToBackStack(null);
			ft.commit();			
		} else if(v.getId() == R.id.back)
			fm.popBackStack();	
	}

//	@Override
//	protected void setView() {
//		// TODO Auto-generated method stub
//		view = setSubView(R.layout.a_settlement_thi);
//	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		getBtn();
		
		pre_settlement = (Button) view.findViewById(R.id.pre_settlement);
		pre_settlement.setOnClickListener(this);

		msa_sa_settlement_detail = (Button) view
				.findViewById(R.id.msa_sa_settlement_detail);
		msa_sa_settlement_detail.setOnClickListener(this);

		this_settlement = (Button) view.findViewById(R.id.this_settlement);
		this_settlement.setOnClickListener(this);

		settlement_date = (Button) view.findViewById(R.id.settlement_date);
		settlement_date.setOnClickListener(this);

		history_settlement = (Button) view
				.findViewById(R.id.history_settlement);
		history_settlement.setOnClickListener(this);

		pre_settlement_choice = (LinearLayout) view
				.findViewById(R.id.pre_settlement_choice);
		
//		setTittleText(this.getString(R.string.assign));
//		TextView username = (TextView) view.findViewById(R.id.username);	
		username.setText(this.getString(R.string.assign));
	}
}
