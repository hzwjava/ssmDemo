package com.zhengs.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhengs.demo.bo.UserBean;
import com.zhengs.demo.bo.UserDTO;

/**
 * 用户数据库dao
 * @author zhengshan
 * @Date 2016-5-17
 */
public interface IUserDao {
	/**
	 * 获取用户数量
	 *	@author zhengshan
	 *	@Date 2016-5-17
	 *  @param dto 用户dto
	 *  @return 用户数量
	 */
	public Integer getUserListCount(UserDTO dto);
	
	/**
	 * 获取用户列表
	 *	@author zhengshan
	 *	@Date 2016-5-17
	 *  @param dto 用户dto
	 *  @return 获取用户列表
	 */
	public List<UserBean> getUserList(UserDTO dto);
	
	/**
	 * 根据用户ID获取用户信息
	 *	@author zhengshan
	 *	@Date 2016-5-17
	 *  @param id 用户ID
	 *  @return 用户信息
	 */
	public UserBean getUserById(@Param("id") String id);
	
	/**
	 * 插入用户
	 *	@author zhengshan
	 *	@Date 2016-5-17
	 *  @param dto 用户dto
	 *  @return 影响数据库记录个数
	 */
	public Integer insertUser(UserDTO dto);

	/**
	 * 修改用户
	 *	@author zhengshan
	 *	@Date 2016-5-18
	 *  @param dto 用户 dto
	 *  @return 影响数据库记录个数
	 */
	public Integer updateUser(UserDTO dto);
	
	/**
	 * 删除用户
	 *	@author zhengshan
	 *	@Date 2016-5-18
	 *  @param list 用户ID集
	 *  @return 影响数据库记录个数
	 */
	public Integer delUser(List<String> list);
}