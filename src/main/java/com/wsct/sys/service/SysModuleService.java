package com.wsct.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wsct.sys.dao.SysFunctionDao;
import com.wsct.sys.dao.SysModuleDao;
import com.wsct.sys.model.SysModule;
import com.wsct.util.string.StringUtil;

@Service
public class SysModuleService {
	@Autowired
	private SysModuleDao sysModuleDao;
	@Autowired
	private SysFunctionDao sysOperaDao;

	public int add(SysModule sysModule) {
		return sysModuleDao.add(sysModule);
	}

	public int update(SysModule sysModule) {
		return sysModuleDao.update(sysModule);
	}

	public int delete(int id) {
		if (sysModuleDao.getChildCount(id) > 0)
			return 2;
		else {
			sysOperaDao.deleteByModule_id(id);
			return sysModuleDao.delete(id);
		}
	}

	public SysModule get(int id) {
		return sysModuleDao.get(id);
	}

	public SysModule getByModuleCode(String code) {
		return sysModuleDao.getByModuleCode(code);
	}

	public List<SysModule> list() {
		return sysModuleDao.list();
	}

	/**
	 * 取得模块 treegrid json
	 * 
	 * @return
	 */
	public String getTreeGridJson() {
		List<SysModule> list = list();
		String json = "";
		json = createTreeGridJson(list, 1);
		return "[" + json + "]";
	}

	private String createTreeGridJson(List<SysModule> list, int parent_id) {
		String str = "";
		for (SysModule sysModule : list) {
			if (sysModule.getParent_id() == parent_id) {
				str += "{\"id\":" + sysModule.getId() + ",\"parent_id\":" + sysModule.getParent_id() + ",\"name\":\"" + sysModule.getName() + "\""
						+ ",\"code\":\"" + sysModule.getCode() + "\"" + ",\"url\":\"" + sysModule.getUrl() + "\"" + ",\"target\":\"" + sysModule.getTarget()
						+ "\"" + ",\"iconImg\":\"" + sysModule.getIconImg() + "\"" + ",\"display_order\":" + sysModule.getDisplay_order();
				if (sysModule.getCnt() > 0)
					str += ",\"leaf\":false,\"expanded\":true,\"children\":[" + createTreeGridJson(list, sysModule.getId()) + "]";
				else
					str += ",\"leaf\":true";
				str += "},";
			}
		}
		str = StringUtil.subTract(str);
		return str;
	}

	/**
	 * 取得模块 comboboxTree json
	 * 
	 * @return
	 */
	public String getComboboxTreeJson() {
		List<SysModule> list = list();
		String json = "";
		json = createComboboxTreeJson(list, 0);
		return "[" + json + "]";
	}

	private String createComboboxTreeJson(List<SysModule> list, int parent_id) {
		String str = "";
		for (SysModule sysModule : list) {
			if (sysModule.getParent_id() == parent_id) {
				str += "{\"id\":\"" + sysModule.getId() + "\",\"parent_id\":" + sysModule.getParent_id() + ",\"text\":\"" + sysModule.getName() + "\"";
				if (sysModule.getParent_id() == 0) {
					str += ",\"expanded\":" + true + "";
				} else {
					str += ",\"expanded\":" + false + "";
				}
				if (sysModule.getCnt() > 0)
					str += ",\"children\":[" + createComboboxTreeJson(list, sysModule.getId()) + "]";
				else {
					str += ",\"leaf\":true";
				}
				str += "},";
			}
		}
		str = StringUtil.subTract(str);
		return str;
	}

	public int getChildCount(int id) {
		return sysModuleDao.getChildCount(id);
	}

	/**
	 * 根据user_id获取所有功能菜单
	 * @param user_id
	 * @return
	 */
	public List<SysModule> listByUser_id(int role_id) {
		return sysModuleDao.listByRole_id(role_id);
	}
}
