package com.android.utils;

import java.text.DecimalFormat;


public class MathUtil {
	public static double getDistance(float x1, float y1, float x2, float y2) {
		return Math.pow(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2), 0.5);
	}
	
	/**
	 * Specialization of format.
	 * @param num 
	 * @param format eg: #.##
	 */
	public static String format(double num, String format) {
        DecimalFormat df = new DecimalFormat(format);
        return df.format(num);
	}
	
	public static String getFormatMoney(long cent) {
		if(cent % 100 == 0) {
			return  String.valueOf(cent/100);
		} else {
			if(cent % 10 == 0) {
				return String.format("%.1f", cent/100.00f);
			}
			return  String.format("%.2f", cent/100.00f);
		}
	}
	public static String getFormatDiscount(long discount) {
		/*if(discount % 10 == 0) {
			return  String.valueOf(discount/10);
		} else {
			return  String.format("%.1f", discount/10.0f);
		}*/
		return  String.format("%.1f", discount/10.0f);
	}
	public static String getFormatDistance(double distance) {
		String formatDistance = "";
		if(distance >= 1000) {
			formatDistance = String.format("%.1f", distance/1000.0f) + "km";
		} else {
			formatDistance = String.format("%.0f", distance) + "m";
		}
		return formatDistance;
	}
}
