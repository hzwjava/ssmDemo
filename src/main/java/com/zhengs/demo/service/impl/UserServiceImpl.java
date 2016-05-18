package com.zhengs.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhengs.demo.bo.UserBean;
import com.zhengs.demo.bo.UserDTO;
import com.zhengs.demo.dao.IUserDao;
import com.zhengs.demo.service.IUserService;

/**
 * 用户 service实现类
 * @author zhengshan
 * @Date 2016-5-18
 */
@Service
public class UserServiceImpl implements  IUserService {
	
	/**
	 * 用户dao接口
	 */
	@Autowired
	private IUserDao userDao;

	@Override
	public UserBean getUserById(String id) {
		return userDao.getUserById(id);
	}
	
	@Override
	public List<UserBean> getUserList(UserDTO dto) {
		dto.setTotal(userDao.getUserListCount(dto));
		
		return userDao.getUserList(dto);
	}

	@Override
	public boolean saveUser(UserDTO dto) {
		String id = dto.getId();
		if(null != id && !"".equals(id)){
			userDao.updateUser(dto);
		}else{
			userDao.insertUser(dto);
		}
		
		return true;
	}

	@Override
	public boolean delUser(List<String> list) {
		userDao.delUser(list);
		return true;
	}
}