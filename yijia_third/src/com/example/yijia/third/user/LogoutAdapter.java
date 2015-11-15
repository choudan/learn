/**
 * 
 */
package com.example.yijia.third.user;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yijia.third.bean.user.User;
import com.example.yijia_third.R;

public class LogoutAdapter extends BaseAdapter{
	private Context mContext;
	private ArrayList<User> list;

	public LogoutAdapter(Context mContext, ArrayList<User> list) {
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
		ViewHodler hodler=null;
		if(convertView==null){
			hodler=new ViewHodler();
			convertView=View.inflate(mContext, R.layout.a_logout_simple_userinfo_item, null);
			hodler.name=(TextView)convertView.findViewById(R.id.name);
			hodler.date=(TextView)convertView.findViewById(R.id.date);	
			convertView.setTag(hodler);
		}else{
			hodler=(ViewHodler) convertView.getTag();
		}
		hodler.name.setText(list.get(position).getRealName());
		hodler.date.setText(list.get(position).getLogoutDate());
		return convertView;
	}
	
	public class ViewHodler{
		private TextView name,date;
	}
}
