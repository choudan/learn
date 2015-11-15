package com.example.yijia.third.uploadphoto;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.example.yijia_third.R;

public class AllGridAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<String> list;
	private Bitmap[] bitmap;
	private int index=-1;
	private List<View> holderlist;
	private OnItemClick onItemClick;
	
	public AllGridAdapter(Context context, ArrayList<String> list,OnItemClick onItemClick) {
		super();
		this.context = context;
		this.list = list;
		this.bitmap = new Bitmap[list.size()];
		this.holderlist = new ArrayList<View>();
		this.onItemClick = onItemClick;	
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
	public View getView(final int arg0, View arg1, ViewGroup viewgroup) {
		// TODO Auto-generated method stub
		final Holder holder;
		if (arg0 != index && arg0 > index) {
			index=arg0;
			holder=new Holder();
			arg1=LayoutInflater.from(context).inflate(R.layout.u_grid_all_imgs_item, null);
			holder.imageView=(ImageView) arg1.findViewById(R.id.imageView1);
			holder.checkBox=(CheckBox) arg1.findViewById(R.id.checkBox1);
			arg1.setTag(holder);
			holderlist.add(arg1);
		}else {
			holder= (Holder)holderlist.get(arg0).getTag();
			arg1=holderlist.get(arg0);
		}
		if (bitmap[arg0] == null) {
			new LoadBitAsynk(holder.imageView).imgExcute(list.get(arg0));	
		}else {
			holder.imageView.setImageBitmap(bitmap[arg0]);
		}

		arg1.setOnClickListener(new OnPhotoClick(arg0,holder.checkBox));
		return arg1;
	}
	
	class Holder{
		ImageView imageView;
		CheckBox checkBox;
	}
	
	public interface OnItemClick{
//		public void onItemClick(View v, int arg0, CheckBox checkBox);	
		public void onItemClick(View buttonView, int arg0, boolean isChecked);		
	}
	
	class OnPhotoClick implements OnClickListener{
		int position;
		CheckBox checkBox;
		
		public OnPhotoClick(int position,CheckBox checkBox) {
			this.position=position;
			this.checkBox=checkBox;
		}
		
		@Override
		public void onClick(View v) {
			if(checkBox.isChecked()){
				checkBox.setChecked(false);			
			}else{
				checkBox.setChecked(true);			
			}			
			if (list!=null && onItemClick!=null ) {
				Log.e("回调", "回调已执行...");
				onItemClick.onItemClick(v, position, checkBox.isChecked());
			}	
		}
	}	
}

	
