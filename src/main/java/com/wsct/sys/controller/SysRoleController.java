package com.wsct.sys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wsct.sys.model.SysRole;
import com.wsct.sys.service.SysRoleService;
import com.wsct.util.controller.BaseController;
import com.wsct.util.json.JsonUtil;
import com.wsct.util.web.WebUtil;

@RequestMapping("/sys/role")
@Controller
public class SysRoleController extends BaseController {
	@Autowired
	private SysRoleService sysRoleService;

	/**
	 * 进入角色维护界面
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/sys/role");
		setBtnAutho(request, "SysRole");
		return mv;
	}

	@RequestMapping("/add")
	public void add(HttpServletRequest request, HttpServletResponse response, SysRole sysRole) {
		int flag = sysRoleService.add(sysRole);
		if (flag == 0)
			WebUtil.out(response, JsonUtil.createOperaStr(false, "保存失败"));
		else if (flag == 1)
			WebUtil.out(response, JsonUtil.createOperaStr(true, "保存成功"));
	}

	@RequestMapping("/update")
	public void update(HttpServletRequest request, HttpServletResponse response, SysRole sysRole) {
		int flag = sysRoleService.update(sysRole);
		if (flag == 0)
			WebUtil.out(response, JsonUtil.createOperaStr(false, "保存失败"));
		else if (flag == 1)
			WebUtil.out(response, JsonUtil.createOperaStr(true, "保存成功"));
	}

	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, int id) {
		int flag = sysRoleService.delete(id);
		if (flag == 0)
			WebUtil.out(response, JsonUtil.createOperaStr(false, "删除失败"));
		else if (flag == 1)
			WebUtil.out(response, JsonUtil.createOperaStr(true, "删除成功"));
		else if (flag == 2)
			WebUtil.out(response, JsonUtil.createOperaStr(false, "还有下级节点，不能删除"));
	}

	/**
	 * 角色 grid列表
	 * @param request
	 * @param response
	 */
	@RequestMapping("/listJson")
	public void listJson(HttpServletRequest request, HttpServletResponse response) {
		String json = JsonUtil.toStr(sysRoleService.list());
		WebUtil.out(response, json);
	}

	/**
	 * 角色 对应的模块列表，checkbox tree
	 * @param request
	 * @param response
	 */
	@RequestMapping("/listRoleModuleJson")
	public void listRoleModuleJson(HttpServletRequest request, HttpServletResponse response) {
		int role_id = WebUtil.getSafeInt(request.getParameter("role_id"));
		WebUtil.out(response, sysRoleService.listRoleModuleJson(role_id));
	}

	/**
	 * 修改角色对应的模块
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updateRoleModule")
	public void updateRoleModule(HttpServletRequest request, HttpServletResponse response, int role_id) {
		String moduleList = WebUtil.getSafeStr(request.getParameter("moduleList"));
		String functionList = WebUtil.getSafeStr(request.getParameter("functionList"));
		int flag = sysRoleService.updateRoleModule(role_id, moduleList, functionList);
		if (flag == 0)
			WebUtil.out(response, JsonUtil.createOperaStr(false, "设置失败"));
		else if (flag == 1)
			WebUtil.out(response, JsonUtil.createOperaStr(true, "设置成功"));
	}
}
