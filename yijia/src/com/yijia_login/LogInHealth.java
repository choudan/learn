package com.yijia_login;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.download.doctorback.MyApplication;
import com.example.yijia.R;

public class LogInHealth extends BaseActivity {

	private EditText jiwang;
	private int num = 2000;
	private TextView text;
	private CharSequence temp;
	private int selectionStart;
	private int selectionEnd;
	private String health;
	private MyApplication app;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			// 透明状态栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);	
		}
		setContentView(R.layout.activity_login_health);
		app = (MyApplication) getApplication();

		jiwang = (EditText) findViewById(R.id.editText1);

		text = (TextView) findViewById(R.id.TextView01);
		text.setText(0 +"/"+ num);

		Button fanhui = (Button) findViewById(R.id.button2);
		fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		Button xiayibu = (Button) findViewById(R.id.button1);
		xiayibu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				health = jiwang.getText().toString().trim();
				if (health.equals("")) {
					Toast toast = Toast.makeText(LogInHealth.this, "既往健康不能为空",
							Toast.LENGTH_SHORT);
					toast.show();
				} else {
					app.setRegisterHealth(health);
					Intent intent = new Intent(LogInHealth.this,
							LogInDisease.class);
					startActivity(intent);
				}
			}
		});
		jiwang.addTextChangedListener(new TextWatcher() {
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				temp = s;
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				int number = s.length();
				text.setText(number + "/" + num);
				selectionStart = jiwang.getSelectionStart();
				selectionEnd = jiwang.getSelectionEnd();
				if (temp.length() > num) {
					s.delete(selectionStart - 1, selectionEnd);
					int tempSelection = selectionEnd;
					jiwang.setText(s);
					jiwang.setSelection(tempSelection);// 设置光标在最后
				}
			}
		});
	}
}