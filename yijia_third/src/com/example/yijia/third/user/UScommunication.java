/**
 * 
 */
package com.example.yijia.third.user;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.base.BaseApp;
import com.example.yijia.third.bean.common.Communication;
import com.example.yijia.third.tool.CalendarUtils;
import com.example.yijia.third.uploadphoto.UaddPath;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class UScommunication extends BaseActivity implements
		OnEditorActionListener, OnRefreshListener {
	private ListView list_communication_record;
	private CommunicationAdapter mAdapter;
	private ArrayList<Communication> list;
	private EditText message;
	private Button add;
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private RelativeLayout rl_bottom;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.u_s_communication);
		init();
	}

	protected void init() {
		// TODO Auto-generated method stub
		setBtnVisiable(false);
		setTittleText(this.getString(R.string.communication));

		list = getData();

		message = (EditText) findViewById(R.id.message);
		message.setOnEditorActionListener(this);
		message.setOnClickListener(this);

		add = (Button) findViewById(R.id.add);
		add.setOnClickListener(this);

		list_communication_record = (ListView) findViewById(R.id.list_communication_record);
		mAdapter = new CommunicationAdapter(this, list);
		list_communication_record.setAdapter(mAdapter);

		mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshable_view);
		mSwipeRefreshLayout.setOnRefreshListener(this);
		
		rl_bottom = (RelativeLayout)findViewById(R.id.rl_bottom);
		if(((BaseApp)getApplication()).getRole()!=3&&((BaseApp)getApplication()).getRole()!=4)
			rl_bottom.setVisibility(View.GONE);	
		setEditable(message, false);
	}

	private ArrayList<Communication> getData() {
		// TODO Auto-generated method stub
		list = new ArrayList<Communication>();
		for (int i = 0; i < 9; i++) {
			Communication communication = new Communication();
			communication.setDate("2015-08-25 14:0" + i + ":00");
			communication.setRealName("张三");
			communication.setText("头疼");
			communication.setType(0);
			list.add(communication);
		}
		return list;
	}

	private void send() {
		String content = message.getText().toString();
		if (content.length() > 0) {
			Communication entity = new Communication();
			entity.setDate(CalendarUtils.dateTime());
			entity.setText(content);
			entity.setSaName(((BaseApp) getApplication()).getRoleName());
			if (((BaseApp) getApplication()).getRole() == 4) {
				entity.setType(0);
				entity.setUserId(((BaseApp) getApplication()).getRoleId());
			} else {
				entity.setType(1);
				entity.setSaId(((BaseApp) getApplication()).getRoleId());
			}
			list.add(entity);
			mAdapter.notifyDataSetChanged();
			list_communication_record.setSelection(list_communication_record.getCount() - 1);
			message.setText("");

			// new Thread(upWords).start();
		} else {
			Toast.makeText(UScommunication.this, "发送内容不能为空", Toast.LENGTH_SHORT)
					.show();
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.add:
			Intent intent = new Intent(UScommunication.this, UaddPath.class);
			startActivity(intent);
			break;
		case R.id.back:
			break;
		case R.id.message:
			setEditable(message, true);
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onEditorAction(TextView textview, int i, KeyEvent keyevent) {
		// TODO Auto-generated method stub
		if (i == KeyEvent.ACTION_DOWN || i == EditorInfo.IME_ACTION_SEND) {
			Log.e(TAG, "发送...");
			send();
		}
		return true;
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		mSwipeRefreshLayout.setRefreshing(true);
		mSwipeRefreshLayout.setColorSchemeResources(
				android.R.color.holo_blue_light,
				android.R.color.background_light,
				android.R.color.holo_blue_light,
				android.R.color.background_light);
		Toast.makeText(getApplicationContext(), "下拉刷新", Toast.LENGTH_SHORT).show();
	}

}
