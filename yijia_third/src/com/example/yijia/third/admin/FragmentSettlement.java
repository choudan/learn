package com.example.yijia.third.admin;

import com.example.yijia_third.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class FragmentSettlement extends BaseFragment implements OnClickListener {
	private Button assign,statistics;
	
	public interface OnBtnSettleClickListener {
		void onBtnSettleClick(View v);
	};
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.a_settlement_fir, container, false);
		init();		
		return view;
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if(getActivity()instanceof OnBtnSettleClickListener){
			((OnBtnSettleClickListener)getActivity()).onBtnSettleClick(view);
		}		
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		assign=(Button) view.findViewById(R.id.assign);
		assign.setOnClickListener(this);
		
		statistics=(Button) view.findViewById(R.id.statistics);
		statistics.setOnClickListener(this);
		
		TextView username = (TextView) view.findViewById(R.id.username);	
		username.setText(this.getString(R.string.settlement));
		
		Button back = (Button)view.findViewById(R.id.back);
		back.setVisibility(View.GONE);//add,query,settlement单独处理标题栏
	}	
}
