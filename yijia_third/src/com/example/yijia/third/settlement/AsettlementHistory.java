package com.example.yijia.third.settlement;

import java.util.ArrayList;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.yijia.third.admin.BaseFragment;
import com.example.yijia.third.bean.common.SettlementTask;
import com.example.yijia.third.inst.IsettlementDetail;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

public class AsettlementHistory extends BaseFragment implements OnItemClickListener {
//	private View view;
	private ListView list_msa_settlement_date, list_inst_settlement_date;
	private ArrayAdapter<String> mAdapter,adapter;
	private ArrayList<SettlementTask> list;
	private ArrayList<String> listDate;
	private Button msa_settlement,inst_settlement;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		setView();
		view = inflater.inflate(R.layout.a_settlement_fou, container, false);
		init();
		return view;
	}

	private ArrayList<String> getInstData() {
		// TODO Auto-generated method stub
		list=getData();
		listDate = new ArrayList<String>();
		for (SettlementTask settlementTask : list)
			listDate.add(settlementTask.getSettlementTime());
		return listDate;
	}

	private ArrayList<SettlementTask> getData() {
		// TODO Auto-generated method stub
		ArrayList<SettlementTask> list = new ArrayList<SettlementTask>();
		for (int i = 0; i < 10; i++) {
			SettlementTask settlementSystem = new SettlementTask();
			settlementSystem.setSettlementTime("200" + i + "-01-01");
			settlementSystem.setSettlementTaskId(i);
			list.add(settlementSystem);
		}
		return list;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
			long arg3) {
		Log.e("≤‚ ‘", "-2");
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();
		bundle.putParcelable(Constant.SETTLEMENT_TASK, list.get(arg2));
		if (arg0.getId() == R.id.list_msa_settlement_date) {
			Log.e("≤‚ ‘", "-1");
			bundle.putInt(Constant.MSA_SETTLEMENT_VIEW_TYPE, 0);
			Intent intent = new Intent(getActivity(),AmsaSettlementDetail.class);
			intent.putExtras(bundle);		
			startActivity(intent);		
		} else if (arg0.getId() == R.id.list_inst_settlement_date) {
			Intent intent = new Intent(getActivity(),IsettlementDetail.class);
			intent.putExtras(bundle);
			startActivity(intent);
		}
			FragmentTransaction ft=fm.beginTransaction();
			ft.addToBackStack(null);
			ft.commit();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.msa_settlement){
			setVisiable(list_msa_settlement_date);
//			if(list_msa_settlement_date.getVisibility()==View.GONE)
//				list_msa_settlement_date.setVisibility(View.VISIBLE);
//			else
//				list_msa_settlement_date.setVisibility(View.GONE);			
		}else if(v.getId()==R.id.inst_settlement){
			setVisiable(list_inst_settlement_date);
//			if(list_inst_settlement_date.getVisibility()==View.GONE)
//				list_inst_settlement_date.setVisibility(View.VISIBLE);
//			else
//				list_inst_settlement_date.setVisibility(View.GONE);						
		}else if(v.getId()==R.id.back)
			fm.popBackStack();
	}

//	@Override
//	protected void setView() {
//		// TODO Auto-generated method stub
//		setSubView(R.layout.a_settlement_fou);
//	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		getBtn();
		
		msa_settlement=(Button)view.findViewById(R.id.msa_settlement);
		msa_settlement.setOnClickListener(this);
		
		inst_settlement=(Button)view.findViewById(R.id.inst_settlement);
		inst_settlement.setOnClickListener(this);		
		
		list_msa_settlement_date = (ListView) view.findViewById(R.id.list_msa_settlement_date);
		adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_expandable_list_item_1,  getInstData());
		list_msa_settlement_date.setAdapter(adapter);
		list_msa_settlement_date.setOnItemClickListener(this);

		list_inst_settlement_date = (ListView) view.findViewById(R.id.list_inst_settlement_date);
		mAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_expandable_list_item_1, getInstData());
		list_inst_settlement_date.setAdapter(mAdapter);
		list_inst_settlement_date.setOnItemClickListener(this);
		
//		setTittleText(this.getString(R.string.history_settlement));
//		TextView username = (TextView) view.findViewById(R.id.username);	
		username.setText(this.getString(R.string.history_settlement));
	}
}
