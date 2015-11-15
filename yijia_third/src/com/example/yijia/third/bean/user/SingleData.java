package com.example.yijia.third.bean.user;

/**
 * @author 丑旦
 * @date 创建时间：2015/10/31 下午2:10:42
 * @version 1.0
 * @parameter
 * @since
 * @return
 * 
 */
public class SingleData {
	public Number avgValue;
	public Number dayValue;
	public String date;

	public Number getAvgValue() {
		return avgValue;
	}

	public void setAvgValue(Number avgValue) {
		this.avgValue = avgValue;
	}

	public Number getDayValue() {
		return dayValue;
	}

	public void setDayValue(Number dayValue) {
		this.dayValue = dayValue;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "SingleData [avgValue=" + avgValue + ", dayValue=" + dayValue
				+ ", date=" + date + "]";
	}

}
