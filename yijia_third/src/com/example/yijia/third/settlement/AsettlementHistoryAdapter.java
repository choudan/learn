/**
 * 
 */
package com.example.yijia.third.settlement;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.yijia.third.bean.common.SettlementTask;
import com.example.yijia_third.R;

public class AsettlementHistoryAdapter extends BaseAdapter{
	private Context mContext;
	private ArrayList<SettlementTask> list;

	public AsettlementHistoryAdapter(Context mContext, ArrayList<SettlementTask> list) {
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
			convertView=View.inflate(mContext, R.layout.a_settlement_fou_item, null);
			hodler.settlement_date=(Button)convertView.findViewById(R.id.settlement_date);
//			hodler.msa_sa_settlement=(LinearLayout)convertView.findViewById(R.id.msa_sa_settlement);	
			convertView.setTag(hodler);
		}else{
			hodler=(ViewHodler) convertView.getTag();
		}
		hodler.settlement_date.setText(list.get(position).getSettlementTime());
		return convertView;
	}
	
	public class ViewHodler{
		private Button settlement_date;
//		private LinearLayout msa_sa_settlement;
	}
}
