package com.example.yijia.third.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.yijia_third.R;

/**
 * @author  丑旦 
 * @date 创建时间：2015-8-22 下午2:40:08 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class UuserDeclare extends Activity{
	private Button agree;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.u_declare);
		init();
	}
	
	private void init(){
		agree=(Button)findViewById(R.id.agree);
		agree.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(UuserDeclare.this,RegisterBegin.class);
				startActivity(intent);
			}
		});
	}
}
