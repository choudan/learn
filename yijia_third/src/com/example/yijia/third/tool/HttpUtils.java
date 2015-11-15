/**
 * 
 */
package com.example.yijia.third.tool;

import httpresult.bean.Result;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.http.AndroidHttpClient;
import android.util.Log;


/**
 * @author Administrator
 *
 */
public class HttpUtils {
	private AndroidHttpClient mHttpClient = null; 
	private final static String TAG=HttpUtils.class.getName();	
       
	public AndroidHttpClient createHttpClient(){
		AndroidHttpClient mAndroidHttpClient = AndroidHttpClient.newInstance("");
		return mAndroidHttpClient;
	}
	
	public void shutdownHttpClient(){  
        if(mHttpClient != null && mHttpClient.getConnectionManager() != null){  
            mHttpClient.getConnectionManager().shutdown();  
        }  
    }  
	
	/** 
	 * get上传map参数
	 */
	public static Result get(HttpClient httpClient,String url, HashMap<String, String> param) {
		// TODO Auto-generated method stub		
		Result result=null;
		HttpGet get = get(url,param);
  		try {
			HttpResponse httpResponse = httpClient.execute(get);
			if(httpResponse.getStatusLine().getStatusCode()==200){
				HttpEntity reultEntity = httpResponse.getEntity();
				BufferedReader mReader=new BufferedReader(new InputStreamReader(reultEntity.getContent()));
				String line = mReader.readLine();
				Log.e(TAG, "=-=-="+line);
				try {
					result=parseResult(line);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.e(TAG, "=-=-=Json解析异常...");
				}
			}else{
				System.out.println("return status code: "+httpResponse.getStatusLine().getStatusCode());
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/** 
	 * post上传map参数
	 */
	public static Result post(HttpClient httpClient,String url, HashMap<String, String> param) {
		// TODO Auto-generated method stub		
		Result result=null;
		HttpPost post = postForm(url,param);
		try {
			HttpResponse httpResponse = httpClient.execute(post);
			if(httpResponse.getStatusLine().getStatusCode()==200){
				HttpEntity reultEntity = httpResponse.getEntity();
				BufferedReader mReader=new BufferedReader(new InputStreamReader(reultEntity.getContent()));
				String line = mReader.readLine();
				Log.e(TAG, "=-=-="+line);
				try {
					result = parseResult(line);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.e(TAG, "=-=-=Json解析异常...");
				}
			}else{
				System.out.println("return status code: "+httpResponse.getStatusLine().getStatusCode());
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 上传文件
	 */
	public static Result postFiles(HttpClient httpClient,String url, String text,ArrayList<String> listPath) {
		// TODO Auto-generated method stub		
		HttpPost post=new HttpPost(url);
		Result result=null;
		MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
		entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		entityBuilder.addTextBody("text", text);
		entityBuilder.addTextBody("number", ""+listPath.size());	
		for(int i=0;i<listPath.size();i++){
			File file=new File(listPath.get(i));		
			entityBuilder.addBinaryBody("picPath"+i, file);			
		}
		HttpEntity entity = entityBuilder.build();
		post.setEntity(entity);
		try {
			HttpResponse httpResponse = httpClient.execute(post);
			if(httpResponse.getStatusLine().getStatusCode()==200){
				HttpEntity reultEntity = httpResponse.getEntity();
				BufferedReader mReader=new BufferedReader(new InputStreamReader(reultEntity.getContent()));
				String line = mReader.readLine();
				Log.e(TAG, "=-=-="+line);
				try {
					result=parseResult(line);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.e(TAG, "=-=-=Json解析错误...");
				}
			}else{
				System.out.println("return status code: "+httpResponse.getStatusLine().getStatusCode());
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 上传文件
	 */
	public static Result postFile(HttpClient httpClient,String url, String filePath) {
		// TODO Auto-generated method stub		
		HttpPost post=new HttpPost(url);
		Result result=null;
		File file =new File(filePath);
		HttpEntity entity = new FileEntity(file, "application/octet-stream");
		post.setEntity(entity);
		try {
			HttpResponse httpResponse = httpClient.execute(post);
			if(httpResponse.getStatusLine().getStatusCode()==200){
				HttpEntity reultEntity = httpResponse.getEntity();
				BufferedReader mReader=new BufferedReader(new InputStreamReader(reultEntity.getContent()));
				String line = mReader.readLine();
				Log.e(TAG, "=-=-="+line);
				try {
					result=parseResult(line);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.e(TAG, "=-=-=Json解析错误...");
				}
			}else{
				System.out.println("return status code: "+httpResponse.getStatusLine().getStatusCode());
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * get挂参
	 */
	private static HttpGet get(String url, HashMap<String, String> params) {
		StringBuilder builder = new StringBuilder();
		if (params != null) {
			builder.append(url).append("?");
			for (Map.Entry<String, String> entry : params.entrySet()) {
				builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
		}
		LogUtils.getInstance().println("builder", builder.substring(0, builder.length()-1));
		HttpGet get = new HttpGet(builder.substring(0, builder.length()-1));
		return get;
	}
	
	/**
	 * post表单
	 */
	private static HttpPost postForm(String url, HashMap<String, String> params) {
		HttpPost post = new HttpPost(url);
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		try {
			//UrlEncodedFormEntity将请求的内容进行格式化了，同时简化客户端发送，也简化服务器端获取，服务器通过getParameters即可获取
			post.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return post;
	}
	
	/**
	 * Result初步解析,统一处理。Result类型不确定，只能用JSON
	 */
	private static Result parseResult(String data) throws JSONException{
		Result result = new Result();
		JSONObject json = new JSONObject(data);
		result.setStatusCode(json.getInt("statusCode"));
		result.setValid(json.getInt("valid"));
		result.setDataType(json.getInt("dataType"));
		result.setData(json.getString("data"));
		return result;
	}
}
