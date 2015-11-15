package com.example.yijia.third.base;

import httpresult.bean.Result;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.client.HttpClient;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.yijia.third.tool.Constant;
import com.example.yijia.third.tool.HttpUtils;
import com.example.yijia.third.tool.LogUtils;

/**
 * @author  丑旦 
 * @date 创建时间：2015-10-1 下午11:24:14
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 *
 */
public class AsynModel extends AsyncTask<Integer,Integer,String>{
	protected final String TAG=this.getClass().getName();
	protected Context mContext;
	
	protected AsynModel(Context mContext) {
		super();
		this.mContext = mContext;
	}
	
	/**
	 * @author 丑旦
	 * 上传文件和一般的请求baseasyn均须实现该方法
	 */
	@Override
	protected String doInBackground(Integer... params) {
		// TODO Auto-generated method stub
		return null;
	}
		
	/**
	 * 上传map参数
	 */
	protected String interactive(String url, HashMap<String, String> param) {
		HttpClient httpClient = BaseApp.getInstance().getHttpClient();
		String URL = Constant.URL + url;
		LogUtils.getInstance().println("URL", URL);
		Result result = HttpUtils.post(httpClient, URL, param);
		if(result == null)				
			LogUtils.getInstance().println("post返回为空...", "连接服务器失败...");
		else	
			LogUtils.getInstance().println("result", result.toString());
		return parseResult(result);
	}
	
	/**
	 * 上传文字，图片
	 */
	protected String interactive(String url, String text,
			ArrayList<String> listPath) {
		HttpClient httpClient = BaseApp.getInstance().getHttpClient();
		String URL = Constant.URL + url;
		Result result = HttpUtils.postFiles(httpClient, URL, text, listPath);
		return parseResult(result);
	}
	
	/**
	 * 统一处理Result
	 */
	protected String parseResult(Result result) {
		LogUtils.getInstance().println("result.getValid()", ""+result.getValid());
		String data = null;
		if (result != null) {
			if (result.getValid() == 1) {
				switch (result.getStatusCode()) {
				case -1:
					Log.e(TAG, "正常解析...");
					data = result.getData();
					Log.e(TAG, "data..." + data);
					break;
				case -100:
					Log.e(TAG, "强制升级...");
					break;
				case -200:
					Log.e(TAG, "无权限...");
					break;
				case -300:
					Log.e(TAG, "设备未激活...");
					break;
				case 0:
					Log.e(TAG, "未知错误...");
					break;
				case 1:
					Log.e(TAG, "缺少必要参数...");
					break;
				case 2:
					Log.e(TAG, "参数解析异常...");
					break;
				case 3:
					Log.e(TAG, "数据源访问失败...");
					break;
				case 4:
					Log.e(TAG, "查询记录为空...");
					break;
				default:
					break;
				}
			} else if (result.getValid() == 0) {
				Log.e(TAG, "无效查询...");
			}
		} 
		return data;
	}
	
//	@Override
//	protected void onPreExecute() {
//		super.onPreExecute();
//		if (mContext != null) {
//			cancel(true);
//			YJResult exception = new YJResult();
//			setResult(exception);
//		}
//		return;
//	}
//	
//	private void setResult(YJResult result) {
//		if(result.throwable == null) {
//			return;
//		}
//		if(result.throwable instanceof Exception) {
//			onException((Exception)result.throwable);
//			return;
//		}
//		onException(new Exception(result.throwable));
//	}
//	
//	protected void onException(Exception exception) {
//		if(exception == null) {
//			return ;
//		}
//		Log.e(TAG, exception.getMessage());
//	}
//	
//	public class YJResult {
//		Result obj;
//		Throwable throwable;
//	}
}
