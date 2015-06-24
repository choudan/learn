package com.yijia_login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.bluetooth.le.DeviceScanActivity;
import com.example.yijia.R;

public class TestStart extends Activity {
	public final  static int COMPLETED = 0;	//全局的返回码
	public final static int UNCOMPLETED = 1;
	public final static int FAIL = 2;	
	public final static int SUCCESS = 3;
	public final static int FINISH = 4;
	public final static int UNFINISH = 5;
	public final static int ACHIVE = 6;
	public final static int LOSE = 7;
	public final static int YES=8;
	public final static int NO=9;
	public final static int LOGOUT=-1;
	public final static int NOLOGOUT=-2;
	public final static int KONG=-3;
	public final static int SHOW=-4;
	public final static int DISMISS=-5;
	public final static int DIALOGCOME=-6;
	public final static int DIALOGGO=-8;
	public final static int MESSAGEFINSH=-7;
	public final static int NOMESSAGE=-11;
	public final static int SYNCHFINSH=-9;
	public final static int SYNCHFAIL=-10;
			 
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);            
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION); 		
		}	
        setContentView(R.layout.activity_start);  
        
        if(isNetworkAvailable(this)||isWifiEnabled(this)){
        	startActivity(new Intent(this,DeviceScanActivity.class));
        	finish();
        } 
        else{  
            Toast.makeText(getApplicationContext(),"亲，网络打开了吗？", Toast.LENGTH_LONG).show();  
        }       
    }
    
    //判断网络连接是否可用
    public static boolean isNetworkAvailable(Context context) {   
        ConnectivityManager cm = (ConnectivityManager) context   
                .getSystemService(Context.CONNECTIVITY_SERVICE);   
        if (cm == null) {   
        } else {
            NetworkInfo[] info = cm.getAllNetworkInfo();   
            if (info != null) {   
                for (int i = 0; i < info.length; i++) {   
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {   
                        return true;   
                    }   
                }   
            }   
        }   
        return false;   
    } 
    
    public static boolean isWifiEnabled(Context context) {   
        ConnectivityManager mgrConn = (ConnectivityManager) context   
                .getSystemService(Context.CONNECTIVITY_SERVICE);   
        TelephonyManager mgrTel = (TelephonyManager) context   
                .getSystemService(Context.TELEPHONY_SERVICE);   
        return ((mgrConn.getActiveNetworkInfo() != null && mgrConn   
                .getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel   
                .getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);   
    } 
}
