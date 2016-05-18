package com.zhengs.demo.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.zhengs.aop.log.SystemControllerLog;
import com.zhengs.bo.ResultDTO;
import com.zhengs.demo.bo.UserBean;
import com.zhengs.demo.bo.UserDTO;
import com.zhengs.demo.service.IUserService;

/**
 * 用户控制器
 * @author zhengshan
 * @Date 2016-5-17
 */
@Controller
@RequestMapping("/user")
public class UserController {

	/**
	 * 用户service
	 */
	@Autowired
	private IUserService userServiceImpl;

	/**
	 * 用户首页
	 *	@author zhengshan
	 *	@Date 2016-5-17
	 *  @param request
	 *  @return 用户首页view
	 */
	@RequestMapping(value = "userList")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("userList");
		return mv;
	}

	/**
	 * 获取用户列表
	 *	@author zhengshan
	 *	@Date 2016-5-17
	 *  @param request
	 *  @param dto 用户dto
	 *  @return 用户列表json结果集
	 */
	@RequestMapping(value = "/getUserList", produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String getUserList(HttpServletRequest request, UserDTO dto) {
		List<UserBean> list = userServiceImpl.getUserList(dto);
		String text = "{\"total\":" + dto.getTotal() + ",\"rows\":" + JSON.toJSONString(list)+ "}";
		return text;
	}
	
	/**
	 * 弹出用户新增、修改页面
	 *	@author zhengshan
	 *	@Date 2016-5-17
	 *  @param dto 用户dto
	 *  @return 用户新增、修改view
	 */
	@RequestMapping(value = "/popuEdit")
	@ResponseBody
	public ModelAndView popuEdit(UserDTO dto) {
		ModelAndView mv = new ModelAndView("User/userEdit");
		
		mv.addObject("id", dto.getId());
		return mv;
	}
	
	/**
	 * 根据用户ID获取用户信息
	 *	@author zhengshan
	 *	@Date 2016-5-17
	 *  @param dto 用户dto
	 *  @param request
	 *  @return 用户信息json字符串
	 */
	@RequestMapping(value = "getUserById" , produces = { "application/json;charset=UTF-8" })
	public @ResponseBody UserBean getUserById(UserDTO dto, HttpServletRequest request) {
		return userServiceImpl.getUserById(dto.getId());
	}

	/**
	 * 新增、修改用户
	 *	@author zhengshan
	 *	@Date 2016-5-17
	 *  @param dto 用户dto
	 *  @param request
	 *  @return 结果dto
	 */
	@SystemControllerLog(description = "新增用户")
	@RequestMapping(value = "/saveUser", produces = { "application/json;charset=UTF-8" })
	public @ResponseBody ResultDTO saveUser(UserDTO dto, HttpServletRequest request) {
		ResultDTO result = new ResultDTO(false, "");
		
		try {
			result.setFlag(userServiceImpl.saveUser(dto));
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("保存失败！");
		}
		
		String id = dto.getId();
		if(id != null && !"".equals(id)){
			result.setAct_desc("修改用户");
		}
		result.setAct_object_name(dto.getName());
		return result;
	}

	/**
	 * 删除用户
	 *	@author zhengshan
	 *	@Date 2016-5-17
	 *  @param dto 用户dto
	 *  @param request
	 *  @return 结果dto
	 */
	@SystemControllerLog(description = "删除用户")
	@RequestMapping(value = "delUser", produces = { "application/json;charset=UTF-8" })
	public @ResponseBody ResultDTO delUser(UserDTO dto, HttpServletRequest request) {
		ResultDTO result = new ResultDTO(false, "");
		
		String[] idArray = dto.getId().split(",");
		List<String> list = Arrays.asList(idArray);
		
		try {
			result.setFlag(userServiceImpl.delUser(list));
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("删除失败！");
		}
		
		result.setAct_object_name(dto.getName());
		return result;
	}
}
