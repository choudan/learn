package com.example.yijia.third.user;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yijia.third.admin.BaseFragment;
import com.example.yijia.third.bean.inst.Inst;
import com.example.yijia.third.bean.user.User;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

/**
 * @author  丑旦 
 * @date 创建时间：2015-8-22 下午2:40:08 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class QueryUserList extends BaseFragment implements OnItemClickListener{
	private TextView total_user,present_user;
	private ListView list_user;
	private ArrayAdapter<String> adapter;
	private ArrayList<User> list;
	private ArrayList<String> listName;
	public final String TAG = this.getActivity().getClass().getName();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		setView();
		view = inflater.inflate(R.layout.a_query_inst_user, container, false);
		init();
		
		return view;
	}

	private ArrayList<User> getData() {
		// TODO Auto-generated method stub
		list=new ArrayList<User>();
		User user=new User();
		user.setUserId(1);
		user.setRealName("李晋");
		list.add(user);
		
		User user1=new User();
		user1.setUserId(2);
		user1.setRealName("张潮");
		list.add(user1);
		return list;
	}

	private ArrayList<String> getName(){
		list=getData();
		listName=new ArrayList<String>();
		for(User user:list)
			listName.add(user.getRealName());
		return listName;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		User user=list.get(position);
		Log.e(TAG, "user的名字："+user.realName+"   user的id:  "+user.userId);
		Intent intent=new Intent();
		intent.setClass(getActivity(), QueryUserInfo.class);
		Bundle bundle=new Bundle();
		bundle.putParcelable(Constant.SIMPLE_USER, user);
		intent.putExtras(bundle);
		startActivity(intent);
	}

//	@Override
//	protected void setView() {
//		// TODO Auto-generated method stub
//		view = setSubView(R.layout.a_query_inst_user);
//	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		Inst simpleInst=getArguments().getParcelable(Constant.SIMPLE_INST);
		Toast.makeText(getActivity(), "inst_id是：  "+simpleInst.getInstId(), Toast.LENGTH_SHORT).show();
		
		total_user=(TextView)view.findViewById(R.id.total_user);
		total_user.setText(this.getString(R.string.total_user)+simpleInst.getTotalNum()+"人");
		
		present_user=(TextView)view.findViewById(R.id.present_user);
		present_user.setText(this.getString(R.string.present_user)+simpleInst.getPresentNum()+"人");
		
		list_user=(ListView)view.findViewById(R.id.list_user);		
		adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1,getName());
		list_user.setAdapter(adapter);
		list_user.setOnItemClickListener(this);
		
		setTittleText(this.getString(R.string.user_list));
	}
}
