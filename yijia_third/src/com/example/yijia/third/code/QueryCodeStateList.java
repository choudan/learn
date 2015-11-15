/**
 * 
 */
package com.example.yijia.third.code;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yijia.third.admin.BaseFragment;
import com.example.yijia.third.bean.common.CodeConsuneDetail;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

/**
 * @author Administrator
 *
 */
public class QueryCodeStateList extends BaseFragment{
	private ListView list_code;
	private CodeAdapter mAdapter;
	private ArrayList<CodeConsuneDetail> list;
	private long codeType;
	private int codeState;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {	
		codeType = getArguments().getLong(Constant.CODE_TYPE);
		codeState = getArguments().getInt(Constant.CODE_STATE);
		// 测试
		Toast.makeText(getActivity(), "传过来的codeType是：  " + codeType+"  codeState是： "+codeState,
				Toast.LENGTH_SHORT).show();
		
		view = inflater.inflate(R.layout.a_query_code_state_detail, container, false);
		init();
		
		return view;
	}

	private ArrayList<CodeConsuneDetail> getData() {
		// TODO Auto-generated method stub
		list=new ArrayList<CodeConsuneDetail>();
		CodeConsuneDetail codeConsuneDetail1=new CodeConsuneDetail();
		codeConsuneDetail1.setCode("1000");
		if(codeState==1)
			codeConsuneDetail1.setCodeConsumer("张三");
		codeConsuneDetail1.setConsumeDate("2010-04-10");
		
		CodeConsuneDetail codeConsuneDetail2=new CodeConsuneDetail();
		codeConsuneDetail2.setCode("2000");
		if(codeState==1)
			codeConsuneDetail2.setCodeConsumer("李四");
		codeConsuneDetail2.setConsumeDate("2010-09-10");
		list.add(codeConsuneDetail1);
		list.add(codeConsuneDetail2);
		return list;
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		getBtn();
		
		list_code = (ListView) view.findViewById(R.id.list_code);
		mAdapter=new CodeAdapter(getActivity(),getData());
		list_code.setAdapter(mAdapter);
		
		username.setText(this.getString(R.string.code));
	}
}
