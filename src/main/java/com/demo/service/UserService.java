package com.demo.service;

import java.util.Map;

import com.demo.common.vo.PageObject;
import com.demo.pojo.User;
import com.demo.pojo.vo.UserDeptResult;
/**
 * User业务层接口
 * @author Administrator
 *
 */
public interface UserService {
	/** 业务层分页查询用户以及对应的部门信息 */
	PageObject<UserDeptResult> findPageObjects(String username, Integer pageCurrent);
	/** 业务层对用户信息执行禁用或启用操作 */
	int validById(Integer id, Integer valid, String modifiedUser);
	/** 根据用户名获取用户信息*/
	User findUserByUserName(String username);
	/** 将数据保存到数据库(用户信息,用户角色关系信息) */
	int saveObject(User user, Integer[] roleIds);
	/** 基于用户id查询用户以及对应的部门，角色等信息 */
	Map<String,Object> findObjectById(Integer id);
	/** 更新用户自身信息 */
	int updateObject(User user, Integer[] roleIds);
}