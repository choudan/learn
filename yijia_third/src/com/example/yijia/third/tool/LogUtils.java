package com.example.yijia.third.tool;

import android.util.Log;

import com.example.yijia.third.base.BaseApp;

/**
 * @author ��
 * @date ����ʱ�䣺2015/10/22 ����10:34:45
 * @version 1.0
 * @parameter
 * @since
 * @return
 * 
 */
public class LogUtils {
	public boolean flag = true;// �Ƿ���ʾ
	private static LogUtils instance;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	private LogUtils() {

	}

	public static LogUtils getInstance() {
		if (instance == null)
			instance = new LogUtils();
		return instance;
	}

	public void e(String content) {
		if (isFlag())
			Log.e(BaseApp.getInstance().getApplicationContext().getClass()
					.getSimpleName(), "------" + content + "------");
	}

	public void println(String prompt, String content) {
		if (isFlag())
			System.out.println("=-=-=" + prompt + ":  " + content + "=-=-=");
	}
}
