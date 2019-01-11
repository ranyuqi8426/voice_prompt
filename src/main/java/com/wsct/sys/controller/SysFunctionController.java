package com.wsct.sys.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wsct.sys.model.SysFunction;
import com.wsct.sys.service.SysFunctionService;
import com.wsct.util.json.JsonUtil;
import com.wsct.util.web.WebUtil;

@Controller
@RequestMapping("/sys/function")
public class SysFunctionController {
	@Autowired
	private SysFunctionService sysFunctionService;

	@RequestMapping("/add")
	public void add(HttpServletRequest request, HttpServletResponse response, SysFunction sysFunction) {
		int flag = sysFunctionService.add(sysFunction);
		if (flag == 0)
			WebUtil.out(response, JsonUtil.createOperaStr(false, "保存失败"));
		else if (flag == 1)
			WebUtil.out(response, JsonUtil.createOperaStr(true, "保存成功"));
	}

	@RequestMapping("/update")
	public void update(HttpServletRequest request, HttpServletResponse response, SysFunction sysFunction) {
		int flag = sysFunctionService.update(sysFunction);
		if (flag == 0)
			WebUtil.out(response, JsonUtil.createOperaStr(false, "保存失败"));
		else if (flag == 1)
			WebUtil.out(response, JsonUtil.createOperaStr(true, "保存成功"));
	}

	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, int id) {
		int flag = sysFunctionService.delete(id);
		if (flag == 0)
			WebUtil.out(response, JsonUtil.createOperaStr(false, "删除失败"));
		else if (flag == 1)
			WebUtil.out(response, JsonUtil.createOperaStr(true, "删除成功"));
	}

	/**
	 * 操作 grid列表
	 * @param request
	 * @param response
	 */
	@RequestMapping("/listJson")
	public void listJson(HttpServletRequest request, HttpServletResponse response, int module_id) {
		List<SysFunction> list = sysFunctionService.list(module_id);
		WebUtil.out(response, JsonUtil.toStr(list));
	}

	/**
	 * 该角色对应的模块下功能列表
	 */
	@RequestMapping("/listRoleModuleFunction")
	public void listRoleModuleFunction(HttpServletRequest request, HttpServletResponse response) {
		int role_id = WebUtil.getSafeInt(request.getParameter("role_id"));
		int module_id = WebUtil.getSafeInt(request.getParameter("module_id"));
		String json = JsonUtil.toStr(sysFunctionService.listRoleModuleFunction(role_id, module_id));
		WebUtil.out(response, json);
	}

}
