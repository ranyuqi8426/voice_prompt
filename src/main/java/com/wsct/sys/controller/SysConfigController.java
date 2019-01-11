package com.wsct.sys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wsct.sys.model.SysConfig;
import com.wsct.sys.service.SysConfigService;
import com.wsct.util.controller.BaseController;
import com.wsct.util.json.JsonUtil;
import com.wsct.util.web.WebUtil;

@Controller
@RequestMapping("/sys/config/")
public class SysConfigController extends BaseController {

	@Autowired
	private SysConfigService sysConfigService;

	/**
	 * 进入界面
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		// 获取是否锁定所有用户
		mv.setViewName("/sys/config");
		setBtnAutho(request, "SysConfig");// 设置按钮权限
		return mv;
	}

	/**
	 * 新增
	 * @param request
	 * @param response
	 * @param baseProtype
	 */
	@RequestMapping("/add")
	public void add(HttpServletRequest request, HttpServletResponse response, SysConfig SysConfig) {
		int flag = sysConfigService.add(SysConfig);
		WebUtil.outOpera(response, flag);
	}

	/**
	 * 修改
	 * @param request
	 * @param response
	 * @param baseProtype
	 */
	@RequestMapping("/update")
	public void update(HttpServletRequest request, HttpServletResponse response, SysConfig SysConfig) {
		int flag = sysConfigService.update(SysConfig);
		WebUtil.outOpera(response, flag);
	}

	/**
	 * 删除
	 * @param request
	 * @param response
	 * @param code
	 */
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, String syskey) {
		int flag = sysConfigService.delete(syskey);
		if (flag == 0)
			WebUtil.out(response, JsonUtil.createOperaStr(false, "删除失败"));
		else if (flag == 1)
			WebUtil.out(response, JsonUtil.createOperaStr(true, "删除成功"));
	}

	/**
	 *列表
	 * @param request
	 * @param response
	 */
	@RequestMapping("/search")
	public void search(HttpServletRequest request, HttpServletResponse response) {
		String json = JsonUtil.toStr(sysConfigService.list());
		WebUtil.out(response, json);
	}

}
