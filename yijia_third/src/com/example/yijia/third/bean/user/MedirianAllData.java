package com.example.yijia.third.bean.user;

import java.util.List;


/**
 * @author ��
 * @date ����ʱ�䣺2015/11/8 ����4:05:27
 * @version 1.0
 * @parameter
 * @since
 * @return
 * 
 */
public class MedirianAllData {
	public Number beansCount;
	public String nextStartDate;
	public List<AllData> beans;

	public Number getBeansCount() {
		return beansCount;
	}

	public void setBeansCount(Number beansCount) {
		this.beansCount = beansCount;
	}

	public String getNextStartDate() {
		return nextStartDate;
	}

	public void setNextStartDate(String nextStartDate) {
		this.nextStartDate = nextStartDate;
	}

	public List<AllData> getBeans() {
		return beans;
	}

	public void setBeans(List<AllData> beans) {
		this.beans = beans;
	}

	@Override
	public String toString() {
		return "MedirianAllData [beansCount=" + beansCount + ", nextStartDate="
				+ nextStartDate + ", beans=" + beans + "]";
	}

}
