package com.example.yijia.third.tool;

/**
 * @author 丑旦
 * @date 创建时间：2015-10-1 下午6:58:23
 * @version 1.0
 * @parameter
 * @since
 * @return
 * 
 */
public enum PreferenceSetting {
	/**是否第一次登陆*/
	SETTINGS_FIRST_USE("com.example.yijia.first_use", Boolean.TRUE), 
	/**保存账号信息*/
	SAVE_CLIENTER("com.example.yijia.account" , ""),
	/**是否自动登录*/
	SETTINGS_LOAD_AUTO("com.example.yijia.load.auto" , Boolean.TRUE),
	/**临时经络数据*/
	SAVE_POINT("com.example.yijia.point" , ""),
	/**离线版本*/
	SETTINGS_OFFLINE_MESSAGE_VERSION("com.example.yijia.offline_version", 0);

	private final String mId;
	private final Object mDefaultValue;

	/**
	 * 自定义方法
	 * 
	 * @param id
	 *            The unique identifier of the setting
	 * @param defaultValue
	 *            The default value of the setting
	 */
	private PreferenceSetting(String id, Object defaultValue) {
		this.mId = id;
		this.mDefaultValue = defaultValue;
	}

	public String getmId() {
		return mId;
	}

	public Object getmDefaultValue() {
		return mDefaultValue;
	}
	
	public static PreferenceSetting fronId(String id){
		PreferenceSetting[] values = values();
		int len = values.length;
		for(int i = 0;i<len;i++){
			if(values[i].mId == id){
				return values[i];
			}
		}
		return null;
	}
}
