package com.example.yijia.third.bean.common;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.yijia.third.tool.ParcelUtils;

/**
 * 个人信息，保存本地
 * @author  丑旦 
 * @date 创建时间：2015-10-1 下午5:55:06
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 *
 */
public class ClientUser implements Parcelable{
	//角色id，角色，真实名字
	private String roleId;
	private int role;
	private String realName = "";
	//账号
	private String appToken;
	private String password;
	//性别
	private int sex;
	private String birthday;
	//版本号
	private int pVersion;
		
	public ClientUser(String appToken) {
		super();
		this.appToken = appToken;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getAppToken() {
		return appToken;
	}

	public void setAppToken(String appToken) {
		this.appToken = appToken;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public int getpVersion() {
		return pVersion;
	}

	public void setpVersion(int pVersion) {
		this.pVersion = pVersion;
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
	
	public static final Parcelable.Creator<ClientUser> CREATOR = ParcelUtils.buildCreator(ClientUser.class,new String[]{"CREATOR"});
	
	@Override
	public String toString() {	
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("roleId" , roleId);
			jsonObject.put("role" , role);
			jsonObject.put("realName" , realName);
			jsonObject.put("appToken" , appToken);
			jsonObject.put("password" , password);
			jsonObject.put("sex" , sex);
			jsonObject.put("birthday" , birthday);
			jsonObject.put("pVersion" , pVersion);
			return jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "ClientUser [roleId=" + roleId + ", realName=" + realName
				+ ", appToken=" + appToken + ", password=" + password
				+ ", sex=" + sex + ", birthday=" + birthday + ", pVersion="
				+ pVersion + "]";
	}
	 
	public ClientUser from(String input) {
		JSONObject object = null;
		try {
			object = new JSONObject(input);
			if (object.has("roleId")) {
				this.roleId = object.getString("roleId");
			}
			if(object.has("role")){				
				this.role = object.getInt("role");
			}
			if (object.has("realName")) {
				this.realName = object.getString("realName");
			}
			if (object.has("appToken")) {
				this.appToken = object.getString("appToken");
			}
			if (object.has("password")) {
				this.password = object.getString("password");
			}
			if (object.has("sex")) {
				this.sex = object.getInt("sex");
			}
			if (object.has("birthday")) {
				this.birthday = object.getString("birthday");
			}
			if (object.has("pVersion")) {
				this.pVersion = object.getInt("pVersion");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}
}
