/**
 * 
 */
package com.example.yijia.third.service;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.bean.common.Service;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

/**
 * @author Administrator
 *
 */
public class QueryServiceActivity extends BaseActivity implements OnItemClickListener{
	private ListView list_service;
	private ServiceAdapter madapter;
	private ArrayList<Service> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.a_query_service_list);
		init();
	}

	protected void init(){
		setBtnVisiable(false);
		setTittleText(this.getString(R.string.service_list));

		list_service=(ListView)findViewById(R.id.list_service);
		list_service.setOnItemClickListener(this);
		madapter=new ServiceAdapter(this,getData());
		list_service.setAdapter(madapter);
	}
	
	private ArrayList<Service> getData() {
		// TODO Auto-generated method stub
		list = new ArrayList<Service>();
		Service service1 = new Service();
		service1.setPrice(600);
		service1.setTypes("1");
		service1.setSaBouns(300);
		service1.setMsaBonus(60);
		service1.setAdminBouns(240);
		Service service2 = new Service();
		service2.setPrice(600);
		service2.setTypes("1,2");
		service2.setSaBouns(300);
		service2.setMsaBonus(60);
		service2.setAdminBouns(240);
		list.add(service1);
		list.add(service2);
		Log.e(TAG, "list.size()   "+list.size());
		return list;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Log.e(TAG, "+¡ª¡ª+¡ª¡ª+¡ª¡ª+¡ª¡ª+¡ª¡ª+µã»÷arg2£º "+arg2);
		Log.e(TAG, "+¡ª¡ª+¡ª¡ª+¡ª¡ª+¡ª¡ª+¡ª¡ª+µã»÷arg3£º "+arg3);	
		Intent intent=new Intent(QueryServiceActivity.this,ServiceDetail.class);
		Bundle mbundle=new Bundle();
		mbundle.putParcelable(Constant.SERVICE_BEAN, list.get(arg2));
		mbundle.putBoolean(Constant.EDITABLE, false);
		intent.putExtras(mbundle);
		startActivity(intent);
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
