package com.example.yijia.third.inst;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yijia.third.admin.BaseFragment;
import com.example.yijia.third.bean.inst.Inst;
import com.example.yijia.third.bean.user.UserInfo;
import com.example.yijia.third.tool.Constant;
import com.example.yijia_third.R;

/**
 * @author 丑旦
 * @date 创建时间：2015-8-22 下午12:23:41
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class AqueryInstInfo extends BaseFragment {
	private UserInfo userBean;
	private TextView institution_name, institution_admin, password, sex, age,
			email, tel, wechat_account;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		Inst simpleInst=getArguments().getParcelable(Constant.SIMPLE_INST);
//		测试
		Toast.makeText(getActivity(), "inst_id是：  "+simpleInst.getInstId(), Toast.LENGTH_SHORT).show();
		
		view = inflater.inflate(R.layout.a_query_inst_info, container, false);					
//		setView();
		init();
		return view;
	}
	
	private UserInfo getData(){
		 userBean=new UserInfo();
		 userBean.setInstName("北京301");
		 userBean.setRealName("曹操");
		 userBean.setPassword("123");
		 userBean.setSex(1);
		 userBean.setBirthday("202-08-17");
		 userBean.setEmail("1988@qq.com");
		 userBean.setTelephone("15650723772");
		 userBean.setWechat("15650723772");
		 return userBean;
	}

//	@Override
//	protected void setView() {
//		// TODO Auto-generated method stub
//		view = setSubView(R.layout.a_query_inst_info);
//	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		getBtn();
		
		institution_name = (TextView) view.findViewById(R.id.institution_name);
		institution_name.setText(getData().getInstName());
		
		institution_admin = (TextView) view.findViewById(R.id.institution_admin);
		institution_admin.setText(getData().getRealName());
		
		password = (TextView) view.findViewById(R.id.password);
		password.setText(getData().getPassword());
		
		sex = (TextView) view.findViewById(R.id.sex);
		sex.setText(getData().getSex());
		
		age = (TextView) view.findViewById(R.id.age);
		age.setText(getData().getBirthday());
		
		email = (TextView) view.findViewById(R.id.email);
		email.setText(getData().getEmail());
		
		tel = (TextView) view.findViewById(R.id.tel);
		tel.setText(getData().getTelephone());
		
		wechat_account = (TextView) view.findViewById(R.id.wechat_account);
		wechat_account.setText(getData().getWechat());
		
//		setTittleText(this.getString(R.string.inst_info));
//		TextView username = (TextView) view.findViewById(R.id.username);	
		username.setText(this.getString(R.string.inst_info));
	}
}
