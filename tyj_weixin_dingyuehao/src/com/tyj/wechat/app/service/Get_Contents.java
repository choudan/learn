package com.tyj.wechat.app.service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Get_Contents {
	public static String getURLContent(String url, String encoding) {
		  StringBuffer content = new StringBuffer();
		  try {
		   // 新建URL对象
		   URL u = new URL(url);
		   InputStream in = new BufferedInputStream(u.openStream());
		   InputStreamReader theHTML = new InputStreamReader(in, encoding);
		   int c;
		   while ((c = theHTML.read()) != -1) {
		    content.append((char) c);
		   }
		  }
		  // 处理异常
		  catch (MalformedURLException e) {
		   System.err.println(e);
		  } catch (IOException e) {
		   System.err.println(e);
		  }
		  return content.toString();
		 }
}
