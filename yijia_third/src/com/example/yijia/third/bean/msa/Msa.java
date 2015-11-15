package com.example.yijia.third.bean.msa;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author 丑旦
 * @date 创建时间：2015-8-22 下午9:09:34
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class Msa implements Parcelable {
	private long id;
	private String realName;
	private String hospitalDept;
	private String boundServiceType;
	private int totalNum;
	private int presentNum;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getHospitalDept() {
		return hospitalDept;
	}

	public void setHospitalDept(String hospitalDept) {
		this.hospitalDept = hospitalDept;
	}

	public String getBoundServiceType() {
		return boundServiceType;
	}

	public void setBoundServiceType(String boundServiceType) {
		this.boundServiceType = boundServiceType;
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
		return "Msa [id=" + id + ", realName=" + realName + ", hospitalDept="
				+ hospitalDept + ", boundServiceType=" + boundServiceType
				+ ", totalNum=" + totalNum + ", presentNum=" + presentNum + "]";
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeLong(id);
		dest.writeString(realName);
		dest.writeString(hospitalDept);
		dest.writeString(boundServiceType);
		dest.writeInt(totalNum);
		dest.writeInt(presentNum);
	}
	
//	@Override
//	public void writeToParcel(Parcel dest, int flags) {
//		// TODO Auto-generated method stub		
//		ParcelUtils.writeToParce(dest, this);
//	}
	
//	public static final Parcelable.Creator<Msa> CREATOR = ParcelUtils.buildCreator(Msa.class);

	public final static Parcelable.Creator<Msa> CREATOR = new Parcelable.Creator<Msa>() {

		@Override
		public Msa createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			Msa msa = new Msa();
			msa.id = source.readLong();
			msa.realName = source.readString();
			msa.hospitalDept = source.readString();
			msa.boundServiceType = source.readString();
			msa.totalNum = source.readInt();
			msa.presentNum = source.readInt();
			return msa;
		}

		@Override
		public Msa[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Msa[size];
		}

	};

}
