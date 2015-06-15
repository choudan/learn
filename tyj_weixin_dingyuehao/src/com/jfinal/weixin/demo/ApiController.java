package com.jfinal.weixin.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.weixin.sdk.api.OAuth2;
import com.tyj.wechat.app.bean.DayStatisticsResult;
import com.tyj.wechat.app.bean.DriveTrend;
import com.tyj.wechat.app.bean.Result;
import com.tyj.wechat.app.bean.SignUp;
import com.tyj.wechat.app.bean.UserDataMain;
import com.tyj.wechat.app.bean.UserDataMyIVI;
import com.tyj.wechat.app.bean.UserInfo;
import com.tyj.wechat.app.model.DeviceGpsInfos;
import com.tyj.wechat.app.model.Device_user_Model;
import com.tyj.wechat.app.model.Drive_points;
import com.tyj.wechat.app.model.Hour_to_day;
import com.tyj.wechat.app.model.Message_Model;
import com.tyj.wechat.app.model.Points_Model;
import com.tyj.wechat.app.model.Sign_up_Model;
import com.tyj.wechat.app.service.Car_limit;
import com.tyj.wechat.app.service.Get_Contents;
import com.tyj.wechat.app.service.JavaMail;
import com.tyj.wechat.app.service.StatisticsService;
import com.tyj.wechat.app.service.UserDataService;

