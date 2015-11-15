/**
 * 
 */
package com.example.yijia.third.inst;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia_third.R;

/**
 * @author Administrator
 * 
 */
public class IeditAlipay extends BaseActivity {
	private EditText edit_alipay;
	private Button confirm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.i_input_alipay);
		init();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		setTittleText(this.getString(R.string.edit_tel_pas));
		
		confirm = (Button) findViewById(R.id.confirm);
		confirm.setOnClickListener(this);

		edit_alipay = (EditText) findViewById(R.id.edit_alipay);
		edit_alipay.setOnClickListener(this);

		setEditable(edit_alipay, false);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.confirm:
			setEditable(edit_alipay, false);
			if(!TextUtils.isEmpty(edit_alipay.getText())){
				Log.e(TAG, "此处提交支付宝账号...");
//					
				if(edit_alipay.getText().toString().equals("18744025659")){
					Intent intent = new Intent(IeditAlipay.this,IeditTelPas.class);
					startActivity(intent);
					finish();
				}
			}else
				Toast.makeText(getApplicationContext(), "请输入支付宝账号", Toast.LENGTH_SHORT).show();

			break;
		case R.id.edit_alipay:
			setEditable(edit_alipay, true);
			
			break;
		case R.id.back:
			finish();
			
			break;
		default:
			break;
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
