package com.zhengs.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhengs.demo.bo.UserBean;
import com.zhengs.demo.bo.UserDTO;
import com.zhengs.demo.dao.IUserDao;
import com.zhengs.demo.service.IUserService;

@Service
public class UserServiceImpl implements  IUserService {
	
	@Autowired
	private IUserDao userDao;

	@Override
	public List<UserBean> getUserList(UserDTO dto) {
		dto.setTotal(userDao.getUserListCount(dto));
		
		return userDao.getUserList(dto);
	}

	@Override
	public boolean saveUser(UserDTO dto) {
		userDao.insertUser(dto);
		return true;
	}

	@Override
	public boolean delUser(List<String> list) {
		userDao.delUser(list);
		return true;
	}
	
}