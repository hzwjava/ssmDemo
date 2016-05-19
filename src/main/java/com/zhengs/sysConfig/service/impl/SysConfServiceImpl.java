package com.zhengs.sysConfig.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhengs.sysConfig.dao.ISysConfDao;
import com.zhengs.sysConfig.service.ISysConfService;

@Service
public class SysConfServiceImpl implements ISysConfService{

	@Autowired
	private ISysConfDao sysConfDao;
	
	@Override
	public void delSysConf(String key) {
		sysConfDao.delSysConf(key);
	}

	@Override
	public String getSysConfig(String key) {
		return sysConfDao.getSysConfig(key);
	}
	
}
