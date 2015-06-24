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
 * ��λ���߼��� |  ||
 * ��λ�룬�߼��� &  &&
 * Integer.parseInt(),ת���ɻ�����������int,ת��������߱����Ժͷ�����
 * Integer.valueOf(),ת����Integer�ࣨ���󣩣��߱��������Ժͷ�������String.valueOf()�����ý��һ�£����ΪString����
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
    private BluetoothLeService mBluetoothLeService;//�Զ����һ���̳���Service�ķ���
   
    private ArrayList<float[]> shujuList=new ArrayList<float[]>();
    private ArrayList<String> riqiList=new ArrayList<String>();
        
    private ArrayList<String> message_doc,msgTime,doc_xuewei,jingluo,shijian;
    private ArrayList<Integer> tiaoshu;
    private boolean mConnected = false;
    private BluetoothGattCharacteristic mCharacteristic,mCharacteristicNotify;
	private BluetoothGattService mnotyGattService;//�������ú����ɴ�С�Ķ���BluetoothGatt��
                                               //BluetoothGattService��BluetoothGattCharacteristic��	                                           
    //�����ǵķ��������ֵ    
	private static final UUID uuid = UUID
				.fromString(SampleGattAttributes.YJ_BLE_Service);  
	private static final UUID UUID_READ_WRITE = UUID
			.fromString(SampleGattAttributes.YJ_BLE_READ_WRITE);
	private static final UUID UUID_NOTIFY = UUID
			.fromString(SampleGattAttributes.YJ_BLE_NOTIFY);
	
	public static int RETRANSFER_NUM=-1;
	private static final int DELETE_ONE = 0;
	public static boolean flag=true;
//	public boolean CLICKABLE=true;	//"�ݲ�ͬ��" ��ť δע���ע���  �Ĳ�ͬ״̬
	public static int COUNT=0;//�������ֵ������ÿ7��һ�顣
	private	int i=1,dataNumber;//iΪ�ֻ��˷��ʹ���������dataNumberΪ�����Ƕ�һ�η��͵ľ����������
	private String dataJluo;
	private String JYM,SOURECE_TO_JYM;
	public StringBuilder bb=new StringBuilder();
	private MyApplication app;
	private Timer timer;
	private TimerTask timetask;

    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
    //�������ӽ���֮��Ļص�������
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
    // ���������豸�����ݣ�����ͨ������֪ͨ������á�
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            //ͨ��intent��õĲ�ͬaction�������ֹ㲥����˭����(ֻ��actionһ��,���ܽ���)��
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
            //���ַ�����Զ�ִ�лص�����onServicesDiscovered(),����һ��action=ACTION_GATT_SERVICES_DISCOVERED�Ĺ㲥���������ͬ��     
            else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the UI.                           	
            	mnotyGattService=mBluetoothLeService.getSupportedGattServices(uuid);
            	mCharacteristic=mnotyGattService.getCharacteristic(UUID_READ_WRITE);
            	mCharacteristicNotify=mnotyGattService.getCharacteristic(UUID_NOTIFY);
