package com.example.bluetooth.le;


import com.download.doctorback.MyApplication;
import com.example.yijia.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
//ʵ��ԭ�������ͼƬһ��������ͨ����������һ��activity��style��ʵ��pupMenu����ʽ�趨��
public class UserListDeleteOne extends Activity {
	//private MyDialog dialog;
	private LinearLayout layout;
	private TextView text_delete;
	public static String  SHAN;
	private MyApplication app;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
		setContentView(R.layout.delete_dialog);
		//dialog=new MyDialog(this);
		app=(MyApplication) this.getApplication();
		text_delete=(TextView)findViewById(R.id.text_delete);
		text_delete.setText("�Ƿ�ע���û���"+app.getNAME());
		
		layout=(LinearLayout)findViewById(R.id.exit_layout);
		layout.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "��ʾ����������ⲿ�رմ��ڣ�", 
						Toast.LENGTH_SHORT).show();	
			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event){
		finish();
		return true;
	}
	
	public void delete(View v) {  	
		Intent intent=new Intent();
		intent.putExtra(SHAN,"OK");
		setResult(Activity.RESULT_OK, intent);
    	this.finish();    	
      } 
	
	public void nodelete(View v) { 
		Intent intent=new Intent();
		intent.putExtra(SHAN,"NO");
		setResult(Activity.RESULT_OK, intent);
    	this.finish();
      }  	
 }
