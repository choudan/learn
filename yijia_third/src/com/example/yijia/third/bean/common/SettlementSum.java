/**
 * 
 */
package com.example.yijia.third.bean.common;

/**
 * @author Administrator
 * 
 */
public class SettlementSum {
	public String settlementMonth;
	public String totalSum;

	public String getSettlementMonth() {
		return settlementMonth;
	}

	public void setSettlementMonth(String settlementMonth) {
		this.settlementMonth = settlementMonth;
	}

	public String getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(String totalSum) {
		this.totalSum = totalSum;
	}

	@Override
	public String toString() {
		return "SettlementSum [settlementMonth=" + settlementMonth
				+ ", totalSum=" + totalSum + "]";
	}

}
