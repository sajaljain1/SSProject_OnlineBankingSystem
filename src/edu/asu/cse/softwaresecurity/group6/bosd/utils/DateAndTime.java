package edu.asu.cse.softwaresecurity.group6.bosd.utils;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.DateTimeZone;

public class DateAndTime {
	public static final String DateTimePattern = "YYYY-MM-dd HH:mm:ss";
	public static final String DOB_PATTERN = "MM-dd-YYYY";
	public static final int TIMEDIFF_SECONDS = 300;
	public static final int ACCESS_PERMISSION_TIMEPERIOD = (4*60*60); // 4 HOURS.
	
	public static DateTime getDateTimefromString(String dateTime) {
		
		DateTimeFormatter parser = DateTimeFormat.forPattern(DateTimePattern);
		String date = StringUtils.substringBefore(dateTime, ".");
		DateTime dt = parser.parseDateTime(date);
		return dt;
	}
	
	public static DateTime getDOBfromString(String dateTime) {
		
		DateTimeFormatter parser = DateTimeFormat.forPattern(DOB_PATTERN);
		DateTime dt = parser.parseDateTime(dateTime);
		return dt;
	}
	public static String getCurrentdateTime(){
		
		String time = DateTime.now().toString(DateTimePattern);
		System.out.println(time);
		return time;
	}
	
	public static boolean isDateTimeWithInValidRange(DateTime dateTime) {
		
		boolean flag = false;
		
		Seconds seconds = Seconds.secondsBetween(dateTime, DateTime.now());
		//System.out.println("Datetime: " + DateTime.now());
		//System.out.println("Difference in time: " + seconds.getSeconds());
		// Check the validity of the Sent DateTime of the token.
		if(dateTime.isBefore(DateTime.now()) && (seconds.getSeconds() <= DateAndTime.TIMEDIFF_SECONDS)) {
			flag = true;
		}
		return flag;
	}
	
public static boolean isDateTimeOutOfRange(DateTime dateTime) {
		
		boolean flag = false;
		
		Seconds seconds = Seconds.secondsBetween(dateTime, DateTime.now());
		//System.out.println("Datetime: " + DateTime.now());
		//System.out.println("Difference in time: " + seconds.getSeconds());
		// Check the validity of the Sent DateTime of the token.
		if(dateTime.isBefore(DateTime.now()) && (seconds.getSeconds() > DateAndTime.TIMEDIFF_SECONDS)) {
			flag = true;
		}
		return flag;
	}

	public static boolean isDateTimeWithInPermissionPeriod(DateTime dateTime) {
		
		boolean flag = false;
		DateTime now = DateTime.now();
		Seconds seconds = Seconds.secondsBetween(dateTime, now);
		if(dateTime.isBefore(now) && (seconds.getSeconds() <= DateAndTime.ACCESS_PERMISSION_TIMEPERIOD)) {
			flag = true;
		}
		return flag;
	}
}
