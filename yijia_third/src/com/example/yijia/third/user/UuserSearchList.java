package com.example.yijia.third.user;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.example.yijia.third.base.RegisterBaseActivity;
import com.example.yijia.third.bean.common.Order;
import com.example.yijia.third.bean.inst.Inst;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

public class UuserSearchList extends RegisterBaseActivity implements OnItemClickListener,OnEditorActionListener{
	private EditText search_content;
	private ListView search_list;
	private ArrayList<Inst> list;
	private ArrayAdapter<String> adapter;
	private Order order;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		trtStatus();
		
		setSubView(R.layout.u_search_list,false);
		
		initTittleBar("新订单/续约服务");
		
		order=getIntent().getParcelableExtra(Constant.ORDER);
		Log.e(TAG, order.toString());
		
		search_content=(EditText)findViewById(R.id.search_content);
		search_content.setOnClickListener(this);
		search_content.setOnEditorActionListener(this);
		setEditable(search_content, false);
		
		search_list=(ListView)findViewById(R.id.search_list);
		search_list.setOnItemClickListener(this);
		search_list.setVisibility(View.INVISIBLE);
	}

	private ArrayList<String> getDataName() {
		// TODO Auto-generated method stub
		ArrayList<String> listName=new ArrayList<String>();		
		for(Inst inst:list)
			listName.add(inst.getInstName());
		return listName;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch(v.getId()){
		case R.id.search_content:
			setEditable(search_content, true);
			break;
		case R.id.back:
			finish();
			break;
		}
	}

	
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			case -1:
				search_list.setVisibility(View.VISIBLE);
				search_list.setAdapter(adapter);
			}
		}	
	};
		
	@Override
	protected void hanlderUi() {
		// TODO Auto-generated method stub	
		if(list.size()>0){
			Message msg=new Message();
			msg.what=-1;
			handler.sendMessage(msg);
			adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getDataName());
		}
	}

	@Override
	protected void interactive() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Log.e(TAG, "=======list.get(arg2)  "+list.get(arg2).toString());
		Intent intent=new Intent(UuserSearchList.this,UuserServiceTypeList.class);
		order.setInstId(list.get(arg2).getInstId());
		intent.putExtra(Constant.ORDER, order);
		startActivity(intent);
	}

	private ArrayList<Inst> getData(){
		ArrayList<Inst> list=new ArrayList<Inst>();
		for(int i=0;i<10;i++){
			Inst inst=new Inst();
			inst.setInstName("中央第 "+i+" 医院");
			inst.setInstId(i+1);
			list.add(inst);
		}
		return list;
	}
	
	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		// TODO Auto-generated method stub
		if(actionId==KeyEvent.ACTION_DOWN||actionId==EditorInfo.IME_ACTION_SEARCH){
			Log.e(TAG, "此时开始搜索...");
			setEditable(search_content, false);
			list=getData();
			Log.e(TAG, "list.size() "+list.size());
			
			InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			imm.hideSoftInputFromWindow(search_content.getWindowToken(), 0);
			Log.e(TAG, "隐藏软键盘...  开始搜索...");
			
			hanlderUi();
		}
		return true;
	}	
}
