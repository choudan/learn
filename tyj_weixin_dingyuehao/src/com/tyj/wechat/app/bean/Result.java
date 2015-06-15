package com.tyj.wechat.app.bean;

public class Result {

	private int statusCode;
	private int valid;
	private int dataType;
	private Object data;
	
	/**
	 * @param statusCode
	 * @param valid
	 * @param dataType
	 * @param data
	 */
	public String toString() {
		return "生成的String>>>>>>>>statusCode:"+statusCode+"valid:"+valid+"dataType:"+dataType+"data:"+data;	
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public int getValid() {
		return valid;
	}
	public void setValid(int valid) {
		this.valid = valid;
	}
	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	public Object getdata() {
		return data;
	}
	public void setdata(Object data) {
		this.data = data;
	}	
}
