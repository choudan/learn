package com.example.yijia.third.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.yijia.third.bean.common.Order;
import com.example.yijia.third.bean.common.OrderAlterNotify;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

public class UuserServiceAlter extends Activity{
	private Button service_alter;
	private TextView service_deadline,gift_month_,gift_reason;
	private OrderAlterNotify orderAlter;
	private Order order;	
	private final String TAG = this.getClass().getName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		setContentView(R.layout.common_service_alter);			
		
		orderAlter=getIntent().getParcelableExtra(Constant.ORDER_ALTER);
		Log.e(TAG, orderAlter.toString());
		
		service_deadline=(TextView)findViewById(R.id.service_deadline);
		service_deadline.setText(this.getString(R.string.service_deadline)+orderAlter.getDelayTakeEffect());
		
		gift_month_=(TextView)findViewById(R.id.gift_month_);		
		gift_month_.setText(this.getString(R.string.gift_month_)+orderAlter.getDelayMonth()+"/月");
		
		gift_reason=(TextView)findViewById(R.id.gift_reason);		
		gift_reason.setText(orderAlter.getDelayReason());
		
		service_alter=(Button)findViewById(R.id.service_alter);
		service_alter.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent =new Intent(UuserServiceAlter.this,UqueryMsaList.class);
				intent.putExtra(Constant.ORDER_ALTER, true);//判断是更改还是新生成的订单
				intent.putExtra(Constant.ORDER, getData());
				startActivity(intent);
				finish();
			}
		});		
	}
		
	private Order getData(){
		order=new Order();
		order.setUserId(1);
		order.setInstId(0);
		order.setServiceId(2);
		order.setType(1);
		order.setDeadline("2015-08-20");
		return order;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode== KeyEvent.KEYCODE_BACK)
			Log.e(TAG, "=-=-= 返回键失效 ");
		return true;
	}
}
