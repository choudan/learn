/**
 * 
 */
package com.example.yijia.third.tool;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.example.yijia.third.myinterface.DataPaserInterface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author Administrator
 * 
 */
public class DataPaser<T> implements DataPaserInterface<T> {

	@Override
	public List<T> parseList(String str, Class<T> classOfT) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		Type type = new TypeToken<List<T>>() {}.getType();
		List<T> list = gson.fromJson(str, type);
		if (list != null && list.size() > 0) {
			List<T> re = new ArrayList<T>(list.size());
			for (int i = 0; i < list.size(); i++) {
				T t = list.get(i);
				String s = gson.toJson(t);
				T newT = gson.fromJson(s, classOfT);
				re.add(newT);
			}
			return re;
		}
		return list;
	}

	@Override
	public T parse(String str, Class<T> classOfT) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();	
		T t = gson.fromJson(str, classOfT);
		return t;
	}
		
	/**
	 * @author Administrator
	 * 除基本类型外，对象必须赋初值才能不丢失字段。如String name=null,则name字段可能丢失
	 * @param <T>
	 */
	public String toJson(Object src){
		Gson gson = new Gson();	
		String jsonString = gson.toJson(src);
		return jsonString;	
	}
}
