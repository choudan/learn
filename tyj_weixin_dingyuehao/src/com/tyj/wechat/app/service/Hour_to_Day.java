package com.tyj.wechat.app.service;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.tyj.wechat.app.model.DeviceGpsInfos;
import com.tyj.wechat.app.model.Hour_to_day;


public class Hour_to_Day {
	 Calendar dth=Calendar.getInstance();
	 private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	 public void hour_to_day() {
		
	 dth.setTime(new Date());
	 dth.add(Calendar.DAY_OF_MONTH,0);		 
	 String yesterdaytime=sdf.format(dth.getTime());
	 System.out.println("Hour_to_Ddy"+yesterdaytime);
	 //先从user表中算出这一天一共有多少设备
	 String sqlus0="select distinct(`device_id`) from device_gps_infos where upload_time like '"+yesterdaytime+"%'";
	 System.out.println("rrrrrrrrrr");
	 List<DeviceGpsInfos> uh0 = DeviceGpsInfos.dao.find(sqlus0);	
	 System.out.println("qqqqqqqqq"+uh0);
	 for(int i=0;i<uh0.size();i++){//一个设备 	
    	 String sqlus2="select msg_type from device_gps_infos where device_id='"+uh0.get(i).getInt("device_id")+"' and upload_time like '"+yesterdaytime+"%' and msg_type>='"+1+"' and msg_type<='"+6+"'";
    	 List<DeviceGpsInfos> uh2 = DeviceGpsInfos.dao.find(sqlus2);			       
         int warntimes=uh2.size();
         String sqlus3="select msg_type from device_gps_infos where device_id='"+uh0.get(i).getInt("device_id")+"' and upload_time like '"+yesterdaytime+"%' and msg_type='"+6+"'";
    	 List<DeviceGpsInfos> uh3 = DeviceGpsInfos.dao.find(sqlus3);			       
         int serious_warntimes=uh3.size();
         new Hour_to_day();
		Hour_to_day.dao.set("id",null).set("device_id",uh0.get(i).getInt("device_id")).set("date",yesterdaytime).set("warntimes",warntimes).set("s_warntimes",serious_warntimes).save();
		System.out.println("现在"+"时间=" + new Date() + " 执行了"); // 1次
		 
	 }  
	
}
}
