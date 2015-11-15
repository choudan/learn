/**
 * 
 */
package com.example.yijia.third.bean.user;

/**
 * @author Administrator
 * 
 */
public class DeviceInfo {

	private long userId;
	private String realName;
	private String devId;
	private String startTime;
	private String useRecord;
	private String serviceDeadline;
	private int monthAvg;

	public String getServiceDeadline() {
		return serviceDeadline;
	}

	public void setServiceDeadline(String serviceDeadline) {
		this.serviceDeadline = serviceDeadline;
	}

	public String getDevId() {
		return devId;
	}

	public void setDevId(String devId) {
		this.devId = devId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getUseRecord() {
		return useRecord;
	}

	public void setUseRecord(String useRecord) {
		this.useRecord = useRecord;
	}

	public int getMonthAvg() {
		return monthAvg;
	}

	public void setMonthAvg(int monthAvg) {
		this.monthAvg = monthAvg;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Override
	public String toString() {
		return "DeviceInfo [userId=" + userId + ", realName=" + realName
				+ ", devId=" + devId + ", startTime=" + startTime
				+ ", useRecord=" + useRecord + ", serviceDeadline="
				+ serviceDeadline + ", monthAvg=" + monthAvg + "]";
	}
}
