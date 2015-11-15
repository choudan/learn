package com.example.yijia.third.tool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

/**
 * 数据转换工具类
 * 
 * @author Administrator
 * 
 */
public class DataUtils {

	public static SimpleDateFormat longSdf = new SimpleDateFormat(
			"yyyyMMddHHmmssSSS");// HH：24小时制
	public static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");// HH：24小时制
	public static SimpleDateFormat shortSdf = new SimpleDateFormat(
			"HH:mm");// HH：24小时制

	private static String resultString = null;
	private static String resultTrimData = null;
	public static String CHAR_ENCODE = "UTF-8";

	public static DecimalFormat df = new DecimalFormat("0.00");

	public DataUtils() {

	}

	/**
	 * 取得String字符的前两位
	 */
	public static String getTag(String data) {
		if (!data.isEmpty()) {
			resultString = data.substring(0, 2);
		}
		return resultString;
	}

	/**
	 * 去除String字符中所有的空白符
	 */
	public static String getTrimData(String data) {
		if (!data.isEmpty()) {
			resultTrimData = data.replaceAll("\\s*", "");
		}
		return resultTrimData;
	}

	/**
	 * 去除String字符中所有的空白符
	 */
	public static String decstr2HexStr(String str) {
		StringBuffer sb = new StringBuffer();
		if (str.length() % 2 == 0) {
			for (int i = 0; i < str.length(); i = i + 2) {
				int value = Integer.parseInt(str.substring(i, i + 2));
				if (value != 0) {
					String hex = Integer.toHexString(value).toUpperCase();
					if (hex.length() == 1) {
						hex = "0" + hex;
					}
					sb.append(hex);
				} else {
					sb.append("00");
				}
			}
		} else
			return null;
		return sb.toString();
	}

	/**
	 * 整型转化1个字节的十六进制字符
	 */
	public static String toOneByteHex(int i) {
		String st;
		st = Integer.toHexString(i).toUpperCase();// 整型转16进制字符串
		if (st.length() < 2) {
			st = "0" + st;
		}
		return st;
	}

	/**
	 * 整型转化2个字节的十六进制字符
	 */
	public static String toTwoByteHex(int i) {
		String st;
		st = Integer.toHexString(i).toUpperCase();
		switch (st.length()) {
		case 1:
			st = "000" + st;
			break;
		case 2:
			st = "00" + st;
			break;
		case 3:
			st = "0" + st;
			break;
		case 4:
			break;
		}
		return st;
	}

	/**
	 * byte[]转变为16进制String字符, 每个字节2位, 不足补0
	 */
	public static String getStringByBytes(byte[] bytes) {
		String result = null;
		String hex = null;
		if (bytes != null && bytes.length > 0) {
			final StringBuilder stringBuilder = new StringBuilder(bytes.length);
			for (byte byteChar : bytes) {
				hex = Integer.toHexString(byteChar & 0xFF);
				if (hex.length() == 1) {
					hex = '0' + hex;
				}
				stringBuilder.append(hex.toUpperCase());
			}
			result = stringBuilder.toString();
		}
		return result;
	}

	/**
	 * 把16进制String字符转变为byte[]
	 */
	public static byte[] getBytesByString(String data) {
		byte[] bytes = null;
		if (data != null) {
			data = data.toUpperCase();
			int length = data.length() / 2;
			char[] dataChars = data.toCharArray();
			bytes = new byte[length];
			for (int i = 0; i < length; i++) {
				int pos = i * 2;
				bytes[i] = (byte) (charToByte(dataChars[pos]) << 4 | charToByte(dataChars[pos + 1]));
			}
		}
		return bytes;
	}

	/**
	 * 取得在16进制字符串中各char所代表的16进制数
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * 方法介绍
	 */
	public static void helpInfo() {
		System.out.println("十进制转成十六进制: Integer.toHexString(int i) ");
		System.out.println("十六进制转成十进制: Integer.valueOf('FFFF',16).toString() ");
	}

	/**
	 * 根据传入的两个double类型的时间戳, 后者比较大, 算出他们之间隔了多少小时
	 */
	public static double getHours(double date1, double date2) {
		double hours = (date2 - date1) / (1000 * 60 * 60);
		hours = Double.parseDouble(df.format(hours));
		// hours = Math.round((hours * 100) / 100);
		return hours;
	}

