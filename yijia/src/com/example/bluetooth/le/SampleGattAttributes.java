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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.bluetooth.BluetoothGattCharacteristic;
import android.text.TextUtils;

/**
 * This class includes a small subset of standard GATT attributes for demonstration purposes.
 */
public class SampleGattAttributes {
    private static HashMap<String, String> attributes = new HashMap();
    //这样写只是赋了一个常量值
    public static String HEART_RATE_MEASUREMENT = "00002a37-0000-1000-8000-00805f9b34fb";
    
    //the descriptor of battery characteristic(battery service)
    public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";
    
    public static String YJ_BLE_Service = "0000fff0-0000-1000-8000-00805f9b34fb";
    public static String YJ_BLE_READ_WRITE = "0000fff1-0000-1000-8000-00805f9b34fb";
    public static String YJ_BLE_NOTIFY = "0000fff4-0000-1000-8000-00805f9b34fb";
	
	
//	public static String SH_CHAR = "0000fede-0000-1000-8000-00805f9b34fb";//手环有读写权限的特征值
//	public static String SH_SERVICE = "0000fee1-0000-1000-8000-00805f9b34fb";
	
//	public static String YJ_BLE_WRITE = "0000fff3-0000-1000-8000-00805f9b34fb";
    
	static {
        // Sample Services.    	
       /* attributes.put("0000180d-0000-1000-8000-00805f9b34fb", "Heart Rate Service");
        attributes.put("0000180a-0000-1000-8000-00805f9b34fb", "Device Information Service");
        
        // Sample Characteristics.
        attributes.put(HEART_RATE_MEASUREMENT, "Heart Rate Measurement");
        attributes.put("00002a29-0000-1000-8000-00805f9b34fb", "Manufacturer Name String");
        */
    	
    	// Sample Services.给自己用到的服务命名
    	attributes.put("0000fff0-0000-1000-8000-00805f9b34fb", "颐佳经络仪");
        attributes.put("0000180a-0000-1000-8000-00805f9b34fb", "经络仪设备信息");
    	
        //Sample Characteristics.给自己用到的特征值命名
        attributes.put(YJ_BLE_READ_WRITE, "READ_WRITE");
		
        attributes.put("00002a37-0000-1000-8000-00805f9b34fb", "YJ Name");
        attributes.put("00002a29-0000-1000-8000-00805f9b34fb", "Manufacturer Name String");
    } 
    public static String lookup(String uuid, String defaultName) {
        String name = attributes.get(uuid);
        return name == null ? defaultName : name;
    }
    
    // 添加方法
    //-------------------------------------------    
    private static HashMap<Integer, String> charPermissions = new HashMap();
    static {
    	charPermissions.put(0, "UNKNOW");
    	charPermissions.put(BluetoothGattCharacteristic.PERMISSION_READ, "READ");
    	charPermissions.put(BluetoothGattCharacteristic.PERMISSION_READ_ENCRYPTED, "READ_ENCRYPTED");
    	charPermissions.put(BluetoothGattCharacteristic.PERMISSION_READ_ENCRYPTED_MITM, "READ_ENCRYPTED_MITM");
    	charPermissions.put(BluetoothGattCharacteristic.PERMISSION_WRITE, "WRITE");
    	charPermissions.put(BluetoothGattCharacteristic.PERMISSION_WRITE_ENCRYPTED, "WRITE_ENCRYPTED");
    	charPermissions.put(BluetoothGattCharacteristic.PERMISSION_WRITE_ENCRYPTED_MITM, "WRITE_ENCRYPTED_MITM");
    	charPermissions.put(BluetoothGattCharacteristic.PERMISSION_WRITE_SIGNED, "WRITE_SIGNED");
    	charPermissions.put(BluetoothGattCharacteristic.PERMISSION_WRITE_SIGNED_MITM, "WRITE_SIGNED_MITM");	
    }
    
    public static String getCharPermission(int permission){
    	return getHashMapValue(charPermissions,permission);
    }
    
    private static String getHashMapValue(HashMap<Integer, String> hashMap,int number){
    	String result =hashMap.get(number);
    	if(TextUtils.isEmpty(result)){
    		List<Integer> numbers = getElement(number);
    		result="";
    		for(int i=0;i<numbers.size();i++){
    			result+=hashMap.get(numbers.get(i))+"|";
    		}
    	}
    	return result;
    }
    
    static private List<Integer> getElement(int number){
    	List<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < 32; i++){
            int b = 1 << i;
            if ((number & b) > 0) 
            	result.add(b);
        }
        
        return result;
    }
       
    public static String bytesToHexString(byte[] src){  
        StringBuilder stringBuilder = new StringBuilder("");  
        if (src == null || src.length <= 0) {  
            return null;  
        }  
        for (int i = 0; i < src.length; i++) {  
            int v = src[i] & 0xFF;  
            String hv = Integer.toHexString(v);  
            if (hv.length() < 2) {  
                stringBuilder.append(0);  
            }  
            stringBuilder.append(hv);  
        }  
        return stringBuilder.toString();  
    }  
}
