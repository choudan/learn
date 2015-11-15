package com.example.yijia.third.bean.user;

import java.util.List;


/**
 * @author 丑旦
 * @date 创建时间：2015/11/8 下午4:09:13
 * @version 1.0
 * @parameter
 * @since
 * @return
 * 
 */
public class AllData {
	public Number avgLine;
	public String date;
	public List<MeridianPoints> meridians;

	public Number getAvgLine() {
		return avgLine;
	}

	public void setAvgLine(Number avgLine) {
		this.avgLine = avgLine;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<MeridianPoints> getMeridians() {
		return meridians;
	}

	public void setMeridians(List<MeridianPoints> meridians) {
		this.meridians = meridians;
	}

	@Override
	public String toString() {
		return "AllData [avgLine=" + avgLine + ", date=" + date
				+ ", meridians=" + meridians + "]";
	}

}
