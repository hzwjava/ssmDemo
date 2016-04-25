package com.zhengs.log.service;

import java.util.List;

import org.bson.Document;

import com.zhengs.log.bo.LogDTO;

public interface ILogService {
	public List<Document> getLogList(LogDTO dto);
}