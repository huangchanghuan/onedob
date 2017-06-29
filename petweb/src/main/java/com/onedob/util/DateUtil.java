package com.onedob.util;

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

		// ��������
		long time = (now - past) / 1000;

		StringBuffer sb = new StringBuffer();
		if (time <= 0) {
			return sb.append("�ո�").toString();
		} else if (time > 0 && time < 60) { // 1Сʱ��
			return sb.append(time + "��ǰ").toString();
		} else if (time > 60 && time < 3600) {
			return sb.append(time / 60 + "����ǰ").toString();
		} else if (time >= 3600 && time < 3600 * 24) {
			return sb.append(time / 3600 + "Сʱǰ").toString();
		} else if (time >= 3600 * 24 && time < 3600 * 48) {
			return sb.append("����").toString();
		} else if (time >= 3600 * 48 && time < 3600 * 72) {
			return sb.append("ǰ��").toString();
		} else if (time >= 3600 * 72) {
			// return sb.append(time / 86400 + "��ǰ").toString();
			return sb.append(dateToString(date, "yyyy-MM-dd")).toString();
		}
		// return sb.append(time / 86400 + "��ǰ").toString();
		return sb.append(dateToString(date, "yyyy-MM-dd")).toString();
	}

	/**
	 * �����ַ���ת��ΪDate
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
	 * ����ת��Ϊ�ַ���
	 * 
	 * @param timeStr
	 * @param format
	 * @return
	 */
	public static String dateToString(String timeStr, String format) {
		// �ж��Ƿ��ǽ���
		Date date = strToDate(timeStr, format);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// ����ǽ���Ļ�����ȡ��xx��xx�ա����ڸ�ʽ
		if (calendar.get(Calendar.YEAR) == date.getYear()) {
			return DATE_FORMAT_TILL_DAY_CURRENT_YEAR.format(date);
		}
		return DATE_FORMAT_TILL_SECOND.format(date);
	}

	/**
	 * ��ʱ��ת��sqlʱ��;
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
	 * ��ʱ��ת��20070101������ʽ;
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
	 * ��ȡ��������,��ʽΪ:"yyyyMMdd";
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
	 * ��ʱ��Ϊ��ʱ,���ص�ǰʱ��;
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
	 * ����ָ��������ĩ;
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
	 * ��ʱ��ת����String; ��"2001/07/04  12:08:56 ";
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
	 * ��ʱ��ת����String; ��"2001/07/04  12:08:56 ";
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
	 * ��ʱ��ת����String; ��"20010704120856 ";
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
	 * ��"2007/01/01"�����ַ���ת��ʱ��Date;
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
	 * ��ʱ��ת��"yyymmdd"��ʽ;
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
	 * �·�����;
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
	 * ������;
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
	 * Сʱ����;
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
	 * ����ʱ����
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
	 * �Ƚ�start�����Ƿ�С�ڻ����end;
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
	 * ��ʱ��ת����Ϊ�ַ���;
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
	 * ���ַ���ת��ʱ��;
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
	 * �����������ڵ�����֮��
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
	 * �����������ڵ�Сʱ֮��
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
	 * �����������ڵķ���֮��
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
	 * �ж������Ƿ�����������
	 * 
	 * @param nowDate���Ƚϵ�����
	 * @param beginDate��������
	 * @param endDate��������
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
	 * ��ȡʱ�䱸ע ���� �ܶ�12��27���� ���� 12��27��
	 * 
	 * @param date
	 *            ��ʽyyyy-MM-dd
	 * @param type
	 *            1�ǻ�ȡ�ܼ�,0�ǻ�ȡ����,����,����
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
			c_month = ca.get(Calendar.MONTH) + 1;// ȡ���·�
			c_day = ca.get(Calendar.DAY_OF_MONTH);// ȡ������
			String dayMark = "";
			int day1 = DateUtil.dateDaysSub(now, showDate).intValue();
			switch (day1) {
			case 0:
				dayMark = "����";
				break;
			case 1:
				dayMark = "����";
				break;
			case 2:
				dayMark = "����";
				break;
			}
			dateMark = dayMark + c_month + "��" + c_day + "��";
		} else {
			c_month = ca.get(Calendar.MONTH) + 1;// ȡ���·�
			c_day = ca.get(Calendar.DAY_OF_MONTH);// ȡ������
			c_week = ca.get(Calendar.DAY_OF_WEEK); // ��ȡ���ܼ�
			String weekMark = "";
			switch (c_week) {
			case 1:
				weekMark = "����";
				break;
			case 2:
				weekMark = "��һ";
				break;
			case 3:
				weekMark = "�ܶ�";
				break;
			case 4:
				weekMark = "����";
				break;
			case 5:
				weekMark = "����";
				break;
			case 6:
				weekMark = "����";
				break;
			case 7:
				weekMark = "����";
				break;
			}
			dateMark = weekMark + c_month + "��" + c_day + "��";
		}
		return dateMark;
	}

	/**
	 * ��ȡ���������
	 * 
	 * @param date
	 *            ��ʽyyyy-MM-dd
	 * @return
	 */
	public static String getTomorrow(String date) {
		Date showDate = DateUtil.strToDate(date, DateUtil.DATE_FORMAT3);
		String tomorrow = DateUtil.dateToString(addDay(showDate, 1), DateUtil.DATE_FORMAT3);
		return tomorrow;
	}
	
}
