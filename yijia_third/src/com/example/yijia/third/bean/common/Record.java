/**
 * 
 */
package com.example.yijia.third.bean.common;

import java.util.ArrayList;


/**
 * @author Administrator 问卷记录实体
 */
public class Record {
	public long recordId;
	public String sendTime;
	public int isFinished;
	public ArrayList<RecordBean> list;

	public long getRecordId() {
		return recordId;
	}

	public void setRecordId(long recordId) {
		this.recordId = recordId;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public int getIsFinished() {
		return isFinished;
	}

	public void setIsFinished(int isFinished) {
		this.isFinished = isFinished;
	}

	public ArrayList<RecordBean> getList() {
		return list;
	}

	public void setList(ArrayList<RecordBean> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "Record [recordId=" + recordId + ", sendTime=" + sendTime
				+ ", isFinished=" + isFinished + ", list=" + list + "]";
	}

}
