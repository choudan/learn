package com.tyj.wechat.app.service;

import java.util.Calendar;
import java.util.List;
import com.tyj.wechat.app.MsgTypes;
import com.tyj.wechat.app.bean.DeviceGpsInfosBean;
import com.tyj.wechat.app.bean.UserDataMain;
import com.tyj.wechat.app.model.DeviceGpsInfos;
import com.tyj.wechat.convertor.DeviceGpsConvertor;

public class UserDataService {
	Calendar c = Calendar.getInstance();
	int year = c.get(Calendar.YEAR);
	int month = c.get(Calendar.MONTH) + 1;
	int day = c.get(Calendar.DAY_OF_MONTH);

	// 方法
	public UserDataMain uds(int deviceId) {

		UserDataMain ud = new UserDataMain();
		List<DeviceGpsInfos> list = DeviceGpsInfos.dao
				.find("select * from device_gps_infos where device_id=? and year(upload_time)=? and month(upload_time)=? and day(upload_time)=? order by upload_time asc",
						deviceId, year, month, day);
		if (list == null || list.size() == 0) {
			return ud;
		}
		// 赋值到这个beans里
		List<DeviceGpsInfosBean> beans = DeviceGpsConvertor.convert(list);
		int a[] = new int[5];
		for (DeviceGpsInfosBean dgi : beans) {
			// 汽车速度
			if (dgi.getMsgType() == MsgTypes.SPEED_LIMIT_WARNING) {
				// newSection = false;
				a[0]++;
			} else if (dgi.getMsgType() == MsgTypes.LEFT_CHANNEL
					|| dgi.getMsgType() == MsgTypes.RIGHT_CHANNEL) {
				a[1]++;
			} else if (dgi.getMsgType() == MsgTypes.CITY_COLLISION_WARNING) {
				a[2]++;
			} else if (dgi.getMsgType() == MsgTypes.FRONT_COLLISION_WARNING
					&& dgi.getSpeed() > 50) {
				a[3]++;

			} else if (dgi.getMsgType() == MsgTypes.FRONT_COLLISION_WARNING
					&& dgi.getSpeed() <= 50) {
				a[4]++;
			}
		}// for的
		ud.setBang(a[3]);
		ud.setCity_bang(a[2]);
		ud.setSpeeding(a[0]);
		ud.setLaneout(a[1]);
		ud.setWalker_bang(a[4]);
		return ud;
	}
}
