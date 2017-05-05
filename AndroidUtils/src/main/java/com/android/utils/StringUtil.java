package com.android.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class StringUtil {
	/**
	 * 根据字节数组和编码生成字符串
	 * 
	 * @param bytes
	 * @param encoding
	 * @return
	 */
	public static String getString(byte[] bytes, String encoding) {
		if (bytes != null && bytes.length > 0) {
			try {
				return new String(bytes, encoding);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 检查字符串是否为空字符串.
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * 格式化double,保留到小数点后fractionDigits位
	 * 
	 * @param fractionDigits
	 * @param value
	 * @return
	 */
	public static String cutNumber(int fractionDigits, double value) {
		NumberFormat format = NumberFormat.getInstance();
		format.setMaximumFractionDigits(fractionDigits);
		return format.format(value);
	}
	
	public static String formatNumb(double value){
		DecimalFormat df1 = new DecimalFormat("#0.0");
		return df1.format(value);
	}

	/**
	 * 将异常信息转化成字符串
	 * 
	 * @param t
	 * @return
	 * @throws IOException
	 */
	public static String exceptionToString(Throwable t) {
		if (t == null)
			return "exception is empty";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			t.printStackTrace(new PrintStream(baos));
		} finally {
			try {
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return baos.toString();
	}

	/**
	 * Convert char to byte
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	public static boolean checkValidUrl(String url){
		return url.startsWith("http") || url.startsWith("https");
	}

	private static String TruncateUrlPage(String strURL) {
		String strAllParam = null;
		String[] arrSplit = null;
		strURL = strURL.trim();
		arrSplit = strURL.split("[?]");
		if (strURL.length() > 1) {
			if (arrSplit.length > 1) {
				if (arrSplit[1] != null) {
					strAllParam = arrSplit[1];
				}
			}
		}
		return strAllParam;
	}

	public static Map<String, String> parseUrl(String URL, String charset) {
		Map<String, String> mapRequest = new HashMap<String, String>();
		String[] arrSplit = null;
		String strUrlParam = TruncateUrlPage(URL);
		if (strUrlParam == null) {
			return mapRequest;
		}
		// 每个键值为一组
		arrSplit = strUrlParam.split("[&]");
		for (String strSplit : arrSplit) {
			String[] arrSplitEqual = null;
			arrSplitEqual = strSplit.split("[=]");
			// 解析出键值
			if (arrSplitEqual.length > 1) {
				// 正确解析
				try {
					mapRequest.put(arrSplitEqual[0], URLDecoder.decode(arrSplitEqual[1], charset));
				} catch (UnsupportedEncodingException e) {
					mapRequest.put(arrSplitEqual[0], "");
				}
			} else {
				if (arrSplitEqual[0] != "") {
					// 只有参数没有值，不加入
					mapRequest.put(arrSplitEqual[0], "");
				}
			}
		}
		return mapRequest;
	}

	public static Map<String, String> parseUrl(String url) {
		return parseUrl(url, "UTF-8");
	}
}
