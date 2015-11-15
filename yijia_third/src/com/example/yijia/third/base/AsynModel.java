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
 * @author  �� 
 * @date ����ʱ�䣺2015-10-1 ����11:24:14
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
	 * @author ��
	 * �ϴ��ļ���һ�������baseasyn����ʵ�ָ÷���
	 */
	@Override
	protected String doInBackground(Integer... params) {
		// TODO Auto-generated method stub
		return null;
	}
		
	/**
	 * �ϴ�map����
	 */
	protected String interactive(String url, HashMap<String, String> param) {
		HttpClient httpClient = BaseApp.getInstance().getHttpClient();
		String URL = Constant.URL + url;
		LogUtils.getInstance().println("URL", URL);
		Result result = HttpUtils.post(httpClient, URL, param);
		if(result == null)				
			LogUtils.getInstance().println("post����Ϊ��...", "���ӷ�����ʧ��...");
		else	
			LogUtils.getInstance().println("result", result.toString());
		return parseResult(result);
	}
	
	/**
	 * �ϴ����֣�ͼƬ
	 */
	protected String interactive(String url, String text,
			ArrayList<String> listPath) {
		HttpClient httpClient = BaseApp.getInstance().getHttpClient();
		String URL = Constant.URL + url;
		Result result = HttpUtils.postFiles(httpClient, URL, text, listPath);
		return parseResult(result);
	}
	
	/**
	 * ͳһ����Result
	 */
	protected String parseResult(Result result) {
		LogUtils.getInstance().println("result.getValid()", ""+result.getValid());
		String data = null;
		if (result != null) {
			if (result.getValid() == 1) {
				switch (result.getStatusCode()) {
				case -1:
					Log.e(TAG, "��������...");
					data = result.getData();
					Log.e(TAG, "data..." + data);
					break;
				case -100:
					Log.e(TAG, "ǿ������...");
					break;
				case -200:
					Log.e(TAG, "��Ȩ��...");
					break;
				case -300:
					Log.e(TAG, "�豸δ����...");
					break;
				case 0:
					Log.e(TAG, "δ֪����...");
					break;
				case 1:
					Log.e(TAG, "ȱ�ٱ�Ҫ����...");
					break;
				case 2:
					Log.e(TAG, "���������쳣...");
					break;
				case 3:
					Log.e(TAG, "����Դ����ʧ��...");
					break;
				case 4:
					Log.e(TAG, "��ѯ��¼Ϊ��...");
					break;
				default:
					break;
				}
			} else if (result.getValid() == 0) {
				Log.e(TAG, "��Ч��ѯ...");
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
