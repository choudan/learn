/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.jfinal.weixin.demo;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.weixin.sdk.jfinal.WeixinController;
import com.jfinal.weixin.sdk.jfinal.WeixinInterceptor;
import com.jfinal.weixin.sdk.msg.in.InImageMsg;
import com.jfinal.weixin.sdk.msg.in.InLinkMsg;
import com.jfinal.weixin.sdk.msg.in.InLocationMsg;
import com.jfinal.weixin.sdk.msg.in.InTextMsg;
import com.jfinal.weixin.sdk.msg.in.InVideoMsg;
import com.jfinal.weixin.sdk.msg.in.InVoiceMsg;
import com.jfinal.weixin.sdk.msg.in.event.InFollowEvent;
import com.jfinal.weixin.sdk.msg.in.event.InLocationEvent;
import com.jfinal.weixin.sdk.msg.in.event.InMenuEvent;
import com.jfinal.weixin.sdk.msg.in.event.InQrCodeEvent;
import com.jfinal.weixin.sdk.msg.in.speech_recognition.InSpeechRecognitionResults;
import com.jfinal.weixin.sdk.msg.out.News;
import com.jfinal.weixin.sdk.msg.out.OutImageMsg;
import com.jfinal.weixin.sdk.msg.out.OutNewsMsg;
import com.jfinal.weixin.sdk.msg.out.OutTextMsg;
import com.jfinal.weixin.sdk.msg.out.OutVoiceMsg;
import com.tyj.wechat.app.model.Device_user_Model;

/**
 * 将此 DemoController 在YourJFinalConfig 中注册路由， 并设置好weixin开发者中心的 URL 与 token ，使
 * URL 指向该 DemoController 继承自父类 WeixinController 的 index
// * 方法即可直接运行看效果，在此基础之上修改相关的方法即可进行实际项目开发
 */
public class DemoController extends WeixinController {

	private static final String helpStr = "\t发送 help 可获得帮助，请发送 任意内容，进入中国好司机页面。公众号持续更新中，欢迎关注 ^_^";
	/**
	 * 实现父类抽方法，处理文本消息 本例子中根据消息中的不同文本内容分别做出了不同的响应，同时也是为了测试 jfinal weixin
	 * sdk的基本功能： 本方法仅测试了 OutTextMsg、OutNewsMsg、OutMusicMsg 三种类型的OutMsg，
	 * 其它类型的消息会在随后的方法中进行测试
	 */
	
