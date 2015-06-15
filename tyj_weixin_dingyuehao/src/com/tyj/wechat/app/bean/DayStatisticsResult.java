package com.tyj.wechat.app.bean;

public class DayStatisticsResult {

	private Integer deviceId;
	private String dateStr;
//	private Date date;
	private int dayAlarms;
	private float totalTime;
	private float totalLength;
	private float averageAlarmHour;
	private float averageAlarmKilometer;
	private float seriousAlarmRate;
	private int seriousAlarms;
	public Integer getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public int getDayAlarms() {
		return dayAlarms;
	}
	public void setDayAlarms(int dayAlarms) {
		this.dayAlarms = dayAlarms;
	}
	public float getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(float totalTime) {
		//System.out.println("qqqqq"+totalTime);
		
		this.totalTime = (float)Math.round(totalTime*100)/100;
		//System.out.println("qqqqq"+this.totalTime);
	}
	public float getTotalLength() {
		return totalLength;
	}
	public void setTotalLength(float totalLength) {
		this.totalLength = (float)Math.round(totalLength*100)/100;
	}
	public float getAverageAlarmHour() {
		return averageAlarmHour;
	}
	public void setAverageAlarmHour(float averageAlarmHour) {
		this.averageAlarmHour = (float)Math.round(averageAlarmHour*100)/100;
	}
	public float getAverageAlarmKilometer() {
		return averageAlarmKilometer;
	}
	public void setAverageAlarmKilometer(float averageAlarmKilometer) {
		this.averageAlarmKilometer = (float)Math.round(averageAlarmKilometer*100)/100;
	}
	public float getSeriousAlarmRate() {
		return seriousAlarmRate;
	}
	public void setSeriousAlarmRate(float seriousAlarmRate) {
		this.seriousAlarmRate = (float)Math.round(seriousAlarmRate*100)/100;
	}
	public int getSeriousAlarms() {
		return seriousAlarms;
	}
	public void setSeriousAlarms(int seriousAlarms) {
		this.seriousAlarms = seriousAlarms;
	}
}
