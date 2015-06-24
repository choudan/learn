/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.bluetooth.le;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.download.doctorback.MyApplication;
import com.example.yijia.R;
import com.http.tool.CRC;
import com.http.tool.DataUtil;
import com.http.tool.HttpUrl;
import com.http.tool.NetTool;
import com.yijia_login.LogIn;
import com.yijia_login.TestStart;
import com.yijia_use.NormalUse;

/**
 * For a given BLE device, this Activity provides the user interface to connect, display data,
 * and display GATT services and characteristics supported by the device.  The Activity
 * communicates with {@code BluetoothLeService}, which in turn interacts with the
 * Bluetooth LE API.
 * 按位或，逻辑或 |  ||
 * 按位与，逻辑与 &  &&
 * Integer.parseInt(),转换成基本数据类型int,转换结果不具备属性和方法。
 * Integer.valueOf(),转换成Integer类（对象），具备该类属性和方法。与String.valueOf()的作用结果一致，结果为String对象
 */
public class DeviceControlActivity extends Activity {
    private final static String TAG = DeviceControlActivity.class.getSimpleName();

    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";

    private TextView mConnectionState;
    private TextView mDataField;
    private Button registerButton,logoutButton;
    private String mDeviceAddress,adress,serNumber;
    private String[] testData=new String[8];
    private static String str;
    private BluetoothLeService mBluetoothLeService;//自定义的一个继承自Service的服务
   
    private ArrayList<float[]> shujuList=new ArrayList<float[]>();
    private ArrayList<String> riqiList=new ArrayList<String>();
        
    private ArrayList<String> message_doc,msgTime,doc_xuewei,jingluo,shijian;
    private ArrayList<Integer> tiaoshu;
    private boolean mConnected = false;
    private BluetoothGattCharacteristic mCharacteristic,mCharacteristicNotify;
	private BluetoothGattService mnotyGattService;//三个长得很像，由大到小的对象BluetoothGatt、
                                               //BluetoothGattService、BluetoothGattCharacteristic、	                                           
    //经络仪的服务和特征值    
	private static final UUID uuid = UUID
				.fromString(SampleGattAttributes.YJ_BLE_Service);  
	private static final UUID UUID_READ_WRITE = UUID
			.fromString(SampleGattAttributes.YJ_BLE_READ_WRITE);
	private static final UUID UUID_NOTIFY = UUID
			.fromString(SampleGattAttributes.YJ_BLE_NOTIFY);
	
	public static int RETRANSFER_NUM=-1;
	private static final int DELETE_ONE = 0;
	public static boolean flag=true;
//	public boolean CLICKABLE=true;	//"暂不同步" 按钮 未注册和注册后  的不同状态
	public static int COUNT=0;//经络采样值计数，每7条一组。
	private	int i=1,dataNumber;//i为手机端发送处方计数。dataNumber为经络仪端一次发送的经络采样条数
	private String dataJluo;
	private String JYM,SOURECE_TO_JYM;
	public StringBuilder bb=new StringBuilder();
	private MyApplication app;
	private Timer timer;
	private TimerTask timetask;

    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
    //服务连接建立之后的回调函数。
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    // Handles various events fired by the Service.    
    // data:This can be a result of read or notification operations.
    // 接受来自设备的数据，可以通过读或通知操作获得。
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            //通过intent获得的不同action，来区分广播该由谁接收(只有action一致,才能接收)。
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                updateConnectionState(R.string.connected);
                invalidateOptionsMenu();
            } 
            else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                updateConnectionState(R.string.disconnected);
                invalidateOptionsMenu();
                clearUI();
            } 
            //发现服务后，自动执行回调方法onServicesDiscovered(),发送一个action=ACTION_GATT_SERVICES_DISCOVERED的广播，其他情况同理     
            else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the UI.                           	
            	mnotyGattService=mBluetoothLeService.getSupportedGattServices(uuid);
            	mCharacteristic=mnotyGattService.getCharacteristic(UUID_READ_WRITE);
            	mCharacteristicNotify=mnotyGattService.getCharacteristic(UUID_NOTIFY);
