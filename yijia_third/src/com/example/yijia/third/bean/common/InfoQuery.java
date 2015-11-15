package com.example.yijia.third.bean.common;

/**
 * @author 丑旦
 * @date 创建时间：2015/11/13 下午9:04:55
 * @version 1.0
 * @parameter
 * @since
 * @return
 * 
 */
public class InfoQuery {
	public long userId;
	public int role;
	public long queryId;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public long getQueryId() {
		return queryId;
	}

	public void setQueryId(long queryId) {
		this.queryId = queryId;
	}

}
