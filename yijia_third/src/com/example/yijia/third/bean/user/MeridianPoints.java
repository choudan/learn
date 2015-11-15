package com.example.yijia.third.bean.user;

/**
 * @author 丑旦
 * @date 创建时间：2015/11/8 下午4:17:31
 * @version 1.0
 * @parameter
 * @since
 * @return
 * 
 */
public class MeridianPoints {
	public Number leftValue1;
	public Number leftValue2;
	public Number leftValue3;
	public Number rightValue1;
	public Number rightValue2;
	public Number rightValue3;
	public Number meridianId;

	public Number getLeftValue1() {
		return leftValue1;
	}

	public void setLeftValue1(Number leftValue1) {
		this.leftValue1 = leftValue1;
	}

	public Number getLeftValue2() {
		return leftValue2;
	}

	public void setLeftValue2(Number leftValue2) {
		this.leftValue2 = leftValue2;
	}

	public Number getLeftValue3() {
		return leftValue3;
	}

	public void setLeftValue3(Number leftValue3) {
		this.leftValue3 = leftValue3;
	}

	public Number getRightValue1() {
		return rightValue1;
	}

	public void setRightValue1(Number rightValue1) {
		this.rightValue1 = rightValue1;
	}

	public Number getRightValue2() {
		return rightValue2;
	}

	public void setRightValue2(Number rightValue2) {
		this.rightValue2 = rightValue2;
	}

	public Number getRightValue3() {
		return rightValue3;
	}

	public void setRightValue3(Number rightValue3) {
		this.rightValue3 = rightValue3;
	}

	public Number getMeridianId() {
		return meridianId;
	}

	public void setMeridianId(Number meridianId) {
		this.meridianId = meridianId;
	}

	@Override
	public String toString() {
		return "MeridianPoints [leftValue1=" + leftValue1 + ", leftValue2="
				+ leftValue2 + ", leftValue3=" + leftValue3 + ", rightValue1="
				+ rightValue1 + ", rightValue2=" + rightValue2
				+ ", rightValue3=" + rightValue3 + ", meridianId=" + meridianId
				+ "]";
	}

}
