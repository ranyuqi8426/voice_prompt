package com.wsct.common.controller;

import javax.servlet.http.HttpServletRequest;


import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wsct.common.service.CommonService;
import com.wsct.sys.service.SysRoleService;
import com.wsct.util.json.JsonUtil;
import com.wsct.util.web.WebUtil;

import net.sf.ehcache.management.sampled.SampledMBeanRegistrationProvider;

/**
 * 公共处理方法，所有涉及数据库查询的下拉框，公共部分都在此定义 说明：这个类改完后及时提交，修改前先更新，避免冲突
 * 
 * @author chykong
 *
 */
@RequestMapping("/common")
@Controller
public class CommonController {
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private CommonService commonService;

	/**
	 * 角色列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/listRoleCombobox")
	public void listRoleCombobox(HttpServletRequest request, HttpServletResponse response) {
		WebUtil.out(response, JsonUtil.toStr(sysRoleService.listCombobox(WebUtil.getSafeInt(request.getParameter("is_admin")))));
	}
	/**
	 * 下拉框列表公共方法
	 * @param request
	 * @param response
	 * @param CATEGORY_VALUE
	 */
	@RequestMapping("/listCombobox")
	public void listUserCombobox(HttpServletRequest request,HttpServletResponse response, String category_value) {
		WebUtil.out(response, JsonUtil.toStr(commonService.listCombobox(category_value)));
		
	}
	
	/**
	 * 银行下拉框列表公共方法
	 * @param request
	 * @param response
	 * @param CATEGORY_VALUE
	 */
	@RequestMapping("/listBankCombobox")
	public void listBankCombobox(HttpServletRequest request,HttpServletResponse response) {
		WebUtil.out(response, JsonUtil.toStr(commonService.listBankCombobox()));
		
	}
	public static void main(String[] args) {
		String  val   = "2017-03-01 12:05:30";
		System.out.println(val.substring(0, 16));
	}
}
