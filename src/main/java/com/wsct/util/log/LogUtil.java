package com.wsct.util.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {
	private static Logger sysLog = LoggerFactory.getLogger("sysLog");

	/**
	 * 
	 * 功能描述:记录系统日志
	 * 
	 * @param str
	 *            void
	 * @version 1.0.0
	 * @author 孔垂云
	 */
	public static void infoSys(String str) {
		sysLog.info(str);
	}

	public static void setSysLog(Logger sysLog) {
		LogUtil.sysLog = sysLog;
	}

	/**
	 * 记录exception
	 * @param e
	 */
	public static void error(Exception e) {
		sysLog.error(e.getMessage());
	}

}
