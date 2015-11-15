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
 * ����ת��������
 * 
 * @author Administrator
 * 
 */
public class DataUtils {

	public static SimpleDateFormat longSdf = new SimpleDateFormat(
			"yyyyMMddHHmmssSSS");// HH��24Сʱ��
	public static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");// HH��24Сʱ��
	public static SimpleDateFormat shortSdf = new SimpleDateFormat(
			"HH:mm");// HH��24Сʱ��

	private static String resultString = null;
	private static String resultTrimData = null;
	public static String CHAR_ENCODE = "UTF-8";

	public static DecimalFormat df = new DecimalFormat("0.00");

	public DataUtils() {

	}

	/**
	 * ȡ��String�ַ���ǰ��λ
	 */
	public static String getTag(String data) {
		if (!data.isEmpty()) {
			resultString = data.substring(0, 2);
		}
		return resultString;
	}

	/**
	 * ȥ��String�ַ������еĿհ׷�
	 */
	public static String getTrimData(String data) {
		if (!data.isEmpty()) {
			resultTrimData = data.replaceAll("\\s*", "");
		}
		return resultTrimData;
	}

	/**
	 * ȥ��String�ַ������еĿհ׷�
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
	 * ����ת��1���ֽڵ�ʮ�������ַ�
	 */
	public static String toOneByteHex(int i) {
		String st;
		st = Integer.toHexString(i).toUpperCase();// ����ת16�����ַ���
		if (st.length() < 2) {
			st = "0" + st;
		}
		return st;
	}

	/**
	 * ����ת��2���ֽڵ�ʮ�������ַ�
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
	 * byte[]ת��Ϊ16����String�ַ�, ÿ���ֽ�2λ, ���㲹0
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
	 * ��16����String�ַ�ת��Ϊbyte[]
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
	 * ȡ����16�����ַ����и�char�������16������
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * ��������
	 */
	public static void helpInfo() {
		System.out.println("ʮ����ת��ʮ������: Integer.toHexString(int i) ");
		System.out.println("ʮ������ת��ʮ����: Integer.valueOf('FFFF',16).toString() ");
	}

	/**
	 * ���ݴ��������double���͵�ʱ���, ���߱Ƚϴ�, �������֮����˶���Сʱ
	 */
	public static double getHours(double date1, double date2) {
		double hours = (date2 - date1) / (1000 * 60 * 60);
		hours = Double.parseDouble(df.format(hours));
		// hours = Math.round((hours * 100) / 100);
		return hours;
	}

	/**
	 * ���ݴ���ĺ�����, ת����ΪHH:MM:SS���ַ�������
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
	 * ���ݴ�����������ͺ͵绰���룬�����ֽ�����
	 * 
	 * @param remindType
	 *            ��������
	 * @param phoneNumber
	 *            �绰����
	 * @return
	 */
	public static byte[] getBytesForRemind(String remindType, String phoneNumber) {
		StringBuffer remindStr = new StringBuffer();
		// ���ѵ�����
		String type = Integer.toHexString(Integer.parseInt(remindType, 2));
		// �绰�����λ��
		String length = Integer.toHexString(phoneNumber.length());

		remindStr.append("F1");
		remindStr.append(type);
		remindStr.append("000");
		remindStr.append(length.toUpperCase());
		// ���������ֻ�����Ĵ���
		phoneNumber = (phoneNumber.length() % 2) == 0 ? phoneNumber
				: phoneNumber + "0";
		remindStr.append(phoneNumber);
		System.out.println("��ʾ����Э�飺" + remindStr.toString());
		return getBytesByString(remindStr.toString());
	}

	/**
	 * ���ݴ����2���ֽ�4λ16�����ַ�����FFFF, ���㷵��int���͵ľ���ֵ
	 */
	public static int hexStringX2bytesToInt(String hexString) {
		return binaryString2int(hexString2binaryString(hexString));
	}

	/**
	 * 16����ת��Ϊ2����
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
	 * ������תΪ10���� ����int
	 */
	public static int binaryString2int(String binarysString) {
		if (binarysString == null || binarysString.length() % 8 != 0) {
			return 0;
		}
		int result = Integer.valueOf(binarysString, 2);
		if ("1".equals(binarysString.substring(0, 1))) {
			System.out.println("���Ǹ�����");
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
	 * ������תΪ16����
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
	 * ȡ�õ�ǰ�ķ�����
	 * 
	 * @return
	 */
	public static int getCurrentMinute() {
		Calendar c = Calendar.getInstance();
		int minute = c.get(Calendar.MINUTE);
		return minute;
	}

	/**
	 * ȡ�õ�ǰ������
	 */
	public static int getCurrentSecond() {
		Calendar c = Calendar.getInstance();
		int minute = c.get(Calendar.SECOND);
		return minute;
	}

	/**
	 * ���ݴ�����������㷵������ֵ �紫��156������200
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
			System.out.println("ת�����max:" + max);
			return max;
		}
	}

	/***************************
	 * ƴ��У����*************************
	 * **********************************************************
	 */
	public byte[] dataSplice(byte[] data) throws IOException {
		// byte[] crc = DecodeCRC16.calu(data); //����CRCУ���루2�ֽڣ�
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
		data = hexStringToByte(sendData); // ʮ�������ַ���ת��ΪByte����

		baos.reset();
		baos.write(data);
		return baos.toByteArray();
	}

	/***************************************************************
	 * ************************ȡ�ö�����תΪʮ���������ɵ��ַ���***************
	 * ************************ÿ��λ֮��׷�ӿո�**************************
	 */
	public synchronized static String getByteOfHexHasBlank(byte[] data) {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			buf.append(" " + DataUtils.byte2Hex(data[i]));
		}
		return buf.toString();
	}

	/***************************************************************
	 * ************************������ת��Ϊʮ�������ַ���*********************
	 * ************************λ��������λ���ײ���0************************
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
	 * *********************ʮ�������ַ���(2λ)ת��Ϊ�ֽ�����******************
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
	 * *********************�ַ�ת��Ϊ�ֽ�********************************
	 */
	private synchronized static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}

