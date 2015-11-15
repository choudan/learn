package com.example.yijia.third.uploadphoto;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.yijia_third.R;

public class GridAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<String> list;
	private OnGridItemClick onGridItemClick;
	
	public GridAdapter(Context context, ArrayList<String> list) {
		super();
		this.context = context;
		this.list = list;
	}

	public void setOnGridItemClick(OnGridItemClick onGridItemClick) {
		this.onGridItemClick = onGridItemClick;
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
	public View getView(final int i, View view, ViewGroup viewgroup) {
		// TODO Auto-generated method stub
		Holder holder;
		if (view==null) {
			holder=new Holder();
			view=LayoutInflater.from(context).inflate(R.layout.u_grid_selected_imgs_item, null);
			holder.imageView=(ImageView) view.findViewById(R.id.imageView2);
			view.setTag(holder);
		}else {
			holder= (Holder) view.getTag();
		}
		holder.imageView.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				if(onGridItemClick!=null){
					onGridItemClick.onGridClick(view,list.get(i));
				}
			}
		});
		new LoadBitAsynk(holder.imageView).imgExcute(list.get(i));
		return view;
	}
	
	class Holder{
		ImageView imageView;
	}
	
	interface OnGridItemClick{
		public void onGridClick(View view,String path );
	}
}