	@Override
	protected void processInTextMsg(InTextMsg inTextMsg) {
		String msgContent = inTextMsg.getContent().trim();
		// 帮助提示
		if ("help".equalsIgnoreCase(msgContent)) {
			OutTextMsg outMsg = new OutTextMsg(inTextMsg);
			outMsg.setContent(helpStr);
			render(outMsg);
		}
		// 图文消息测试
		// else if ("news".equalsIgnoreCase(msgContent)) {
		// OutNewsMsg outMsg = new OutNewsMsg(inTextMsg);
		// outMsg.addNews("天盈健 ，好生活", "现在就加入天盈键世界，享受高质量生活 ^_^",
		// "http://mmbiz.qpic.cn/mmbiz/zz3Q6WSrzq1ibBkhSA1BibMuMxLuHIvUfiaGsK7CC4kIzeh178IYSHbYQ5eg9tVxgEcbegAu22Qhwgl5IhZFWWXUw/0",
		// "http://www.temobi.com/htmlrc/jiejuefangan/zhihuichengshiyewu/tra/");
		// outMsg.addNews("智慧交通", "本次主要升级了 ActiveRecord 插件，本次升级全面支持多数源、多方言、多缓",
		// "http://mmbiz.qpic.cn/mmbiz/zz3Q6WSrzq0fcR8VmNCgugHXv7gVlxI6w95RBlKLdKUTjhOZIHGSWsGvjvHqnBnjIWHsicfcXmXlwOWE6sb39kA/0",
		// "http://www.temobi.com/htmlrc/jiejuefangan/zhihuichengshiyewu/tra/");
		// render(outMsg);
		// }
		// 音乐消息测试
		// else if ("music".equalsIgnoreCase(msgContent)) {
		// OutMusicMsg outMsg = new OutMusicMsg(inTextMsg);
		// outMsg.setTitle("Listen To Your Heart");
		// outMsg.setDescription("建议在 WIFI 环境下流畅欣赏此音乐");
		// outMsg.setMusicUrl("http://www.jfinal.com/Listen_To_Your_Heart.mp3");
		// outMsg.setHqMusicUrl("http://www.jfinal.com/Listen_To_Your_Heart.mp3");
		// outMsg.setFuncFlag(true);
		// render(outMsg);
		// }
		// else if ("美景".equalsIgnoreCase(msgContent)) {
		// OutNewsMsg outMsg = new OutNewsMsg(inTextMsg);
		// outMsg.addNews("大好河山", "好山好水好风景^_^",
		// "http://182.92.224.124:80/1/2.JPG",
		// "http://182.92.224.124:80/1/3.JPG");
		// render(outMsg);
		// }
		// else if ("天气".equalsIgnoreCase(msgContent)) {
		// OutNewsMsg outMsg = new OutNewsMsg(inTextMsg);
		// outMsg.addNews("首都好天气","nice","http://182.92.224.124:80/1/2.JPG","http://www.weather.com.cn/weather/101010100.shtml#7d");
		// render(outMsg);
		// }
		
		// 其它文本消息直接返回原值 + 帮助提示
		else {
			String open_id = inTextMsg.getFromUserName();
			OutNewsMsg outMsg = new OutNewsMsg(inTextMsg);
			outMsg.addNews("中国好司机", "点击查看快速设置指南",
					UrlHelper.BACK_DIR+"/img/1.jpg",
					UrlHelper.FRONT_DIR+"/Intro.html");

			render(outMsg);
		}
	}

	/**
	 * 实现父类抽方法，处理图片消息
	 */
	@Override
	protected void processInImageMsg(InImageMsg inImageMsg) {
		OutImageMsg outMsg = new OutImageMsg(inImageMsg);
		// 将刚发过来的图片再发回去
		outMsg.setMediaId(inImageMsg.getMediaId());
		render(outMsg);
	}

	/* (non-Javadoc)
	 * @see com.jfinal.weixin.sdk.jfinal.WeixinController#index()
	 */
	@Override
	@Before(WeixinInterceptor.class)
	public void index() {
		// TODO Auto-generated method stub
		super.index();
		System.out.println("++++++++++++++++++++DemoController中的index方法执行完毕+++++++++++");
	}

	/**
	 * 实现父类抽方法，处理语音消息
	 */
	@Override
	protected void processInVoiceMsg(InVoiceMsg inVoiceMsg) {
		OutVoiceMsg outMsg = new OutVoiceMsg(inVoiceMsg);
		// 将刚发过来的语音再发回去
		outMsg.setMediaId(inVoiceMsg.getMediaId());
		render(outMsg);
	}

	/**
	 * 实现父类抽方法，处理视频消息
	 */
	@Override
	protected void processInVideoMsg(InVideoMsg inVideoMsg) {
		/*
		 * 腾讯 api 有 bug，无法回复视频消息，暂时回复文本消息代码测试 OutVideoMsg outMsg = new
		 * OutVideoMsg(inVideoMsg); outMsg.setTitle("OutVideoMsg 发送");
		 * outMsg.setDescription("刚刚发来的视频再发回去"); // 将刚发过来的视频再发回去，经测试证明是腾讯官方的 api
		 * 有 bug，待 api bug 却除后再试 outMsg.setMediaId(inVideoMsg.getMediaId());
		 * render(outMsg);
		 */
		OutTextMsg outMsg = new OutTextMsg(inVideoMsg);
		outMsg.setContent("\t视频消息已成功接收，该视频的 mediaId 为: "
				+ inVideoMsg.getMediaId());
		render(outMsg);
	}

