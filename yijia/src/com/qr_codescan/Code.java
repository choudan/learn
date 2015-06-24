package com.qr_codescan;


import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.download.doctorback.MyApplication;
import com.example.yijia.R;
import com.yijia_use.UserInformation;

public class Code extends Activity {
	private final static int SCANNIN_GREQUEST_CODE = 1;
	private boolean flag=false;
	private MyApplication app;
	/**
	 * 显示扫描结果
	 */
//	private TextView mTextView ;
	/**
	 * 显示扫描拍的图片
	 */
//	private ImageView mImageView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);            
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);		
		}	
		setContentView(R.layout.activity_logout1);
		//这里用的是startActivityForResult跳转
		//扫描完了之后调到该界面
		
		app=(MyApplication) this.getApplication();
		Intent intent = new Intent();
		intent.setClass(Code.this, MipcaActivityCapture.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivityForResult(intent, SCANNIN_GREQUEST_CODE);

		Button yButton = (Button) findViewById(R.id.button2);
		yButton.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				if(flag){
					startActivity(new Intent(Code.this,Logouting.class));	
					finish();					
				}
				else{
					Toast toast=Toast.makeText(Code.this, "扫描结果异常，无法注销", Toast.LENGTH_LONG);
	    	    	toast.show();
	    	    	startActivity(new Intent(Code.this,UserInformation.class));	
	    	    	finish();
				}
			}
		});
		
		Button nButton = (Button) findViewById(R.id.button1);
		nButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				startActivity(new Intent(Code.this,UserInformation.class));
				finish();
			}
		});
	}
		

	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if(resultCode == RESULT_OK){
				/****在此处判断扫描内容是否是经络仪****/								
				Bundle bundle = data.getExtras();
				if(bundle.getString("result").equals(app.getXULIEHAO()))
					flag=true;				
				else{
					Toast toast=Toast.makeText(Code.this, "注销失败", Toast.LENGTH_SHORT);
	    	    	toast.show();
				}
				
//				//显示扫描到的内容
//				mTextView.setText(bundle.getString("result"));
//				//显示
//				mImageView.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
			}
			break;
		}
    }	

}
