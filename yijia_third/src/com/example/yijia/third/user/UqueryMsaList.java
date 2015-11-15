package com.example.yijia.third.user;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.yijia.third.base.RegisterBaseActivity;
import com.example.yijia.third.bean.common.Order;
import com.example.yijia.third.bean.msa.Msa;
import com.example.yijia.third.msa.AqueryMsaAdapter;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class UqueryMsaList extends RegisterBaseActivity implements OnItemClickListener {
	private ListView list_msa;
	private AqueryMsaAdapter mAdapter;
	private ArrayList<Msa> list;
	private Order order;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
	}

	private ArrayList<Msa> getData() {
		// TODO Auto-generated method stub
		list = new ArrayList<Msa>();
		Msa msaSa = new Msa();
		msaSa.setId(1);
		msaSa.setRealName("��ʱ��");
		msaSa.setHospitalDept("909 ���");
		msaSa.setPresentNum(300);
		msaSa.setTotalNum(500);
		msaSa.setBoundServiceType("ר����ѯ");
		Msa msaSa1 = new Msa();
		msaSa1.setId(2);
		msaSa1.setRealName("��˼��");
		msaSa1.setHospitalDept("301 �ڿ�");
		msaSa1.setPresentNum(2670);
		msaSa1.setTotalNum(530);
		msaSa1.setBoundServiceType("ר����ѯ");
		list.add(msaSa);
		list.add(msaSa1);
		Log.e(TAG, "list.size()   " + list.size());
		return list;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Log.e(TAG, "ѡ�е��ǵ�   "+arg2+1+"  λר��");
		order.setProviderId(list.get(arg2).getId());
		order.setProviderName(list.get(arg2).getRealName());
		order.setProviderUnit(list.get(arg2).getHospitalDept());
		Log.e(TAG, "ѡ�е�ר��id��   "+list.get(arg2).getId());
		Intent intent=new Intent();
		if(order.isNewAlter()){
			intent.setClass(this, UpayPath.class);			
		}else{
			order.setAlterFinished(true);//�������
			order.setIsValid(1);//���ĵĶ�������Ҫ֧��������Ч
			intent.setClass(this, UpayResult.class);	
		}
		intent.putExtra(Constant.ORDER, order);
		startActivity(intent);
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		trtStatus();
		
		setSubView(R.layout.u_query_msa_list,false);
		
		initTittleBar("�¶���/��Լ����");
		
//		flag=getIntent().getBooleanExtra(Constant.ORDER_ALTER, false);
		order=getIntent().getParcelableExtra(Constant.ORDER);		
		Log.e(TAG, order.toString());
		
		list_msa=(ListView)findViewById(R.id.list_msa);
		mAdapter = new AqueryMsaAdapter(this, getData());
		list_msa.setAdapter(mAdapter);
		list_msa.setOnItemClickListener(this);
	}

	@Override
	protected void hanlderUi() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interactive() {
		// TODO Auto-generated method stub
		if(order.getType()==0){
			Log.e(TAG, "����type=0���������(��������������)  Ŀǰ���������  ");
			
		}else if(order.getType()==1){
			Log.e(TAG, "����type=1���������    ");
			
		}
	}
				

}
