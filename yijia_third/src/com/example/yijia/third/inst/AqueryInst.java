/**
 * 
 */
package com.example.yijia.third.inst;

import java.util.ArrayList;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.yijia.third.admin.BaseFragment;
import com.example.yijia.third.bean.inst.Inst;
import com.example.yijia.third.tool.Constant;
import com.example.yijia.third.user.QueryUserList;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class AqueryInst extends BaseFragment implements OnItemClickListener,OnClickListener {

	private ListView list_inst;
	private ArrayList<Inst> list;
	private AqueryInstAdapter adapter;
	private Button inst_info,inst_user_info;
	private Bundle mbundle;
	private final static String TAG=AqueryInst.class.getName();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.a_query_inst_list, container, false);
//		setView();
		init();
		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		LinearLayout inst_info_detail=(LinearLayout)arg1.findViewById(R.id.inst_info_detail);
		inst_info=(Button)arg1.findViewById(R.id.inst_info);
		inst_user_info=(Button)arg1.findViewById(R.id.inst_user_info);
		if(inst_info_detail.getVisibility()==View.GONE){
			inst_info_detail.setVisibility(View.VISIBLE);	
			inst_info.setOnClickListener(this);
			inst_user_info.setOnClickListener(this);			
			mbundle=new Bundle();
			mbundle.putParcelable(Constant.SIMPLE_INST, getData().get(arg2));
		}else
			inst_info_detail.setVisibility(View.GONE);	
	}
	
	private ArrayList<Inst> getData() {
		// TODO Auto-generated method stub
		list = new ArrayList<Inst>();
		Inst simpleInst = new Inst();
		simpleInst.setInstId(1);
		simpleInst.setRealName("曹操");
		simpleInst.setInstName("北京301");
		simpleInst.setPresentNum(3);
		simpleInst.setTotalNum(5);
		
		Inst simpleInst0 = new Inst();
		simpleInst0.setInstId(2);
		simpleInst0.setRealName("刘备");
		simpleInst0.setInstName("北京望京会所");
		simpleInst0.setPresentNum(2);
		simpleInst0.setTotalNum(4);
			
		list.add(simpleInst);
		list.add(simpleInst0);
		Log.e(TAG, "list.size()   "+list.size());
		return list;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
//		FragmentManager fm=getFragmentManager();
		FragmentTransaction ft=fm.beginTransaction();
		switch(v.getId()){
		case R.id.inst_info:
			AqueryInstInfo queryInstInfo=new AqueryInstInfo();
			queryInstInfo.setArguments(mbundle);
			ft.hide(this);
			ft.add(R.id.content, queryInstInfo,"THREE");			
			ft.addToBackStack(null);
			ft.commit();
			break;
		case R.id.inst_user_info:
			QueryUserList queryUserList=new QueryUserList();
			queryUserList.setArguments(mbundle);
			ft.hide(this);
			ft.add(R.id.content, queryUserList,"THREE");
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
//		view = setSubView(R.layout.a_query_inst_list);
//	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		getBtn();
		
		inst_info=(Button)view.findViewById(R.id.inst_info);
		inst_user_info=(Button)view.findViewById(R.id.inst_user_info);	
		
		list_inst = (ListView) view.findViewById(R.id.list_inst);			
		adapter=new AqueryInstAdapter(getActivity(),getData());
		list_inst.setAdapter(adapter);
		list_inst.setOnItemClickListener(this);
		
// 		TextView username = (TextView) view.findViewById(R.id.username);	
		username.setText(this.getString(R.string.init_list));
	}
}
