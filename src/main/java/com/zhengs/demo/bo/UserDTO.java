package com.zhengs.demo.bo;

import com.zhengs.bo.CommonDTO;

/**
 * 用户dto
 * @author zhengshan
 * @Date 2016-5-17
 */
public class UserDTO extends CommonDTO{
	
	private String id;
	
	private String name;
	
	private String age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}