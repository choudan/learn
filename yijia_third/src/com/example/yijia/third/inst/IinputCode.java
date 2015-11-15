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
public class IinputCode extends BaseActivity {
	private EditText edit_code;
	private Button confirm, request_identity_code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setSubView(R.layout.i_input_code);
		init();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		setTittleText(this.getString(R.string.edit_tel_pas));
		
		confirm = (Button) findViewById(R.id.confirm);
		confirm.setOnClickListener(this);

		request_identity_code = (Button) findViewById(R.id.request_identity_code);
		request_identity_code.setOnClickListener(this);

		edit_code = (EditText) findViewById(R.id.edit_code);

		setEditable(edit_code, false);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.confirm:
			if(!TextUtils.isEmpty(edit_code.getText())){
				Log.e(TAG, "此处提交验证码...");
				setEditable(edit_code, false);
//					
				if(edit_code.getText().toString().equals("123456")){
					Intent intent = new Intent(IinputCode.this,IeditTelPas.class);
					startActivity(intent);
					finish();
				}
			}else
				Toast.makeText(getApplicationContext(), "请输入验证码", Toast.LENGTH_SHORT).show();

			break;
		case R.id.request_identity_code:
			setEditable(edit_code, true);

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
