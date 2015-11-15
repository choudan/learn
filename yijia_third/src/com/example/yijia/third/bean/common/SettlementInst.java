package com.example.yijia.third.bean.common;

public class SettlementInst {
	public String instName;
	public String date;
	public int totalNum;
	public int presentNum;

	public String getInstName() {
		return instName;
	}

	public void setInstName(String instName) {
		this.instName = instName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getPresentNum() {
		return presentNum;
	}

	public void setPresentNum(int presentNum) {
		this.presentNum = presentNum;
	}

	@Override
	public String toString() {
		return "SettlementInst [instName=" + instName + ", date=" + date
				+ ", totalNum=" + totalNum + ", presentNum=" + presentNum + "]";
	}

}
