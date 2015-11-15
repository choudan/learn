/**
 * 
 */
package com.example.yijia.third.code;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.yijia.third.admin.BaseFragment;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

/**
 * @author Administrator
 *
 */
public class QueryCodeState extends BaseFragment {
	private Button used,unused;
	private long codeType;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {	
		codeType = getArguments().getLong(Constant.CODE_TYPE);
		// 测试
		Toast.makeText(getActivity(), "传过来的codeType是：  " + codeType,Toast.LENGTH_SHORT).show();	
		view = inflater.inflate(R.layout.a_query_code_state, container, false);
		
//		setView();
		init();
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		QueryCodeStateList queryCodeStateList=new QueryCodeStateList();
		Bundle bundle=new Bundle();
		bundle.putLong(Constant.CODE_TYPE, codeType);
		FragmentTransaction ft=fm.beginTransaction();
		switch(v.getId()){
		case R.id.used:
			bundle.putInt(Constant.CODE_STATE, 1);
			queryCodeStateList.setArguments(bundle);				
			ft.hide(this);
			ft.add(R.id.content,queryCodeStateList,"SIX");		
			ft.addToBackStack(null);
			ft.commit();
			break;
		case R.id.unused:
			bundle.putInt(Constant.CODE_STATE, 0);			
			queryCodeStateList.setArguments(bundle);				
			ft.hide(this);
			ft.add(R.id.content,queryCodeStateList,"SIX");		
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
//		view = setSubView(R.layout.a_query_code_state);
//	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		getBtn();
		
		used = (Button) view.findViewById(R.id.used);
		unused = (Button) view.findViewById(R.id.unused);		
		used.setOnClickListener(this);
		unused.setOnClickListener(this);	
		
//		TextView username = (TextView) view.findViewById(R.id.username);	
		username.setText(this.getString(R.string.code_state));
	}
}
