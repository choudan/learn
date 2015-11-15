package com.example.yijia.third.msa;

import android.os.Bundle;
import android.widget.Toast;

import com.example.yijia.third.base.BaseApp;
import com.example.yijia.third.bean.admin.DeleteMsa;
import com.example.yijia.third.tool.Constant;
import com.example.yijia.wiget.dialog.DeleteDialog;

/**
 * @author 丑旦
 * @date 创建时间：2015-8-21 下午10:23:58
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class AdeleteMsa extends DeleteDialog {
	private DeleteMsa deleteMsa;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
 		Bundle mBundle = getIntent().getExtras();
		deleteMsa = mBundle.getParcelable(Constant.DELETE);
		// 测试
		Toast.makeText(getApplicationContext(), deleteMsa.toString(),Toast.LENGTH_SHORT).show();
		setTittle(((BaseApp) getApplication()).getRoleName() + "服务截止于:"
				+ deleteMsa.getServiceDeadline() + ",\n赠送服务时间："
				+ deleteMsa.getGiftMonth() + "个月\n是否发送到关联用户？");
		setLeftBtnContent("是");
		setRightBtnContent("否");
	}

	@Override
	public void confirm() {
		// TODO Auto-generated method stub
		super.confirm();
		Toast.makeText(getApplicationContext(), "已发送", Toast.LENGTH_SHORT).show();
	}
}
