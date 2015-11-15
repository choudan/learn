package com.example.yijia.third.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.yijia.third.code.AddCode;
import com.example.yijia.third.inst.AaddInst;
import com.example.yijia.third.msa.AaddMsa;
import com.example.yijia.third.service.ServiceDetail;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

public class FragmentAdd extends BaseFragment implements OnClickListener{
	private Button add_common_code_doc,add_main_service,add_institution,add_common_code_exp,
			add_service,add_free_code;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.a_admin_add, container, false);
//		setView();
		init();		
		return view;
	}
	
	protected void init(){
		add_common_code_doc=(Button)view.findViewById(R.id.add_common_code_doc);
		add_common_code_doc.setOnClickListener(this);
		
		add_main_service=(Button)view.findViewById(R.id.add_main_service);
		add_main_service.setOnClickListener(this);
		
		add_institution=(Button)view.findViewById(R.id.add_institution);
		add_institution.setOnClickListener(this);
		
		add_common_code_exp=(Button)view.findViewById(R.id.add_common_code_exp);
		add_common_code_exp.setOnClickListener(this);
		
		add_free_code=(Button)view.findViewById(R.id.add_free_code);
		add_free_code.setOnClickListener(this);
		
		add_service=(Button)view.findViewById(R.id.add_service);
		add_service.setOnClickListener(this);
		
		TextView username = (TextView) view.findViewById(R.id.username);	
		username.setText(this.getString(R.string.add));
		
		Button back = (Button)view.findViewById(R.id.back);
		back.setVisibility(View.GONE);//add,query,settlement单独处理标题栏
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		switch(v.getId()){
		case R.id.add_free_code:
			intent.putExtra(Constant.CODE_TYPE, 0);	
			intent.setClass(getActivity(), AddCode.class);
			
			break;
		case R.id.add_common_code_doc:
			intent.putExtra(Constant.CODE_TYPE, 1);
			intent.setClass(getActivity(), AddCode.class);
			
			break;
		case R.id.add_common_code_exp:			
			intent.putExtra(Constant.CODE_TYPE, 2);
			intent.setClass(getActivity(), AddCode.class);
			
			break;
		case R.id.add_institution:
			intent.setClass(getActivity(), AaddInst.class);
			break;
		case R.id.add_main_service:
			intent.setClass(getActivity(), AaddMsa.class);			
			break;
		case R.id.add_service:
			intent.setClass(getActivity(), ServiceDetail.class);	
			Bundle mbundle=new Bundle();
			mbundle.putBoolean(Constant.EDITABLE, true);
			intent.putExtras(mbundle);
			break;
//		case R.id.back:
//			fm.popBackStack();
//			break;
			default:
				break;
		}
		startActivity(intent);
		
	}

//	@Override
//	protected void setView() {
//		// TODO Auto-generated method stub
//		view=setSubView(R.layout.a_admin_add);
//	}	
}
