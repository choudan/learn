/**
 * 
 */
package com.example.yijia.third.bean.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

/**
 * @author Administrator
 * 
 */
public class UserInfo implements Parcelable {
	public long userId;
	public String instName;
	public String realName;
	public int sex;
	public String birthday;
	public int length;
	public int weight;
	public String telephone;
	public String alipay;
	public String email;
	public String headImgUrl;
	public String hospital;
	public String dept;
	public String wechat;
	public String qq;
	public String license;
	public String password;
	public String introduction;
	private String boundServiceType;
	private double weight_index;
	private String relation_msa;

	public String getRelation_msa() {
		return relation_msa;
	}

	public void setRelation_msa(String relation_msa) {
		this.relation_msa = relation_msa;
	}

	public double getWeight_index() {
		return weight_index;
	}

	public void setWeight_index(double weight_index) {
		this.weight_index = weight_index;
	}

	public String getBoundServiceType() {
		return boundServiceType;
	}

	public void setBoundServiceType(String boundServiceType) {
		this.boundServiceType = boundServiceType;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getInstName() {
		return instName;
	}

	public void setInstName(String instName) {
		this.instName = instName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAlipay() {
		return alipay;
	}

	public void setAlipay(String alipay) {
		this.alipay = alipay;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
//		return "UserInfo [userId=" + userId + ", instName=" + instName
//				+ ", realName=" + realName + ", sex=" + sex + ", birthday="
//				+ birthday + ", length=" + length + ", weight=" + weight
//				+ ", telephone=" + telephone + ", alipay=" + alipay
//				+ ", email=" + email + ", headImgUrl=" + headImgUrl
//				+ ", hospital=" + hospital + ", dept=" + dept + ", wechat="
//				+ wechat + ", qq=" + qq + ", license=" + license
//				+ ", password=" + password + ", introduction=" + introduction
//				+ ", boundServiceType=" + boundServiceType + ", weight_index="
//				+ weight_index + ", relation_msa=" + relation_msa + "]";
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(instName);
		dest.writeString(realName);
		dest.writeInt(sex);
		dest.writeString(birthday);
		dest.writeString(telephone);
		dest.writeString(alipay);
		dest.writeString(email);
		dest.writeString(headImgUrl);
		dest.writeString(hospital);
		dest.writeString(dept);
		dest.writeString(wechat);
		dest.writeString(qq);
		dest.writeString(license);
		dest.writeString(password);
		dest.writeString(introduction);
		dest.writeInt(length);
		dest.writeInt(weight);
		dest.writeLong(userId);
		dest.writeString(boundServiceType);
		dest.writeDouble(weight_index);
		dest.writeString(relation_msa);
	}

	public static final Parcelable.Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
		@Override
		public UserInfo[] newArray(int size) {
			return new UserInfo[size];
		}

		@Override
		public UserInfo createFromParcel(Parcel in) {
			UserInfo userInfo = new UserInfo();
			userInfo.instName = in.readString();
			userInfo.realName = in.readString();
			userInfo.sex = in.readInt();
			userInfo.birthday = in.readString();
			userInfo.telephone = in.readString();
			userInfo.alipay = in.readString();
			userInfo.email = in.readString();
			userInfo.headImgUrl = in.readString();
			userInfo.hospital = in.readString();
			userInfo.dept = in.readString();
			userInfo.wechat = in.readString();
			userInfo.qq = in.readString();
			userInfo.license = in.readString();
			userInfo.password = in.readString();
			userInfo.introduction = in.readString();
			userInfo.length = in.readInt();
			userInfo.weight = in.readInt();
			userInfo.userId = in.readLong();
			userInfo.boundServiceType = in.readString();
			userInfo.weight_index = in.readDouble();
			userInfo.relation_msa = in.readString();
			return userInfo;
		}
	};
}
