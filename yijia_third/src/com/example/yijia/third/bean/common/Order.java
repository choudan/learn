package com.example.yijia.third.bean.common;

import com.example.yijia.third.tool.ParcelUtils;

import android.os.Parcel;
import android.os.Parcelable;

public class Order implements Parcelable {
	public long userId;
	public long serviceId;//服务类型
	public String serviceName;
	public long providerId;
	public String providerName;
	public String providerUnit;
	public int type;//0、1； 机构、个人
	public int payPath;
	public int sum;
	public int purchaseNum;
	public String startTime;
	public String deadline;
	public int isValid;
	public long instId;
	public boolean newAlter;//true新（续约）订单，false更改订单
	public boolean isAlterFinished;//true更换完成
	
	public boolean isAlterFinished() {
		return isAlterFinished;
	}

	public void setAlterFinished(boolean isAlterFinished) {
		this.isAlterFinished = isAlterFinished;
	}

	public boolean isNewAlter() {
		return newAlter;
	}

	public void setNewAlter(boolean newAlter) {
		this.newAlter = newAlter;
	}

	public long getInstId() {
		return instId;
	}

	public void setInstId(long instId) {
		this.instId = instId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getServiceId() {
		return serviceId;
	}

	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}

	public long getProviderId() {
		return providerId;
	}

	public void setProviderId(long providerId) {
		this.providerId = providerId;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getProviderUnit() {
		return providerUnit;
	}

	public void setProviderUnit(String providerUnit) {
		this.providerUnit = providerUnit;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getPayPath() {
		return payPath;
	}

	public void setPayPath(int payPath) {
		this.payPath = payPath;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public int getPurchaseNum() {
		return purchaseNum;
	}

	public void setPurchaseNum(int purchaseNum) {
		this.purchaseNum = purchaseNum;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public int getIsValid() {
		return isValid;
	}

	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}

	@Override
	public String toString() {
		return "Order [userId=" + userId + ", serviceId=" + serviceId
				+ ", serviceName=" + serviceName + ", providerId=" + providerId
				+ ", providerName=" + providerName + ", providerUnit="
				+ providerUnit + ", type=" + type + ", payPath=" + payPath
				+ ", sum=" + sum + ", purchaseNum=" + purchaseNum
				+ ", startTime=" + startTime + ", deadline=" + deadline
				+ ", isValid=" + isValid + ", instId=" + instId + ", newAlter="
				+ newAlter + ", isAlterFinished=" + isAlterFinished + "]";
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub		
		ParcelUtils.writeToParce(dest, this,new String[]{"CREATOR"});
	}
	
	public static final Parcelable.Creator<Order> CREATOR = ParcelUtils.buildCreator(Order.class,new String[]{"CREATOR"});

}
