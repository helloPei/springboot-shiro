package com.demo.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.demo.common.vo.CheckBox;
import com.demo.pojo.Role;
@Mapper
public interface RoleMapper {
	/**查询所有角色的id,name字段的值，每行记录封装一个checbox对象*/
	List<CheckBox> findObjects();
	/**修改角色自身信息*/
	int updateObject(Role role);
	/**基于角色id查询关系表中角色id对应菜单id*/
	Role findObjectById(Integer id);                                                                                                                                                                 
	//SysRoleMenuResult findObjectById(Integer id);
	/**保存角色自身信息*/
	int insertObject(Role role);
	/** 基于角色id删除角色自身信息*/
	int deleteObject(Integer id);
	/**查询当前页数据*/
	List<Role> findPageObjects(
			@Param("name")String name,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize
	);
	/** 依据条件查询总记录数(要依据这个值计算总页数)*/
	int getRowCount(
			@Param("name")String name
	); 
}