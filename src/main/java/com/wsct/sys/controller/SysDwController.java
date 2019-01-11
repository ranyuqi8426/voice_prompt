package com.wsct.sys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wsct.sys.model.SysDw;
import com.wsct.sys.service.SysDwService;
import com.wsct.sys.vo.SysDwSearchVO;
import com.wsct.util.controller.BaseController;
import com.wsct.util.global.GlobalConst;
import com.wsct.util.json.JsonUtil;
import com.wsct.util.session.SessionUtil;
import com.wsct.util.string.StringUtil;
import com.wsct.util.web.WebUtil;

/**
 * 系统单位Controller
 * @author 孔垂云
 * @date 2016年12月6日
 */
@Controller
@RequestMapping("/sys/dw")
public class SysDwController extends BaseController {

	@Autowired
	private SysDwService sysDwService;

	/**
	 * 进入单位维护界面
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/sys/dw");
		setBtnAutho(request, "SysDw");
		return mv;
	}

	/**
	 * 新增单位
	 * @param request
	 * @param response
	 * @param sysUser
	 */
	@RequestMapping("/add")
	public void add(HttpServletRequest request, HttpServletResponse response, SysDw sysDw) {
		int flag = sysDwService.add(sysDw);
		if (flag == 0)
			WebUtil.out(response, JsonUtil.createOperaStr(false, "保存失败"));
		else if (flag == 1)
			WebUtil.out(response, JsonUtil.createOperaStr(true, "保存成功"));
		else if (flag == 2)
			WebUtil.out(response, JsonUtil.createOperaStr(false, "单位代码已存在"));
	}

	/**
	 * 修改单位
	 * @param request
	 * @param response
	 */
	@RequestMapping("/update")
	public void update(HttpServletRequest request, HttpServletResponse response, SysDw sysDw) {
		int flag = sysDwService.update(sysDw);
		if (flag == 0)
			WebUtil.out(response, JsonUtil.createOperaStr(false, "保存失败"));
		else if (flag == 1)
			WebUtil.out(response, JsonUtil.createOperaStr(true, "保存成功"));
	}

	/**
	 * 删除单位
	 * @param request
	 * @param response
	 * @param id
	 */
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, int dwid) {
		int flag = sysDwService.delete(dwid);
		if (flag == 0)
			WebUtil.out(response, JsonUtil.createOperaStr(false, "删除失败"));
		else if (flag == 1)
			WebUtil.out(response, JsonUtil.createOperaStr(true, "删除成功"));
		else if (flag == 2)
			WebUtil.out(response, JsonUtil.createOperaStr(false, "还有子节点，不允许删除"));
	}

	/**
	 * 单位grid列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/search")
	public void search(HttpServletRequest request, HttpServletResponse response, SysDwSearchVO sysDwSearchVO) {
		int count = sysDwService.listCount(sysDwSearchVO);
		int pageIndex = WebUtil.getSafeInt(request.getParameter("page"), 1);
		int pageSize = WebUtil.getSafeInt(request.getParameter("limit"), GlobalConst.pageSize);
		String json = JsonUtil.createExtjsPageJson(count, sysDwService.list(sysDwSearchVO, pageIndex, pageSize));
		WebUtil.out(response, json);
	}

	/**
	 * 单位树列表
	 * 根据selType来判断是选本级的还是全部，默认全部，只有单位维护里面才显示本级的dwid
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getTreeGrid")
	public void getTreeGrid(HttpServletRequest request, HttpServletResponse response) {
		int dwid = 0;
		String selType = request.getParameter("selType");
		if (StringUtil.isNullOrEmpty(selType))
			dwid = 1;
		else
			dwid = SessionUtil.getDwid(request);
		String json = sysDwService.getTreeGridJson(dwid);
		WebUtil.out(response, json);
	}

	/**
	 * 单位下拉树
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getComboboxTree")
	public void getComboboxTree(HttpServletRequest request, HttpServletResponse response) {
		int dwid = 0;
		String selType = request.getParameter("selType");
		if (StringUtil.isNullOrEmpty(selType))
			dwid = 1;
		else
			dwid = SessionUtil.getDwid(request);
		String json = sysDwService.getComboboxTreeJson(dwid);
		WebUtil.out(response, json);
	}

}
