package com.example.yijia.third.user;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.yijia.third.bean.common.Service;

public class UuserServiceTypeAdapter extends BaseAdapter {
	private ArrayList<Service> list;
	private Context mcontext;
	
	public UuserServiceTypeAdapter(Context mcontext,ArrayList<Service> list) {
		super();
		this.list = list;
		this.mcontext = mcontext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final UuserServiceTypeListItem userServiceTypeListItem;
		if(convertView==null){
			userServiceTypeListItem=new UuserServiceTypeListItem(mcontext,null);					
		}else{
			userServiceTypeListItem=(UuserServiceTypeListItem)convertView ;			
		}
		userServiceTypeListItem.setText(list.get(position).getServiceName()+"    "+list.get(position).getPrice());
		userServiceTypeListItem.setContent(list.get(position).getIntroduction());		
		userServiceTypeListItem.getBtn().setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				userServiceTypeListItem.setVisiable();
			}
		});
		return userServiceTypeListItem;
	}
}
