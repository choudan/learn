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
 * @author 丑旦
 * @date 创建时间：2015/10/24 下午3:06:59
 * @version 1.0
 * @parameter
 * @since
 * @return
 * 
 */
public class DeviceControlActivity extends Activity {
	private BluetoothLeService mBluetoothLeService;// 自定义的一个继承自Service的服务
	private String mDeviceAddress, adress, serialNumber;
	private boolean mConnected = false;
	private boolean mPress = false;// 判断时候是否是主动断开蓝牙,true表示主动(见代码160行,1494行)
	private TextView mConnectionState, mDataField;
	private ArrayList<float[]> shujuList = new ArrayList<float[]>();
	private ArrayList<String> riqiList = new ArrayList<String>();
	public static int RETRANSFER_NUM = -1;
	private Timer timer;
	private TimerTask timetask;
	public static boolean flag = true;
	public int COUNT = 1;// 经络采样值计数，每8条一组。
	public StringBuilder bb = new StringBuilder();
	private BluetoothGattCharacteristic mCharacteristic, mCharacteristicNotify;
	private BluetoothGattService mGattService, myNotifyService;
	private ArrayList<String> message_doc,msgTime;//网络数据源
	public static String YJ_BLE_SERVICE = "0000FFE5-0000-1000-8000-00805F9B34FB";// 写入
	public static String YJ_NOTIFY_SERVICE = "0000FFE0-0000-1000-8000-00805F9B34FB";// 通知服务
	public static String YJ_BLE_READ_WRITE = "0000FFE9-0000-1000-8000-00805F9B34FB";
	public static String YJ_BLE_NOTIFY = "0000FFE4-0000-1000-8000-00805F9B34FB";// 通知

	private static final UUID UUID_SERVICE = UUID.fromString(YJ_BLE_SERVICE);
	private static final UUID NOTIFY_SERVICE = UUID.fromString(YJ_NOTIFY_SERVICE);
	private static final UUID UUID_READ_WRITE = UUID.fromString(YJ_BLE_READ_WRITE);
	private static final UUID UUID_NOTIFY = UUID.fromString(YJ_BLE_NOTIFY);
	private String dataJluo;
	private String JYM,SOURECE_TO_JYM;
	private String str;//接收的数据
	private	int i=1,dataNumber;//i为手机端发送处方计数。dataNumber为经络仪端一次发送的经络采样条数
	private String[] testData=new String[8];
	
	private final static String TAG = DeviceControlActivity.class.getSimpleName();

