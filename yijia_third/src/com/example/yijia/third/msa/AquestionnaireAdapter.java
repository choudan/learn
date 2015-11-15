/**
 * 
 */
package com.example.yijia.third.msa;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yijia.third.bean.common.Questionnaire;
import com.example.yijia_third.R;

public class AquestionnaireAdapter extends BaseAdapter{
	private ArrayList<Questionnaire> list;
	private LayoutInflater inflater;//View.inflate()方法性能不好，慎用

	public AquestionnaireAdapter(Context mContext, ArrayList<Questionnaire> list) {
		super();
		this.list = list;
		inflater = LayoutInflater.from(mContext);
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

//	//要实现这两个方法
//	public int getViewTypeCount() {
//		// TODO Auto-generated method stub
//		return 2;
//	}
//
//	@Override
//	public int getItemViewType(int position) {
//		// TODO Auto-generated method stub
//		Questionnaire entity = list.get(position);
//		int type = -1;
//		if (entity.getContributorId() == 20)
//			type = 0;
//		else
//			type = 1;
//		return type;
//	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder hodler = null;
		if (convertView == null) {
			hodler = new ViewHolder();
			convertView = inflater.inflate(R.layout.a_logout_simple_userinfo_item, parent, false);
			hodler.name = (TextView) convertView.findViewById(R.id.name);
			hodler.date = (TextView) convertView.findViewById(R.id.date);
			convertView.setTag(hodler);
		} else {
			hodler = (ViewHolder) convertView.getTag();
			Log.e("已执行到此", "type0");
		}
		hodler.name.setText(list.get(position).getQuestionnaire());
		hodler.date.setText(list.get(position).getRealName());
		return convertView;
	}

//			if(getItemViewType(position)==0){
//				hodler=new ViewHolder();
//				convertView=inflater.inflate(R.layout.a_logout_simple_userinfo_item, parent, false);
//				hodler.name=(TextView)convertView.findViewById(R.id.name);
//				hodler.date=(TextView)convertView.findViewById(R.id.date);					
//				convertView.setTag(hodler);
//				TextPaint paint = hodler.name.getPaint();  
//				paint.setFakeBoldText(true);  
//				Log.e("已执行到此","字体加粗设置");				
//			}else{				
//				hodler1=new ViewHolder();
//				convertView=inflater.inflate(R.layout.a_logout_simple_userinfo_item, parent, false);
//				hodler1.name=(TextView)convertView.findViewById(R.id.name);
//				hodler1.date=(TextView)convertView.findViewById(R.id.date);	
//				convertView.setTag(hodler1);
//			}
//		}else{
//			if(getItemViewType(position)==0){
//				hodler=(ViewHolder) convertView.getTag();
//				hodler.name.setText(list.get(position).getQuestionnaire());
//				hodler.date.setText(list.get(position).getRealName());				
//				Log.e("已执行到此","type0");				
//			}else{				
//				hodler1=(ViewHolder) convertView.getTag();
//				hodler1.name.setText(list.get(position).getQuestionnaire());
//				hodler1.date.setText(list.get(position).getRealName());				
//				Log.e("已执行到此","type1");				
//			}
//		}
//		return convertView;
//	}	

	public class ViewHolder{
		public TextView name,date;
	}
}
