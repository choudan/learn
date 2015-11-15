package com.example.yijia.third.user;

import java.text.DecimalFormat;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yijia.third.asyn.user.AsynInfoQuery;
import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.base.BaseApp;
import com.example.yijia.third.bean.common.InfoQuery;
import com.example.yijia.third.bean.user.UserInfo;
import com.example.yijia.third.tool.Constant;
import com.example.yijia.third.tool.LogUtils;
import com.example.yijia_third.R;
import com.google.gson.Gson;

public class UuserInfo extends BaseActivity{
	private Button edit_finish,logout;
	private TextView age,weight_index,relation_msa;
	private EditText name,sex,height,weight;
	private UserInfo userInfo;
	private boolean flag=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		trtStatus();
		
		setSubView(R.layout.u_user_info);
		
		String rolename= ((BaseApp)getApplication()).getRoleName();				
		Log.e(TAG, "myapp中的name是：  "+rolename);
		if(name!=null)
			setTittleText(rolename);
		
//		userinfo = getData();
		
		name=(EditText)findViewById(R.id.name);
		name.setText(userInfo.getRealName());
		
		sex=(EditText)findViewById(R.id.sex);	
		sex.setText(userInfo.getSex());
		
		age=(TextView)findViewById(R.id.age);	
		age.setText(userInfo.getBirthday());
		
		height=(EditText)findViewById(R.id.height);	
		height.setText(""+userInfo.getLength());
		
		weight=(EditText)findViewById(R.id.weight);	
		weight.setText(""+userInfo.getWeight());
		
//		name.setOnClickListener(this);
//		sex.setOnClickListener(this);
//		height.setOnClickListener(this);
//		weight.setOnClickListener(this);
				
		weight_index=(TextView)findViewById(R.id.weight_index);	
		weight_index.setText(""+userInfo.getWeight_index());
		
		relation_msa=(TextView)findViewById(R.id.relation_msa);	
		relation_msa.setText(userInfo.getRelation_msa());
		
		edit_finish=(Button)findViewById(R.id.edit_finish);	
		edit_finish.setOnClickListener(this);
		
		logout=(Button)findViewById(R.id.logout);	
		logout.setOnClickListener(this);
			
		setEditable(false);
	}
	
	protected void setEditable(boolean value) {
		// TODO Auto-generated method stub
		setEditable(name,value);
		setEditable(sex,value);
		setEditable(height,value);
		setEditable(weight,value);
	}

	@Override
	public void onClick(View v) {
		
		// TODO Auto-generated method stub
		super.onClick(v);
		switch(v.getId()){
		case R.id.edit_finish:
			if(!flag){
				edit_finish.setText(this.getString(R.string.finish));
				flag=true;
			}else{
				if(!TextUtils.isEmpty(name.getText())&&!TextUtils.isEmpty(sex.getText())
					&&!TextUtils.isEmpty(height.getText())&&!TextUtils.isEmpty(weight.getText())){
					weight_index.setText(""+weightIndex(weight,height));
					
					edit_finish.setText(this.getString(R.string.edit));
					userInfo.setRealName(name.getText().toString());
					userInfo.setSex(Integer.parseInt(sex.getText().toString()));
					userInfo.setLength(Integer.parseInt(height.getText().toString()));
					userInfo.setWeight((Integer.parseInt(weight.getText().toString())));
					userInfo.setWeight_index(weightIndex(weight,height));
					flag=false;		
					Log.e(TAG, "可以提交了  userinfo.toString  "+userInfo.toString());
				}else{
					Toast.makeText(getApplicationContext(), "请将信息补充完整", Toast.LENGTH_SHORT).show();
				}
			}			
			setEditable(flag);
			logout.setVisibility(View.INVISIBLE);
			break;
		case R.id.logout:

			break;
		case R.id.back:
			finish();		
			break;
//		case R.id.name:
//		case R.id.sex:
//		case R.id.height:
//		case R.id.weight:
//			setEditable(true);
//			break;
		}
	}

	private double weightIndex(EditText weight, EditText length) {
		double index;
		double a, b, c;
		DecimalFormat df = new DecimalFormat("########.0");
		a = Double.parseDouble(weight.getText().toString());
		b = Double.parseDouble(length.getText().toString());
		c = a / b / b * 10000;
		index=Double.parseDouble(df.format(c));
		return index;
	}
	
	@Override
	protected void hanlderUi() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interactive() {
		// TODO Auto-generated method stub
		AsynInfoQuery asynInfoQuery = new AsynInfoQuery(this,getInfoQuery()){
			@Override
			protected void onPostExecute(String data) {
				// TODO Auto-generated method stub
				super.onPostExecute(data);
				Gson gson = new Gson();
				userInfo = gson.fromJson(data, UserInfo.class);
				LogUtils.getInstance().println("userInfo", userInfo.toString());
			}		
		};
		asynInfoQuery.execute();
	}

	//假数据，注意更改
	private InfoQuery getInfoQuery(){
		InfoQuery infoQuery = new InfoQuery();
		infoQuery.setQueryId(1);
		infoQuery.setUserId(1);
		infoQuery.setRole(Constant.USER_ROLE);
		return infoQuery;
	}
}
