/*package com.example.yijia.third.msa;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yijia.third.bean.Sa;
import com.example.yijia.third.common.SwipeListView;
import com.example.yijia_third.R;

*//**
 * @author Administrator
 * 
 *//*
public class MquerySaListAdapter extends BaseAdapter {

	public Context mContext;
	public ArrayList<Sa> list;
	public SwipeListView swipeListView;
	private OnRightListioner mOnRightListioner;
	private int mRightWidth = 0;

	public interface OnRightListioner{
		public void onRightClick(View v,int position);
	}
	
	public MquerySaListAdapter(Context mContext, ArrayList<Sa> list,
			int rightWidth) {
		super();
		this.mContext = mContext;
		this.list = list;
		mRightWidth = rightWidth;
	}
	
	public void setOnRightListioner(OnRightListioner mOnRightListioner) {
		this.mOnRightListioner = mOnRightListioner;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHodler viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHodler();
			convertView = View.inflate(mContext,R.layout.m_assign_sa_list_swipe_item, null);
			viewHolder.service_name = (TextView) convertView.findViewById(R.id.service_name);
			viewHolder.price = (TextView) convertView.findViewById(R.id.price);
			viewHolder.delete = (TextView) convertView.findViewById(R.id.delete);
			viewHolder.item_left = (RelativeLayout) convertView.findViewById(R.id.item_left);
			viewHolder.item_right = (RelativeLayout) convertView.findViewById(R.id.item_right);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHodler) convertView.getTag();
		}

		LinearLayout.LayoutParams lp1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		viewHolder.item_left.setLayoutParams(lp1);
		LinearLayout.LayoutParams lp2 = new LayoutParams(mRightWidth,LayoutParams.MATCH_PARENT);
		viewHolder.item_right.setLayoutParams(lp2);
		viewHolder.service_name.setText(list.get(position).getRealName());
		// 注意转换成String类型，否则会报错"android.content.res.Resources$NotFoundException: String resource ID #0x258"
		viewHolder.price.setText("用户数" + list.get(position).getPresentNum());
		viewHolder.delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mOnRightListioner != null)
					mOnRightListioner.onRightClick(v,position);
				Log.e(" =-=-=-=-=-=-position    ", "" + position);
			}
		});
		return convertView;
	}

	private class ViewHodler {
		private TextView service_name, price;
		private TextView delete;
		private RelativeLayout item_left;
		private RelativeLayout item_right;
	}
}
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

import com.example.yijia.third.bean.sa.Sa;
import com.example.yijia.wiget.swipelist.OnDeleteListioner;
import com.example.yijia.wiget.swipelist.SwipeListView;
import com.example.yijia_third.R;

 /**
 * @author Administrator
 * 
 */
public class MquerySaListAdapter extends BaseAdapter {

	public Context mContext;
	public ArrayList<Sa> list;
	public SwipeListView swipeListView;
	private OnDeleteListioner mOnDeleteListioner;
	private LayoutInflater mInflater;

	public interface OnRightListioner{
		public void onRightClick(View v,int position);
	}
	
	public MquerySaListAdapter(Context mContext, ArrayList<Sa> list) {
		super();
		this.mContext = mContext;
		this.list = list;
		mInflater=LayoutInflater.from(mContext);
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

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHodler viewHolder = null;		
		if (convertView == null) {
			viewHolder = new ViewHodler();
			convertView = mInflater.inflate(R.layout.m_assign_sa_list_swipe_item, null);
			viewHolder.service_name = (TextView) convertView.findViewById(R.id.service_name);
			viewHolder.price = (TextView) convertView.findViewById(R.id.price);
			viewHolder.delete = (Button) convertView.findViewById(R.id.delete);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHodler) convertView.getTag();
		}
		viewHolder.service_name.setText(list.get(position).getRealName());
		// 注意转换成String类型，否则会报错"android.content.res.Resources$NotFoundException: String resource ID #0x258"
		viewHolder.price.setText("用户数" + list.get(position).getPresentNum());
		viewHolder.delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mOnDeleteListioner != null)
					mOnDeleteListioner.onDelete(position);
				Log.e(" =-=-=-=-=-=-position    ", "" + position);
			}
		});
		return convertView;
	}

	private class ViewHodler {
		private TextView service_name, price;
//		private TextView delete;
		private Button delete;
	}
}
  