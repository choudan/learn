/**
 * 
 */
package com.example.yijia.third.picture;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * @author Administrator
 * 
 */
public class GridViewAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<String> imageUrls;
	
	public GridViewAdapter(Context context, ArrayList<String> imageUrls) {
		super();
		this.context = context;
		this.imageUrls = imageUrls;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imageUrls.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return imageUrls.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView image;
		if (convertView == null) {
			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(70, 70);
			image = new ImageView(context);
			image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			image.setLayoutParams(lp);
			image.setPadding(5, 5, 5, 5);
		} else {
			image = (ImageView) convertView;
		}
		DisplayImageOptions options = new DisplayImageOptions.Builder()//
				.cacheInMemory(true)//
				.cacheOnDisk(true)//
				.bitmapConfig(Config.RGB_565)//
				.build();
		ImageLoader.getInstance().displayImage(imageUrls.get(position),
				image, options);
		return image;
	}

}
