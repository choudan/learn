package com.example.yijia.third.bean.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author  丑旦 
 * @date 创建时间：2015-8-22 下午3:20:59 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class User implements Parcelable {
	public long userId;
	public String realName;
	public String logoutDate;
	
	public String getLogoutDate() {
		return logoutDate;
	}

	public void setLogoutDate(String logoutDate) {
		this.logoutDate = logoutDate;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", realName=" + realName
				+ ", logoutDate=" + logoutDate + "]";
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeLong(userId);
		dest.writeString(realName);		
		dest.writeString(logoutDate);		
	}
	
	public final static Parcelable.Creator<User> CREATOR=new Parcelable.Creator<User>(){

		@Override
		public User createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			User user=new User();
			user.userId=source.readLong();
			user.realName=source.readString();
			user.logoutDate=source.readString();
			return user;
		}

		@Override
		public User[] newArray(int size) {
			// TODO Auto-generated method stub
			return new User[size];
		}
		
	};
}
