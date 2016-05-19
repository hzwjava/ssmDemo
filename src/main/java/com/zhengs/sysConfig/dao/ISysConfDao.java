package com.zhengs.sysConfig.dao;

public interface ISysConfDao {
	public void delSysConf(String key);
	
	public String getSysConfig(String key);
}
