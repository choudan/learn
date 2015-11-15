/**
 * 
 */
package com.example.yijia.third.bean.sa;

import com.example.yijia.third.tool.ParcelUtils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Administrator
 *
 */
public class Sa implements Parcelable{
	private long id;
	private String realName;
	private String hospitalDept;
	private int presentNum;
	
	public int getPresentNum() {
		return presentNum;
	}
	public void setPresentNum(int presentNum) {
		this.presentNum = presentNum;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String name) {
		this.realName = name;
	}
	public String getHospitalDept() {
		return hospitalDept;
	}
	public void setHospitalDept(String hospitalDept) {
		this.hospitalDept = hospitalDept;
	}
		
	@Override
	public String toString() {
		return "MsaSa [id=" + id + ", realName=" + realName + ", hospitalDept="
				+ hospitalDept + ", presentNum=" + presentNum + "]";
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
	
	public static final Parcelable.Creator<Sa> CREATOR = ParcelUtils.buildCreator(Sa.class,new String[]{"CREATOR"});

//	@Override
//	public void writeToParcel(Parcel dest, int flags) {
//		// TODO Auto-generated method stub
//		dest.writeLong(id);
//		dest.writeString(realName);
//		dest.writeString(hospitalDept);
//		dest.writeInt(presentNum);	
//	}	
//	
//	public final static Parcelable.Creator<MsaSa> CREATOR=new Creator<MsaSa>(){
//
//		@Override
//		public MsaSa createFromParcel(Parcel source) {
//			// TODO Auto-generated method stub
//			MsaSa msaSa=new MsaSa();
//			msaSa.id=source.readLong();
//			msaSa.realName=source.readString();
//			msaSa.hospitalDept=source.readString();
//			msaSa.presentNum=source.readInt();		
//			return msaSa;
//		}
//
//		@Override
//		public MsaSa[] newArray(int size) {
//			// TODO Auto-generated method stub
//			return new MsaSa[size];
//		}
//		
//	};
	
}
