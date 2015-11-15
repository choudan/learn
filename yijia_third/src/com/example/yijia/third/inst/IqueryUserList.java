/**
 * 
 */
package com.example.yijia.third.inst;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.bean.user.User;
import com.example.yijia.third.tool.Constant;
import com.example.yijia.third.user.QueryUserInfo;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class IqueryUserList extends BaseActivity implements OnItemClickListener {
	private ListView list_user;
	private ArrayAdapter<String> mAdapter;
	private ArrayList<User> list;
	private ArrayList<String> listName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.a_query_inst_user);
		init();
	}

	protected void init() {
		// TODO Auto-generated method stub
		setBtnVisiable(false);
		setTittleText(this.getString(R.string.user));// ±êÌâ
		
		list=getData();
		Log.e(TAG, "list.size():  "+list.size());
		
		list_user = (ListView) findViewById(R.id.list_user);
		mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, getData(list));
		list_user.setAdapter(mAdapter);
		list_user.setOnItemClickListener(this);
	}

	private ArrayList<String> getData(ArrayList<User> list) {
		// TODO Auto-generated method stub
		listName=new ArrayList<String>();
		for (User user : list)
			listName.add(user.getRealName());
		return listName;
	}

	private ArrayList<User> getData() {
		// TODO Auto-generated method stub
		list = new ArrayList<User>();
		User user = new User();
		user.setRealName("ÕÅºÕ");
		user.setUserId(1);
		User user1 = new User();
		user1.setRealName("³ÂºÕ");
		user1.setUserId(2);
		list.add(user);
		list.add(user1);
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, QueryUserInfo.class);
		Bundle bundle = new Bundle();
		bundle.putParcelable(Constant.SIMPLE_USER, list.get(arg2));
		intent.putExtras(bundle);
		startActivity(intent);
	}

}
