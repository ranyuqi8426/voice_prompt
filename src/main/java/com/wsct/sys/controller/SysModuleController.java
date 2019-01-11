package com.wsct.sys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wsct.sys.model.SysModule;
import com.wsct.sys.service.SysModuleService;
import com.wsct.util.cache.EhCacheUtil;
import com.wsct.util.controller.BaseController;
import com.wsct.util.json.JsonUtil;
import com.wsct.util.web.WebUtil;

@Controller
@RequestMapping("/sys/module")
public class SysModuleController extends BaseController {
	@Autowired
	private SysModuleService sysModuleService;

	/**
	 * 进入模块维护界面
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/sys/module");
		setBtnAutho(request, "SysModule");
		return mv;
	}

	@RequestMapping("/add")
	public void add(HttpServletRequest request, HttpServletResponse response, SysModule sysModule) {
		int flag = sysModuleService.add(sysModule);
		if (flag == 0)
			WebUtil.out(response, JsonUtil.createOperaStr(false, "保存失败"));
		else if (flag == 1)
			WebUtil.out(response, JsonUtil.createOperaStr(true, "保存成功"));
	}

	@RequestMapping("/update")
	public void update(HttpServletRequest request, HttpServletResponse response, SysModule sysModule) {
		if (sysModule.getId() == sysModule.getParent_id()) {
			WebUtil.out(response, JsonUtil.createOperaStr(false, "不能和上级节点一样"));
		} else {
			int flag = sysModuleService.update(sysModule);
			if (flag == 0)
				WebUtil.out(response, JsonUtil.createOperaStr(false, "保存失败"));
			else if (flag == 1)
				WebUtil.out(response, JsonUtil.createOperaStr(true, "保存成功"));
			else if (flag == 2)
				WebUtil.out(response, JsonUtil.createOperaStr(false, "上级节点不存在"));
		}
	}

	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, int id) {
		int flag = sysModuleService.delete(id);
		if (flag == 0)
			WebUtil.out(response, JsonUtil.createOperaStr(false, "删除失败"));
		else if (flag == 1)
			WebUtil.out(response, JsonUtil.createOperaStr(true, "删除成功"));
		else if (flag == 2)
			WebUtil.out(response, JsonUtil.createOperaStr(false, "还有下级节点，不能删除"));
	}

	/**
	 * 模块tree grid列表
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getTreeGrid")
	public void getTreeGrid(HttpServletRequest request, HttpServletResponse response) {
		String json = sysModuleService.getTreeGridJson();
		WebUtil.out(response, json);
	}

	/**
	 * 模块combobox tree
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getComboboxTree")
	public void getComboboxTree(HttpServletRequest request, HttpServletResponse response) {
		String json = sysModuleService.getComboboxTreeJson();
		WebUtil.out(response, json);
	}

	/**
	 * 根据模块代码获取模块信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getModuleInfo")
	public void getModuleInfo(HttpServletRequest request, HttpServletResponse response, String code) {
		SysModule sysModule = sysModuleService.getByModuleCode(code);
		String json = JsonUtil.toStr(sysModule);
		WebUtil.out(response, json);
	}

	/**
	 * 清空缓存
	 * @param request
	 * @param response
	 */
	@RequestMapping("/clearCache")
	public void clearCache(HttpServletRequest request, HttpServletResponse response) {
		EhCacheUtil.removeAll("sysCache");
		WebUtil.out(response, JsonUtil.createOperaStr(true, "操作成功"));
	}

}
