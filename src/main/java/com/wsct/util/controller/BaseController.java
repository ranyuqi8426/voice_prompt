package com.wsct.util.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import com.wsct.config.interceptor.Message;
import com.wsct.sys.service.SysUserService;
import com.wsct.util.json.JsonUtil;
import com.wsct.util.session.SessionUtil;
import com.wsct.util.session.UserSession;
import com.wsct.util.string.StringUtil;
import com.wsct.util.web.WebUtil;

public class BaseController {
	private static Logger logger = LoggerFactory.getLogger("controllerLog");
	@Autowired
	private SysUserService sysUserService;

	@ExceptionHandler(Exception.class)
	public ModelAndView exception(HttpServletRequest request, HttpServletResponse response, Exception ex) {
		ex.printStackTrace();
		logger.error(ex.getMessage());
		//判断是否是Ajax请求
		boolean isAjaxRequest = StringUtil.checkAjaxRequest(request);// this.isAjaxRequest(request);
		//获取异常的详细信息
		if (isAjaxRequest) {
			Message msg = new Message();// MessageManager.exception(ex);
			//Ajax请求处理  
			msg.setSuccess(false);
			msg.setIsException(true);
			msg.setMessage(ex.getMessage());
			msg.setExName("错误提示");
			msg.setMsgText(ex.getMessage());
			WebUtil.out(response, JsonUtil.toStr(msg));
			return null;
		} else {
			//URL请求处理  
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("message", ex.getMessage());
			map.put("isError", true);
			map.put("exceptionName", ex.getMessage());
			return new ModelAndView("exception", map);
		}
	}

	/**
	 * 设置按钮权限
	 * @param request
	 * @param moduleCode
	 */
	protected void setBtnAutho(HttpServletRequest request, String moduleCode) {
		UserSession userSession = SessionUtil.getUserSession(request);
		String btnAutho = sysUserService.createBtnAutho(userSession.getRole_id(), moduleCode);
		request.setAttribute("btnAutho", btnAutho);
	}
}
