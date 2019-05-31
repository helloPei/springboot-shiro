package com.demo.pojo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class User implements Serializable {
	/** 实现序列化接口，生成序列号id */
	private static final long serialVersionUID = -3720062967489912501L;
	/** ID号 */
	private Integer id;
	/** 用户名 */
	private String username;
	/** 密码 */
	private String password;
	/** 盐值 */
	private String salt;
	/** 邮箱 */
	private String email;
	/** 电话号码 */
	private String mobile;
	/** 合法性等于1 */
	private Integer valid = 1;
	/** 部门id */
	private Integer deptId;
	/** 创建时间 */
	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createdTime;
	/** 修改时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date modifiedTime;
	/** 创建用户 */
	private String createdUser;
	/** 修改用户 */
	private String modifiedUser;
	/* getter/setter */
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getValid() {
		return valid;
	}
	public void setValid(Integer valid) {
		this.valid = valid;
	}
	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	public String getModifiedUser() {
		return modifiedUser;
	}
	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}
}