package com.zhengs.log.bo;

import com.zhengs.bo.CommonDTO;

public class LogDTO extends CommonDTO{
	private String id;
	
	private String message;
	
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

