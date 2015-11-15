package com.example.yijia.third.bean.admin;


/**
 * @author 丑旦
 * @date 创建时间：2015-8-22 下午3:20:59
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class Staff {
	public String date;
	public int msaNum;
	public int saNum;
	public int presentUserNum;
	public int totalUserNum;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getMsaNum() {
		return msaNum;
	}

	public void setMsaNum(int msaNum) {
		this.msaNum = msaNum;
	}

	public int getSaNum() {
		return saNum;
	}

	public void setSaNum(int saNum) {
		this.saNum = saNum;
	}

	public int getPresentUserNum() {
		return presentUserNum;
	}

	public void setPresentUserNum(int presentUserNum) {
		this.presentUserNum = presentUserNum;
	}

	public int getTotalUserNum() {
		return totalUserNum;
	}

	public void setTotalUserNum(int totalUserNum) {
		this.totalUserNum = totalUserNum;
	}

	@Override
	public String toString() {
		return "Staff [date=" + date + ", msaNum=" + msaNum + ", saNum="
				+ saNum + ", presentUserNum=" + presentUserNum
				+ ", totalUserNum=" + totalUserNum + "]";
	}

}
