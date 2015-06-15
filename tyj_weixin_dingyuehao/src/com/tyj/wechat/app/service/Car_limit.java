package com.tyj.wechat.app.service;
import java.util.HashMap;

import com.tyj.wechat.app.model.Car_limit_Model;

public class Car_limit {
	 
public HashMap<String,String> carl(int week){
	int limitNumber[]=new int[2];
	 if(week==1){
		 int a=Car_limit_Model.dao.findFirst("select monday from car_limit").get("monday");		
		 limitNumber[0]=a/10;//取整
		 limitNumber[1]=a%10;//取余	    
}
	 else if(week==2){
		 int a=Car_limit_Model.dao.findFirst("select tuesday from car_limit").get("tuesday");		
		 limitNumber[0]=a/10;//取整
		 limitNumber[1]=a%10;//取余	    
}
	 else if(week==3){
		 int a=Car_limit_Model.dao.findFirst("select wednesday from car_limit").get("wednesday");		
		 limitNumber[0]=a/10;//取整
		 limitNumber[1]=a%10;//取余	    
}
	 else if(week==4){
		 int a=Car_limit_Model.dao.findFirst("select thursday from car_limit").get("thursday");		
		 limitNumber[0]=a/10;//取整
		 limitNumber[1]=a%10;//取余	    
}
	 else if(week==5){
		 int a=Car_limit_Model.dao.findFirst("select friday from car_limit").get("friday");		
		 limitNumber[0]=a/10;//取整
		 limitNumber[1]=a%10;//取余	    
}
	 else{
		 limitNumber[0]=-1;//取整
		 limitNumber[1]=-1;//取余	
	 }
	 HashMap<String, String>  map = new HashMap<String, String>();
	 map.put("limitNumber", limitNumber[0]+"/"+limitNumber[1]);
	 return map;
	 }
}
