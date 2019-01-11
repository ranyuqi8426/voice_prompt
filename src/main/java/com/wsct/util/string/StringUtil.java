package com.wsct.util.string;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户字符串操作
 * 
 * 
 */
public class StringUtil {

	public static String getAdm(String code) {
		if (code.equals("B"))
			return "哈尔滨铁路局";
		else if (code.equals("T"))
			return "沈阳铁路局";
		else if (code.equals("P"))
			return "北京铁路局";
		else if (code.equals("V"))
			return "太原铁路局";
		else if (code.equals("C"))
			return "呼和浩特铁路局";
		else if (code.equals("F"))
			return "郑州铁路局";
		else if (code.equals("N"))
			return "武汉铁路局";
		else if (code.equals("Y"))
			return "西安铁路局";
		else if (code.equals("K"))
			return "济南铁路局";
		else if (code.equals("H"))
			return "上海铁路局";
		else if (code.equals("G"))
			return "南昌铁路局";
		else if (code.equals("Q"))
			return "广铁集团";
		else if (code.equals("Z"))
			return "南宁铁路局";
		else if (code.equals("W"))
			return "成都铁路局";
		else if (code.equals("M"))
			return "昆明铁路局";
		else if (code.equals("J"))
			return "兰州铁路局";
		else if (code.equals("R"))
			return "乌鲁木齐铁路局";
		else if (code.equals("O"))
			return "青藏铁路公司";
		else
			return "其他";

	}

	/**
	 * 根据报表类型，报表日期取得要显示的报表日期
	 * 
	 * @param repType
	 * @param repDate
	 * @return
	 */
	public static String getOperaDate(String repType, String repDate) {
		if (repType.equals("D")) {
			return repDate.substring(0, 4) + "年" + repDate.substring(4, 6) + "月" + repDate.substring(6, 8) + "日";
		} else if (repType.equals("T")) {
			String xun = "";
			String day = repDate.substring(6, 8);
			if (Integer.parseInt(day) <= 10)
				xun = "上旬";
			else if (Integer.parseInt(day) <= 20)
				xun = "中旬";
			else if (Integer.parseInt(day) <= 31)
				xun = "下旬";
			return repDate.substring(0, 4) + "年" + repDate.substring(4, 6) + "月" + xun;
		} else if (repType.equals("M")) {
			return repDate.substring(0, 4) + "年" + repDate.substring(4, 6) + "月";
		} else if (repType.equals("Q")) {
			String month = repDate.substring(4, 6);
			String quarter = "";
			if (month.equals("01") || month.equals("02") || month.equals("03"))
				quarter = "一季度";
			else if (month.equals("04") || month.equals("05") || month.equals("06"))
				quarter = "二季度";
			else if (month.equals("07") || month.equals("08") || month.equals("09"))
				quarter = "三季度";
			else if (month.equals("10") || month.equals("11") || month.equals("12"))
				quarter = "四季度";
			return repDate.substring(0, 4) + "年" + quarter;
		} else if (repType.equals("Y")) {
			return repDate.substring(0, 4) + "年";
		} else
			return "";
	}

	/**
	 * 处理报表类型在report_description中的字段
	 * 
	 * @param kind
	 * @return
	 */
	public static String dealReportKind(String kind) {
		if (kind.equalsIgnoreCase("D"))
			return "isD=1";
		else if (kind.equalsIgnoreCase("T"))
			return "isT=1";
		else if (kind.equalsIgnoreCase("M"))
			return "isM=1";
		else
			return "";
	}

	/**
	 * 根据字段序号取得数据表对应的字段名称
	 * 
	 * @param id
	 * @return
	 */
	public static String dealFieldStr(int id) {
		if (id < 10)
			return "data_00" + id;
		else if (id < 100)
			return "data_0" + id;
		else
			return "data_" + id;
	}

	public static String dealFieldStr(String report_name, int id) {
		if (id < 10)
			return report_name + "_00" + id;
		else if (id < 100)
			return report_name + "_0" + id;
		else
			return report_name + "_" + id;
	}

