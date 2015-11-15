package com.example.yijia.third.msa;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.base.BaseApp;
import com.example.yijia.third.bean.common.Questionnaire;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

public class MeditQuestionnaire extends BaseActivity{
	private Button edit_content;
	private TextView name;
	private EditText content;
	private Questionnaire questionnaire;
	private boolean edit_save=false;
	private boolean add_item=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.m_edit_questionnaire);
		add_item=getIntent().getBooleanExtra(Constant.ADD_OR_ITEM,false);
		if(add_item){
			questionnaire=new Questionnaire();
			questionnaire.setRealName(((BaseApp)getApplication()).getRoleName());
			questionnaire.setQuestionnaire("");		
			
		}else{
			questionnaire=getIntent().getExtras().getParcelable(Constant.QUESTIONNAIRE);			
			Log.e(TAG, "questionnaire.toString():   "+questionnaire.toString());
		}
		
		init();		
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		setBtnVisiable(false);
		setTittleText(this.getString(R.string.ques_repository));
		
		name=(TextView)findViewById(R.id.name);
		name.setText(questionnaire.getRealName());
		
		edit_content=(Button)findViewById(R.id.edit_content);		
		edit_content.setOnClickListener(this);	
		
		content=(EditText)findViewById(R.id.content);
		content.setText(questionnaire.getQuestionnaire());
		
		setEditable(false);
	}

	private void setEditable(boolean flag){
		setEditable(content,flag);
	}
	
	@Override
	protected void hanlderUi() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interactive() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		super.onClick(v);
		switch(v.getId()){
		case R.id.back:
			finish();
			break;
		case R.id.edit_content:
			if(add_item){//区分生成还是编辑
				if(!edit_save){
					setEditable(true);				
					edit_save=true;
					edit_content.setText(this.getString(R.string.save));				
				}else{
					if(!TextUtils.isEmpty(content.getText())){
						edit_content.setText(this.getString(R.string.edit));				
						setEditable(false);				
						edit_save=false;											
					}else
						Toast.makeText(getApplicationContext(), "将填写问卷后再保存", Toast.LENGTH_SHORT).show();						
				}				
			}else{
				if(!edit_save){
					if(!TextUtils.isEmpty(content.getText())){
						edit_content.setText(this.getString(R.string.save));
						edit_save=true;
						setEditable(true);				
					}else
						Toast.makeText(getApplicationContext(), "保存内容不能为空", Toast.LENGTH_SHORT).show();
				}else{
					edit_content.setText(this.getString(R.string.edit));
					edit_save=false;
					setEditable(false);				
				}				
			}		
			break;
			default:
				break;
		}
	}
}
