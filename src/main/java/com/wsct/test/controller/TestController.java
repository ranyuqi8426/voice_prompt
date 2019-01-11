package com.wsct.test.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wsct.util.json.JsonUtil;
import com.wsct.util.web.WebUtil;
import com.wsct.activemq.MqProducer;
import com.wsct.test.model.Test;
import com.wsct.test.service.TestService;
import com.wsct.test.vo.TestVO;
import com.wsct.util.controller.BaseController;

@Controller
@RequestMapping("/first")
public class TestController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger("operationLog");
	@Autowired
	private TestService testService;
	@Autowired
    private MqProducer mqProducer;
	/**
	 * 页面跳转
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView  index(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("name", "测试成功");
		mv.setViewName("test");
		return mv ;
	}
	/**
	 * 查询列表
	 * @param request
	 * @param response
	 * @param testVO
	 */
	@RequestMapping("/searchTest")
	public void searchTest(HttpServletRequest request,HttpServletResponse response,TestVO testVO) {
		logger.info("开始");
		List<Test> list = testService.list(testVO);
		String json = JsonUtil.toStr(list);
		WebUtil.out(response, json);
	}
	/**
	 * 测试MQ发送接收
	 * @param request
	 * @param response
	 * @param testVO
	 */
	@RequestMapping("/mqSend")
	public void mqSend(HttpServletRequest request,HttpServletResponse response,TestVO testVO) {
		logger.info("开始发送");
		mqProducer.sendStringQueue("stringQueue", "12345测试success");
		logger.info("发送成功");
	}
	@RequestMapping("/add")
	public void add(HttpServletRequest request,HttpServletResponse response,TestVO testVO) {
		int flag = testService.add(testVO);
		WebUtil.outOpera(response, flag);
	}
	@RequestMapping("/update")
	@ResponseBody
	public void update(HttpServletRequest request,HttpServletResponse response,TestVO testVO) {
		int flag = testService.update(testVO);
		WebUtil.outOpera(response, flag);
	}
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request,HttpServletResponse response,String id) {
		int flag = testService.delete(id);
		WebUtil.outOpera(response, flag);
	}
}
