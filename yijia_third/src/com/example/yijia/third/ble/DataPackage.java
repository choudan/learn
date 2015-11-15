package com.example.yijia.third.ble;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.example.yijia.third.tool.CRC;
import com.example.yijia.third.tool.DataUtils;

/**
 * @author 丑旦
 * @date 创建时间：2015/10/27 下午8:05:32
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
					.get(i))));// 医生回复时间，6字节>>>>>>>>>>>>>>>>>>>>>>>（进制处理）
			builder.append(DataUtils.toTwoByteHex(DataUtils
					.getCountStringToGBK(message_doc.get(i))));// 医生回复长度，占2字节+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=
			builder.append(DataUtils.stringToGBK(message_doc.get(i)));// 医生回复内容，长度不确定>>>>>>>>（中文处理GBK）
		}
		builder.insert(6, DataUtils.toTwoByteHex((builder.length() - 6) / 2));// 消息体长度，2字节，值不确定.length有问题++++++++++++++++++++
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
	 * 时间的特殊处理
	 */
	public static String transferDateTime(String time) {
		time = time.replace("-", "").replace(":", "").replace(" ", "").trim()
				.substring(2);
		return time;
	}
	
	/**
	 * @return
	 * 同步时间的特殊处理
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
