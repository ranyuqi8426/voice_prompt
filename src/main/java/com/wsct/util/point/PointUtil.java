package com.wsct.util.point;

import com.alibaba.druid.sql.ast.statement.SQLIfStatement.Else;

/**
 * 积分计算
 * 
 * @author ryq
 * 
 */
public class PointUtil {
	/**
	 * 学习积分
	 * @param time 时间
	 * @param type 类型
	 * @return
	 */
	public static int getPointNum(String time,String type) {
		String[] times = time.split(":");
		int TimeNum = 0;
		if(times.length>=2&&times[1] != null&&!times[1].equals("")){
			TimeNum = Integer.valueOf(times[1])*3600 + TimeNum;
		}
		if(times.length>=3&&times[2] != null&&!times[2].equals("")){
			TimeNum = Integer.valueOf(times[2])*60 + TimeNum;
		}
		if(times.length>=4&&times[3] != null&&!times[3].equals("")){
			TimeNum = Integer.valueOf(times[3]) + TimeNum;
		}
		int PointNum = 0;
		if (type != null&&type.equals("1")) {
			PointNum = TimeNum*100+PointNum;
		}else if (type != null&&type.equals("2")) {
			PointNum = TimeNum*100+PointNum;
		}else if (type != null&&type.equals("3")) {
			PointNum = TimeNum*100+PointNum;
		}
		return PointNum;
	}
	/**
	 * 签到
	 * @param type
	 * @return
	 */
	public static int getPointNum(String type) {
		
		int PointNum = 0;
		if (type != null&&type.equals("1")) {
			PointNum = 10;
		}else if (type != null&&type.equals("2")) {
			PointNum = 30;
		}
		return PointNum;
	}
}
