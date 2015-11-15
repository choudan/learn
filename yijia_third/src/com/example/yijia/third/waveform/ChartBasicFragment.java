package com.example.yijia.third.waveform;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.yijia.third.tool.LogUtils;
import com.example.yijia_third.R;

/**
 * @author 丑旦
 * @date 创建时间：2015/10/5 下午11:21:25
 * @version 1.0
 * @parameter
 * @since
 * @return
 * 
 */
public abstract class ChartBasicFragment extends Fragment implements OnClickListener {
	public View rootView;
	public RadioButton fei, renmai, dachang, chongmai, xinbao, geshu, dumai,
			sanjiao, xin, xiaochang;	
	public final static boolean FRAG_TYPE = false;//single
	public final static String FRAG_BEAN = "frag_bean";//保存数据
	public final String TAG = this.getClass().getSimpleName();
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub			
		rootView = inflater.inflate(getLayout(), container, false);
		init(savedInstanceState);		
		return rootView;
	}

	public void setText(boolean flag) {
		if (flag) {
			fei.setText(this.getResources().getString(R.string.fei));
			renmai.setText(this.getResources().getString(R.string.renmai));
			dachang.setText(this.getResources().getString(R.string.dachang));
			chongmai.setText(this.getResources().getString(R.string.chongmai));
			xinbao.setText(this.getResources().getString(R.string.xinbao));
			geshu.setText(this.getResources().getString(R.string.geshu));
			dumai.setText(this.getResources().getString(R.string.dumai));
			sanjiao.setText(this.getResources().getString(R.string.sanjiao));
			xin.setText(this.getResources().getString(R.string.xin));
			xiaochang.setText(this.getResources().getString(R.string.xiaochang));
		} else {
			fei.setText(this.getResources().getString(R.string.pi));
			renmai.setText(this.getResources().getString(R.string.gan));
			dachang.setText(this.getResources().getString(R.string.renmai));
			chongmai.setText(this.getResources().getString(R.string.chongmaidaimai));
			xinbao.setText(this.getResources().getString(R.string.wei));
			geshu.setText(this.getResources().getString(R.string.geshu));
			dumai.setText(this.getResources().getString(R.string.dumai));
			sanjiao.setText(this.getResources().getString(R.string.dan));
			xin.setText(this.getResources().getString(R.string.shen));
			xiaochang.setText(this.getResources().getString(R.string.pangguang));
		}
	}	
	
	protected abstract void init(Bundle savedInstanceState);

	protected abstract int getLayout();
	
	protected abstract Object requestData(String startTime,String []label);
	
	protected abstract Object getBean(String startTime);
	
//	protected abstract String getMedirianSingleData();
		
//	@Override
//	public void onSaveInstanceState(Bundle outState) {
//		// TODO Auto-generated method stub
//		super.onSaveInstanceState(outState);
//		String saveResult = getMedirianSingleData();
//		LogUtils.getInstance().println("onSaveInstanceState  saveResult", saveResult);
//		outState.putString(ChartBasicFragment.FRAG_BEAN, saveResult);			
//	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		LogUtils.getInstance().println("onActivityCreated", "onActivityCreated");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LogUtils.getInstance().println("onCreate", "onCreate");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		LogUtils.getInstance().println("onDestroy", "onDestroy");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		LogUtils.getInstance().println("onPause", "onPause");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		LogUtils.getInstance().println("onResume", "onResume");
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		LogUtils.getInstance().println("onStart", "onStart");
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		LogUtils.getInstance().println("onStop", "onStop");
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		LogUtils.getInstance().println("onAttach", "onAttach");
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		LogUtils.getInstance().println("onDetach", "onDetach");
	}	
	
}
