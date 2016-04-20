package com.zhengs.demo.dao;

import java.util.List;

import com.zhengs.demo.bo.UserBean;
import com.zhengs.demo.bo.UserDTO;

public interface IUserDao {
	public Integer getUserListCount(UserDTO dto);
	
	public List<UserBean> getUserList(UserDTO dto);
	
	public Integer insertUser(UserDTO dto);

	public Integer updateUser(UserDTO dto);
	
	public Integer delUser(List<String> list);
}