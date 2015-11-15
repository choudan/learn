package com.example.yijia.third.admin;

import com.example.yijia_third.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class FragmentQuery extends BaseFragment implements OnClickListener {
	private Button query_msa, query_inst, query_code,
			service_content,logout_user;
	
	public interface OnBtnClickListener {
		void onBtnClick(View v);
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {		
		view = inflater.inflate(R.layout.a_admin_query, container, false);
		init();
		return view;
	}

	
	protected void init(){
		query_msa = (Button) view.findViewById(R.id.query_msa);
		query_inst = (Button) view.findViewById(R.id.query_inst);
		query_code = (Button) view.findViewById(R.id.query_code);
		service_content = (Button) view.findViewById(R.id.service_content);
		logout_user = (Button) view.findViewById(R.id.logout_user);
		query_msa.setOnClickListener(this);
		query_inst.setOnClickListener(this);
		query_code.setOnClickListener(this);
		service_content.setOnClickListener(this);
		logout_user.setOnClickListener(this);
		
		TextView username = (TextView) view.findViewById(R.id.username);	
		username.setText(this.getString(R.string.check));
		
		Button back = (Button)view.findViewById(R.id.back);
		back.setVisibility(View.GONE);//add,query,settlement单独处理标题栏
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (getActivity() instanceof OnBtnClickListener) {
			((OnBtnClickListener) getActivity()).onBtnClick(v);
		}
	}

//	@Override
//	protected void setView() {
//		// TODO Auto-generated method stub
//		view=setSubView(R.layout.a_admin_query);
//	}
}
