package com.example.yijia.third.bean.user;

import java.util.List;


/**
 * @author 丑旦
 * @date 创建时间：2015/10/28 下午10:55:19
 * @version 1.0
 * @parameter
 * @since
 * @return
 * 
 */
public class MedirianSingleData {
	public Number beansCount;
	public Number meridianId;
	public String nextStartDate;
	public List<SingleData> beans;

	public Number getBeansCount() {
		return beansCount;
	}

	public void setBeansCount(Number beansCount) {
		this.beansCount = beansCount;
	}

	public Number getMeridianId() {
		return meridianId;
	}

	public void setMeridianId(Number meridianId) {
		this.meridianId = meridianId;
	}

	public String getNextStartDate() {
		return nextStartDate;
	}

	public void setNextStartDate(String nextStartDate) {
		this.nextStartDate = nextStartDate;
	}

	public List<SingleData> getBeans() {
		return beans;
	}

	public void setBeans(List<SingleData> beans) {
		this.beans = beans;
	}

	@Override
	public String toString() {
		return "MedirianSingleData [beansCount=" + beansCount + ", meridianId="
				+ meridianId + ", nextStartDate=" + nextStartDate + ", beans="
				+ beans + "]";
	}

//	@Override
//	public int describeContents() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//	
//	@Override
//	public void writeToParcel(Parcel dest, int flags) {
//		// TODO Auto-generated method stub		
//		ParcelUtils.writeToParce(dest, this,new String[]{"CREATOR"});
//	}
//	
//	public static final Parcelable.Creator<MedirianSingleData> CREATOR = ParcelUtils.buildCreator(MedirianSingleData.class,new String[]{"CREATOR"});

}
