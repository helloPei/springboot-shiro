package com.demo.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;

public class Log implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	//用户名
	private String username;
	//用户操作
	private String operation;
	//请求方法
	private String method;
	//请求参数
	private String params;
	//执行时长(毫秒)
	private Long time;
	//IP地址
	private String ip;
	//创建时间
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createdTime;
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getOperation() {
		return operation;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getMethod() {
		return method;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getParams() {
		return params;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getIp() {
		return ip;
	}
	public void setCreateDate(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
}