	package com.tyj.wechat.app.service;
	import java.util.Calendar;
	import java.util.Date;
	import java.util.Timer;
import java.util.TimerTask;

	public class TestTimer {
	    static int count = 0;
	    
	    public static void showTimer() {
	        TimerTask task = new TimerTask() {//复写方法，实现功能
	            @Override
	            public void run () {
	                ++count;
	                new Hour_to_Day().hour_to_day();
	                System.out.println("new"+"时间=" + new Date() + " 执行了" + count + "次"); // 1次
	            }
	        };

	        //设置执行时间
	        Calendar calendar = Calendar.getInstance();
	        int year = calendar.get(Calendar.YEAR);
	        int month = calendar.get(Calendar.MONTH);
	        int day = calendar.get(Calendar.DAY_OF_MONTH);//每天
//	        int hour=calendar.get(Calendar.HOUR_OF_DAY);
//	        int min=calendar.get(Calendar.MINUTE);
	        //定制每天的21:09:00执行，
	        calendar.set(year,month,day,23,00,00);
	        Date date = calendar.getTime();
			//第一次执行时间小于现在时间，即已经过了时间点了	        
//	        if (date.before(new Date())) {
//	        	int hour = calendar.get(Calendar.HOUR_OF_DAY);
//	        	 int min = calendar.get(Calendar.MINUTE)+30;
//		        calendar.set(year, month, day, hour, min,00);
//		         date = calendar.getTime();
//		         System.out.println("uuuuuuuuuu"+date);
//	        }
	        Timer timer = new Timer();
	        System.out.println("zoumeng"+"执行时间"+date+"现在时间"+ new Date());	        
	        timer.schedule(task,date,1*1000*60*60*24);
	    }

}

