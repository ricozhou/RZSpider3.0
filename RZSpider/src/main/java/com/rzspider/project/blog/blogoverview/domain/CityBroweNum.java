package com.rzspider.project.blog.blogoverview.domain;

/**
 * @author ricozhou
 * @date Oct 26, 2018 10:49:30 AM
 * @Desc
 */
public class CityBroweNum {
	public String name;
	public Integer value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "CityBroweNum [name=" + name + ", value=" + value + "]";
	}

}
