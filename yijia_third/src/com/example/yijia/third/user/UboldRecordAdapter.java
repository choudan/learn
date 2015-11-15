package com.example.yijia.third.user;

import java.util.ArrayList;

import android.content.Context;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yijia.third.bean.common.Record;
import com.example.yijia_third.R;

public class UboldRecordAdapter extends BaseAdapter {
	private ArrayList<Record> list;
	private LayoutInflater mLayoutInflater;

	public UboldRecordAdapter(Context mContext, ArrayList<Record> list) {
		super();
		this.list = list;
		this.mLayoutInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int i) {
		// TODO Auto-generated method stub
		return list.get(i);
	}

	@Override
	public long getItemId(int i) {
		// TODO Auto-generated method stub
		return i;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		if(list.get(position).getIsFinished()==0)
			return 0;
		else if(list.get(position).getIsFinished()==1)
			return 1;
		return super.getItemViewType(position);
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewgroup) {
		// TODO Auto-generated method stub
//		ViewHolder viewHolder = null;
//		if(getItemViewType(i)==0){
//			if(view==null){
//				viewHolder = new ViewHolder();
//				view = mLayoutInflater.inflate(R.layout.u_query_questionnaire_list_item, null);
//				viewHolder.date = (TextView) view.findViewById(R.id.date);
//				TextPaint paint = viewHolder.date.getPaint();
//				paint.setFakeBoldText(true);
//				view.setTag(viewHolder);					
//			}else
//				viewHolder = (ViewHolder) view.getTag();				
//		}else if(getItemViewType(i)==1){
//			if(view==null){
//				viewHolder = new ViewHolder();
//				view = mLayoutInflater.inflate(R.layout.u_query_questionnaire_list_item, null);
//				viewHolder.date = (TextView) view.findViewById(R.id.date);
//				view.setTag(viewHolder);					
//			}else
//				viewHolder = (ViewHolder) view.getTag();							
//		}
//		viewHolder.date.setText(list.get(i).getSendTime());
			
		ViewHolder viewHolder = null;
		if (view == null) {
			viewHolder = new ViewHolder();
			view = mLayoutInflater.inflate(R.layout.u_query_questionnaire_list_item, null);
			viewHolder.date = (TextView) view.findViewById(R.id.date);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		if (list.get(i).getIsFinished() == 0) {
			TextPaint paint = viewHolder.date.getPaint();
			paint.setFakeBoldText(true);
		}
		viewHolder.date.setText(list.get(i).getSendTime());
		return view;
	}

	class ViewHolder {
		TextView date;
	}
}
