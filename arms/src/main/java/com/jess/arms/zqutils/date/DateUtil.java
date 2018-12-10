package com.jess.arms.zqutils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


/**
 * 日期管理工具
 * */
public class DateUtil {
	private static final String TAG = DateUtil.class.getSimpleName();

	public static final String YEAR = "yyyy";
	public static final String MONTH = "MM";
	public static final String DAY = "dd";
	public static final String WEEK = "EEE";
	public static final String HOUR_FORMAT_24 = "HH";
	public static final String HOUR_FORMAT_12 = "hh";
	public static final String MINUTE = "mm";
	public static final String SECOND = "ss";
	
	/*常用时间单位毫秒数，到天，再往上没有意义了，请通过日历获取*/
	/**一秒的毫秒数*/
	public static final long MS_ONE_SECOND = 1000;
	/**一分钟的毫秒数*/
	public static final long MS_ONE_MINUTE = MS_ONE_SECOND * 60;
	/**一小时的毫秒数*/
	public static final long MS_ONE_HOUR = MS_ONE_MINUTE * 60;
	/**一天的毫秒数*/
	public static final long MS_ONE_DAY = MS_ONE_HOUR * 24;
	

	/**
	 * 用于获取当前系统的时间
	 * */
	public static Date getCurrentDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getDefault());
		return calendar.getTime();
	}

	public static long getZeroTimeOfToday() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	/**
	 * 获取当前时间戳
	 * */
	public static long currentTimeMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * 将字符串日期转为Date类型
	 * */
	public static Date convertToDate(String format, String strDate) {
		SimpleDateFormat sdf =  new SimpleDateFormat(format, Locale.getDefault());
		try {
			return sdf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将日期转换为时间戳
	 * */
	public static long convertToTimestamp(Date date) {

		return date.getTime();
	}

	/**
	 * 将string格式日期转换为时间戳
	 * @param format String日期格式化
	 * @param strDate string日期
	 * @return timestamp
	 */
	public static long convertToTimestamp(String format, String strDate) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		try {
			Date date = sdf.parse(strDate);
			return date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String formatDate(String format, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.getDefault());
		try {
			return sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String formatDate(String format, long timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		try {
			return sdf.format(new Date(timestamp));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * 当前是否在指定时间段内
	 * @param format 格式化字符串
	 * @param start	开始时间戳
	 * @param end	结束时间戳
	 * @return true 在时间段内， false 不在时间段内
	 */
	public static boolean isInThePeriod(String format, long start, String end) {
		return isInThePeriod(
				start,
				convertToTimestamp(format, end)
		);
	}

	/**
	 * 当前是否在指定时间段内
	 * @param format 格式化字符串
	 * @param start	开始时间戳
	 * @param end	结束时间戳
	 * @return true 在时间段内， false 不在时间段内
	 */
	public static boolean isInThePeriod(String format, String start, long end) {
		return isInThePeriod(
				convertToTimestamp(format, start),
				end
		);
	}
	/**
	 * 当前是否在指定时间段内
	 * @param format 格式化字符串
	 * @param start	开始时间
	 * @param end	结束时间
	 * @return true 在时间段内， false 不在时间段内
	 */
	public static boolean isInThePeriod(String format, String start, String end) {
		return isInThePeriod(
				convertToTimestamp(format, start),
				convertToTimestamp(format, end)
		);
	}

	/**
	 * 当前是否在指定时间段内
	 * @param start	开始时间戳
	 * @param end	结束时间戳
	 * @return true 在时间段内， false 不在时间段内
	 */
	public static boolean isInThePeriod(long start, long end) {
		long cur = currentTimeMillis();
		return cur >= start && cur <= end;
	}

	/**
	 * 比较两个时间字符串的大小
	 * @param format 格式化字符串
	 * @param compared 开始时间
	 * @param compareTo 结束时间
	 * @return 1: compared > compareTo; 0: compared = compareTo; -1: compared < compareTo;
	 */
	public static int compareDate(String format, Date compared, Date compareTo) {
		return compareDate(format, formatDate(format, compared), formatDate(format, compareTo));
	}

	/**
	 * 比较两个时间字符串的大小
	 * @param format 格式化字符串
	 * @param compared 开始时间
	 * @param compareTo 结束时间
	 * @return 1: compared > compareTo; 0: compared = compareTo; -1: compared < compareTo;
	 */
	public static int compareDate(String format, long compared, long compareTo) {
		return compareDate(format, formatDate(format, compared), formatDate(format, compareTo));
	}

	/**
	 * 比较两个时间字符串的大小
	 * @param format 格式化字符串
	 * @param compared 开始时间
	 * @param compareTo 结束时间
	 * @return 1: compared > compareTo; 0: compared = compareTo; -1: compared < compareTo;
	 */
	public static int compareDate(String format, Date compared, long compareTo) {
		return compareDate(format, formatDate(format, compared), formatDate(format, compareTo));
	}

	/**
	 * 比较两个时间字符串的大小
	 * @param format 格式化字符串
	 * @param compared 开始时间
	 * @param compareTo 结束时间
	 * @return 1: compared > compareTo; 0: compared = compareTo; -1: compared < compareTo;
	 */
	public static int compareDate(String format, long compared, Date compareTo) {
		return compareDate(format, formatDate(format, compared), formatDate(format, compareTo));
	}

	/**
	 * 比较两个时间字符串的大小
	 * @param format 格式化字符串
	 * @param compared 开始时间
	 * @param compareTo 结束时间
	 * @return 1: compared > compareTo; 0: compared = compareTo; -1: compared < compareTo;
	 */
	public static int compareDate(String format, String compared, Date compareTo) {
		return compareDate(format, compared, formatDate(format, compareTo));
	}

	/**
	 * 比较两个时间字符串的大小
	 * @param format 格式化字符串
	 * @param compared 开始时间
	 * @param compareTo 结束时间
	 * @return 1: compared > compareTo; 0: compared = compareTo; -1: compared < compareTo;
	 */
	public static int compareDate(String format, String compared, long compareTo) {
		return compareDate(format, compared, formatDate(format, compareTo));
	}

	/**
	 * 比较两个时间字符串的大小
	 * @param format 格式化字符串
	 * @param compared 开始时间
	 * @param compareTo 结束时间
	 * @return 1: compared > compareTo; 0: compared = compareTo; -1: compared < compareTo;
	 */
	public static int compareDate(String format, long compared, String compareTo) {
		return compareDate(format, formatDate(format, compared), compareTo);
	}

	/**
	 * 比较两个时间字符串的大小
	 * @param format 格式化字符串
	 * @param compared 开始时间
	 * @param compareTo 结束时间
	 * @return 1: compared > compareTo; 0: compared = compareTo; -1: compared < compareTo;
	 */
	public static int compareDate(String format, Date compared, String compareTo) {
		return compareDate(format, formatDate(format, compared), compareTo);
	}

	/**
	 * 比较两个时间字符串的大小
	 * @param format 格式化字符串
	 * @param compared 开始时间
	 * @param compareTo 结束时间
	 * @return 1: compared > compareTo; 0: compared = compareTo; -1: compared < compareTo;
	 */
	public static int compareDate(String format, String compared, String compareTo){
		int flag = 0;
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		try {
			long result = (sdf.parse(compared).getTime() - sdf.parse(compareTo).getTime());
			if(result > 0) {
				return 1;
			}
			else if(result < 0) {
				return -1;
			}
			else {
				return 0;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return flag;
	}
}