	/**
	 * 根据传入的耗秒数, 转换成为HH:MM:SS的字符串返回
	 */
	public static String getHHMMSS(long time) {
		String hhmmss = "00:00:00";
		StringBuffer bf = new StringBuffer();
		long hh = time / 1000 / 60 / 60;
		long mm = (time % (1000 * 60 * 60)) / 1000 / 60;
		long ss = ((time % (1000 * 60 * 60)) % (1000 * 60)) / 1000;

		if (hh < 0) {
			bf.append("00:");
		} else if (hh < 10) {
			bf.append("0" + hh + ":");
		} else {
			bf.append(hh + ":");
		}
		if (mm < 0) {
			bf.append("00:");
		} else if (mm < 10) {
			bf.append("0" + mm + ":");
		} else {
			bf.append(mm + ":");
		}
		if (ss < 0) {
			bf.append("00");
		} else if (ss < 10) {
			bf.append("0" + ss);
		} else {
			bf.append(ss);
		}
		hhmmss = bf.toString();
		System.out.println(hhmmss);
		return hhmmss;
	}

	/**
	 * 根据传入的提醒类型和电话号码，返回字节数组
	 * 
	 * @param remindType
	 *            提醒类型
	 * @param phoneNumber
	 *            电话号码
	 * @return
	 */
	public static byte[] getBytesForRemind(String remindType, String phoneNumber) {
		StringBuffer remindStr = new StringBuffer();
		// 提醒的类型
		String type = Integer.toHexString(Integer.parseInt(remindType, 2));
		// 电话号码的位数
		String length = Integer.toHexString(phoneNumber.length());

		remindStr.append("F1");
		remindStr.append(type);
		remindStr.append("000");
		remindStr.append(length.toUpperCase());
		// 对座机、手机号码的处理
		phoneNumber = (phoneNumber.length() % 2) == 0 ? phoneNumber
				: phoneNumber + "0";
		remindStr.append(phoneNumber);
		System.out.println("提示数据协议：" + remindStr.toString());
		return getBytesByString(remindStr.toString());
	}

	/**
	 * 根据传入的2个字节4位16进制字符比如FFFF, 计算返回int类型的绝对值
	 */
	public static int hexStringX2bytesToInt(String hexString) {
		return binaryString2int(hexString2binaryString(hexString));
	}

	/**
	 * 16进制转换为2进制
	 */
	public static String hexString2binaryString(String hexString) {
		if (hexString == null || hexString.length() % 2 != 0) {
			return null;
		}
		String bString = "", tmp;
		for (int i = 0; i < hexString.length(); i++) {
			tmp = "0000"
					+ Integer.toBinaryString(Integer.parseInt(
							hexString.substring(i, i + 1), 16));
			bString += tmp.substring(tmp.length() - 4);
		}
		return bString;
	}

	/**
	 * 二进制转为10进制 返回int
	 */
	public static int binaryString2int(String binarysString) {
		if (binarysString == null || binarysString.length() % 8 != 0) {
			return 0;
		}
		int result = Integer.valueOf(binarysString, 2);
		if ("1".equals(binarysString.substring(0, 1))) {
			System.out.println("这是个负数");
			char[] values = binarysString.toCharArray();
			for (int i = 0; i < values.length; i++) {
				if (values[i] == '1') {
					values[i] = '0';
				} else {
					values[i] = '1';
				}
			}
			binarysString = String.valueOf(values);
			result = Integer.valueOf(binarysString, 2) + 1;
		}

		return result;
	}

	/**
	 * 
	 * 二进制转为16进制
	 */
	public static String binaryString2hexString(String bString) {
		if (bString == null || bString.equals("") || bString.length() % 8 != 0) {
			return null;
		}
		StringBuffer tmp = new StringBuffer();
		int iTmp = 0;
		for (int i = 0; i < bString.length(); i += 4) {
			iTmp = 0;
			for (int j = 0; j < 4; j++) {
				iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
			}
			tmp.append(Integer.toHexString(iTmp));
		}
		return tmp.toString();
	}

