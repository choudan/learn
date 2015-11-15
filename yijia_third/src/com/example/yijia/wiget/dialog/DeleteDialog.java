package com.example.yijia.wiget.dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yijia.third.myinterface.OnDeleteListioner;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

//实现原理与添加图片一样，都是通过设置另外一个activity的style来实现pupMenu的样式设定。
public class DeleteDialog extends Activity implements  OnDeleteListioner,OnClickListener{
	private LinearLayout exit_layout;
	private TextView text_tittle;
	private Button confirm,cancel;
	protected final String TAG=this.getClass().getName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	protected void init(){
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}	
		setContentView(R.layout.common_delete_dialog);	
		text_tittle = (TextView) findViewById(R.id.text_tittle);
		exit_layout = (LinearLayout) findViewById(R.id.exit_layout);
		exit_layout.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",Toast.LENGTH_SHORT).show();
			}
		});		
		confirm=(Button)findViewById(R.id.confirm);
		confirm.setOnClickListener(this);
		cancel=(Button)findViewById(R.id.cancel);		
		cancel.setOnClickListener(this);
	}
	
	@Override
	public void confirm() {
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		setResult(Constant.DELETE_RESP_CONFIRM, intent);//结果码，Activity.RESULT_OK	
		Log.e(TAG, "confirm已执行...");
    	this.finish();  
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		this.finish();
	}

	@Override
	public void setTittle(String str) {
		// TODO Auto-generated method stub
		text_tittle.setText(str);
	}

	@Override
	public void setLeftBtnContent(String str) {
		// TODO Auto-generated method stub
		confirm.setText(str);
	}

	@Override
	public void setRightBtnContent(String str) {
		// TODO Auto-generated method stub
		cancel.setText(str);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.confirm:
			confirm();
			break;
		case R.id.cancel:
			cancel();
			break;
		}
	}

	@Override
	public void show(View v, int position) {
		// TODO Auto-generated method stub
		
	}
}
