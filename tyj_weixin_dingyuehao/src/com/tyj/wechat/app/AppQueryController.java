package com.tyj.wechat.app;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.weixin.demo.UrlHelper;
import com.tyj.wechat.app.bean.DayStatisticsResult;
import com.tyj.wechat.app.bean.DriveTrend;
import com.tyj.wechat.app.bean.Result;
import com.tyj.wechat.app.bean.UserDataMain;
import com.tyj.wechat.app.bean.UserDataMyIVI;
import com.tyj.wechat.app.bean.UserInfo;
import com.tyj.wechat.app.model.Device_user_Model;
import com.tyj.wechat.app.model.Drive_points;
import com.tyj.wechat.app.model.Points_Model;
import com.tyj.wechat.app.model.Sign_up_Model;
import com.tyj.wechat.app.service.JavaMail;
import com.tyj.wechat.app.service.MD5;
import com.tyj.wechat.app.service.MyCaptchaRender;
import com.tyj.wechat.app.service.StatisticsService;
import com.tyj.wechat.app.service.UserDataService;

public class AppQueryController extends Controller {

	String appliedSessionKey = "appliedDevice";

	public void index() {
		// render("/applyDevice.html");
	}

	public void driver_login() {
		String phone = getPara("phone");
		String psw = getPara("password");
		String password = MD5.GetMD5Code(psw);
		Device_user_Model i = Device_user_Model.dao
				.findFirst("select id from device_user where telephone='"
						+ phone + "' and password='" + password + "'");
		if (null != i) {
			Integer id = i.get("id");
			// Device_user_Model user = Device_user_Model.dao.findById(id,
			// "id,avatarType");
			Result a = new Result();
			a.setDataType(2);
			a.setStatusCode(-1);
			a.setValid(1);
			a.setdata(id);
			renderJson(a);
		} else {
			Result a = new Result();
			a.setDataType(2);
			a.setStatusCode(-1);
			a.setValid(0);
			a.setdata("用户名或密码错误，请重新输入");
			renderJson(a);
		}
	}

	public void driving_habits_today() {
		Result re = new Result();
		// app端的
		Integer userId = getParaToInt("userId");
		// 转成device_id
		Integer deviceId = Device_user_Model.dao.findById(userId, "device_id")
				.getInt("device_id");
		if (userId == null) {
			re.setStatusCode(1);
		} else {
			StatisticsService ss = new StatisticsService();
			DayStatisticsResult dsr = ss.getDeviceDayStatisticsResult(deviceId,
					new Date());
			re.setStatusCode(-1);
			re.setDataType(2);
			re.setdata(dsr);
		}
		renderJson(re);
	}

	public void driving_trend() {
		Result re = new Result();
		// app端的
		Integer userId = getParaToInt("userId");
		// 转成device_id
		userId = Device_user_Model.dao.findById(userId, "device_id").getInt(
				"device_id");

		if (userId == null) {
			re.setStatusCode(1);
		} else {
			Integer interval = getParaToInt("interval", 0);
			StatisticsService ss = new StatisticsService();
			Date endDate = new Date();// 今天
			Date startDate = getStartDate(interval, endDate);
			DriveTrend trend = ss.getDriveTrend(userId, interval, startDate,
					endDate);
			if (trend.getKeypoint1() == null
					|| trend.getKeypoint1().size() == 0) {
				re.setStatusCode(4);
			} else {
				re.setStatusCode(-1);
				re.setDataType(2);
			}
			re.setdata(trend);
		}
		renderJson(re);
	}

	private Date getStartDate(Integer interval, Date endDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(endDate);
		if (interval == 0) {
			c.add(Calendar.DAY_OF_MONTH, -3);
		} else if (interval == 1) {
			c.add(Calendar.DAY_OF_MONTH, -7);
		} else if (interval == 2) {
			c.add(Calendar.MONTH, -1);
		} else {
			c.add(Calendar.MONTH, -6);
		}
		return c.getTime();
	}

