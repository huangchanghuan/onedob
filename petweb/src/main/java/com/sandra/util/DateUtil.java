package com.sandra.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	private static SimpleDateFormat DATE_FORMAT_TILL_SECOND = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat DATE_FORMAT_TILL_DAY_CURRENT_YEAR = new SimpleDateFormat("MM-dd");
	public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static String DATE_FORMAT2 = "yyyy-MM-dd HH:mm";
	public static String DATE_FORMAT3 = "yyyy-MM-dd";
	public static String DATE_FORMAT4 = "yyyyMMdd";

	public static String timeLogic(String dateStr) {

		Calendar calendar = Calendar.getInstance();
		calendar.get(Calendar.DAY_OF_MONTH);
		long now = calendar.getTimeInMillis();
		Date date = strToDate(dateStr, DATE_FORMAT);
		calendar.setTime(date);
		long past = calendar.getTimeInMillis();

		// 相差的秒数
		long time = (now - past) / 1000;

		StringBuffer sb = new StringBuffer();
		if (time <= 0) {
			return sb.append("刚刚").toString();
		} else if (time > 0 && time < 60) { // 1小时内
			return sb.append(time + "秒前").toString();
		} else if (time > 60 && time < 3600) {
			return sb.append(time / 60 + "分钟前").toString();
		} else if (time >= 3600 && time < 3600 * 24) {
			return sb.append(time / 3600 + "小时前").toString();
		} else if (time >= 3600 * 24 && time < 3600 * 48) {
			return sb.append("昨天").toString();
		} else if (time >= 3600 * 48 && time < 3600 * 72) {
			return sb.append("前天").toString();
		} else if (time >= 3600 * 72) {
			// return sb.append(time / 86400 + "天前").toString();
			return sb.append(dateToString(date, "yyyy-MM-dd")).toString();
		}
		// return sb.append(time / 86400 + "天前").toString();
		return sb.append(dateToString(date, "yyyy-MM-dd")).toString();
	}

	/**
	 * 日期字符串转换为Date
	 * 
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date strToDate(String dateStr, String format) {
		Date date = null;

		if (dateStr != null && !"".equals(dateStr)) {
			DateFormat df = new SimpleDateFormat(format);
			try {
				date = df.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}

	/**
	 * 日期转换为字符串
	 * 
	 * @param timeStr
	 * @param format
	 * @return
	 */
	public static String dateToString(String timeStr, String format) {
		// 判断是否是今年
		Date date = strToDate(timeStr, format);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 如果是今年的话，才取“xx月xx日”日期格式
		if (calendar.get(Calendar.YEAR) == date.getYear()) {
			return DATE_FORMAT_TILL_DAY_CURRENT_YEAR.format(date);
		}
		return DATE_FORMAT_TILL_SECOND.format(date);
	}

	/**
	 * 把时间转成sql时间;
	 * 
	 * @param date
	 * @return
	 */
	public static Timestamp toTimeStamp(Date date) {
		if (date == null)
			date = new Date();
		return new java.sql.Timestamp(date.getTime());
	}

	/**
	 * 把时间转成20070101这种形式;
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateFormat(Date date) {
		String result = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		result = dateFormat.format(date);
		return result;
	}

	/**
	 * 获取当天日期,格式为:"yyyyMMdd";
	 * 
	 * @return
	 */
	public static String getCurrentDateString() {
		Date date = new Date();
		String result = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		result = dateFormat.format(date);
		return result;
	}

	/**
	 * 当时间为空时,返回当前时间;
	 * 
	 * @param obj
	 * @return
	 */
	public static Date defaultDate(Date obj) {
		if (obj == null)
			return new Date();
		return obj;
	}

	/**
	 * 返回指定日期月末;
	 * 
	 * @param date
	 * @return
	 */
	public static Date lastDay(Date date) {
		// Calendar ca = Calendar.getInstance();
		Calendar tmp = Calendar.getInstance();
		tmp.setTime(date);
		tmp.set(Calendar.YEAR, tmp.get(Calendar.YEAR));
		tmp.set(Calendar.MONTH, tmp.get(Calendar.MONTH));
		tmp.set(Calendar.DAY_OF_MONTH, 1);
		tmp.set(Calendar.HOUR_OF_DAY, 0);
		tmp.set(Calendar.MINUTE, 0);
		tmp.set(Calendar.SECOND, 0);
		tmp.add(Calendar.MONTH, 1);
		tmp.add(Calendar.SECOND, -1);
		return tmp.getTime();
	}

	/**
	 * 把时间转换成String; 如"2001/07/04  12:08:56 ";
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		String result = "";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		result = simpleDateFormat.format(date);
		return result;
	}

	/**
	 * 把时间转换成String; 如"2001/07/04  12:08:56 ";
	 * 
	 * @param date
	 * @return
	 */
	public static String formatStandardDate(Date date) {
		String result = "";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		result = simpleDateFormat.format(date);
		return result;
	}

	/**
	 * 把时间转换成String; 如"20010704120856 ";
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateNum(Date date) {
		String result = "";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		result = simpleDateFormat.format(date);
		return result;
	}

	/**
	 * 把"2007/01/01"这类字符串转成时间Date;
	 * 
	 * @param str
	 * @return
	 */
	public static Date toDate(String str) {
		Date result = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		try {
			result = simpleDateFormat.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Date toYMDate(String str) {
		Date result = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
		try {
			result = simpleDateFormat.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String toYMString(Date date) {
		String result = "";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
		result = simpleDateFormat.format(date);
		return result;
	}

	/**
	 * 把时间转成"yyymmdd"形式;
	 *
	 * @return
	 */
	public static String toYMDString(Date date) {
		String result = "";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		result = simpleDateFormat.format(date);
		return result;
	}

	/**
	 * 月份增加;
	 * 
	 * @param date
	 * @param i
	 * @return
	 */
	public static Date addMonth(Date date, int i) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.MONTH, i);
		return ca.getTime();
	}

	/**
	 * 日增加;
	 * 
	 * @param date
	 * @param i
	 * @return
	 */
	public static Date addDay(Date date, int i) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.DAY_OF_MONTH, i);
		return ca.getTime();
	}

	/**
	 * 小时增加;
	 * 
	 * @param date
	 * @param i
	 * @return
	 */
	public static Date addHour(Date date, int i) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.HOUR_OF_DAY, i);
		return ca.getTime();
	}
	
	/**
	 * 设置时分秒
	 */
	public static Date setTime(Date date, Integer hours, Integer minute, Integer second) {
		Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);
        c.set(Calendar.HOUR, hours);
        return c.getTime();
	}

	/**
	 * 比较start日期是否小于或等于end;
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean compareSMEQDate(Date start, Date end) {
		if (start.before(end) || start.equals(end)) {
			return true;
		} else {
			return false;
		}
	}

	public static String toYString(Date date) {
		String result = "";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
		result = simpleDateFormat.format(date);
		return result;
	}

	public static Object toYMDDate(Object obj) {
		String result = "";
		long ll = 0;
		if (obj instanceof Timestamp) {
			ll = ((Timestamp) obj).getTime();
			Date date = new Date(ll);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
			result = simpleDateFormat.format(date);
			return result;
		} else {
			return obj;
		}
	}

	public static int getDateDay(Date date) {
		int result = 0;
		String dayTmp = "";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
		dayTmp = simpleDateFormat.format(date);
		result = Integer.parseInt(dayTmp);
		return result;
	}

	public static String dateChang(String str) {
		String result = str;
		if (result.trim().length() == 8) {
			result = result.substring(0, 4) + "/" + result.substring(4, 6) + "/" + result.substring(6);
		}
		return result;
	}

	/**
	 * 把时间转换成为字符串;
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToString(Date date, String format) {
		String result = "";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		result = simpleDateFormat.format(date);
		return result;
	}

	/**
	 * 把字符串转成时间;
	 * 
	 * @param strDate
	 * @param format
	 * @return
	 */
	public static Date stringToDate(String strDate, String format) {
		Date result = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		try {
			result = simpleDateFormat.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 计算两个日期的天数之差
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static Long dateDaysSub(Date beginDate, Date endDate) {
		long days = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		return days;
	}

	/**
	 * 计算两个日期的小时之差
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static Long dateHoursSub(Date beginDate, Date endDate) {
		long days = (endDate.getTime() - beginDate.getTime()) / (60 * 60 * 1000);
		return days;
	}

	/**
	 * 计算两个日期的分钟之差
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static Long dateMinSub(Date beginDate, Date endDate) {
		long mins = DoubleUtil.div((double) (endDate.getTime() - beginDate.getTime()), (double) 60 * 1000, 0)
				.longValue();
		return mins;
	}

	/**
	 * 判断日期是否在日期区间
	 * 
	 * @param nowDate：比较的日期
	 * @param beginDate：开区间
	 * @param endDate：闭区间
	 * @return
	 */
	public static boolean inDateRange(Date nowDate, Date beginDate, Date endDate) {
		Calendar begin = Calendar.getInstance();
		begin.setTime(beginDate);
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		Calendar now = Calendar.getInstance();
		now.setTime(nowDate);
		if (nowDate.after(beginDate) && nowDate.before(endDate)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取时间备注 例如 周二12月27或者 今天 12月27日
	 * 
	 * @param date
	 *            格式yyyy-MM-dd
	 * @param type
	 *            1是获取周几,0是获取今天,明天,后天
	 * @return
	 */
	public static String getDateMark(String date, int type) {
		int c_month = 0;
		int c_day = 0;
		int c_week = 0;
		String dateMark = "";
		Calendar ca = Calendar.getInstance();
		Date showDate = DateUtil.strToDate(date, DateUtil.DATE_FORMAT3);
		ca.setTime(showDate);
		if (type == 0) {
			Date now = DateUtil.strToDate(DateUtil.dateToString(new Date(), DateUtil.DATE_FORMAT3),
					DateUtil.DATE_FORMAT3);
			c_month = ca.get(Calendar.MONTH) + 1;// 取得月份
			c_day = ca.get(Calendar.DAY_OF_MONTH);// 取得日期
			String dayMark = "";
			int day1 = DateUtil.dateDaysSub(now, showDate).intValue();
			switch (day1) {
			case 0:
				dayMark = "今天";
				break;
			case 1:
				dayMark = "明天";
				break;
			case 2:
				dayMark = "后天";
				break;
			}
			dateMark = dayMark + c_month + "月" + c_day + "日";
		} else {
			c_month = ca.get(Calendar.MONTH) + 1;// 取得月份
			c_day = ca.get(Calendar.DAY_OF_MONTH);// 取得日期
			c_week = ca.get(Calendar.DAY_OF_WEEK); // 获取在周几
			String weekMark = "";
			switch (c_week) {
			case 1:
				weekMark = "周日";
				break;
			case 2:
				weekMark = "周一";
				break;
			case 3:
				weekMark = "周二";
				break;
			case 4:
				weekMark = "周三";
				break;
			case 5:
				weekMark = "周四";
				break;
			case 6:
				weekMark = "周五";
				break;
			case 7:
				weekMark = "周六";
				break;
			}
			dateMark = weekMark + c_month + "月" + c_day + "日";
		}
		return dateMark;
	}

	/**
	 * 获取明天的日期
	 * 
	 * @param date
	 *            格式yyyy-MM-dd
	 * @return
	 */
	public static String getTomorrow(String date) {
		Date showDate = DateUtil.strToDate(date, DateUtil.DATE_FORMAT3);
		String tomorrow = DateUtil.dateToString(addDay(showDate, 1), DateUtil.DATE_FORMAT3);
		return tomorrow;
	}
	
}
