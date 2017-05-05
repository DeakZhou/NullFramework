package com.android.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

	public static final String REGEX_MOBILE = "^1[3-9]\\d{9}$";;
	public static final String REGEX_SPECIAL_CHAR = "^[a-zA-Z0-9]+$"; //检测是否有特殊字符
	public static final String REGEX_PLATE_NO = "^[\\u4e00-\\u9fa5|WJ]{1}[A-Za-z0-9]{5}[A-Za-z0-9港澳]{1}$";
	public static final String REGEX_NUMBER = "^[1-9][0-9]*$"; //检测是否有特殊字符
	public static final String REGEX_CHINESE = "[\\u4e00-\\u9fa5]"; //检测中文
	public static final String REGEX_CARD_IDENTITY = "^[0-9a-zA-Z]{32}$";
	public static final String REGEX_POSITIVE_NUMBER = "/^[1-9]+\\d*$";
	public static final String REGEX_IMG_LINK="(<img\\s[\\s\\S]*?src=['\"](.*?)['\"][\\s\\S]*?>)+?";

	public static boolean regexMatch(String s, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(s.toString());
		return m.find();
	}
}