public class ApiController extends Controller {
	public static String open_id="";//static变量，全局唯一一份，所有访问者得到的均是同一份资源。用session来代替，面对每一个访问者，每次给open_id重新赋值。
    public String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());//统一时间格式
	
    /*//获取公众号菜单 
	public void getMenu() {
		ApiResult apiResult = MenuApi.getMenu();
		if (apiResult.isSucceed()){
			renderText(apiResult.getJson());	
			System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLLL getMenu  apiResult:"+apiResult);
		}else{
			renderText(apiResult.getErrorMsg());		
		}
	}
	
	//获取公众号关注用户	 
	public void getFollowers() {
		ApiResult apiResult = UserApi.getFollows();
		renderText(apiResult.getJson());
	}

	//创建菜单, 将菜单按钮的链接设置为用户信息授权链接,跳转到的{REDIRECT_URI}可以获取到用户openid了，调用一次即可。
	public void createmenu(){
		MenuApi a=new MenuApi();
		String jsonStr="{\"button\":[{"	
			                     +     "\"type\":\"view\","
                                 +    "\"name\":\"好司机\","
                                 +    "\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxdff2819da92b0ac3&redirect_uri=http://182.92.224.124/tyj_weixin_dingyuehao/api/get_openid&response_type=code&scope=snsapi_base&state=1#wechat_redirect\""			                    	                         
			                        +"},{"
			                        +"\"type\":\"view\","
				                    +"\"name\":\"用户注册\","
				                    +"\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxdff2819da92b0ac3&redirect_uri=http://182.92.224.124/tyj_weixin_dingyuehao/api/install_openid&response_type=code&scope=snsapi_base&state=1#wechat_redirect\""				           				                    
			                     + "},{"			                  	
			                     +  "\"type\":\"view\","
				                     +    "\"name\":\"安装设备\","
				                     +    "\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxdff2819da92b0ac3&redirect_uri=http://182.92.224.124/tyj_weixin_dingyuehao/api/comp_openid&response_type=code&scope=snsapi_base&state=1#wechat_redirect\""				                    
				                    +"}]}";
		a.createMenu(jsonStr);	
	}
	
	// 获取关注用户信息
	public void getUserInfos() {
		String name=getPara("name");
		ApiResult apiResult1 = UserApi.getUserInfo(name);
		renderText(apiResult1.getJson());
	}*/
	  
    public String oAuth(){
    	String op_id="";
		System.out.println("++++++++++++++++++++++++++++++++++++开始");
		if(getSessionAttr("openid")==null){
			String code = getPara("code");
			System.out.println("++++++++++++++++++++++++++++++++++++"+code);
			String j = new OAuth2().oAuth2(code);
			System.out.println("++++++++++++++++++++++++++++++++++++"+ j);
			JSONObject Oth = JSON.parseObject(j);
			System.out.println("++++++++++++++++++++++++++++++++++++"+ Oth);
			op_id = (String) Oth.get("openid");	
			setSessionAttr("openid", op_id);		
			System.out.println("++++++++++++++++++++++++++++++++++++++++"+ op_id);								
		}else{
			op_id=getSessionAttr("openid");
		}
		return op_id;
    }   
		
	//中国好司机	
	public void get_openid() {
//		redirect("http://182.92.224.124/iviservice/view/1.jsp");//1.jsp的作用：创建session，保存openid.30分钟以内，用户再次访问，就不用重新outh2认证。（其实，不必如此，网页授权获取用户信息（open_id）的接口无调用次数限制，可一直刷新获得code(存活5分钟)、认证、access_token、open_id，弊端：加载太慢,有时间认证可能不成功）
		redirect(UrlHelper.FRONT_DIR+"/view/1.jsp");//1.jsp的作用：创建session，保存openid.30分钟以内，用户再次访问，就不用重新outh2认证。（其实，不必如此，网页授权获取用户信息（open_id）的接口无调用次数限制，可一直刷新获得code(存活5分钟)、认证、access_token、open_id，不过这样做的弊端：加载太慢,有时间认证可能不成功）
		open_id=oAuth();
		System.out.println(">>>>>>>>>>>>>>>>>>>>get_openid()执行完毕<<<<<<<<<<<<<<<<<<<<<<  open_id:"+open_id);
	}	
	
	//用户注册
	public void install_openid(){
		open_id=oAuth();	
		System.out.println("+_+_+_+_+_+_+  open_id:"+open_id);
		switch(check_regist()){
		case 0:
			redirect(UrlHelper.FRONT_DIR+"/myIVI/register.html");
//			redirect("http://182.92.224.124/iviservice/myIVI/register.html");
			System.out.println(">>>>>>>>>>>>>>>>>>>++++++++++++用户注册菜单测试：游客");
			break;
		case 1:
			redirect(UrlHelper.FRONT_DIR+"/myIVI/register_finish.html");
			System.out.println(">>>>>>>>>>>>>>>>>>>++++++++++++用户注册菜单测试：普通");
			break;
		case 2:
			redirect(UrlHelper.FRONT_DIR+"/myIVI/register_finish_vip.html");
			System.out.println(">>>>>>>>>>>>>>>>>>>++++++++++++用户注册菜单测试：vip");
			break;
		default:
			break;
		}			
 	}
	
	//安装设备
	public void comp_openid(){	
		open_id=oAuth();
		System.out.println("+_+_+_+_+_+_+  open_id:"+open_id);

		Device_user_Model qt = Device_user_Model.dao.findFirst("select device_id,car_type,buyYear,car_no,insurance,lastYearCosts,installAddress,check_pay from device_user where wechat_openid='" 
				+ open_id+ "'");	
		Integer check_pay=qt.getInt("check_pay");
		System.out.println("——————————————————————————————————————————————————————————————————————————————————————"+check_pay);
		if(check_regist()==2){			
			redirect(UrlHelper.FRONT_DIR+"/myIVI/install_info.html");
		}else if(check_regist()==1&&check_pay==0){
			redirect(UrlHelper.FRONT_DIR+"/myIVI/more_info.html");
		}else if(check_regist()==1&&check_pay==1){
			redirect(UrlHelper.FRONT_DIR+"/myIVI/install_device_pay_finish.html");		
		}else{
			redirect(UrlHelper.FRONT_DIR+"/myIVI/register.html");
		}
 	}
	
	//vip会员获得安装信息（安装以后，点击  菜单安装设备 后的效果）	
	public void back_comp(){
//		String open_id=oAuth();
		System.out.println("back_comp中的open_id："+open_id);	
		Device_user_Model qt = Device_user_Model.dao.findFirst("select device_id,car_type,buyYear,car_no,insurance,lastYearCosts,installAddress from device_user where wechat_openid='" 
				+ open_id+ "'");			
		Result aq = new Result();
		aq.setDataType(2);
		aq.setStatusCode(-1);
		aq.setValid(1);
		aq.setdata(qt);
		renderJson(aq);
		System.out.println("back_comp生成的Json:"+aq);
	}
		
	/*完善个人信息*/
	public void complete_info(){
//		String open_id=oAuth();
//		Calendar c = Calendar.getInstance();
//		int year = c.get(Calendar.YEAR);
//		int month = c.get(Calendar.MONTH) + 1;
//		int day = c.get(Calendar.DAY_OF_MONTH);
//		String date = year + "-" + month + "-" + day;
		// String open_id=getSessionAttr("openid");
		Integer userId = Device_user_Model.dao.findFirst("select id from device_user where wechat_openid='" + open_id
						+ "'").getInt("id");
		// String installInfo=getPara("installInfo");
		// 把数组型的json转为json
		// JSONObject jo = JSON.parseObject(installInfo);
		// String name=jo.get("name").toString();
		// String phoneNumber=jo.get("phoneNumber").toString();
		String compinfo = getPara("compinfo");
		// 把数组型的json转为json
		JSONObject jo = JSON.parseObject(compinfo);
		// Integer
		// userId=Device_user_Model.dao.findFirst("select id from device_user where wechat_openid='"+open_id+"'").getInt("id");
		// int allowNumber=getParaToInt("allowNumber");
		int device_id = 0;
		try {
			device_id = Integer.parseInt(jo.get("device_id") + "");// 设备的sim卡号
		} catch (Exception e) {
			device_id = 0;
		}
		String carType = jo.get("carType") + "";
		String buyYear = jo.get("buyYear") + "";
		String carNumber = jo.get("carNumber") + "";
		String insurance = jo.get("insurance") + "";
		String lastYearCosts = jo.get("lastYearCosts") + "";
		String installAddress = jo.get("installAddress") + "";
		// String driver_license_type=getPara("driver_license_type");
		// String real_name=getPara("real_name");
		// String gender=getPara("gender");
		// String email=jo.get("email").toString();
		// String identity_no=jo.get("identity_no").toString();
		// String phone=getPara("phone");
		// String password=MD5.GetMD5Code(getPara("password"));//Md5加密
		Device_user_Model.dao.findById(userId).set("device_id", device_id)
				.set("car_type", carType).set("buyYear", buyYear)
				.set("car_no", carNumber).set("insurance", insurance)
				.set("lastYearCosts", lastYearCosts)
				.set("installAddress", installAddress).set("if_member", 1)
				.update();
		// 写到积分表里
		Points_Model s = Points_Model.dao
				.findFirst("select * from points where user_id='" + userId
						+ "'");
		if (null == s) {
			new Points_Model().set("active", 100).set("id", null)
					.set("sum", 100).set("sign_up", 0).set("date", null)
					.set("user_id", userId).set("active_time", date).save();

			Device_user_Model uq = Device_user_Model.dao
					.findFirst("select * from device_user where wechat_openid='"
							+ open_id + "'");
			// 向管理员发送邮件
			String contents = "申请人姓名:" + uq.get("real_name") + "\n" + "年龄:"
					+ uq.get("age") + "\n" + "手机号:" + uq.get("phoneNumber")
					+ "\n" + "车型:" + uq.get("carType") + "\n" + "购买年份:"
					+ uq.get("buyYear") + "\n" + "车牌号:" + uq.get("carNumber")
					+ "\n" + "安装地址:" + uq.get("installAddress") + "\n"
					+ "去年保费:" + uq.get("lastYearCosts") + "\n" + "车险到期:"
					+ uq.get("insurance") + "\n";
			new JavaMail(false).sdMail(contents);
			System.out.println("邮件发送成功");
		}

		Result aq = new Result();
		aq.setDataType(2);
		aq.setStatusCode(-1);
		aq.setValid(1);
		aq.setdata("保存成功");
		renderJson(aq);
	}
 	
	//获取推送信息
	public void message_bar() {
		// String city=getPara("city");
		List<String> m = new ArrayList<String>();
		Result a = new Result();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		String m1 = Message_Model.dao.findFirst("select m1 from message").get("m1");
		String m2 = Message_Model.dao.findFirst("select m2 from message").get("m2");
		String m3 = Message_Model.dao.findFirst("select m3 from message").get("m3");
		m.add(m1);
		m.add(m2);
		m.add(m3);
		
		map.put("message", m);
		a.setDataType(2);
		a.setStatusCode(-1);
		a.setValid(1);
		a.setdata(map);//map放进去，就是JSON里面的键值对
		renderJson(a);
	}
	
	//限号
	public void car_number_limit(){
		Result a = new Result();
		Calendar cal = Calendar.getInstance();
		int week = cal.get(Calendar.DAY_OF_WEEK)-1;//周五，对应的6
		if(week==6){
			a.setdata("no");			
			a.setDataType(1);
		}else if(week==0){
			a.setdata("no");					
			a.setDataType(1);
		}else{
			a.setdata(new Car_limit().carl(week));
			a.setDataType(2);		
		}
		a.setStatusCode(-1);
		a.setValid(1);
		renderJson(a); 
	}
	 
	//签到查询	 
	public int if_sign(){	
		System.out.println("++++++++++++++++++++++++++++++if_sign执行到了：00");
//		String open_id=oAuth();
		int isSignIn=-1;
    	int flag=check_regist();
		if(flag==0){
    		isSignIn=-1;//表示游客身份
    		System.out.println("++++++++++++++++++++++++++++++if_sign执行到了：11");    		
    	}
    	else{
    		System.out.println("++++++++++++++++++++++++++++++if_sign中的日期："+date);
    		Integer userId = Device_user_Model.dao.findFirst("select id from device_user where wechat_openid='"
							+ open_id + "'").getInt("id");
    		Sign_up_Model msg=Sign_up_Model.dao.findFirst("select * from sign_up where user_id='"+userId+"' and date='"+date+"'");
    		System.out.println("++++++++++++++++++++++++++++++if_sign中的日期："+msg);    		  		
    		if (msg != null) {
    			isSignIn=1;//签到返回值
    		} else {
    			isSignIn=0;//未签到
    		}   		
    	}
    	System.out.println("++++++++++++++++++++++++++++++if_sign中的isSignIn："+isSignIn);    		  		
		return isSignIn;
	}
	
	//签到 ,计算出相关数值，存到表中;
    public void sign_up(){	
    	System.out.println("————————————————————签到date:"+date+"  0 ");
		Result a = new Result();
		SignUp sign=new SignUp();
		System.out.println("————————————————————签到date:"+date+"  1 ");
    	Integer userId=Device_user_Model.dao.findFirst("select * from device_user where wechat_openid='"+open_id+"'").getInt("id");	
    	System.out.println("————————————————————签到date:"+date+"  2    "+userId);
    	if(check_regist()==0){
    		sign.setContent("啊哦，游客不能签到哦");
    		sign.setPoints(0);
    		sign.setPointsIncrease(0);
    	}else if(check_regist()==1){
    		System.out.println("————————————————————签到date:"+date+"  2    "+userId);
    		Points_Model pmodel=Points_Model.dao.findFirst("select * from points where user_id='"+userId+"'");
    		Integer sing_up=pmodel.get("sign_up");   
    		Integer sum=pmodel.get("sum");  
    		if(if_sign()==0){
    			Sign_up_Model msg_re=Sign_up_Model.dao.findFirst("select * from sign_up where user_id='"+userId+"'");	
    			int s_points=msg_re.get("s_points");	
    			msg_re.set("date", date).set("user_id", userId).set("s_points",s_points+1).update();		
    			boolean update=pmodel.set("sign_up",(int)sing_up+1).set("sum", sum+1).set("active_time", date).update();//date字段表示安装时间，建立表后不予更新。	
    			if(update){
    				sign.setContent("今日已签到");
    				sign.setPoints(sum+1);
    				sign.setPointsIncrease(1);   			
    			}else{
    				sign.setContent("签到失败");   			
    			} 
    			a.setStatusCode(-1);	
    		}else if(if_sign()==1){
    	    	a.setStatusCode(1);	//表示已签过，数据不会有变化
    		}
    	}else if(check_regist()==2){
    		System.out.println("————————————————————签到date:"+date+"  3    "+"执行到此");
    		
    		Points_Model pmodel=Points_Model.dao.findFirst("select * from points where user_id='"+userId+"'");
    		System.out.println("————————————————————签到date:"+date+"  6    "+pmodel);
    		Integer sum=pmodel.get("sum");  
    		Integer sign_up=pmodel.get("sign_up");  
    		if(if_sign()==0){
    			Sign_up_Model msg_re=Sign_up_Model.dao.findFirst("select * from sign_up where user_id='"+userId+"'");	
    			System.out.println("————————————————————签到date:"+date+"  4    "+msg_re);
    			int s_points=msg_re.get("s_points");	
    			System.out.println("————————————————————签到date:"+date+"  5    "+s_points);
    			msg_re.set("date", date).set("user_id", userId).set("s_points",s_points+1).update();
    			boolean update=pmodel.set("sign_up",(int)sign_up+1).set("sum", sum+10).set("active_time", date).update();	  		
    			System.out.println("+++++++++++++++++++++++++++++++VIP会员++签到：update"+update);
    			if(update){
    				sign.setContent("今日已签到");
    				sign.setPoints(sum+10);
    				sign.setPointsIncrease(10);					
    			}else{
    				sign.setContent("签到失败");
    			}  			
    			a.setStatusCode(-1);	
    		}else if(if_sign()==1){
    	    	a.setStatusCode(1);	//表示已签过，数据不会有变化
    		}
    	}	
    	a.setDataType(2);
    	a.setValid(1);
    	a.setdata(sign);
		renderJson(a);
    }   	
    
    //天气
    public void weather(){
    	HashMap<String, String> map = new HashMap<String, String>();
    	Result aq = new Result();
		String url = "http://www.bjmb.gov.cn/zsyb.html";
		String encoding = "UTF-8";
		String ct = new Get_Contents().getURLContent(url, encoding);
		int a = ct.indexOf("洗车");
		// int b=ct.indexOf("</td><td>适宜洗车");
		String index = ct.substring(a + 26, a + 27);
		map.put("weatherIndex", index);
		aq.setDataType(2);
		aq.setStatusCode(-1);
		aq.setValid(1);
		aq.setdata(map);
		renderJson(aq);
	}
    
    //驾驶趋势图 
	public void driving_habits_today() {
		Result re = new Result();
//		String open_id=oAuth();
		Integer userId = Device_user_Model.dao.findFirst(
				"select device_id from device_user where wechat_openid='"
						+ open_id + "'").getInt("device_id");
		if (userId == null) {
			re.setStatusCode(1);
		} else {
			StatisticsService ss = new StatisticsService();
			DayStatisticsResult dsr = ss.getDeviceDayStatisticsResult(userId,new Date());
			re.setStatusCode(-1);
			re.setDataType(2);
			re.setdata(dsr);
		}
		renderJson(re);
	}
	
	//驾驶曲线图     
	public void driving_trend() {
		DriveTrend trend=null; 
		Result re = new Result();
		String para=getPara("interval");
//		String open_id=oAuth();
		if(para==null){
			re.setStatusCode(4);//参数错误
			trend=null;
		}else{
			Integer device_id = Device_user_Model.dao.findFirst(
					"select device_id from device_user where wechat_openid='"
							+ open_id + "'").getInt("device_id");
			System.out.println("+++++++++++++++++"+device_id);
			if(device_id==null){
				re.setStatusCode(3);//非VIP，无数据
			}else{
				Integer interval=Integer.parseInt(para);
				System.out.println("-------------------"+interval);
				StatisticsService ss = new StatisticsService();
				Date endDate = new Date();
				Date startDate = getStartDate(interval, endDate);
				trend = ss.getDriveTrend(device_id, interval, startDate,endDate);
				if (trend.getKeypoint1() == null||trend.getKeypoint1().size() == 0) {
					re.setStatusCode(1);//VIP，没有数值
				} else {
					re.setStatusCode(-1);//正常值
					re.setDataType(2);
				}						
			}		
		}
		re.setdata(trend);
		renderJson(re);
	}
     
 	private Date getStartDate(Integer interval, Date endDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(endDate);
		if(interval==0){
			c.add(Calendar.DAY_OF_MONTH, -3);
		}else if(interval==1){
			c.add(Calendar.DAY_OF_MONTH, -7);
		}else if(interval==2){
			c.add(Calendar.MONTH, -1);
		}else{
			c.add(Calendar.MONTH, -6);
		}
		return c.getTime();
	}
 	
 	//限速、偏离、城市、行人、车辆、打折(只有VIP会员有数据)	
 	public void user_data_main(){
		Result re = new Result();	
		//app端的
//		String open_id=oAuth();
	    System.out.println("user_data_main openid ="+open_id);
		switch(check_regist()){
		case 0:
			UserDataMain traveler=new UserDataMain();	
			traveler.setBang(0);
			traveler.setSpeeding(0);
			traveler.setCity_bang(0);
			traveler.setWalker_bang(0);
			traveler.setLaneout(0);
			traveler.setDiscount(100);			
			re.setdata(traveler);
			break;
		case 1:
			UserDataMain user=new UserDataMain();			
			user.setBang(0);
			user.setSpeeding(0);
			user.setCity_bang(0);
			user.setWalker_bang(0);
			user.setLaneout(0);
			user.setDiscount(90);			
			re.setdata(user);
			break;
		case 2://vip有数据
			Integer userId=Device_user_Model.dao.findFirst("select device_id from device_user where wechat_openid='"+open_id+"'").getInt("device_id");			
			UserDataService ss = new UserDataService();
			UserDataMain vip = ss.uds(userId);
			vip.setDiscount(80);
			re.setdata(vip);
			break;
		default:
			break;		
		}
		re.setStatusCode(-1);
		re.setDataType(2);
		renderJson(re);
	}
 	
 	//********************该接口调用了两次******register_finish.html*****member_infor.html*********** 	
 	//查询是否注册，若注册则返回个人信息 ，dataType区别三种身份
 	public void query_user_info(){
		UserInfo rs = new UserInfo();
		Result aq = new Result();
//		String open_id=oAuth();
		System.out.println(">>>>>>>>>>>>>>>>>query_user_info中的wechat_openid："+ open_id);
		switch(check_regist()){
		case 0:
			aq.setDataType(0);//此处0表示游客身份，前台提醒注册
			rs=null;
			break;
		case 1:
			System.out.println(">>>>>>>>>>>>>>>>>query_user_info中的00：");
			Device_user_Model x = Device_user_Model.dao.findFirst(
					"select * from device_user where wechat_openid='" + open_id+ "'");
			System.out.println(">>>>>>>>>>>>>>>>>query_user_info中的11："+x);	
			rs.setAge(x.getStr("age"));
			System.out.println(">>>>>>>>>>>>>>>>>query_user_info中的22："+x.getStr("age"));
			if(x.getInt("allowNumber")==null)
				rs.setAllowNumber(0);
			else{
				rs.setAllowNumber(x.getInt("allowNumber").intValue());			
			}
			if(x.getInt("drive_years")==null)
				rs.setAllowNumber(0);
			else{
				rs.setAllowNumber(x.getInt("drive_years").intValue());			
			}
//			rs.setGender(x.getStr("gender"));
//			System.out.println(">>>>>>>>>>>>>>>>>query_user_info中的44："+x.getStr("gender"));
//			rs.setLicenseNumber(x.getStr("car_no"));
//			System.out.println(">>>>>>>>>>>>>>>>>query_user_info中的55："+x.getStr("car_no"));
//			rs.setLicenseType(x.getStr("driver_license_type"));
//			System.out.println(">>>>>>>>>>>>>>>>>query_user_info中的66："+x.getStr("driver_license_type"));
//			rs.setMemberPrivileges(x.getStr("memberPrivileges"));
//			System.out.println(">>>>>>>>>>>>>>>>>query_user_info中的77："+x.getStr("memberPrivileges"));
			rs.setTelephoneNum(x.getStr("telephone"));
			System.out.println(">>>>>>>>>>>>>>>>>query_user_info中的88："+x.getStr("telephone"));
			rs.setName(x.getStr("real_name"));
			System.out.println(">>>>>>>>>>>>>>>>>query_user_info中的99："+x.getStr("real_name"));
			rs.setEmail(x.getStr("email"));
			System.out.println(">>>>>>>>>>>>>>>>>query_user_info中的1111："+x.getInt("drive_years").intValue());
			rs.setHome_addr_street(x.getStr("home_addr_street"));
			System.out.println(">>>>>>>>>>>>>>>>>query_user_info中的1212："+x.getStr("home_addr_street"));
			rs.setIdentity_no(x.getStr("identity_no"));
			System.out.println(">>>>>>>>>>>>>>>>>query_user_info中的1313："+x.getStr("identity_no"));
			aq.setDataType(1);
			System.out.println(">>>>>>>>>>>>>>>>>query_user_info中的1414：结束");
			break;
		case 2:
			Device_user_Model vip = Device_user_Model.dao.findFirst(
					"select * from device_user where wechat_openid='" + open_id+ "'");
			rs.setAge(vip.getStr("age"));
			if(vip.getInt("allowNumber")==null)
				rs.setAllowNumber(0);
			else{
				rs.setAllowNumber(vip.getInt("allowNumber").intValue());			
			}
			if(vip.getInt("drive_years")==null)
				rs.setDrive_years(0);
			else{
				rs.setDrive_years(vip.getInt("drive_years").intValue());			
			}
			System.out.println(">>>>>>>>>>>>>>>>>query_user_info中的11：");
//			rs.setGender(vip.getStr("gender"));
//			rs.setLicenseNumber(vip.getStr("car_no"));
//			rs.setLicenseType(vip.getStr("driver_license_type"));
//			rs.setMemberPrivileges(vip.getStr("memberPrivileges"));
			rs.setTelephoneNum(vip.getStr("telephone"));
			rs.setName(vip.getStr("real_name"));
			rs.setEmail(vip.getStr("email"));
			rs.setHome_addr_street(vip.getStr("home_addr_street"));
			rs.setIdentity_no(vip.getStr("identity_no"));
			System.out.println(">>>>>>>>>>>>>>>>>query_user_info中的15：");
			aq.setDataType(2);			
			break;
		default:
			break;		
		}
		aq.setStatusCode(-1);
		aq.setdata(rs);
		renderJson(aq);
	}
 	
 	/*userId这个是真正的id*/
 	public void update_user_info(){
//		String open_id=oAuth();
 		Integer userId=Device_user_Model.dao.findFirst("select * from device_user where wechat_openid='"+open_id+"'").getInt("id");	
 		String	update_user_info =getPara("update_user_info");
 		JSONObject jo = JSON.parseObject(update_user_info);
	    String	name=jo.get("name").toString();
		String	gender=jo.get("gender").toString();
		String	age=jo.get("age").toString();
		String	licenseType=jo.get("licenseType").toString();
		String	licenseNumber=jo.get("licenseNumber").toString();
		int	allowNumber=Integer.parseInt(jo.get("allowNumber").toString());
		//String	memberPrivileges=getPara("memberPrivileges");
		try{
			Device_user_Model.dao.findById(userId).set("real_name", name)
					.set("gender", gender).set("age", age)
					.set("car_no", licenseNumber)
					.set("driver_license_type", licenseType)
					.set("allowNumber", allowNumber).update();
			HashMap<String, Object> map2 = new HashMap<String, Object>();
			map2.put("status", 0);
			map2.put("msg", "修改成功");
			Result aq = new Result();
			aq.setDataType(2);
			aq.setStatusCode(-1);
			aq.setValid(1);
			aq.setdata(map2);
			renderJson(aq);
		} catch (Exception e) {
			HashMap<String, Object> map3 = new HashMap<String, Object>();
			map3.put("status", 1);
			map3.put("msg", "修改失败");
			Result aq = new Result();
			aq.setDataType(2);
			aq.setStatusCode(-1);
			aq.setValid(1);
			aq.setdata(map3);
			renderJson(aq);
			e.printStackTrace();
		}
 	}
 	
 	//获取 安装设备 信息,++++++++++++ 没有添加s_points这个字段 （具体作用）	
 	public void installInfo(){
//		String open_id=oAuth();
 		Result aq = new Result();
 		HashMap<String, Object> map = new HashMap<String, Object>();
		String installInfo = getPara("installInfo");
		JSONObject jo = JSON.parseObject(installInfo);
		String carType = jo.get("carType").toString();
		String buyYear = jo.get("buyYear").toString();
		String carNumber = jo.get("carNumber").toString();
		String insurance = jo.get("insurance").toString();//保险到期时间
		String lastYearCosts = jo.get("lastYearCosts").toString();
		String installAddress = jo.get("installAddress").toString();
		int device_id = Integer.parseInt(jo.get("device_id").toString());		
		Device_user_Model installModel = Device_user_Model.dao.findFirst("select * from device_user where wechat_openid='"
				+ open_id + "'");
		String real_name=installModel.getStr("real_name");
		Integer pay_check=installModel.getInt("check_pay");		
		System.out.println(">>>>>>>>>>>>>>>>>>>>installInfo()中的installInfo:"+real_name);
		if(installModel.getInt("id")==null){
			map.put("submitFail", "安装失败，请先注册");			
		}else if(pay_check==1){
			System.out.println(">>>>>>>>>>>>>>>>>>>>installInfo()中执行到1");
			int userId=Integer.parseInt(installModel.getInt("id").toString());
			
			installModel.set("car_type", carType).set("buyYear", buyYear)
			.set("car_no", carNumber).set("insurance", insurance)
			.set("lastYearCosts", lastYearCosts).set("installAddress", installAddress)
			.set("device_id", device_id).update();	
			
			new Points_Model().set("active", 100).set("id", null)
			.set("sum", 100).set("sign_up", 0).set("date", date)
			.set("user_id", userId).set("active_time", date).save();	
			
			new Drive_points().set("device_id", device_id).set("points", 60).set("date", date).set("real_name", real_name).save();		
			map.put("submitFail", "安装成功");
			
			new Hour_to_day().set("device_id", device_id).set("date", date).set("warntimes", 0).set("s_warntimes", 0).save();
			
			new DeviceGpsInfos().set("device_id", device_id).set("event_time", null).set("longitude", null).set("latitude", null)
			.set("speed", null).set("msg_type", null).set("flash_state", null).set("upload_time", null).save();
		}else {
			installModel.set("check_pay", 0);
			map.put("submitFail", "安装失败，请先购买设备");	
		}	 
		aq.setDataType(2);
		aq.setStatusCode(-1);
		aq.setValid(1);
		aq.setdata(map);
		renderJson(aq);
 	}
 	
 	//用户注册,不用再判断了，菜单项已经有过判断。check_pay:0未支付，1支付
 	public void register(){
//		String open_id=oAuth();
 		boolean confirm=false,promt=false;
   		Result aq = new Result();
   		HashMap<String, Object> map = new HashMap<String, Object>();
   		
   		Device_user_Model device_user=Device_user_Model.dao.findFirst("select * from device_user where wechat_openid='" + open_id
				+ "'");
   		System.out.println("++++++++++++++++++++++++register()中的device_user:" + device_user);
   		
//		String auto_img = "http://182.92.224.124/tyj_weixin_dingyuehao/head_img/p1.png";//默认图像
		String register = getPara("register");//注意参数
		System.out.println("++++++++++++++++++++++++register()中的register:" + register);
		JSONObject jo = JSON.parseObject(register);
		System.out.println("++++++++++++++++++++++++register()中的jo:" + jo);
		String name = jo.get("name").toString();
		System.out.println("++++++++++++++++++++++++register()中的name:" + name);
		String phoneNumber = jo.get("phoneNumber").toString();
		String identity_no = jo.get("identity_no").toString();
		String email = jo.get("email").toString();
		String driveyears = jo.get("drive_year").toString();
		String adress = jo.get("adress").toString();
		String age = jo.getString("age");
		String driver_license_type=jo.getString("type");
			
		System.out.println("++++++++++++++++++++++++register()中的age:" + age);
		
		confirm=device_user.set("real_name", name).set("telephone", phoneNumber)
		.set("identity_no", identity_no).set("email", email).set("drive_years", driveyears)
		.set("home_addr_city", adress).set("driver_license_type", driver_license_type)
		.set("age", age).set("check_pay", 0).update();

		int userId=device_user.getInt("id");
		promt=new Sign_up_Model().set("s_points",0).set("user_id",userId).set("date", 0000-00-00).save();		
		if(confirm&&promt){
			map.put("submitFail", "注册成功");	
			String contents = "申请人姓名:" + name + "\n" + "年龄:"
					+ age+ "\n" + "手机号:" + phoneNumber + "\n";
			new JavaMail(false).sdMail(contents);
			System.out.println("++++++++++++++++++++++++邮件发送成功");			
		}
		else{
			map.put("submitFail", "注册失败");			
		}					
		aq.setDataType(2);
		aq.setStatusCode(-1);
		aq.setValid(1);
		aq.setdata(map);
		renderJson(aq);
		System.out.println("++++++++++++++++++++++++register()中的aq:" + aq);
 	}
 	
 	//smdate为 小时间，bdate为大时间	
 	public static int daysBetween(String smdate,String bdate) throws ParseException{  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(sdf.parse(smdate));    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(sdf.parse(bdate));    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));     
    }  
      
 	
