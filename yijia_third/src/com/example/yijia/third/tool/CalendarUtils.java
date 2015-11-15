package com.example.yijia.third.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;

/**
 * @author  丑旦 
 * @date 创建时间：2015/11/13 下午3:34:39
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 *
 */
public class CalendarUtils {
	static SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
	static SimpleDateFormat sf = new SimpleDateFormat("MM-dd",Locale.getDefault());
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());
	/**
	 * @return 显示日期 MM-dd
	 * */
	public static ArrayList<String> getListDate(String startDate, int length) throws ParseException {
		ArrayList<String> listDate = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		Date start = sf1.parse(startDate);
		calendar.setTime(start);

		int i = 0;
		while (i < length) {
			String date = sf.format(calendar.getTime());
			listDate.add(date);
			System.out.println("=-=-=date=-=-=" + date);
			calendar.add(Calendar.DATE, 1);
			i++;
		}
		return listDate;
	}

	/**
	 * @return 第几天
	 * */
	public static ArrayList<String> getListDateRelative(int length) {
		ArrayList<String> listDateRelative = new ArrayList<String>();
		for (int i = 1; i < length; i++) {
			listDateRelative.add("" + i);
		}
		return listDateRelative;
	}

	/**
	 * @return 计算两个日期之间相差的天数，绝对时差
	 * */
	public static int daysBetween(String smdate, String bdate) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}
	
	/**
	 * @return 计算某日期与今天之间相差的天数，相对时差
	 * */
	public static int getDateSpace(String date2)throws ParseException {		
		Calendar calst = Calendar.getInstance();
		Calendar caled = Calendar.getInstance();
		caled.setTime(sf1.parse(date2));
		calst.set(Calendar.HOUR_OF_DAY, 0);   
		calst.set(Calendar.MINUTE, 0);   
		calst.set(Calendar.SECOND, 0); 
		
		caled.set(Calendar.HOUR_OF_DAY, 0);   
		caled.set(Calendar.MINUTE, 0);   
		caled.set(Calendar.SECOND, 0);   
		int days = ((int)(calst.getTime().getTime()/1000)-(int)(caled.getTime().getTime()/1000))/3600/24;   
		
		return days;   
	}	
	
	@SuppressLint("SimpleDateFormat")
	public static String dateTime(){
//		SimpleDateFormat sf=new SimpleDateFormat(DATE_TIME);
		String dateTime=sdf.format(new Date());
		return dateTime;		
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String date(){
//		SimpleDateFormat sf=new SimpleDateFormat(DATE);
		String dateTime=sf1.format(new Date());
		return dateTime;		
	}
	
	//年份加1	
	public static String deadline() {
		Calendar calendar = Calendar.getInstance();
		Date date = new Date(System.currentTimeMillis());
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, +1);
		date = calendar.getTime();
//		SimpleDateFormat sf = new SimpleDateFormat(DATE);
		String dateTime = sf1.format(date);
		return dateTime;
	}
}
