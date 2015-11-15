package com.example.yijia.third.waveform;

import com.example.yijia_third.R;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;


/**
 * @author 丑旦
 * @date 创建时间：2015/10/8 上午11:29:26
 * @version 1.0
 * @parameter
 * @since
 * @return
 * 
 */
public class PopMenu extends PopupWindow {
	public PopMenuAdapter mAdapter;
	public Activity mContext;
	public ListView mListView;
	public View anchor;
	
	public PopMenu(PopMenuAdapter mAdapter, Activity mContext, View anchor) {
		super();
		this.mAdapter = mAdapter;
		this.mContext = mContext;
		this.anchor = anchor;
		init();
	}

	public void init(){
		View view = LayoutInflater.from(mContext).inflate(R.layout.waveform_pop_menu, null);	
		mListView = (ListView)view.findViewById(R.id.listView);
		mListView.setAdapter(mAdapter);
				
		this.setContentView(view);
		
		DisplayMetrics metrics = new DisplayMetrics();
		mContext.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int width = anchor.getWidth();
		System.out.println("=-=-=width=-=-="+width);
		
		this.setWidth(width / 5);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		this.setOutsideTouchable(true);
		this.setAnimationStyle(R.style.AnimationPopWindows);
		this.update();
		
		ColorDrawable drawable = new ColorDrawable(0000000000);
		// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
		this.setBackgroundDrawable(drawable);
	}
	
	public void show() {
		// TODO Auto-generated method stub
		if(!this.isShowing())
			showAsDropDown(anchor, 0, 0);	
		else
			dismiss();
	}

	public void setOnItemClick(OnItemClickListener listener){
		mListView.setOnItemClickListener(listener);
	}
}
