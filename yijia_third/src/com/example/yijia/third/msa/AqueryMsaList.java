package com.example.yijia.third.msa;

import java.util.ArrayList;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.yijia.third.admin.BaseFragment;
import com.example.yijia.third.bean.msa.Msa;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class AqueryMsaList extends BaseFragment implements OnItemClickListener {
	private ListView list_service;
	private AqueryMsaAdapter mAdapter;
	private ArrayList<Msa> list;
	private final static String TAG = AqueryMsaList.class.getName();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		long serviceId = getArguments().getLong(Constant.SERVICE_ID);
		// 测试
		Log.e(TAG, "传过来的serviceId是：  " + serviceId);
		view = inflater.inflate(R.layout.a_query_service_list, container, false);
//		setView();
		init();
		return view;
	}

	private ArrayList<Msa> getData() {
		// TODO Auto-generated method stub
		list = new ArrayList<Msa>();
		Msa msaSa = new Msa();
		msaSa.setId(1);
		msaSa.setRealName("李时珍");
		msaSa.setHospitalDept("909 外科");
		msaSa.setPresentNum(300);
		msaSa.setTotalNum(500);
		msaSa.setBoundServiceType("专家咨询");
		Msa msaSa1 = new Msa();
		msaSa1.setId(2);
		msaSa1.setRealName("孙思邈");
		msaSa1.setHospitalDept("301 内科");
		msaSa1.setPresentNum(2670);
		msaSa1.setTotalNum(530);
		msaSa1.setBoundServiceType("专家咨询");
		list.add(msaSa);
		list.add(msaSa1);
		Log.e(TAG, "list.size()   " + list.size());
		return list;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		// if (getActivity() instanceof OnListItemClickListener) {
		// ((OnListItemClickListener)
		// getActivity()).onListItemClickListener(arg0, arg1, arg2, arg3);
		// }
		Intent intent = new Intent(getActivity(), Amsa.class);
		Bundle bundle = new Bundle();
		bundle.putParcelable(Constant.MSA, list.get(arg2));
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.addToBackStack(null);
		ft.commit();
		Log.e(TAG, "QueryMsaList 已执行");
	}

//	@Override
//	protected void setView() {
//		// TODO Auto-generated method stub
//		view = setSubView(R.layout.a_query_service_list);
//	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		getBtn();
		
		list_service = (ListView) view.findViewById(R.id.list_service);
		mAdapter = new AqueryMsaAdapter(getActivity(), getData());
		list_service.setAdapter(mAdapter);
		list_service.setOnItemClickListener(this);
		
//		TextView username = (TextView) view.findViewById(R.id.username);	
		username.setText(this.getString(R.string.msa_list));
	}

}
