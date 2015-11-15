package com.example.yijia.third.tool;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 丑旦
 * @date 创建时间：2015/10/28 下午11:10:55
 * @version 1.0
 * @parameter
 * @since
 * @return map
 * 
 */
public class BeanUtils {

	/**
	 * @author 丑旦
	 * beanToMap
	 */
	public static HashMap<String, String> convertBeanToMap(Object bean)
			throws IllegalAccessException, IllegalArgumentException {
		Field[] fields = bean.getClass().getDeclaredFields();
		HashMap<String, String> data = new HashMap<String, String>();
		for (Field field : fields) {
			field.setAccessible(true);
//			data.put(field.getName(), String.valueOf(field.get(bean)));
			data.put(field.getName(), field.get(bean).toString());
		}
		return data;
	}

	/**
	 * @author 丑旦 
	 * 遍历map
	 */
	public static void ergodicMap(HashMap<String, String> map) {
		for (Map.Entry<String, String> entry : map.entrySet()) {
			System.out.println("key= " + entry.getKey() + " and value= "
					+ entry.getValue());
		}
	}
}
