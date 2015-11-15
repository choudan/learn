package com.example.yijia.third.tool;

/**
 * @author ��
 * @date ����ʱ�䣺2015-10-1 ����6:58:23
 * @version 1.0
 * @parameter
 * @since
 * @return
 * 
 */
public enum PreferenceSetting {
	/**�Ƿ��һ�ε�½*/
	SETTINGS_FIRST_USE("com.example.yijia.first_use", Boolean.TRUE), 
	/**�����˺���Ϣ*/
	SAVE_CLIENTER("com.example.yijia.account" , ""),
	/**�Ƿ��Զ���¼*/
	SETTINGS_LOAD_AUTO("com.example.yijia.load.auto" , Boolean.TRUE),
	/**��ʱ��������*/
	SAVE_POINT("com.example.yijia.point" , ""),
	/**���߰汾*/
	SETTINGS_OFFLINE_MESSAGE_VERSION("com.example.yijia.offline_version", 0);

	private final String mId;
	private final Object mDefaultValue;

	/**
	 * �Զ��巽��
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
