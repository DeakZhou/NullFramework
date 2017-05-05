package com.android.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	
	/**
	 * MD5加密,返回MD5加密后的字节数组.
	 * @param str
	 * @return
	 */
	public static byte[] encrypt(String str, String encoding) {
		byte [] result = null;
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
			digest.reset();
			digest.update(str.getBytes(encoding));
			return digest.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * MD5 加密，返回16进制字符串.
	 * @param str
	 * @return
	 */
	public static String encrypt2HexString(String str) {
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
			digest.reset();
			digest.update(str.getBytes("UTF-8"));
			byte[] messageDigest = digest.digest();
			return toHexString(messageDigest); // 将加密后的字节以十六进制形式字符串返回
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
			sb.append(HEX_DIGITS[b[i] & 0x0f]);
		}
		return (sb.toString()).toLowerCase();  
	}
	
}