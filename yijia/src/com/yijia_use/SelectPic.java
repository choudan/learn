package com.yijia_use;

import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.choosephoto.ImgFileListActivity;
import com.download.doctorback.DoctorReply;
import com.example.yijia.R;

public class SelectPic extends Activity implements OnClickListener{

	private Button btn_take_photo, btn_pick_photo;
	private LinearLayout layout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            //͸��״̬��
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);            
	        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//���õ�����
		}	
		setContentView(R.layout.choose_photo);
		btn_take_photo = (Button) this.findViewById(R.id.btn_take_photo);
		btn_pick_photo = (Button) this.findViewById(R.id.btn_pick_photo);
		
		layout=(LinearLayout)findViewById(R.id.pop_layout);		
		//����ѡ�񴰿ڷ�Χ�����������Ȼ�ȡ���㣬������ִ��onTouchEvent()��������������ط�ʱִ��onTouchEvent()��������Activity
		layout.setOnClickListener(new OnClickListener() {	
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "��ʾ����������ⲿ�رմ��ڣ�", 
				Toast.LENGTH_SHORT).show();	
			}
		});
		//���Ӱ�ť����
		btn_pick_photo.setOnClickListener(this);
		btn_take_photo.setOnClickListener(this);
	}
	
	//ʵ��onTouchEvent���������������Ļʱ���ٱ�Activity
	@Override
	public boolean onTouchEvent(MotionEvent event){
		finish();
		return true;
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_take_photo:
			startActivity(new Intent(this,TakePhoto.class));
			finish();
			break;
		case R.id.btn_pick_photo:
			startActivity(new Intent(this,ImgFileListActivity.class));
			finish();
			break;
		default:
			break;
		}	
	}
}