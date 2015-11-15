package com.example.yijia.third.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.yijia.third.base.RegisterBaseActivity;
import com.example.yijia.third.bean.common.Order;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

public class UpayPath extends RegisterBaseActivity {
	private Button alipay_pay,wechat_pay,code_pay;
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
		
		setSubView(R.layout.u_user_pay_path,false);
		
		initTittleBar("新订单/续约服务");
		
		order=getIntent().getParcelableExtra(Constant.ORDER);
		Log.e(TAG, order.toString());
		
		alipay_pay=(Button)findViewById(R.id.alipay_pay);
		alipay_pay.setOnClickListener(this);
		
		wechat_pay=(Button)findViewById(R.id.wechat_pay);
		wechat_pay.setOnClickListener(this);
		
		code_pay=(Button)findViewById(R.id.code_pay);
		code_pay.setOnClickListener(this);	
	
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
		case R.id.wechat_pay:
			order.setPayPath(0);
			
			break;
		case R.id.alipay_pay:
			order.setPayPath(1);
			
			break;
		case R.id.code_pay:
			order.setPayPath(2);
			Intent intent =new Intent(UpayPath.this,UpayCodePath.class);
			intent.putExtra(Constant.ORDER, order);
			startActivity(intent);
			
			break;
		case R.id.back:
			finish();
			break;
		}
		Log.e(TAG, order.toString());
	}
}
