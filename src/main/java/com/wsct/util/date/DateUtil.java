package com.wsct.util.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.aspectj.weaver.ast.Var;

public class DateUtil {
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
			if(date!=null){
				strTime = df.format(date);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strTime;
	}

	/**
	 * 判断日期是null或空
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isDateNotNullOrEmpty(Date date) {
		String str = DateUtil.dateToString(date, "yyyy-MM-dd HH:mm:ss");
		if (str != null && !str.equals(""))
			return true;
		else
			return false;
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
	 * 取得系统时间
	 * 
	 * @return
	 */
	public static String getSystemTime() {
		String strTime = "";
		java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		strTime = df.format(new Date());
		return strTime;
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
		Date SysDate = stringToDate(getSystemDate(),"yyyy-MM-dd");
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
	 * @param date
	 * @param dayNum
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
		// gc.set(gc.YEAR,gc.get(gc.MONTH),gc.get(gc.DATE));
		return String.valueOf(df.format(gc.getTime()));
	}

	/**
	 * 系统时间加减yyyyMMdd
	 * 
	 * @param date
	 * @param dayNum
	 * @return
	 */
	public static String getOpeDate2(String date, int dayNum) {

		Date dt = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		try {
			dt = df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(dt);
		gc.add(5, dayNum);
		// gc.set(gc.YEAR,gc.get(gc.MONTH),gc.get(gc.DATE));
		return String.valueOf(df.format(gc.getTime()));
	}

	/**
	 * 取得两个日期的时间差
	 */
	public static long getDateDifferent(String date1, String date2) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		ParsePosition pos1 = new ParsePosition(0);
		java.util.Date dt1 = formatter.parse(date1, pos);
		java.util.Date dt2 = formatter.parse(date2, pos1);
		long l = (dt2.getTime() - dt1.getTime()) / (3600 * 24 * 1000);
		return l;
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
	* 取得系统时间
	* 
	* @return
	*/
	public static String getShortSystemTime() {
		String strTime = "";
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		strTime = df.format(new java.util.Date());
		return strTime;
	}

	/**
	 * 取得系统短日期，yymmdd
	 * 
	 * @return
	 */
	public static String getShortSystemDate() {
		String strTime = "";
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		strTime = df.format(new java.util.Date());
		return strTime;
	}

	/**
	 * 取得系统短日期，yyyyMMdd
	 * 
	 * @return
	 */
	public static String getSystemDate2() {
		String strTime = "";
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		strTime = df.format(new java.util.Date());
		return strTime;
	}

	/**
	 * 功能描述：取得sql.date格式系统时间
	 * 
	 * @param jcJbxxSearchVO
	 * @author 乐振雷
	 * @date 2016-12-16
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
	 * 获取当月
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static String getCurMonth() {
		return dateToString(new Date(), "yyyyMM");
	}

	/**
	 * 获取当年
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static String getCurYear() {
		return dateToString(new Date(), "yyyy");
	}

	/**
	 * 获取当季
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
	 * 获取新的时间字符串
	 * @return String
	 */
	public static String changeDateString(String dateStr) {
		if (dateStr.length() == 4) {
			return dateStr + "年";
		} else {
			String year = dateStr.substring(0, 4);
			String month = dateStr.substring(4);
			return year + "年" + month + "月";
		}
	}
	/**
	 * *
	 * 签到时间对比
	 * @param LogDate
	 * @return 0 已签到
	 * 1 未签到
	 */
	public static int checkSignDate(Date LogDate) {
		Calendar cal = Calendar.getInstance(); 
		int year = cal.get(Calendar.YEAR); 
		int month = cal.get(Calendar.MONTH) + 1; 
		int day = cal.get(Calendar.DAY_OF_MONTH); 
		cal.setTime(LogDate);
		int year1 = cal.get(Calendar.YEAR); 
		int month1 = cal.get(Calendar.MONTH) + 1; 
		int day1 = cal.get(Calendar.DAY_OF_MONTH); 
		if(year == year1 && month == month1 && day == day1){
			return 0;
		}
		return 1;
	}
	/**
	 * 根据年 月 生成 上个月时间
	 */
	public static Date yearMonthFormatTop(int y,int m) {
		String month = "";
		if(m==1){
			month = "12";
			y= y-1;
		}else if (m>10) {
			month = String.valueOf(m-1);
		}else {
			month = "0" + (m-1);
			
		}
		String dateStr = y + "-"+ month +"-01";
		return stringToDate(dateStr,"yyyy-MM-dd");
	}
	/**
	 * 根据年 月 生成 下个月时间
	 */
	public static Date yearMonthFormatNext(int y,int m) {
		String month = "";
		if(m==12){
			month = "1";
			y = y+1;
		}else if (m>9) {
			month = String.valueOf(m+1);
		}else {
			month = "0" + (m+1);
			
		}
		String dateStr = y + "-"+ month +"-01";
		return stringToDate(dateStr,"yyyy-MM-dd");
	}
	public static void main(String[] args) {
		System.out.println(dateToString(yearMonthFormatNext(2017,1),"yyyy-MM-dd"));
	}
}
