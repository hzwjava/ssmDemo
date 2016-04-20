package com.zhengs.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test")
public class TestController{

	@RequestMapping(value = "test")
	public ModelAndView epistemeList(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("test");
		return mv;
	}
}
