package com.example.yijia.third.bean.common;

import com.example.yijia.third.tool.ParcelUtils;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderAlterNotify implements Parcelable {
	public long orderId;
	public int type;
	public int delayMonth;
	public String delayTakeEffect;
	public String delayReason;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getDelayMonth() {
		return delayMonth;
	}

	public void setDelayMonth(int delayMonth) {
		this.delayMonth = delayMonth;
	}

	public String getDelayTakeEffect() {
		return delayTakeEffect;
	}

	public void setDelayTakeEffect(String delayTakeEffect) {
		this.delayTakeEffect = delayTakeEffect;
	}

	public String getDelayReason() {
		return delayReason;
	}

	public void setDelayReason(String delayReason) {
		this.delayReason = delayReason;
	}

	@Override
	public String toString() {
		return "OrderAlter [orderId=" + orderId + ", type=" + type
				+ ", delayMonth=" + delayMonth + ", delayTakeEffect="
				+ delayTakeEffect + ", delayReason=" + delayReason + "]";
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		ParcelUtils.writeToParce(dest, this, new String[] { "CREATOR" });
	}

	public static final Parcelable.Creator<OrderAlterNotify> CREATOR = ParcelUtils
			.buildCreator(OrderAlterNotify.class, new String[] { "CREATOR" });

}
