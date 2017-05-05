package com.android.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * TimeUtils
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-8-24
 */
public class TimeUtil {

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_DATE    = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * long time to string
     * 
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     * 
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }
    
    public static String getTime(long timeInMillis, String format) {
    	 return getTime(timeInMillis, new SimpleDateFormat(format));
    }

    public static long getTime(String date, String format){
        long lTime=0;
        try {
            SimpleDateFormat sdf= new SimpleDateFormat(format);
            Date dt2 = sdf.parse(date);
            //继续转换得到毫秒的long型
            lTime = dt2.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return lTime;
    }
    
    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     * 
     * @param timeInMillis
     * @return
     */
    public static String getTime(String srcTime, String srcDateFormat, String tarDateFormat) {
    	Date date = null;
		try {
			date = new SimpleDateFormat(srcDateFormat).parse(srcTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new SimpleDateFormat(tarDateFormat).format(date);
    }

    /**
     * get current time in milliseconds
     * 
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
     * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
     * 
     * @return
     */
    public static String getCurrentTimeInString() {
        return getTime(getCurrentTimeInLong());
    }

    /**
     * get current time in milliseconds
     * 
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }
    
    /**
	 * 将日期格式的字符串转换为长整型
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static long convert2long(String date, String format) {
		try {
			if (!StringUtil.isEmpty(date)) {
				if (StringUtil.isEmpty(format))
					format = "yyyy-MM-dd HH:mm:ss";
				SimpleDateFormat sf = new SimpleDateFormat(format);
				return sf.parse(date).getTime();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0l;
	}

    public static boolean isToday(long time){
       return getTime(time, "dd").equals(getTime(System.currentTimeMillis(), "dd"));
    }

	/**
	 * 计算两个字符串日期的差值
	 * @param date1
	 * @param date2
	 * @param format
	 * @return
	 */
	public static long getDateDifference(String date1, String date2, String format) {
		return Math.abs(convert2long(date2, format) - convert2long(date1, format)) / 1000;
	}

    public static long getTommorowStartTime(){

        long _TommorowStartTime = 0;
        try {
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            String nowDate = sf.format(date);
            cal.setTime(sf.parse(nowDate));
            cal.add(Calendar.DAY_OF_YEAR, +1);
            String nextDate = sf.format(cal.getTime());
            nextDate += " 00:00:00";

            sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date2 = sf.parse(nextDate);

            _TommorowStartTime = date2.getTime();

        } catch (Exception e){
            e.printStackTrace();
        }
        return _TommorowStartTime;
    }

    public static String getDelayTime(int delay, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, delay);
        return sdf.format(c.getTime());
    }
}
