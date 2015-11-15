/**
 * 
 */
package com.example.yijia.third.msa;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.bean.admin.DeleteMsa;
import com.example.yijia.third.bean.sa.Sa;
import com.example.yijia.third.tool.CalendarUtils;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class AdeleteMsaActivity extends BaseActivity implements
		OnItemSelectedListener, OnClickListener {
	private Button send,back;
	// private EditText year,month,day,gift_month,gift_reason;
	private int mYear, mMonth, mDay;
	private EditText deadline, gift_month, gift_reason;
	private Spinner msa_spinner;
	private long msaId;
	private ArrayList<Sa> list;
	private ArrayList<String> listName;
	private DeleteMsa deleteMsa;
	private ArrayAdapter<String> adapter;
	private final static String TAG = AdeleteMsaActivity.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.a_delete_msa);

		// Bundle mBundle=getIntent().getExtras();
		// msaId=mBundle.getLong(Constant.MSA_ID);
		// roleId=mBundle.getString(Constant.ROLE_ID);
		// // 测试
		// Toast.makeText(getApplicationContext(),
		// "msaId是：  "+msaId+"\n name是：  "+name, Toast.LENGTH_SHORT).show();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		init();
	}

	protected void init() {
		// TODO Auto-generated method stub
		// year=(EditText)findViewById(R.id.year);
		// month=(EditText)findViewById(R.id.year);
		// day=(EditText)findViewById(R.id.year);
		setBtnVisiable(false);
		setTittleText(this.getString(R.string.msa_delete));
		
		deleteMsa = new DeleteMsa();

		deadline = (EditText) findViewById(R.id.deadline);
		deadline.setOnClickListener(this);
 		
		gift_month = (EditText) findViewById(R.id.gift_month);
		gift_reason = (EditText) findViewById(R.id.gift_reason);

		msa_spinner = (Spinner) findViewById(R.id.msa_spinner);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, getMsaName());
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		msa_spinner.setAdapter(adapter);
		msa_spinner.setOnItemSelectedListener(this);

		send = (Button) findViewById(R.id.send);
		send.setOnClickListener(this);
		
		back = (Button) findViewById(R.id.back);
		back.setOnClickListener(this);
	}

	private ArrayList<Sa> getMsaSa() {
		list = new ArrayList<Sa>();
		for(int i=0;i<4;i++){
			Sa simpleMsaSa=new Sa();
			simpleMsaSa.setId((long)i);
			simpleMsaSa.setRealName("孙思邈");
			list.add(simpleMsaSa);
		}
		return list;
	}

	private ArrayList<String> getMsaName() {
		list=getMsaSa();
		listName = new ArrayList<String>();
		for(Sa simpleMsaSa:list)
			listName.add(simpleMsaSa.getRealName());
		Log.e(TAG, "listName.size:  "+listName.size()+"   list.size:   "+list.size());
		return listName;
	}

	private DeleteMsa getDeleteMsa() {
		// TODO Auto-generated method stub
		deleteMsa.setMsaId(msaId);
		deleteMsa.setIsBroadcast(1);
		if(!gift_month.getText().toString().equals(""))
			deleteMsa.setGiftMonth(Integer.parseInt(gift_month.getText().toString()));		
		else
			deleteMsa.setGiftMonth(0);
		deleteMsa.setGiftReason(gift_reason.getText().toString());
		deleteMsa.setServiceDeadline(deadline.getText().toString());
		deleteMsa.setBroadcastStartDate(deadline.getText().toString());
		deleteMsa.setBroadcastDeadline("2015-08-22");
		deleteMsa.setApplyDate(CalendarUtils.dateTime().substring(0, 10));
		return deleteMsa;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		deleteMsa.setDefaultMsaId(list.get(position).getId());
		Toast.makeText(getApplicationContext(), "你选择的是"+list.get(position).getId(),
				Toast.LENGTH_SHORT).show();
		
		Log.e(TAG, "你选择的默认主服务号是： "+list.get(position).getRealName());
		send.setClickable(true);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		Log.e(TAG, "你没有选择默认主服务号");
		send.setClickable(false);
	}

	private void setEditable(boolean flag) {
		setEditable(deadline,flag);
		setEditable(gift_month,flag);
		setEditable(gift_reason,flag);
	}

//	private void setEditable(EditText editText, boolean value) {
//		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//		if (value) {
//			editText.setFocusableInTouchMode(true);
//			editText.requestFocus();
//			imm.showSoftInput(editText, 0);
//		} else {
//			editText.setFocusableInTouchMode(false);
//			editText.clearFocus();
//			imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
//		}
//	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.deadline:
			InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(AdeleteMsaActivity.this
					.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
			showDialog(0);
			break;
		case R.id.send:
			setEditable(false);
			Toast.makeText(getApplicationContext(),  getDeleteMsa().toString(),
					Toast.LENGTH_SHORT).show();	
			Intent intent = new Intent(AdeleteMsaActivity.this,
					AdeleteMsa.class);
			Bundle mbundle = new Bundle();
			mbundle.putParcelable(Constant.DELETE, getDeleteMsa());	
			intent.putExtras(mbundle);
			startActivity(intent);
			break;
		case R.id.back:
			finish();
			break;
		default:
			break;
		}
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

			deadline.setText(String.valueOf(mYear) + "-" + mm + "-" + dd);
		}
	};

	protected Dialog onCreateDialog(int id) {
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		switch (id) {
		case 0:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,mDay);
		}
		return null;
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
