/**
 * 
 */
package com.example.yijia.third.bean.admin;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author  丑旦 
 * @date 创建时间：2015-8-21 下午9:42:44 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
/**
 * @author choudan
 * 
 */
public class DeleteMsa implements Parcelable {
	private long msaId;
	private long defaultMsaId;
	private String serviceDeadline;
	private String applyDate;
	private String giftReason;
	private String broadcastStartDate;
	private String broadcastDeadline;
	private int giftMonth;
	private int isBroadcast;

	public long getMsaId() {
		return msaId;
	}

	public void setMsaId(long msaId) {
		this.msaId = msaId;
	}

	public long getDefaultMsaId() {
		return defaultMsaId;
	}

	public void setDefaultMsaId(long defaultMsaId) {
		this.defaultMsaId = defaultMsaId;
	}

	public String getServiceDeadline() {
		return serviceDeadline;
	}

	public void setServiceDeadline(String serviceDeadline) {
		this.serviceDeadline = serviceDeadline;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getGiftReason() {
		return giftReason;
	}

	public void setGiftReason(String giftReason) {
		this.giftReason = giftReason;
	}

	public String getBroadcastStartDate() {
		return broadcastStartDate;
	}

	public void setBroadcastStartDate(String broadcastStartDate) {
		this.broadcastStartDate = broadcastStartDate;
	}

	public String getBroadcastDeadline() {
		return broadcastDeadline;
	}

	public void setBroadcastDeadline(String broadcastDeadline) {
		this.broadcastDeadline = broadcastDeadline;
	}

	public int getGiftMonth() {
		return giftMonth;
	}

	public void setGiftMonth(int giftMonth) {
		this.giftMonth = giftMonth;
	}

	public int getIsBroadcast() {
		return isBroadcast;
	}

	public void setIsBroadcast(int isBroadcast) {
		this.isBroadcast = isBroadcast;
	}

	@Override
	public String toString() {
		return "DeleteMsa [msaId=" + msaId + ", defaultMsaId=" + defaultMsaId
				+ ", serviceDeadline=" + serviceDeadline + ", applyDate="
				+ applyDate + ", giftReason=" + giftReason
				+ ", broadcastStartDate=" + broadcastStartDate
				+ ", broadcastDeadline=" + broadcastDeadline + ", giftMonth="
				+ giftMonth + ", isBroadcast=" + isBroadcast + "]";
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeLong(msaId);
		dest.writeLong(defaultMsaId);
		dest.writeString(serviceDeadline);
		dest.writeString(applyDate);
		dest.writeString(giftReason);
		dest.writeString(broadcastStartDate);
		dest.writeString(broadcastDeadline);
		dest.writeInt(giftMonth);
		dest.writeInt(isBroadcast);
	}

	public static final Parcelable.Creator<DeleteMsa> CREATOR = new Creator<DeleteMsa>() {
		@Override
		public DeleteMsa[] newArray(int size) {
			return new DeleteMsa[size];
		}

		@Override
		public DeleteMsa createFromParcel(Parcel in) {
			DeleteMsa deleteMsa = new DeleteMsa();
			deleteMsa.msaId = in.readLong();
			deleteMsa.defaultMsaId = in.readLong();
			deleteMsa.serviceDeadline = in.readString();
			deleteMsa.applyDate = in.readString();
			deleteMsa.giftReason = in.readString();
			deleteMsa.broadcastStartDate = in.readString();
			deleteMsa.broadcastDeadline = in.readString();
			deleteMsa.giftMonth = in.readInt();
			deleteMsa.isBroadcast = in.readInt();
			return deleteMsa;
		}
	};

}