//            	mBluetoothLeService.setCharacteristicNotification(mCharacteristicNotify, true); 
            } 
            else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
            	Log.e("广播", "通知了吗");    
            	str=intent.getStringExtra(BluetoothLeService.EXTRA_DATA);            	
             	displayData(str);    //必须偶数个16进制字符发送，否则易丢失      
             	
             	//传输开始协议的单独处理
             	if(flag){
             		dealData();			//取出第一个数据
             	}else{ 
     				Log.e("未++之前的COUNT值"," "+COUNT);
     				COUNT++;			//计算一条完整测试数据的发送次数，每7次为一周期
 					testData[COUNT]=str;  					
 					if(COUNT==7){
 						StringBuilder sb=new StringBuilder();
 						for(int i=1;i<8;i++){
 							if(!(testData[i]==null))            				
 								sb.append(testData[i]);
 							else{
 								initConstant();
 								reTransfer();	//重传（有数据丢失，重传该数据）
 							}    							
 						}
 						dataJluo=sb.toString();
 						bb.append(SOURECE_TO_JYM+dataJluo);//校验码的数据源
 						Log.e(">>>>>>b 1>>>>>>", ""+bb);
 						if(JYM.equals(CRC.calcCrc16(bb.substring(0, bb.length())))){
 							shujuList.add(DataUtil.hex2DecStr(dataJluo));//每执行一次，数据列表增加一条
 							riqiList.add(testData[0]);//每执行一次，日期列表增加一条     
 							Toast toast1=Toast.makeText(DeviceControlActivity.this, "已接收数据条数： "+riqiList.size(), Toast.LENGTH_SHORT);
				     		toast1.show(); 
				     		if(riqiList.size()<dataNumber)
				     			beginTransfer();			     		
				     		else{
				     			Log.e(">>>>>>riqiList>>>>>>", ""+riqiList.size()+"   最后一条 日期："+riqiList.get(riqiList.size()-1));
				     			Log.e(">>>>>>shujuList>>>>>>", ""+shujuList.size()+"   最后一条 数据："+shujuList.get(riqiList.size()-1)[0]);
				     			Toast.makeText(DeviceControlActivity.this, "接收数据条数："+riqiList.size(), Toast.LENGTH_SHORT).show();					     		
				     			initConstant();
				     			
				     			timetask = new TimerTask() {
									@Override
									public void run() {
										// TODO Auto-generated method stub
										try {
					    					mMessage();//同步医生回复
					    				} catch (UnsupportedEncodingException e) {
					    					// TODO Auto-generated catch block
					    					e.printStackTrace();
					    				}
									}
								};
								timer.schedule(timetask, 500);
								if(riqiList.size()>0){
									new Thread(runnableUploadData).start();//上传数据	
								}else{
									Log.e(TAG, "没数据呃");
								}   		
					     	}     			
 						}else{
 							Log.e(">>>>>>bb>>>>>>", ""+bb);
 							Log.e(">>>>>>JYM>>>>>>", ""+JYM);
 							initConstant();
 							shujuList.clear();
 						    riqiList.clear();
 							reTransfer();
	        				Toast.makeText(DeviceControlActivity.this, "这条数据出错了", Toast.LENGTH_SHORT).show();
 						}						
 					}else{
 						mConfirm();
 					}					
	     		}
	        }
	    }
	};					
	
	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		return new AlertDialog.Builder(DeviceControlActivity.this).setTitle("正在同步数据...").setMessage("稍等啦").create();
	}

	private void initConstant(){
    	flag=true;
    	COUNT=0;
    	bb.delete(0, bb.length());//***********************************************   	
	}
    
    private void dealData(){
    	//每次都要计算校验码
    	Message msg=new Message();
    	if(str.substring(0, 2).equals("5C")){  //++++++++++++++++++++++++++++  		
    		switch(str.substring(2,6)){
    		case "C1D4"://5C C1 D4 00 00 C9 89 ++++++++++++++++++++++++++++++	
    			JYM=CRC.calcCrc16(str.substring(0, 10));
    			if(str.substring(10, 14).equals(JYM)){
    				Toast.makeText(getApplicationContext(), "经络仪拒绝连接", Toast.LENGTH_SHORT).show();
    				mBluetoothLeService.disconnect();
    				if(RETRANSFER_NUM==7){
    					timetask.cancel();				
    				}
    			}else{
    				reTransfer();
    			}
    			break;
    		case "C1D3"://5C C1 D3 00 00 08 38   	
    			JYM=CRC.calcCrc16(str.substring(0, 10));
    			if(str.substring(10, 14).equals(JYM)){
    				msg.what=TestStart.DIALOGCOME;
    				handler.sendMessage(msg);
    				if(RETRANSFER_NUM==7){
    					timetask.cancel();		
    				}
    				timetask=new TimerTask(){
    					@Override
    					public void run() {
    						// TODO Auto-generated method stub
    						ifRegister();
    					}    				
    				}; 
    				timer.schedule(timetask, 500);
    				
    			}else{
    				reTransfer();
    			}
    			break;
    		case "C1D1": //5C C1 D1 00 07 A0 00 00 00 00 00 00 AD F8 未注册    
    					 //5C C1 D1 00 07 A1 00 00 00 00 00 00 6D E8 已注册	
    			JYM=CRC.calcCrc16(str.substring(0, 24));
    			if(str.substring(24,28).equals(JYM)){   		
    				switch(str.substring(10,12)){
    				case "A0":
    					if(app.getNAME().equals("")||app.getNAME()==null){//判断时候需要命名的标志，注销之后要清空，见程序681行，否则全局变量没清空，会重复命名。
    						//跳转注册
//    						if(timetask!=null){
//    							timetask.cancel();
//    						}
    						msg.what=TestStart.DIALOGGO;
    	    				handler.sendMessage(msg);
    					}else{
    						try {//。。。。。。。。。。。。。。。。。。。。0612 修改。
//								if(timetask!=null){
//									timetask.cancel();
//								}							
    							setName();
    						} catch (UnsupportedEncodingException e) {
    							// TODO Auto-generated catch block
    							e.printStackTrace();
    						}    						
    					}
    					break;
    				case "A1":
    					//进行同步时间流程	                 
    					Toast.makeText(getApplicationContext(), "个人已注册", Toast.LENGTH_SHORT).show();	  					
    					timetask = new TimerTask() {
    						@Override
    						public void run() {
    							tTime();
    						}
    					};
    					timer.schedule(timetask, 500);
    					break;
    				default:
    					Toast.makeText(getApplicationContext(), "消息类型错误", Toast.LENGTH_SHORT).show();
    					break;
    				}		
    			}else{
    				reTransfer();	             //重传（有数据出错，重传该数据），我方差错重传时设置标志位。
    			}
    			break;
    		case "C1D2": //5C C1 D2 00 01 0F BA 49 ,命名为李斯>>>>>>>>>>>>
    			JYM=CRC.calcCrc16(str.substring(0, 12));
    			if(JYM.equals(str.substring(12, 16))){
    				Toast.makeText(DeviceControlActivity.this, "经络仪已命名", Toast.LENGTH_SHORT).show();  			 				
    				timetask = new TimerTask() {
						@Override
						public void run() {
							tTime();
						}
					};
					timer.schedule(timetask, 6000);
    			}else{
    				reTransfer();	             //重传（有数据出错，重传该数据），我方差错重传时设置标志位(参数)。
    			}
    			
    			break;
    		case "C2D2": //5C C2 D2 00 0C 0F 03 12 14 0E 04 00 00 00 00 00 00 81 9B 
    			JYM=CRC.calcCrc16(str.substring(0, 34));
    			if(JYM.equals(str.substring(34, 38))){
    				Toast toast3=Toast.makeText(DeviceControlActivity.this, "时间已同步:"+DataUtil.hex2Dec(str.substring(10,22)), Toast.LENGTH_SHORT);
    				toast3.show();  
    				
    				timetask = new TimerTask() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							dataNum();
						}
					};
					timer.schedule(timetask, 500);
					
    			}else{
    				reTransfer();	             //重传（有数据出错，重传该数据），我方差错重传时设置标志位(参数)。
    			}     		
    			break;
    		case "C3D3": //5C C3 D3 00 04 00 01 00 00 7B AD //5C C3 D3 00 04 00 02 00 00 7B 5D
    			JYM=CRC.calcCrc16(str.substring(0, 18));
    			if(JYM.equals(str.substring(18, 22))){
    				dataNumber=Integer.parseInt(str.substring(10, 14), 16);
    				Log.e("+_+_+_+_+_+_+_+_+ dataNumber：", ""+dataNumber);
    				Toast.makeText(DeviceControlActivity.this, "数据条数："+dataNumber, Toast.LENGTH_SHORT).show();
    				if(dataNumber>0){	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>数据为0，不传输，直接同步处方
    					timetask = new TimerTask() {
    						@Override
    						public void run() {
    							// TODO Auto-generated method stub
    							beginTransfer();
    						}
    					};
    				}else{
    					timetask = new TimerTask() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								try {
			    					mMessage();
			    				} catch (UnsupportedEncodingException e) {
			    					// TODO Auto-generated catch block
			    					e.printStackTrace();
			    				}
							}
						};
    				}
    				timer.schedule(timetask, 500);
    				
    			}else{
    				reTransfer();	             //重传（有数据出错，重传该数据），我方差错重传时设置标志位(参数)。>>>>>>>>修改函数
    			}	    		
    			break;
    		case "C4D4"://5C C4 D4 00 7E 00 00 00 00 00 00 62 91(第一条)5C C4 D4 00 7E 00 00 00 00 00 00 00 00 00 00 00 00 00 00(第二条)//第一条消息里面包含了整条协议的校验码
    			//先传送采集时间6比特的采集时间 +2比特的校验码    ,后传送采集数据120字节，分6次发送完毕。一共发7次,第七次发送该数据。
    			Log.e("=-=-=-=-=-=-=--=-=-=-=-= ：C4D4 :", str);
    			testData[0]=DataUtil.hexTime2DecStr(str.substring(10, 22));//时间在此解析
    			SOURECE_TO_JYM=str.substring(0, 22);
    			if(str.length()>24)//识别第一条消息，再取校验码
    				JYM=str.substring(22, 26);
    			Toast toast5=Toast.makeText(DeviceControlActivity.this, "6字节的采集时间:"+testData[0], Toast.LENGTH_SHORT);
    			toast5.show();   
    			
    			mConfirm();
    			flag=false;	 
    			
    			break;
    		case "C5D5": //5C C5 D5 00 04 00 00 00 00  
    			RETRANSFER_NUM=-1;//恢复初值
    			Log.e("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= ：", "C5D5执行到此 "+str);
    			mBluetoothLeService.setCharacteristicNotification(mCharacteristicNotify, false); 									    			            		
    			Toast toast9=Toast.makeText(DeviceControlActivity.this, "已发送总的字节数："+adress.length()/2, Toast.LENGTH_SHORT);
    			toast9.show(); 
