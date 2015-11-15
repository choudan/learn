/**
 * 
 */
package com.example.yijia.third.bean.common;

/**
 * @author Administrator
 * 
 */
public class SendQuestionnaire {
	public long userId;
	public long receiveId;
	public String questionnaireIds;
	public String sendTime;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(long receiveId) {
		this.receiveId = receiveId;
	}

	public String getQuestionnaireIds() {
		return questionnaireIds;
	}

	public void setQuestionnaireIds(String questionnaireIds) {
		this.questionnaireIds = questionnaireIds;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	@Override
	public String toString() {
		return "SendQuestionnaire [userId=" + userId + ", receiveId="
				+ receiveId + ", questionnaireIds=" + questionnaireIds
				+ ", sendTime=" + sendTime + "]";
	}

}
