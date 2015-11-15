package com.example.yijia.third.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.yijia.third.base.RegisterBaseActivity;
import com.example.yijia.third.bean.common.Order;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

public class UnewOrderType extends RegisterBaseActivity {
	private Button order_type_inst,order_type_personal;
	
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
		
		setSubView(R.layout.u_new_order_type,false);
		
		initTittleBar("新订单/续约服务");
		
		order_type_inst=(Button)findViewById(R.id.order_type_inst);
		order_type_inst.setOnClickListener(this);
		
		order_type_personal=(Button)findViewById(R.id.order_type_personal);
		order_type_personal.setOnClickListener(this);	
	}	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Order order=new Order();
		order.setNewAlter(true);
		super.onClick(v);
		switch(v.getId()){
		case R.id.order_type_inst:
			order.setType(0);	//0机构
			Intent intent=new Intent(UnewOrderType.this,UuserSearchList.class);
			intent.putExtra(Constant.ORDER, order);
			startActivity(intent);
			break;
		case R.id.order_type_personal:
			order.setType(1);	//1个人
			Intent intent1=new Intent(UnewOrderType.this,UuserServiceTypeList.class);
			intent1.putExtra(Constant.ORDER, order);
			startActivity(intent1);
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
}
