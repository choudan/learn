package com.tyj.wechat.convertor;

import java.util.List;

import cn.zlg.util.New;

import com.jfinal.plugin.activerecord.Model;
import com.tyj.wechat.app.bean.DeviceGpsInfosBean;
import com.tyj.wechat.app.model.DeviceGpsInfos;

public class DeviceGpsConvertor {

	public static DeviceGpsInfosBean convert(Model m){
		DeviceGpsInfosBean dg = new DeviceGpsInfosBean();
		dg.setDeviceId(m.getInt("device_id"));
		dg.setEventTime(m.getTimestamp("upload_time"));
		dg.setFlashState(m.getInt("flash_state"));
		dg.setId(m.getInt("id"));
		dg.setLatitude(m.getDouble("latitude"));
		dg.setLongitude(m.getDouble("longitude"));
		dg.setMsgType(m.getInt("msg_type"));
		dg.setSpeed(m.getInt("speed"));
		dg.setUploadTime(m.getTimestamp("upload_time"));
		return dg;
	}
	public static List<DeviceGpsInfosBean> convert(List<DeviceGpsInfos> ms){
		if(ms==null){
			return null;
		}
		List<DeviceGpsInfosBean> list = New.arraylist(ms.size());
		for(Model m:ms){
			list.add(convert(m));
		}
		return list;
	}
}
