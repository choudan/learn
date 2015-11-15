/**
 * 
 */
package com.example.yijia.third.uploadphoto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class UaddPath extends Activity implements OnClickListener {
	private Button btn_take_photo, btn_pick_photo;
	private RelativeLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.u_add_path);
		init();
	}

	protected void init() {
		btn_take_photo = (Button) this.findViewById(R.id.btn_take_photo);
		btn_pick_photo = (Button) this.findViewById(R.id.btn_pick_photo);
		btn_pick_photo.setOnClickListener(this);
		btn_take_photo.setOnClickListener(this);
		
		layout = (RelativeLayout) findViewById(R.id.pop_layout);
		// 添加选择窗口范围监听可以优先获取触点，即不再执行onTouchEvent()函数，点击其他地方时执行onTouchEvent()函数销毁Activity
		layout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_take_photo:
			startActivity(new Intent(this, UtakePhotoDialog.class));
			finish();
			break;
		case R.id.btn_pick_photo:
			startActivity(new Intent(this, UallPhoto.class));
			finish();
			break;
		default:
			break;
		}

	}
}
