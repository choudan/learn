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
import android.widget.TextView;

import com.example.yijia.third.bean.common.SettlementDetail;
import com.example.yijia_third.R;
public class AsettlementAdapter extends BaseAdapter{
	private Context mContext;
	private ArrayList<SettlementDetail> list;
	private int flag;

	public AsettlementAdapter(Context mContext,
			ArrayList<SettlementDetail> list, int flag) {
		super();
		this.mContext = mContext;
		this.list = list;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHodler hodler=null;
		if(convertView==null){
			hodler=new ViewHodler();
			convertView=View.inflate(mContext, R.layout.a_settlement_fou_item_next_item, null);
			hodler.msa_textview=(TextView)convertView.findViewById(R.id.msa_textview);
			hodler.sa=(TextView)convertView.findViewById(R.id.sa);	
			hodler.total_num=(TextView)convertView.findViewById(R.id.total_num);	
			hodler.msa_btn=(Button)convertView.findViewById(R.id.msa_btn);				
			hodler.sum=(TextView)convertView.findViewById(R.id.sum);				
			if(flag==0){
				hodler.msa_textview.setVisibility(View.GONE);
			}else if(flag==1){
				hodler.msa_btn.setVisibility(View.GONE);
			}
			convertView.setTag(hodler);
		}else{
			hodler=(ViewHodler) convertView.getTag();
		}
		if(flag==0)
			hodler.msa_btn.setText(list.get(position).getRealName());
		else if(flag==1)	
			hodler.msa_textview.setText(list.get(position).getRealName());		
		hodler.sa.setText(""+list.get(position).getTeamNum());
		hodler.total_num.setText(""+list.get(position).getTotalNum());
		hodler.sum.setText(""+list.get(position).getTotalIncome());		
		return convertView;
	}
	
	public class ViewHodler{
		private TextView msa_textview,sa,total_num,sum;
		private Button msa_btn;
	}
}
