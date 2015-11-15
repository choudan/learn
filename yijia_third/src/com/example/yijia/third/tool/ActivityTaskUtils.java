package com.example.yijia.third.tool;

import java.util.List;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

/**
 * 打印Activity相关信息
 * @author  丑旦 
 * @date 创建时间：2015-10-1 下午4:22:55
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 *
 */
public class ActivityTaskUtils {
	private Context mContext;

	public ActivityTaskUtils(Context mContext) {
		super();
		this.mContext = mContext;
	}

	@Override
	public String toString() {
		Log.e("=-=-=ActivityTaskUtils...", "进入");
		if(mContext==null)
			return null;
		ActivityManager mActivityManager = (ActivityManager)mContext.getSystemService(Context.ACTIVITY_SERVICE);
		String packageName = mContext.getPackageName();
		Log.e("=-=-=ActivityTaskUtils...packageName", packageName);
		if(mActivityManager==null||TextUtils.isEmpty(packageName))
			return null;
		
		List<ActivityManager.RunningTaskInfo> runningTasks = mActivityManager.getRunningTasks(100);
		Log.e("=-=-=ActivityTaskUtils...runningTasks.size()", ""+runningTasks.size());
		StringBuffer buffer = new StringBuffer();
		for(ActivityManager.RunningTaskInfo info:runningTasks){
			Log.e("=-=-=ActivityTaskUtils...RunningTaskInfo", ""+info);
			if(!info.baseActivity.getClassName().startsWith(packageName)&&!info.topActivity.getClassName().startsWith(packageName)){
				Log.e("=-=-=ActivityTaskUtils...baseActivity", ""+info.baseActivity.getClassName());
				continue;
			}
			Object[] args = new Object[5];
			args[0] = info.id;
			args[1] = info.numRunning;
			args[2] = info.numActivities;
			args[3] = info.topActivity.getShortClassName();
			args[0] = info.baseActivity.getShortClassName();
			buffer.append(String.format("{id:%d num:%d/%d top:%s base:%s}", args));
			Log.e("=-=-=ActivityTaskUtils...", "buffer作用了？");
		}
		return buffer.toString();
	}	
}
