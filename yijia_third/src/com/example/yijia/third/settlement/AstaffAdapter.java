/**
 * 
 */
package com.example.yijia.third.settlement;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yijia.third.bean.admin.Staff;
import com.example.yijia_third.R;

public class AstaffAdapter extends BaseAdapter{
	private Context mContext;
	private ArrayList<Staff> list;

	public AstaffAdapter(Context mContext, ArrayList<Staff> list) {
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
			convertView=View.inflate(mContext, R.layout.a_settlement_sec_item, null);
			hodler.date=(TextView)convertView.findViewById(R.id.date);
			hodler.income=(TextView)convertView.findViewById(R.id.income);	
			hodler.expend=(TextView)convertView.findViewById(R.id.expend);	
			convertView.setTag(hodler);
		}else{
			hodler=(ViewHodler) convertView.getTag();
		}
		hodler.date.setText(list.get(position).getDate());
		hodler.income.setText(list.get(position).getMsaNum()+"/"+list.get(position).getSaNum());
		hodler.expend.setText(list.get(position).getTotalUserNum()+"/"+list.get(position).getPresentUserNum());
		return convertView;
	}
	
	public class ViewHodler{
		private TextView income,date,expend;
	}
}
