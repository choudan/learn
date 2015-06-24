package com.yijia_login;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.example.yijia.R;

public class LogInFinish extends BaseActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			// Í¸Ã÷×´Ì¬À¸
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); // Òþ²ØÈí¼üÅÌ
		}
		setContentView(R.layout.activity_login_finish);

		Timer timer = new Timer();
		TimerTask tast = new TimerTask() {
			@Override
			public void run() {
				exit();
			}
		};
		timer.schedule(tast, 3000);
	}

	private void exit() {
		Log.e("+_+_+_+_+_+_+_+_+ name():", "Ö´ÐÐ1");
		Intent intent = new Intent();
		intent.setAction("exit");
		this.sendBroadcast(intent);// ¹ã²¥·¢ËÍAction£¬BaseActivity½ÓÊÕ
		super.finish();
	}
}