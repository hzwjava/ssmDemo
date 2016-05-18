package com.zhengs.log.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.zhengs.log.bo.LogDTO;
import com.zhengs.log.service.impl.LogServiceImpl;

/**
 * 日志controller
 * @author zhengshan
 * @Date 2016-5-18
 */
@Controller
@RequestMapping("/log")
public class LogController {
	
	/**
	 * 日志service
	 */
	@Autowired
	private LogServiceImpl logServiceImpl;

	/**
	 * 日志主页面
	 *	@author zhengshan
	 *	@Date 2016-5-18
	 *  @param request
	 *  @return 日志view
	 */
	@RequestMapping(value = "logList")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("logList");
		return mv;
	}

	/**
	 * 获取日志列表
	 *	@author zhengshan
	 *	@Date 2016-5-18
	 *  @param request
	 *  @param dto 日志dto
	 *  @return 日志列表
	 */
	@RequestMapping(value = "/getLogList", produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String getLogList(HttpServletRequest request, LogDTO dto) {
		List<Document> list = logServiceImpl.getLogList(dto);
		String text = "{\"total\":" + dto.getTotal() + ",\"rows\":" + JSON.toJSONString(list)+ "}";
		
		return text;
	}
}
