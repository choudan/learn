/**
 * 
 */
package com.example.yijia.third.service;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.bean.common.Service;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class ServiceDetail extends BaseActivity implements OnClickListener,
		OnCheckedChangeListener {
	private EditText service_name, msa, sa, admin, service_content;
	private CheckBox inst, personal;
	private Button edit_service,relation_msa_sa;
	private Service service;
	private boolean editable;//区分查看还是生成,false是查看
	private boolean switcher=false;//控制编辑和完成

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.a_service_detail);
		
		Bundle mBundle = getIntent().getExtras();
		editable = mBundle.getBoolean(Constant.EDITABLE);
		if(!editable){
			service = mBundle.getParcelable(Constant.SERVICE_BEAN);
			Log.e(TAG, "editable 是：  "+editable+"    service.toString：  "+service.toString());			
		}else{
			service=new Service();
			Log.e(TAG, "editable 是：  "+editable+"    service.toString：  "+service.toString());						
		}
		init();
	}

	protected void init() {
		setBtnVisiable(false);
		setTittleText(this.getString(R.string.service_content));
		
		service_name = (EditText) findViewById(R.id.service_name);
		msa = (EditText) findViewById(R.id.msa);
		sa = (EditText) findViewById(R.id.sa);
		admin = (EditText) findViewById(R.id.admin);
		service_content = (EditText) findViewById(R.id.service_content);
		
		inst = (CheckBox) findViewById(R.id.inst);
		inst.setOnCheckedChangeListener(this);
		
		personal = (CheckBox) findViewById(R.id.personal);
		personal.setOnCheckedChangeListener(this);
		
		edit_service = (Button) findViewById(R.id.edit_service);
		edit_service.setOnClickListener(this);
		
		relation_msa_sa = (Button) findViewById(R.id.relation_msa_sa);
		
		setEditable(false);
		
		if(!editable){
			service_name.setText(service.getServiceName());
			msa.setText(""+service.getMsaBonus());
			sa.setText(""+service.getSaBouns());
			admin.setText(""+service.getAdminBouns());
			service_content.setText(service.getIntroduction());		
			relation_msa_sa.setOnClickListener(this);	
			
			if(service.getTypes().equals("1"))
				inst.setChecked(true);			
			else if(service.getTypes().equals("2"))
				personal.setChecked(true);
			else if(service.getTypes().equals("1,2")||service.getTypes().equals("2,1")){
				inst.setChecked(true);			
				personal.setChecked(true);			
			}		
		}else{
			relation_msa_sa.setVisibility(View.INVISIBLE);			
		}	
	}

	private void setEditable(boolean flag) {
		setEditable(service_name,flag);
		setEditable(msa,flag);
		setEditable(sa,flag);
		setEditable(admin,flag);
		setEditable(service_content,flag);	
		inst.setClickable(flag);
		personal.setClickable(flag);		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.edit_service:
			if(!switcher){
				setEditable(true);	
				switcher=true;
				edit_service.setText(this.getString(R.string.finish));
			}else if(!TextUtils.isEmpty(admin.getText())
					&&!TextUtils.isEmpty(msa.getText())
					&&!TextUtils.isEmpty(sa.getText())
					&&!TextUtils.isEmpty(service_content.getText())
					&&!TextUtils.isEmpty(service_name.getText())){
				service.setAdminBouns(Integer.parseInt(admin.getText().toString()));
				service.setMsaBonus(Integer.parseInt(msa.getText().toString()));
				service.setSaBouns(Integer.parseInt(sa.getText().toString()));
				service.setIntroduction(service_content.getText()+"");
				service.setServiceName(service_name.getText()+"");
				service.setPrice(service.getAdminBouns()+service.getMsaBonus()+service.getSaBouns());
				getType();
				Log.e(TAG, service.toString());
//				提交数据				
				if(!service.getTypes().equals("")){
					Log.e(TAG, "可以提交了");							
					setEditable(false);	
					switcher=false;
					edit_service.setText(this.getString(R.string.edit));	
					relation_msa_sa.setVisibility(View.INVISIBLE);
				}
			}else
				Toast.makeText(getApplicationContext(), "请将内容填写完整再提交", Toast.LENGTH_SHORT).show();
			break;
		case R.id.back:
			finish();
			break;
		case R.id.relation_msa_sa:
			Toast.makeText(getApplicationContext(), "功能待明确", Toast.LENGTH_SHORT).show();
			break;
			default:
				break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub			
		Log.e(TAG, "服务类型改变了");
		service.setTypes("");
	}
	
	private void getType(){
		if(inst.isChecked()&&personal.isChecked())
//			service.setTypes(this.getString(R.string.service_inst)+","+this.getString(R.string.service_personal));
			service.setTypes("1,2");
		else if(!inst.isChecked()&&personal.isChecked())
//			service.setTypes(this.getString(R.string.service_personal));
			service.setTypes("2");
		else if(inst.isChecked()&&!personal.isChecked())	
//			service.setTypes(this.getString(R.string.service_inst));
			service.setTypes("1");
		else if(!inst.isChecked()&&!personal.isChecked()){
			Toast.makeText(getApplicationContext(), "请选择服务类型", Toast.LENGTH_SHORT).show();								
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
}
