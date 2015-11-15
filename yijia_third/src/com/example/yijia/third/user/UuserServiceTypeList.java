package com.example.yijia.third.user;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.example.yijia.third.base.RegisterBaseActivity;
import com.example.yijia.third.bean.common.Order;
import com.example.yijia.third.bean.common.Service;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

public class UuserServiceTypeList extends RegisterBaseActivity implements OnItemClickListener{
	private ListView list_service_type;
	private UuserServiceTypeAdapter madapter;
	private int position;
	private Button next_step;
	private ArrayList<Service> list;
	private Order order;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		trtStatus();
		
		setSubView(R.layout.u_user_service_type_list,false);
		
		initTittleBar("新订单/续约服务");
		
		order=getIntent().getParcelableExtra(Constant.ORDER);
		Log.e(TAG, order.toString());
		
		list=getData();
		
		next_step=(Button)findViewById(R.id.next_step);
		next_step.setOnClickListener(this);
		
		list_service_type=(ListView)findViewById(R.id.list_service_type);
		madapter=new UuserServiceTypeAdapter(this,list);
		list_service_type.setAdapter(madapter);		
		list_service_type.setOnItemClickListener(this);
	}


	private ArrayList<Service> getData() {
		// TODO Auto-generated method stub
		list = new ArrayList<Service>();
		Service service1 = new Service();
		service1.setServiceName("医生咨询");
		service1.setPrice(600);
		service1.setTypes("1");
		service1.setSaBouns(300);
		service1.setMsaBonus(60);
		service1.setAdminBouns(240);
		service1.setIntroduction("lve");
		Service service2 = new Service();
		service2.setServiceName("专家咨询");
		service2.setPrice(600);
		service2.setTypes("1,2");
		service2.setSaBouns(300);
		service2.setMsaBonus(60);
		service2.setAdminBouns(240);
		service2.setIntroduction("暂无");
		list.add(service1);
		list.add(service2);
		Log.e(TAG, "list.size()   "+list.size());
		return list;
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch(v.getId()){
		case R.id.next_step:
			Log.e(TAG, "下一步要传的是的是第 "+position+"种服务");
			order.setServiceId(list.get(position).getServiceId());
			order.setServiceName(list.get(position).getServiceName());
			order.setSum(list.get(position).getPrice());
			Intent intent=new Intent(UuserServiceTypeList.this,UqueryMsaList.class);
			intent.putExtra(Constant.ORDER, order);
			startActivity(intent);
			break;
		case R.id.back:
			finish();
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		position=list_service_type.getCheckedItemPosition();
		Log.e(TAG, "选中的是第 "+position+"种服务");		
	}
}