//    			msg.what=TestStart.MESSAGEFINSH;
//    			handler.sendMessage(msg);
    			
    			new Thread(synch).start();	
    			break;
    		case "C6D6": //5C C6 D6 00 0A 00 00 00 00 00 00 00 00 00 00 CF CD 
    			JYM=CRC.calcCrc16(str.substring(0, 30));
    			if(JYM.equals(str.substring(30, 34))){
    				serNumber=DataUtil.hex2Dec(str.substring(10, 22));
    				new Thread(logout).start();
    				Log.e("+_+_+_+_+_+_+_+_+_+ serNumber： ", serNumber);
    			}else{
    				reTransfer();	             //重传（有数据出错，重传该数据），我方差错重传时设置标志位(参数)。
    			}     		
    			break;
    		case "C6D7":
    			JYM=CRC.calcCrc16(str.substring(0, 30));
    			if(JYM.equals(str.substring(30, 34))){
    				Toast.makeText(DeviceControlActivity.this, "经络仪拒绝注销", Toast.LENGTH_SHORT).show();
    			}else{
    				reTransfer();	             //重传（有数据出错，重传该数据），我方差错重传时设置标志位(参数)。
    			}     		
    			break;	 			
    		case "C7D7": //5C C7 D7 00 00 41 79 
    			JYM=CRC.calcCrc16(str.substring(0, 10));
    			if(JYM.equals(str.substring(10, 14))){
    				int num=adress.length()/40;
    				if((i<num)&&(adress.length()>40)){
    					byte[]mybyte=DataUtil.hexStringToByte(adress.substring(i*40, (i+1)*40));
    					mBluetoothLeService.writeCharacteristic(mCharacteristic, mybyte);
    					i++;  
    					Log.e("罗罗", String.valueOf(i));
    				}else{
    					byte[]mybyte=DataUtil.hexStringToByte(adress.substring(40*num, adress.length()));
    					if(mBluetoothLeService.writeCharacteristic(mCharacteristic, mybyte)){//+++++++++++++++++++++++++++++++++++++++++
    						Toast.makeText(getApplicationContext(), "处方传输完毕", Toast.LENGTH_SHORT).show();
    						i=1;//将i恢复初值并不再接收广播++++++++++++++++++++++++++++++++++++++
    					}else{
    						Toast.makeText(getApplicationContext(), "处方写操作错误", Toast.LENGTH_SHORT).show();   						
    					}				
    				}              			
    			}else{
    				reTransfer();	             //重传（有数据出错，要求经络仪重传该数据），我方差错重传时设置标志位(参数)。
    			}
    			break;
    		case "C8D8"://5C C8 D8 00 00 56 4A 
    			switch(RETRANSFER_NUM){
    			case 0:
    				ifRegister();    			
    				break;
    			case 1:
    				try {
    					setName();
    				} catch (UnsupportedEncodingException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
    				break;
    			case 2:
    				tTime();	
    				break;
    			case 3:
    				dataNum();
    				break;
    			case 4:
    				beginTransfer();
    				break;
    			case 5:
    				try {
    					i=1;
    					mMessage();
    				} catch (UnsupportedEncodingException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    				break;
    			case 6:
    				mOut();
    				break;
    			case 7:
    				if(RETRANSFER_NUM!=-1&&RETRANSFER_NUM!=6){ //判断协议是否执行过,等于6时，注销单列出来了，没用定时器。
    					timetask.cancel();    					
    				}
    				ifConnect();
    				break;
    			default:
    				break;     		
    			}
    			
    			break;
    		default:     		
    			break;
    		}             	
    	}else{
    		Toast.makeText(getApplicationContext(), "标志位异常", Toast.LENGTH_SHORT).show();
    	}
    }

    public void setCLICKABLE(boolean CLICKABLE){
		logoutButton.setClickable(CLICKABLE);
    }
    
    private void clearUI() {
        mDataField.setText(R.string.no_data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.ble_main);
        
        app=(MyApplication) this.getApplication();
        Intent intent = getIntent();
        mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);
        app.setXULIEHAO(DataUtil.adressToString(mDeviceAddress));
        
        Drawable bj=this.getResources().getDrawable(R.drawable.bj);
        getActionBar().setTitle(R.string.app_name);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setBackgroundDrawable(bj);
        
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        
//		builder = new Builder(DeviceControlActivity.this);
//		builder.setOnDismissListener(onDismissListener);

        init(); 
    }
    
	OnDismissListener onDismissListener=new OnDismissListener(){
		@Override
		public void onDismiss(DialogInterface dialog) {
			// TODO Auto-generated method stub
			dialog.dismiss();
		}		
	};
    
    private void init() {
		// TODO Auto-generated method stub
		registerButton=(Button)findViewById(R.id.register);
		registerButton.setOnClickListener(listener);
		
		logoutButton=(Button)findViewById(R.id.logout);
		logoutButton.setOnClickListener(listener);
				
        mDataField = (TextView) findViewById(R.id.data_value);
        mDataField.setText(mDeviceAddress);      
        mConnectionState = (TextView) findViewById(R.id.connection_state); 
        
        new Thread(runnableStart).start();
    }
    
	private OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			if (mConnected) {
				switch (v.getId()) {
				case R.id.register:				
//					=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
					timer = new Timer();
					timetask = new TimerTask() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Message msg=new Message();
							if(mConnected){
								boolean flag=ifConnect();
								if(flag){
									msg.what=TestStart.SHOW;								
								}else{
									msg.what=TestStart.DISMISS;			
								}							
							}else{
								msg.what=TestStart.DISMISS;
							}
							handler.sendMessage(msg);													
						}
					};
					timer.schedule(timetask, 500, 5000);					
					break;
				case R.id.logout:
					Intent intent = new Intent();
					intent.setClass(DeviceControlActivity.this,UserListDeleteOne.class);
					startActivityForResult(intent, DELETE_ONE);			
					break;
				default:
					break;
				}
			} else {
				Toast.makeText(getApplicationContext(), "请先连接蓝牙",Toast.LENGTH_SHORT).show();
			}
		}
	};	

	  public void onActivityResult(int requestCode, int resultCode, Intent data){
			switch (requestCode){
				case DELETE_ONE:               
					if (resultCode == Activity.RESULT_OK){
						String str=data.getExtras().getString(UserListDeleteOne.SHAN);
						if(str.equals("OK")){
							boolean flag=mOut();
//							app.setNAME("");//清空命名，避免重复命名。
							Log.e("+_+_+_+_+_+_+_+_+ logout：", ""+flag);
						}else{		
							break;
						}
					}						
					break;			
				default	:
					break;	
			}
		}							
	
    //线程返回值处理    
    private Handler handler = new Handler() {  
    	@SuppressWarnings("deprecation")
		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg){
    		switch(msg.what){
    		case TestStart.SHOW:
    			Toast.makeText(getApplicationContext(), "请在经络仪上授权连接", Toast.LENGTH_SHORT).show();
				break;
    		case TestStart.DISMISS:
    			Toast.makeText(getApplicationContext(), "请先连接蓝牙", Toast.LENGTH_SHORT).show();
    			break;		
    		case TestStart.COMPLETED:
        		setCLICKABLE(true);
    			new Thread(runnableDownloadReply).start();		      		       		 			
        		break;
        	case TestStart.UNCOMPLETED:
        		Toast.makeText(DeviceControlActivity.this, "经络仪尚未注册,请点击同步数据进行注册", Toast.LENGTH_SHORT).show();
        		setCLICKABLE(false);
        		break;
        	case TestStart.FAIL:
        		Toast.makeText(DeviceControlActivity.this, "返回值解析出错啦！请查看网络", Toast.LENGTH_SHORT).show();
        		break;
        	case TestStart.UNFINISH:
        		Toast.makeText(DeviceControlActivity.this, "statusCode异常", Toast.LENGTH_SHORT).show();
        		break;
        	case TestStart.YES:
        		Toast.makeText(DeviceControlActivity.this, "处方下载成功", Toast.LENGTH_SHORT).show();
        		break;
        	case TestStart.NO:        		
        		Toast.makeText(DeviceControlActivity.this, "处方下载失败", Toast.LENGTH_SHORT).show();     		        		
        		break;
        	case TestStart.KONG:    
        		Toast.makeText(DeviceControlActivity.this, "暂无医生回复和经络处方", Toast.LENGTH_SHORT).show();     		        		
        		break;
        	case TestStart.ACHIVE:      		
        		Toast.makeText(DeviceControlActivity.this, "数据上传成功", Toast.LENGTH_SHORT).show();     		
        		initConstant();//初始化常量
        		shujuList.clear();
        		riqiList.clear();
        		RETRANSFER_NUM=-1;
    			break;
        	case TestStart.LOSE:
        		Toast.makeText(DeviceControlActivity.this, "数据上传失败", Toast.LENGTH_LONG).show();     		
        		break;  
        	case TestStart.LOGOUT:
        		setCLICKABLE(false);
        		app.setNAME("");//清空命名，避免重复命名。
        		Toast.makeText(DeviceControlActivity.this, "注销完成", Toast.LENGTH_LONG).show();     				
        		break;
        	case TestStart.NOLOGOUT:
        		Toast.makeText(DeviceControlActivity.this, "注销失败", Toast.LENGTH_LONG).show();     		
        		break;
//        	case TestStart.MESSAGEFINSH://有医生回复，传完后，与服务器同步
//        		RETRANSFER_NUM=-1;//恢复初值
//        		removeDialog(1);
//        		new Thread(synch).start();	
//        		break;
        	case TestStart.NOMESSAGE://无医生回复，不用传输，不用同步
        		RETRANSFER_NUM=-1;//恢复初值
        		if(timetask!=null){
					timetask.cancel();
				}
        		removeDialog(1);
        		startActivity(new Intent(DeviceControlActivity.this,NormalUse.class));				
        		finish();
        		break;
        	case TestStart.DIALOGCOME:
    			onCreateDialog(1).show();
    			break;
        	case TestStart.DIALOGGO:
        		if(timetask!=null){
					timetask.cancel();
				}
        		removeDialog(1);
				Toast.makeText(getApplicationContext(), "个人未注册", Toast.LENGTH_SHORT).show();
				startActivity(new Intent().setClass(getApplication(), LogIn.class));				
				finish();	
        		break;
        	case TestStart.SYNCHFINSH:
        		if(timetask!=null){
        			timetask.cancel();
        		}
        		removeDialog(1);
        		Toast.makeText(getApplicationContext(), "同步成功", Toast.LENGTH_SHORT).show();
        		startActivity(new Intent(DeviceControlActivity.this,NormalUse.class));				
    			finish();
        		break;       	 
        	case TestStart.SYNCHFAIL:
        		Toast.makeText(getApplicationContext(), "同步失败", Toast.LENGTH_SHORT).show();
        		break;       	 
        	default:
    			break;
    		}
    	}
   }; 
    
   //注销线程   
	Runnable logout = new Runnable() {
		private String url = HttpUrl.IP+HttpUrl.DIR+"logout";
		private String back;
		private JSONObject jsonLogout;
		private int statusCode;
		public void run() {
			Map<String, String> map = new HashMap<String, String>();
			map.put("username", app.getXULIEHAO()); // 测试时，注意修改。
			try {
				back = NetTool.sendGetRequest(url, map, "utf-8");
				jsonLogout = new JSONObject(back);
				statusCode = jsonLogout.getInt("statusCode");
			} catch (Exception e) {
				e.printStackTrace();
			}
			Message msg = new Message();
			if (statusCode == -1)
				msg.what = TestStart.LOGOUT;
			else {
				msg.what = TestStart.NOLOGOUT;
			}
			handler.sendMessage(msg);
		}
	};
	
   	//验证是否注册
   	Runnable runnableStart = new Runnable(){
		private String url =HttpUrl.IP+HttpUrl.DIR+"if_register";
		private String JsonString,result;
		private int statusCode;
		private JSONObject Json;
 		public void run(){	
 			Message msg = new Message();  
 			Map<String, String> map = new HashMap<String, String>(); 
 			map.put("username",app.getXULIEHAO()); 
 			Log.e("+_+_+_+_+_+_+_+_+_+ :runnableStart 1", ""+app.getXULIEHAO());
 			try {
				JsonString = NetTool.sendGetRequest(url, map, "utf-8");	
				Log.e("+_+_+_+_+_+_+_+_+_+ :runnableStart 2", ""+JsonString);				
 				Json=new JSONObject(JsonString);			
 				Log.e("+_+_+_+_+_+_+_+_+_+ :runnableStart 3", ""+Json);				
 				result=Json.getString("result");
 				Log.e("+_+_+_+_+_+_+_+_+_+ :runnableStart 4", ""+result);				
 				statusCode=Json.getInt("statusCode");
 				Log.e("+_+_+_+_+_+_+_+_+_+ :runnableStart 5", ""+statusCode);				
 				if(statusCode==-1){
 					JSONObject subjson=new JSONObject(result); 
 					Log.e("+_+_+_+_+_+_+_+_+_+ :runnableStart 6", ""+subjson);				
 					app.setID(subjson.getString("ID"));
 					app.setNAME(subjson.getString("true_name"));//开始解第二层json
					app.setDoc_ID(subjson.getString("doctorID"));
 					msg.what = TestStart.COMPLETED;   					
 				}else if(statusCode==4){
 					msg.what = TestStart.UNCOMPLETED; 				
 				}else{
 					msg.what = TestStart.UNFINISH;  					
 				}	
 			} 	  			
 			catch (Exception e) {
 				e.printStackTrace();			
				msg.what = TestStart.FAIL;		
 			}  									
 			handler.sendMessage(msg); 
 		}
   	};
  	 	
   //上传经络数据
   Runnable runnableUploadData = new Runnable(){
		private String url =HttpUrl.IP+HttpUrl.DIR+"upload_data";
		private String JsonString;
		private int statusCode;
		JSONObject Json;
		public void run()//复写Thread类中的run(),此方法包含线程的处理，在UI线程中调用，来启动线程。
		{	
			Message msg = new Message();  
			Map<String, String> map = new HashMap<String, String>();  			
			Log.e("+_+_+_+_+_+_+_+_+_+ :runnableUploadData", ""+app.getID());
			map.put("userid",app.getID());
			Log.e("+_+_+_+_+_+_+_+_+_+_+_+ :userid", ""+app.getID());
			for(int i=0;i<shujuList.size();i++){
				Log.e("------------------------------------- shujuList: ", ""+shujuList.size()+"   shujuList.get(i).size()"+shujuList.get(i)+"   "+shujuList.get(i)[119]);
				Log.e("------------------------------------- riqiList: ", ""+riqiList.size()+"   "+riqiList.get(i));			
			}
			map.put("data", initData(shujuList,riqiList));				
			try {
				JsonString = NetTool.sendPostRequest(url, map, "utf-8");	
				Log.e("+_+_+_+_+_+_+_+_+_+_+_+ :JsonString1", JsonString);
				Json=new JSONObject(JsonString);
				statusCode=Json.getInt("statusCode");
				
				if(statusCode==-1)
					msg.what = TestStart.ACHIVE; 
				else{
					msg.what = TestStart.LOSE;
				}
			} 	  			
			catch (Exception e) {
				e.printStackTrace();
				msg.what = TestStart.FAIL;	
				Log.e("+_+_+_+_+_+_+_+_+_+_+_+ :JsonString1", JsonString);
			} 		     
			handler.sendMessage(msg); 					
		}
  }; 
  
  
  private String[]Jmname={"胆经", "肝经", "肺经", "大肠经", "胃经", "脾经", "心经", "小肠经",
			"膀胱经", "肾经", "心包经", "三焦经", "任脉（手）", "任脉（足）", "督脉（手）", "督脉（足）",
			"冲脉（手）", "冲脉带脉（足）", "膈腧（手）", "膈腧（足）"};
  
  //拼装数据
  private String initData(ArrayList<float[]> list, ArrayList<String> listtime) {
		// TODO Auto-generated method stub
		String JmData = "";
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		Log.e("=-=-=-=-=-=-=-=-=-= initData: list", ""+list.size());
		for (int j = 0; j < list.size(); j++) {
			float[] data = list.get(j);
			Log.e("=-=-=-=-=-=-=-=-=-= initData: data.length", ""+data.length);
			sb.append("[");//------------------------------------------------------------------------
			String time =listtime.get(j);//
			Log.e("=-=-=-=-=-=-=-=-=-= initData: time", ""+time);
			if (data.length == 120) {
				for (int i = 0; i < 20; i++) {
					sb.append("{" + "\"jmname\":" + "\"" + Jmname[i] + "\""
							+ "," + "\"leftthirnum\":" + data[i * 6] + ","
							+ "\"leftsecnum\":" + data[i * 6 + 1] + ","
							+ "\"leftfirnum\":" + data[i * 6 + 2] + ","
							+ "\"rightfirnum\":" + data[i * 6 + 3] + ","
							+ "\"rightsecnum\":" + data[i * 6 + 4] + ","
							+ "\"rightthirnum\":" + data[i * 6 + 5] + ","
							+ "\"sampletime\":" + "\"" + time + "\"" + "}"
							+ ",");
				}
				sb.deleteCharAt(sb.length() - 1);
				sb.append("],");
			}else{
				Log.e(TAG, "第"+j+"条数据长度不足120");
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");// json字符串到此完成
		JmData=sb.toString();
		Log.e("=-=-=-=-=-=-=-=-=-= initData: JmData", ""+JmData);
		return JmData;
	}
 	
	private byte[] mTransmit() {
		// TODO Auto-generated method stub		
		StringBuffer s=new StringBuffer();
		s.append("5C");
		s.append("A4B4");
		s.append("0004");
		s.append("00000000");
		s.append(CRC.calcCrc16(s.toString()));

 		adress=s.toString();
		byte[]send=DataUtil.hexStringToByte(adress);	
		
		Log.e("旦", String.valueOf(send[0]));//byte值就是它的实际值。
		return send;
	}
	
	private void beginTransfer(){
		RETRANSFER_NUM=4;
		initConstant();
		byte[]transmit=mTransmit();				
		if(mBluetoothLeService.writeCharacteristic(mCharacteristic, transmit)){
			mBluetoothLeService.setCharacteristicNotification(mCharacteristicNotify, true); 
		}else{
			Log.e(TAG, "传输开始：写操作错误");
		}
	}
	
	private boolean ifRegister(){
		RETRANSFER_NUM=0;
		boolean retu=false;
		StringBuffer s=new StringBuffer();;
		s.append("5C");
		s.append("A1B1");
		s.append("0004");
		s.append("00000000");
		s.append(CRC.calcCrc16(s.toString()));

		adress=s.toString();
		byte[]send=DataUtil.hexStringToByte(adress);	
		
		if(mBluetoothLeService.writeCharacteristic(mCharacteristic, send)){
			retu=true;
		}else{
			Log.e(TAG, "注册：写操作错误");
		}
		return retu;
	}
	
	private boolean setName() throws UnsupportedEncodingException{
		RETRANSFER_NUM=1;
		boolean retu=false;
		StringBuffer s=new StringBuffer();;
		s.append("5C");
		s.append("A1B2");
		s.append("000C");
		//此名字从注册时就获取			
//		s.append(DataUtil.GBK2eight("李斯"));//汉字转换为十六进制字符，十六进制字符转换为汉字，需要修改
		s.append(DataUtil.GBK2eight(app.getNAME()));//汉字转换为十六进制字符，十六进制字符转换为汉字，需要修改
		s.append("A1000000");
		s.append(CRC.calcCrc16(s.toString()));

		adress=s.toString();
		byte[]send=DataUtil.hexStringToByte(adress);
		
		if(mBluetoothLeService.writeCharacteristic(mCharacteristic, send)){
			mBluetoothLeService.setCharacteristicNotification(mCharacteristicNotify, true); 
			retu=true;
		}else{
			Log.e(TAG, "命名：写操作错误");
		}
		return retu;
	}
	
	private boolean tTime(){
		RETRANSFER_NUM=2;
		boolean retu=false;
		StringBuffer s=new StringBuffer();;
		s.append("5C");
		s.append("A2B2");
		s.append("000C");
		s.append(getDate());
		s.append(CRC.calcCrc16(s.toString()));

		adress=s.toString();
		byte[]send=DataUtil.hexStringToByte(adress);	
		if(mBluetoothLeService.writeCharacteristic(mCharacteristic, send)){
			mBluetoothLeService.setCharacteristicNotification(mCharacteristicNotify, true); 
			retu=true;
		}else{
			Log.e(TAG, "时间：写操作错误");
		}
		return retu;
	}
	
	private boolean dataNum(){
		RETRANSFER_NUM=3;
		boolean retu=false;
		StringBuffer s=new StringBuffer();;
		s.append("5C");
		s.append("A3B3");
		s.append("0004");
		s.append("00000000");
		s.append(CRC.calcCrc16(s.toString()));

		adress=s.toString();
		byte[]send=DataUtil.hexStringToByte(adress);	
		if(mBluetoothLeService.writeCharacteristic(mCharacteristic, send)){
			mBluetoothLeService.setCharacteristicNotification(mCharacteristicNotify, true); 
			retu=true;
		}else{
			Log.e(TAG, "数据：写操作错误");
		}		
		return retu;
	}		
	
	
	private boolean mMessage() throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		Message msg=new Message();
		boolean retu=false;		
		if(message_doc.size()>0){	
			Log.e(TAG, "---------------- "+"message_doc.size():"+message_doc.size()+" msgTime.size():"+msgTime.size()+" doc_xuewei.size():"+doc_xuewei.size()+" shijian.size():"+shijian.size()+" jingluo.size():"+jingluo.size());
			for(int m=0;m<tiaoshu.size();m++){
				Log.e(TAG, "----------------yyyy \t:"+" "+tiaoshu.get(m));					
			}
			for(int a=0;a<message_doc.size();a++){
				Log.e(TAG, "----------------aaaa \t:"+" "+message_doc.get(a));							
			}
			for(int b=0;b<message_doc.size();b++){
				Log.e(TAG, "----------------bbbb \t:"+" "+msgTime.get(b));							
			}
//			for(int b=0;b<message_doc.size();b++){
//				Log.e(TAG, "----------------dddd \t:"+" "+jingluo.get(b));							
//			}
//			for(int b=0;b<message_doc.size();b++){
//				Log.e(TAG, "----------------eeee \t:"+" "+shijian.get(b));							
//			}
			
			RETRANSFER_NUM=5;
			byte[]message;
			StringBuffer s=new StringBuffer();
			int k=-1;
			s.append("5C");//1
			s.append("A5B5");//2	
			s.append(toOneByteHex(message_doc.size()));//消息条数，1字节,tiaoshu[]与message_doc[]的长度必须一样
			for(int i=0;i<msgTime.size();i++){
				s.append(decstr2HexStr(msgTime.get(i)));//医生回复时间，6字节>>>>>>>>>>>>>>>>>>>>>>>（进制处理）
				Log.e(TAG, "----------------0000:"+s);
				s.append(toTwoByteHex(DataUtil.getCountStringToGBK(message_doc.get(i))));//医生回复长度，占2字节+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=
				Log.e(TAG, "----------------1111:"+s);
				s.append(DataUtil.stringToGBK(message_doc.get(i)));//医生回复内容，长度不确定>>>>>>>>（中文处理GBK）
				Log.e(TAG, "----------------2222:"+s);
//				s.append(DataUtil.toTwoHex(tiaoshu.get(i)));//处方条数,1字节
				
				if(tiaoshu.get(i)>0){
					s.append(DataUtil.toTwoHex(tiaoshu.get(i)));//处方条数,1字节
					Log.e(TAG, "----------------3333:"+s);
					for(int j=i;j<tiaoshu.get(i)+i;j++){
						k++;//k：循环执行的次数，初始值设为-1
						s.append(DataUtil.toTHex(jingluo.get(k)));//第i条处方，调理经络,12个字节>>>>>>>>>>>（中文处理GBK）
						Log.e(TAG, "----------------4444:"+s);
						s.append(decstr2HexStr(shijian.get(k)));//调理时间和时长，3个字节				
						Log.e(TAG, "----------------5555:"+s);
					}									
				}else{
					s.append(DataUtil.toTwoHex(tiaoshu.get(i)));//处方条数,1字节
					Log.e(TAG, "----------------------------------tiaoshu.get(i):  "+tiaoshu.get(i));					
				}
				
			}		
			Log.e("罗丑旦", s.length()+" ");
			s.insert(6, toTwoByteHex((s.length()-6)/2));//消息体长度，2字节，值不确定.length有问题++++++++++++++++++++	
			//校验码
			s.append(CRC.calcCrc16(s.toString()));//2字节的校验码
			
			adress=s.toString();
			Log.e("罗丑旦", adress.length()+"  "+adress);
			
			if(adress.length()>40)
				message=DataUtil.hexStringToByte(adress.substring(0, 40));				
			else{
				message=DataUtil.hexStringToByte(adress);
			}
			
			if(mBluetoothLeService.writeCharacteristic(mCharacteristic, message)){
				mBluetoothLeService.setCharacteristicNotification(mCharacteristicNotify, true); 
				retu=true;
			}else{
				Log.e(TAG, "处方：写操作错误");
			}	
		}else{
			Log.e(TAG, "暂无医生回复和经络处方");
			msg.what=TestStart.NOMESSAGE;
			handler.sendMessage(msg);		
		}
		return retu;	
	}
	
	private boolean mOut() {
		// TODO Auto-generated method stub
		RETRANSFER_NUM=6;
		boolean retu=false;
		StringBuffer s=new StringBuffer();;
		s.append("5C");
		s.append("A6B6");
		s.append("0004");//消息体长度
		s.append("00000000");
		s.append(CRC.calcCrc16(s.toString()));

		adress=s.toString();
		byte[]send=DataUtil.hexStringToByte(adress);	
		if(mBluetoothLeService.writeCharacteristic(mCharacteristic, send)){
			mBluetoothLeService.setCharacteristicNotification(mCharacteristicNotify, true); 
			retu=true;
		}else{
			Log.e(TAG, "注销：写操作错误");
		}			
		return retu;	
	}
	
	private boolean mConfirm() {
		// TODO Auto-generated method stub
		boolean retu=false;
		StringBuffer s=new StringBuffer();;
		s.append("5C");
		s.append("A7B7");
		s.append("0000");//消息体长度
		//校验位
		s.append(CRC.calcCrc16(s.toString()));
		
		adress=s.toString();
		byte[]send=DataUtil.hexStringToByte(adress);	
		if(mBluetoothLeService.writeCharacteristic(mCharacteristic, send)){
			retu=true;
		}else{
			Log.e(TAG, "确认：写操作错误");
		}					
		return retu;	
	}
	
	private boolean reTransfer() {
		// TODO Auto-generated method stub	
		boolean retu=false;
		StringBuffer s=new StringBuffer();;
		s.append("5C");
		s.append("A8B8");
		s.append("0000");//消息体长度
		//校验位
		s.append(CRC.calcCrc16(s.toString()));

		adress=s.toString();
		byte[]send=DataUtil.hexStringToByte(adress);	
		
		if(mBluetoothLeService.writeCharacteristic(mCharacteristic, send)){
			mBluetoothLeService.setCharacteristicNotification(mCharacteristicNotify, true); 
			retu=true;
		}else{
			Log.e(TAG, "重传：写操作错误");
		}			
		return retu;	
	}
	
	private boolean ifConnect() {
		// TODO Auto-generated method stub	
		boolean retu=false;
		if(mConnected){
			RETRANSFER_NUM=7;
			StringBuffer s=new StringBuffer();
			s.append("5C");
			s.append("A1B3");
			s.append("0000");//消息体长度2字节
			//校验位
			s.append(CRC.calcCrc16(s.toString()));
			
			adress=s.toString();
			byte[]send=DataUtil.hexStringToByte(adress);	
			
			if(mBluetoothLeService.writeCharacteristic(mCharacteristic, send)){
				mBluetoothLeService.setCharacteristicNotification(mCharacteristicNotify, true); 
				retu=true;
			}else{
				Log.e(TAG, "是否连接：写操作错误");
			}					
		}else{
			return retu;
		}
		return retu;	
	}
	
    private String getDate() {   
//    	HH：返回的是24小时制的时间，hh：返回的是12小时制的时间    　　　 
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");       
        String date=sDateFormat.format(new java.util.Date()); 
        StringBuffer str=new StringBuffer();
        str.append(date.substring(2, 4));
        str.append(date.substring(5, 7));
        str.append(date.substring(8, 10));
        str.append(date.substring(12, 14));
        str.append(date.substring(15, 17));
        str.append(date.substring(18, 20));
        str.append("000000000000");
        
//    	获取系统时间的时制
//      ContentResolver cv = this.getContentResolver();
//      String strTimeFormat = android.provider.Settings.System.getString(cv,
//                               android.provider.Settings.System.TIME_12_24);    
        return decstr2HexStr(str.toString());
    } 
    
    Runnable synch=new Runnable(){
		private String url = HttpUrl.IP + HttpUrl.DIR + "synch";		
		private JSONObject jsonReply; 
		private String reply; 
		private int statusCode;
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Map<String, String> map = new HashMap<String, String>();
			Message msg=new Message();
			map.put("userid", app.getID());		
			map.put("ifsynch", "1");	
			try {
				reply=NetTool.sendGetRequest(url, map, "utf-8");
				jsonReply=new JSONObject(reply);
				Log.e(TAG, "=-=-=-=-=-=-= :synch: jsonReply: "+jsonReply);
				statusCode = jsonReply.getInt("statusCode");// Toast据此提示
				if(statusCode==-1){
					msg.what=TestStart.SYNCHFINSH;					
				}else{
					msg.what=TestStart.SYNCHFAIL;						
				}
				handler.sendMessage(msg);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, "=-=-=-=-=-=-= :synch: "+reply);
			}
		}
    }; 
    
    // 下载最新十条反馈     
	Runnable runnableDownloadReply = new Runnable() {
		private String url = HttpUrl.IP + HttpUrl.DIR + "doc_back";
		private JSONObject jsonReply;
		private JSONArray array,subjsonReply;
		private int statusCode;

		public void run() {
			message_doc=new ArrayList<String>();
			msgTime=new ArrayList<String>();
			doc_xuewei=new ArrayList<String>();
			jingluo=new ArrayList<String>();
			shijian=new ArrayList<String>();
			tiaoshu=new ArrayList<Integer>();
			Message msg = new Message();
			Map<String, String> map = new HashMap<String, String>();
			map.put("userid", app.getID());
			map.put("ifdownload", "1");//1下载未下载过的医生回复;			
			try {
				String retStrDoctorReply = NetTool.sendGetRequest(url, map,"utf-8");
				jsonReply = new JSONObject(retStrDoctorReply);
				statusCode = jsonReply.getInt("statusCode");// Toast据此提示
				if(statusCode == -1){
					array = jsonReply.getJSONArray("result");// 第一层
//-------------------------------------------------------------------
					if(array!=null&&array.length()>0){
						message_doc=getStrarray(array,"doc_back");//只要有回复，肯定不为空
						msgTime=getStrarray(array,"doc_time");//只要有回复，肯定不为空
						doc_xuewei=getStrarray(array,"doc_xuewei");				
						//  肺经/3:00/40分钟,大肠经/3:00/50分钟,肺经/1:00/20分钟,肺经/3:00/40分钟
						Log.e(TAG, "=-=-=-=-=-=-= : doc_xuewei.size(): "+doc_xuewei.size()); 
						for(int i=0;i<doc_xuewei.size();i++){
							msgTime.set(i, transferDateTime(msgTime.get(i)));
							String[]diot=doc_xuewei.get(i).split(",");
							Log.e(TAG, "=-=-=-=-=-=-= : diot.length: "+diot.length); 
							Log.e(TAG, "=-=-=-=-=-=-= : diot[0]: "+diot[0]); 
							if(!(diot[0].equals(""))){
								tiaoshu.add(diot.length);
								for(int j=0;j<diot.length;j++){
									String[]chufang=diot[j].split("/");
									jingluo.add(chufang[0]);
									shijian.add(transferTime(chufang[1])+transferMinute(chufang[2]));
								}								
							}else{
								tiaoshu.add(0);
							}
						}
						msg.what=TestStart.YES;
					}else{
						msg.what=TestStart.KONG;					
					}
				}else{
					msg.what=TestStart.KONG;
				}
			} catch (Exception e) {
				e.printStackTrace();
				Log.e(TAG,"========retStrDoctorReply值解析错误");
			}
			handler.sendMessage(msg);
		}
	};		
	
	//医生回复时间	
	private String transferDateTime(String time){
		String ttime="";
		ttime=time.replace("-", "").replace(":", "").replace(" ", "").trim().substring(2);
		return ttime;
	}
	
	//调理时长     
	private String transferMinute(String time){
		String ttime="";
		ttime=time.replace("分钟", "");
		switch(ttime.length()){
		case 2:
			break;	
		case 1:
			ttime="0"+ttime;
			break;
		default:
			break;
		}
		return ttime;
	}
	
	//调理时间    
	private String transferTime(String time){
		String ttime="";
		ttime=time.replace(":", "");
		switch(ttime.length()){
		case 4:
			break;
		case 3:
			ttime="0"+ttime;
			break;
		case 2:
			ttime=ttime+"00";
			break;	
		default:
			break;
		}
		return ttime;
	}
	
 	private ArrayList<String> getStrarray(JSONArray array,String key){		
 		String[]strarray=new String[array.length()];
 		ArrayList<String> list=new ArrayList<String>();
		for(int i=0;i<array.length();i++){			
			try {
				strarray[i]=(array.getJSONObject(i).getString(key));//要求医生回复不能为空
				if((!strarray[i].equals(""))&&(!strarray[i].equals("(NULL)"))&&strarray[i]!=null){
					list.add(strarray[i]);			
				}else{
					list.add("");								
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}				
 		return list;	
 	}
 	

    //注意逻辑，从服务器获取的经络穴位和调理时间，按照相同顺序排列在jingluo[]和 shijian[]两个数组中。处方条数添加在tiaoshu[]数组中
//    private String[] message_doc={"多喝水多喝水多喝水多喝水多喝水" 
//    		,"腿","喝茶","睡觉","早起","早睡"};
//    private String[] msgTime={"150901180110","150902180220","150903180330","150904184040","150905180550","1509061850"};
//    private String[] jingluo={"肝经","心经","肺经","胆经","大肠经","胃经","脾经","小肠经","心包经","三焦经"};
//    private String[] shijian={"101010","101020","101030","101040","101050","200000","101030","101040","101050","200000"};
//    private int[]tiaoshu={1,2,1,3,2,1};
    
    private String toOneByteHex(int i){
    	String st;
    	st=Integer.toHexString(i).toUpperCase();//整型转16进制字符串
    	if(st.length()<2){
    		st="0"+st;
    	}
    	return st;
    }
    
    private String toTwoByteHex(int i){
    	String st;
    	st=Integer.toHexString(i).toUpperCase();
    	switch(st.length()){
    	case 1:
    		st="000"+st;
    		break;
    	case 2:
    		st="00"+st;
    		break;
    	case 3:
    		st="0"+st;
    		break;
    	case 4:
    		break;   	
    	}
    	return st;
    }
    
    //数字转16进制
    public String decstr2HexStr(String str) { 
		 StringBuffer sb=new StringBuffer();
		 str=str.replace(" ", "").trim();
		 if(str.length()%2==0){
			 for(int i=0;i<str.length();i=i+2){
				 Log.e(TAG, "+_+_+_+_+_+_+_+_+ str.substring(i, i+2):"+str.substring(i, i+2));
				 int value=Integer.parseInt(str.substring(i, i+2));
				 if(value!=0){
					 String hex = Integer.toHexString(value).toUpperCase(); 
					 if (hex.length() == 1){
						 hex="0"+hex;
					 }
					 sb.append(hex);				 
				 }else{
					 sb.append("00");
				 }
				Log.e(TAG, "+_+_+_+_+_+_+_+_+ :"+sb);
			 }			 
		 }else{
			 Toast.makeText(DeviceControlActivity.this, "转16进制出问题", Toast.LENGTH_SHORT).show();
		 }	 		 
	     return sb.toString();    
	 } 
    
    @Override
    protected void onResume() {
        super.onResume();
        //Register a BroadcastReceiver to be run in the main activity thread.
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
            Log.d(TAG, "Connect request result=" + result);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
        removeDialog(1);
        if(timetask!=null){
        	timetask.cancel();  
        }   	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gatt_services, menu);
        if (mConnected) {
            menu.findItem(R.id.menu_connect).setVisible(false);
            menu.findItem(R.id.menu_disconnect).setVisible(true);
            
        } else {
            menu.findItem(R.id.menu_connect).setVisible(true);
            menu.findItem(R.id.menu_disconnect).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_connect:
                mBluetoothLeService.connect(mDeviceAddress);
                return true;
            case R.id.menu_disconnect:
            	if(RETRANSFER_NUM!=-1&&RETRANSFER_NUM!=6){
            		timetask.cancel();  		
            	}
                mBluetoothLeService.disconnect();
                return true;
            case android.R.id.home:
            	if(RETRANSFER_NUM!=-1&&RETRANSFER_NUM!=6){
            		timetask.cancel();  		
            	}
                onBackPressed();//finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateConnectionState(final int resourceId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mConnectionState.setText(resourceId);
            }
        });
    }

    private void displayData(String data) {
        if (data != null) {
            mDataField.setText(data);
        }
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }
    
    @Override  
	public boolean onKeyDown(int keyCode, KeyEvent event) {  
	    if(keyCode == KeyEvent.KEYCODE_BACK&& event.getAction() == KeyEvent.ACTION_DOWN){  
	    	onBackPressed();//finish();	   有异常    
            return true;  
	    }  
	    return super.onKeyDown(keyCode, event);  
	}   

}
