package com.example.yijia.third.bean.common;

import com.example.yijia.third.bean.user.UserInfo;

/**
 * @author ��
 * @date ����ʱ�䣺2015/11/13 ����1:50:32
 * @version 1.0
 * @parameter
 * @since
 * @return
 * 
 */
public class InfoEdit {
	public Number userId;
	public Number infoType;
	public UserInfo userInfo;

	public Number getUserId() {
		return userId;
	}

	public void setUserId(Number userId) {
		this.userId = userId;
	}

	public Number getInfoType() {
		return infoType;
	}

	public void setInfoType(Number infoType) {
		this.infoType = infoType;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

}
