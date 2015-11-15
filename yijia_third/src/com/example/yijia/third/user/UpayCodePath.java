package com.example.yijia.third.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.example.yijia.third.base.RegisterBaseActivity;
import com.example.yijia.third.bean.common.Order;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

public class UpayCodePath extends RegisterBaseActivity implements OnEditorActionListener{
	private Button next_step;
	private Order order;
	private EditText input_code;
	
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
		
		setSubView(R.layout.u_user_pay_code_path,false);
		
		initTittleBar("新订单/续约服务");
		
		order=getIntent().getParcelableExtra(Constant.ORDER);
		
		input_code=(EditText)findViewById(R.id.input_code);
		input_code.setOnClickListener(this);
		input_code.setOnEditorActionListener(this);
		setEditable(input_code, false);
		
		next_step=(Button)findViewById(R.id.next_step);
		next_step.setOnClickListener(this);
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
		case R.id.input_code:
			setEditable(input_code, true);			
			break;
		case R.id.next_step:			
			Intent intent=new Intent(UpayCodePath.this,UpayResult.class);
			if(!TextUtils.isEmpty(input_code.getText())&&checkValid(input_code)){
				Log.e(TAG, "输入的兑换码是：  "+input_code.getText());
				order.setIsValid(1);
				Log.e(TAG, "此处生成订单...   ");
				intent.putExtra(Constant.ORDER, order);
				startActivity(intent);	
								
//				Intent intent=new Intent(UpayCodePath.this,Uuser.class);
//				intent.putExtra(Constant.UNFINISHED_SURVEY, 0);
//				intent.putExtra(Constant.ORDER_STATE, 0);
//				intent.setAction(Constant.EXIT);
//				this.sendBroadcast(intent);
//				startActivity(intent);
			}
			break;
		case R.id.back:
			finish();
			break;
		}
		Log.e(TAG, order.toString());
	}

	private boolean checkValid(EditText input_code2) {
		// TODO Auto-generated method stub
		if(input_code2.getText().toString().equals("123456"))
			return true;
		return false;
	}
	
	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		// TODO Auto-generated method stub
		if(actionId==KeyEvent.ACTION_DOWN||actionId==EditorInfo.IME_ACTION_DONE){
			Log.e(TAG, "隐藏软键盘...");
			InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			imm.hideSoftInputFromWindow(input_code.getWindowToken(), 0);
		}
		return true;
	}
}
