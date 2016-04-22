package com.zhengs.bo;


public class ActLogDTO{
	/**
	 * 操作类型
	 */
	protected ActType act_type=ActType.ADD;
	
	/**
	 * 操作对象的ID
	 */
	protected String act_object_id="";
	
	/**
	 * 操作对象名称
	 */
	protected String act_object_name="";
	
	/**
	 * 操作描述
	 */
	protected String act_desc="";

	/**
	 * 
	 * 类名称：ActType 
	 * 类描述： 日志类型枚举
	 *
	 */
	public enum ActType {
		ADD("新增", 1), DEL("删除", 2), UPDATE("修改", 3);
		
		// 成员变量
		private String name;
		private int index;

		// 构造方法
		private ActType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		// 普通方法
		public static String getName(int index) {
			for (ActType c : ActType.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		// get set 方法
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	public ActType getAct_type() {
		return act_type;
	}

	public void setAct_type(ActType act_type) {
		this.act_type = act_type;
	}

	public String getAct_object_id() {
		return act_object_id;
	}

	public void setAct_object_id(String act_object_id) {
		this.act_object_id = act_object_id;
	}

	public String getAct_object_name() {
		return act_object_name;
	}

	public void setAct_object_name(String act_object_name) {
		this.act_object_name = act_object_name;
	}

	public String getAct_desc() {
		return act_desc;
	}

	public void setAct_desc(String act_desc) {
		this.act_desc = act_desc;
	}
}

