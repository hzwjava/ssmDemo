package com.zhengs.demo.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.zhengs.bo.ResultDTO;
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
	
	@RequestMapping(value = "/popuEdit")
	@ResponseBody
	public ModelAndView popuEdit(UserDTO dto) {
		ModelAndView mv = new ModelAndView("User/userEdit");
		
		mv.addObject("id", dto.getId());
		return mv;
	}
	
	@RequestMapping(value = "getUserById" , produces = { "application/json;charset=UTF-8" })
	public @ResponseBody
	UserBean getUserById(UserDTO dto, HttpServletRequest request) {
		return userServiceImpl.getUserById(dto.getId());
	}

	@RequestMapping(value = "/saveUser", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public ResultDTO saveUser(UserDTO dto, HttpServletRequest request) {
		ResultDTO result = new ResultDTO(false, "");
		
		try {
			result.setFlag(userServiceImpl.saveUser(dto));
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("保存失败！");
		}
		
		return result;
	}

	@RequestMapping(value = "delUser")
	public @ResponseBody ResultDTO delUser(HttpServletResponse response,
			@RequestParam(required = false, value = "id") String id) {
		ResultDTO result = new ResultDTO(false, "");
		
		String[] idArray = id.split(",");
		List<String> list = Arrays.asList(idArray);
		
		try {
			result.setFlag(userServiceImpl.delUser(list));
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("删除失败！");
		}
		
		return result;
	}
}
