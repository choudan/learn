/**
 * 
 */
package com.example.yijia.third.myinterface;

import java.util.List;

/**
 * @author Administrator ���ַ������ݽ�����ʵ��
 * 
 */
public interface DataPaserInterface<T> {
	public List<T> parseList(String str, Class<T> classOfT);

	public T parse(String str, Class<T> classOfT);
}
