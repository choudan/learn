package com.example.yijia.third.bean.inst;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author 丑旦
 * @date 创建时间：2015-8-22 上午11:15:29
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class Inst implements Parcelable{
	private long instId;
	private String realName;
	private String instName;
	private int totalNum;
	private int presentNum;

	public long getInstId() {
		return instId;
	}

	public void setInstId(long instId) {
		this.instId = instId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getInstName() {
		return instName;
	}

	public void setInstName(String instName) {
		this.instName = instName;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getPresentNum() {
		return presentNum;
	}

	public void setPresentNum(int presentNum) {
		this.presentNum = presentNum;
	}

	@Override
	public String toString() {
		return "SimpleInst [instId=" + instId + ", realName=" + realName
				+ ", instName=" + instName + ", totalNum=" + totalNum
				+ ", presentNum=" + presentNum + "]";
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub		
		dest.writeLong(instId);
		dest.writeString(realName);
		dest.writeString(instName);
		dest.writeInt(totalNum);
		dest.writeInt(presentNum);
	}

	public static final Parcelable.Creator<Inst> CREATOR = new Creator<Inst>() {
		@Override
		public Inst[] newArray(int size) {
			return new Inst[size];
		}

		@Override
		public Inst createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			Inst simpleInst=new Inst();
			simpleInst.instId=source.readLong();
			simpleInst.realName=source.readString();
			simpleInst.instName=source.readString();
			simpleInst.totalNum=source.readInt();
			simpleInst.presentNum=source.readInt();			
			return simpleInst;
		}
	};
}
