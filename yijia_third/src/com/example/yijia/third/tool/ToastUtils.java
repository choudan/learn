package com.example.yijia.third.tool;

import com.example.yijia.third.base.BaseApp;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * @author ��
 * @date ����ʱ�䣺2015/10/22 ����11:14:05
 * @version 1.0
 * @parameter
 * @since
 * @return
 * 
 */
public class ToastUtils {
	private static Handler handler = new Handler(Looper.getMainLooper());

	private static Toast toast = null;

	private static Object synObj = new Object();

	public static void showMessage(final String msg) {
		showMessage(msg, Toast.LENGTH_SHORT);
	}

	/**
	 * �������õ��ı���ʾ
	 * 
	 * @param msg
	 */
	public static void showMessage(final int msg) {
		showMessage(msg, Toast.LENGTH_SHORT);
	}

	/**
	 * ��ʾһ���ı���������ʱ��
	 * 
	 * @param msg
	 * @param len
	 */
	public static void showMessage(final CharSequence msg, final int len) {
		if (msg == null || msg.equals("")) {
			LogUtils.getInstance().e("[ToastUtil] response message is null.");
			return;
		}
		handler.post(new Runnable() {
			@Override
			public void run() {
				synchronized (synObj) { // ����ͬ����Ϊ��ÿ��toastֻҪ�л�����ʾ����
					if (toast != null) {
						// toast.cancel();
						toast.setText(msg);
						toast.setDuration(len);
					} else {
						toast = Toast.makeText(BaseApp.getInstance()
								.getApplicationContext(), msg, len);
					}
					toast.show();
				}
			}
		});
	}

	/**
	 * ��Դ�ļ���ʽ��ʾ�ı�
	 * 
	 * @param msg
	 * @param len
	 */
	public static void showMessage(final int msg, final int len) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				synchronized (synObj) {
					if (toast != null) {
						// toast.cancel();
						toast.setText(msg);
						toast.setDuration(len);
					} else {
						toast = Toast.makeText(BaseApp.getInstance()
								.getApplicationContext(), msg, len);
					}
					toast.show();
				}
			}
		});
	}
}
