/**
 * 
 */
package com.example.yijia.third.inst;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.bean.common.SettlementInst;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class IqueryUserStatistics extends BaseActivity {
	private ListView list_inst;
	private IqueryUserStatisticsAdapter mAdapter;
	private ArrayList<SettlementInst> list;
	private TextView inst_name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.i_settlement_fiv);
		init();
	}

	protected void init() {
		// TODO Auto-generated method stub
		setBtnVisiable(false);
		setTittleText(this.getString(R.string.user_statistics));// 标题
		
		inst_name=(TextView)findViewById(R.id.inst_name);//从application中获取机构名字
		inst_name.setText("北京909");
		
		list_inst = (ListView) findViewById(R.id.list_inst);
		mAdapter = new IqueryUserStatisticsAdapter(this, getData());
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
}
