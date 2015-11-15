/**
 * 
 */
package com.example.yijia.third.code;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yijia.third.bean.common.CodeConsuneDetail;
import com.example.yijia_third.R;

/**
 * @author Administrator
 *
 */
public class CodeAdapter extends BaseAdapter {
	public Context mContext;
	public ArrayList<CodeConsuneDetail> list;
	public CodeAdapter(Context mContext, ArrayList<CodeConsuneDetail> list) {
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
		ViewHodler viewHolder=null;
		if(convertView==null){
			viewHolder=new ViewHodler();
			convertView=View.inflate(mContext, R.layout.a_query_code_state_detai_item, null);
			viewHolder.code_consumer=(TextView)convertView.findViewById(R.id.code_consumer);
			viewHolder.code=(TextView)convertView.findViewById(R.id.code);
			viewHolder.consume_date=(TextView)convertView.findViewById(R.id.consume_date);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHodler) convertView.getTag();		
		}
		viewHolder.code_consumer.setText(list.get(position).getCodeConsumer());
		//注意转换成String类型，否则会报错"android.content.res.Resources$NotFoundException: String resource ID #0x258"
		viewHolder.code.setText(list.get(position).getCode());
		viewHolder.consume_date.setText(list.get(position).getConsumeDate());
		return convertView;
	}

	private class ViewHodler{
		private TextView code_consumer,code,consume_date;
	}
}
