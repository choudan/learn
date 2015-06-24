package com.qr_codescan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.yijia.R;
import com.yijia_login.One;

public class LogoutFinish extends Activity implements OnClickListener {

	/**
	 * @param args
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logout3);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_back:
			startActivity(new Intent(LogoutFinish.this,One.class));
			finish();
			break;
		}
	}

}
