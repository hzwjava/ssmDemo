package com.zhengs.bo;

public class ResultDTO {
	/**
	 * 对错标记
	 */
	private boolean flag;
	
	/**
	 * 返回消息
	 */
	private String msg;
	
	public ResultDTO(){
		
	}

	public ResultDTO(boolean flag, String msg){
		this.flag = flag;
		this.msg = msg;
	}
	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}