/**
 * 
 */
package com.example.yijia.third.admin;

import android.app.Fragment;
import android.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.yijia_third.R;


/**
 * @author Administrator
 *
 */
public class BaseFragment extends Fragment implements OnClickListener{
	protected Button btn_back;
	protected TextView username;
	protected View view;
	protected FragmentManager fm;
//	private FrameLayout content;
		
	public void getBtn(){
		fm = getFragmentManager();
		btn_back = (Button) view.findViewById(R.id.back);
		btn_back.setOnClickListener(this);
		
		username = (TextView) view.findViewById(R.id.username);	
//		content = (FrameLayout )view.findViewById(R.id.content);	
	}
	
	protected void setVisiable(View v){
		if(v.getVisibility()==View.GONE)
			v.setVisibility(View.VISIBLE);
		else
			v.setVisibility(View.GONE);			
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.back){
			fm.popBackStack();			
		}
	}
	
	public void setTittleText(String text){
		username.setText(text);
	}
	
//	public View setSubView(int layoutResId){
//		// ¿ªÆôFragmentÊÂÎñ
//		View v = getActivity().getLayoutInflater().inflate(layoutResId, null);
//		content.addView(v);	
//		return view;
//	}
	
	protected void setView(){
		
	};
	
	protected void init(){
		
	};
}


