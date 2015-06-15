package com.tyj.wechat.app.bean;

public class UserDataMyIVI {
	int isSignIn;
	int points;
	int pointsIncrease;
	int validity;
	Object memberKind;
	Object deviceId;

	public int getIsSignIn() {
		return isSignIn;
	}

	public void setIsSignIn(int isSignIn) {
		this.isSignIn = isSignIn;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getPointsIncrease() {
		return pointsIncrease;
	}

	public void setPointsIncrease(int pointsIncrease) {
		this.pointsIncrease = pointsIncrease;
	}

	public int getValidity() {
		return validity;
	}

	public void setValidity(int validity) {
		this.validity = validity;
	}

	public Object getMemberKind() {
		return memberKind;
	}

	public void setMemberKind(Object memberKind) {
		this.memberKind = memberKind;
	}

	public Object getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Object deviceId) {
		this.deviceId = deviceId;
	}

}
