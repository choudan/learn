package com.example.yijia.third.waveform;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.yijia.third.bean.user.MedirianSingleData;
import com.example.yijia_third.R;

/**
 * @author 丑旦
 * @date 创建时间：2015-10-3 上午11:03:40
 * @version 1.0
 * @parameter
 * @since
 * @return
 * 
 */
public class WaveformMain extends Activity implements OnClickListener {
	private FragmentManager fm;
	private SinglePointFrag singleFragment;
	private AllPointFrag allFragment;
	public RadioButton single, all, shou, jiao;
	public Button back;
	public RadioGroup shou_jiao;
	public static final String FRAGMENT_TAG = "fragment_tag";
	public MedirianSingleData medirianSingleData;
	public final String TAG = this.getClass().getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
		if(savedInstanceState!=null){
			String tag = savedInstanceState.getString(FRAGMENT_TAG);
			recoveryFragment(tag);
		}else
			initView();
	}

	protected void init() {
		setContentView(R.layout.waveform_main);

		back = (Button) findViewById(R.id.back);
		back.setOnClickListener(this);

		single = (RadioButton) findViewById(R.id.single);
		single.setOnClickListener(this);

		all = (RadioButton) findViewById(R.id.all);
		all.setOnClickListener(this);
		
		shou_jiao = (RadioGroup)findViewById(R.id.shou_jiao);
		
		shou = (RadioButton) findViewById(R.id.shou);
		shou.setOnClickListener(this);
		
		jiao = (RadioButton) findViewById(R.id.jiao);
		jiao.setOnClickListener(this);
		
		fm = getFragmentManager();	
	}

	protected void initView() {
		singleFragment = new SinglePointFrag();
		allFragment = new AllPointFrag();
		FragmentTransaction ft = fm.beginTransaction();
		ft.add(R.id.container, singleFragment, singleFragment.getClass().getSimpleName());
		ft.add(R.id.container, allFragment, allFragment.getClass().getSimpleName()).hide(allFragment);
		ft.commit();
		System.out.println("----执行----");
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.single:
			switchFragment(allFragment,singleFragment,null);
			shou_jiao.setVisibility(View.GONE);
			break;
		case R.id.all:
			switchFragment(singleFragment,allFragment,null);
			shou_jiao.setVisibility(View.VISIBLE);
			break;
		case R.id.shou:
			System.out.println("=-=-=shou=-=-=");
//			refresh(null,this.getResources().getStringArray(R.array.labelTop));	
			break;
		case R.id.jiao:
			System.out.println("=-=-=jiao=-=-=");
//			refresh("",this.getResources().getStringArray(R.array.labelBottom));
			break;
		default:
			break;
		}
	}

	public void refresh(String startTime,String[] str){
		AllPointFrag allPointFrag = (AllPointFrag)getFragmentManager().findFragmentById(R.id.container);
		allPointFrag.requestData(startTime,str);
	}
	
	public void recoveryFragment(String tag){	
		singleFragment = new SinglePointFrag();
		allFragment = new AllPointFrag();
		if(tag.equalsIgnoreCase(singleFragment.getClass().getSimpleName())){
			single.setChecked(true);
			switchFragment(allFragment,singleFragment,null);
		}else if(tag.equalsIgnoreCase(allFragment.getClass().getSimpleName())){
			all.setChecked(true);
			switchFragment(singleFragment,allFragment,null);
		}
	}
	
	public void switchFragment(Fragment from, Fragment to, Bundle bundle) {
		String fromTag = from.getClass().getSimpleName();
		Log.e(TAG, "=-=-=fromTag=-=-=" + fromTag);
		String toTag = to.getClass().getSimpleName();
		Fragment fromFragment = fm.findFragmentByTag(fromTag);
		Fragment toFragment = fm.findFragmentByTag(toTag);
		if (toFragment == null) {
			toFragment = to;
			if (bundle != null)
				toFragment.setArguments(bundle);
		}
		FragmentTransaction ft = fm.beginTransaction();
		if (!toFragment.isAdded()) 
			ft.hide(fromFragment).add(R.id.container, toFragment, toTag);
		else{
			ft.hide(fromFragment).show(toFragment);
			Log.e(TAG, "=-=-=show=-=-="+toFragment);			
		}
		ft.commit();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		Fragment frag = fm.findFragmentByTag(singleFragment.getClass()
				.getSimpleName());
		String visiableFrag = frag.isVisible() ? singleFragment.getClass()
				.getSimpleName() : allFragment.getClass().getSimpleName();
		Log.e("=-=-=visiableFrag=-=-=", visiableFrag);
		outState.putString(FRAGMENT_TAG, visiableFrag);
		
 	}		
}
