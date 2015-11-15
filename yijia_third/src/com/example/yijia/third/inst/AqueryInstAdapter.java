package com.example.yijia.third.inst;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yijia.third.bean.inst.Inst;
import com.example.yijia_third.R;

/**
 * @author  丑旦 
 * @date 创建时间：2015-8-22 上午11:32:20 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class AqueryInstAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Inst> list;

	public AqueryInstAdapter(Context context, ArrayList<Inst> list) {
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
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=View.inflate(context, R.layout.a_query_inst_item, null);
			holder.inst_name=(TextView)convertView.findViewById(R.id.inst_name);
//			holder.inst_info_detail=(LinearLayout)convertView.findViewById(R.id.inst_info_detail);			
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder)convertView.getTag();
		}
		holder.inst_name.setText(list.get(position).getInstName());
//		holder.inst_info.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//		});
//		holder.inst_user_info.setOnClickListener(new OnClickListener(){
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//		});
		return convertView;
	}
	
	public class ViewHolder{
		private TextView inst_name;
//		private LinearLayout inst_info_detail;
//		private Button inst_info,inst_user_info;

	}

}
