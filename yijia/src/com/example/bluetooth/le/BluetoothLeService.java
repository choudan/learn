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

import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Service for managing connection and data communication with a GATT server hosted on a
 * given Bluetooth LE device.
 */
@SuppressLint("NewApi")
public class BluetoothLeService extends Service {
    private final static String TAG = BluetoothLeService.class.getSimpleName();

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private String mBluetoothDeviceAddress;
    private BluetoothGatt mBluetoothGatt;
    private int mConnectionState = STATE_DISCONNECTED;

    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_CONNECTED = 2;

    public final static String ACTION_GATT_CONNECTED           = "com.example.bluetooth.le.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED        = "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED = "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE           = "com.example.bluetooth.le.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_DATA                      = "com.example.bluetooth.le.EXTRA_DATA";
    public final static String TEST_TIME                       = "com.example.bluetooth.le.TEST_TIME";
    public final static String NAME                            = "com.example.bluetooth.le.NAME";	
    
    //�����ǵ�����ֵUUID   
//    private static final UUID UUID_READ_WRITE = UUID.fromString(SampleGattAttributes.YJ_BLE_READ_WRITE);
     
    // Implements callback methods for GATT events that the app cares about.  For example,
    // connection change and services discovered.
    // ������9��Ҫʵ�ֵĻص������������Ҫʵ����Щ���õ���Щ��ʵ����Щ
    // ���ֻ����ĳ�������(�пͻ�������)�������ö�;���Ҫһֱ����(�޿ͻ�������)��Ҫ��notify

    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        //���ӻ��߶Ͽ�����������һ
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            String intentAction;
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                intentAction = ACTION_GATT_CONNECTED;
                mConnectionState = STATE_CONNECTED;
                broadcastUpdate(intentAction);
                Log.i(TAG, "Connected to GATT server.");
                // Attempts to discover services after successful connection.
                // ��������֮������Ⱥ��ϵ������������Ҫconnect�ϲ���discoverServices��    
                Log.i(TAG, "Attempting to start service discovery:" +
                        mBluetoothGatt.discoverServices());

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                intentAction = ACTION_GATT_DISCONNECTED;
                mConnectionState = STATE_DISCONNECTED;
                Log.i(TAG, "Disconnected from GATT server.");
                broadcastUpdate(intentAction);
            }
        }
        
        //���ַ���Ļص������͹㲥��������
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
            } else {
                Log.w(TAG, "onServicesDiscovered received: " + status);
            }
        }
       
        //write�Ļص�,���͹㲥��������
        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status){
        	Log.e("WRITE", "onCharacteristicWrite() - status: " + status + "  - UUID: " + characteristic.getUuid());            
        	// write�ص�ʧ�� status=128��    read�ص�ʧ��status=128.  	status=0,�ٵ��ɹ���=9�����鳬��
        	if (status == BluetoothGatt.GATT_SUCCESS) 
            {
//                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
                Log.e("WRITE SUCCESS", "�ص��ɹ� " + status + "  - UUID: " + characteristic.getUuid());
            }else{
            	 Log.e("FAIL", "�ص�ʧ�� " + status + "  - UUID: " + characteristic.getUuid());
            }
        }
        
        //read�ص���������
        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic,
                                         int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
                Log.e("READ SUCCESS", "onCharacteristicRead() - status: " + status + "  - UUID: " + characteristic.getUuid());
            }
        }

        //notification�ص���������
        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
        }
    };

    
    //��һ���ض��Ļص���������������������ʵ���broadcastUpdate()��������������һ��action��
    //���broadcastUpdate������ʵ������״̬��������һ�������Ĺ㲥
    private void broadcastUpdate(final String action) {
        final Intent intent = new Intent(action);
        sendBroadcast(intent);
    }
    
    //��дbroadcastUpdate��������ʵ����������״̬�Ĺ㲥�����������ġ���Ļص�����������Ҫ������������
    //ע��,�����е����ݽ���ִ�а����������ʲ�����Ҫ�ļ��淶    
    //Parameters intent �� The Intent to broadcast; 
    //all receivers matching this Intent will receive the broadcast.
    //an intent with a given action.
    private void broadcastUpdate( String action,
                                  BluetoothGattCharacteristic characteristic) {
    	 Intent intent = new Intent(action);   
        	// �����ļ�����ʮ�����Ʒ��ͺͽ������ݡ�    	 
		      byte[] data = characteristic.getValue();
		      StringBuilder stringBuilder = new StringBuilder(data.length);//StringBuilder���̰߳�ȫ��ִ���ٶ����	      
		      if (data != null && data.length > 0) {	    	  		     	  	            	 	             
    			  for(byte byteChar : data)
    				  stringBuilder.append(String.format("%02X", byteChar));//"%02x "��FF FF��ʽ��������(ע�����޿ո�)   			             		  
	              intent.putExtra(EXTRA_DATA,stringBuilder.toString());		            	  
	          }
		      
		   Log.e("�㲥����","����ֵ����"+characteristic.getValue().length+"  "+stringBuilder.toString());
    	   sendBroadcast(intent); 	  
    }     
    
    public class LocalBinder extends Binder {
        BluetoothLeService getService() {
            return BluetoothLeService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // After using a given device, you should make sure that BluetoothGatt.close() is called
        // such that resources are cleaned up properly.  In this particular example, close() is
        // invoked when the UI is disconnected from the Service.
        close();
        return super.onUnbind(intent);
    }

    private final IBinder mBinder = new LocalBinder();

    /**
     * Initializes a reference to the local Bluetooth adapter.
     *
     * @return Return true if the initialization is successful.
     */
    public boolean initialize() {
        // For API level 18 and above, get a reference to BluetoothAdapter through
        // BluetoothManager.
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null) {
                Log.e(TAG, "Unable to initialize BluetoothManager.");
                return false;
            }
        }

        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
            return false;
        }
        
        return true;
    }

    /**
     * Connects to the GATT server hosted on the Bluetooth LE device.
     *
     * @param address The device address of the destination device.
     *
     * @return Return true if the connection is initiated successfully. The connection result
     *         is reported asynchronously through the
     *         {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     *         callback.
     */
    public boolean connect(final String address) {
        if (mBluetoothAdapter == null || address == null) {
            Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }

        // Previously connected device.  Try to reconnect.
        if (mBluetoothDeviceAddress != null && address.equals(mBluetoothDeviceAddress)
                && mBluetoothGatt != null) {
            Log.d(TAG, "Trying to use an existing mBluetoothGatt for connection.");
            if (mBluetoothGatt.connect()) {
                mConnectionState = STATE_CONNECTING;
                return true;
            } else {
                return false;
            }
        }

        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            Log.w(TAG, "Device not found.  Unable to connect.");
            return false;
        }
        // We want to directly connect to the device, so we are setting the autoConnect
        // parameter to false.
        // device.connectGatt���ӵ�GATT Server,������һ��BluetoothGattʵ��.
        // mGattCallbackΪ�ص�����BluetoothGattCallback�������ࣩ��ʵ����
        mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
        Log.d(TAG, "Trying to create a new connection.");
        mBluetoothDeviceAddress = address;
        mConnectionState = STATE_CONNECTING;
        return true;
    }

    /**
     * Disconnects an existing connection or cancel a pending connection. The disconnection result
     * is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */
    public void disconnect() {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.disconnect();
    }

    /**
     * After using a given BLE device, the app must call this method to ensure resources are
     * released properly.
     */
    public void close() {
        if (mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    /**
     * Request a read on a given {@code BluetoothGattCharacteristic}. The read result is reported
     * asynchronously through the {@code BluetoothGattCallback#onCharacteristicRead(android.bluetooth.BluetoothGatt, android.bluetooth.BluetoothGattCharacteristic, int)}
     * callback.
     *
     * @param characteristic The characteristic to read from.
     * 
     * ΪӦ�÷��㣬��д��readCharacteristic()����
     */
    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.readCharacteristic(characteristic);        
    }

    /**
     * Enables or disables notification on a give characteristic.
     *
     * @param characteristic Characteristic to act on.
     * @param enabled If true, enable notification.  False otherwise.
     * 
     * ��дsetCharacteristicNotification()
     */
    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic,
                                              boolean enabled) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);

        // This is specific to Heart Rate Measurement.����characteristic������ֵ��
        // ���еķ�������ֵ������ֵ����UUID����ʶ���ȸ���characteristic��UUID�ҵ�characteristic���ٸ���BluetoothGattDescriptor��
        // UUID�ҵ�BluetoothGattDescriptor��Ȼ���趨��ֵ��
        // ����descriptor������ͨ��getDescriptor()�����ķ���ֵ�����,
        // Returns a descriptor with a given UUID out of the list of descriptors for this characteristic.

//        if (UUID_READ_WRITE.equals(characteristic.getUuid())) {
//        	byte[] test={(byte)0xA1,(byte)0xFF};
//            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(
//            		UUID_NOTIFY_DESCRI);
//            descriptor.setValue(test);
//            mBluetoothGatt.writeDescriptor(descriptor);
//        }
    }
    
    public boolean writeCharacteristic(BluetoothGattCharacteristic characteristic,byte[]test){
    	boolean flag = false;   		
		    characteristic.setValue(test);
		    flag=mBluetoothGatt.writeCharacteristic(characteristic);
		    Log.e(TAG,"���鳤��"+test.length+""+flag);
		return flag;    	
   }

    /**
     * Retrieves a list of supported GATT services on the connected device. This should be
     * invoked only after {@code BluetoothGatt#discoverServices()} completes successfully.
     *
     * @return A {@code List} of supported services.
     */
    public BluetoothGattService getSupportedGattServices(UUID uuid) {
    	BluetoothGattService mBluetoothGattService;
    	if (mBluetoothGatt == null) return null;
    	mBluetoothGattService=mBluetoothGatt.getService(uuid);
        return mBluetoothGattService;
    }  
}

