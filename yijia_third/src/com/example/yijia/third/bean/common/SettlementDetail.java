package com.example.yijia.third.bean.common;

public class SettlementDetail {
	public long msaId;
	public String realName;
	public int totalIncome;
	public int totalNum;
	public int teamNum;
	public String boundServiceType;

	public String getBoundServiceType() {
		return boundServiceType;
	}

	public void setBoundServiceType(String boundServiceType) {
		this.boundServiceType = boundServiceType;
	}

	public long getMsaId() {
		return msaId;
	}

	public void setMsaId(long msaId) {
		this.msaId = msaId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public int getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(int totalIncome) {
		this.totalIncome = totalIncome;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getTeamNum() {
		return teamNum;
	}

	public void setTeamNum(int teamNum) {
		this.teamNum = teamNum;
	}

	@Override
	public String toString() {
		return "SettlementDetail [msaId=" + msaId + ", realName=" + realName
				+ ", totalIncome=" + totalIncome + ", totalNum=" + totalNum
				+ ", teamNum=" + teamNum + ", boundServiceType="
				+ boundServiceType + "]";
	}

}
