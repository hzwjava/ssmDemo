package com.zhengs.bo;

/**
 * 公共对象dto
 * @author zhengshan
 * @Date 2016-5-17
 */
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
	private long total;
	
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

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
}