/**
 * 
 */
package com.example.yijia.third.inst;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia_third.R;

/**
 * @author Administrator
 *
 */
public class IeditTelPas extends BaseActivity {
	private EditText edit_tel,edit_pas;
	private Button confirm;
	private Timer timer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.i_edit_tel_pas);
		init();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		setTittleText(this.getString(R.string.edit_tel_pas));
		
		confirm=(Button)findViewById(R.id.confirm);
		confirm.setOnClickListener(this);
		
		edit_tel=(EditText)findViewById(R.id.edit_tel);
		edit_tel.setOnClickListener(this);	
		
		edit_pas=(EditText)findViewById(R.id.edit_pas);
		edit_pas.setOnClickListener(this);	
		
		setEditable(edit_tel, false);
		setEditable(edit_pas, false);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch(v.getId()){
		case R.id.confirm:
			if(!TextUtils.isEmpty(edit_tel.getText())&&!TextUtils.isEmpty(edit_pas.getText())){
				Log.e(TAG, "提交手机号和密码...");
				setEditable(edit_tel, false);
				setEditable(edit_pas, false);
				Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
				timer = new Timer();
				timer.schedule(new TimerTask() {				
					@Override
					public void run() {
						// TODO Auto-generated method stub						
						Log.e(TAG, "3秒后关闭此Activity...");
						finish();						
					}
				}, 3000);
			}else
				Toast.makeText(getApplicationContext(), "手机号和密码不能为空", Toast.LENGTH_SHORT).show();
			break;
		case R.id.back:
			finish();
			
			break;
		case R.id.edit_tel:		
		case R.id.edit_pas:
			setEditable(edit_tel, true);
			setEditable(edit_pas, true);
			
			break;		
			default:
				break;
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(timer!=null){
			timer.cancel();
			Log.e(TAG, "timer已取消...");		
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