	/**
	 * 取得当前的分钟数
	 * 
	 * @return
	 */
	public static int getCurrentMinute() {
		Calendar c = Calendar.getInstance();
		int minute = c.get(Calendar.MINUTE);
		return minute;
	}

	/**
	 * 取得当前的秒数
	 */
	public static int getCurrentSecond() {
		Calendar c = Calendar.getInstance();
		int minute = c.get(Calendar.SECOND);
		return minute;
	}

	/**
	 * 根据传入的数，计算返回整百值 如传入156，返回200
	 * 
	 * @param snore_count
	 * @return
	 */
	public static int getMaxbySnorecount(int snore_count) {
		String str = (snore_count + "");
		int length = str.length();
		if (length < 3) {
			return 100;
		} else {
			str = str.substring(0, length - 2);
			int max = (Integer.parseInt(str) + 1) * 100;
			System.out.println("转换后的max:" + max);
			return max;
		}
	}

	/***************************
	 * 拼接校验码*************************
	 * **********************************************************
	 */
	public byte[] dataSplice(byte[] data) throws IOException {
		// byte[] crc = DecodeCRC16.calu(data); //产生CRC校验码（2字节）
		// baos.write(data);
		// baos.write(crc);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String sendData = getByteOfHexHasBlank(baos.toByteArray());
		if (sendData.indexOf(" 5C ") != -1) {
			sendData = sendData.replaceAll("5C", "5E 02");
		}
		if (sendData.indexOf(" 5E ") != -1) {
			sendData = sendData.replaceAll("5E", "5E 01");
		}

		sendData = sendData.replace(" ", "");
		data = hexStringToByte(sendData); // 十六进制字符串转换为Byte数组

		baos.reset();
		baos.write(data);
		return baos.toByteArray();
	}

