package com.example.yijia.third.msa;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.bean.common.Service;
import com.example.yijia.third.bean.user.UserInfo;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

public class AaddMsaType extends BaseActivity {
	private Button next_step;
	private ListView list_service_type;
	private ArrayAdapter<String> adapter;
	private ArrayList<Service> list;
	private ArrayList<String> listType;
	private UserInfo userInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setSubView(R.layout.a_add_msa_type);
		
		userInfo=getIntent().getExtras().getParcelable(Constant.USER_INFO_KEY);
		Log.e(TAG, userInfo.toString());
		
		init();
	}
	
	protected void init(){
		setTittleText(this.getString(R.string.add_main_service));
		setBtnVisiable(false);
		
		list_service_type=(ListView)findViewById(R.id.list_service_type);
		adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,getData());
		list_service_type.setAdapter(adapter);	
		list_service_type.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				int position = list_service_type .getCheckedItemPosition();
				Log.e(TAG, "position是：  "+position);
				userInfo.setBoundServiceType(list.get(position).getServiceName());
				Log.e(TAG, "setBoundServiceType是：  "+list.get(position).getServiceName());
			}		
		});
		
		next_step=(Button)findViewById(R.id.next_step);
		next_step.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!userInfo.getBoundServiceType().equals("")&&userInfo.getBoundServiceType()!=null){
					Intent intent=new Intent(AaddMsaType.this,AaddMsaConfirm.class);
					Bundle mbundle=new Bundle();
					mbundle.putParcelable(Constant.USER_INFO_KEY, userInfo);
					intent.putExtras(mbundle);
					startActivity(intent);
					finish();
				}				
			}			
		});
	}

	private ArrayList<String> getData(){
		list=getDataService();
		listType=new ArrayList<String>();	
		for(Service service:list){
			listType.add(service.getServiceName());
		}
		return listType;
	}
	
	private ArrayList<Service> getDataService() {
		// TODO Auto-generated method stub
		list=new ArrayList<Service>();
		for(int i=0;i<5;i++){
			Service service=new Service();
			service.setServiceName("医生咨询  "+i);
			service.setServiceId(i);
			list.add(service);
		}
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
