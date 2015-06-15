/*
 * Word Union : Powered By [zlg]
 * Since 2010 - 2014
 */

package com.tyj.wechat.app.bean;



import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class DeviceGpsInfosBean implements java.io.Serializable{
	
	
	private java.lang.Integer id;
	private java.lang.Integer deviceId;
	private java.util.Date eventTime;
	private Double longitude;
	private Double latitude;
	private Integer speed;
	private Integer msgType;
	private Integer flashState;
	private java.sql.Timestamp uploadTime;

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public java.lang.Integer getDeviceId() {
		return this.deviceId;
	}
	
	public void setDeviceId(java.lang.Integer value) {
		this.deviceId = value;
	}
	
	public Date getEventTime() {
		return this.eventTime;
	}
	
	public void setEventTime(Date value) {
		this.eventTime = value;
	}
	
	public Double getLongitude() {
		return this.longitude;
	}
	
	public void setLongitude(Double value) {
		this.longitude = value;
	}
	
	public Double getLatitude() {
		return this.latitude;
	}
	
	public void setLatitude(Double value) {
		this.latitude = value;
	}
	
	public Integer getSpeed() {
		return this.speed;
	}
	
	public void setSpeed(Integer value) {
		this.speed = value;
	}
	
	public Integer getMsgType() {
		return this.msgType;
	}
	
	public void setMsgType(Integer value) {
		this.msgType = value;
	}
	
	public Integer getFlashState() {
		return this.flashState;
	}
	
	public void setFlashState(Integer value) {
		this.flashState = value;
	}
	
	public java.sql.Timestamp getUploadTime() {
		return this.uploadTime;
	}
	
	public void setUploadTime(java.sql.Timestamp value) {
		this.uploadTime = value;
	}
	

	@Override
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("DeviceId",getDeviceId())
			.append("EventTime",getEventTime())
			.append("Longitude",getLongitude())
			.append("Latitude",getLatitude())
			.append("Speed",getSpeed())
			.append("Type",getMsgType())
			.append("FlashState",getFlashState())
			.append("UploadTime",getUploadTime())
			.toString();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof DeviceGpsInfosBean == false) return false;
		if(this == obj) return true;
		DeviceGpsInfosBean other = (DeviceGpsInfosBean)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