	// Code to manage Service lifecycle.
	private final ServiceConnection mServiceConnection = new ServiceConnection() {
		// 服务连接建立之后的回调函数。
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
	// 接受来自设备的数据，可以通过读或通知操作获得。
	private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();
			// 通过intent获得的不同action，来区分广播该由谁接收(只有action一致,才能接收)。
			if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
				mConnected = true;
				updateConnectionState(R.string.connected);
				invalidateOptionsMenu();
			} else if (BluetoothLeService.ACTION_GATT_DISCONNECTED
					.equals(action)) {
				if (mPress) {// 主动断开
					Log.e("mPress", "" + mPress);
					mConnected = false;
					updateConnectionState(R.string.disconnected);
					invalidateOptionsMenu();
					clearUI();
				} else {// 第一次进入该Activity时，若蓝牙未连接，则自动连接
					if (flag) {
						mConnected = true;
						updateConnectionState(R.string.connected);
						invalidateOptionsMenu();
					} else {
						Toast.makeText(getApplicationContext(), "请检查蓝牙是否可用",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
			// 发现服务后，自动执行回调方法onServicesDiscovered(),发送一个action=ACTION_GATT_SERVICES_DISCOVERED的广播，其他情况同理
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
					Toast.makeText(getApplicationContext(), "未发现有效特征值    通知",
							Toast.LENGTH_SHORT).show();
				}

				if (mCharacteristic == null) {
					Toast.makeText(getApplicationContext(), "未发现有效特征值   写入",
							Toast.LENGTH_SHORT).show();
				}
			} else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
				str = intent.getStringExtra(BluetoothLeService.EXTRA_DATA);
				displayData(str); // 必须偶数个16进制字符发送，否则易丢失

				// 传输开始协议的单独处理
				if (flag) {
//					dealData(); // 取出第一个数据
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
								reTransfer(); // 重传（有数据丢失，重传该数据）
							}
						}
						dataJluo = sb.toString();
						bb.append(SOURECE_TO_JYM + dataJluo);// 校验码的数据源
						Log.e(">>>>>>b 1>>>>>>", "" + bb);
						if (JYM.equals(CRC.calcCrc16(bb.substring(0,bb.length())))) {
							shujuList.add(DataUtils.hex2DecStr(dataJluo));// 每执行一次，数据列表增加一条
							riqiList.add(testData[0]);// 每执行一次，日期列表增加一条
							Toast.makeText(DeviceControlActivity.this,"已接收数据条数： " + riqiList.size(),Toast.LENGTH_SHORT).show();
							if (riqiList.size() < dataNumber)
								beginTransfer();
							else {
								Toast.makeText(DeviceControlActivity.this,"接收数据条数：" + riqiList.size(),
										Toast.LENGTH_SHORT).show();
								initConstant();

								timetask = new TimerTask() {
									@Override
									public void run() {
										// TODO Auto-generated method stub
										try {
											mMessage();// 同步医生回复
										} catch (UnsupportedEncodingException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								};
								timer.schedule(timetask, 500);
								if (riqiList.size() > 0) {
//									new Thread(runnableUploadData).start();// 上传数据
								} else {
									Log.e(TAG, "没数据呃");
								}
							}
						} else {
							initConstant();
							shujuList.clear();
							riqiList.clear();
							reTransfer();
							Toast.makeText(DeviceControlActivity.this,"这条数据出错了", Toast.LENGTH_SHORT).show();
						}
						initConstant();
					} else {
						mConfirm();
					}
				}
			}
		}
	};
	
	// 医生回复时间
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
			s.append(DataUtils.toOneByteHex(message_doc.size()));//消息条数，1字节,tiaoshu[]与message_doc[]的长度必须一样
			for(int i=0;i<msgTime.size();i++){			
				s.append(DataUtils.decstr2HexStr(transferDateTime(msgTime.get(i))));//医生回复时间，6字节>>>>>>>>>>>>>>>>>>>>>>>（进制处理）
				s.append(DataUtils.toTwoByteHex(DataUtils.getCountStringToGBK(message_doc.get(i))));//医生回复长度，占2字节+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=
				s.append(DataUtils.stringToGBK(message_doc.get(i)));//医生回复内容，长度不确定>>>>>>>>（中文处理GBK）
			}		
			s.insert(6, DataUtils.toTwoByteHex((s.length()-6)/2));//消息体长度，2字节，值不确定.length有问题++++++++++++++++++++	
			//校验码
			s.append(CRC.calcCrc16(s.toString()));//2字节的校验码
			adress=s.toString();												
			if(adress.length()>40){
				message=DataUtils.hexStringToByte(adress.substring(0, 40));	//触发开始传输的功能							
				Log.e(TAG+TAG, "message.size():  "+message.length+"     adress.substring(0, 40)： "+adress.substring(0, 40));
			}else{
				message=DataUtils.hexStringToByte(adress);
			}
			if(mBluetoothLeService.writeCharacteristic(mCharacteristic, message)){
				mBluetoothLeService.setCharacteristicNotification(mCharacteristicNotify, true); 
				retu=true;
			}else{
				LogUtils.getInstance().println(TAG, "处方：写操作错误");
			}	
		}else{
			LogUtils.getInstance().println(TAG, "暂无医生回复和经络处方");
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
		byte[]send=DataUtils.hexStringToByte(adress);	
		if(mBluetoothLeService.writeCharacteristic(mCharacteristic, send)){
			retu=true;
		}else{
			Log.e(TAG, "确认：写操作错误");
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
		byte[]send=DataUtils.hexStringToByte(adress);	
		
		if(mBluetoothLeService.writeCharacteristic(mCharacteristic, send)){
			mBluetoothLeService.setCharacteristicNotification(mCharacteristicNotify, true); 
			retu=true;
		}else{
			Log.e(TAG, "重传：写操作错误");
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
			onBackPressed();// finish(); 有异常
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
