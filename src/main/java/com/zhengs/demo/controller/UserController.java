package com.zhengs.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.zhengs.demo.bo.UserBean;
import com.zhengs.demo.bo.UserDTO;
import com.zhengs.demo.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userServiceImpl;

	@RequestMapping(value = "index")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("index");
		return mv;
	}

	@RequestMapping(value = "/getUserList", produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String getUserList(HttpServletRequest request, UserDTO dto) {
		List<UserBean> list = userServiceImpl.getUserList(dto);
		String text = "{\"total\":" + dto.getTotal() + ",\"rows\":" + JSON.toJSONString(list) + "}";
		return text;
	}
}
