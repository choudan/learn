/**
 * 
 */
package com.example.yijia.third.settlement;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.bean.common.SettlementDetail;
import com.example.yijia.third.bean.common.SettlementTask;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class AmsaSettlementDetail extends BaseActivity implements
		OnItemClickListener {

//	private TextView sa;
	private ListView list_exp;
	private SettlementAdapter mAdapter;
	private ArrayList<SettlementDetail> list;
	private int msaSettlementViewType;// 0表示查看主服务结算，1表示查看服务结算
	private SettlementTask settlementTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.a_settlement_fou_item_next);
			
		msaSettlementViewType=getIntent().getExtras().getInt(Constant.MSA_SETTLEMENT_VIEW_TYPE);
		settlementTask=getIntent().getExtras().getParcelable(Constant.SETTLEMENT_TASK);					
		
		Log.e(TAG, "传过来的msaSettlementViewType是：   "+msaSettlementViewType+"    settlementTaskId:"+settlementTask.getSettlementTaskId());
		// 测试
		init();
	}

	protected void init() {
		// TODO Auto-generated method stub
		setBtnVisiable(false);
		setTittleText(settlementTask.getSettlementTime());
		
//		sa = (TextView) findViewById(R.id.sa);
//		sa.setText(this.getString(R.string.team_num));
		
		list_exp = (ListView) findViewById(R.id.list_exp);
		mAdapter = new SettlementAdapter(this, getData(), msaSettlementViewType);
		list_exp.setAdapter(mAdapter);
		list_exp.setOnItemClickListener(this);
	}

	private ArrayList<SettlementDetail> getData() {
		// TODO Auto-generated method stub
		list = new ArrayList<SettlementDetail>();
		for (int i = 0; i < 50; i++) {
			SettlementDetail settlementDetail = new SettlementDetail();
			settlementDetail.setMsaId(i);
			settlementDetail.setRealName("张仲景");
			settlementDetail.setTeamNum(50);
			settlementDetail.setTotalNum(1000);
			settlementDetail.setTotalIncome(20000);			
			list.add(settlementDetail);
		}
		list.get(0).setBoundServiceType("医生咨询");
		list.get(15).setBoundServiceType("专家咨询");
		return list;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Log.e(TAG, "arg2的值是：  "+arg2);
		Log.e(TAG, list.get(arg2).toString());
		
//		Button msa_btn = (Button) arg1.findViewById(R.id.msa_btn);
//		msa_btn.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				long msaId=list.get(arg2).getMsaId();
//				Log.e(TAG, "msaId是：  "+msaId);
//				Log.e(TAG, "arg2的值是： "+arg2);
//				Intent intent=new Intent(MsaSettlementDetail.this,SaSettlementDetail.class);
//				Bundle bundle=new Bundle();
//				bundle.putLong(Constant.MSA_ID, msaId);
//				bundle.putLong(Constant.SETTLEMENT_TASK_ID, settlementTaskId);
//				intent.putExtras(bundle);
//				startActivity(intent);
//			}
//		});
	}

	@Override
	protected void hanlderUi() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interactive() {
		// TODO Auto-generated method stub
		
	}
	
	public class SettlementAdapter extends BaseAdapter{
		private Context mContext;
		private ArrayList<SettlementDetail> list;
		private int flag;

		public SettlementAdapter(Context mContext,
				ArrayList<SettlementDetail> list, int flag) {
			super();
			this.mContext = mContext;
			this.list = list;
			this.flag = flag;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		
		@Override
		public int getItemViewType(int position) {
			// TODO Auto-generated method stub
			if(list.get(position).getBoundServiceType()!=null)
				return 0;
			else 
				return 1;
		}

		@Override
		public int getViewTypeCount() {
			// TODO Auto-generated method stub
			return 2;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHodler hodler=null;
			if(convertView == null){
				hodler = new ViewHodler();
				convertView = View.inflate(mContext,R.layout.a_settlement_fou_item_next_item, null);
				hodler.msa_textview = (TextView) convertView.findViewById(R.id.msa_textview);
				hodler.sa = (TextView) convertView.findViewById(R.id.sa);
				hodler.total_num = (TextView) convertView.findViewById(R.id.total_num);
				hodler.msa_btn = (Button) convertView.findViewById(R.id.msa_btn);
				hodler.sum = (TextView) convertView.findViewById(R.id.sum);
				hodler.category = (RelativeLayout) convertView.findViewById(R.id.category);
				hodler.msa_sa=(TextView)convertView.findViewById(R.id.msa_sa);					
				if(getItemViewType(position)==0){
					hodler.category.setVisibility(View.VISIBLE);
					hodler.msa_sa=(TextView)convertView.findViewById(R.id.msa_sa);					
					hodler.msa_sa.setText(list.get(position).getBoundServiceType());											
					convertView.setTag(hodler);									
				}else{
					convertView.setTag(hodler);							
				}			
			}else{
				if(getItemViewType(position)==0){								
					hodler=(ViewHodler) convertView.getTag();				
					hodler.msa_sa.setText(list.get(position).getBoundServiceType());											
				}else{
					hodler=(ViewHodler) convertView.getTag();				
				}			
			}		
		
//			if(getItemViewType(position)==0){
//				if(convertView == null){
//					hodler = new ViewHodler();
//					convertView = View.inflate(mContext,R.layout.a_settlement_fou_item_next_item, null);
//					hodler.msa_textview = (TextView) convertView.findViewById(R.id.msa_textview);
//					hodler.sa = (TextView) convertView.findViewById(R.id.sa);
//					hodler.total_num = (TextView) convertView.findViewById(R.id.total_num);
//					hodler.msa_btn = (Button) convertView.findViewById(R.id.msa_btn);
//					hodler.sum = (TextView) convertView.findViewById(R.id.sum);
//					hodler.category = (RelativeLayout) convertView.findViewById(R.id.category);
//					hodler.category.setVisibility(View.VISIBLE);
//					hodler.msa_sa=(TextView)convertView.findViewById(R.id.msa_sa);					
//					convertView.setTag(hodler);				
//				}else
//					hodler=(ViewHodler) convertView.getTag();				
//				hodler.msa_sa.setText(list.get(position).getBoundServiceType());											
//			} else if (getItemViewType(position) == 1) {
//				if (convertView == null) {
//					hodler = new ViewHodler();
//					convertView = View.inflate(mContext,R.layout.a_settlement_fou_item_next_item_gone_category, null);
//					hodler.msa_textview = (TextView) convertView.findViewById(R.id.msa_textview);
//					hodler.sa = (TextView) convertView.findViewById(R.id.sa);
//					hodler.total_num = (TextView) convertView.findViewById(R.id.total_num);
//					hodler.msa_btn = (Button) convertView.findViewById(R.id.msa_btn);
//					hodler.sum = (TextView) convertView.findViewById(R.id.sum);
//					convertView.setTag(hodler);
//				} else
//					hodler = (ViewHodler) convertView.getTag();
//			}
			
			if(flag==0){
				hodler.msa_btn.setText(list.get(position).getRealName());
				hodler.msa_btn.setOnClickListener(new ClickListener(position));
			}
			else if(flag==1){
				hodler.msa_textview.setText(list.get(position).getRealName());		
				hodler.msa_btn.setVisibility(View.INVISIBLE);
			}
			hodler.sa.setText(""+list.get(position).getTeamNum());
			hodler.total_num.setText(""+list.get(position).getTotalNum());
			hodler.sum.setText(""+list.get(position).getTotalIncome());						
			return convertView;
		}
		
		
		class ClickListener implements OnClickListener{
			private int position;
			ClickListener(int arg){
				position=arg;
			}
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e(TAG, "listener中的position的值是：  "+position);
				Intent intent=new Intent(AmsaSettlementDetail.this,AsaSettlementDetail.class);
				Bundle bundle=new Bundle();			
				bundle.putLong(Constant.MSA_ID, list.get(position).getMsaId());
				bundle.putParcelable(Constant.SETTLEMENT_TASK, settlementTask);
				intent.putExtras(bundle);
				startActivity(intent);
				
			}		
		}
			
		public class ViewHodler{
			private TextView msa_textview,sa,total_num,sum,msa_sa;
			private Button msa_btn;
			private RelativeLayout category;
		}
		
	}
	
	
//	private void requestData(){
//		long roleId;
//		long settlementTaskId;
//	}
}
