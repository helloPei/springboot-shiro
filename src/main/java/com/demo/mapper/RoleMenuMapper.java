package com.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface RoleMenuMapper {
	/**添加角色和菜单的关系数据(为角色授权操作)*/
	int insertObject(
			@Param("roleId") Integer roleId,
			@Param("menuIds")Integer[] menuIds
	);
	/**修改角色和菜单的关系数据*/
	int updateObject(
			@Param("roleId") Integer roleId,
			@Param("menuIds")Integer[] menuIds
	);
	
	/**根据菜单id删除角色与菜单的关系数据*/
	int deleteObjectsByMenuId(Integer menuId);
	/**根据角色id删除角色菜单关系中的记录*/
	int deleteObjectsByRoleId(Integer roleIds);
	
	/**根据角色id查询关系表中角色*/
	List<Integer> findMenuIdsByRoleId(
			@Param("roleIds")Integer... roleId
	);
}