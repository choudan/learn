/**
 * 
 */
package com.example.yijia.third.msa;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.bean.user.User;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * onResume()方法，刷新，区分isAssigned == 0、1,刷新内容不同
 */

public class MassignChooseUserList extends BaseActivity implements
		OnClickListener {
	private ListView list_user;
	private TextView prompt;
	private int isAssigned;
	private ArrayAdapter<String> adapter;
	private ArrayList<User> list;
	private Button next_step;
	private long saId;
	// private ArrayList<String> listName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.m_assign_user_list);
		isAssigned = getIntent().getIntExtra(Constant.IS_ASSIGNED, -1);
		if(isAssigned==1){
			saId=getIntent().getLongExtra(Constant.SA_ID, -1);			
			Log.e(TAG, "  saId:  " + saId);
		}
		Log.e(TAG, "  isAssigned:  " + isAssigned);
		init();
	}

	protected void init() {
		// TODO Auto-generated method stub
		setBtnVisiable(false);

		prompt = (TextView) findViewById(R.id.prompt);
		if (isAssigned == 0){
			prompt.setText(this.getString(R.string.assign_prompt));
			setTittleText(this.getString(R.string.unassigned));			
		}else{
			prompt.setText(this.getString(R.string.transfer_prompt));
			setTittleText(this.getString(R.string.assigned));						
		}

		list_user = (ListView) findViewById(R.id.list_user);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice,
				getName(getData()));
		list_user.setAdapter(adapter);
		
		next_step=(Button)findViewById(R.id.next_step);
		next_step.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch(v.getId()){
		case R.id.back:
			finish();
			break;
		case R.id.next_step:
			if(getListSelectededItemIds().length()>0){
				Intent intent=new Intent();
				intent.setClass(getApplicationContext(), MassignChooseSaList.class);
				intent.putExtra(Constant.CHANGE_USER_IDS, getListSelectededItemIds());
				startActivity(intent);		
			}else
				
			Log.e(TAG, "  getListSelectededItemIds(): "+getListSelectededItemIds());
			break;
			default:
				break;
		}
	}

	private ArrayList<String> getName(ArrayList<User> list) {
		ArrayList<String> listName = new ArrayList<String>();
		for (User user : list)
			listName.add(user.getRealName());
		return listName;
	}

	private ArrayList<User> getData() {
		// TODO Auto-generated method stub
		list = new ArrayList<User>();
		for (int i = 0; i < 50; i++) {
			User user = new User();
			user.setRealName("霍元甲第" + i);
			user.setUserId(2 * i);
			list.add(user);
		}
		return list;
	}

	public String getListSelectededItemIds() {
		String changeUserIds = "";
		long[] ids = getListSelectededItemIds(list_user);
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		if (ids.length > 0){
			for (int i = 0; i < ids.length; i++) {
				sb.append(ids[i] + ",");
				sb1.append(list.get((int) ids[i]).getUserId() + ",");
			}
			Log.e(TAG, "sb选中的位置是：  " + sb.toString());
			Log.e(TAG, "sb1组成的用户ids是：  " + sb1.toString());			
			changeUserIds=sb1.toString().substring(0, sb1.length()-1);
		}
		else
			Toast.makeText(getApplicationContext(), "请至少选择一个用户",Toast.LENGTH_SHORT).show();
		return changeUserIds;
	}
	
	// 获取多选的listview的id,避免使用getCheckItemIds()方法
	public long[] getListSelectededItemIds(ListView listView) {
		long[] ids = new long[listView.getCount()];// getCount()即获取到ListView所包含的item总个数
		// 定义用户选中Item的总个数
		int checkedTotal = 0;
		for (int i = 0; i < listView.getCount(); i++) {
			// 如果这个Item是被选中的
			if (listView.isItemChecked(i)) {
				ids[checkedTotal++] = i;
			}
		}
		if (checkedTotal < listView.getCount()) {
			// 定义选中的Item的ID数组
			final long[] selectedIds = new long[checkedTotal];
			// 数组复制 ids
			System.arraycopy(ids, 0, selectedIds, 0, checkedTotal);
			return selectedIds;
		} else {
			// 用户将所有的Item都选了
			return ids;
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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.e(TAG, "更新数据");
	}	
}
