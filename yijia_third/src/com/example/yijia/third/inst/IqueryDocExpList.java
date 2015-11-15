package com.example.yijia.third.inst;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.bean.msa.MsaBound;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class IqueryDocExpList extends BaseActivity implements OnItemClickListener {

	private ListView list_exp_doc;
	private IqueryDocExpAdapter mAdapter;
	private ArrayList<MsaBound> list;
	private long serviceId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.a_query_service_list);
		
		serviceId=getIntent().getExtras().getLong(Constant.SERVICE_ID);//��ȡmsa�б�
		Log.e(TAG, "serviceId�ǣ�"+serviceId);
		
		init();
	}

	protected void init() {
		// TODO Auto-generated method stub
		setBtnVisiable(false);
		setTittleText(this.getString(R.string.exp_doc));// ����

		list_exp_doc = (ListView) findViewById(R.id.list_service);
		mAdapter=new IqueryDocExpAdapter(this,getData());
		list_exp_doc.setAdapter(mAdapter);
		list_exp_doc.setOnItemClickListener(this);
	}
	
	private ArrayList<MsaBound> getData() {
		// TODO Auto-generated method stub
		list = new ArrayList<MsaBound>();
		MsaBound msaSa = new MsaBound();
		msaSa.setId(1);
		msaSa.setRealName("��ʱ��");
		msaSa.setHospital("909ҽԺ");
		msaSa.setDept("���");
		msaSa.setSex("��");
		msaSa.setBirthday("1988-07-01");
		msaSa.setIntroduction("��ҽ");
		MsaBound msaSa1 = new MsaBound();
		msaSa1.setId(2);
		msaSa1.setRealName("��ʱ��");
		msaSa1.setHospital("������ҽԺ");
		msaSa1.setDept("�ڿ�");
		msaSa1.setSex("Ů");
		msaSa1.setBirthday("1977-07-01");
		msaSa1.setIntroduction("��ҽ����");
		list.add(msaSa);
		list.add(msaSa1);
		Log.e(TAG, "list.size()   " + list.size());
		return list;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, IqueryDocExpInfo.class);
		Bundle bundle = new Bundle();
		bundle.putParcelable(Constant.MSA, list.get(arg2));
		intent.putExtras(bundle);
		startActivity(intent);
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
