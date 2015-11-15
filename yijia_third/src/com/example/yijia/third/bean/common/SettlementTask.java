package com.example.yijia.third.bean.common;

import com.example.yijia.third.tool.ParcelUtils;

import android.os.Parcel;
import android.os.Parcelable;


public class SettlementTask implements Parcelable {
	public long settlementTaskId;
	public String settlementTime;

	public long getSettlementTaskId() {
		return settlementTaskId;
	}

	public void setSettlementTaskId(long settlementTaskId) {
		this.settlementTaskId = settlementTaskId;
	}

	public String getSettlementTime() {
		return settlementTime;
	}

	public void setSettlementTime(String settlementTime) {
		this.settlementTime = settlementTime;
	}

	@Override
	public String toString() {
		return "SettlementTask [settlementTaskId=" + settlementTaskId
				+ ", settlementTime=" + settlementTime + "]";
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

	public static final Parcelable.Creator<SettlementTask> CREATOR = ParcelUtils
			.buildCreator(SettlementTask.class, new String[] { "CREATOR" });
}
