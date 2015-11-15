package com.example.yijia.third.settlement;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.yijia.third.admin.BaseFragment;
import com.example.yijia.third.bean.admin.Staff;
import com.example.yijia.third.bean.common.SettlementSystem;
import com.example.yijia_third.R;

public class Astatistics extends BaseFragment {
	private Button finance, staff;
	private ListView list_settlement, list_settlement_staff;
	private AstatisticAdapter madapter;
	private AstaffAdapter staffAdapter;
	private ArrayList<SettlementSystem> list;
	private ArrayList<Staff> listStaff;
	private LinearLayout system_settlement, system_settlement_staff;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.a_settlement_sec, container, false);
		init();
		return view;
	}

	private ArrayList<Staff> getStaffData() {
		// TODO Auto-generated method stub
		listStaff = new ArrayList<Staff>();
		for (int i = 0; i < 10; i++) {
			Staff settlementSystem = new Staff();
			settlementSystem.setDate("190" + i + "-01-01");
			settlementSystem.setMsaNum(10 * i);
			settlementSystem.setSaNum(500 * i);
			settlementSystem.setTotalUserNum(10000 * i);
			settlementSystem.setPresentUserNum(5000 * i);
			listStaff.add(settlementSystem);
		}
		return listStaff;
	}

	private ArrayList<SettlementSystem> getData() {
		// TODO Auto-generated method stub
		list = new ArrayList<SettlementSystem>();
		for (int i = 0; i < 10; i++) {
			SettlementSystem settlementSystem = new SettlementSystem();
			settlementSystem.setSettlementTime("200" + i + "-01-01");
			settlementSystem.setTotalIncome(10000 * i);
			settlementSystem.setTotalExpend(500 * i);
			list.add(settlementSystem);
		}
		return list;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.finance) {
			setVisiable(system_settlement);
//			if (system_settlement.getVisibility() == View.GONE)
//				system_settlement.setVisibility(View.VISIBLE);
//			else
//				system_settlement.setVisibility(View.GONE);
		} else if (v.getId() == R.id.staff) {
			setVisiable(system_settlement_staff);
//			if (system_settlement_staff.getVisibility() == View.GONE)
//				system_settlement_staff.setVisibility(View.VISIBLE);
//			else
//				system_settlement_staff.setVisibility(View.GONE);
		} else if(v.getId() == R.id.back)
			fm.popBackStack();
	}

//	@Override
//	protected void setView() {
//		// TODO Auto-generated method stub
//		view = setSubView(R.layout.a_settlement_sec);
//	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		getBtn();
		
		finance = (Button) view.findViewById(R.id.finance);
		finance.setOnClickListener(this);

		staff = (Button) view.findViewById(R.id.staff);
		staff.setOnClickListener(this);

		system_settlement = (LinearLayout) view.findViewById(R.id.system_settlement);
		system_settlement_staff = (LinearLayout) view.findViewById(R.id.system_settlement_staff);

		list_settlement = (ListView) view.findViewById(R.id.list_settlement);
		madapter = new AstatisticAdapter(getActivity(), getData());
		list_settlement.setAdapter(madapter);

		list_settlement_staff = (ListView) view.findViewById(R.id.list_settlement_staff);
		staffAdapter = new AstaffAdapter(getActivity(), getStaffData());
		list_settlement_staff.setAdapter(staffAdapter);
		
//		TextView username = (TextView) view.findViewById(R.id.username);	
		username.setText(this.getString(R.string.statistics));
	}
}