	/**
	 * 实现父类抽方法，处理地址位置消息
	 */
	@Override
	protected void processInLocationMsg(InLocationMsg inLocationMsg) {
		OutTextMsg outMsg = new OutTextMsg(inLocationMsg);
		outMsg.setContent("已收到地理位置消息:" + "\nlocation_X = "
				+ inLocationMsg.getLocation_X() + "\nlocation_Y = "
				+ inLocationMsg.getLocation_Y() + "\nscale = "
				+ inLocationMsg.getScale() + "\nlabel = "
				+ inLocationMsg.getLabel());
		render(outMsg);
	}

	/**
	 * 实现父类抽方法，处理链接消息 特别注意：测试时需要发送我的收藏中的曾经收藏过的图文消息，直接发送链接地址会当做文本消息来发送
	 */
	@Override
	protected void processInLinkMsg(InLinkMsg inLinkMsg) {
		String name = inLinkMsg.getFromUserName();
		OutNewsMsg outMsg = new OutNewsMsg(inLinkMsg);
		outMsg.addNews(
				"链接消息已成功接收",
				"链接使用图文消息的方式发回给你，还可以使用文本方式发回。点击图文消息可跳转到链接地址页面，是不是很好玩 :)",
				"http://mmbiz.qpic.cn/mmbiz/zz3Q6WSrzq1ibBkhSA1BibMuMxLuHIvUfiaGsK7CC4kIzeh178IYSHbYQ5eg9tVxgEcbegAu22Qhwgl5IhZFWWXUw/0",
				inLinkMsg.getUrl());
		render(outMsg);
	}

