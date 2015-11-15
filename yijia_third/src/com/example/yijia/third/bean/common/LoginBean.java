package com.example.yijia.third.bean.common;

public class LoginBean {
	long roleId;
	int role;
	int loadState;
	int accountState;
	int isPaid;
	int unfinishedSurvey;
	int orderState;
	String realName;

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public int getLoadState() {
		return loadState;
	}

	public void setLoadState(int loadState) {
		this.loadState = loadState;
	}

	public int getAccountState() {
		return accountState;
	}

	public void setAccountState(int accountState) {
		this.accountState = accountState;
	}

	public int getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(int isPaid) {
		this.isPaid = isPaid;
	}

	public int getUnfinishedSurvey() {
		return unfinishedSurvey;
	}

	public void setUnfinishedSurvey(int unfinishedSurvey) {
		this.unfinishedSurvey = unfinishedSurvey;
	}

	public int getOrderState() {
		return orderState;
	}

	public void setOrderState(int orderState) {
		this.orderState = orderState;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Override
	public String toString() {
		return "LoginBean [roleId=" + roleId + ", role=" + role
				+ ", loadState=" + loadState + ", accountState=" + accountState
				+ ", isPaid=" + isPaid + ", unfinishedSurvey="
				+ unfinishedSurvey + ", orderState=" + orderState
				+ ", realName=" + realName + "]";
	}

}
