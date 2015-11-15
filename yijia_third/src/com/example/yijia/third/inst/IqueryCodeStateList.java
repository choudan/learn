/**
 * 
 */
package com.example.yijia.third.inst;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ListView;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.bean.common.CodeConsuneDetail;
import com.example.yijia.third.code.CodeAdapter;
import com.example.yijia_third.R;

/**
 * @author Administrator
 *
 */
public class IqueryCodeStateList extends BaseActivity{
	private ListView list_code;
	private CodeAdapter mAdapter;
	private ArrayList<CodeConsuneDetail> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.a_query_code_state_detail);	
		init();
	}
	
	protected void init() {
		// TODO Auto-generated method stub
		setBtnVisiable(false);
		setTittleText(this.getString(R.string.code));//标题
		
		list_code = (ListView)findViewById(R.id.list_code);
		mAdapter=new CodeAdapter(this,getData());
		list_code.setAdapter(mAdapter);
	}

	private ArrayList<CodeConsuneDetail> getData() {
		// TODO Auto-generated method stub
		list=new ArrayList<CodeConsuneDetail>();
		CodeConsuneDetail codeConsuneDetail1=new CodeConsuneDetail();
		codeConsuneDetail1.setCode("1000");
		codeConsuneDetail1.setCodeConsumer("张三");
		codeConsuneDetail1.setConsumeDate("2010-04-10");
		CodeConsuneDetail codeConsuneDetail2=new CodeConsuneDetail();
		codeConsuneDetail2.setCode("2000");
		codeConsuneDetail2.setCodeConsumer("李四");
		codeConsuneDetail2.setConsumeDate("2010-09-10");
		list.add(codeConsuneDetail1);
		list.add(codeConsuneDetail2);
		return list;
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