	/**************************************************************
	 * ���ַ���ת��ΪUnicode�����16�����ַ���,�����������ַ����������ģ�
	 * ************************************************************
	 */
	public static String stringToUnicode(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);// Returns the character at the specified
										// offset in this
										// string.���ظ��ַ��ĸ�λ�룬ת��int��

			// ԭװ����
			// if (ch > 255)
			// str += "\\u" + Integer.toHexString(ch);
			// else
			// str += "\\" + Integer.toHexString(ch);

			str += Integer.toHexString(ch);

		}
		return str;
	}

	/********************************************************************
	 * ��Unicode���루�����ַ������֡���㡢Ӣ���ַ���16�����ַ���������ַ���,�����������ַ����������ģ�
	 * ******************************************************************
	 */
	public static String unicodeToString(String str) {

		// ��Э�������ʵ��
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
			Log.e("���������", str);
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
	 * *************************�����ı���(GBK)ת��Ϊʮ�������ַ���**************************
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
	 * *************************�������ַ�ת��ΪGBK�����ʮ�������ַ�������������ֽ���***************
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
	 * ****************************��GBK�����ʮ�������ַ����չ�16λ8bytes************** *
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
	 * ************GBK���루�����ַ������֡���㡢Ӣ���ַ��ȣ����ֽ�����ת���������ַ���************
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
	 * ************************�������ֽڵ�����ת��int������******************
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
			for (int j = myStr1.length - 1; j >= 0; j--) { // ���Ϊabcdef,��ת��
				if (param.equalsIgnoreCase(myStr1[j]))
					param = "1" + String.valueOf(j);
			}
			result += Integer.parseInt(param) * n;
			n *= 16;
		}
		return result;
	}

	// ƴ�����ݿ�ͷ��
	public byte[] concat(byte[] a, byte[] b) {
		byte[] c = new byte[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}

	/********************************************************************************
	 * **********************��λ������У����*******************************************
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
	 *************************** ��Ϣ����ת��Ϊʮ���Ʊ�ʾ���ַ�������λ����Ҫ��0��*********************
	 ********************************************************************************* 
	 */
	public static String hexCount2Dec(String str) {
		StringBuilder ab = new StringBuilder();
		if (str.length() % 2 == 0) {
			for (int i = 0; i < str.length(); i = i + 2) {
				String dec = String.valueOf(Integer.parseInt(
						str.substring(i, i + 2), 16)); // ��������
				ab.append(dec);
			}
		} else {
			Log.e("TAG1", "תʮ���Ƴ����⣨���ڣ�");
		}
		return ab.toString();
	}

	/*********************************************************************************
	 *************** ���ڡ����к�ת��Ϊʮ���ƣ�100���ڣ���ʾ���ַ�������λ�������λ��0��*******************
	 ********************************************************************************* 
	 */
	public static String hex2Dec(String str) {
		StringBuilder ab = new StringBuilder();
		if (str.length() % 2 == 0) {
			for (int i = 0; i < str.length(); i = i + 2) {
				String dec = String.valueOf(Integer.parseInt(
						str.substring(i, i + 2), 16)); // ��������
				if (dec.length() == 1) {
					dec = "0" + dec;
				}
				ab.append(dec);
			}
		} else {
			Log.e("TAG1", "תʮ���Ƴ����⣨���ڣ�");
		}
		return ab.toString();
	}

	/*********************************************************************************
	 ******************************** ��������ת��Ϊʮ���Ʊ�ʾ�ĸ���������*************************
	 ********************************************************************************* 
	 */
	public static float[] hex2DecStr(String str) { // ������
		float[] test = new float[str.length() / 2];
		if (str.length() % 2 == 0) {
			for (int i = 0, j = 0; i < str.length(); i = i + 2, j++) {
				float dec = Integer.parseInt(str.substring(i, i + 2), 16); // ��������
				test[j] = dec;
			}
		} else {
			Log.e("��������ת�����ͳ�����", "=-=-=-=-=-=-=-=-=hex2DecStr (str):" + str);
		}
		return test;
	}

	/*********************************************************************************
	 ******************************** ��������ת��Ϊʮ���Ʊ�ʾ���ַ���****************************
	 ********************************************************************************* 
	 */
	// 0F0312140E04
	public static String hexTime2DecStr(String str) { // ������
		String time = hex2Dec(str);
		if (time.length() == 12) {
			time = "20" + time.substring(0, 2) + "-" + time.substring(2, 4)
					+ "-" + time.substring(4, 6) + " " + time.substring(6, 8)
					+ ":" + time.substring(8, 10) + ":"
					+ time.substring(10, 12);
		} else {
			Log.e("�������ڸ�ʽ������", "=-=-=-=-=-=-=-=-=hexTime2DecStr (str):" + str);
		}
		return time;
	}

	/*************************************************************
	 * ***********************************************************
	 * **********************int��ת��Ϊ16����************************ ����������
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
	 * *************6�������ַ�ת��Ϊ12��16�����ַ���GBK���룩*********
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
	 * *********************����������ַ��ת��Ϊʮ�������ַ���*******************
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
	 * ***********************ͬ���ַ�ת��****************************
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
		data = DataUtils.hexStringToByte(sendData); // ʮ�������ַ���ת��ΪByte����

		baos.reset();
		baos.write(data);
		return baos.toByteArray();
	}
}
