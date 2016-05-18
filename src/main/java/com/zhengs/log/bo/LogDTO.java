package com.zhengs.log.bo;

import com.zhengs.bo.CommonDTO;

/**
 * 日志dto
 * @author zhengshan
 * @Date 2016-5-18
 */
public class LogDTO extends CommonDTO{
	/**
	 * 日志ID
	 */
	private String id;
	
	/**
	 * 日志内容
	 */
	private String message;
	
	/**
	 * 日志时间
	 */
	private String dateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

