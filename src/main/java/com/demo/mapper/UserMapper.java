package com.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.demo.pojo.User;
import com.demo.pojo.vo.UserDeptResult;

/**
 * User持久层实现Mybatis框架中的Mapper接口，声名对数据库的操作方法
 * @author Administrator
 *
 */
@Mapper
public interface UserMapper {
	/**保存用户自身信息*/
	int insertObject(User user);
	/**更新用户自身信息*/
	int updateObject(User user);
	/**基于用户名获取用户对象*/
	User findUserByUserName(String username);
	/**基于用户名查询记录总数*/
	int getRowCount(@Param("username")String username);
	/**负责对用户信息执行禁用或启用操作*/
	int validById(@Param("id")Integer id, @Param("valid")Integer valid, @Param("modifiedUser")String modifiedUser);
	/**查询用户以及用户对应的部门信息*/
	UserDeptResult findObjectById(Integer id);
	/**分页查询用户以及对应的部门信息*/
	List<UserDeptResult> findPageObjects(
			@Param("username")String username, 
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize
	);
}