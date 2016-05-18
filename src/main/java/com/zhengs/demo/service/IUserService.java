package com.zhengs.demo.service;

import java.util.List;

import com.zhengs.demo.bo.UserBean;
import com.zhengs.demo.bo.UserDTO;

/**
 * 用户service接口
 * @author zhengshan
 * @Date 2016-5-18
 */
public interface IUserService {
	/**
	 * 根据用户ID获取用户信息
	 *	@author zhengshan
	 *	@Date 2016-5-18
	 *  @param id 用户ID
	 *  @return 用户信息
	 */
	public UserBean getUserById(String id);
	
	/**
	 * 获取用户列表
	 *	@author zhengshan
	 *	@Date 2016-5-18
	 *  @param dto 用户dto
	 *  @return 用户列表
	 */
	public List<UserBean> getUserList(UserDTO dto);
	
	/**
	 * 新增、修改用户
	 *	@author zhengshan
	 *	@Date 2016-5-18
	 *  @param dto 用户dto
	 *  @return 成功或失败
	 */
	public boolean saveUser(UserDTO dto);
	
	/**
	 * 删除用户
	 *	@author zhengshan
	 *	@Date 2016-5-18
	 *  @param list 用户ID集
	 *  @return 成功或失败
	 */
	public boolean delUser(List<String> list);
}