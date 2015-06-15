/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.jfinal.weixin.demo;

import java.util.Properties;

import com.tyj.wechat.app.model.Drive_points;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.interceptors.CtxInteceptor;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.tyj.wechat.app.AppQueryController;
import com.tyj.wechat.app.model.Car_limit_Model;
import com.tyj.wechat.app.model.DeviceGpsInfos;
import com.tyj.wechat.app.model.Hour_to_day;
import com.tyj.wechat.app.model.Install_info_Model;
import com.tyj.wechat.app.model.Message_Model;
import com.tyj.wechat.app.model.Sign_up_Model;
import com.tyj.wechat.dingyuehao.controller.QueryController;
import com.tyj.wechat.app.model.Device_user_Model;
import com.tyj.wechat.app.model.Points_Model;
public class WeixinConfig extends JFinalConfig {
	
	public Properties loadProp(String pro, String dev) {
		try {return loadPropertyFile(pro);}
		catch (Exception e)
			{return loadPropertyFile(dev);}
	}
	
	@Override
	public void configConstant(Constants me) {
		// 如果生产环境配置文件存在，则优先加载该配置，否则加载开发环境配置文件
		// 此处用的配置文件不再是jdbc.properties，而是用a_little_config.txt
		me.setViewType(ViewType.JSP);
		loadProp("a_little_config_pro.txt", "a_little_config.txt");
		//me.setDevMode(getPropertyToBoolean("devMode", false));
//		loadPropertyFile("a_little_config.txt");
		me.setDevMode(getPropertyToBoolean("devMode", true));
		// 配置微信 API相关常量
		ApiConfig.setDevMode(me.getDevMode());
		ApiConfig.setUrl(getProperty("url"));
		ApiConfig.setToken(getProperty("token"));
		ApiConfig.setAppId(getProperty("appId"));
		ApiConfig.setAppSecret(getProperty("appSecret"));
	}
	
	@Override
	public void configRoute(Routes me) {
		me.add("/weixin", DemoController.class);
		me.add("/api", ApiController.class, "/api");
		me.add("/", QueryController.class,"/");
		me.add("/app", AppQueryController.class);
	}
	
	@Override
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
		C3p0Plugin here = new C3p0Plugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password").trim());
		me.add(here);		
		// 配置ActiveRecord插件	
		//本地,连接微信端数据库
		ActiveRecordPlugin arphere = new ActiveRecordPlugin("a",here);
		arphere.addMapping("device_user", Device_user_Model.class);
		arphere.addMapping("car_limit", Car_limit_Model.class);
		arphere.addMapping("message", Message_Model.class);
		arphere.addMapping("sign_up", Sign_up_Model.class);
		arphere.addMapping("install_info", Install_info_Model.class);
		arphere.addMapping("device_gps_dayinfos", Hour_to_day.class);	
		arphere.addMapping("points",Points_Model.class);			
		arphere.addMapping("drive_points",Drive_points.class);			
		me.add(arphere);
		
		//远程,连接管理平台数据库
		C3p0Plugin there = new C3p0Plugin(getProperty("jdbcUrl_there"), getProperty("user_there"), getProperty("password_there").trim());
		me.add(there);
		ActiveRecordPlugin arpthere = new ActiveRecordPlugin("b",there);
		arpthere.addMapping("device_gps_infos", DeviceGpsInfos.class);
		me.add(arpthere);
		
		
		EhCachePlugin ecp = new EhCachePlugin();
		me.add(ecp);
	}
	
	@Override
	public void configInterceptor(Interceptors me) {
		me.add(new CtxInteceptor());
	}
	
	@Override
	public void configHandler(Handlers me) {
		
	}
	
	public static void main(String[] args) {
		JFinal.start("webapp", 80, "/", 5);
	}
}
