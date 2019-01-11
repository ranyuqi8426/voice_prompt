package com.wsct.util.page;

public class PageUtil {

	/**
	 * 生成mysql分页查询语句
	 * @param sql
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public static String createMysqlPageSql(String sql, int pageIndex, int pageSize) {
		return sql += " limit " + (pageIndex - 1) * pageSize + "," + pageSize;
	}
	
	/**
	 * 生成oracle分页查询语句
	 * @param sql
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public static String createOraclePageSQL(String sql, int pageIndex, int pageSize) {
		return "SELECT * FROM ( SELECT A.*, ROWNUM RN FROM (" + sql + ") A WHERE ROWNUM <=" + pageIndex * pageSize
				+ " ) WHERE RN > " + (pageIndex - 1) * pageSize;
	}
}
