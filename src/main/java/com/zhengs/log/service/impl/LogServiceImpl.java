package com.zhengs.log.service.impl;

import java.util.List;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Service;

import com.mongodb.client.model.Filters;
import com.zhengs.log.bo.LogDTO;
import com.zhengs.log.service.ILogService;
import com.zhengs.utils.MongoDBUtil;
import com.zhengs.utils.PropertiesUtil;

@Service
public class LogServiceImpl implements  ILogService {
	
	//mongodb操作工具类
	private MongoDBUtil mongoDBUtil =  MongoDBUtil.getInstance();
	
	private String dbName = PropertiesUtil.getValue("dbName");
	
	private String connectionName = PropertiesUtil.getValue("connectionName");
	
	@Override
	public List<Document> getLogList(LogDTO dto) {
		Bson filter = Filters.regex("message", Pattern.compile(dto.getMessage()));
		int limit = dto.getLimit();
		int pageIndex = dto.getOffset()/limit+1;
		
		dto.setTotal(mongoDBUtil.getCount(dbName, connectionName));
		List<Document> list = mongoDBUtil.findByPage(dbName, connectionName, filter, pageIndex, limit);
		
		return list;
	}
}