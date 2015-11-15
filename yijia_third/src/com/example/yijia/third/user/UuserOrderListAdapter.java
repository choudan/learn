package com.example.yijia.third.user;

import java.util.ArrayList;

import com.example.yijia.third.bean.common.Order;
import com.example.yijia_third.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class UuserOrderListAdapter extends BaseAdapter {
	private ArrayList<Order> list;
	private Context mcontext;
	private LayoutInflater mInflater;
	
	public UuserOrderListAdapter(ArrayList<Order> list, Context mcontext) {
		super();
		this.list = list;
		this.mcontext = mcontext;
		mInflater=LayoutInflater.from(mcontext);
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
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		if(list.get(position).getIsValid()==0)
			return 0;
		else if(list.get(position).getIsValid()==1)
			return 1;
		return super.getItemViewType(position);
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder=null;		
		if(getItemViewType(position)==0){
			if(convertView==null){
				viewHolder=new ViewHolder();
				convertView=mInflater.inflate(R.layout.u_user_order_list_item, null);
				viewHolder.contract_extension=(Button)convertView.findViewById(R.id.contract_extension);
				Log.e("mcontext.getString(R.string.service_valid_)", mcontext.getString(R.string.service_valid_));
				viewHolder.service_valid=(TextView)convertView.findViewById(R.id.service_valid);
				viewHolder.service_valid.setText(mcontext.getString(R.string.service_valid_));
				viewHolder.contract_extension.setVisibility(View.INVISIBLE);				
				convertView.setTag(viewHolder);
			}else{
				viewHolder=(ViewHolder) convertView.getTag();
			}
		}else{
			if(convertView==null){
				viewHolder=new ViewHolder();
				convertView=mInflater.inflate(R.layout.u_user_order_list_item, null);
				viewHolder.contract_extension=(Button)convertView.findViewById(R.id.contract_extension);
				viewHolder.service_valid=(TextView)convertView.findViewById(R.id.service_valid);
				convertView.setTag(viewHolder);				
			}else{
				viewHolder=(ViewHolder) convertView.getTag();				
			}
			viewHolder.service_valid.setText(mcontext.getString(R.string.service_valid));
			viewHolder.contract_extension.setOnClickListener(new OnClickListener() {		
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(mOnBtnClicker!=null)
						mOnBtnClicker.onBtnClicker(v, position);
				}
			});
		}	
		viewHolder.service_deadline=(TextView)convertView.findViewById(R.id.service_deadline);
		viewHolder.service_name=(TextView)convertView.findViewById(R.id.service_name);
		viewHolder.service_startTime=(TextView)convertView.findViewById(R.id.service_startTime);
		viewHolder.service_providerUnit=(TextView)convertView.findViewById(R.id.service_providerUnit);
		viewHolder.service_providerName=(TextView)convertView.findViewById(R.id.service_providerName);
		
		viewHolder.service_deadline.setText(mcontext.getString(R.string.service_deadline)+list.get(position).getDeadline());
		viewHolder.service_name.setText(mcontext.getString(R.string.service_name)+list.get(position).getServiceName());
		viewHolder.service_startTime.setText(mcontext.getString(R.string.service_startTime)+list.get(position).getStartTime());
		viewHolder.service_providerUnit.setText(mcontext.getString(R.string.service_providerUnit)+list.get(position).getProviderUnit());
		viewHolder.service_providerName.setText(mcontext.getString(R.string.service_providerName)+list.get(position).getProviderName());	

//		ViewHolder viewHolder=null;		
//		if(getItemViewType(position)==0){
//			if(convertView==null){
//				viewHolder=new ViewHolder();
//				convertView=mInflater.inflate(R.layout.u_user_order_list_item, null);
//				viewHolder.service_deadline=(TextView)convertView.findViewById(R.id.service_deadline);
//				viewHolder.service_name=(TextView)convertView.findViewById(R.id.service_name);
//				viewHolder.service_startTime=(TextView)convertView.findViewById(R.id.service_startTime);
//				viewHolder.service_providerUnit=(TextView)convertView.findViewById(R.id.service_providerUnit);
//				viewHolder.service_providerName=(TextView)convertView.findViewById(R.id.service_providerName);
//				viewHolder.contract_extension=(Button)convertView.findViewById(R.id.contract_extension);
//				Log.e("mcontext.getString(R.string.service_valid_)", mcontext.getString(R.string.service_valid_));
//				viewHolder.service_valid=(TextView)convertView.findViewById(R.id.service_valid);
//				viewHolder.service_valid.setText(mcontext.getString(R.string.service_valid_));
//				viewHolder.contract_extension.setVisibility(View.INVISIBLE);				
//				convertView.setTag(viewHolder);
//			}else{
//				viewHolder=(ViewHolder) convertView.getTag();
//			}
//		}else{
//			if(convertView==null){
//				viewHolder=new ViewHolder();
//				convertView=mInflater.inflate(R.layout.u_user_order_list_item, null);
//				viewHolder.service_deadline=(TextView)convertView.findViewById(R.id.service_deadline);
//				viewHolder.service_name=(TextView)convertView.findViewById(R.id.service_name);
//				viewHolder.service_startTime=(TextView)convertView.findViewById(R.id.service_startTime);
//				viewHolder.service_providerUnit=(TextView)convertView.findViewById(R.id.service_providerUnit);
//				viewHolder.service_providerName=(TextView)convertView.findViewById(R.id.service_providerName);
//				viewHolder.contract_extension=(Button)convertView.findViewById(R.id.contract_extension);
//				viewHolder.service_valid=(TextView)convertView.findViewById(R.id.service_valid);
//				convertView.setTag(viewHolder);				
//			}else{
//				viewHolder=(ViewHolder) convertView.getTag();				
//			}
//			viewHolder.service_valid.setText(mcontext.getString(R.string.service_valid));
//			viewHolder.contract_extension.setOnClickListener(new OnClickListener() {		
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					if(mOnBtnClicker!=null)
//						mOnBtnClicker.onBtnClicker(v, position);
//				}
//			});
//		}	
//		viewHolder.service_deadline.setText(mcontext.getString(R.string.service_deadline)+list.get(position).getDeadline());
//		viewHolder.service_name.setText(mcontext.getString(R.string.service_name)+list.get(position).getServiceName());
//		viewHolder.service_startTime.setText(mcontext.getString(R.string.service_startTime)+list.get(position).getStartTime());
//		viewHolder.service_providerUnit.setText(mcontext.getString(R.string.service_providerUnit)+list.get(position).getProviderUnit());
//		viewHolder.service_providerName.setText(mcontext.getString(R.string.service_providerName)+list.get(position).getProviderName());	
		return convertView;
	}
	
	private OnBtnClicker mOnBtnClicker=null;
	
	public interface OnBtnClicker{
		public void onBtnClicker(View v,int position);
	}
	
	public void setOnBtnClicker(OnBtnClicker mOnBtnClicker){
		this.mOnBtnClicker=mOnBtnClicker;//调用该方法才能将接口实例化；
	}
	public class ViewHolder{
		public TextView service_valid, service_name, service_startTime,
				service_deadline, service_providerUnit, service_providerName;
		public Button contract_extension;
	}
}
