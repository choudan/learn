package com.tyj.wechat.app.service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.zlg.util.ListMap;
import cn.zlg.util.ListMapLoopCallback;

import com.tyj.wechat.app.MsgTypes;
import com.tyj.wechat.app.bean.DayStatisticsResult;
import com.tyj.wechat.app.bean.DeviceGpsInfosBean;
import com.tyj.wechat.app.bean.DriveTrend;
import com.tyj.wechat.app.model.DeviceGpsInfos;
import com.tyj.wechat.app.model.Hour_to_day;
import com.tyj.wechat.convertor.DeviceGpsConvertor;
import com.tyj.wechat.util.LatLngUtils;

public class StatisticsService {

	// 保留两位小数
	int itemNum = 2;
	// float num=(float)(Math.round(totalPrice*100)/100);

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Calendar begin = Calendar.getInstance();

	public DriveTrend getDriveTrend(int deviceId, int type, Date startDate, Date endDate) {
		DriveTrend dt = new DriveTrend();
		dt.setDeviceId(deviceId);
		dt.setStartDate(sdf.format(startDate));
		dt.setEndDate(sdf.format(endDate));
		Integer a[][] = new Integer[2][7];
		a[0][0] = a[0][1] = a[0][2] = a[0][3] = a[0][4] = a[0][5] = a[0][6] = 0;
		a[1][0] = a[1][1] = a[1][2] = a[1][3] = a[1][4] = a[1][5] = a[1][6] = 0;
		if (type == 0 || type == 1) {
			long i = (endDate.getTime() - startDate.getTime())/ (24 * 60 * 60 * 1000);
			for (int j = 0; j < (int) i; j++) {
				begin.setTime(startDate);
				begin.add(Calendar.DAY_OF_MONTH, j);
				String add = sdf.format(begin.getTime());
				String sql = "select warntimes from device_gps_dayinfos where device_id='"
						+ deviceId + "' and date='" + add + "'";
				try {
					a[0][j] = Hour_to_day.dao.findFirst(sql).get("warntimes");
					String sql1 = "select s_warntimes from device_gps_dayinfos where device_id='"
							+ deviceId + "' and date='" + add + "'";
					a[1][j] = Hour_to_day.dao.findFirst(sql1).get(
							"s_warntimes", 0);
				} catch (Exception e) {
					continue;
				}
			}
		} else if (type == 2) {
			begin.set(Calendar.DATE, 1);
			begin.add(Calendar.MONTH, -1);
			String ad[] = new String[5];
			ad[0] = sdf.format(begin.getTime());
			for (int j = 0; j < 4; j++) {
				begin.add(Calendar.DAY_OF_MONTH, 7);
				try {
					ad[j + 1] = sdf.format(begin.getTime());
					String sql = "select warntimes from device_gps_dayinfos where device_id='"
							+ deviceId
							+ "' and date>='"
							+ ad[j]
							+ "' and date <'" + ad[j + 1] + "'";
					List<Hour_to_day> u = Hour_to_day.dao.find(sql);
					for (int t = 0; t < u.size(); t++) {
						a[0][j] = a[0][j] + u.get(t).getInt("warntimes");
					}
					String sql1 = "select s_warntimes from device_gps_dayinfos where device_id='"
							+ deviceId
							+ "' and date>='"
							+ ad[j]
							+ "' and date <'" + ad[j + 1] + "'";
					List<Hour_to_day> u1 = Hour_to_day.dao.find(sql1);
					System.out.println("uuuuuuuu" + u1.size());
					for (int t = 0; t < u1.size(); t++) {
						a[1][j] = a[1][j] + u1.get(t).getInt("s_warntimes");
					}
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		} else {
			begin.set(Calendar.DATE, 1);
			begin.add(Calendar.MONTH, -6);
			String ad[] = new String[7];
			ad[0] = sdf.format(begin.getTime());

			for (int j = 0; j < 6; j++) {
				begin.add(Calendar.MONTH, 1);
				try {
					ad[j + 1] = sdf.format(begin.getTime());
					System.out.println("000000000000000" + ad[j]);
					String sql = "select warntimes from device_gps_dayinfos where device_id='"
							+ deviceId
							+ "' and date>='"
							+ ad[j]
							+ "' and date <'" + ad[j + 1] + "%'";
					List<Hour_to_day> u = Hour_to_day.dao.find(sql);
					System.out.println("qqqqq" + u.size());
					for (int t = 0; t < u.size(); t++) {
						a[0][j] = a[0][j] + u.get(t).getInt("warntimes");
					}
					String sql1 = "select s_warntimes from device_gps_dayinfos where device_id='"
							+ deviceId
							+ "' and date>='"
							+ ad[j]
							+ "' and date <'" + ad[j + 1] + "%'";
					List<Hour_to_day> u1 = Hour_to_day.dao.find(sql1);
					System.out.println("qqqqq" + u1.size());
					for (int t = 0; t < u1.size(); t++) {
						a[1][j] = a[1][j] + u1.get(t).getInt("s_warntimes");
					}
				} catch (Exception e) {
					continue;
				}
			}
		}

		// 三天的3个点
		if (type == 0) {
			dt.setKeypoint1(Arrays.asList(new Integer[] { a[0][0], a[0][1],
					a[0][2] }));
			dt.setKeypoint2(Arrays.asList(new Integer[] { a[1][0], a[1][1],
					a[1][2] }));
			dt.setLabels1(Arrays.asList(new Integer[] { 1, 2, 3 }));
			dt.setSuggestion("驾驶习惯良好，请继续保持");
		}
		// 一周的7个点
		else if (type == 1) {
			dt.setKeypoint1(Arrays.asList(new Integer[] { a[0][0], a[0][1],
					a[0][2], a[0][3], a[0][4], a[0][5], a[0][6] }));
			dt.setKeypoint2(Arrays.asList(new Integer[] { a[1][0], a[1][1],
					a[1][2], a[1][3], a[1][4], a[1][5], a[1][6] }));
			dt.setLabels1(Arrays.asList(new Integer[] { 1, 2, 3, 4, 5, 6, 7 }));
			dt.setSuggestion("驾驶习惯较好，请注意车道偏离情况");
		}
		// 一个月的4个点，除以7取整
		else if (type == 2) {
			dt.setKeypoint1(Arrays.asList(new Integer[] { a[0][0] / 7,
					a[0][1] / 7, a[0][2] / 7, a[0][3] / 7 }));
			dt.setKeypoint2(Arrays.asList(new Integer[] { a[1][0] / 7,
					a[1][1] / 7, a[1][2] / 7, a[1][3] / 7 }));
			dt.setLabels1(Arrays.asList(new Integer[] { 1, 2, 3, 4 }));
			dt.setSuggestion("驾驶习惯良好，请继续保持");
		}
		// 半年的6个点,除以30取整
		else if (type == 3) {
			dt.setKeypoint1(Arrays.asList(new Integer[] { a[0][0] / 30,
					a[0][1] / 30, a[0][2] / 30, a[0][3] / 30, a[0][4] / 30,
					a[0][5] / 30 }));
			dt.setKeypoint2(Arrays.asList(new Integer[] { a[1][0] / 30,
					a[1][1] / 30, a[1][2] / 30, a[1][3] / 30, a[1][4] / 30,
					a[1][5] / 30 }));
			dt.setLabels1(Arrays.asList(new Integer[] { 1, 2, 3, 4, 5, 6 }));
			dt.setSuggestion("驾驶习惯较好，请多注意车道偏离及保持车距");
		}
		return dt;
	}

	// 图下面的数据
	public DayStatisticsResult getDeviceDayStatisticsResult(Integer deviceId,
			Date date) {
		final DayStatisticsResult re = new DayStatisticsResult();
		re.setDateStr(sdf.format(date));
		re.setDeviceId(deviceId);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		// 列出所有结果
		List<DeviceGpsInfos> list = DeviceGpsInfos.dao
				.find("select * from device_gps_infos where device_id=? and year(upload_time)=? and month(upload_time)=? and day(upload_time)=? order by upload_time asc",
						deviceId, year, month, day);
		if (list == null || list.size() == 0) {
			return re;
		}
		// 赋值到这个beans里
		List<DeviceGpsInfosBean> beans = DeviceGpsConvertor.convert(list);
		// 告警次数
		re.setDayAlarms(getAlarms(beans));

		ListMap<Integer, DeviceGpsInfosBean> driveTimeSection = getDriveTimeSection(beans);
		// 实现抽象方法
		driveTimeSection.each(new ListMapLoopCallback<Integer, DeviceGpsInfosBean>() {
			@Override
			public void call(Integer arg0, List<DeviceGpsInfosBean> list) {
				if (list.size() < 2) {
					return;
				}
				// list的最后一个对象和第一个对象的时间差
				long time = list.get(list.size() - 1).getEventTime()
						.getTime()
						- list.get(0).getEventTime().getTime();
				re.setTotalTime(re.getTotalTime()
						+ (float) (time * 1.0 / 1000 / 3600));
				// re.setTotalTime((float) 12.34674224);
				re.setTotalLength(re.getTotalLength()
						+ getSectionLengthInKilometor(list));
			}
		});
		if (re.getTotalTime() > 0) {
			re.setAverageAlarmHour(re.getDayAlarms() / re.getTotalTime());
		}
		if (re.getTotalLength() > 0) {
			re.setAverageAlarmKilometer(re.getDayAlarms() / re.getTotalLength());
		}
		re.setSeriousAlarms(getSeriousAlarms(beans));
		re.setSeriousAlarmRate((float) (re.getSeriousAlarms() * 1.0 / re
				.getDayAlarms()));
		return re;
	}

	private int getAlarms(List<DeviceGpsInfosBean> list) {
		int re = 0;
		for (DeviceGpsInfosBean d : list) {
			if (d.getMsgType() == MsgTypes.FRONT_COLLISION_WARNING
					|| d.getMsgType() == MsgTypes.CITY_COLLISION_WARNING
					|| d.getMsgType() == MsgTypes.LEFT_CHANNEL
					|| d.getMsgType() == MsgTypes.RIGHT_CHANNEL
					|| d.getMsgType() == MsgTypes.CAR_DISTANCE_AND_WARNING
					|| d.getMsgType() == MsgTypes.SPEED_LIMIT_WARNING) {
				re++;
			}
		}
		return re;
	}

	private int getSeriousAlarms(List<DeviceGpsInfosBean> list) {
		int re = 0;
		for (DeviceGpsInfosBean d : list) {
			if (d.getMsgType() == MsgTypes.FRONT_COLLISION_WARNING) {// d.getMsgType()==MsgTypes.CITY_COLLISION_WARNING
																		// ||
				re++;
			}
		}
		return re;
	}

	protected float getSectionLengthInKilometor(List<DeviceGpsInfosBean> list) {
		float re = 0;
		for (int i = 1; i < list.size(); i++) {
			DeviceGpsInfosBean current = list.get(i);// 得到一个gps信息对象
			DeviceGpsInfosBean prev = list.get(i - 1);// 得到他的前一个数据对象
			re += LatLngUtils.getBrokenLineDistanceInMeter(
					current.getLongitude(), current.getLatitude(),
					prev.getLongitude(), prev.getLatitude());
		}
		return re;
	}

	private ListMap<Integer, DeviceGpsInfosBean> getDriveTimeSection(
			List<DeviceGpsInfosBean> beans) {
		int index = 0;
		boolean newSection = true;
		ListMap<Integer, DeviceGpsInfosBean> map = new ListMap<Integer, DeviceGpsInfosBean>();
		// 一个一个的遍历这个Beans，统计启动次数和熄火次数
		for (DeviceGpsInfosBean dgi : beans) {
			// 汽车启动，类的属性为
			if (dgi.getMsgType() == MsgTypes.CAR_START_WARNING) {
				newSection = false;
				index++;
				map.add(index, dgi);
			} else if (dgi.getMsgType() == MsgTypes.CAR_CLOSE_WARNING) {
				if (!newSection) {
					newSection = true;
					map.add(index, dgi);
				}
			} else {
				if (!newSection) {
					map.add(index, dgi);
				}
			}
		}
		return map;
	}
}