//	public void installInfo() {
//		// String open_id=getSessionAttr("openid");
//		System.out.println("ddddddddddd" + open_id);
//		// 默认头像
//		String auto_img = "http://182.92.224.124/tyj_weixin_dingyuehao/head_img/p1.png";
//		String installInfo = getPara("installInfo");
//		// 把数组型的json转为json
//		JSONObject jo = JSON.parseObject(installInfo);
//		String name = jo.get("name").toString();
//		String phoneNumber = jo.get("phoneNumber").toString();
//		String identity_no = jo.get("identity_no").toString();
//		String email = jo.get("email").toString();
//		String driveyears = jo.get("drive-year").toString();
//		String home_addr_city = jo.get("home_addr_city").toString();
//		String home_addr_street = jo.get("home_addr_street").toString();
//		String home_addr_district = jo.get("home_addr_district").toString();
//		String home_addr_province = jo.get("home_addr_province").toString();
//		// String insurance=jo.get("insurance").toString();
//		String age = jo.get("age").toString();
//		// String lastYearCosts=jo.get("lastYearCosts").toString();
//		Object u = Device_user_Model.dao
//				.findFirst("select * from device_user where wechat_openid='"
//						+ open_id + "'");
//		// Object
//		// u=Device_user_Model.dao.findFirst("select car_no from device_user where telephone='"+phoneNumber+"'");
//		if (null != u) {
//			Result aq = new Result();
//			HashMap<String, Object> map5 = new HashMap<String, Object>();
//			map5.put("verifyStatus", 0);
//			map5.put("submitStatus", 0);
//			map5.put("submitFail", "您已申请注册，不要重复提交啦");
//			aq.setDataType(2);
//			aq.setStatusCode(-1);
//			aq.setValid(0);
//			aq.setdata(map5);
//			renderJson(aq);
//		} else {
//			try {
//				new Device_user_Model().set("id", null)
//						.set("telephone", phoneNumber).set("car_type", null)
//						.set("buyYear", null).set("car_no", null)
//						.set("installAddress", null).set("insurance", null)
//						.set("age", age).set("lastYearCosts", null)
//						.set("password", null).set("driver_license_type", null)
//						.set("create_time", null).set("outofdate_time", null)
//						.set("recorder_id", null).set("wechat_openid", open_id)
//						.set("real_name", null).set("gender", null)
//						.set("email", email).set("identity_no", identity_no)
//						.set("driver_license_type", null)
//						.set("drive_years", driveyears).set("device_id", null)
//						.set("home_addr_province", home_addr_province)
//						.set("home_addr_city", home_addr_city)
//						.set("home_addr_district", home_addr_district)
//						.set("home_addr_street", home_addr_street)
//						.set("receive_addr_provice", null)
//						.set("receive_addr_city", null)
//						.set("receive_addr_district", null)
//						.set("receive_addr_street", null).set("points", 0)
//						.set("apply_time", null).set("memberPrivileges", null)
//						.set("allowNumber", null).set("avatarType", null)
//						.set("if_member", null).set("userImgs", auto_img)
//						.set("phone_of_device", null).set("real_name", name)
//						.save();
//				// Integer
//				// userId=Device_user_Model.dao.findFirst("select id from device_user where telephone='"+phoneNumber+"'").get("id");
//				// Device_user_Model s = Device_user_Model.dao.findById(userId,
//				// "real_name,telephone,car_type,buyYear,car_no,installAddress,insurance,age,lastYearCosts");
//
//				Result aq = new Result();
//				HashMap<String, Object> map4 = new HashMap<String, Object>();
//				map4.put("verifyStatus", 1);
//				map4.put("submitStatus", 1);
//				map4.put("submitFail", "提交成功");
//				aq.setDataType(2);
//				aq.setStatusCode(-1);
//				aq.setValid(1);
//				aq.setdata(map4);
//				renderJson(aq);
//			} catch (Exception e) {
//				Result aq = new Result();
//				HashMap<String, Object> map5 = new HashMap<String, Object>();
//				map5.put("verifyStatus", 0);
//				map5.put("submitStatus", 0);
//				map5.put("submitFail", "验证码输入错误，提交失败");
//				aq.setDataType(2);
//				aq.setStatusCode(-1);
//				aq.setValid(0);
//				aq.setdata(map5);
//				renderJson(aq);
//			}
//		}
//	}
 	
 	
 	//获取积分、设备号、是否会员、产品有效期 	（vip签到X10）
 	public void my_ivi(){
//		String open_id=oAuth();
 		int pointsIncrease=0,score=0,vaild=0;
 		String insurance="";
 		int isSignIn=if_sign();//1签到，0未签到，-1表示游客不能签到
 		Integer device_id=null;
 		String memberKind=null;
 		UserDataMyIVI rt = new UserDataMyIVI();
 		Result aq = new Result();
		System.out.println("输出时间："+date);		
 		switch(check_regist()){
 		case 0:
 			score=0;
 			memberKind="游客";
 			rt.setPoints(0);
 			rt.setMemberKind("游客");	
 			rt.setValidity(vaild); //此处为假数据
 			rt.setIsSignIn(isSignIn);
 			rt.setPointsIncrease(pointsIncrease);
 			rt.setDeviceId("");
 			aq.setValid(1);
 			break;
 		case 1:			
 			System.out.println("积++++++++++++++++分执行到此0"); 			
 			Device_user_Model user = Device_user_Model.dao.findFirst("select * from device_user where wechat_openid='" + open_id
 					+ "'");
 			System.out.println("积++++++++++++++++分执行到此1"+user); 			
 			Integer userId=user.getInt("id");
 			System.out.println("积++++++++++++++++分执行到此2"+userId); 			
 			Points_Model msg = Points_Model.dao.findFirst("select * from points where user_id='" + userId+"'");
 			if(msg==null){
 				memberKind="普通会员";
 			}else{
 				System.out.println("积++++++++++++++++分执行到此2"+msg); 			
 				score=msg.getInt("sum");//sign_up的值在数据库sign_up表里面修改		
 				System.out.println("积++++++++++++++++分执行到此3"+score); 			
 				memberKind="普通会员";
 				vaild=0;			
 			}
 			rt.setPoints(score);
 			rt.setMemberKind(memberKind);	
 			rt.setValidity(vaild); //此处为假数据
 			rt.setIsSignIn(isSignIn);
 			rt.setPointsIncrease(pointsIncrease);
 			rt.setDeviceId("");
 			aq.setValid(1);
 			break;
 		case 2:
 			Device_user_Model vip_user = Device_user_Model.dao.findFirst("select * from device_user where wechat_openid='" + open_id+ "'");
 			Integer vip_userId=vip_user.getInt("id"); 			
 			System.out.println("积++++++++++++++++分执行到此6   "+vip_userId); 			
 			device_id=vip_user.getInt("device_id");		
 			insurance=vip_user.getStr("insurance");
 			System.out.println("积++++++++++++++++分执行到此7   "+insurance); 			
 			memberKind="VIP会员";
 			
 			try {
				vaild=daysBetween(date,insurance);
				if(vaild<=0)
					aq.setValid(0);//针对购买设备的 用户 0表示过期，1表示有效  100表示解析异常
				else
					aq.setValid(1);				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
	 			System.out.println("积++++++++++++++++分执行到此7   "+"有效期解析错误"); 			
			}
 			
 			Points_Model vip_msg = Points_Model.dao.findFirst("select * from points where user_id='" +
 					vip_userId+"'");//积分model	
 			System.out.println("积++++++++++++++++分执行到此6   "+vip_msg);
			score=vip_msg.getInt("sum");//sign_up的值在数据库sign_up表里面修改		
 			System.out.println("积++++++++++++++++分执行到此7++++++score："+score);	
 			rt.setPoints(score);
 			rt.setDeviceId(device_id);
 			rt.setMemberKind(memberKind);	
 			rt.setValidity(vaild); //此处为假数据
 			rt.setIsSignIn(isSignIn);
 			rt.setPointsIncrease(pointsIncrease);
 			System.out.println("积++++++++++++++++分执行到此8++++++rt："+rt);	
 			break;
 		default:
 			break;		
 		}
		
		aq.setDataType(2);
		aq.setStatusCode(-1);
		aq.setdata(rt);
		renderJson(aq);
 	}
 	
	public void linkapp(){
		String phone=getPara("telephone");
//		String open_id=oAuth();
		Device_user_Model u=Device_user_Model.dao.findFirst("select * from device_user where telephone='"+phone+"'");			 	
		if (null == u) {
			Result aq = new Result();
			aq.setDataType(2);
			aq.setStatusCode(4);
			aq.setValid(0);
			aq.setdata("绑定失败，您未在App端申请安装");
			renderJson(aq);
		} else if (!u.get("wechat_openid").equals(null)) {
			Result aq = new Result();
			aq.setDataType(2);
			aq.setStatusCode(4);
			aq.setValid(0);
			aq.setdata("您已绑定或注册，请直接使用");
			renderJson(aq);
			
		}else{
			 Integer userid=u.get("id");
			 Device_user_Model.dao.findById(userid).set("wechat_openid",open_id).update();
			 Result aq=new Result();
			 aq.setDataType(2);aq.setStatusCode(-1);
			 aq.setValid(1);
			 aq.setdata("绑定成功");	
			 renderJson(aq);
		}
	}
	
	//获取头像（走弯路了，获取这些东西，一个my_ivi接口就够了）
	public void head_img() { 
		Result aq = new Result();	
		String img_url="";
//		String open_id=oAuth();
		Device_user_Model head_img=Device_user_Model.dao.findFirst(
					"select userImgs from device_user where wechat_openid='" + open_id
					+ "'");
		System.out.println(">>>>>>>>>>>>>>>>>>>>head_img  =" + head_img);
		img_url=head_img.getStr("userImgs");
		System.out.println(">>>>>>>>>>>>>>>>>>>>img_url  =" + img_url);
		aq.setStatusCode(-1);
		aq.setDataType(1);
		aq.setdata(img_url);
		aq.setValid(1);
		renderJson(aq);
	}
	
	//更换头像
	public void changeHeadView(){
		boolean flag=false;
		Result result=new Result();
		int para=Integer.parseInt(getPara("headView"));
		Device_user_Model userHeadView=Device_user_Model.dao.findFirst("select userImgs,id from device_user where wechat_openid='" + open_id
					+ "'");
		switch(para){
		case 0:
			flag=userHeadView.set("userImgs", UrlHelper.BACK_DIR+"/head_img/p1.png").update();
			break;
		case 1:
			flag=userHeadView.set("userImgs", UrlHelper.BACK_DIR+"/head_img/p2.png").update();
			break;
		case 2:
			flag=userHeadView.set("userImgs", UrlHelper.BACK_DIR+"/head_img/p3.png").update();
			break;
		case 3:
			flag=userHeadView.set("userImgs", UrlHelper.BACK_DIR+"/head_img/p4.png").update();
			break;
		default:
			break;	
		}
		result.setStatusCode(-1);
		result.setDataType(1);
		if(flag)
			result.setdata("更换成功");
		else
			result.setdata("更换失败");			
		result.setValid(1);
		renderJson(result);
	}
	
	//我的积分
	public void points_info(){
//		String open_id=oAuth();
		Result aq=new Result();
		try{
			Integer userId=Device_user_Model.dao.findFirst("select id from device_user where wechat_openid='"+open_id+"'").getInt("id");	
			Integer uid=Points_Model.dao.findFirst("select id from points where user_id='"+userId+"'").get("id");
			Points_Model pi=Points_Model.dao.findById(uid,"date,sign_up,active,sum,active_time");		
			aq.setdata(pi);
			aq.setValid(1);
		}catch(Exception e){
			aq.setValid(0);//游客
			aq.setdata(0);			
		}		    		
		aq.setDataType(2);
		aq.setStatusCode(-1);
		renderJson(aq);
	}
	
	//积分排名
	public void user_rank() {
//		String open_id=oAuth();
		System.out.println("user_rank openid =" + open_id);
		int i=0;
		Result aq = new Result();
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		List<Points_Model> list_point=Points_Model.dao.find("select user_id,sum from points order by sum desc");
		if(list_point.size()<=10)
			for(Points_Model lp :list_point){
				HashMap<String, String> map = new HashMap<String, String>();
				try{
					Integer user_id=lp.getInt("user_id");
					Integer points=lp.getInt("sum");				
					String real_name=Device_user_Model.dao.findFirst(
							"select real_name from device_user where id='" + user_id + "'")
							.get("real_name");				
					map.put("name", real_name);
					map.put("score", String.valueOf(points));
					map.put("rank", String.valueOf(i++));	
				}catch(Exception e){
					map.put("name", "**");
					map.put("score", "**");
					map.put("rank", "**");						
				}
				list.add(map);
			}
		else
			for(int j=0;j<10;j++){				
				HashMap<String, String> map = new HashMap<String, String>();
				try{
					Integer user_id=list_point.get(j).getInt("user_id");
					System.out.println("_+_+_+_+_+_+_+_+_+  user_id:  "+user_id);
					Integer points=list_point.get(j).getInt("sum");				
					System.out.println("_+_+_+_+_+_+_+_+_+  points:  "+points);
					String real_name=Device_user_Model.dao.findFirst(
							"select real_name from device_user where id='" + user_id + "'")
							.get("real_name");
					System.out.println("_+_+_+_+_+_+_+_+_+  real_name:  "+real_name);
					map.put("name", real_name);
					map.put("score", String.valueOf(points));
					map.put("rank", String.valueOf(j+1));					
				}catch(Exception e){
					map.put("name", "**");
					map.put("score", "**");
					map.put("rank", "**");										
				}
				list.add(map);
			}
		aq.setDataType(2);
		aq.setStatusCode(-1);
		aq.setValid(1);
		aq.setdata(list);
		renderJson(aq);
	}
					
	//检验是否注册
	public int check_regist(){
		int flag=-1;
		System.out.println("+++++++——————————————++++++open_id："+open_id);
		Device_user_Model x = Device_user_Model.dao
				.findFirst("select telephone,device_id from device_user where wechat_openid='"+ open_id + "'");		
		System.out.println("+++++++——————————————++++++x："+x);
		
		String tele=x.getStr("telephone");
		Integer dev_id=x.getInt("device_id");
		
		if(tele==null&&dev_id==null) //游客
			flag=0;
		else if(tele!=null&&dev_id==null) //普通
			flag=1;
		else if(tele!=null&&dev_id!=null) //vip
			flag=2;
		System.out.println("+++++++——————————————++++++这是一位："+flag);
		return flag;	
	}
	
