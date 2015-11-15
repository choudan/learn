/**
 * 
 */
package com.example.yijia.third.settlement;

import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class AsettlementDate extends BaseActivity{
	private DatePicker datepicker;
	private RelativeLayout exit_layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		setContentView(R.layout.a_settlement_date);
		setTittleText(this.getString(R.string.settlement_date));
		
		exit_layout = (RelativeLayout) findViewById(R.id.exit_layout);
		exit_layout.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",Toast.LENGTH_SHORT).show();
			}
		});		
			
		datepicker = (DatePicker) findViewById(R.id.datepicker);
		final Calendar calendar = Calendar.getInstance();
		datepicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener(){
			@Override
			public void onDateChanged(DatePicker view, int year,
					int monthOfYear, int dayOfMonth) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "选择日期是："+year+"-"+(monthOfYear+1)+"-"+dayOfMonth, Toast.LENGTH_SHORT).show();
			}		
		});	
		
		long startMillis = System.currentTimeMillis();
		System.out.println("=-=-= startMillis: " + startMillis);
		datepicker.setMaxDate(getEndtime());
		datepicker.setMinDate(getStarttime());	
	}

	private long getEndtime() {
		Calendar calendar = Calendar.getInstance();
		Date date = new Date(System.currentTimeMillis());
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, +1);
		calendar.set(Calendar.DAY_OF_MONTH, 5);
		long datelong = calendar.getTimeInMillis();
		Log.e(TAG, "=-=-= getEndtime datelong :" + datelong);
		return datelong;
	}

	private long getStarttime() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		long datelong = calendar.getTimeInMillis();
		Log.e(TAG, "=-=-= getStarttime datelong :" + datelong);
		return datelong;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
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
