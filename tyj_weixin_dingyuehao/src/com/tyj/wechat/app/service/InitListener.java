package com.tyj.wechat.app.service;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
public class InitListener implements ServletContextListener  {
public void contextInitialized(ServletContextEvent paramServletContextEvent) {
              new  TestTimer();
	//;
	//实现方法
	TestTimer.showTimer();
}
public void contextDestroyed(ServletContextEvent paramServletContextEvent) {
}
}
