/**
 * 
 */
package com.example.yijia.third.user;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.bean.user.User;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class AqueryLogoutUser extends BaseActivity implements OnItemClickListener {

	private ListView list_logout_user;
	private LogoutAdapter mAdapter;
	private ArrayList<User> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.a_query_logout_user);
		init();
	}

	protected void init() {
		// TODO Auto-generated method stub
		setBtnVisiable(false);
		setTittleText(this.getString(R.string.logout_user));
		
		list_logout_user = (ListView) findViewById(R.id.list_logout_user);
		list_logout_user.setOnItemClickListener(this);
		mAdapter = new LogoutAdapter(this, getData());
		list_logout_user.setAdapter(mAdapter);
	}

	private ArrayList<User> getData() {
		// TODO Auto-generated method stub
		list = new ArrayList<User>();
		for (int i = 0; i < 5; i++) {
			User simpleInfo = new User();
			simpleInfo.realName = "张三";
			simpleInfo.userId = (long) i;
			simpleInfo.logoutDate = "2014-08-29   "+simpleInfo.userId;
			list.add(simpleInfo);
		}
		return list;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(AqueryLogoutUser.this,QueryUserInfo.class);
		Bundle mbundle=new Bundle();
		mbundle.putParcelable(Constant.SIMPLE_USER,list.get(arg2));
		intent.putExtras(mbundle);
		startActivity(intent);
		
//		final int position=arg2;
//		LinearLayout simple_info=(LinearLayout) arg1.findViewById(R.id.simple_info);
//		Button user_info=(Button)arg1.findViewById(R.id.user_info);
//		user_info.setOnClickListener(new OnClickListener(){
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Toast.makeText(getApplicationContext(), "这是第  "+position+"  行的button", Toast.LENGTH_SHORT).show();
//			}			
//		});
//		if(simple_info.getVisibility()==View.VISIBLE)
//			simple_info.setVisibility(View.GONE);			
//		else
//			simple_info.setVisibility(View.VISIBLE);			
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
