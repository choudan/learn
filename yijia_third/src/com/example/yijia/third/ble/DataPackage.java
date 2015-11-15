package com.example.yijia.third.ble;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.example.yijia.third.tool.CRC;
import com.example.yijia.third.tool.DataUtils;

/**
 * @author ��
 * @date ����ʱ�䣺2015/10/27 ����8:05:32
 * @version 1.0
 * @parameter
 * @since
 * @return
 * 
 */
public class DataPackage {

	/**
	 * @return
	 * 
	 */
	public static String ifRegister() {
		StringBuilder builder = new StringBuilder();
		builder.append("5C").append("A1B1").append("0004").append("00000000")
				.append(CRC.calcCrc16(builder.toString()));
		return builder.toString();
	}

	/**
	 * @return
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	public static String setName(String name) throws UnsupportedEncodingException {
		StringBuilder builder = new StringBuilder();
		builder.append("5C").append("A1B2").append("000C")
				.append(DataUtils.GBK2eight(name)).append("A1000000")
				.append(CRC.calcCrc16(builder.toString()));
		return builder.toString();
	}
	
	/**
	 * @return
	 * 
	 */
	public static String synchTime() {
		StringBuilder builder = new StringBuilder();
		builder.append("5C").append("A2B2").append("000C").append(getDate())
				.append(CRC.calcCrc16(builder.toString()));
		return builder.toString();
	}
	
	/**
	 * @return
	 * 
	 */
	public static String dataNum() {
		StringBuilder builder = new StringBuilder();
		builder.append("5C").append("A3B3").append("0004").append("00000000")
				.append(CRC.calcCrc16(builder.toString()));
		return builder.toString();
	}
	
	/**
	 * @return
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	public static String mMessage(ArrayList<String> message_doc,
			ArrayList<String> msgTime) throws UnsupportedEncodingException {
		StringBuilder builder = new StringBuilder();
		builder.append("5C").append("A5B5")
				.append(DataUtils.toOneByteHex(message_doc.size()));
		for (int i = 0; i < msgTime.size(); i++) {
			builder.append(DataUtils.decstr2HexStr(transferDateTime(msgTime
					.get(i))));// ҽ���ظ�ʱ�䣬6�ֽ�>>>>>>>>>>>>>>>>>>>>>>>�����ƴ���
			builder.append(DataUtils.toTwoByteHex(DataUtils
					.getCountStringToGBK(message_doc.get(i))));// ҽ���ظ����ȣ�ռ2�ֽ�+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=
			builder.append(DataUtils.stringToGBK(message_doc.get(i)));// ҽ���ظ����ݣ����Ȳ�ȷ��>>>>>>>>�����Ĵ���GBK��
		}
		builder.insert(6, DataUtils.toTwoByteHex((builder.length() - 6) / 2));// ��Ϣ�峤�ȣ�2�ֽڣ�ֵ��ȷ��.length������++++++++++++++++++++
		builder.append(CRC.calcCrc16(builder.toString()));
		return builder.toString();
	}
	
	/**
	 * @return
	 * 
	 */
	public static String mOut() {
		StringBuilder builder = new StringBuilder();
		builder.append("5C").append("A6B6").append("0004").append("00000000")
				.append(CRC.calcCrc16(builder.toString()));
		return builder.toString();
	}

	/**
	 * @return
	 * 
	 */
	public static String mConfirm() {
		StringBuilder builder = new StringBuilder();
		builder.append("5C").append("A7B7").append("0000")
				.append(CRC.calcCrc16(builder.toString()));
		return builder.toString();
	}
	
	/**
	 * @return
	 * 
	 */
	public static String reTransfer() {
		StringBuilder builder = new StringBuilder();
		builder.append("5C").append("A8B8").append("0000")
				.append(CRC.calcCrc16(builder.toString()));
		return builder.toString();
	}
	
	/**
	 * @return
	 * 
	 */
	public static String ifConnect() {
		StringBuilder builder = new StringBuilder();
		builder.append("5C").append("A1B3").append("0000")
				.append(CRC.calcCrc16(builder.toString()));
		return builder.toString();
	}
	
	/**
	 * @return
	 * ʱ������⴦��
	 */
	public static String transferDateTime(String time) {
		time = time.replace("-", "").replace(":", "").replace(" ", "").trim()
				.substring(2);
		return time;
	}
	
	/**
	 * @return
	 * ͬ��ʱ������⴦��
	 */
	public static String getDate() {
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd  HH:mm:ss");
		String date = sDateFormat.format(new java.util.Date())
				.replaceAll("-", "").replaceAll(":", "").replaceAll("  ", "");
		StringBuilder builder = new StringBuilder();
		builder.append(date.substring(2)).append("000000000000");

		return DataUtils.decstr2HexStr(builder.toString());
	}
}