	/***************************************************************
	 * ************************取得二进制转为十六进制生成的字符串***************
	 * ************************每两位之间追加空格**************************
	 */
	public synchronized static String getByteOfHexHasBlank(byte[] data) {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			buf.append(" " + DataUtils.byte2Hex(data[i]));
		}
		return buf.toString();
	}

	/***************************************************************
	 * ************************二进制转换为十六进制字符串*********************
	 * ************************位数不满两位，首部加0************************
	 */
	public synchronized static String byte2Hex(byte b) {
		String hex = Integer.toHexString(b & 0xFF);
		hex = hex.toUpperCase();
		if (hex.length() == 1) {
			return "0" + hex;
		} else {
			return hex;
		}
	}

	/***************************************************************
	 * *********************十六进制字符串(2位)转换为字节数组******************
	 */
	public synchronized static byte[] hexStringToByte(String hex) {
		hex = hex.toUpperCase();
		hex = hex.replace(" ", "");
		if ((hex.length() % 2) != 0) {
			hex += " ";
		}
		int len = hex.length() / 2;
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	/****************************************************************
	 * *********************字符转换为字节********************************
	 */
	private synchronized static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}

	/**************************************************************
	 * 将字符串转化为Unicode编码的16进制字符串,适用于所有字符（包括中文）
	 * ************************************************************
	 */
	public static String stringToUnicode(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);// Returns the character at the specified
										// offset in this
										// string.返回该字符的高位码，转成int型

			// 原装方法
			// if (ch > 255)
			// str += "\\u" + Integer.toHexString(ch);
			// else
			// str += "\\" + Integer.toHexString(ch);

			str += Integer.toHexString(ch);

		}
		return str;
	}

	/********************************************************************
	 * 将Unicode编码（中文字符、数字、标点、英文字符的16进制字符串解码成字符串,适用于所有字符（包括中文）
	 * ******************************************************************
	 */
	public static String unicodeToString(String str) {

		// 该协议的特殊实现
		StringBuilder sb = new StringBuilder();

		if (str.length() % 4 == 0) {
			for (int i = 0; i < str.length(); i = 4 + i) {

				if (Integer.parseInt(str.substring(i, 4 + i), 16) > 255) {
					sb.append("\\u");
				} else {
					sb.append("\\");
				}
				sb.append(str.substring(i, 4 + i));
			}
			str = sb.toString();
		} else {
			Log.e("解码出错了", str);
		}

		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");

		Matcher matcher = pattern.matcher(str);

		char ch;

		while (matcher.find()) {

			ch = (char) Integer.parseInt(matcher.group(2), 16);

			str = str.replace(matcher.group(1), ch + "");

		}
		return str;
	}

	/******************************************************************************
	 * *************************将中文编码(GBK)转换为十六进制字符串**************************
	 * **
	 * ************************************************************************
	 * **
	 */
	public static String stringToGBK(String s)
			throws UnsupportedEncodingException {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			String ch = String.valueOf(s.charAt(i));
			byte[] sgbk = ch.getBytes("gbk");
			String shex = "";
			for (int j = 0; j < sgbk.length; j++) {
				// System.out.println(sb[i]);
				String hex = Integer.toHexString(sgbk[j] & 0xFF);
				if (hex.length() == 1) {
					hex = "0" + hex;
				}
				shex = shex + hex;
			}
			// str = str + " 0x" + shex.toUpperCase();

			str = str + shex.toUpperCase();
		}
		return str;
	}

	/******************************************************************************
	 * *************************将中文字符转换为GBK编码的十六进制字符串，计算编码字节数***************
	 * ****
	 * ************************************************************************
	 */
	public static int getCountStringToGBK(String s)
			throws UnsupportedEncodingException {
		int i = 0;
		String str = stringToGBK(s);
		if (str.length() % 2 == 0)
			i = str.length() / 2;
		return i;
	}

	/************************************************************************
	 * ****************************将GBK编码的十六进制字符串凑够16位8bytes************** *
	 */
	public static String GBK2eight(String str)
			throws UnsupportedEncodingException {
		str = stringToGBK(str);
		switch (str.length()) {
		case 2:
			str = str + "00000000000000";
			break;
		case 4:
			str = str + "000000000000";
			break;
		case 6:
			str = str + "0000000000";
			break;
		case 8:
			str = str + "00000000";
			break;
		case 10:
			str = str + "000000";
			break;
		case 12:
			str = str + "0000";
			break;
		case 14:
			str = str + "00";
			break;
		case 16:
			break;
		}

		return str;
	}

	/************************************************************************
	 * ************GBK编码（中文字符、数字、标点、英文字符等）的字节数组转换成中文字符串************
	 * ***********************************************************************
	 */
	public static String decodeByteToGBK(byte[] data, int offset, int len) {
		if (offset + len > data.length) {
			return new String();
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(data, offset, len);
		try {
			return new String(baos.toByteArray(), "GBK");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String();
	}

	/***************************************************************
	 * ************************把任意字节的数组转成int型整数******************
	 */
	public synchronized static int byte2ArrayToInt(byte[] data, int offset,
			int len) {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < len; i++) {
			buf.append(DataUtils.byte2Hex(data[i + offset]));
		}

		String tmp = buf.toString();
		String myStr1[] = { "a", "b", "c", "d", "e", "f" };
		int result = 0;
		int n = 1;
		for (int i = tmp.length(); i >= 1; i--) {
			String param = tmp.substring(i - 1, i);
			for (int j = myStr1.length - 1; j >= 0; j--) { // 如果为abcdef,则转换
				if (param.equalsIgnoreCase(myStr1[j]))
					param = "1" + String.valueOf(j);
			}
			result += Integer.parseInt(param) * n;
			n *= 16;
		}
		return result;
	}

	// 拼接数据块头部
	public byte[] concat(byte[] a, byte[] b) {
		byte[] c = new byte[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}

	/********************************************************************************
	 * **********************按位异或产生校验码*******************************************
	 * **
	 */
	public byte jiaoyama(byte[] data) throws IOException {
		byte JYM = 0x00;

		for (int i = 0; i < data.length; i++) {
			JYM ^= data[i];
		}
		return JYM;
	}

	/*********************************************************************************
	 *************************** 消息条数转化为十进制表示的字符串（奇位不需要补0）*********************
	 ********************************************************************************* 
	 */
	public static String hexCount2Dec(String str) {
		StringBuilder ab = new StringBuilder();
		if (str.length() % 2 == 0) {
			for (int i = 0; i < str.length(); i = i + 2) {
				String dec = String.valueOf(Integer.parseInt(
						str.substring(i, i + 2), 16)); // 基本类型
				ab.append(dec);
			}
		} else {
			Log.e("TAG1", "转十进制出问题（日期）");
		}
		return ab.toString();
	}

	/*********************************************************************************
	 *************** 日期、序列号转化为十进制（100以内）表示的字符串（两位输出，奇位补0）*******************
	 ********************************************************************************* 
	 */
	public static String hex2Dec(String str) {
		StringBuilder ab = new StringBuilder();
		if (str.length() % 2 == 0) {
			for (int i = 0; i < str.length(); i = i + 2) {
				String dec = String.valueOf(Integer.parseInt(
						str.substring(i, i + 2), 16)); // 基本类型
				if (dec.length() == 1) {
					dec = "0" + dec;
				}
				ab.append(dec);
			}
		} else {
			Log.e("TAG1", "转十进制出问题（日期）");
		}
		return ab.toString();
	}

	/*********************************************************************************
	 ******************************** 测试数据转化为十进制表示的浮点型数组*************************
	 ********************************************************************************* 
	 */
	public static float[] hex2DecStr(String str) { // 有问题
		float[] test = new float[str.length() / 2];
		if (str.length() % 2 == 0) {
			for (int i = 0, j = 0; i < str.length(); i = i + 2, j++) {
				float dec = Integer.parseInt(str.substring(i, i + 2), 16); // 基本类型
				test[j] = dec;
			}
		} else {
			Log.e("采样数据转浮点型出问题", "=-=-=-=-=-=-=-=-=hex2DecStr (str):" + str);
		}
		return test;
	}

	/*********************************************************************************
	 ******************************** 测试日期转化为十进制表示的字符串****************************
	 ********************************************************************************* 
	 */
	// 0F0312140E04
	public static String hexTime2DecStr(String str) { // 有问题
		String time = hex2Dec(str);
		if (time.length() == 12) {
			time = "20" + time.substring(0, 2) + "-" + time.substring(2, 4)
					+ "-" + time.substring(4, 6) + " " + time.substring(6, 8)
					+ ":" + time.substring(8, 10) + ":"
					+ time.substring(10, 12);
		} else {
			Log.e("采样日期格式出问题", "=-=-=-=-=-=-=-=-=hexTime2DecStr (str):" + str);
		}
		return time;
	}

	/*************************************************************
	 * ***********************************************************
	 * **********************int型转换为16进制************************ 方法有问题
	 */
	public static String toTwoHex(int data) {
		String s = Integer.toHexString(data);
		if (s.length() < 2) {
			s = "0" + s;
		}
		return s;
	}

	/**
	 * *********************************************************
	 * *************6个中文字符转换为12个16进制字符（GBK编码）*********
	 */
	public static String toTHex(String data)
			throws UnsupportedEncodingException {
		String s = stringToGBK(data);
		switch (data.length()) {
		case 2:
			s = s + "0000000000000000";
			break;
		case 3:
			s = s + "000000000000";
			break;
		case 4:
			s = s + "00000000";
			break;
		case 5:
			s = s + "0000";
			break;
		case 6:
			break;
		default:
			break;
		}
		return s;
	}

	/**
	 * **************************************************************
	 * *********************解析蓝牙地址，转换为十六进制字符串*******************
	 */
	public static String adressToString(String adress) {
		StringBuffer sb = new StringBuffer();
		String[] d = adress.split(":");
		if (d.length > 0) {
			for (int i = 0; i < d.length; i++) {
				sb.append(d[i]);
			}
		}
		return sb.toString();
	}

	/**
	 * **********************************************************
	 * ***********************同名字符转义****************************
	 */
	public byte[] zhuanYi(byte[] data) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String sendData = DataUtils.getByteOfHexHasBlank(baos.toByteArray());
		if (sendData.indexOf(" 5C ") != -1) {
			sendData = sendData.replaceAll("5C", "5E 02");
		}
		if (sendData.indexOf(" 5E ") != -1) {
			sendData = sendData.replaceAll("5E", "5E 01");
		}

		sendData = sendData.replace(" ", "");
		data = DataUtils.hexStringToByte(sendData); // 十六进制字符串转换为Byte数组

		baos.reset();
		baos.write(data);
		return baos.toByteArray();
	}
}
