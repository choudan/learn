package com.example.yijia.third.user;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.bean.common.Order;
import com.example.yijia.third.tool.Constant;
import com.example.yijia.third.user.UuserOrderListAdapter.OnBtnClicker;
import com.example.yijia_third.R;

public class UuserOrderList extends BaseActivity implements OnBtnClicker{
	private Button add_order;
//	private ListView list_order;
	private UuserOrderListAdapter madapter;
	private ArrayList<Order> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		setSubView(R.layout.u_user_order_list);
		
		add_order=(Button)findViewById(R.id.add_order);
		add_order.setOnClickListener(this);
		
		ListView list_order=(ListView)findViewById(R.id.list_order);
		madapter=new UuserOrderListAdapter(getData(),this);
		madapter.setOnBtnClicker(this);	
		list_order.setAdapter(madapter);
	}

	private ArrayList<Order> getData() {
		// TODO Auto-generated method stub
		list=new ArrayList<Order>();
		for(int i=1;i<30;i++){		
			Order order1=new Order();
			order1.setUserId(i);
			order1.setServiceId(2);
			order1.setServiceName("专家咨询");
			order1.setProviderId(2);
			order1.setProviderName("李四");
			order1.setProviderUnit("北京301总院");
			order1.setType(1);
			order1.setSum(200);
			order1.setPayPath(1);
			order1.setPurchaseNum(1);
			order1.setStartTime("2015-08-29");
			order1.setDeadline("2016-08-29");
			order1.setIsValid(1);
			list.add(order1);		
		}
		for(int i=1;i<5;i++){
			Order order=new Order();
			order.setUserId(i);
			order.setServiceId(1);
			order.setServiceName("医生咨询");
			order.setProviderId(1);
			order.setProviderName("张三");
			order.setProviderUnit("北京301总院");
			order.setType(1);
			order.setSum(200);
			order.setPayPath(1);
			order.setPurchaseNum(1);
			order.setStartTime("2014-08-29");
			order.setDeadline("2015-08-29");
			order.setIsValid(0);
			list.add(order);
		}
		return list;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch(v.getId()){
		case R.id.add_order:
			Intent intent=new Intent(UuserOrderList.this,UnewOrderType.class);
			startActivity(intent);
			break;
		case R.id.back:
			finish();
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
	public void onBtnClicker(View v, int position) {
		// TODO Auto-generated method stub
		Log.e(TAG, "点击第  " + (position+1)+" 条订单");	
		Intent intent=new Intent(UuserOrderList.this,UpayPath.class);
		intent.putExtra(Constant.ORDER, list.get(position));
		startActivity(intent);
	}

}
