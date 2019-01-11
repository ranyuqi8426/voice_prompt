package com.wsct.sys.service;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.wsct.sys.dao.SysLogDao;
import com.wsct.sys.model.SysFunction;
import com.wsct.sys.model.SysLog;
import com.wsct.sys.vo.SysLogSearchVO;
import com.wsct.util.date.DateUtil;
import com.wsct.util.excel.CSVUtil;

/**
 * 系统日志service
 * @author chykong
 *
 */
@Service
public class SysLogService {
	@Autowired
	private SysLogDao sysLogDao;
	@Autowired
	private SysFunctionService sysFunctionService;

	/**
	 * 操作时记录日志
	 */
	@Async
	public void addLog(int user_id, String url, String parameters, String opera_ip) {
		HashMap<String, SysFunction> hashMap = sysFunctionService.getAllFunction();// 获取所有function

		SysLog sysLog = new SysLog();
		sysLog.setUser_id(user_id);
		sysLog.setOpera_url(url);
		if (parameters.length() > 500)
			parameters = parameters.substring(0, 500);
		sysLog.setOpera_params(parameters);
		sysLog.setOpera_date(new Date());
		sysLog.setOpera_ip(opera_ip);

		SysFunction sysFunction = hashMap.get(url);
		if (sysFunction != null) {
			sysLog.setModule_name(sysFunction.getModule_name());
			sysLog.setOpera_name(sysFunction.getName());
			sysLog.setOpera_type(sysFunction.getType());
		}
		sysLogDao.add(sysLog);
	}

	/**
	 * 日志列表
	 * 
	 * @param sysUserSearchVO
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<SysLog> list(SysLogSearchVO sysLogSearchVO, int pageIndex, int pageSize) {
		List<SysLog> list = sysLogDao.list(sysLogSearchVO, pageIndex, pageSize);
		return list;
	}

	/**
	 * 日志列表
	 * 
	 * @param sysUserSearchVO
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<SysLog> list(SysLogSearchVO sysLogSearchVO) {
		List<SysLog> list = sysLogDao.list(sysLogSearchVO);
		return list;
	}

	/**
	 * 日志列表总数
	 * 
	 * @param sysUserSearchVO
	 * @return
	 */
	public int listCount(SysLogSearchVO sysLogSearchVO) {
		return sysLogDao.listCount(sysLogSearchVO);
	}

	/**
	 * 下载日志
	 * @param sysLogSearchVO
	 */
	public void exportLog(String path, SysLogSearchVO sysLogSearchVO, HttpServletResponse response) {
		List<SysLog> list = sysLogDao.list(sysLogSearchVO);
		String[][] data = new String[list.size() + 1][9];
		data[0][0] = "序号";
		data[0][1] = "用户账号";
		data[0][2] = "用户姓名";
		data[0][3] = "操作时间";
		data[0][4] = "IP";
		data[0][5] = "模块名称";
		data[0][6] = "操作名称";
		data[0][7] = "操作url";
		data[0][8] = "参数";

		for (int i = 0; i < list.size(); i++) {
			SysLog sysLog = list.get(i);
			data[i + 1][0] = String.valueOf(i + 1);
			data[i + 1][1] = sysLog.getUsername();
			data[i + 1][2] = sysLog.getRealname();
			data[i + 1][3] = DateUtil.dateToString(sysLog.getOpera_date(), "yyyy-MM-dd HH:mm:ss");
			data[i + 1][4] = sysLog.getOpera_ip();
			data[i + 1][5] = sysLog.getModule_name();
			data[i + 1][6] = sysLog.getOpera_name();
			data[i + 1][7] = sysLog.getOpera_url();
			data[i + 1][8] = sysLog.getOpera_params();
		}

		String fileName = "导出操作日志";
		File file = CSVUtil.createCSVFile(path, fileName, data);
		CSVUtil.exportFile(response, path + "/" + file.getName(), fileName + ".CSV");
		CSVUtil.deleteFile(path, file.getName());
	}
}
