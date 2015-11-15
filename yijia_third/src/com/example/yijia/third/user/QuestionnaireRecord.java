package com.example.yijia.third.user;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia_third.R;

public class QuestionnaireRecord extends BaseActivity {
	private ExpandableListView list_questionnaire_record;
	private ExpandableListAdapter mAdapter;
	private List<ArrayList<String>> list_record;
	private ArrayList<String> list_date;
	private Button back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.a_questionnaire_record);
		init();
	}

	protected void init() {
		// TODO Auto-generated method stub
		setBtnVisiable(false);
		setTittleText(this.getString(R.string.questionnaire_record));
		
		back=(Button)findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
			
		});
		
		list_questionnaire_record = (ExpandableListView) findViewById(R.id.list_questionnaire_record);
		mAdapter = new BaseExpandableListAdapter() {
						
			@Override
			public Object getChild(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub
				return getChildList().get(groupPosition).get(childPosition);
			}

			@Override
			public long getChildId(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub
				return childPosition;
			}

			@Override
			public View getChildView(int groupPosition, int childPosition,
					boolean isLastChild, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				LinearLayout linear = new LinearLayout(QuestionnaireRecord.this);
				linear.setOrientation(1);
				
				TextView question = getTextView(ViewGroup.LayoutParams.MATCH_PARENT,60);
				question.setTextColor(Color.BLACK);
				question.setText(getChild(groupPosition,childPosition).toString());
               
				TextView answer = getTextView(ViewGroup.LayoutParams.MATCH_PARENT,60);
                answer.setTextColor(Color.BLACK);
                answer.setText(getChild(groupPosition,childPosition).toString());
                
                linear.addView(question);
                linear.addView(answer);			
				return linear;
			}
			
			//定义TextView的布局			
			private TextView getTextView(int w,int h) {
				// TODO Auto-generated method stub
				AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                        w, h);
                TextView textView = new TextView(QuestionnaireRecord.this);
                textView.setLayoutParams(lp);
                textView.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
                textView.setPadding(36, 0, 0, 0);
                textView.setTextSize(20);
                textView.setTextColor(Color.BLACK);
                return textView;
			}

			@Override
			public int getChildrenCount(int groupPosition) {
				// TODO Auto-generated method stub
				return getChildList().get(groupPosition).size();
			}

			@Override
			public Object getGroup(int groupPosition) {
				// TODO Auto-generated method stub
				return getGroupList().get(groupPosition);
			}

			@Override
			public int getGroupCount() {
				// TODO Auto-generated method stub
				return getGroupList().size();
			}

			@Override
			public long getGroupId(int groupPosition) {
				// TODO Auto-generated method stub
				return groupPosition;
			}

			@Override
			public View getGroupView(int groupPosition, boolean isExpanded,
					View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				TextView date=getTextView(ViewGroup.LayoutParams.MATCH_PARENT,140);
				date.setText(getGroup(groupPosition).toString());
				return date;
			}

			@Override
			public boolean hasStableIds() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public boolean isChildSelectable(int groupPosition,
					int childPosition) {
				// TODO Auto-generated method stub
				return true;
			}
		};
		list_questionnaire_record.setAdapter(mAdapter);
	}
	
	private List<ArrayList<String>> getChildList(){
		list_record=new ArrayList<ArrayList<String>>();
		for(int j=0;j<4;j++){
			ArrayList<String> list=new ArrayList<String>();
			for(int i=1;i<5;i++){
				list.add(""+i);
			}
			list_record.add(list);
		}	
		return list_record;		
	}
	
	private ArrayList<String> getGroupList(){
		list_date=new ArrayList<String>();
		for(int i=0;i<4;i++){
			list_date.add("  2014-0"+i+"-0"+i);
		}
		return list_date;
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
