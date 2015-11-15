package com.example.yijia.third.tool;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.example.yijia.third.base.BaseActivity;
import com.example.yijia.third.bean.common.ClientUser;

/**
 * ����activity/����Preference
 * 
 * @author ��
 * @date ����ʱ�䣺2015-10-1 ����10:01:19
 * @version 1.0
 * @parameter
 * @since
 * @return
 * 
 */
public class AppManager {
	private static ClientUser mClientUser;
	private static Context mContext = null;
	public static ArrayList<BaseActivity> activities = new ArrayList<BaseActivity>();
	
	public static void setClientUser(ClientUser mClientUser) {
		AppManager.mClientUser = mClientUser;
	}

	public static Context getContext() {
		return mContext;
	}

	public static void setContext(Context mContext) {
		AppManager.mContext = mContext;
	}

	private static String getRegistAccount() {
		SharedPreferences sharedPreferences = PreferenceUtils.getSharedPreferences();
		PreferenceSetting registAuto = PreferenceSetting.SAVE_CLIENTER;
		String registAccount = sharedPreferences.getString(registAuto.getmId(),
				(String) registAuto.getmDefaultValue());
		LogUtils.getInstance().println("getRegistAccount()", registAccount);
		return registAccount;
	}

	/**
	 * ��ȡ���ش洢��Ϣ
	 * @return
	 */
	public static ClientUser getClientUser() {
		if (mClientUser != null)
			return mClientUser;
		String account = getRegistAccount();
		if (!TextUtils.isEmpty(account)) {
			mClientUser = new ClientUser("");
			return mClientUser.from(account);
		}
		return null;
	}
	
	/**
	 * ��ȡid
	 * @return
	 */
	public static String getRoleId() {
		return getClientUser().getRoleId();
	}
	
	 /**
     * ��ȡӦ�ó���汾����
     * @return
     */
    public static String getVersion() {
        String version = "0.0.0";
        if(mContext == null) 
            return version; 
        try {
            PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(
                    getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return version;
    }
    
    /**
     * ��ȡӦ�ð汾��
     * @return �汾��
     */
    public static int getVersionCode() {
        int code = 1;
        if(mContext == null) 
            return code;      
        try {
            PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(
                    getPackageName(), 0);
            code = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

	private static String getPackageName() {
		// TODO Auto-generated method stub
		return "com.example.yijia";
	}

	public static void addActivity(BaseActivity activity) {
		activities.add(activity);
	}

	public static void clearActivity() {
		for (BaseActivity activity : activities) {
			if (activity != null) {
				activity.finish();
				activity = null;
			}
			activities.clear();
		}
	}
}
