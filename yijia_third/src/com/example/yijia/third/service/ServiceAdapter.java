/**
 * 
 */
package com.example.yijia.third.service;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yijia.third.base.BaseAbsAdapter;
import com.example.yijia.third.bean.common.Service;
import com.example.yijia_third.R;

/**
 * @author Administrator
 *
 */
public class ServiceAdapter extends BaseAbsAdapter<Service> {
	
	public ServiceAdapter(Context context, ArrayList<Service> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}
	
//	public Context mContext;
//	public ArrayList<Service> list;
//	public ServiceAdapter(Context mContext, ArrayList<Service> list) {
//		super();
//		this.mContext = mContext;
//		this.list = list;
//	}
//	
//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
//		return list.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		// TODO Auto-generated method stub
//		return list.get(position);
//	}
//
//	@Override
//	public long getItemId(int position) {
//		// TODO Auto-generated method stub
//		return position;
//	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHodler viewHolder=null;
		if(convertView==null){
			viewHolder=new ViewHodler();
			convertView=View.inflate(context, R.layout.a_query_service_list_item, null);
			viewHolder.service_name=(TextView)convertView.findViewById(R.id.service_name);
			viewHolder.price=(TextView)convertView.findViewById(R.id.price);
			viewHolder.service_content=(TextView)convertView.findViewById(R.id.service_content);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHodler) convertView.getTag();		
		}
		viewHolder.service_name.setText(list.get(position).getServiceName());
		//注意转换成String类型，否则会报错"android.content.res.Resources$NotFoundException: String resource ID #0x258"
		viewHolder.price.setText(""+list.get(position).getPrice());
		viewHolder.service_content.setText("服务内容");
		return convertView;
	}

	private class ViewHodler{
		private TextView service_name,price,service_content;
	}
}
