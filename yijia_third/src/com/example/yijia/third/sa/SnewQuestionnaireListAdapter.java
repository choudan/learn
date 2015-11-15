/**
 * 
 */
package com.example.yijia.third.sa;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.yijia.third.bean.common.Questionnaire;

public class SnewQuestionnaireListAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<Questionnaire> list;
	private boolean isVisiable;
	private boolean flag = false;

	public SnewQuestionnaireListAdapter(Context mContext,ArrayList<Questionnaire> list,boolean isVisiable) {
		super();
		this.mContext = mContext;
		this.list = list;
		this.isVisiable=isVisiable;
	}
	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
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
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		SnewQuestionnaireItem item;
		if (convertView == null) {
			item = new SnewQuestionnaireItem(mContext, null);	
		} else {
			item = (SnewQuestionnaireItem) convertView;
		}	
		item.setName(list.get(position).getQuestionnaire());
		item.setDate(list.get(position).getRealName());
		item.setVisiable(flag);	
		item.setTextVisiable(isVisiable);
		
		return item;
	}	
}

