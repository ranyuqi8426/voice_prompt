package com.wsct.config.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.wsct.sys.service.SysLogService;
import com.wsct.sys.service.SysUserService;
import com.wsct.util.date.DateUtil;
import com.wsct.util.json.JsonUtil;
import com.wsct.util.session.SessionUtil;
import com.wsct.util.session.UserSession;
import com.wsct.util.string.StringUtil;
import com.wsct.util.web.WebUtil;

public class AuthorityInterceptor implements HandlerInterceptor {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysLogService sysLogService;
	private static Logger logger = LoggerFactory.getLogger("operationLog");

	/**
	 * 操作前先判断是否登录，未登录跳转到登录界面
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		UserSession userSession = SessionUtil.getUserSession(request);
		// 校验权限
		String path = request.getServletPath();
		path = path.substring(1, path.length());
		// return true;

		String operaMethod = path.substring(path.lastIndexOf("/"));
		operaMethod = operaMethod.substring(1, operaMethod.length());
		String parameters = getOperaParams(request);
		// 记日志
		logOperation(path, parameters, userSession);
		// 目前只校验add/update/delete/save/import开头的方法，其余不校验
		if (operaMethod.startsWith("add") || operaMethod.startsWith("update") || operaMethod.startsWith("delete")
				|| operaMethod.startsWith("save") || operaMethod.startsWith("import")) {
			boolean checked = sysUserService.checkAuthority(userSession.getRole_id(), path);
			if (checked) {
				// 记录数据库日志
				sysLogService.addLog(userSession.getUser_id(), path, parameters, userSession.getUser_ip());
				return true;
			} else {
				boolean isAjaxRequest = StringUtil.checkAjaxRequest(request);
				if (isAjaxRequest) {
					Message msg = new Message();
					msg.setSuccess(false);
					msg.setExName("错误提示");
					msg.setMessage("权限不足");
					msg.setMsgText("权限不足");
					msg.setIsException(true);
					WebUtil.out(response, JsonUtil.toStr(msg));
				} else {
					String str = "<script>alert('权限不足!');history.back(-1);</script>";
					WebUtil.out(response, str);
				}
				return false;
			}
		} else {
			return true;
		}
	}

	/**
	 * 记录文本日志
	 * @param path
	 * @param parameters
	 * @param us
	 */
	public void logOperation(String path, String parameters, UserSession us) {
		String log = "";
		log = "[OPERALOG]" + "-[" + us.getUser_ip() + "]" + "-[" + DateUtil.getSystemTime() + "]-" + "["
				+ us.getUser_id() + "]-" + "[INFO]-" + path + "-" + parameters;
		logger.info(log);
	}

	/**
	 * 获取所有操作参数
	 * @param request
	 * @return
	 */
	private String getOperaParams(HttpServletRequest request) {
		String parameters = "";// 定义所有参数值
		Map<String, String[]> map = request.getParameterMap();
		// /取得所有参数值，用&号组装起来
		Object[] obj = null;
		obj = map.keySet().toArray();
		for (int i = 0; i < obj.length; i++) {
			parameters += obj[i].toString() + "=" + request.getParameter(obj[i].toString()) + "&";
		}
		return parameters;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
