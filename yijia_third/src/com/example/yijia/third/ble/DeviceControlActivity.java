package com.example.yijia.third.ble;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yijia.third.tool.CRC;
import com.example.yijia.third.tool.DataUtils;
import com.example.yijia.third.tool.LogUtils;
import com.example.yijia_third.R;

/**
 * @author ��
 * @date ����ʱ�䣺2015/10/24 ����3:06:59
 * @version 1.0
 * @parameter
 * @since
 * @return
 * 
 */
public class DeviceControlActivity extends Activity {
	private BluetoothLeService mBluetoothLeService;// �Զ����һ���̳���Service�ķ���
	private String mDeviceAddress, adress, serialNumber;
	private boolean mConnected = false;
	private boolean mPress = false;// �ж�ʱ���Ƿ��������Ͽ�����,true��ʾ����(������160��,1494��)
	private TextView mConnectionState, mDataField;
	private ArrayList<float[]> shujuList = new ArrayList<float[]>();
	private ArrayList<String> riqiList = new ArrayList<String>();
	public static int RETRANSFER_NUM = -1;
	private Timer timer;
	private TimerTask timetask;
	public static boolean flag = true;
	public int COUNT = 1;// �������ֵ������ÿ8��һ�顣
	public StringBuilder bb = new StringBuilder();
	private BluetoothGattCharacteristic mCharacteristic, mCharacteristicNotify;
	private BluetoothGattService mGattService, myNotifyService;
	private ArrayList<String> message_doc,msgTime;//��������Դ
	public static String YJ_BLE_SERVICE = "0000FFE5-0000-1000-8000-00805F9B34FB";// д��
	public static String YJ_NOTIFY_SERVICE = "0000FFE0-0000-1000-8000-00805F9B34FB";// ֪ͨ����
	public static String YJ_BLE_READ_WRITE = "0000FFE9-0000-1000-8000-00805F9B34FB";
	public static String YJ_BLE_NOTIFY = "0000FFE4-0000-1000-8000-00805F9B34FB";// ֪ͨ

	private static final UUID UUID_SERVICE = UUID.fromString(YJ_BLE_SERVICE);
	private static final UUID NOTIFY_SERVICE = UUID.fromString(YJ_NOTIFY_SERVICE);
	private static final UUID UUID_READ_WRITE = UUID.fromString(YJ_BLE_READ_WRITE);
	private static final UUID UUID_NOTIFY = UUID.fromString(YJ_BLE_NOTIFY);
	private String dataJluo;
	private String JYM,SOURECE_TO_JYM;
	private String str;//���յ�����
	private	int i=1,dataNumber;//iΪ�ֻ��˷��ʹ���������dataNumberΪ�����Ƕ�һ�η��͵ľ����������
	private String[] testData=new String[8];
	
	private final static String TAG = DeviceControlActivity.class.getSimpleName();

