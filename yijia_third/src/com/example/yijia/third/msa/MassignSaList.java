package com.example.yijia.third.msa;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.bean.sa.Sa;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

//onResume()方法，刷新

public class MassignSaList extends BaseActivity implements OnItemClickListener{
	private ListView list_user;
	private TextView prompt;
//	private ArrayAdapter<String> adapter;
	protected MassignSaListAdapter adapter;
	private ArrayList<Sa> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.m_assign_sa_list);
		init();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		setBtnVisiable(false);
		setTittleText(this.getString(R.string.doc_user));
		
		prompt=(TextView)findViewById(R.id.prompt);
		prompt.setVisibility(View.GONE);
		
		list_user = (ListView) findViewById(R.id.list_user);
//		adapter = new ArrayAdapter<String>(this,
//				android.R.layout.simple_list_item_1,
//				getName(getData()));
		adapter = new MassignSaListAdapter(this,getData());
		list_user.setAdapter(adapter);
		list_user.setOnItemClickListener(this);
	}

	protected ArrayList<Sa> getData() {
		// TODO Auto-generated method stub
		list = new ArrayList<Sa>();
		for (int i = 0; i < 20; i++) {
			Sa user = new Sa();
			user.setRealName("张仲景第" + i);
			user.setId(10 * i);
			user.setPresentNum(2);
			list.add(user);
		}
		return list;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch(v.getId()){
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
		Log.e(TAG, " arg2 :"+arg2);
		Log.e(TAG, " list.get(arg2).getId() :"+list.get(arg2).getId());
		Intent intent=new Intent();
		intent.putExtra(Constant.IS_ASSIGNED, 1);//1表示已分配，0未分配
		intent.putExtra(Constant.SA_ID, list.get(arg2).getId());
		intent.setClass(getApplicationContext(), MassignChooseUserList.class);
		startActivity(intent);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.e(TAG, "更新数据");
	}	

}
