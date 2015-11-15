package com.example.yijia.third.bean.user;

/**
 * @author 丑旦
 * @date 创建时间：2015/11/13 上午9:54:07
 * @version 1.0
 * @parameter
 * @since
 * @return
 * 
 */
public class UserRegister {
	public String devId;
	public UserInfo userInfo;
	public int registerType;

	public String getDevId() {
		return devId;
	}

	public void setDevId(String devId) {
		this.devId = devId;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public int getRegisterType() {
		return registerType;
	}

	public void setRegisterType(int registerType) {
		this.registerType = registerType;
	}

	@Override
	public String toString() {
		return "Register [devId=" + devId + ", userInfo=" + userInfo
				+ ", registerType=" + registerType + "]";
	}
}
