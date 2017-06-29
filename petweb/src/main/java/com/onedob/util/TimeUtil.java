package com.onedob.util;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil
{
	public final static int PINPOINT_TO_YEAR = 0;
	public final static int PINPOINT_TO_MONTH = 1;
	public final static int PINPOINT_TO_DATE = 2;
	public final static int PINPOINT_TO_HOUR = 3;
	public final static int PINPOINT_TO_MINUTE = 4;
	public final static int PINPOINT_TO_SECOND = 5;
	
	public static void addTime(Date date, int field, int value)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, value);
		date.setTime(calendar.getTimeInMillis());
	}
	
	public static void initTime(Date date, int field)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		switch(field)
		{
			case Calendar.DAY_OF_MONTH :
			{
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				break;
			}
			case Calendar.HOUR_OF_DAY :
			{
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				break;
			}
			case Calendar.MINUTE :
			{
				calendar.set(Calendar.MINUTE, 0);
				break;
			}
			case Calendar.SECOND :
			{
				calendar.set(Calendar.SECOND, 0);
				break;
			}
		}
		date.setTime(calendar.getTimeInMillis());
	}
	
	public static void trunc(Date date, int field)
	{
		switch(field)
		{
			case Calendar.MONTH :
			{
				initTime(date, Calendar.DAY_OF_MONTH);
				initTime(date, Calendar.HOUR_OF_DAY);
				initTime(date, Calendar.MINUTE);
				initTime(date, Calendar.SECOND);
				break;
			}
			case Calendar.DAY_OF_MONTH :
			{
				initTime(date, Calendar.HOUR_OF_DAY);
				initTime(date, Calendar.MINUTE);
				initTime(date, Calendar.SECOND);
				break;
			}
			case Calendar.HOUR :
			{
				initTime(date, Calendar.MINUTE);
				initTime(date, Calendar.SECOND);
				break;
			}
		}
	}
	
	public static String getDate(long millis, int pinpoint, String split)
	{
		String date = "";
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		String year = ""+calendar.get(Calendar.YEAR);
		String month = calendar.get(Calendar.MONTH)+1+"";
		String day = ""+calendar.get(Calendar.DAY_OF_MONTH);
		String hour = ""+calendar.get(Calendar.HOUR_OF_DAY);
		String minute = ""+calendar.get(Calendar.MINUTE);
		String second = ""+calendar.get(Calendar.SECOND);
		if(month.length()==1)
		{
			month = "0"+month;
		}
		if(day.length()==1)
		{
			day = "0"+day;
		}
		if(hour.length()==1)
		{
			hour = "0"+hour;
		}
		if(minute.length()==1)
		{
			minute = "0"+minute;
		}
		if(second.length()==1)
		{
			second = "0"+second;
		}
		if(pinpoint==TimeUtil.PINPOINT_TO_YEAR)
		{
			date = year;
		}
		else if(pinpoint==TimeUtil.PINPOINT_TO_MONTH)
		{
			date = year+split+month;
		}
		else if(pinpoint==TimeUtil.PINPOINT_TO_DATE)
		{
			date = year+split+month+split+day;
		}
		else if(pinpoint==TimeUtil.PINPOINT_TO_HOUR)
		{
			date = year+split+month+split+day+split+hour;
		}
		else if(pinpoint==TimeUtil.PINPOINT_TO_MINUTE)
		{
			date = year+split+month+split+day+split+hour+split+minute;
		}
		else if(pinpoint==TimeUtil.PINPOINT_TO_SECOND)
		{
			date = year+split+month+split+day+split+hour+split+minute+split+second;
		}
		return date;
	}
}