/*	//是否注册	
	public void if_regist() {
		Result aq = new Result();	
		switch(check_regist()){
		case 0:			//游客
			aq.setDataType(1);
			aq.setStatusCode(-1);
			aq.setValid(0);
			aq.setdata("0");
			System.out.println("+++++++——————————————++++++这是一位：游客");
			break;
		case 1:			//普通会员
			Device_user_Model x = Device_user_Model.dao
			.findFirst("select age, telephone,real_name,drive_years,email,home_addr_street,identity_no from device_user where wechat_openid='"+ open_id + "'");
			aq.setDataType(2);
			aq.setStatusCode(-1);
			aq.setValid(1);
			aq.setdata(x);
			System.out.println("+++++++——————————————++++++这是一位：普通会员");
			break;
		case 2:			//vip
			Device_user_Model xx = Device_user_Model.dao
			.findFirst("select age, telephone,real_name,drive_years,email,home_addr_street,identity_no from device_user where wechat_openid='"+ open_id + "'");
			aq.setDataType(2);
			aq.setStatusCode(-1);
			aq.setValid(1);
			aq.setdata(xx);
			System.out.println("+++++++——————————————++++++这是一位：vip会员");
			break;
		default:
			break;			
		}
		renderJson(aq);	
	}*/
	
	//驾驶得分、击败车友比例，data为数组
	public void drive_points() {
//		String open_id=oAuth();
		StatisticsService ss = new StatisticsService();
		Result aq = new Result();
		int points = 0;
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		HashMap<String,String> map=new HashMap<String,String>();
		System.out.println("+++++++++++++" + "取驾驶得分openid:" + open_id);
		switch (check_regist()) {
		case 0:
			points = 0;
			map.put("points", String.valueOf(points));
			map.put("beat_percent", "0.0%");	
			break;
		case 1:
			points = 0;
			map.put("points", String.valueOf(points));
			map.put("beat_percent", "0.0%");	
			break;
		case 2:
			Device_user_Model p = Device_user_Model.dao.findFirst("select id,device_id,real_name from device_user where wechat_openid='"
							+ open_id + "'");		
			Integer deviceId = p.getInt("device_id");
			String real_name=p.getStr("real_name");
			Drive_points x = Drive_points.dao.findFirst("select * from drive_points where device_id='"
							+ deviceId + "'");	
			if(x==null){
				new Drive_points().set("device_id", deviceId).set("points", 60).set("date", new Date()).set("real_name", real_name).save();
			}else{
				System.out.println("+++++++++++++" + "取驾驶得分deviceId:" + deviceId);
				
				DayStatisticsResult dsr = ss.getDeviceDayStatisticsResult(deviceId,new Date());
				int dayAlarms = dsr.getDayAlarms();
				int seriousAlarms = dsr.getSeriousAlarms();
				
				System.out.println("dayAlarms =" + dayAlarms + "seriousAlarms ="+ seriousAlarms);	
				float hours = dsr.getTotalTime();//没有使用设备，基础得分60
				if (hours == 0) {
					points = 60;
				} else {
					if (dayAlarms > 25) {
						points = 25;// 超过25次，直接划为垃圾挡
					} else {
						points = 100 - (dayAlarms - seriousAlarms) * 5- seriousAlarms * 10;// 实际得分
						if (points <= 30)
							points = 30;
						else if(points==100){
							points = 99;//最高分99
						}
					}
				}			
				x.set("points", points).set("date", new Date()).update();			
				System.out.println("+++++++++++++++++++++++++++++++++++drive_points中的x =" + x);
			}
			Drive_points self_rank=Drive_points.dao.findFirst("select COUNT( * ) from drive_points WHERE points >( SELECT points FROM drive_points WHERE device_id ="+deviceId+")");	
			int rank=self_rank.getLong("COUNT( * )").intValue();
			int all_num=Drive_points.dao.findFirst("select COUNT( * ) from drive_points").getLong("COUNT( * )").intValue();
			System.out.println("+++++++++++++++++++++++++++++++++++drive_rank中的rank和all_num =" + rank+"   "+all_num);
			String beat_percent=(int) Math.rint((1 - ((float) (rank + 1) /all_num)) * 100) + "%";
			System.out.println("+++++++++++++++++++++++++++++++++++drive_rank中的beat_percent =" + beat_percent+"   ");
			map.put("points", String.valueOf(points));
			map.put("beat_percent", beat_percent);
			break;
		default:
			break;
		}
		list.add(map);
		aq.setValid(1);//能点击驾驶比例按钮
		aq.setDataType(2);
		aq.setdata(list);
		aq.setStatusCode(-1);
		System.out.println("+++++++++++++驾驶得分:" + points);
		renderJson(aq);
	}
		
	//驾驶排名
	public void drive_rank() {
//		String open_id=oAuth();
		List<HashMap<String, String>> q = new ArrayList<HashMap<String, String>>();
		Result aq = new Result();
		System.out.println("drive_rank openid =" + open_id);
		Device_user_Model uq = Device_user_Model.dao
				.findFirst("select device_id,id,real_name from device_user where wechat_openid='"
						+ open_id + "'");
		if(uq==null){
			aq.setValid(0);//来区分是否注册的用户。0未注册，1已注册。
		}else{
			Integer deviceId = uq.getInt("device_id");
			String real_name = uq.getStr("real_name");
			
			System.out.println("  device_id  "+ deviceId + "  name  " + real_name);
			List<Drive_points> ur = Drive_points.dao.find("select * from drive_points order by points desc");
			// 遍历以获取名次
			if (ur.size() < 5) {
				for (int m = 0; m < ur.size(); m++) {
					// 必须放在循环内，否则map之前的相同键值会被替换
					// 打印出前五名的名字及分数
					HashMap<String, String> map = new HashMap<String, String>();
					String rnames = ur.get(m).get("real_name");
					String points = ur.get(m).get("points").toString();
					String device_id= ur.get(m).get("device_id").toString();
					map.put("name", rnames);
					map.put("score", points);
					map.put("rank", "" + (m + 1));
					map.put("device_id", device_id);
					map.put("beat_percent",""+ (int) Math.rint((1 - ((float) (m + 1) / ur.size())) * 100) + "%");
					q.add(map);
				}
				System.out.println("驾驶排名" + q);
			} else {
				for (int m = 0; m < 5; m++) {
					// 必须放在循环内，否则map之前的相同键值会被替换
					// 打印出前五名的名字及分数
					HashMap<String, String> map = new HashMap<String, String>();
					String rnames = ur.get(m).get("real_name");
					String points = ur.get(m).get("points").toString();
					String device_id= ur.get(m).get("device_id").toString();
					map.put("name", rnames);
					map.put("device_id", device_id);
					map.put("score", points);
					map.put("rank", "" + (m + 1));
					map.put("beat_percent",""+ (int) Math.rint((1 - (float) (m + 1)/ ur.size()) * 100) + "%");
					q.add(map);
				}
				System.out.println("驾驶排名" + q);
			}		
			aq.setValid(1);//
		}
		aq.setDataType(2);
		System.out.println("驾驶排名已执行到此");
		aq.setStatusCode(-1);
		aq.setdata(q);
		renderJson(aq);
	}
	
	//更新后台支付状态
	public void checkPay(){
//		String open_id=oAuth();
		System.out.println("++++++++++++++++++++++++++checkPay中 开始执行" );
		Result aq = new Result();
		System.out.println("++++++++++++++++++++++++++checkPay中 openid =" + open_id);
		Device_user_Model uq = Device_user_Model.dao
				.findFirst("select id,device_id,check_pay from device_user where wechat_openid='"
						+ open_id + "'");
		uq.set("check_pay", 1).update();		
		System.out.println("++++++++++++++++++++++++++checkPay中已完成支付:"+uq.getStr("check_pay"));
		aq.setDataType(2);
		aq.setStatusCode(-1);
		aq.setValid(1);//
		aq.setdata("支付成功");
		renderJson(aq);
	}
}



