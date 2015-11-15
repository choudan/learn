/**
 * 
 */
package com.example.yijia.third.bean.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.yijia.third.tool.ParcelUtils;

/**
 * @author Administrator
 * 
 */
public class Questionnaire implements Parcelable{
	public long questionnaireId;
	public String questionnaire;
	public String realName;
	public long contributorId;
	public boolean isMine;
	public boolean icChecked;
	
	public boolean isIcChecked() {
		return icChecked;
	}

	public void setIcChecked(boolean icChecked) {
		this.icChecked = icChecked;
	}

	public boolean isMine() {
		return isMine;
	}

	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}

	public long getContributorId() {
		return contributorId;
	}

	public void setContributorId(long contributorId) {
		this.contributorId = contributorId;
	}

	public long getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(long questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public String getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(String questionnaire) {
		this.questionnaire = questionnaire;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
		
	@Override
	public String toString() {
		return "Questionnaire [questionnaireId=" + questionnaireId
				+ ", questionnaire=" + questionnaire + ", realName=" + realName
				+ ", contributorId=" + contributorId + ", isMine=" + isMine
				+ ", icChecked=" + icChecked + "]";
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
	
	public static final Parcelable.Creator<Questionnaire> CREATOR = ParcelUtils.buildCreator(Questionnaire.class,new String[]{"CREATOR"});


}
