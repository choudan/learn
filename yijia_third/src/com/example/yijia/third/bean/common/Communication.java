/**
 * 
 */
package com.example.yijia.third.bean.common;

import java.util.ArrayList;

/**
 * @author Administrator
 * 
 */
public class Communication {
	private long userId;
	private long saId;
	private int type;
	private String realName;
	private String saName;
	private String headImgUrl;
	private String text;
	private String date;
	private ArrayList<String> picPath;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getSaId() {
		return saId;
	}

	public void setSaId(long saId) {
		this.saId = saId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getSaName() {
		return saName;
	}

	public void setSaName(String saName) {
		this.saName = saName;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public ArrayList<String> getPicPath() {
		return picPath;
	}

	public void setPicPath(ArrayList<String> picPath) {
		this.picPath = picPath;
	}

	@Override
	public String toString() {
		return "Communication [userId=" + userId + ", saId=" + saId + ", type="
				+ type + ", realName=" + realName + ", saName=" + saName
				+ ", headImgUrl=" + headImgUrl + ", text=" + text + ", date="
				+ date + ", picPath=" + picPath + "]";
	}

}
