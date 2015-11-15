package com.example.yijia.third.sa;

import com.example.yijia_third.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SnewQuestionnaireItem extends LinearLayout implements Checkable {

	public TextView name,date;
	public CheckBox checkBox;
	public SnewQuestionnaireItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.m_quertionnaire_list_tem, this, true);
		name = (TextView) v.findViewById(R.id.name);
		date = (TextView) v.findViewById(R.id.date);
		checkBox = (CheckBox) v.findViewById(R.id.checkBox);		
	}

	public void setName(String str){
		name.setText(str);
	}
	
	public void setDate(String str){
		date.setText(str);
	}
	
	public void setVisiable(boolean flag){
		if(flag)
			checkBox.setVisibility(View.VISIBLE);
		else
			checkBox.setVisibility(View.INVISIBLE);			
	}
	
	public void setTextVisiable(boolean flag){
		if(flag)
			date.setVisibility(View.VISIBLE);
		else
			date.setVisibility(View.GONE);			
	}
	
	
	@Override
	public boolean isChecked() {
		// TODO Auto-generated method stub
		return checkBox.isChecked();
	}

	public void setDisable(boolean enable){
		if(enable)
			checkBox.setChecked(false);
	}
	
	@Override
	public void setChecked(boolean checked) {
		// TODO Auto-generated method stub
		checkBox.setChecked(checked);
	}

	@Override
	public void toggle() {
		// TODO Auto-generated method stub
		checkBox.toggle();
	}

}
