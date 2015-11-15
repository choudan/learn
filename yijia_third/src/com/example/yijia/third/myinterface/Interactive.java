package com.example.yijia.third.myinterface;

import httpresult.bean.Result;


public interface Interactive<T extends Result> {
	public void request();

	public void response();
	
}
