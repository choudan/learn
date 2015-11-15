/**
 * 
 */
package com.example.yijia.third.inst;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.bean.msa.MsaBound;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class IqueryDocExpInfo extends BaseActivity {
	private TextView name, sex, age, hosiptal, dept, introduction;
	private MsaBound msaBound;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.i_msa_info);	
		msaBound=getIntent().getExtras().getParcelable(Constant.MSA);
		Log.e(TAG, msaBound.toString());
		init();
	}

	protected void init() {
		// TODO Auto-generated method stub
		setBtnVisiable(true);
		setTittleText(msaBound.getRealName());// БъЬт

		name = (TextView) findViewById(R.id.name);
		name.setText(msaBound.getRealName());

		sex = (TextView) findViewById(R.id.sex);
		sex.setText(msaBound.getSex());

		age = (TextView) findViewById(R.id.age);
		age.setText(msaBound.getBirthday());

		hosiptal = (TextView) findViewById(R.id.hosiptal);
		hosiptal.setText(msaBound.getHospital());

		dept = (TextView) findViewById(R.id.dept);
		dept.setText(msaBound.getDept());

		introduction = (TextView) findViewById(R.id.introduction);
		introduction.setText(msaBound.getIntroduction());

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
