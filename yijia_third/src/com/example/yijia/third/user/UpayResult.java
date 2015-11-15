package com.example.yijia.third.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.example.yijia.third.base.RegisterBaseActivity;
import com.example.yijia.third.bean.common.Order;
import com.example.yijia.third.tool.CalendarUtils;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

public class UpayResult extends RegisterBaseActivity implements OnEditorActionListener{
	private Button pay_finish;
	private Order order;
	private int isValid;
	
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
		
		setSubView(R.layout.u_user_pay_result,true);
		
		initTittleBar("新订单/续约服务");
		
		pay_finish=(Button)findViewById(R.id.pay_finish);
		pay_finish.setOnClickListener(this);
		
		order=getIntent().getParcelableExtra(Constant.ORDER);
		isValid=order.getIsValid();
		Log.e(TAG, ""+isValid);
		if(isValid==1){
			Log.e(TAG, "订单完成支付...");	
			if(!order.isNewAlter()){
				if(order.isAlterFinished())
					pay_finish.setText(this.getString(R.string.alter_finish));	
				else
					pay_finish.setText(this.getString(R.string.alter_fail));						
			}
			order=makeOrder();
			Log.e(TAG, "此处提交..."+order.toString());		
		}else{
			Log.e(TAG, "订单支付失败...");		
			pay_finish.setText(this.getString(R.string.pay_fail));
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch(v.getId()){
		case R.id.pay_finish:
			exit();			
			break;
		}
	}
	
	private Order makeOrder(){
		order.setIsValid(1);
		order.setPurchaseNum(1);
		order.setStartTime(CalendarUtils.dateTime());
		order.setDeadline(CalendarUtils.deadline());	
		return order;
	}
	
	private void exit(){
		Intent intent=new Intent();
		intent.setAction(Constant.EXIT);
		this.sendBroadcast(intent);
		Log.e(TAG, "返回订单列表或者用户主页，并刷新...");
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		// TODO Auto-generated method stub
		return true;
	}	
}
