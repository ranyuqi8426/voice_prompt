package com.wsct.sys.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wsct.config.listener.OnLineListener;
import com.wsct.sys.model.SysDw;
import com.wsct.sys.model.SysUser;
import com.wsct.sys.model.SysUserLogin;
import com.wsct.sys.service.SysDwService;
import com.wsct.sys.service.SysUserLoginService;
import com.wsct.sys.service.SysUserService;
import com.wsct.util.date.DateUtil;
import com.wsct.util.json.JsonUtil;
import com.wsct.util.session.SessionUtil;
import com.wsct.util.session.UserSession;
import com.wsct.util.string.StringUtil;
import com.wsct.util.web.WebUtil;

@RequestMapping("/")
@Controller
public class SysLoginController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserLoginService sysUserLoginService;
	@Autowired
	private SysDwService sysDwService;

	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/login2");
		return mv;
	}

	/**
	 * 登录校验
	 * @param request
	 * @param response
	 * @param username
	 * @param password
	 */
	@RequestMapping("/checkLogin")
	public void checkLogin(HttpServletRequest request, HttpServletResponse response, String username, String password) {
		{
			SysUser sysUser = sysUserService.getByUsername(username);
			if (sysUser == null || !sysUserService.checkPass(sysUser, password)) {
				WebUtil.out(response, JsonUtil.createOperaStr(false, "用户名或密码错误"));
			} else {
				if (sysUser.getStatus() == 2) {
					WebUtil.out(response, JsonUtil.createOperaStr(false, "该用户已锁定"));
				} else {
					String ip = StringUtil.getIp(request);
					UserSession userSession = new UserSession();
					userSession.setUser_id(sysUser.getId());
					userSession.setUser_name(sysUser.getUsername());
					userSession.setUser_id(sysUser.getId());
					userSession.setUser_ip(ip);
					userSession.setRealname(sysUser.getRealname());
					userSession.setPhone(sysUser.getPhone());//电话
					userSession.setRole_id(sysUser.getRole_id());

					userSession.setDefault_module(sysUser.getDefault_module());// 默认模块
					userSession.setDwid(sysUser.getDwid());// 单位id
					SysDw sysDw = sysDwService.get(sysUser.getDwid());
					if (sysDw != null) {
						userSession.setDwjb(sysDw.getDwjb());// 单位级别
						userSession.setDwmc(sysDw.getDwmc());// 单位名称
						userSession.setDwlx(sysDw.getDwlx());// 单位类型
						userSession.setDwsx(sysDw.getDwsx());// 单位属性
						userSession.setDwfzr(sysDw.getDwfzr());//单位负责人
					} else {
						userSession.setDwjb("");
						userSession.setDwmc("");// 单位名称
						userSession.setDwlx("");// 单位类型
						userSession.setDwsx("");// 单位属性
						userSession.setDwfzr("");//单位负责人
					}
					request.getSession().setAttribute("userSession", userSession);

					// 插入登录日志
					SysUserLogin sysUserLogin = new SysUserLogin();
					sysUserLogin.setUser_id(sysUser.getId());
					sysUserLogin.setLogin_date(new Date());
					sysUserLogin.setLogin_ip(ip);
					sysUserLogin.setTerminal(WebUtil.getSafeStr(request.getParameter("terminal")));
					sysUserLogin.setExplorerType(WebUtil.getSafeStr(request.getParameter("explorerType")));
					sysUserLogin.setExplorerVersion(WebUtil.getSafeStr(request.getParameter("explorerVersion")));
					sysUserLoginService.add(sysUserLogin);
					WebUtil.out(response, JsonUtil.createOperaStr(true, "登录成功"));
				}
			}
		}
	}

	/**
	 * 进入首页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		UserSession userSession = SessionUtil.getUserSession(request);
		if (userSession != null) {
			mv.addObject("today", DateUtil.getSystemDate());
			int onlineCount = OnLineListener.hashMap.size();
			mv.addObject("onlineCount", onlineCount);// 在线人数
			SysUser sysUser = sysUserService.get(userSession.getUser_id());
			mv.addObject("status", sysUser.getStatus());// 状态
			mv.setViewName("/index");
		} else {
			mv.setViewName("redirect:/login.do");
		}
		return mv;
	}

	/**
	 *生成menu
	 * @param request
	 * @param response
	 */
	@RequestMapping("/createMenu")
	public void createMenu(HttpServletRequest request, HttpServletResponse response) {
		UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
		String json = sysUserService.createMenu(userSession.getRole_id());
		WebUtil.out(response, json);
	}

	/**
	 * 退出
	 * @param request
	 * @param response
	 */
	@RequestMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		// request.getSession().removeAttribute("userSession");
		request.getSession().invalidate();
		WebUtil.out(response, JsonUtil.createOperaStr(true, "操作成功"));
	}

	/**
	 * 修改个人信息，登录页面
	 */
	@RequestMapping("/updateInfo")
	public void updateInfo(HttpServletRequest request, HttpServletResponse response, SysUser sysUser) {
		String oldPass = WebUtil.getSafeStr(request.getParameter("oldPass"));
		String newPass = WebUtil.getSafeStr(request.getParameter("newPass"));
		int flag = sysUserService.updateInfo(sysUser, oldPass, newPass);
		if (flag == 0)
			WebUtil.out(response, JsonUtil.createOperaStr(false, "修改失败"));
		else if (flag == 1)
			WebUtil.out(response, JsonUtil.createOperaStr(true, "修改成功"));
		else if (flag == 2)
			WebUtil.out(response, JsonUtil.createOperaStr(false, "原密码输入错误"));
	}

	/**
	 * 获取用户信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getUserInfo")
	public void getUserInfo(HttpServletRequest request, HttpServletResponse response) {
		UserSession userSession = SessionUtil.getUserSession(request);
		// 获取用户信息
		SysUser sysUser = sysUserService.get(userSession.getUser_id());
		WebUtil.out(response, JsonUtil.toStr(sysUser));
	}

}
