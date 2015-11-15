package com.example.yijia.third.admin;

import com.example.yijia_third.R;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;


public class FragmentTittle extends Fragment {
	private FragmentManager fm;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub	
		View view = inflater.inflate(R.layout.fragment_tittle, container, false);

		// ¿ªÆôFragmentÊÂÎñ
		// FragmentTransaction transaction = fm.beginTransaction();

		fm = getFragmentManager();
		Button btn_back = (Button) view.findViewById(R.id.back);
		btn_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				fm.popBackStack();
			}
		});	
		return view;
	}
}