	public void user_data_main() {
		Result re1 = new Result();
		Integer userId = getParaToInt("userId");
		// 转成device_id
		userId = Device_user_Model.dao.findById(userId, "device_id").getInt(
				"device_id");
		if (userId == null) {
			re1.setStatusCode(1);
		} else {
			UserDataService ss1 = new UserDataService();
			UserDataMain dsr1 = ss1.uds(userId);
			re1.setStatusCode(-1);
			re1.setDataType(2);
			re1.setdata(dsr1);
		}
		renderJson(re1);
	}

	public void query_user_info() {
		Integer userId = getParaToInt("userId");
		// 转成device_id
		// userId=Device_user_Model.dao.findById(userId,
		// "device_id").getInt("device_id");

		UserInfo rs = new UserInfo();
		Device_user_Model x = Device_user_Model.dao
				.findById(
						userId,
						"real_name,allowNumber,gender,age,driver_license_type,car_no,allowNumber,memberPrivileges");
		rs.setAge(x.getStr("age"));
		try {
			rs.setAllowNumber(x.getInt("allowNumber"));
		} catch (Exception e) {
			rs.setAllowNumber(0);
		}
		rs.setGender(x.getStr("gender"));
		rs.setLicenseNumber(x.getStr("car_no"));
		rs.setLicenseType(x.getStr("driver_license_type"));
		rs.setMemberPrivileges(x.getStr("memberPrivileges"));
		rs.setName(x.getStr("real_name"));
		// rs.setUserId(x.getInt("device_id"));
		Result aq = new Result();
		aq.setDataType(2);
		aq.setStatusCode(-1);
		aq.setValid(1);
		aq.setdata(rs);
		renderJson(aq);
	}

