/**
 * 
 */
package com.example.yijia.third.settlement;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.bean.common.SettlementDetail;
import com.example.yijia.third.bean.common.SettlementTask;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class AsaSettlementDetail extends BaseActivity {

//	private TextView sa;
	private ListView list_exp;
	private AsettlementAdapter mAdapter;
	private ArrayList<SettlementDetail> list;
	private long msaId;
	private SettlementTask settlementTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.a_settlement_fou_item_next);
		// ����
		msaId = getIntent().getExtras().getLong(Constant.MSA_ID);
		settlementTask = getIntent().getExtras().getParcelable(Constant.SETTLEMENT_TASK);

		Log.e(TAG, "����ʱ����������msaId�ǣ�  " + msaId + "  \n settlementTaskId�ǣ�"
				+ settlementTask.getSettlementTaskId() + "\nsettlementTaskDate�ǣ�"
				+ settlementTask.getSettlementTime());
		init();
	}

	protected void init() {
		// TODO Auto-generated method stub
		setBtnVisiable(false);
		setTittleText(settlementTask.getSettlementTime());
//		sa = (TextView) findViewById(R.id.sa);
//		sa.setText(this.getString(R.string.team_num));
		list_exp = (ListView) findViewById(R.id.list_exp);
		mAdapter = new AsettlementAdapter(this, getData(), 1);// ��ʾ�鿴��������㣬1��ʾ�鿴�������
		list_exp.setAdapter(mAdapter);
	}

	private ArrayList<SettlementDetail> getData() {
		// TODO Auto-generated method stub
		list = new ArrayList<SettlementDetail>();
		for (int i = 0; i < 10; i++) {
			SettlementDetail settlementDetail = new SettlementDetail();
			settlementDetail.setMsaId(i);
			settlementDetail.setRealName("��ȵ");
			settlementDetail.setTeamNum(20);
			settlementDetail.setTotalNum(500);
			settlementDetail.setTotalIncome(8000);
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

//	private void requestData() {
//		long roleId;
//		long settlementTaskId;
//		long msaId;
//	}
}
