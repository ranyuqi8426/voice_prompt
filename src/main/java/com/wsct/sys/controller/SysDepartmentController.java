package com.wsct.sys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wsct.sys.model.SysDepartment;
import com.wsct.sys.service.SysDepartmentService;
import com.wsct.util.controller.BaseController;
import com.wsct.util.json.JsonUtil;
import com.wsct.util.web.WebUtil;

@Controller
@RequestMapping("/base/department")
public class SysDepartmentController extends BaseController {

	@Autowired
	private SysDepartmentService departmentService;

	/**
	 * 主界面
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/sys/department");
		setBtnAutho(request, "SysDepartment");// 设置按钮权限
		return mv;
	}

	/**
	 * 列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getTreeGrid")
	public void getTreeGrid(HttpServletRequest request, HttpServletResponse response) {
		String json = departmentService.getTreeGridJson();
		WebUtil.out(response, json);
	}

	/**
	 * 修改
	 * 
	 * @param request
	 * @param response
	 * @param baseDepartment
	 */
	@RequestMapping("/update")
	public void update(HttpServletRequest request, HttpServletResponse response, SysDepartment sysDepartment) {
		if (sysDepartment.getCode().equals(sysDepartment.getParent_code())) {
			WebUtil.out(response, JsonUtil.createOperaStr(false, "不能和上级节点一样"));
		} else {
			int flag = departmentService.update(sysDepartment);
			if (flag == 0)
				WebUtil.out(response, JsonUtil.createOperaStr(false, "保存失败"));
			else if (flag == 1)
				WebUtil.out(response, JsonUtil.createOperaStr(true, "保存成功"));
			else if (flag == 2)
				WebUtil.out(response, JsonUtil.createOperaStr(false, "上级节点不存在"));
		}
	}

	/**
	 * 新增
	 * @param request
	 * @param response
	 * @param baseDepartment
	 */
	@RequestMapping("/add")
	public void add(HttpServletRequest request, HttpServletResponse response, SysDepartment sysDepartment) {
		if (sysDepartment.getCode().equals("0")) {
			int flag = departmentService.add(sysDepartment);
			if (flag == 0)
				WebUtil.out(response, JsonUtil.createOperaStr(false, "保存失败"));
			else if (flag == 1)
				WebUtil.out(response, JsonUtil.createOperaStr(true, "保存成功"));
		}
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 * @param id
	 */
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, String code) {
		int flag = departmentService.delete(code);
		if (flag == 0)
			WebUtil.out(response, JsonUtil.createOperaStr(false, "删除失败"));
		else if (flag == 1)
			WebUtil.out(response, JsonUtil.createOperaStr(true, "删除成功"));
		else if (flag == 2)
			WebUtil.out(response, JsonUtil.createOperaStr(false, "还有下级节点，不能删除"));
		else if (flag == 3)
			WebUtil.out(response, JsonUtil.createOperaStr(false, "已有员工选择该团队，不能删除"));
	}
}