	public void update_user_info() {
		Integer userId = getParaToInt("userId");
		String name = getPara("name");
		String gender = getPara("gender");
		String age = getPara("age");
		String licenseType = getPara("licenseType");
		String licenseNumber = getPara("licenseNumber");
		int allowNumber = getParaToInt("allowNumber");
		// String memberPrivileges=getPara("memberPrivileges");
		try {
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

	public void installInfo() {
		// int userId=getParaToInt("userId");
		String installInfo = getPara("installInfo");
		// 默认头像
		String auto_img = UrlHelper.BACK_DIR+"/head_img/p1.png";

		// String inputRandomCode = getPara("verificationCode");
		// 把数组型的json转为json
		JSONObject jo = JSON.parseObject(installInfo);
		String inputRandomCode = jo.get("verificationCode").toString();
		String psw = jo.get("password").toString();
		String password = MD5.GetMD5Code(psw);
		String inputRandomCode1 = MD5.GetMD5Code(inputRandomCode);
		String name = jo.get("name").toString();
		String phoneNumber = jo.get("phoneNumber").toString();
		String identity_no = jo.get("identity_no").toString();
		String email = jo.get("email").toString();
		String driveyears = jo.get("drive-year").toString();
		String home_addr_city = jo.get("home_addr_city").toString();
		String home_addr_street = jo.get("home_addr_street").toString();
		String home_addr_district = jo.get("home_addr_district").toString();
		String home_addr_province = jo.get("home_addr_province").toString();
		// String insurance=jo.get("insurance").toString();
		String age = jo.get("age").toString();
		// String verificationCode=jo.get("verificationCode").toString();
		Object u = Device_user_Model.dao
				.findFirst("select telephone from device_user where telephone='"
						+ phoneNumber + "'");
		boolean validate = MyCaptchaRender.validate(this, inputRandomCode);
		if (null != u) {
			Result aq = new Result();
			HashMap<String, Object> map5 = new HashMap<String, Object>();
			map5.put("verifyStatus", 0);
			map5.put("submitStatus", 0);
			map5.put("submitFail", "您已申请，请勿重复申请，谢谢");
			aq.setDataType(2);
			aq.setStatusCode(-1);
			aq.setValid(0);
			aq.setdata(map5);
			renderJson(aq);
		} else {
			if (!validate) {
				Result aq = new Result();
				HashMap<String, Object> map5 = new HashMap<String, Object>();
				map5.put("verifyStatus", 0);
				map5.put("submitStatus", 0);
				map5.put("submitFail", "验证码输入错误，提交失败");
				aq.setDataType(2);
				aq.setStatusCode(-1);
				aq.setValid(0);
				aq.setdata(map5);
				renderJson(aq);
			} else {
				try {
					new Device_user_Model().set("id", null)
							.set("telephone", phoneNumber)
							.set("car_type", null).set("buyYear", null)
							.set("car_no", null).set("installAddress", null)
							.set("insurance", null).set("age", age)
							.set("lastYearCosts", null).set("password", null)
							.set("driver_license_type", null)
							.set("create_time", null)
							.set("outofdate_time", null)
							.set("recorder_id", null)
							.set("wechat_openid", null).set("real_name", null)
							.set("gender", null).set("email", email)
							.set("identity_no", identity_no)
							.set("driver_license_type", null)
							.set("drive_years", driveyears)
							.set("device_id", null)
							.set("home_addr_province", home_addr_province)
							.set("home_addr_city", home_addr_city)
							.set("home_addr_district", home_addr_district)
							.set("home_addr_street", home_addr_street)
							.set("receive_addr_provice", null)
							.set("receive_addr_city", null)
							.set("receive_addr_district", null)
							.set("receive_addr_street", null).set("points", 0)
							.set("apply_time", null)
							.set("memberPrivileges", null)
							.set("allowNumber", null).set("avatarType", null)
							.set("if_member", null).set("userImgs", auto_img)
							.set("phone_of_device", null)
							.set("real_name", name).save();
					// Integer
					// userId=Device_user_Model.dao.findFirst("select id from device_user where telephone='"+phoneNumber+"'").get("id");
					// Device_user_Model s =
					// Device_user_Model.dao.findById(userId,
					// "real_name,telephone,car_type,buyYear,car_no,installAddress,insurance,age,lastYearCosts");
					Result aq = new Result();
					HashMap<String, Object> map4 = new HashMap<String, Object>();
					map4.put("verifyStatus", 1);
					map4.put("submitStatus", 1);
					map4.put("submitFail", "提交成功");
					aq.setDataType(2);
					aq.setStatusCode(-1);
					aq.setValid(1);
					// aq.setdata(s);
					aq.setdata(map4);
					renderJson(aq);
				} catch (Exception e) {
					e.printStackTrace();
					Result aq = new Result();
					HashMap<String, Object> map5 = new HashMap<String, Object>();
					map5.put("verifyStatus", 1);
					map5.put("submitStatus", 0);
					map5.put("submitFail", "填写信息不正确，请重新填写");
					aq.setDataType(2);
					aq.setStatusCode(-1);
					aq.setValid(0);
					aq.setdata(map5);
					renderJson(aq);
				}
			}
		}
	}

	public void my_ivi() {
		UserDataMyIVI rt = new UserDataMyIVI();
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		c.setTime(new Date());
		String date = sf.format(c.getTime());
		Integer userId = getParaToInt("userId");
		// Integer
		// userId=Device_user_Model.dao.findFirst("select device_id from device_user where id='"+user_Id+"'").getInt("device_id");
		Object msg = Sign_up_Model.dao
				.findFirst("select * from sign_up where user_id='" + userId
						+ "' and date='" + date + "'");
		if (null == msg) {
			rt.setIsSignIn(0);
		} else {
			rt.setIsSignIn(1);
		}
		// 积分之和
		Integer uid = Points_Model.dao.findFirst(
				"select id from points where user_id='" + userId + "'").get(
				"id");
		Points_Model s = Points_Model.dao.findById(uid, "sign_up,active");
		int points1 = s.get("sign_up");
		int points2 = s.get("active");
		int points = points1 + points2;
		Points_Model.dao.findById(uid).set("sum", points).set("date", date)
				.update();
		Device_user_Model.dao.findById(userId).set("points", points).update();
		rt.setPoints(points);
		rt.setPointsIncrease(1);
		rt.setValidity(66);
		Integer memberKind = Device_user_Model.dao.findFirst(
				"select * from device_user where id='" + userId + "'").get(
				"if_member", 0);
		rt.setMemberKind(memberKind);
		Integer device_id = Device_user_Model.dao.findFirst(
				"select device_id from device_user where id='" + userId + "'")
				.get("device_id", 0);
		rt.setDeviceId(device_id);
		Result aq = new Result();
		aq.setDataType(2);
		aq.setStatusCode(-1);
		aq.setValid(1);
		aq.setdata(rt);
		renderJson(aq);
	}

	public void if_sign() {
		Integer userId = getParaToInt("userId");
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		String date = year + "-" + month + "-" + day;
		// try{
		Object msg1 = Sign_up_Model.dao
				.findFirst("select * from sign_up where user_id='" + userId
						+ "' and date='" + date + "'");
		if (null != msg1) {
			Result a = new Result();
			a.setDataType(2);
			a.setStatusCode(-1);
			a.setValid(1);
			a.setdata("今日已签到");
			renderJson(a);
		} else {
			Result a = new Result();
			a.setDataType(2);
			a.setStatusCode(-1);
			a.setValid(0);
			a.setdata("今日未签到");
			renderJson(a);
		}
	}

	public void sign_up() {
		Integer userId = getParaToInt("userId");
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		String date = year + "-" + month + "-" + day;
		// try{
		Object msg = Sign_up_Model.dao
				.findFirst("select * from sign_up where user_id='" + userId
						+ "' and date='" + date + "'");
		Object msg2 = Device_user_Model.dao.findFirst(
				"select device_id from device_user where id='" + userId + "'")
				.get("device_id");
		if (null == msg2) {
			Result a = new Result();
			a.setDataType(2);
			a.setStatusCode(-1);
			a.setValid(1);
			a.setdata("您未完善信息，不能签到哦");
			renderJson(a);
		} else if (null != msg) {
			Result a = new Result();
			a.setDataType(2);
			a.setStatusCode(1);
			a.setValid(1);
			a.setdata("今日已签到");
			renderJson(a);
		}
		// }
		// catch(Exception e)
		else {
			new Sign_up_Model().set("s_points", 0).set("id", null)
					.set("date", date).set("user_id", userId).save();
			// int points=Device_user_Model.dao.findById(userId,
			// "points").get("points");
			// Device_user_Model.dao.findById(userId).set("points",points+1).update();
			Integer uid = Sign_up_Model.dao.findFirst(
					"select id from sign_up where user_id='" + userId + "'")
					.get("id");
			int points = Sign_up_Model.dao.findById(uid, "s_points").get(
					"s_points");
			Sign_up_Model.dao.findById(uid).set("s_points", points + 1)
					.update();
			Integer pid = Points_Model.dao.findFirst(
					"select id from points where user_id='" + userId + "'")
					.get("id");
			Points_Model.dao.findById(pid).set("sign_up", points + 1).update();
			//
			Result a = new Result();
			a.setDataType(2);
			a.setStatusCode(-1);
			a.setValid(1);
			a.setdata("签到成功");
			renderJson(a);
			// e.printStackTrace();
		}
	}

	// 完善个人信息
	public void complete_info() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		String date = year + "-" + month + "-" + day;
		String userId = getPara("userId");
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
		// String phone=getPara("phone");
		// String password=MD5.GetMD5Code(getPara("password"));//Md5加密
		Device_user_Model.dao.findById(userId).set("device_id", device_id)
				.set("car_type", carType).set("buyYear", buyYear)
				.set("car_no", carNumber).set("insurance", insurance)
				.set("lastYearCosts", lastYearCosts)
				.set("installAddress", installAddress).set("if_member", 1)
				.update();
		// Device_user_Model q = Device_user_Model.dao.findById(userId,
		// "device_id,email,identity_no,drive_years,home_addr_province,home_addr_city ,home_addr_district,home_addr_street,apply_time");
		// 写到积分表里
		Points_Model s = Points_Model.dao
				.findFirst("select * from points where user_id='" + userId
						+ "'");
		if (null == s) {
			new Points_Model().set("active", 100).set("id", null)
					.set("sum", 100).set("sign_up", 0).set("active_time", date)
					.set("date", null).set("user_id", userId).save();

			Device_user_Model uq = Device_user_Model.dao
					.findFirst("select * from device_user where id='" + userId
							+ "'");
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
		// Device_user_Model qt = Device_user_Model.dao.findById(userId,
		// "device_id,car_type,buyYear,car_no,insurance,lastYearCosts,installAddress");
		Result aq = new Result();
		aq.setDataType(2);
		aq.setStatusCode(-1);
		aq.setValid(1);
		aq.setdata("保存成功");
		// aq.setdata(q);
		renderJson(aq);
	}

	public void back_comp() {
		Integer userId = getParaToInt("userId");
		// Integer
		// userId=Device_user_Model.dao.findFirst("select id from device_user where wechat_openid='"+open_id+"'").getInt("id");
		Device_user_Model qt = Device_user_Model.dao
				.findById(userId,
						"device_id,car_type,buyYear,car_no,insurance,lastYearCosts,installAddress");
		Result aq = new Result();
		aq.setDataType(2);
		aq.setStatusCode(-1);
		aq.setValid(1);
		aq.setdata(qt);
		renderJson(aq);
	}

	public void show_info() {
		String userId = getPara("userId");
		Device_user_Model q = Device_user_Model.dao
				.findById(
						userId,
						"device_id,email,identity_no,drive_years,home_addr_province,home_addr_city ,home_addr_district,home_addr_street,apply_time");
		Result aq = new Result();
		aq.setDataType(2);
		aq.setStatusCode(-1);
		aq.setValid(1);
		aq.setdata(q);
		renderJson(aq);
	}

	public void head_img() {
		String userId = getPara("userId");
		String img_name = getPara("img_name");
		if (null != img_name) {
			String img_url = UrlHelper.BACK_DIR+"/head_img/"
					+ img_name + ".png";
			Device_user_Model.dao.findById(userId).set("userImgs", img_url)
					.update();
			Result aq = new Result();
			aq.setDataType(2);
			aq.setStatusCode(-1);
			aq.setValid(1);
			aq.setdata("修改头像成功");
			renderJson(aq);
		} else {
			String img_url = Device_user_Model.dao.findById(userId, "userImgs")
					.getStr("userImgs");
			Result aq = new Result();
			aq.setDataType(2);
			aq.setStatusCode(-1);
			aq.setValid(1);
			aq.setdata(img_url);
			renderJson(aq);
		}
	}

	/**
	 * 验证码
	 */
	public void verification_code_url() {
		render(new MyCaptchaRender(60, 22, 4, true));
	}

	public void points_info() {
		String userId = getPara("userId");
		Integer uid = Points_Model.dao.findFirst(
				"select id from points where user_id='" + userId + "'").get(
				"id");
		Points_Model pi = Points_Model.dao.findById(uid,
				"date,sign_up,active,sum,active_time");
		Result aq = new Result();
		aq.setDataType(2);
		aq.setStatusCode(-1);
		aq.setValid(1);
		aq.setdata(pi);
		renderJson(aq);
	}

	public void test() {
		// JavaMail a=new JavaMail(true);//(false).sdMail();
		// a.sdMail();
		String name = "haha";
		String age = "18";
		String contents = "申请人姓名:" + name + "\n" + "申请人年龄:" + age + "\n";
	}

	public void user_rank() {
		String userId = getPara("userId");
		Device_user_Model r = Device_user_Model.dao
				.findFirst("select real_name,device_id from device_user where id='"
						+ userId + "'");
		String real_name = r.get("real_name");
		Integer deviceId = r.get("device_id");
		// Device_user_Model.dao.find("select @rank:='"+0+"' from device_user");
		List<Device_user_Model> ur = Device_user_Model.dao
				.find("select real_name,points,device_id from device_user order by points desc");
		// 遍历以获取名次
		if (ur.size() < 5) {
			List q = new ArrayList();
			for (int m = 0; m < 5; m++) {
				// 必须放在循环内，否则map之前的相同键值会被替换
				HashMap<String, String> map = new HashMap<String, String>();
				String rnames = ur.get(m).get("real_name");
				String points = ur.get(m).get("points").toString();
				map.put("name", rnames);
				map.put("score", points);
				map.put("rank", "" + (m + 1));
				q.add(map);
				Result aq = new Result();
				aq.setDataType(2);
				aq.setStatusCode(-1);
				aq.setValid(1);
				aq.setdata(q);
				renderJson(aq);
			}
		} else {
			List q = new ArrayList();
			for (int m = 0; m < 5; m++) {
				// 必须放在循环内，否则map之前的相同键值会被替换
				HashMap<String, String> map = new HashMap<String, String>();
				String rnames = ur.get(m).get("real_name");
				String points = ur.get(m).get("points").toString();
				map.put("name", rnames);
				map.put("score", points);
				map.put("rank", "" + (m + 1));
				q.add(map);
			}
			for (int j = 1; j <= ur.size(); j++) {
				Integer device_id = ur.get(j - 1).getInt("device_id");
				if (device_id.equals(deviceId)) {
					if (j <= 5) {// 前五名
						Result aq = new Result();
						aq.setDataType(2);
						aq.setStatusCode(-1);
						aq.setValid(1);
						aq.setdata(q);
						renderJson(aq);

					} else {
						HashMap<String, String> map = new HashMap<String, String>();
						String points = ur.get(j - 1).get("points").toString();
						map.put("name", real_name);
						map.put("score", points);
						map.put("rank", "" + j);
						q.add(map);
						Result aq = new Result();
						aq.setDataType(2);
						aq.setStatusCode(-1);
						aq.setValid(1);
						aq.setdata(q);
						renderJson(aq);
					}
					break;
				}
			}// for's

		}
	}

	public void drive_points() {
		Integer user_id = getParaToInt("userId");
		Device_user_Model p = Device_user_Model.dao
				.findFirst("select device_id,real_name from device_user where id='"
						+ user_id + "'");
		// Integer userId=p.get("id");
		Integer deviceId = p.get("device_id");
		String real_name = p.get("real_name");
		StatisticsService ss = new StatisticsService();
		DayStatisticsResult dsr = ss.getDeviceDayStatisticsResult(user_id,
				new Date());
		int dayAlarms = dsr.getDayAlarms();
		int seriousAlarms = dsr.getSeriousAlarms();
		float hours = dsr.getTotalTime();
		int points = 0;
		if (hours == 0) {
			points = 60;
		} else {
			if (dayAlarms > 25) {
				points = 25;// 超过25次，直接划为垃圾挡
			} else {
				points = 100 - (dayAlarms - seriousAlarms) * 5 - seriousAlarms
						* 10;// 实际得分
				if (points <= 30)
					points = 30;
			}

			Object t = Drive_points.dao
					.findFirst("select * from drive_points where device_id='"
							+ deviceId + "'");
			if (null == t) {
				new Drive_points().set("real_name", real_name).set("id", null)
						.set("device_id", deviceId).set("points", points)
						.set("date", new Date()).save();
			} else {
				Integer id = Drive_points.dao.findFirst(
						"select id from drive_points where device_id='"
								+ deviceId + "'").get("id");

				Drive_points.dao.findById(id).set("points", points)
						.set("date", new Date()).update();
			}
			Result aq = new Result();
			aq.setDataType(2);
			aq.setStatusCode(-1);
			aq.setValid(1);
			aq.setdata(points);
			renderJson(aq);

		}
	}

	public void drive_rank() {
		Integer user_id = getParaToInt("userId");
		Device_user_Model uq = Device_user_Model.dao
				.findFirst("select device_id,real_name from device_user where id='"
						+ user_id + "'");
		// Integer userId=uq.getInt("id");
		Integer deviceId = uq.getInt("device_id");
		String real_name = uq.getStr("real_name");
		// Integer
		// deviceId=Device_user_Model.dao.findFirst("select real_name,device_id from device_user where id='"+userId+"'").get("device_id");
		// Device_user_Model.dao.find("select @rank:='"+0+"' from device_user");
		List<Drive_points> ur = Drive_points.dao
				.find("select * from drive_points  order by points desc");
		System.out.println("user" + deviceId);
		// 遍历以获取名次
		if (ur.size() < 5) {
			List q = new ArrayList();
			for (int m = 0; m < ur.size(); m++) {
				// 必须放在循环内，否则map之前的相同键值会被替换
				// 打印出前五名的名字及分数
				HashMap<String, String> map = new HashMap<String, String>();
				String rnames = ur.get(m).get("real_name");
				String points = ur.get(m).get("points").toString();
				map.put("name", rnames);
				map.put("score", points);
				map.put("rank", "" + (m + 1));
				map.put("beat_percent",
						""
								+ (int) Math.rint((1 - ((float) (m + 1) / ur
										.size())) * 100) + "%");
				q.add(map);
				Result aq = new Result();
				aq.setDataType(2);
				aq.setStatusCode(-1);
				aq.setValid(1);
				aq.setdata(q);
				renderJson(aq);
			}
		} else {
			List q = new ArrayList();
			for (int m = 0; m < 5; m++) {
				// 必须放在循环内，否则map之前的相同键值会被替换
				// 打印出前五名的名字及分数
				HashMap<String, String> map = new HashMap<String, String>();
				String rnames = ur.get(m).get("real_name");
				String points = ur.get(m).get("points").toString();
				map.put("name", rnames);
				map.put("score", points);
				map.put("rank", "" + (m + 1));
				map.put("beat_percent",
						""
								+ (int) Math.rint((1 - (float) (m + 1)
										/ ur.size()) * 100) + "%");
				q.add(map);
			}
			for (int j = 1; j <= ur.size(); j++) {
				Integer device_id = ur.get(j - 1).getInt("device_id");
				System.out.println("device_id" + device_id);
				if (device_id.equals(deviceId)) {
					System.out.println("xiang deng1");
					if (j <= 5) {// 在前五名中
						// 添加一个第六组标识
						System.out.println("xiang deng");
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("name", real_name);
						map.put("score", "");
						map.put("rank", "");
						map.put("beat_percent",
								""
										+ (int) Math.rint((1 - (float) j
												/ ur.size()) * 100) + "%");
						q.add(map);
						Result aq = new Result();
						aq.setDataType(2);
						aq.setStatusCode(-1);
						aq.setValid(1);
						aq.setdata(q);
						renderJson(aq);

					} else {
						// 不在前五名中，加一个自己的排位信息
						HashMap<String, String> map = new HashMap<String, String>();
						String points = ur.get(j - 1).get("points").toString();
						map.put("name", real_name);
						map.put("score", points);
						map.put("rank", "" + j);
						map.put("beat_percent",
								""
										+ (int) Math.rint((1 - (float) j
												/ ur.size()) * 100) + "%");
						q.add(map);
						Result aq = new Result();
						aq.setDataType(2);
						aq.setStatusCode(-1);
						aq.setValid(1);
						aq.setdata(q);
						renderJson(aq);
					}
					break;
				}
			}// for's
		}
	}
}
