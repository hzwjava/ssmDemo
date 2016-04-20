package com.zhengs.demo.service;

import java.util.List;

import com.zhengs.demo.bo.UserBean;
import com.zhengs.demo.bo.UserDTO;

public interface IUserService {
	public List<UserBean> getUserList(UserDTO dto);
	
	public boolean saveUser(UserDTO dto);
	
	public boolean delUser(List<String> list);
}