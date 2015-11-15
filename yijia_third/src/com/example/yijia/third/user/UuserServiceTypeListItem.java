package com.example.yijia.third.user;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.yijia_third.R;

public class UuserServiceTypeListItem extends LinearLayout implements Checkable {
	public TextView mTextView,service_content;
	public RadioButton mRadioButton;
	public Button mButton;
	
	public UuserServiceTypeListItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		View view=LayoutInflater.from(context).inflate(R.layout.u_user_service_type_list_item,this,true);
		mTextView=(TextView)view.findViewById(R.id.mTextView);
		service_content=(TextView)view.findViewById(R.id.service_content);
		mRadioButton=(RadioButton)view.findViewById(R.id.mRadioButton);
		mButton=(Button)view.findViewById(R.id.mButton);	
	}

	@Override
	public boolean isChecked() {
		// TODO Auto-generated method stub
		return mRadioButton.isChecked();
	}

	@Override
	public void setChecked(boolean checked) {
		// TODO Auto-generated method stub
		mRadioButton.setChecked(checked);
	}

	@Override
	public void toggle() {
		// TODO Auto-generated method stub
		mRadioButton.toggle();
	}
	
	public void setText(String str){
		mTextView.setText(str);
	}
	
	public void setContent(String str){
		service_content.setText(str);
	}
	
	public Button getBtn(){
		return mButton;
	}
	
	public void setVisiable(){
		if(service_content.getVisibility()==View.GONE)
			service_content.setVisibility(View.VISIBLE);
		else
			service_content.setVisibility(View.GONE);
	}
}
