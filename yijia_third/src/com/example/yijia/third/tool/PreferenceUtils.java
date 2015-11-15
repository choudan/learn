package com.example.yijia.third.tool;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.example.yijia.third.base.BaseApp;

/**
 * @author  丑旦 
 * @date 创建时间：2015-10-1 下午6:14:26
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 *
 */
@SuppressLint("CommitPrefEdits")
public class PreferenceUtils {
	//默认文件名
	public static final String PREFERENCE = getDefaultSharedPreferencesFileName();

	public static String getDefaultSharedPreferencesFileName() {
		// TODO Auto-generated method stub
		return "com.example.yijia.preference";
	}
	
	public static SharedPreferences getSharedPreferences(){
		return BaseApp.getInstance().getSharedPreferences(PREFERENCE, Context.MODE_MULTI_PROCESS);
	}
	
	public static Editor getSharedPreferencesEditor(){
		SharedPreferences preference = getSharedPreferences();
		Editor edit = preference.edit();
		return edit;
	}
	
	public static void savePreference(PreferenceSetting pre,Object value,boolean applied){
		Map<PreferenceSetting,Object> pres = new HashMap<PreferenceSetting,Object>();
		pres.put(pre, value);
		savePreference(pres,applied);
	}

	public static void savePreference(Map<PreferenceSetting, Object> pres,
			boolean applied) {
		// TODO Auto-generated method stub
		savePreference(pres,true,applied);
	}

	public static void savePreference(Map<PreferenceSetting, Object> prefs,
			boolean noSaveIfExists, boolean applied) {
		// TODO Auto-generated method stub
		SharedPreferences sp = getSharedPreferences();
		Editor editor = sp.edit();
		Iterator<PreferenceSetting> it = prefs.keySet().iterator();
		while (it.hasNext()) {
			PreferenceSetting pref = it.next();
			if (!noSaveIfExists && sp.contains(pref.getmId())) {
				// The preference already has a value
				continue;
			}

			// Known and valid types
			Object value = prefs.get(pref);
			if (value == null) {
				return;
			}
			if (value instanceof Boolean && pref.getmDefaultValue() instanceof Boolean) {
				editor.putBoolean(pref.getmId(),((Boolean) value).booleanValue());
			} else if (value instanceof String && pref.getmDefaultValue() instanceof String) {
				editor.putString(pref.getmId(), (String) value);
			} else if (value instanceof Integer && pref.getmDefaultValue() instanceof Integer) {
				editor.putInt(pref.getmId(), (Integer) value);
			} else if (value instanceof Long && pref.getmDefaultValue() instanceof Long) {
				editor.putLong(pref.getmId(), (Long) value);
			} else if (value instanceof Set && pref.getmDefaultValue() instanceof Set) {
//				 editor.putStringSet(pref.getId(), (Set<String>)value);
			} else {
				// The object is not of the appropriate type
				String msg = String.format("%s: %s", pref.getmId(), value.getClass().getName());
				Log.e("=-=-=PreferenceUtils=-=-=", String.format("Configuration error. InvalidClassException: %s", msg));
			}
		}
		// Commit settings
		editor.commit();
	}
}
