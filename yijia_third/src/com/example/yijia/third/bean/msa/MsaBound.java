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
public class MsaBound implements Parcelable {
	private long id;
	private String realName;
	private String hospital;
	private String sex;
	private String birthday;
	private String dept;
	private String introduction;

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
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

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	
	}
	
	@Override
	public String toString() {
		return "MsaBound [id=" + id + ", realName=" + realName + ", hospital="
				+ hospital + ", sex=" + sex + ", birthday=" + birthday
				+ ", dept=" + dept + ", introduction=" + introduction + "]";
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
		dest.writeString(hospital);
		dest.writeString(sex);
		dest.writeString(birthday);
		dest.writeString(dept);
		dest.writeString(introduction);
	}

	// @Override
	// public void writeToParcel(Parcel dest, int flags) {
	// // TODO Auto-generated method stub
	// ParcelUtils.writeToParce(dest, this);
	// }

	// public static final Parcelable.Creator<Msa> CREATOR =
	// ParcelUtils.buildCreator(Msa.class);

	public final static Parcelable.Creator<MsaBound> CREATOR = new Parcelable.Creator<MsaBound>() {

		@Override
		public MsaBound createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			MsaBound msa = new MsaBound();
			msa.id = source.readLong();
			msa.realName = source.readString();
			msa.hospital = source.readString();
			msa.sex = source.readString();
			msa.birthday = source.readString();
			msa.dept = source.readString();
			msa.introduction = source.readString();
			return msa;
		}

		@Override
		public MsaBound[] newArray(int size) {
			// TODO Auto-generated method stub
			return new MsaBound[size];
		}

	};

}
