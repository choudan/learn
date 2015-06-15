package com.tyj.wechat.app.service;
import java.util.*; 
import javax.servlet.*; 

public class MyTask extends TimerTask { 
  private static final int C_SCHEDULE_HOUR = 11; 
  //private static final int C_SCHEDULE_MIN = 46; 
  private static boolean isRunning = false; 
  private ServletContext context = null; 

  public MyTask() { 
  } 
  public MyTask(ServletContext context) { 
    this.context = context; 
  } 

  public void run() { 
    Calendar cal = Calendar.getInstance();
    //System.out.println("yyyyyyyy"+cal.get(Calendar.MINUTE));
    System.out.println("hhhhhhhhh"+cal.get(Calendar.HOUR_OF_DAY));
    if (!isRunning) { 
    	System.out.println("进来了aaa");
      if (C_SCHEDULE_HOUR==cal.get(Calendar.HOUR_OF_DAY)) { 
    	  System.out.println("进来了");
        isRunning = true; 
        context.log("开始执行指定任务"); 
        //TODO 添加自定义的详细任务，以下只是示例 
        //系统定时接收邮件 
        new Hour_to_Day().hour_to_day();
        System.out.println("执行啦");

        isRunning = false; 
        context.log("指定任务执行结束"); 
      } 
    } 
    else { 
      context.log("上一次任务执行还未结束"); 
    } 
  } 

} 