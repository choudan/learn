/**
 * 
 */
package com.example.yijia.third.user;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia_third.R;

/**
 * @author Administrator
 *
 */
public class Waveform extends BaseActivity implements OnClickListener{
	private Button back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.a_query_waveform);
		init();
	}
	
	protected void init(){
		setBtnVisiable(false);
		setTittleText(this.getString(R.string.waveform));
		
		back=(Button)findViewById(R.id.back);
		back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.back:
			finish();
			break;
			default:
				break;
		}
	}

	@Override
	protected void hanlderUi() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interactive() {
		// TODO Auto-generated method stub
		
	}
}
