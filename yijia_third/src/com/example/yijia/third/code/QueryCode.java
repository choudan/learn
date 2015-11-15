package com.example.yijia.third.code;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.yijia.third.admin.BaseFragment;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

public class QueryCode extends BaseFragment {
	private Button common_code_exp, common_code_doc, free_code;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.a_query_code, container, false);
		init();
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		FragmentTransaction ft=fm.beginTransaction();
		ft.hide(this);
		QueryCodeState queryCodeState=new QueryCodeState();
		Bundle bundle=new Bundle();
		switch(v.getId()){ 	
		case R.id.common_code_exp:
			bundle.putLong(Constant.CODE_TYPE,0);
			queryCodeState.setArguments(bundle);
			ft.add(R.id.content,queryCodeState,"FIVE");		
			ft.addToBackStack(null);
			ft.commit();
			break;
		case R.id.common_code_doc:
			bundle.putLong(Constant.CODE_TYPE,1);
			queryCodeState.setArguments(bundle);
			ft.add(R.id.content,queryCodeState,"FIVE");		
			ft.addToBackStack(null);
			ft.commit();
			break;
		case R.id.free_code:
			bundle.putLong(Constant.CODE_TYPE,2);
			queryCodeState.setArguments(bundle);
			ft.add(R.id.content,queryCodeState,"FIVE");		
			ft.addToBackStack(null);
			ft.commit();
			break;
		case R.id.back:
			fm.popBackStack();
			break;
			default:
				break;
		}
	}

//	@Override
//	protected void setView() {
//		// TODO Auto-generated method stub
//		view = setSubView(R.layout.a_query_code);
//	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		getBtn();
		
		common_code_exp = (Button) view.findViewById(R.id.common_code_exp);
		common_code_doc = (Button) view.findViewById(R.id.common_code_doc);
		free_code = (Button) view.findViewById(R.id.free_code);
		common_code_exp.setOnClickListener(this);
		common_code_doc.setOnClickListener(this);
		free_code.setOnClickListener(this);	
		
//		TextView username = (TextView) view.findViewById(R.id.username);	
		username.setText(this.getString(R.string.code));
	}
}
