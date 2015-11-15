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
public class Service implements Parcelable{
	public long serviceId;
	public String types;
	public String serviceName;
	public int price;
	public int msaBonus;
	public int saBouns;
	public int adminBouns;
	public String introduction;
	public String msaList;

	public long getServiceId() {
		return serviceId;
	}

	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getMsaBonus() {
		return msaBonus;
	}

	public void setMsaBonus(int msaBonus) {
		this.msaBonus = msaBonus;
	}

	public int getSaBouns() {
		return saBouns;
	}

	public void setSaBouns(int saBouns) {
		this.saBouns = saBouns;
	}

	public int getAdminBouns() {
		return adminBouns;
	}

	public void setAdminBouns(int adminBouns) {
		this.adminBouns = adminBouns;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getMsaList() {
		return msaList;
	}

	public void setMsaList(String msaList) {
		this.msaList = msaList;
	}

	@Override
	public String toString() {
		return "Service [serviceId=" + serviceId + ", types=" + types
				+ ", serviceName=" + serviceName + ", price=" + price
				+ ", msaBonus=" + msaBonus + ", saBouns=" + saBouns
				+ ", adminBouns=" + adminBouns + ", introduction="
				+ introduction + ", msaList=" + msaList + "]";
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(types);
		dest.writeString(serviceName);
		dest.writeString(introduction);
		dest.writeString(msaList);
		dest.writeInt(price);
		dest.writeInt(msaBonus);
		dest.writeInt(saBouns);
		dest.writeInt(adminBouns);
		dest.writeLong(serviceId);		
	}
	
	public static final Parcelable.Creator<Service> CREATOR = new Creator<Service>() {
		@Override
		public Service[] newArray(int size) {
			return new Service[size];
		}
		@Override
		public Service createFromParcel(Parcel in) {
			Service service=new Service();
			service.types=in.readString();
			service.serviceName=in.readString();
			service.introduction=in.readString();
			service.msaList=in.readString();
			
			service.price=in.readInt();
			service.msaBonus=in.readInt();
			service.saBouns=in.readInt();
			service.adminBouns=in.readInt();
			service.serviceId=in.readLong();
			return service;
		}
	};
}
