package com.yijia_login;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class BaseActivity extends Activity { 
 	
 	  private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
 		  @Override
	       public void onReceive(Context context, Intent intent) {
 			  	Log.e("=-=-=-=-=-=--=-=-= BaseActivity:", "����exit�㲥");
				finish();
	    	}
	  };
	  
	  @Override  
	  public void onResume() {  
        super.onResume();  
        // �ڵ�ǰ��activity��ע��㲥  
        IntentFilter filter = new IntentFilter();  
        filter.addAction("exit");  
        this.registerReceiver(this.broadcastReceiver, filter);  
	  }
	  
	  @Override  
	  protected void onDestroy() {  
        // TODO Auto-generated method stub  
        super.onDestroy();  
        this.unregisterReceiver(this.broadcastReceiver);    
	  }    
}


