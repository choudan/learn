package com.example.yijia.third.waveform;

import java.util.List;

import com.example.yijia_third.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author  丑旦 
 * @date 创建时间：2015/10/8 下午5:10:53
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 *
 */
public class PopMenuAdapter extends BaseAdapter{
	public List<String> list;
	public Context mContext;
	
	public PopMenuAdapter(List<String> list, Context mContext) {
		super();
		this.list = list;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int i) {
		// TODO Auto-generated method stub
		return list.get(i);
	}

	@Override
	public long getItemId(int i) {
		// TODO Auto-generated method stub
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewgroup) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		System.out.println("=-=-=list.size()=-=-="+list.size());
		if (view == null) {
			holder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.waveform_pomenu_item, null);
			holder.groupItem = (TextView) view.findViewById(R.id.textView);
			view.setTag(holder);
		} else 
			holder = (ViewHolder) view.getTag();
		holder.groupItem.setText(list.get(i));
		return view;
	}

	class ViewHolder {
		TextView groupItem;
	}
}
