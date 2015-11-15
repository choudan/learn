package com.example.yijia.third.user;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.yijia.third.base.RegisterBaseActivity;
import com.example.yijia_third.R;

public class RegisterDate extends RegisterBaseActivity {
	private Button back, next_step;
	private int mYear, mMonth, mDay;
	private EditText date;
//	private UserInfo userInfo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		trtStatus();
		
		setSubView(R.layout.u_register_date, false);
		
		initTittleBar("ÐÂ¾­ÂçÒÇ");
		
//		userInfo=getIntent().getParcelableExtra(Constant.USER_INFO_KEY);
		Log.e(TAG, userInfo.toString());

		back = (Button) findViewById(R.id.back);
		back.setOnClickListener(this);

		next_step = (Button) findViewById(R.id.next_step);
		next_step.setOnClickListener(this);

		date = (EditText) findViewById(R.id.date);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (view.getId() == R.id.next_step) {
			if (checkInput(date)) {
				Intent intent = new Intent(RegisterDate.this,RegisterWeightLength.class);// Ãû×Ö
				userInfo.setBirthday(date.getText().toString());
//				intent.putExtra(Constant.USER_INFO_KEY, userInfo);
				startActivity(intent);
			}
		} else if (view.getId() == R.id.back)
			finish();
		else if (view.getId() == R.id.date) {
			InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(RegisterDate.this
					.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
			showDialog(0);
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

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			String mm;
			String dd;

			mMonth = monthOfYear + 1;
			mm = String.valueOf(mMonth);
			if (mm.length() < 2)
				mm = "0" + mm;

			mDay = dayOfMonth;
			dd = String.valueOf(mDay);
			if (dd.length() < 2)
				dd = "0" + dd;

			date.setText(String.valueOf(mYear) + "/" + mm + "/" + dd);
		}
	};

	protected Dialog onCreateDialog(int id) {
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		switch (id) {
		case 0:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}
		return null;
	}
}
