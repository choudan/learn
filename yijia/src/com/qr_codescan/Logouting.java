package com.qr_codescan;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;

import com.download.doctorback.MyApplication;
import com.example.yijia.R;
import com.http.tool.HttpUrl;
import com.http.tool.NetTool;
import com.yijia_login.TestStart;
import com.yijia_use.UserInformation;

public class Logouting extends Activity implements OnClickListener {
	private MyApplication app;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            //Í¸Ã÷×´Ì¬À¸
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);            
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION); //Òþ²ØÈí¼üÅÌ		
		}	
		setContentView(R.layout.activity_logout2);
		app=(MyApplication) this.getApplication();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;
		}
	}
	
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what){
			case TestStart.COMPLETED:
				startActivity(new Intent(Logouting.this,LogoutFinish.class));
				break;
			case TestStart.UNCOMPLETED:
				startActivity(new Intent(Logouting.this,UserInformation.class));				
				break;
			default:
				break;
			}
			finish();
		}	
	};
	
	Runnable logout = new Runnable() {
//		private String url = "http://182.92.224.124:8086/yijia/zm/logout";
		private String url = HttpUrl.IP+HttpUrl.DIR+"logout";
		private String back;
		private JSONObject jsonLogout;
		private int statusCode;

		public void run() {
			Map<String, String> map = new HashMap<String, String>();
			map.put("username", app.getXULIEHAO()); // ²âÊÔÊ±£¬×¢ÒâÐÞ¸Ä¡£
			try {
				back = NetTool.sendGetRequest(url, map, "utf-8");
				jsonLogout = new JSONObject(back);
				statusCode = jsonLogout.getInt("statusCode");
			} catch (Exception e) {
				e.printStackTrace();
			}

			Message msg = new Message();
			if (statusCode == -1)
				msg.what = TestStart.COMPLETED;
			else {
				msg.what = TestStart.UNCOMPLETED;
			}
			handler.sendMessage(msg);
		}
	};
}