	// Code to manage Service lifecycle.
	private final ServiceConnection mServiceConnection = new ServiceConnection() {
		// �������ӽ���֮��Ļص�������
		@Override
		public void onServiceConnected(ComponentName componentName,
				IBinder service) {
			mBluetoothLeService = ((BluetoothLeService.LocalBinder) service)
					.getService();
			if (!mBluetoothLeService.initialize()) {
				Log.e(TAG, "Unable to initialize Bluetooth");
				finish();
			}
			// Automatically connects to the device upon successful start-up
			// initialization.
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
			// ͨ��intent��õĲ�ͬaction�������ֹ㲥����˭����(ֻ��actionһ��,���ܽ���)��
			if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
				mConnected = true;
				updateConnectionState(R.string.connected);
				invalidateOptionsMenu();
			} else if (BluetoothLeService.ACTION_GATT_DISCONNECTED
					.equals(action)) {
				if (mPress) {// �����Ͽ�
					Log.e("mPress", "" + mPress);
					mConnected = false;
					updateConnectionState(R.string.disconnected);
					invalidateOptionsMenu();
					clearUI();
				} else {// ��һ�ν����Activityʱ��������δ���ӣ����Զ�����
					if (flag) {
						mConnected = true;
						updateConnectionState(R.string.connected);
						invalidateOptionsMenu();
					} else {
						Toast.makeText(getApplicationContext(), "���������Ƿ����",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
			// ���ַ�����Զ�ִ�лص�����onServicesDiscovered(),����һ��action=ACTION_GATT_SERVICES_DISCOVERED�Ĺ㲥���������ͬ��
			else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED
					.equals(action)) {
				// Show all the supported services and characteristics on the
				// UI.
				mGattService = mBluetoothLeService
						.getSupportedGattServices(UUID_SERVICE);
				myNotifyService = mBluetoothLeService
						.getSupportedGattServices(NOTIFY_SERVICE);

				mCharacteristicNotify = myNotifyService
						.getCharacteristic(UUID_NOTIFY);
				mCharacteristic = mGattService
						.getCharacteristic(UUID_READ_WRITE);

				if (mCharacteristicNotify == null) {
					Toast.makeText(getApplicationContext(), "δ������Ч����ֵ    ֪ͨ",
							Toast.LENGTH_SHORT).show();
				}

				if (mCharacteristic == null) {
					Toast.makeText(getApplicationContext(), "δ������Ч����ֵ   д��",
							Toast.LENGTH_SHORT).show();
				}
			} else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
				str = intent.getStringExtra(BluetoothLeService.EXTRA_DATA);
				displayData(str); // ����ż����16�����ַ����ͣ������׶�ʧ

				// ���俪ʼЭ��ĵ�������
				if (flag) {
//					dealData(); // ȡ����һ������
				} else {
					testData[COUNT] = str;
					COUNT++;
					if (COUNT == 8) {
						StringBuilder sb = new StringBuilder();
						for (int i = 1; i < 8; i++) {
							if (!(testData[i] == null)) {
								sb.append(testData[i]);
							} else {
								initConstant();								
								reTransfer(); // �ش��������ݶ�ʧ���ش������ݣ�
							}
						}
						dataJluo = sb.toString();
						bb.append(SOURECE_TO_JYM + dataJluo);// У���������Դ
						Log.e(">>>>>>b 1>>>>>>", "" + bb);
						if (JYM.equals(CRC.calcCrc16(bb.substring(0,bb.length())))) {
							shujuList.add(DataUtils.hex2DecStr(dataJluo));// ÿִ��һ�Σ������б�����һ��
							riqiList.add(testData[0]);// ÿִ��һ�Σ������б�����һ��
							Toast.makeText(DeviceControlActivity.this,"�ѽ������������� " + riqiList.size(),Toast.LENGTH_SHORT).show();
							if (riqiList.size() < dataNumber)
								beginTransfer();
							else {
								Toast.makeText(DeviceControlActivity.this,"��������������" + riqiList.size(),
										Toast.LENGTH_SHORT).show();
								initConstant();

								timetask = new TimerTask() {
									@Override
									public void run() {
										// TODO Auto-generated method stub
										try {
											mMessage();// ͬ��ҽ���ظ�
										} catch (UnsupportedEncodingException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								};
								timer.schedule(timetask, 500);
								if (riqiList.size() > 0) {
//									new Thread(runnableUploadData).start();// �ϴ�����
								} else {
									Log.e(TAG, "û������");
								}
							}
						} else {
							initConstant();
							shujuList.clear();
							riqiList.clear();
							reTransfer();
							Toast.makeText(DeviceControlActivity.this,"�������ݳ�����", Toast.LENGTH_SHORT).show();
						}
						initConstant();
					} else {
						mConfirm();
					}
				}
			}
		}
	};
	
	// ҽ���ظ�ʱ��
	private String transferDateTime(String time) {
		String ttime = "";
		ttime = time.replace("-", "").replace(":", "").replace(" ", "").trim().substring(2);
		return ttime;
	}
	
	private boolean mMessage() throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		boolean retu=false;		
//		Message msg=new Message();
		if(message_doc.size()>0){				
			RETRANSFER_NUM=5;
			byte[]message;
			StringBuffer s=new StringBuffer();
			int k=-1;
			s.append("5C");//1
			s.append("A5B5");//2	
			s.append(DataUtils.toOneByteHex(message_doc.size()));//��Ϣ������1�ֽ�,tiaoshu[]��message_doc[]�ĳ��ȱ���һ��
			for(int i=0;i<msgTime.size();i++){			
				s.append(DataUtils.decstr2HexStr(transferDateTime(msgTime.get(i))));//ҽ���ظ�ʱ�䣬6�ֽ�>>>>>>>>>>>>>>>>>>>>>>>�����ƴ���
				s.append(DataUtils.toTwoByteHex(DataUtils.getCountStringToGBK(message_doc.get(i))));//ҽ���ظ����ȣ�ռ2�ֽ�+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=
				s.append(DataUtils.stringToGBK(message_doc.get(i)));//ҽ���ظ����ݣ����Ȳ�ȷ��>>>>>>>>�����Ĵ���GBK��
			}		
			s.insert(6, DataUtils.toTwoByteHex((s.length()-6)/2));//��Ϣ�峤�ȣ�2�ֽڣ�ֵ��ȷ��.length������++++++++++++++++++++	
			//У����
			s.append(CRC.calcCrc16(s.toString()));//2�ֽڵ�У����
			adress=s.toString();												
			if(adress.length()>40){
				message=DataUtils.hexStringToByte(adress.substring(0, 40));	//������ʼ����Ĺ���							
				Log.e(TAG+TAG, "message.size():  "+message.length+"     adress.substring(0, 40)�� "+adress.substring(0, 40));
			}else{
				message=DataUtils.hexStringToByte(adress);
			}
			if(mBluetoothLeService.writeCharacteristic(mCharacteristic, message)){
				mBluetoothLeService.setCharacteristicNotification(mCharacteristicNotify, true); 
				retu=true;
			}else{
				LogUtils.getInstance().println(TAG, "������д��������");
			}	
		}else{
			LogUtils.getInstance().println(TAG, "����ҽ���ظ��;��紦��");
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
		byte[]send=DataUtils.hexStringToByte(adress);	
		if(mBluetoothLeService.writeCharacteristic(mCharacteristic, send)){
			retu=true;
		}else{
			Log.e(TAG, "ȷ�ϣ�д��������");
		}					
		return retu;	
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
		byte[]send=DataUtils.hexStringToByte(adress);	
		
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
		byte[]send=DataUtils.hexStringToByte(adress);	
		
		if(mBluetoothLeService.writeCharacteristic(mCharacteristic, send)){
			mBluetoothLeService.setCharacteristicNotification(mCharacteristicNotify, true); 
			retu=true;
		}else{
			Log.e(TAG, "�ش���д��������");
		}			
		return retu;	
	}
	
	private void initConstant() {
		flag = true;
		COUNT = 1;
		bb.delete(0, bb.length());
	}

	private void clearUI() {
		mDataField.setText(R.string.no_data);
	}

	private void updateConnectionState(final int resourceId) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mConnectionState.setText(resourceId);
				mDataField.setText(mDeviceAddress);
			}
		});
	}
	
	private static IntentFilter makeGattUpdateIntentFilter() {
		final IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
		intentFilter
				.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
		intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
		return intentFilter;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			onBackPressed();// finish(); ���쳣
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void displayData(String data) {
		if (data != null) {
			mDataField.setText(data);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_connect:
			mBluetoothLeService.connect(mDeviceAddress);
			mPress = true;
			return true;
		case R.id.menu_disconnect:
			if (RETRANSFER_NUM != -1 && RETRANSFER_NUM != 6) {
				timetask.cancel();
			}
			mBluetoothLeService.disconnect();
			mPress = true;
			return true;
		case android.R.id.home:
			if (timetask != null) {
				timetask.cancel();
			}
			onBackPressed();// finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	   
}
