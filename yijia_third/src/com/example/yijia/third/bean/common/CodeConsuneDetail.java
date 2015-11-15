/**
 * 
 */
package com.example.yijia.third.bean.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Administrator
 * 
 */
public class CodeConsuneDetail implements Parcelable {
	public int codeType;
	public String code;
	public String codeConsumer;
	public String consumeDate;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getCodeType() {
		return codeType;
	}

	public void setCodeType(int codeType) {
		this.codeType = codeType;
	}

	public String getCodeConsumer() {
		return codeConsumer;
	}

	public void setCodeConsumer(String codeConsumer) {
		this.codeConsumer = codeConsumer;
	}

	public String getConsumeDate() {
		return consumeDate;
	}

	public void setConsumeDate(String date) {
		this.consumeDate = date;
	}

	@Override
	public String toString() {
		return "CodeConsuneDetail [code=" + code + ", codeType=" + codeType
				+ ", codeConsumer=" + codeConsumer + ", date=" + consumeDate + "]";
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(code);
		dest.writeString(codeConsumer);
		dest.writeString(consumeDate);
		dest.writeInt(codeType);
	}

	public static final Parcelable.Creator<CodeConsuneDetail> CREATOR = new Creator<CodeConsuneDetail>() {
		@Override
		public CodeConsuneDetail[] newArray(int size) {
			return new CodeConsuneDetail[size];
		}

		@Override
		public CodeConsuneDetail createFromParcel(Parcel in) {
			CodeConsuneDetail codeConsuneDetail = new CodeConsuneDetail();
			codeConsuneDetail.code = in.readString();
			codeConsuneDetail.codeConsumer = in.readString();
			codeConsuneDetail.consumeDate = in.readString();
			codeConsuneDetail.codeType = in.readInt();
			return codeConsuneDetail;
		}
	};
}
