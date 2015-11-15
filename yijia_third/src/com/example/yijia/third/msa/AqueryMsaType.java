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
import android.widget.Toast;

import com.example.yijia.third.admin.BaseFragment;
import com.example.yijia.third.bean.common.Service;
import com.example.yijia.third.service.ServiceDetail;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class AqueryMsaType extends BaseFragment implements OnItemClickListener{
	private ListView list_service;
	private AqueryMsaTypeAdapter mAdapter;
	private ArrayList<Service> list;
	private boolean queryType;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {		
		queryType=getArguments().getBoolean(Constant.QUERY_TYPE);
		Log.e("=-=-=queryType的值是：=-=-=", ""+queryType);
		Toast.makeText(getActivity(), ""+queryType, Toast.LENGTH_SHORT).show();
				
		view = inflater.inflate(R.layout.a_query_service_list, container, false);
		init();
		return view;
	}	

	private ArrayList<Service> getData() {
		// TODO Auto-generated method stub
		list = new ArrayList<Service>();
		Service service1 = new Service();
		service1.setServiceId(1);
		service1.setPrice(600);
		service1.setServiceName("医生咨询");
		service1.setTypes("1");
		service1.setSaBouns(300);
		service1.setMsaBonus(60);
		service1.setAdminBouns(240);
		service1.setIntroduction("华佗，名医");
		Service service2 = new Service();
		service2.setServiceId(2);
		service2.setPrice(1200);
		service2.setServiceName("专家咨询");
		service2.setTypes("2");
		service2.setSaBouns(300);
		service2.setMsaBonus(60);
		service2.setAdminBouns(240);
		service2.setIntroduction("专家，专家");
		list.add(service1);
		list.add(service2);
 		return list;
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
 
		FragmentManager fm=getFragmentManager();
		FragmentTransaction ft=fm.beginTransaction();
		Bundle bundle=new Bundle();
		if(queryType){
			AqueryMsaList queryMsaList=new AqueryMsaList();
			bundle.putLong(Constant.SERVICE_ID, list.get(arg2).getServiceId());
			queryMsaList.setArguments(bundle);		
			ft.add(R.id.content, queryMsaList,"FOUR");
		}else{
			bundle.putBoolean(Constant.EDITABLE, false);
			bundle.putParcelable(Constant.SERVICE_BEAN, list.get(arg2));
			Intent intent=new Intent(getActivity(),ServiceDetail.class);
			intent.putExtras(bundle);
			startActivity(intent);
		}
		ft.addToBackStack(null);
		ft.commit();
	}

	protected void init() {
		// TODO Auto-generated method stub
		getBtn();
		
		list_service = (ListView) view.findViewById(R.id.list_service);
		mAdapter = new AqueryMsaTypeAdapter(getActivity(),getData());
		list_service.setAdapter(mAdapter);
		list_service.setOnItemClickListener(this);	
		
		username.setText(this.getString(R.string.service_type_list));
	}
}
