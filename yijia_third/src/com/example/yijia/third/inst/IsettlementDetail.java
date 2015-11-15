/**
 * 
 */
package com.example.yijia.third.inst;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.bean.common.SettlementInst;
import com.example.yijia.third.bean.common.SettlementTask;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class IsettlementDetail extends BaseActivity {
	
	private TextView date;
	private ListView list_inst;
	private IsettlementAdapter mAdapter;
	private ArrayList<SettlementInst> list;
	private SettlementTask settlementTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.a_settlement_fiv);
		// 测试
		settlementTask = getIntent().getExtras().getParcelable(Constant.SETTLEMENT_TASK);		
		Log.e(TAG, "结算时，传过来的 settlementTaskId是："
				+ settlementTask.getSettlementTaskId() + "\n settlementTaskDate是:"
				+ settlementTask.getSettlementTime());
		init();
	}

	protected void init() {
		// TODO Auto-generated method stub
		setBtnVisiable(false);
		setTittleText(this.getString(R.string.inst_settlement));
		date = (TextView) findViewById(R.id.date);
		date.setText(settlementTask.getSettlementTime());
		list_inst = (ListView) findViewById(R.id.list_inst);
		mAdapter = new IsettlementAdapter(this, getData());
		list_inst.setAdapter(mAdapter);
		
	}

	private ArrayList<SettlementInst> getData() {
		// TODO Auto-generated method stub
		list = new ArrayList<SettlementInst>();
		for (int i = 0; i < 10; i++) {
			SettlementInst settlementDetail = new SettlementInst();
			settlementDetail.setInstName("北京301");
			settlementDetail.setPresentNum(200 * i);
			settlementDetail.setTotalNum(500 * i);
			settlementDetail.setDate("200" + i + "01-10");
			list.add(settlementDetail);
		}
		return list;
	}

	@Override
	protected void hanlderUi() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interactive() {
		// TODO Auto-generated method stub
		
	}

	// private void requestData(){
	// long roleId;
	// long settlementTaskId;
	// long msaId;
	// }
}
