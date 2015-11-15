/**
 * 
 */
package com.example.yijia.third.settlement.column;

import java.util.Calendar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.bean.common.SettlementSum;
import com.example.yijia.third.tool.CalendarUtils;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class MSsettlement extends BaseActivity {
	private Button right_arrow, left_arrow, get_cash;
//	private HorizontalScrollView settlement_pillars;
	private RelativeLayout pillars, cash;
	private TextView date, cash_sum;
	private MsettlementColumn settlementColumn;
	private SettlementSum settlementSum;
	private String year;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		setSubView(R.layout.common_column,true);
		setTittleText(this.getString(R.string.settlement));

//		settlement_pillars = (HorizontalScrollView) findViewById(R.id.settlement_pillars);
		cash = (RelativeLayout) findViewById(R.id.cash);//不同场景显隐性需要控制
		cash.setVisibility(View.VISIBLE);
		pillars = (RelativeLayout) findViewById(R.id.pillars);

		right_arrow = (Button) findViewById(R.id.right_arrow);
		right_arrow.setOnClickListener(this);
		left_arrow = (Button) findViewById(R.id.left_arrow);
		left_arrow.setOnClickListener(this);
		get_cash = (Button) findViewById(R.id.get_cash);
		get_cash.setOnClickListener(this);

		date = (TextView) findViewById(R.id.date);
		date.setText(CalendarUtils.date());
		cash_sum = (TextView) findViewById(R.id.cash_sum);
		cash_sum.setText("请求数据...");
		
		Calendar calendar = Calendar.getInstance();
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		this.year=year;
		Log.e(TAG, "=-=-= year: " + year);

		settlementColumn = new MsettlementColumn(this);
		pillars.addView(settlementColumn);

		settlementSum = getData();// 获取数据
		Log.e(TAG, "=-=-= settlementSum: "+settlementSum.toString());
		postRefresh(settlementSum);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.right_arrow:
			this.year=getYear(year,+1);
			postRefresh(settlementSum);
			Log.e(TAG, "=-=-= year: "+year);
			
			break;
		case R.id.left_arrow:
			this.year=getYear(year,-1);
			postRefresh(settlementSum);
			Log.e(TAG, "=-=-= year: "+year);

			break;
		case R.id.get_cash:
			Toast.makeText(getApplicationContext(), "此功能暂未开放", Toast.LENGTH_SHORT).show();
			
			break;
		case R.id.back:
			finish();

			break;
		default:
			break;
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

	public SettlementSum getData() {
		// list=new ArrayList<SettlementSum>();
		SettlementSum settlementSum = new SettlementSum();
		settlementSum.setSettlementMonth("1,2,3,4,5,6,7,8,9,10,11,12");
		settlementSum.setTotalSum("2000,3000,4000,2000,2000,3000,2000,3000,0,2000,3000,3000");
		return settlementSum;
	}
	
	public String getYear(String yearBase, int i) {
		int ye = Integer.parseInt(yearBase);
		int result = ye + i;
		String year = String.valueOf(result);
		return year;
	}

	// 数据源必须是12个成对出现
	public void postRefresh(SettlementSum settlementSum) {
		String[] month = (year + "," + settlementSum.getSettlementMonth()).split(",");
		Log.e(TAG, "=-=-= month.length: " + month.length);
		settlementColumn.updateXtittle(month);

		String[] sum = settlementSum.getTotalSum().split(",");
		Log.e(TAG, "=-=-= sum.length: " + sum.length);
		settlementColumn.updateData(sum);
	}
}
