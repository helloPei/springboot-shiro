package com.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.demo.common.vo.Node;
import com.demo.pojo.Menu;
@Mapper
public interface MenuMapper {
	/**添加菜单信息*/
	int insertObject(Menu entity);
	/**删除菜单信息*/
	int deleteObject(Integer id);
	/**修改菜单信息*/
	int updateObject(Menu entity);
	/**查询所有菜单信息*/
	List<Map<String,Object>> findObjects();
	/**查询菜单节点数据*/
	List<Node> findZtreeMenuNodes();
	
	/**根据菜单id统计子菜单的个数*/
	int getChildCount(Integer id);
	/**基于菜单id获取权限标识*/
	List<String> findPermissions(@Param("menuIds")Integer... menuIds);
}
