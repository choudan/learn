package com.example.yijia.third.base;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @author  丑旦 
 * @date 创建时间：2015/10/22 下午4:10:25
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 *
 */
public class BaseAbsAdapter<T> extends BaseAdapter{
	public Context context;
	public ArrayList<T> list;
	
	public BaseAbsAdapter(Context context, ArrayList<T> list) {
		super();
		this.context = context;
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
		return null;
	}	
}
