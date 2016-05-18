package com.zhengs.log.service;

import java.util.List;

import org.bson.Document;

import com.zhengs.log.bo.LogDTO;

/**
 * 日志service接口
 * @author zhengshan
 * @Date 2016-5-18
 */
public interface ILogService {
	/**
	 * 获取日志列表
	 *	@author zhengshan
	 *	@Date 2016-5-18
	 *  @param dto 日志dto
	 *  @return 日志列表
	 */
	public List<Document> getLogList(LogDTO dto);
}