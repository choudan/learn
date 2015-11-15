package com.example.yijia.third.msa;

import android.os.Bundle;
import android.widget.Toast;

import com.example.yijia.third.base.BaseApp;
import com.example.yijia.third.bean.admin.DeleteMsa;
import com.example.yijia.third.tool.Constant;
import com.example.yijia.wiget.dialog.DeleteDialog;

/**
 * @author ��
 * @date ����ʱ�䣺2015-8-21 ����10:23:58
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
		// ����
		Toast.makeText(getApplicationContext(), deleteMsa.toString(),Toast.LENGTH_SHORT).show();
		setTittle(((BaseApp) getApplication()).getRoleName() + "�����ֹ��:"
				+ deleteMsa.getServiceDeadline() + ",\n���ͷ���ʱ�䣺"
				+ deleteMsa.getGiftMonth() + "����\n�Ƿ��͵������û���");
		setLeftBtnContent("��");
		setRightBtnContent("��");
	}

	@Override
	public void confirm() {
		// TODO Auto-generated method stub
		super.confirm();
		Toast.makeText(getApplicationContext(), "�ѷ���", Toast.LENGTH_SHORT).show();
	}
}
