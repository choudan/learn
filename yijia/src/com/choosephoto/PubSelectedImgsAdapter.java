package com.choosephoto;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.yijia.R;

public class PubSelectedImgsAdapter extends BaseAdapter {

	Context context;
	List<String> list;
	public Bitmap bitmaps[];
	CompressUtil util;
	OnItemClickClass onItemClickClass;
	
	public PubSelectedImgsAdapter(Context context,List<String> data,OnItemClickClass onItemClickClass) {
		this.context=context;
		this.list=data;
		this.onItemClickClass=onItemClickClass;
		bitmaps=new Bitmap[data.size()];
		util=new CompressUtil(context);
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		Holder holder;
		if (view==null) {
			view=LayoutInflater.from(context).inflate(R.layout.pub_selected_imgs_item, null);
			holder=new Holder();
			holder.imageView=(ImageView) view.findViewById(R.id.imageView2);
			view.setTag(holder);
		}else {
			holder= (Holder) view.getTag();
		}
		util.imgExcute(holder.imageView,new ImgClallBackLisner(position), list.get(position));
		view.setOnClickListener(new OnPhotoClick(list.get(position)));
		return view;
	}
	
	class Holder{
		ImageView imageView;
	}

	public class ImgClallBackLisner implements ImgCallBack{
		int num;
		public ImgClallBackLisner(int num) {
			this.num=num;
		}
		
		@Override
		public void resultImgCall(ImageView imageView, Bitmap bitmap) {
			bitmaps[num]=bitmap;
			imageView.setImageBitmap(bitmap);
		}
	}

	public interface OnItemClickClass{
		public void OnItemClick(View v,String filepath);
	}
	
	class OnPhotoClick implements OnClickListener{
		String filepath;
		
		public OnPhotoClick(String  filepath) {
			this.filepath=filepath;
		}
		@Override
		public void onClick(View v) {
			if (list!=null && onItemClickClass!=null ) {
				onItemClickClass.OnItemClick(v, filepath);
			}
		}
	}
	
}
