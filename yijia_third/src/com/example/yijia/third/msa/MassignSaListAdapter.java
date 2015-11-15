package com.example.yijia.third.msa;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yijia.third.bean.sa.Sa;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class MassignSaListAdapter extends BaseAdapter {
	
	public Context mContext;
	public ArrayList<Sa> list;
	public MassignSaListAdapter(Context mContext, ArrayList<Sa> list) {
		super();
		this.mContext = mContext;
		this.list = list;
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
		ViewHodler viewHolder=null;
		if(convertView==null){
			viewHolder=new ViewHodler();
			convertView=View.inflate(mContext, R.layout.a_query_msa_team, null);
			viewHolder.service_name=(TextView)convertView.findViewById(R.id.service_name);
			viewHolder.price=(TextView)convertView.findViewById(R.id.price);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHodler) convertView.getTag();		
		}
		viewHolder.service_name.setText(list.get(position).getRealName());
		//注意转换成String类型，否则会报错"android.content.res.Resources$NotFoundException: String resource ID #0x258"
		viewHolder.price.setText("用户数"+list.get(position).getPresentNum());
		return convertView;
	}

	private class ViewHodler{
		private TextView service_name,price;
	}
}

