package com.wsct.util.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil2 {
	/**
	 * 日期转字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date, String formatStr) {
		String strTime = "";
		try {
			DateFormat df = new SimpleDateFormat(formatStr);
			strTime = df.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strTime;
	}
	/**
	 * 字符串转换到时间格式
	 * 
	 * @param dateStr
	 *            需要转换的字符串
	 * @param formatStr
	 *            需要格式的目标字符串 举例 yyyy-MM-dd
	 * @return Date 返回转换后的时间
	 * @throws ParseException
	 *             转换异常
	 */
	public static Date stringToDate(String dateStr, String formatStr) {
		DateFormat sdf = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 判断日期是null或空
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isDateNotNullOrEmpty(Date date) {
		String str = DateUtil2.dateToString(date, "yyyy-MM-dd HH:mm:ss");
		if (str != null && !str.equals(""))
			return true;
		else
			return false;
	}
	/**
	 * 取得系统日期
	 * 
	 * @return
	 */
	public static String getSystemDate() {
		String strDate = "";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		strDate = df.format(new Date());
		return strDate;
	}
	/**
	 * 两时间求差
	 * 
	 * @return
	 */
	public static boolean datePK(Date date) {
		//取系统时间
		Date SysDate = new Date();
		//时间
		long dateLong=SysDate.getTime()-date.getTime();
		long day=dateLong/(24*60*60*1000);
		if(day<=1){
			return true;
		}else {
			return false;
		}
		
	}
	/**
	 * 系统时间加减
	 * 
	 * @param date 时间
	 * @param dayNum 加/减天数  +/-天数
	 * @return
	 */
	public static String getOpeDate(String date, int dayNum) {

		Date dt = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			dt = df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(dt);
		gc.add(5, dayNum);
		return String.valueOf(df.format(gc.getTime()));
	}



	/* 取得当月最后一天 */
	public static String getLastDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));// 年
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH));// 月，因为Calendar里的月是从0开始，所以要减1
		cal.set(Calendar.DATE, 1);// 日，设为一号
		cal.add(Calendar.MONTH, 1);// 月份加一，得到下个月的一号
		cal.add(Calendar.DATE, -1);// 下一个月减一为本月最后一天
		String df = new java.text.SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		return df;// 获得月末是几号
	}

	/* 取得当月第一天 */
	public static String getFristDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));// 年
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH));// 月，因为Calendar里的月是从0开始，所以要减1
		cal.set(Calendar.DATE, 1);// 日，设为一号
		String df = new java.text.SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		return df;// 获得月初是几号
	}


	/**
	 * 功能描述：util.date转成Sql.date
	 * 
	 * @return
	 */
	public static java.sql.Date getSystemSqltime() {
		java.util.Date utilDate = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sDate = df.format(utilDate);
		utilDate = stringToDate(sDate, "yyyy-MM-dd HH:mm:ss");
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); // 转型成java.sql.Date类型
		return sqlDate;
	}


	/**
	 * 获取当季度
	 * @return
	 */
	public static String getCurQuarter() {
		String month = dateToString(new Date(), "MM");
		String quarter = "";
		if (month.equals("01") | month.equals("02") || month.equals("03"))
			quarter = "Q1";
		else if (month.equals("04") | month.equals("05") || month.equals("06"))
			quarter = "Q2";
		else if (month.equals("07") | month.equals("08") || month.equals("09"))
			quarter = "Q3";
		else if (month.equals("10") | month.equals("11") || month.equals("12"))
			quarter = "Q4";
		return dateToString(new Date(), "yyyy") + quarter;
	}

	/**
	 * *
	 * 签到时间对比
	 * @param LogDate
	 */
	public static int checkSignDate(Date LogDate) {
		//当前时间 取年月日
		Calendar cal = Calendar.getInstance(); 
		int year = cal.get(Calendar.YEAR); 
		int month = cal.get(Calendar.MONTH) + 1; 
		int day = cal.get(Calendar.DAY_OF_MONTH); 
		//历史时间 取年月日
		cal.setTime(LogDate);
		int year1 = cal.get(Calendar.YEAR); 
		int month1 = cal.get(Calendar.MONTH) + 1; 
		int day1 = cal.get(Calendar.DAY_OF_MONTH); 
		if(year == year1 && month == month1 && day == day1){
			return 0;
		}
		return 1;
	}
}
