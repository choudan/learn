package com.tyj.wechat.dingyuehao.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import cn.zlg.util.StringUtils;

import com.jfinal.core.Controller;
import com.tyj.wechat.dingyuehao.model.ApplyInstallInfos;
import com.tyj.wechat.dingyuehao.model.ApplyUserInfos;

public class QueryController extends Controller {

	/**
	 * 
wechatOpenid
realName
gender
phone
email
identityNo
carType
driverLicenseType
carNo
driveYears
homeAddrProvince
homeAddrCity
homeAddrDistrict
homeAddStreet
receiveAddrProvice
receiveAddrCity
receiveAddrDistrict
receiveAddrStreet
applyTime

 */
	String appliedSessionKey = "appliedDevice";
	
	public void index(){
		System.out.println("hahaha");
		render("/applyDevice.html");
	}
	
	public void toApplyDevice(){
		render("/infor.html");
	}
	
	public void applyDevice(){
		String wechatOpenid = getPara("wechatOpenid");
		String realName = getPara("realName");
		String gender = getPara("gender");
		String phone = getPara("phone");
		String email = getPara("email");
		String identityNo = getPara("identityNo");
		String carType = getPara("carType");
		String driverLicenseType = getPara("driverLicenseType");
		String carNo = getPara("carNo");
		String driveYears = getPara("driveYears");
		String homeAddrProvince = getPara("homeAddrProvince");
		String homeAddrCity = getPara("homeAddrCity");
		String homeAddrDistrict = getPara("homeAddrDistrict");
		String homeAddrStreet = getPara("homeAddrStreet");
		String receiveAddrProvice = getPara("receiveAddrProvice");
		String receiveAddrCity = getPara("receiveAddrCity");
		String receiveAddrDistrict = getPara("receiveAddrDistrict");
		String receiveAddrStreet = getPara("receiveAddrStreet");


		
		if( StringUtils.nullOrEmpty(realName) || StringUtils.nullOrEmpty(gender) 
				|| StringUtils.nullOrEmpty(phone) || StringUtils.nullOrEmpty(email) 
				|| StringUtils.nullOrEmpty(identityNo) || StringUtils.nullOrEmpty(carType) 
				|| StringUtils.nullOrEmpty(driverLicenseType) || StringUtils.nullOrEmpty(carNo) 
				|| StringUtils.nullOrEmpty(driveYears) || StringUtils.nullOrEmpty(homeAddrProvince) 
				|| StringUtils.nullOrEmpty(homeAddrCity) || StringUtils.nullOrEmpty(homeAddrDistrict) 
				|| StringUtils.nullOrEmpty(homeAddrStreet) || StringUtils.nullOrEmpty(receiveAddrProvice) 
				|| StringUtils.nullOrEmpty(receiveAddrCity) || StringUtils.nullOrEmpty(receiveAddrDistrict) 
				|| StringUtils.nullOrEmpty(receiveAddrStreet)){
			this.keepPara();
			this.setAttr("msg", "您填写的信息不完全，请填写完全带*的信息");
			render("/infor.html");
			return;
		}
		
		ApplyUserInfos aui = new ApplyUserInfos();
		aui.set("wechat_openid",wechatOpenid);
		aui.set("real_name",realName);
		aui.set("gender",gender);
		aui.set("phone",phone);
		aui.set("email",email);
		aui.set("identity_no",identityNo);
		aui.set("car_type",carType);
		aui.set("driver_license_type",driverLicenseType);
		aui.set("car_no",carNo);
		aui.set("drive_years",driveYears);
		aui.set("home_addr_province",homeAddrProvince);
		aui.set("home_addr_city",homeAddrCity);
		aui.set("home_addr_district",homeAddrDistrict);
		aui.set("home_addr_street",homeAddrStreet);
		aui.set("receive_addr_provice",receiveAddrProvice);
		aui.set("receive_addr_city",receiveAddrCity);
		aui.set("receive_addr_district",receiveAddrDistrict);
		aui.set("receive_addr_street",receiveAddrStreet);
		aui.set("apply_time",new Timestamp(System.currentTimeMillis()));
		aui.save();
		ApplyUserInfos ui = ApplyUserInfos.dao.findFirst("select id from apply_user_infos where car_no=? order by apply_time desc limit 1",carNo);
		this.setSessionAttr("applyInfoId", ui.getInt("id"));
		this.setSessionAttr("appliedDevice", true);
		this.setSessionAttr("wechatOpenid", wechatOpenid);
		this.setSessionAttr("carNo", carNo);
		setAttr("applyDevice",true);
		render("/ok.html");
	}
	
	public void toApplyToInstall(){
		Boolean appliedDevice = this.getSessionAttr(appliedSessionKey);
		if(!appliedDevice){
			this.setAttr("msg", "请您先预约安装设备");
			render("/applyDevice.html");
			return;
		}
		render("/install.html");
	}
	public void applyToInstall(){
		Boolean appliedDevice = this.getSessionAttr(appliedSessionKey);
		if(!appliedDevice){
			this.setAttr("msg", "请您先预约安装设备");
			render("/applyDevice.html");
			return;
		}
		int applyInfoId = getSessionAttr("applyInfoId");
		String installDate = getPara("installDate");
		int installPlaceId = getParaToInt("installPlaceId");
		ApplyInstallInfos aii = new ApplyInstallInfos();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			aii.set("user_id", applyInfoId).set("apply_install_time",sdf.parse(installDate))
				.set("apply_install_place_id", installPlaceId).set("online_apply_time", new Timestamp(System.currentTimeMillis()));
			aii.save();
			setAttr("msg","安装成功");
			setAttr("applyInstallSuccess",true);
			render("/ok.html");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
}
