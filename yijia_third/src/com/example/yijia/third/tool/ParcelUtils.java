package com.example.yijia.third.tool;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public class ParcelUtils {

	public static void writeToParce(Parcel parcel,Object obj){
		writeToParce(parcel,obj,null);
	}
	
	public static void writeToParce(Parcel parcel,Object obj,String[] exludeProps){
		List<Field> list = getFields(obj,exludeProps);
		for(Field f:list){
			writeField(obj,f,parcel);
		}
	}

	private static List<Field> getFields(Object obj, String[] exludeProps) {
		Field[] fields = obj.getClass().getDeclaredFields();
		List<Field> list = excludeFields(fields,exludeProps);
		Collections.sort(list, new FieldComparator());
		return list;
	}

	private static void writeField(Object obj, Field f, Parcel parcel) {
		f.setAccessible(true);
		try {
			Object value = f.get(obj);
			parcel.writeValue(value);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static <T> Creator<T> buildCreator(final Class<T> cls){
		return buildCreator(cls,null);
	}
	
	public static <T> Creator<T> buildCreator(final Class<T> cls,final String[] excludeProps){
		Creator<T> c = new Creator<T>() {
			@SuppressWarnings("unchecked")
			@Override
			public T[] newArray(int size) {
				return (T[]) Array.newInstance(cls, size);
			}

			@Override
			public T createFromParcel(Parcel in) {
				T ni = null;
				try {
					ni = cls.newInstance();
					List<Field> fields = getFields(ni, excludeProps);
					for(Field f:fields){
						f.setAccessible(true);
						f.set(ni, in.readValue(null));
					}
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				return ni;
			}
		};
		return c;
	}
	
	private static class FieldComparator implements Comparator<Field>{

		@Override
		public int compare(Field lhs, Field rhs) {
			return lhs.getName().compareTo(rhs.getName());
		}
		
	}
	
	private static List<Field> excludeFields(Field[] fields, String[] exludeProps) {
		List<Field> list = new ArrayList<Field>(Arrays.asList(fields));
		if(exludeProps != null){
			Iterator<Field> it = list.iterator();
			while(it.hasNext()){
				if(shouldExclude(exludeProps,it.next().getName())){
					it.remove();
				}
			}
		}
		return list;
	}

	private static boolean shouldExclude(String[] exludeProps, String name) {
		for(String e:exludeProps){
			if(e.equals(name)){
				return true;
			}
		}
		return false;
	}
}