	/**
	 * 把前台传过来的含中文的url字符串转换成标准中文，比如%25E5%258C%2597%25E4%25BA%25AC转换成北京
	 */
	public static String decodeUrl(String url) {
		String str = "";
		try {
			str = URLDecoder.decode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 取字符除最后一位的子串，比如 aaa,bbb, 返回aaa,bbb
	 * 
	 * @param str
	 * @return
	 */
	public static String subTract(String str) {
		if (str.length() == 0)
			return "";
		else
			return str.substring(0, str.length() - 1);
	}

	/**
	 * 判断字符串是null或空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmpty(String str) {
		if (str == null || str.equals(""))
			return true;
		else
			return false;
	}

	/**
	 * 把字符串里面的\r\n替换掉，json处理
	 * 
	 * @param str
	 * @return
	 */
	public static String dealJsonFormat(String str) {
		str = str.replace("\r", "");
		str = str.replace("\n", "");
		return str;
	}

	/**
	 * 把字符串里面的"-"和空格" "替换掉，并截取年月日成八位数日期字符串（18点日期格式），日期处理
	 * 
	 * @param str
	 * @return
	 */
	public static String dealDateFormat(String str) {
		str = str.replace("-", "");
		str = str.replace(" ", "");
		str = str.substring(0, 8);
		return str;
	}

	public static boolean checkFileExist(String path) {
		File file = new File(path);
		if (file.isFile() && file.exists()) {
			return true;
		} else
			return false;
	}

	/**
	 * 判断字符串是null或空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotNullOrEmpty(String str) {
		if (str != null && !str.equals(""))
			return true;
		else
			return false;
	}

	/**
	 * 如果为null不trim
	 * 
	 * @param str
	 * @return
	 */
	public static String isNullNoTrim(String str) {
		if (!(str == null)) {

			return str.trim();
		} else {
			return str;
		}
	}

	/**
	 * 格式化小数
	 * 
	 * @param val
	 * @param point小数位
	 * @return
	 */
	public static String formatDouble(String val, int point) {
		String str = "";
		DecimalFormat nf = new DecimalFormat();
		nf.setMaximumFractionDigits(point);
		str = nf.format(Double.parseDouble(val));
		return str.replace(",", "");
	}

	/**
	 * 格式化小数
	 * 
	 * @param val
	 * @param point小数位
	 * @return
	 */
	public static double formatDouble(double val, int point) {
		String str = "";
		DecimalFormat nf = new DecimalFormat();
		nf.setMaximumFractionDigits(point);
		str = nf.format(val);
		return Double.parseDouble(str.replace(",", ""));
	}

	/**
	 * 格式化两位小数
	 * 
	 * @param val
	 * @param point
	 * @return
	 */
	public static String formatDouble(String val) {
		String str = "";
		DecimalFormat nf = new DecimalFormat();
		nf.setMaximumFractionDigits(2);
		str = nf.format(Double.parseDouble(val));
		return str.replace(",", "");
	}

	/**
	 * 格式化两位小数
	 * 
	 * @param val
	 * @param point
	 * @return
	 */
	public static double formatDouble(double val) {
		String str = "";
		DecimalFormat nf = new DecimalFormat();
		nf.setMaximumFractionDigits(2);
		str = nf.format(val);
		return Double.parseDouble(str.replace(",", ""));
	}

	/**
	 * 格式化金钱
	 * 
	 * @param val
	 * @param point
	 * @return
	 */
	public static String formatAmount(double val) {
		NumberFormat nf = new DecimalFormat("#,###.##");
		String str = nf.format(val);
		return str;
	}

	/**
	 * 判断是否是ajax请求
	 * @param request
	 * @return
	 */
	public static boolean checkAjaxRequest(HttpServletRequest request) {
		String requestType = request.getHeader("X-Requested-With");
		if (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) {
			return true;
		} else
			return false;
	}

	/**
	 * 把数字补零，比如传入T001，需要处理成T002，如果超出最大长度，返回"";
	 * @param 代码前缀
	 * @param code已经存在的最大代码
	 * @param 数字长度
	 * @return
	 */
	public static String addZero(String pre, String code, int numLength) {
		String str = "";
		if (StringUtil.isNullOrEmpty(code)) {
			str = pre;
			for (int i = 0; i < numLength - 1; i++) {
				str += "0";
			}
			str += "1";
		} else {
			str = pre;
			int num = Integer.parseInt(code.substring(pre.length(), code.length())) + 1;
			for (int i = 0; i < numLength - String.valueOf(num).length(); i++) {
				str += "0";
			}
			str += num;
		}
		if (str.length() > pre.length() + numLength)
			return "";
		else
			return str;
	}
	
	/**
	 * 把数字补零，比如传入1，需要处理成01，如果超出最大长度，返回"";
	 * @param pre  代码前缀
	 * @param code 数字
	 * @param len  数字长度
	 * @author 乐振雷
	 * @date 2017-01-08
	 * @return
	 */
	public static String codeAddZero(String pre,Integer code, int len){
		code++;
	    String strCode = code.toString();
	    while (strCode.length() < len) {
	    	strCode = "0" + strCode;
	    }
	    return pre+strCode;
	}

	/*	public static String addZeroByType(String pre, String code) {
			String str = "";
			if (StringUtil.isNullOrEmpty(code)) {
				str = pre;
				for (int i = 0; i < numLength - 1; i++) {
					str += "0";
				}
				str += "1";
			} else {
				str = pre;
				int num = Integer.parseInt(code.substring(pre.length(), code.length())) + 1;
				int numLength1 = code.substring(pre.length(),code.length()-String.valueOf(num).length()).length();
				for (int i = 0; i < numLength; i++) {
					str += "0";
				}
				str += num;
			}
			if (str.length() > pre.length() + numLength)
				return "";
			else
				return str;
		}*/
	/**
	 * 获取ip地址
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request) {
		String szClientIP = request.getHeader("x-forwarded-for");
		if (szClientIP == null || szClientIP.length() == 0 || "unknown".equalsIgnoreCase(szClientIP))
			szClientIP = request.getHeader("Proxy-Client-IP");
		if (szClientIP == null || szClientIP.length() == 0 || "unknown".equalsIgnoreCase(szClientIP))
			szClientIP = request.getHeader("WL-Proxy-Client-IP");
		if (szClientIP == null || szClientIP.length() == 0 || "unknown".equalsIgnoreCase(szClientIP))
			szClientIP = request.getRemoteAddr();
		return szClientIP;
	}

	/**
	 * 获取物料类型的数字编号，比如T001，就是001
	 * @param protype
	 * @return
	 */
	public static String getProtypeNum(String protype) {
		if (protype != null && protype.length() >= 4)
			return protype.substring(1, 4);
		else
			return "";
	}

	public static String text2Html(String str) {
		if (str == null) {
			return "";
		} else if (str.length() == 0) {
			return "";
		}
		str = str.replaceAll("\n", "<br />");
		str = str.replaceAll("\r", "<br />");
		return str;
	}

	/**
	 * 取得系统日期
	 * 
	 * @return
	 */
	public static String getSystemDate() {
		String strDate = "";
		try {
			java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
			strDate = df.format(new java.util.Date());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return strDate;
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

	public static String getOpeDate(String date, String format, int dayNum) {
		Date dt = null;
		SimpleDateFormat df = new SimpleDateFormat(format);
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
	 * 获取某个时间段的所有天数集合(包含起始日期与终止日期)
	 * 
	 * @param starDate
	 * @param endDate
	 * @return
	 */
	public static List<String> getDayList(String starDate, String endDate) {
		String dateFormat = "yyyyMMdd";
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		List<String> dayList = new ArrayList<String>();
		if (starDate.equals(endDate)) {
			dayList.add(starDate);
		} else if (starDate.compareTo(endDate) < 0) {
			while (starDate.compareTo(endDate) <= 0) {
				dayList.add(starDate);
				try {
					long l = stringToDate(starDate, "yyyyMMdd").getTime();
					starDate = format.format(l + 3600 * 24 * 1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			dayList.add(endDate);
		}
		Collections.reverse(dayList);// 倒序
		return dayList;
	}

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
	 * 处理长日期，去掉年，比如yyyyMMdd，转成MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String getLongDate2(String date) {
		return date.substring(4, 6) + "-" + date.substring(6, 8);
	}

	/** 转换提交的中文参数 ，只有当url中含有中文时才需要转换，ajax提交的param带中文不需要转换
	 * 
	 * @param value
	 * @return */
	public static String decodeParam(String value) {
		String str = "";
		if (value == null)
			return "";
		else {
			try {
				str = java.net.URLDecoder.decode(value, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return str;
		}
	}

	/**
	 * 取得系统时间
	 * 
	 * @return
	 */
	public static String getSystemTime() {
		String strTime = "";
		try {
			java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			strTime = df.format(new java.util.Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strTime;
	}

	/**
	 * 去掉小数点后面的0，导入数据的时候用到
	 * @param val
	 * @return
	 */
	public static String formatNumber(String val) {
		val = val.replaceAll("0+?$", "");// 去掉后面无用的零
		val = val.replaceAll("[.]$", "");// 如小数点后面全是零则去掉小数点
		return val;
	}

	/**
	 * 首字母变大写其余变小写
	 * @param val
	 * @return
	 */
	public static String formatFirstLetter(String val) {
		if (val.length() == 1)
			return val.toUpperCase();
		else
			return val.substring(0, 1).toUpperCase() + val.substring(1, val.length()).toLowerCase();
	}
	 /**
     * 根据从request中获取body的参数值
     *
     * @param br bufferreader
     * @return 字符串
     */
    public static String getBodyString(BufferedReader br) {
        String inputLine;
        String str = "";
        try {
            while ((inputLine = br.readLine()) != null) {
                str += inputLine;
            }
            br.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        return str;
    }

    /**
     * 取得header的所有属性
     *
     * @param headers 请求的headers
     * @param request request
     * @return 把header拼成字符串
     */
    public static String getHeaderValue(Enumeration<String> headers, HttpServletRequest request) {
        String str = "";
        while (headers.hasMoreElements()) {
            String header = headers.nextElement();
            str += header + "=" + request.getHeader(header) + "&";
        }
        return str;
    }
}