	/**
	 * 实现父类抽方法，处理关注/取消关注(一种消息类型)
	 */
	@Override
	protected void processInFollowEvent(InFollowEvent inFollowEvent) {
		boolean confirm=false;
		String open_id = inFollowEvent.getFromUserName();
		System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLL  open_id:"+open_id);
		String subscribe=inFollowEvent.getEvent();
		System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLL  subscribe:"+subscribe);
		if (subscribe.equals("subscribe")) {
			Device_user_Model device = Device_user_Model.dao.findFirst("select * from device_user where wechat_openid='"
							+ open_id + "'");
			System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLL  device:" + device);
			if (device == null) {
				confirm = new Device_user_Model().set("wechat_openid", open_id)
						.set("real_name", null).set("telephone", null)
						.set("identity_no", null).set("email", null)
						.set("drive_years", null).set("home_addr_city", null)
						.set("driver_license_type", null).set("age", null)
						.set("userImgs", UrlHelper.BACK_DIR+"/head_img/p1.png").set("check_pay", 0).save();// 关注后，即在数据库生成信息
				System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLL  confirm:"+ confirm);
			}	
			OutNewsMsg outMsg = new OutNewsMsg(inFollowEvent);
			News at = new News();
			at.setDescription("点击查看快速设置指南");
			at.setPicUrl(UrlHelper.BACK_DIR+"/img/1.jpg");
			at.setTitle("IVI课堂");
			at.setUrl(UrlHelper.FRONT_DIR+"/Intro.html");
			News at1 = new News();
			at1.setDescription("点击查看快速设置指南1");
			at1.setPicUrl(UrlHelper.BACK_DIR+"/img/2.jpg");
			at1.setTitle("IVI课堂");
			at1.setUrl(UrlHelper.FRONT_DIR+"/Intro.html");
			
			News at2 = new News();
			at2.setDescription("点击查看快速设置指南3");
			at2.setPicUrl(UrlHelper.BACK_DIR+"/img/video.png");
			at2.setTitle("中国好司机");
			at2.setUrl(UrlHelper.FRONT_DIR+"/IVI.mp4");
	
			List<News> aS = new ArrayList<News>();
			aS.add(at2);
			aS.add(at1);
			aS.add(at);
			outMsg.addNews(aS);
			render(outMsg);			
		}else{
			Device_user_Model model=Device_user_Model.dao.findFirst("select * from device_user where wechat_openid='"+open_id+"'");
			Integer user_id=model.getInt("id");
			Integer device_id=model.getInt("device_id");
			Db.update("delete from apply_install_infos where user_id='"+user_id+"'");
			Db.update("delete from device_gps_infos where device_id='"+device_id+"'");
			Db.update("delete from device_gps_dayinfos where device_id='"+device_id+"'");
			Db.update("delete from device_gps_dayinfos where device_id='"+device_id+"'");
			Db.update("delete from sign_up where user_id='"+user_id+"'");
			Db.update("delete from points where user_id='"+user_id+"'");
			Db.update("delete from apply_install_infos where user_id='"+user_id+"'");
			Db.update("delete from device_user where wechat_openid='"+open_id+"'");
			Db.update("delete from apply_user_infos where wechat_openid='"+open_id+"'");				
		}
	}
				
//		OutNewsMsg outMsg = new OutNewsMsg(inFollowEvent);
//		News at = new News();
//		at.setDescription("点击查看快速设置指南");
//		at.setPicUrl(UrlHelper.BACK_DIR+"/img/1.jpg");
//		at.setTitle("IVI课堂");
//		at.setUrl(UrlHelper.FRONT_DIR+"/Intro.html");
//		News at1 = new News();
//		at1.setDescription("点击查看快速设置指南1");
//		at1.setPicUrl(UrlHelper.BACK_DIR+"/img/2.jpg");
//		at1.setTitle("IVI课堂");
//		at1.setUrl(UrlHelper.FRONT_DIR+"/Intro.html");
//		
//		News at2 = new News();
//		at2.setDescription("点击查看快速设置指南3");
//		at2.setPicUrl(UrlHelper.BACK_DIR+"/img/video.png");
//		at2.setTitle("中国好司机");
//		at2.setUrl(UrlHelper.FRONT_DIR+"/IVI.mp4");
//
//		List<News> aS = new ArrayList<News>();
//		aS.add(at2);
//		aS.add(at1);
//		aS.add(at);
//		outMsg.addNews(aS);
//		render(outMsg);	
//	}

	/**
	 * 实现父类抽方法，处理扫描带参数二维码事件
	 */
	@Override
	protected void processInQrCodeEvent(InQrCodeEvent inQrCodeEvent) {
		OutTextMsg outMsg = new OutTextMsg(inQrCodeEvent);
		outMsg.setContent("processInQrCodeEvent() 方法测试成功");
		render(outMsg);
	}

	/**
	 * 实现父类抽方法，处理上报地理位置事件
	 */
	@Override
	protected void processInLocationEvent(InLocationEvent inLocationEvent) {
		OutTextMsg outMsg = new OutTextMsg(inLocationEvent);
		outMsg.setContent("processInLocationEvent() 方法测试成功");
		render(outMsg);
	}
	
	/**
	 * 实现父类抽方法，处理自定义菜单事件
	 */
	@Override
	protected void processInMenuEvent(InMenuEvent inMenuEvent) {// 接收一个类
		// renderOutTextMsg("processInMenuEvent() 方法测试成功");
		String a = inMenuEvent.getEventKey();
		System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLL  a:"+a);
		if (a.equals("zou")) {
			forwardAction("/api/getFollowers");
		} else if (a.equals("meng")) {
			String name = inMenuEvent.getFromUserName();
			forwardAction("/api/getUserInfos");
		}
	}

	/**
	 * 实现父类抽象方法，处理接收语音识别结果
	 */
	@Override
	protected void processInSpeechRecognitionResults(
			InSpeechRecognitionResults inSpeechRecognitionResults) {
		renderOutTextMsg("语音识别结果： "
				+ inSpeechRecognitionResults.getRecognition());
	}
}
