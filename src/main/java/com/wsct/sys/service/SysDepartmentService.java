package com.wsct.sys.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wsct.sys.dao.SysDepartmentDao;
import com.wsct.sys.model.SysDepartment;
import com.wsct.util.date.DateUtil;
import com.wsct.util.string.StringUtil;
import com.wsct.util.web.WebUtil;

@Service
public class SysDepartmentService {

	@Autowired
	private SysDepartmentDao sysDepartmentDao;

	/**
	 * 新增
	 * @param sysDepartment
	 * @return
	 */
	public synchronized int add(SysDepartment sysDepartment) {
		String code = "";
		if (StringUtil.isNullOrEmpty(code))
			return -1;
		else {
			sysDepartment.setCode(code);
			sysDepartment.setCreate_date(new Date());
			return sysDepartmentDao.add(sysDepartment);
		}

	}

	/**
	 * 修改
	 * @param sysDepartment
	 * @return
	 */
	public int update(SysDepartment sysDepartment) {
		return sysDepartmentDao.update(sysDepartment);
	}

	public int delete(String code) {
		if (sysDepartmentDao.getChildCount(code) > 0)
			return 2;
		else if (sysDepartmentDao.getUserCount(code) > 0)
			return 3;
		else
			return sysDepartmentDao.delete(code);
	}

	/**
	 * 取出团队列表
	 * @return
	 */
	public List<SysDepartment> list() {
		return sysDepartmentDao.list();
	}

	public String getTreeGridJson() {
		List<SysDepartment> list = list();
		String json = "";
		json = createTreeGridJson(list, "DEP000");
		return "[" + json + "]";
	}

	/**
	 * 取得团队信息 treegrid JSON
	 * @param list
	 * @param parent_code
	 * @return
	 */
	private String createTreeGridJson(List<SysDepartment> list, String parent_code) {
		String str = "";
		for (SysDepartment sysDepartment : list) {
			if (WebUtil.getSafeStr(sysDepartment.getParent_code()).equals(parent_code)) {
				str += "{\"code\":\"" + sysDepartment.getCode() + "\",\"name\":\"" + sysDepartment.getName() + "\",\"create_personname\":\""
						+ sysDepartment.getCreate_personname() + "\",\"parent_code\":\"" + sysDepartment.getParent_code() + "\",\"create_date\":\""
						+ DateUtil.dateToString(sysDepartment.getCreate_date(), "yyyy-MM-dd HH:mm") + "\"";
				if (sysDepartment.getCnt() > 0)
					str += ",\"leaf\":false,\"expanded\":true,\"children\":[" + createTreeGridJson(list, sysDepartment.getCode()) + "]";
				else
					str += ",\"leaf\":true";
				str += "},";
			}
		}
		str = StringUtil.subTract(str);
		return str;
	}

	/**
	 * 取得团队结构 comboboxTree json
	 * 
	 * @return
	 */
	public String getComboboxTreeJson() {
		List<SysDepartment> list = sysDepartmentDao.list();
		String json = "";
		json = createComboboxTreeJson(list, "DEP000");
		return "[" + json + "]";
	}

	private String createComboboxTreeJson(List<SysDepartment> list, String parent_code) {
		String str = "";
		for (SysDepartment sysDepartment : list) {
			if (WebUtil.getSafeStr(sysDepartment.getParent_code()).equals(parent_code)) {
				str += "{\"id\":\"" + sysDepartment.getCode() + "\",\"parent_id\":\"" + sysDepartment.getParent_code() + "\",\"text\":\""
						+ sysDepartment.getName() + "\"";
				if (WebUtil.getSafeStr(sysDepartment.getParent_code()).equals("DEP000")) {
					str += ",\"expanded\":" + true + "";
				} else {
					str += ",\"expanded\":" + false + "";
				}
				if (sysDepartment.getCnt() > 0)
					str += ",\"children\":[" + createComboboxTreeJson(list, sysDepartment.getCode()) + "]";
				else {
					str += ",\"leaf\":true";
				}
				str += "},";
			}
		}
		str = StringUtil.subTract(str);
		return str;
	}
}
