package com.tyj.wechat.util;


public class LatLngUtils {
	private final static double EARTH_RADIUS = 6378137.0;    
	private final static double TO_RAD = Math.PI/180.0;
	private final static double TO_ANGLE = 180.0/Math.PI;
	
	public static double toRad(double angle){
		return angle*TO_RAD;
	}
	public static double getDistanceInMeter(double longitudeX,double latitudeX,double longitudeY,double latitudeY){
		       double radLat1 = toRad(latitudeX);
		       double radLat2 = toRad(latitudeY);  
		       double a = radLat1 - radLat2;  
		       double b = toRad(longitudeX - longitudeY);
		       double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)  
		              + Math.cos(radLat1) * Math.cos(radLat2)  
		              * Math.pow(Math.sin(b/2), 2)));  
		      s = s * EARTH_RADIUS/1000; 
		      // s = Math.round(s * 10000) / 10000/1000;//化成千米
		       
		return s;
	}
	/**
	 * 得到两点之间的折线距离
	 * 折线距离是以两点作为矩形的对角点，计算矩形两边距离之和
	 * @return
	 */
	public static double getBrokenLineDistanceInMeter(double longitudeX,double latitudeX,double longitudeY,double latitudeY){
		double midLongitudeZ = longitudeX;
		double midLatitudeZ = latitudeY;
		double d1 = getDistanceInMeter(longitudeX,latitudeX,midLongitudeZ,midLatitudeZ);
		double d2 = getDistanceInMeter(longitudeY,latitudeY,midLongitudeZ,midLatitudeZ);
		return (d1+d2);
	}
	
//	public static void main(String[] args){
//		System.out.println(getDistanceInMeter(0,1,1,1));
//		System.out.println(getBrokenLineDistanceInMeter(0,1,1,1));
//	}
//	
//	public static double dufenToDu(int du,int minute,double second){
//		double result = du;
//		result += (minute+(second)/60)/60;
//		return result;
//	}
	
}
