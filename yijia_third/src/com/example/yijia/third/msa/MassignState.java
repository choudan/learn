/**
 * 
 */
package com.example.yijia.third.msa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class MassignState extends BaseActivity implements OnClickListener {
	private Button assigned, unassigned;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.m_assign_user);	
		init();
	}

	protected void init() {
		// TODO Auto-generated method stub	
		setBtnVisiable(false);
		setTittleText(this.getString(R.string.assign_user));//从app中获取姓名
		
		assigned=(Button)findViewById(R.id.assigned);
		assigned.setOnClickListener(this);
		
		unassigned=(Button)findViewById(R.id.unassigned);
		unassigned.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		switch (v.getId()) {		
		case R.id.assigned:
			intent.setClass(getApplicationContext(), MassignSaList.class);
			startActivity(intent);
			
			break;
		case R.id.unassigned:
			intent.setClass(getApplicationContext(), MassignChooseUserList.class);
			intent.putExtra(Constant.IS_ASSIGNED, 0);//1表示已分配，0未分配
			startActivity(intent);
		
			break;
		case R.id.back:
			finish();
			break;
		default:
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
