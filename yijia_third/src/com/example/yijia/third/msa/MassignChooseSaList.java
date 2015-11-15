package com.example.yijia.third.msa;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.bean.sa.Sa;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

public class MassignChooseSaList extends BaseActivity implements OnItemClickListener{
	private String changeUserIds;
	private ListView list_user;
	private TextView prompt;
	private Button confirm;
	private ArrayAdapter<String> adapter;
	private ArrayList<Sa> list;
	private int position=-1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.m_assign_sa_list_choice);
		changeUserIds = getIntent().getStringExtra(Constant.CHANGE_USER_IDS);
		Log.e(TAG, "  收到的changeUserIds:  " + changeUserIds);
		init();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		setBtnVisiable(false);
		setTittleText(this.getString(R.string.bound_sa));// 从app中获取姓名
		
		prompt=(TextView)findViewById(R.id.prompt);
		prompt.setText(R.string.doc);
		
		list_user = (ListView) findViewById(R.id.list_user);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice,
				getName(getData()));
		list_user.setAdapter(adapter);
		list_user.setOnItemClickListener(this);
		
		confirm=(Button)findViewById(R.id.confirm);
		confirm.setOnClickListener(this);
	}
	
	private ArrayList<String> getName(ArrayList<Sa> list) {
		ArrayList<String> listName = new ArrayList<String>();
		for (Sa user : list)
			listName.add(user.getRealName());
		return listName;
	}

	private ArrayList<Sa> getData() {
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
		case R.id.confirm:
			if(position!=ListView.INVALID_POSITION){				
				Log.e(TAG, "  将要上传的saId: "+list.get(position).getId());
				Log.e(TAG, "  将要上传的changeUserIds: "+changeUserIds);
				
			}else
				Toast.makeText(getApplicationContext(), "请选择一名医生", Toast.LENGTH_SHORT).show();
			break;
			default:
				break;
		}
	}

//	private BroadcastReceiver broadcastReceiver=new BroadcastReceiver(){
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			// TODO Auto-generated method stub
//			
//		}	
//	};
	
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
		position= list_user.getCheckedItemPosition();
		Log.e(TAG, " position :"+position);
	}

}
