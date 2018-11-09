package com.rzspider.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 时间工具类
 * 
 * @author ricozhou
 */
public class DateUtils {
	public static String YYYY = "yyyy";

	public static String YYYY_MM = "yyyy-MM";

	public static String YYYY_MM_DD = "yyyy-MM-dd";

	public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public static String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";

	public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	
	public static String YYYY_MM_DD_HH_MM_SS2 = "yyyy.MM.dd HH:mm";
	
	public static String YYYY_MM_DD_HH_MM_SS3 = "yyyy/MM/dd HH:mm";
	
	public static String YYYY_MM_DD_HH_MM_SS4 = "yyyy-MM-dd HH:mm";

	/**
	 * 时间格式(yyyy-MM-dd HH:mm:ss)
	 */
	public static String format(Date date) {
		return format(date, YYYY_MM_DD_HH_MM_SS);
	}

	public static String format(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			return df.format(date);
		}
		return null;
	}

	public static Date formatStringDate(String date, String pattern) {
		try {
			if (date != null) {
				SimpleDateFormat df = new SimpleDateFormat(pattern);
				return df.parse(date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取当前Date型日期
	 * 
	 * @return Date() 当前日期
	 */
	public static Date getNowDate() {
		return new Date();
	}

	/**
	 * 获取当前日期, 默认格式为yyyy-MM-dd
	 * 
	 * @return String
	 */
	public static String getDate() {
		return dateTimeNow(YYYY_MM_DD);
	}

	public static final String getTime() {
		return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
	}

	public static final String dateTimeNow() {
		return dateTimeNow(YYYYMMDDHHMMSS);
	}

	public static final String dateTimeNow(final String format) {
		return parseDateToStr(format, new Date());
	}

	public static final String dateTime(final Date date) {
		return parseDateToStr(YYYY_MM_DD, date);
	}

	public static final String parseDateToStr(final String format, final Date date) {
		return new SimpleDateFormat(format).format(date);
	}

	public static final Date dateTime(final String format, final String ts) {
		try {
			return new SimpleDateFormat(format).parse(ts);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 日期路径 即年/月/日 如2018/08/08
	 */
	public static final String datePath() {
		Date now = new Date();
		return DateFormatUtils.format(now, "yyyy/MM/dd");
	}

	/**
	 * 日期路径 即年/月/日 如20180808
	 */
	public static final String dateTime() {
		Date now = new Date();
		return DateFormatUtils.format(now, "yyyyMMdd");
	}

	// 取出时间分量年月日时分秒
	public static int[] getTimeComponent(String dateTime, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date date = new Date();
		try {
			date = df.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 指定日期
		Calendar cal = df.getCalendar();
		// 当前时间
		Calendar cas = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		int WeekOfYear = cal.get(Calendar.DAY_OF_WEEK);// 一周的第几天
		// System.out.println(year);
		// System.out.println(month);
		// System.out.println(day);
		// System.out.println(hour);
		// System.out.println(minute);
		// System.out.println(second);
		// System.out.println(WeekOfYear);

		return new int[] { year, month, day, hour, minute, second, WeekOfYear };
	}

	// 计算时间返回秒
	public static long getTimeFromTwoDate(Date startTime, Date endTime) {
		return (endTime.getTime() - startTime.getTime()) > 0 ? (endTime.getTime() - startTime.getTime()) : 0;
	}

	// 返回固定格式时间
	public static String formatDuring(long mss) {
		long days = mss / (1000 * 60 * 60 * 24);
		long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (mss % (1000 * 60)) / 1000;
		return ((days > 0) ? (days + " 天 ") : "") + ((hours > 0) ? (hours + " 时 ") : "")
				+ ((minutes > 0) ? (minutes + " 分 ") : "") + seconds + " 秒 ";
	}
}
