/**
 * 
 */
package com.example.yijia.third.msa;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.yijia.third.bean.common.Questionnaire;
import com.example.yijia.wiget.swipelist.OnDeleteListioner;
import com.example.yijia_third.R;

public class MquestionnaireSwipeListAdapter extends BaseAdapter {
	private ArrayList<Questionnaire> list;
	private LayoutInflater inflater;// View.inflate()方法性能不好，慎用
	private OnDeleteListioner mOnDeleteListioner;

	public MquestionnaireSwipeListAdapter(Context mContext,
			ArrayList<Questionnaire> list) {
		super();
		this.list = list;
		inflater = LayoutInflater.from(mContext);
	}

	public void setOnDeleteListioner(OnDeleteListioner mOnDeleteListioner) {
		this.mOnDeleteListioner = mOnDeleteListioner;
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

	// 要实现这两个方法
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		Questionnaire entity = list.get(position);
		int type = -1;
		if (entity.isMine())
			type = 0;
		else
			type = 1;
		return type;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder hodler = null;
		if (convertView == null) {
			hodler = new ViewHolder();
			convertView = inflater.inflate(R.layout.m_assign_sa_list_swipe_item, parent, false);
			hodler.service_name = (TextView) convertView.findViewById(R.id.service_name);
			hodler.price = (TextView) convertView.findViewById(R.id.price);
			hodler.delete = (Button) convertView.findViewById(R.id.delete);
			convertView.setTag(hodler);
		} else
			hodler = (ViewHolder) convertView.getTag();
		hodler.service_name.setText(list.get(position).getQuestionnaire());
		hodler.price.setText(list.get(position).getRealName());
		hodler.delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				if (mOnDeleteListioner != null)
					mOnDeleteListioner.onDelete(position);
				Log.e(" =-=-=-=-=-=-position    ", "" + position);
			}
		});
		return convertView;
	}

	public class ViewHolder {
		public TextView service_name, price;
		private Button delete;
	}
}
