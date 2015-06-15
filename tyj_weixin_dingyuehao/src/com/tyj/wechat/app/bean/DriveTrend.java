package com.tyj.wechat.app.bean;

import java.util.List;

public class DriveTrend {

	private int deviceId;
	private String startDate;
	private String endDate;
	private List<Integer> keypoint1;
	private List<Integer> keypoint2;
	private List<Integer> labels1;
	//private List<Integer> labels2;
	private String suggestion;
	
	public List<Integer> getLabels1() {
		return labels1;
	}
	public void setLabels1(List<Integer> labels1) {
		this.labels1 = labels1;
	}
//	public List<Integer> getLabels2() {
//		return labels2;
//	}
//	public void setLabels2(List<Integer> labels2) {
//		this.labels2 = labels2;
//	}
	public List<Integer> getKeypoint1() {
		return keypoint1;
	}
	public void setKeypoint1(List<Integer> keypoint1) {
		this.keypoint1 = keypoint1;
	}
	public List<Integer> getKeypoint2() {
		return keypoint2;
	}
	public void setKeypoint2(List<Integer> keypoint2) {
		this.keypoint2 = keypoint2;
	}
	public int getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getSuggestion() {
		return suggestion;
	}
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
	
}
