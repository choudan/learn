/**
 * 
 */
package com.example.yijia.third.bean.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.yijia.third.tool.ParcelUtils;

/**
 * @author Administrator 问卷记录实体
 */
public class RecordBean implements Parcelable{

	public long questionnaireId;
	public String questionerName;
	public long questionerId;
	public long answererId;
	public String answererName;
	public String question;
	public String answer;
	private int isAnswered;
	
	public int getIsAnswered() {
		return isAnswered;
	}

	public void setIsAnswered(int isAnswered) {
		this.isAnswered = isAnswered;
	}

	public long getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(long questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public String getQuestionerName() {
		return questionerName;
	}

	public void setQuestionerName(String questionerName) {
		this.questionerName = questionerName;
	}

	public long getQuestionerId() {
		return questionerId;
	}

	public void setQuestionerId(long questionerId) {
		this.questionerId = questionerId;
	}

	public long getAnswererId() {
		return answererId;
	}

	public void setAnswererId(long answererId) {
		this.answererId = answererId;
	}

	public String getAnswererName() {
		return answererName;
	}

	public void setAnswererName(String answererName) {
		this.answererName = answererName;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
		
	@Override
	public String toString() {
		return "RecordBean [questionnaireId=" + questionnaireId
				+ ", questionerName=" + questionerName + ", questionerId="
				+ questionerId + ", answererId=" + answererId
				+ ", answererName=" + answererName + ", question=" + question
				+ ", answer=" + answer + ", isAnswered=" + isAnswered + "]";
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub		
		ParcelUtils.writeToParce(dest, this,new String[]{"CREATOR"});
	}
	
	public static final Parcelable.Creator<RecordBean> CREATOR = ParcelUtils.buildCreator(RecordBean.class,new String[]{"CREATOR"});


}
