package com.wsct.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wsct.common.vo.ComboboxVO;
import com.wsct.sys.dao.SysFunctionDao;
import com.wsct.sys.dao.SysModuleDao;
import com.wsct.sys.dao.SysRoleDao;
import com.wsct.sys.model.SysFunction;
import com.wsct.sys.model.SysModule;
import com.wsct.sys.model.SysRole;
import com.wsct.sys.model.SysRoleFunction;
import com.wsct.sys.model.SysRoleModule;
import com.wsct.util.cache.EhCacheUtil;
import com.wsct.util.string.StringUtil;

@Service
public class SysRoleService {

	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysModuleDao sysModuleDao;
	@Autowired
	private SysFunctionDao sysFunctionDao;

	public int add(SysRole sysRole) {
		// String maxCode = sysRoleDao.getMaxCode();
		// String code = CodeUtil.createRole(maxCode);
		// if (StringUtil.isNullOrEmpty(code))
		// return -1;
		// else {
		// sysRole.setCode(code);
		return sysRoleDao.add(sysRole);
	}

	public int update(SysRole sysRole) {
		return sysRoleDao.update(sysRole);
	}

	public int delete(int id) {
		int flag = 0;
		sysRoleDao.delete(id);
		sysRoleDao.deleteRoleModule(id);
		flag = 1;
		return flag;
	}

	public SysRole get(int id) {
		return sysRoleDao.get(id);
	}

	public List<SysRole> list() {
		return sysRoleDao.list();
	}

	/**
	 * 根据角色id获取该角色对应模块列表checkbox
	 * 
	 * @param role_id
	 * @return
	 */
	public String listRoleModuleJson(int role_id) {
		List<SysModule> listModule = sysModuleDao.list();// 模块列表
		String json = "";
		List<SysRoleModule> listRoleModule = sysRoleDao.listRoleModule(role_id);// 角色模块列表
		List<SysFunction> listFunction = sysFunctionDao.list();// 功能列表
		List<SysRoleFunction> listRoleFunction = sysFunctionDao.listRoleFunctionByRole_id(role_id);// 角色对应功能
		HashMap<Integer, Integer> hashRoleFunction = new HashMap<Integer, Integer>();// 角色对应功能hash
		for (SysRoleFunction sysRoleFunction : listRoleFunction) {
			hashRoleFunction.put(sysRoleFunction.getFunction_id(), sysRoleFunction.getFunction_id());
		}
		json = createRoleModuleJson(listModule, listRoleModule, listFunction, hashRoleFunction, 1);// 从根节点开始查找模块，不计算根节点
		return "[" + json + "]";
	}

	private String createRoleModuleJson(List<SysModule> listModule, List<SysRoleModule> listRoleModule,
			List<SysFunction> listFunction, HashMap<Integer, Integer> hashRoleFunction, int parent_id) {
		String str = "";
		for (SysModule sysModule : listModule) {
			if (sysModule.getParent_id() == parent_id) {
				str += "{\"id\":" + sysModule.getId() + ",\"text\":\"" + sysModule.getName() + "\",\"function\":\""
						+ createFunction(listFunction, hashRoleFunction, sysModule.getId()) + "\"";
				if (sysModule.getCnt() > 0) {
					str += ",\"leaf\":false";
					boolean flag = false;
					for (SysRoleModule sysRoleModule : listRoleModule) {
						if (sysRoleModule.getModule_id() == sysModule.getId()) {
							flag = true;
							break;
						}
					}
					str += ",\"checked\":" + flag;
					if (sysModule.getCnt() > 0)
						str += ",\"expanded\":true";
					str += ",\"children\":[" + createRoleModuleJson(listModule, listRoleModule, listFunction,
							hashRoleFunction, sysModule.getId()) + "]";
				} else {
					str += ",\"leaf\":true";
					boolean flag = false;
					for (SysRoleModule sysRoleModule : listRoleModule) {
						if (sysRoleModule.getModule_id() == sysModule.getId()) {
							flag = true;
							break;
						}
					}
					str += ",\"checked\":" + flag;
				}
				str += "},";
			}
		}
		str = StringUtil.subTract(str);
		return str;
	}

	private String createFunction(List<SysFunction> listFunction, HashMap<Integer, Integer> hashRoleFunction,
			int module_id) {
		String function = "";
		for (SysFunction sysFunction : listFunction) {
			if (sysFunction.getModule_id() == module_id && sysFunction.getType() == 1) {
				function += sysFunction.getId() + "@" + sysFunction.getName() + "@";
				if (hashRoleFunction.containsKey(sysFunction.getId())) {
					function += "1";
				} else {
					function += "0";
				}
				function += ",";
			}
		}
		function = StringUtil.subTract(function);
		return function;
	}

	/**
	 * 修改角色对应模块。目前的流程是，权限设置只设置写按钮的权限，只读的随模块选中一起设置。 处理逻辑如下：1、删除角色对应模块、角色对应按钮权限
	 * 2、新增角色对应模块，角色对应按钮 3、把模块下面的所有只读按钮也取出来，插入角色对应按钮
	 * 
	 * @param role_id
	 * @param moduleList
	 *            模块列表
	 * @param buttonIdList
	 *            按钮列表
	 * @return
	 */
	public int updateRoleModule(int role_id, String moduleList, String functionList) {
		int flag = 0;
		String moduleArr[] = null;
		String functionArr[] = null;
		sysRoleDao.deleteRoleModule(role_id);
		moduleArr = moduleList.split("@@");

		List<SysRoleModule> listRoleModule = new ArrayList<>();
		for (int i = 0; i < moduleArr.length; i++) {
			if (StringUtil.isNotNullOrEmpty(moduleArr[i])) {
				listRoleModule.add(new SysRoleModule(role_id, Integer.parseInt(moduleArr[i])));
			}
		}
		sysRoleDao.addRoleModule(listRoleModule);

		sysRoleDao.deleteRoleFunction(role_id);
		functionArr = functionList.split("@@");
		List<SysRoleFunction> listRoleFunction = new ArrayList<>();
		for (int i = 0; i < functionArr.length; i++) {
			if (StringUtil.isNotNullOrEmpty(functionArr[i])) {
				listRoleFunction.add(new SysRoleFunction(role_id, Integer.parseInt(functionArr[i])));
			}
		}
		//添加角色对应功能
		sysRoleDao.addRoleFunction(listRoleFunction);
		EhCacheUtil.removeAll("sysCache");
		flag = 1;
		return flag;
	}

	/**
	 * 取得所有角色的所有功能
	 * 
	 * @return
	 */
	public HashMap<Integer, HashMap<Integer, String>> hashRolesFunctions() {
		HashMap<Integer, HashMap<Integer, String>> hashMap = null;
		hashMap = EhCacheUtil.get("sysCache", "roleFunction");
		if (hashMap == null) {
			List<SysRole> listRoles = list();// 所有角色
			hashMap = new HashMap<Integer, HashMap<Integer, String>>();
			for (SysRole sysRole : listRoles) {
				HashMap<Integer, String> hashFunction = sysFunctionDao.hashRoleFunctionByRole_id(sysRole.getId());
				hashMap.put(sysRole.getId(), hashFunction);
			}
			System.out.println("---初始化所有角色的功能--");
			EhCacheUtil.put("sysCache", "roleFunction", hashMap);
		}

		return hashMap;
	}

	/**
	 * 下拉框角色列表
	 * 
	 * @return
	 */
	public List<ComboboxVO> listCombobox(int is_admin) {
		return sysRoleDao.listCombobox(is_admin);
	}

}
