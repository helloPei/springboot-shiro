package com.demo.common.vo;
import java.io.Serializable;
/**
 * 值对象vo：通过
 * @author Administrator
 *
 */
public class CheckBox implements Serializable{
	private static final long serialVersionUID = -412637841166383222L;
	private Integer id;//checkbox value
	private String name;//checkbox name
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}