//            	mBluetoothLeService.setCharacteristicNotification(mCharacteristicNotify, true); 
            } 
            else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
            	Log.e("�㲥", "֪ͨ����");    
            	str=intent.getStringExtra(BluetoothLeService.EXTRA_DATA);            	
             	displayData(str);    //����ż����16�����ַ����ͣ������׶�ʧ      
             	
             	//���俪ʼЭ��ĵ�������
             	if(flag){
             		dealData();			//ȡ����һ������
             	}else{ 
     				Log.e("δ++֮ǰ��COUNTֵ"," "+COUNT);
     				COUNT++;			//����һ�������������ݵķ��ʹ�����ÿ7��Ϊһ����
 					testData[COUNT]=str;  					
 					if(COUNT==7){
 						StringBuilder sb=new StringBuilder();
 						for(int i=1;i<8;i++){
 							if(!(testData[i]==null))            				
 								sb.append(testData[i]);
 							else{
 								initConstant();
 								reTransfer();	//�ش��������ݶ�ʧ���ش������ݣ�
 							}    							
 						}
 						dataJluo=sb.toString();
 						bb.append(SOURECE_TO_JYM+dataJluo);//У���������Դ
 						Log.e(">>>>>>b 1>>>>>>", ""+bb);
 						if(JYM.equals(CRC.calcCrc16(bb.substring(0, bb.length())))){
 							shujuList.add(DataUtil.hex2DecStr(dataJluo));//ÿִ��һ�Σ������б�����һ��
 							riqiList.add(testData[0]);//ÿִ��һ�Σ������б�����һ��     
 							Toast toast1=Toast.makeText(DeviceControlActivity.this, "�ѽ������������� "+riqiList.size(), Toast.LENGTH_SHORT);
				     		toast1.show(); 
				     		if(riqiList.size()<dataNumber)
				     			beginTransfer();			     		
				     		else{
				     			Log.e(">>>>>>riqiList>>>>>>", ""+riqiList.size()+"   ���һ�� ���ڣ�"+riqiList.get(riqiList.size()-1));
				     			Log.e(">>>>>>shujuList>>>>>>", ""+shujuList.size()+"   ���һ�� ���ݣ�"+shujuList.get(riqiList.size()-1)[0]);
				     			Toast.makeText(DeviceControlActivity.this, "��������������"+riqiList.size(), Toast.LENGTH_SHORT).show();					     		
				     			initConstant();
				     			
				     			timetask = new TimerTask() {
									@Override
									public void run() {
										// TODO Auto-generated method stub
										try {
					    					mMessage();//ͬ��ҽ���ظ�
					    				} catch (UnsupportedEncodingException e) {
					    					// TODO Auto-generated catch block
					    					e.printStackTrace();
					    				}
									}
								};
								timer.schedule(timetask, 500);
								if(riqiList.size()>0){
									new Thread(runnableUploadData).start();//�ϴ�����	
								}else{
									Log.e(TAG, "û������");
								}   		
					     	}     			
 						}else{
 							Log.e(">>>>>>bb>>>>>>", ""+bb);
 							Log.e(">>>>>>JYM>>>>>>", ""+JYM);
 							initConstant();
 							shujuList.clear();
 						    riqiList.clear();
 							reTransfer();
	        				Toast.makeText(DeviceControlActivity.this, "�������ݳ�����", Toast.LENGTH_SHORT).show();
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
		return new AlertDialog.Builder(DeviceControlActivity.this).setTitle("����ͬ������...").setMessage("�Ե���").create();
	}

	private void initConstant(){
    	flag=true;
    	COUNT=0;
    	bb.delete(0, bb.length());//***********************************************   	
	}
    
    private void dealData(){
    	//ÿ�ζ�Ҫ����У����
    	Message msg=new Message();
    	if(str.substring(0, 2).equals("5C")){  //++++++++++++++++++++++++++++  		
    		switch(str.substring(2,6)){
    		case "C1D4"://5C C1 D4 00 00 C9 89 ++++++++++++++++++++++++++++++	
    			JYM=CRC.calcCrc16(str.substring(0, 10));
    			if(str.substring(10, 14).equals(JYM)){
    				Toast.makeText(getApplicationContext(), "�����Ǿܾ�����", Toast.LENGTH_SHORT).show();
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
    		case "C1D1": //5C C1 D1 00 07 A0 00 00 00 00 00 00 AD F8 δע��    
    					 //5C C1 D1 00 07 A1 00 00 00 00 00 00 6D E8 ��ע��	
    			JYM=CRC.calcCrc16(str.substring(0, 24));
    			if(str.substring(24,28).equals(JYM)){   		
    				switch(str.substring(10,12)){
    				case "A0":
    					if(app.getNAME().equals("")||app.getNAME()==null){//�ж�ʱ����Ҫ�����ı�־��ע��֮��Ҫ��գ�������681�У�����ȫ�ֱ���û��գ����ظ�������
    						//��תע��
//    						if(timetask!=null){
//    							timetask.cancel();
//    						}
    						msg.what=TestStart.DIALOGGO;
    	    				handler.sendMessage(msg);
    					}else{
    						try {//����������������������������������������0612 �޸ġ�
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
    					//����ͬ��ʱ������	                 
    					Toast.makeText(getApplicationContext(), "������ע��", Toast.LENGTH_SHORT).show();	  					
    					timetask = new TimerTask() {
    						@Override
    						public void run() {
    							tTime();
    						}
    					};
    					timer.schedule(timetask, 500);
    					break;
    				default:
    					Toast.makeText(getApplicationContext(), "��Ϣ���ʹ���", Toast.LENGTH_SHORT).show();
    					break;
    				}		
    			}else{
    				reTransfer();	             //�ش��������ݳ����ش������ݣ����ҷ�����ش�ʱ���ñ�־λ��
    			}
    			break;
    		case "C1D2": //5C C1 D2 00 01 0F BA 49 ,����Ϊ��˹>>>>>>>>>>>>
    			JYM=CRC.calcCrc16(str.substring(0, 12));
    			if(JYM.equals(str.substring(12, 16))){
    				Toast.makeText(DeviceControlActivity.this, "������������", Toast.LENGTH_SHORT).show();  			 				
    				timetask = new TimerTask() {
						@Override
						public void run() {
							tTime();
						}
					};
					timer.schedule(timetask, 6000);
    			}else{
    				reTransfer();	             //�ش��������ݳ����ش������ݣ����ҷ�����ش�ʱ���ñ�־λ(����)��
    			}
    			
    			break;
    		case "C2D2": //5C C2 D2 00 0C 0F 03 12 14 0E 04 00 00 00 00 00 00 81 9B 
    			JYM=CRC.calcCrc16(str.substring(0, 34));
    			if(JYM.equals(str.substring(34, 38))){
    				Toast toast3=Toast.makeText(DeviceControlActivity.this, "ʱ����ͬ��:"+DataUtil.hex2Dec(str.substring(10,22)), Toast.LENGTH_SHORT);
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
    				reTransfer();	             //�ش��������ݳ����ش������ݣ����ҷ�����ش�ʱ���ñ�־λ(����)��
    			}     		
    			break;
    		case "C3D3": //5C C3 D3 00 04 00 01 00 00 7B AD //5C C3 D3 00 04 00 02 00 00 7B 5D
    			JYM=CRC.calcCrc16(str.substring(0, 18));
    			if(JYM.equals(str.substring(18, 22))){
    				dataNumber=Integer.parseInt(str.substring(10, 14), 16);
    				Log.e("+_+_+_+_+_+_+_+_+ dataNumber��", ""+dataNumber);
    				Toast.makeText(DeviceControlActivity.this, "����������"+dataNumber, Toast.LENGTH_SHORT).show();
    				if(dataNumber>0){	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>����Ϊ0�������䣬ֱ��ͬ������
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
    				reTransfer();	             //�ش��������ݳ����ش������ݣ����ҷ�����ش�ʱ���ñ�־λ(����)��>>>>>>>>�޸ĺ���
    			}	    		
    			break;
    		case "C4D4"://5C C4 D4 00 7E 00 00 00 00 00 00 62 91(��һ��)5C C4 D4 00 7E 00 00 00 00 00 00 00 00 00 00 00 00 00 00(�ڶ���)//��һ����Ϣ�������������Э���У����
    			//�ȴ��Ͳɼ�ʱ��6���صĲɼ�ʱ�� +2���ص�У����    ,���Ͳɼ�����120�ֽڣ���6�η�����ϡ�һ����7��,���ߴη��͸����ݡ�
    			Log.e("=-=-=-=-=-=-=--=-=-=-=-= ��C4D4 :", str);
    			testData[0]=DataUtil.hexTime2DecStr(str.substring(10, 22));//ʱ���ڴ˽���
    			SOURECE_TO_JYM=str.substring(0, 22);
    			if(str.length()>24)//ʶ���һ����Ϣ����ȡУ����
    				JYM=str.substring(22, 26);
    			Toast toast5=Toast.makeText(DeviceControlActivity.this, "6�ֽڵĲɼ�ʱ��:"+testData[0], Toast.LENGTH_SHORT);
    			toast5.show();   
    			
    			mConfirm();
    			flag=false;	 
    			
    			break;
    		case "C5D5": //5C C5 D5 00 04 00 00 00 00  
    			RETRANSFER_NUM=-1;//�ָ���ֵ
    			Log.e("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= ��", "C5D5ִ�е��� "+str);
    			mBluetoothLeService.setCharacteristicNotification(mCharacteristicNotify, false); 									    			            		
    			Toast toast9=Toast.makeText(DeviceControlActivity.this, "�ѷ����ܵ��ֽ�����"+adress.length()/2, Toast.LENGTH_SHORT);
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
    				Log.e("+_+_+_+_+_+_+_+_+_+ serNumber�� ", serNumber);
    			}else{
    				reTransfer();	             //�ش��������ݳ����ش������ݣ����ҷ�����ش�ʱ���ñ�־λ(����)��
    			}     		
    			break;
    		case "C6D7":
    			JYM=CRC.calcCrc16(str.substring(0, 30));
    			if(JYM.equals(str.substring(30, 34))){
    				Toast.makeText(DeviceControlActivity.this, "�����Ǿܾ�ע��", Toast.LENGTH_SHORT).show();
    			}else{
    				reTransfer();	             //�ش��������ݳ����ش������ݣ����ҷ�����ش�ʱ���ñ�־λ(����)��
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
    					Log.e("����", String.valueOf(i));
    				}else{
    					byte[]mybyte=DataUtil.hexStringToByte(adress.substring(40*num, adress.length()));
    					if(mBluetoothLeService.writeCharacteristic(mCharacteristic, mybyte)){//+++++++++++++++++++++++++++++++++++++++++
    						Toast.makeText(getApplicationContext(), "�����������", Toast.LENGTH_SHORT).show();
    						i=1;//��i�ָ���ֵ�����ٽ��չ㲥++++++++++++++++++++++++++++++++++++++
    					}else{
    						Toast.makeText(getApplicationContext(), "����д��������", Toast.LENGTH_SHORT).show();   						
    					}				
    				}              			
    			}else{
    				reTransfer();	             //�ش��������ݳ���Ҫ�������ش������ݣ����ҷ�����ش�ʱ���ñ�־λ(����)��
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
    				if(RETRANSFER_NUM!=-1&&RETRANSFER_NUM!=6){ //�ж�Э���Ƿ�ִ�й�,����6ʱ��ע�����г����ˣ�û�ö�ʱ����
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
    		Toast.makeText(getApplicationContext(), "��־λ�쳣", Toast.LENGTH_SHORT).show();
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
				Toast.makeText(getApplicationContext(), "������������",Toast.LENGTH_SHORT).show();
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
//							app.setNAME("");//��������������ظ�������
							Log.e("+_+_+_+_+_+_+_+_+ logout��", ""+flag);
						}else{		
							break;
						}
					}						
					break;			
				default	:
					break;	
			}
		}							
	
    //�̷߳���ֵ����    
    private Handler handler = new Handler() {  
    	@SuppressWarnings("deprecation")
		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg){
    		switch(msg.what){
    		case TestStart.SHOW:
    			Toast.makeText(getApplicationContext(), "���ھ���������Ȩ����", Toast.LENGTH_SHORT).show();
				break;
    		case TestStart.DISMISS:
    			Toast.makeText(getApplicationContext(), "������������", Toast.LENGTH_SHORT).show();
    			break;		
    		case TestStart.COMPLETED:
        		setCLICKABLE(true);
    			new Thread(runnableDownloadReply).start();		      		       		 			
        		break;
        	case TestStart.UNCOMPLETED:
        		Toast.makeText(DeviceControlActivity.this, "��������δע��,����ͬ�����ݽ���ע��", Toast.LENGTH_SHORT).show();
        		setCLICKABLE(false);
        		break;
        	case TestStart.FAIL:
        		Toast.makeText(DeviceControlActivity.this, "����ֵ��������������鿴����", Toast.LENGTH_SHORT).show();
        		break;
        	case TestStart.UNFINISH:
        		Toast.makeText(DeviceControlActivity.this, "statusCode�쳣", Toast.LENGTH_SHORT).show();
        		break;
        	case TestStart.YES:
        		Toast.makeText(DeviceControlActivity.this, "�������سɹ�", Toast.LENGTH_SHORT).show();
        		break;
        	case TestStart.NO:        		
        		Toast.makeText(DeviceControlActivity.this, "��������ʧ��", Toast.LENGTH_SHORT).show();     		        		
        		break;
        	case TestStart.KONG:    
        		Toast.makeText(DeviceControlActivity.this, "����ҽ���ظ��;��紦��", Toast.LENGTH_SHORT).show();     		        		
        		break;
        	case TestStart.ACHIVE:      		
        		Toast.makeText(DeviceControlActivity.this, "�����ϴ��ɹ�", Toast.LENGTH_SHORT).show();     		
        		initConstant();//��ʼ������
        		shujuList.clear();
        		riqiList.clear();
        		RETRANSFER_NUM=-1;
    			break;
        	case TestStart.LOSE:
        		Toast.makeText(DeviceControlActivity.this, "�����ϴ�ʧ��", Toast.LENGTH_LONG).show();     		
        		break;  
        	case TestStart.LOGOUT:
        		setCLICKABLE(false);
        		app.setNAME("");//��������������ظ�������
        		Toast.makeText(DeviceControlActivity.this, "ע�����", Toast.LENGTH_LONG).show();     				
        		break;
        	case TestStart.NOLOGOUT:
        		Toast.makeText(DeviceControlActivity.this, "ע��ʧ��", Toast.LENGTH_LONG).show();     		
        		break;
//        	case TestStart.MESSAGEFINSH://��ҽ���ظ���������������ͬ��
//        		RETRANSFER_NUM=-1;//�ָ���ֵ
//        		removeDialog(1);
//        		new Thread(synch).start();	
//        		break;
        	case TestStart.NOMESSAGE://��ҽ���ظ������ô��䣬����ͬ��
        		RETRANSFER_NUM=-1;//�ָ���ֵ
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
				Toast.makeText(getApplicationContext(), "����δע��", Toast.LENGTH_SHORT).show();
				startActivity(new Intent().setClass(getApplication(), LogIn.class));				
				finish();	
        		break;
        	case TestStart.SYNCHFINSH:
        		if(timetask!=null){
        			timetask.cancel();
        		}
        		removeDialog(1);
        		Toast.makeText(getApplicationContext(), "ͬ���ɹ�", Toast.LENGTH_SHORT).show();
        		startActivity(new Intent(DeviceControlActivity.this,NormalUse.class));				
    			finish();
        		break;       	 
        	case TestStart.SYNCHFAIL:
        		Toast.makeText(getApplicationContext(), "ͬ��ʧ��", Toast.LENGTH_SHORT).show();
        		break;       	 
        	default:
    			break;
    		}
    	}
   }; 
    
   //ע���߳�   
	Runnable logout = new Runnable() {
		private String url = HttpUrl.IP+HttpUrl.DIR+"logout";
		private String back;
		private JSONObject jsonLogout;
		private int statusCode;
		public void run() {
			Map<String, String> map = new HashMap<String, String>();
			map.put("username", app.getXULIEHAO()); // ����ʱ��ע���޸ġ�
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
	
   	//��֤�Ƿ�ע��
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
 					app.setNAME(subjson.getString("true_name"));//��ʼ��ڶ���json
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
  	 	
   //�ϴ���������
   Runnable runnableUploadData = new Runnable(){
		private String url =HttpUrl.IP+HttpUrl.DIR+"upload_data";
		private String JsonString;
		private int statusCode;
		JSONObject Json;
		public void run()//��дThread���е�run(),�˷��������̵߳Ĵ�����UI�߳��е��ã��������̡߳�
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
  
  
  private String[]Jmname={"����", "�ξ�", "�ξ�", "�󳦾�", "θ��", "Ƣ��", "�ľ�", "С����",
			"���׾�", "����", "�İ���", "������", "�������֣�", "�������㣩", "�������֣�", "�������㣩",
			"�������֣�", "�����������㣩", "�����֣�", "�����㣩"};
  
  //ƴװ����
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
				Log.e(TAG, "��"+j+"�����ݳ��Ȳ���120");
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");// json�ַ����������
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
		
		Log.e("��", String.valueOf(send[0]));//byteֵ��������ʵ��ֵ��
		return send;
	}
	
	private void beginTransfer(){
		RETRANSFER_NUM=4;
		initConstant();
		byte[]transmit=mTransmit();				
		if(mBluetoothLeService.writeCharacteristic(mCharacteristic, transmit)){
			mBluetoothLeService.setCharacteristicNotification(mCharacteristicNotify, true); 
		}else{
			Log.e(TAG, "���俪ʼ��д��������");
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
			Log.e(TAG, "ע�᣺д��������");
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
		//�����ִ�ע��ʱ�ͻ�ȡ			
//		s.append(DataUtil.GBK2eight("��˹"));//����ת��Ϊʮ�������ַ���ʮ�������ַ�ת��Ϊ���֣���Ҫ�޸�
		s.append(DataUtil.GBK2eight(app.getNAME()));//����ת��Ϊʮ�������ַ���ʮ�������ַ�ת��Ϊ���֣���Ҫ�޸�
		s.append("A1000000");
		s.append(CRC.calcCrc16(s.toString()));

		adress=s.toString();
		byte[]send=DataUtil.hexStringToByte(adress);
		
		if(mBluetoothLeService.writeCharacteristic(mCharacteristic, send)){
			mBluetoothLeService.setCharacteristicNotification(mCharacteristicNotify, true); 
			retu=true;
		}else{
			Log.e(TAG, "������д��������");
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
			Log.e(TAG, "ʱ�䣺д��������");
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
			Log.e(TAG, "���ݣ�д��������");
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
			s.append(toOneByteHex(message_doc.size()));//��Ϣ������1�ֽ�,tiaoshu[]��message_doc[]�ĳ��ȱ���һ��
			for(int i=0;i<msgTime.size();i++){
				s.append(decstr2HexStr(msgTime.get(i)));//ҽ���ظ�ʱ�䣬6�ֽ�>>>>>>>>>>>>>>>>>>>>>>>�����ƴ���
				Log.e(TAG, "----------------0000:"+s);
				s.append(toTwoByteHex(DataUtil.getCountStringToGBK(message_doc.get(i))));//ҽ���ظ����ȣ�ռ2�ֽ�+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=
				Log.e(TAG, "----------------1111:"+s);
				s.append(DataUtil.stringToGBK(message_doc.get(i)));//ҽ���ظ����ݣ����Ȳ�ȷ��>>>>>>>>�����Ĵ���GBK��
				Log.e(TAG, "----------------2222:"+s);
//				s.append(DataUtil.toTwoHex(tiaoshu.get(i)));//��������,1�ֽ�
				
				if(tiaoshu.get(i)>0){
					s.append(DataUtil.toTwoHex(tiaoshu.get(i)));//��������,1�ֽ�
					Log.e(TAG, "----------------3333:"+s);
					for(int j=i;j<tiaoshu.get(i)+i;j++){
						k++;//k��ѭ��ִ�еĴ�������ʼֵ��Ϊ-1
						s.append(DataUtil.toTHex(jingluo.get(k)));//��i��������������,12���ֽ�>>>>>>>>>>>�����Ĵ���GBK��
						Log.e(TAG, "----------------4444:"+s);
						s.append(decstr2HexStr(shijian.get(k)));//����ʱ���ʱ����3���ֽ�				
						Log.e(TAG, "----------------5555:"+s);
					}									
				}else{
					s.append(DataUtil.toTwoHex(tiaoshu.get(i)));//��������,1�ֽ�
					Log.e(TAG, "----------------------------------tiaoshu.get(i):  "+tiaoshu.get(i));					
				}
				
			}		
			Log.e("�޳�", s.length()+" ");
			s.insert(6, toTwoByteHex((s.length()-6)/2));//��Ϣ�峤�ȣ�2�ֽڣ�ֵ��ȷ��.length������++++++++++++++++++++	
			//У����
			s.append(CRC.calcCrc16(s.toString()));//2�ֽڵ�У����
			
			adress=s.toString();
			Log.e("�޳�", adress.length()+"  "+adress);
			
			if(adress.length()>40)
				message=DataUtil.hexStringToByte(adress.substring(0, 40));				
			else{
				message=DataUtil.hexStringToByte(adress);
			}
			
			if(mBluetoothLeService.writeCharacteristic(mCharacteristic, message)){
				mBluetoothLeService.setCharacteristicNotification(mCharacteristicNotify, true); 
				retu=true;
			}else{
				Log.e(TAG, "������д��������");
			}	
		}else{
			Log.e(TAG, "����ҽ���ظ��;��紦��");
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
		s.append("0004");//��Ϣ�峤��
		s.append("00000000");
		s.append(CRC.calcCrc16(s.toString()));

		adress=s.toString();
		byte[]send=DataUtil.hexStringToByte(adress);	
		if(mBluetoothLeService.writeCharacteristic(mCharacteristic, send)){
			mBluetoothLeService.setCharacteristicNotification(mCharacteristicNotify, true); 
			retu=true;
		}else{
			Log.e(TAG, "ע����д��������");
		}			
		return retu;	
	}
	
	private boolean mConfirm() {
		// TODO Auto-generated method stub
		boolean retu=false;
		StringBuffer s=new StringBuffer();;
		s.append("5C");
		s.append("A7B7");
		s.append("0000");//��Ϣ�峤��
		//У��λ
		s.append(CRC.calcCrc16(s.toString()));
		
		adress=s.toString();
		byte[]send=DataUtil.hexStringToByte(adress);	
		if(mBluetoothLeService.writeCharacteristic(mCharacteristic, send)){
			retu=true;
		}else{
			Log.e(TAG, "ȷ�ϣ�д��������");
		}					
		return retu;	
	}
	
	private boolean reTransfer() {
		// TODO Auto-generated method stub	
		boolean retu=false;
		StringBuffer s=new StringBuffer();;
		s.append("5C");
		s.append("A8B8");
		s.append("0000");//��Ϣ�峤��
		//У��λ
		s.append(CRC.calcCrc16(s.toString()));

		adress=s.toString();
		byte[]send=DataUtil.hexStringToByte(adress);	
		
		if(mBluetoothLeService.writeCharacteristic(mCharacteristic, send)){
			mBluetoothLeService.setCharacteristicNotification(mCharacteristicNotify, true); 
			retu=true;
		}else{
			Log.e(TAG, "�ش���д��������");
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
			s.append("0000");//��Ϣ�峤��2�ֽ�
			//У��λ
			s.append(CRC.calcCrc16(s.toString()));
			
			adress=s.toString();
			byte[]send=DataUtil.hexStringToByte(adress);	
			
			if(mBluetoothLeService.writeCharacteristic(mCharacteristic, send)){
				mBluetoothLeService.setCharacteristicNotification(mCharacteristicNotify, true); 
				retu=true;
			}else{
				Log.e(TAG, "�Ƿ����ӣ�д��������");
			}					
		}else{
			return retu;
		}
		return retu;	
	}
	
    private String getDate() {   
//    	HH�����ص���24Сʱ�Ƶ�ʱ�䣬hh�����ص���12Сʱ�Ƶ�ʱ��    ������ 
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
        
//    	��ȡϵͳʱ���ʱ��
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
				statusCode = jsonReply.getInt("statusCode");// Toast�ݴ���ʾ
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
    
    // ��������ʮ������     
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
			map.put("ifdownload", "1");//1����δ���ع���ҽ���ظ�;			
			try {
				String retStrDoctorReply = NetTool.sendGetRequest(url, map,"utf-8");
				jsonReply = new JSONObject(retStrDoctorReply);
				statusCode = jsonReply.getInt("statusCode");// Toast�ݴ���ʾ
				if(statusCode == -1){
					array = jsonReply.getJSONArray("result");// ��һ��
//-------------------------------------------------------------------
					if(array!=null&&array.length()>0){
						message_doc=getStrarray(array,"doc_back");//ֻҪ�лظ����϶���Ϊ��
						msgTime=getStrarray(array,"doc_time");//ֻҪ�лظ����϶���Ϊ��
						doc_xuewei=getStrarray(array,"doc_xuewei");				
						//  �ξ�/3:00/40����,�󳦾�/3:00/50����,�ξ�/1:00/20����,�ξ�/3:00/40����
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
				Log.e(TAG,"========retStrDoctorReplyֵ��������");
			}
			handler.sendMessage(msg);
		}
	};		
	
	//ҽ���ظ�ʱ��	
	private String transferDateTime(String time){
		String ttime="";
		ttime=time.replace("-", "").replace(":", "").replace(" ", "").trim().substring(2);
		return ttime;
	}
	
	//����ʱ��     
	private String transferMinute(String time){
		String ttime="";
		ttime=time.replace("����", "");
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
	
	//����ʱ��    
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
				strarray[i]=(array.getJSONObject(i).getString(key));//Ҫ��ҽ���ظ�����Ϊ��
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
 	

    //ע���߼����ӷ�������ȡ�ľ���Ѩλ�͵���ʱ�䣬������ͬ˳��������jingluo[]�� shijian[]���������С��������������tiaoshu[]������
//    private String[] message_doc={"���ˮ���ˮ���ˮ���ˮ���ˮ" 
//    		,"��","�Ȳ�","˯��","����","��˯"};
//    private String[] msgTime={"150901180110","150902180220","150903180330","150904184040","150905180550","1509061850"};
//    private String[] jingluo={"�ξ�","�ľ�","�ξ�","����","�󳦾�","θ��","Ƣ��","С����","�İ���","������"};
//    private String[] shijian={"101010","101020","101030","101040","101050","200000","101030","101040","101050","200000"};
//    private int[]tiaoshu={1,2,1,3,2,1};
    
    private String toOneByteHex(int i){
    	String st;
    	st=Integer.toHexString(i).toUpperCase();//����ת16�����ַ���
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
    
    //����ת16����
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
			 Toast.makeText(DeviceControlActivity.this, "ת16���Ƴ�����", Toast.LENGTH_SHORT).show();
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
	    	onBackPressed();//finish();	   ���쳣    
            return true;  
	    }  
	    return super.onKeyDown(keyCode, event);  
	}   

}
