package com.example.yijia.third.bean.user;

/**
 * @author 丑旦
 * @date 创建时间：2015/10/28 下午10:55:19
 * @version 1.0
 * @parameter
 * @since
 * @return
 * 
 */
public class MedirianAll {
	public Number userId;
	public Number count;
	public String startDate;
	public Number days;

	public Number getUserId() {
		return userId;
	}

	public void setUserId(Number userId) {
		this.userId = userId;
	}

	public Number getCount() {
		return count;
	}

	public void setCount(Number count) {
		this.count = count;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public Number getDays() {
		return days;
	}

	public void setDays(Number days) {
		this.days = days;
	}

	@Override
	public String toString() {
		return "MedirianAll [userId=" + userId + ", count=" + count
				+ ", startDate=" + startDate + ", days=" + days + "]";
	}

}
