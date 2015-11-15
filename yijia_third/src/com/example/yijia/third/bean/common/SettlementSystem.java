package com.example.yijia.third.bean.common;

import android.os.Parcel;
import android.os.Parcelable;

public class SettlementSystem implements Parcelable {
	public String settlementTime;
	public int totalIncome;
	public int totalExpend;

	public String getSettlementTime() {
		return settlementTime;
	}

	public void setSettlementTime(String settlementTime) {
		this.settlementTime = settlementTime;
	}

	public int getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(int totalIncome) {
		this.totalIncome = totalIncome;
	}

	public int getTotalExpend() {
		return totalExpend;
	}

	public void setTotalExpend(int totalExpend) {
		this.totalExpend = totalExpend;
	}

	@Override
	public String toString() {
		return "SettlementSystem [settlementTime=" + settlementTime
				+ ", totalIncome=" + totalIncome + ", totalExpend="
				+ totalExpend + "]";
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(settlementTime);
		dest.writeInt(totalIncome);
		dest.writeInt(totalExpend);
	}

	public final static Parcelable.Creator<SettlementSystem> CREATOR = new Parcelable.Creator<SettlementSystem>() {

		@Override
		public SettlementSystem createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			SettlementSystem user = new SettlementSystem();
			user.settlementTime = source.readString();
			user.totalIncome = source.readInt();
			user.totalExpend = source.readInt();
			return user;
		}

		@Override
		public SettlementSystem[] newArray(int size) {
			// TODO Auto-generated method stub
			return new SettlementSystem[size];
		}

	};

}
