package com.zhengs.bo;

public class CommonDTO {
	/**
	 * 分页属性：每页显示多少条
	 */
	private int limit;
	
	/**
	 * 分页属性：从多少条开始
	 */
	private int offset;
	
	/**
	 * 分页属性：总记录数
	 */
	private int total;
	
	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
}