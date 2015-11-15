/**
 * 
 */
package com.example.yijia.third.bean.common;

/**
 * @author Administrator
 * 
 */
public class DeleteQuestionnaire {
	public long userId;
	public String questionnaireIds;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getQuestionnaireIds() {
		return questionnaireIds;
	}

	public void setQuestionnaireIds(String questionnaireIds) {
		this.questionnaireIds = questionnaireIds;
	}

	@Override
	public String toString() {
		return "DeleteQuestionnaire [userId=" + userId + ", questionnaireIds="
				+ questionnaireIds + "]";
	}

}
