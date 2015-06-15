package com.tyj.wechat.app.bean;

public class UserInfo {
int	userId;
String	name;
String	gender;
String	age;
String	licenseType;
String	licenseNumber;
int	allowNumber,drive_years;
String	memberPrivileges;
String telephoneNum,email,home_addr_street,identity_no;
public int getUserId() {
	return userId;
}
/**
 * @return the telephoneNum
 */
public String getTelephoneNum() {
	return telephoneNum;
}
/**
 * @return the identity_no
 */
public String getIdentity_no() {
	return identity_no;
}
/**
 * @param identity_no the identity_no to set
 */
public void setIdentity_no(String identity_no) {
	this.identity_no = identity_no;
}
/**
 * @param telephoneNum the telephoneNum to set
 */
public void setTelephoneNum(String telephoneNum) {
	this.telephoneNum = telephoneNum;
}
/**
 * @return the email
 */
public String getEmail() {
	return email;
}
/**
 * @param email the email to set
 */
public void setEmail(String email) {
	this.email = email;
}
/**
 * @return the drive_years
 */
public Integer getDrive_years() {
	return drive_years;
}
/**
 * @param drive_years the drive_years to set
 */
public void setDrive_years(int drive_years) {
	this.drive_years = drive_years;
}
/**
 * @return the home_addr_street
 */
public String getHome_addr_street() {
	return home_addr_street;
}
/**
 * @param home_addr_street the home_addr_street to set
 */
public void setHome_addr_street(String home_addr_street) {
	this.home_addr_street = home_addr_street;
}
public void setUserId(int userId) {
	this.userId = userId;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}
public String getAge() {
	return age;
}
public void setAge(String age) {
	this.age = age;
}
public String getLicenseType() {
	return licenseType;
}
public void setLicenseType(String licenseType) {
	this.licenseType = licenseType;
}
public String getLicenseNumber() {
	return licenseNumber;
}
public void setLicenseNumber(String licenseNumber) {
	this.licenseNumber = licenseNumber;
}
public Integer getAllowNumber() {
	return allowNumber;
}
public void setAllowNumber(int allowNumber) {
	this.allowNumber = allowNumber;
}
public String getMemberPrivileges() {
	return memberPrivileges;
}
public void setMemberPrivileges(String memberPrivileges) {
	this.memberPrivileges = memberPrivileges;
}

}
